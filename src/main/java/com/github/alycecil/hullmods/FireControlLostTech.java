package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.DynamicStatsAPI;
import com.github.alycecil.hullmods.abstracts.CommonCombatHullmod;
import com.github.alycecil.hullmods.data.LostTechData;

import java.util.Set;

import static com.github.alycecil.hullmods.helpers.StatChanges.commonControlTeam;
import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;

public class FireControlLostTech extends CommonCombatHullmod {

    public static final String KEY = CORE_RELOAD_DATA_KEY + "fire_control";
    public static final float ROF_BONUS = 2f;
    public static final float DAMAGE_REDUCTION = 0.85f;
    public static final int AMMO_BONUS = 1;

    @Override
    protected String getDataKey() {
        return KEY;
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if (stats == null) return;

        lostTechCommon(stats, id);
        commonControlTeam(stats, id);

        stats.getMissileAmmoBonus().modifyFlat(id, AMMO_BONUS);
        stats.getMissileRoFMult().modifyMult(id, ROF_BONUS);
        stats.getMissileWeaponDamageMult().modifyMult(id, DAMAGE_REDUCTION);
        stats.getMissileWeaponRangeBonus().modifyFlat(id, 5000f);

        stats.getBallisticAmmoBonus().modifyFlat(id, AMMO_BONUS);
        stats.getBallisticRoFMult().modifyMult(id, ROF_BONUS);
        stats.getBallisticWeaponDamageMult().modifyMult(id, DAMAGE_REDUCTION);
        stats.getBallisticWeaponRangeBonus().modifyFlat(id, 500f);


        stats.getEnergyAmmoBonus().modifyFlat(id, AMMO_BONUS);
        stats.getEnergyRoFMult().modifyMult(id, ROF_BONUS);
        stats.getEnergyWeaponDamageMult().modifyMult(id, DAMAGE_REDUCTION);
        stats.getEnergyWeaponRangeBonus().modifyFlat(id, 500f);

        stats.getMissileWeaponFluxCostMod().modifyMult(id, 1.2f);
        stats.getEnergyWeaponFluxCostMod().modifyMult(id, 1.2f);
        stats.getBeamWeaponFluxCostMult().modifyMult(id, 1.2f);


        DynamicStatsAPI dynamic = stats.getDynamic();
        if (dynamic == null) return;

        dynamic.getStat(Stats.SURVEY_COST_REDUCTION).modifyMult(id, 0.5f);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        if (ship == null) return;
        Set<WeaponAPI> disabledWeapons = ship.getDisabledWeapons();
        if (disabledWeapons != null) {
            for (WeaponAPI weapon : disabledWeapons) {
                if (weapon.isPermanentlyDisabled())
                    continue;

                weapon.repair();

                if (!weapon.isDisabled())
                    break;
            }
        }
    }

    @Override
    public LostTechData buildData() {
        return new LostTechData(30f, 120f);
    }
}
