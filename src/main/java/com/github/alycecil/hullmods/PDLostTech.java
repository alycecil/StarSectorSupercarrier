package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.DynamicStatsAPI;
import com.github.alycecil.hullmods.abstracts.CommonCombatHullmod;

import static com.github.alycecil.hullmods.helpers.StatChanges.commonControlTeam;
import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;

public class PDLostTech extends CommonCombatHullmod {

    public static final String KEY = CORE_RELOAD_DATA_KEY + "pd";

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if (stats == null) return;

        commonControlTeam(stats, id);
        lostTechCommon(stats, id);

        stats.getDamageToMissiles().modifyMult(id, 2f);
        stats.getDamageToFighters().modifyMult(id, 2f);

        DynamicStatsAPI dynamic = stats.getDynamic();
        if (dynamic == null) return;

        dynamic.getMod(Stats.PD_IGNORES_FLARES).modifyFlat(id, 1f);
    }


    @Override
    protected String getDataKey() {
        return KEY;
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        for (WeaponAPI allWeapon : ship.getAllWeapons()) {
            if (allWeapon.isPermanentlyDisabled()) continue;
            if (allWeapon.getCooldownRemaining() > 0.5f) {
                allWeapon.setRemainingCooldownTo(allWeapon.getCooldownRemaining() / 2f);
            }
        }
    }
}
