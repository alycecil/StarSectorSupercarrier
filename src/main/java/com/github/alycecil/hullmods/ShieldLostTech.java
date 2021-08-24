package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShieldAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.github.alycecil.hullmods.abstracts.CommonCombatHullmod;

import java.awt.*;

import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;

public class ShieldLostTech extends CommonCombatHullmod {

    public static final String KEY = CORE_RELOAD_DATA_KEY + "shield";

    @Override
    protected String getDataKey() {
        return KEY;
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        super.applyEffectsBeforeShipCreation(hullSize, stats, id);
        if (stats == null) return;

        stats.getShieldArcBonus().modifyMult(id, 2f);
        stats.getBeamShieldDamageTakenMult().modifyMult(id, 0.5f);
        stats.getProjectileShieldDamageTakenMult().modifyMult(id, 0.5f);

        lostTechCommon(stats, id);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        if (ship == null) return;

        ShieldAPI shield = ship.getShield();
        if (shield == null) return;

        shield.setInnerColor(Color.CYAN);
        shield.setRingColor(Color.YELLOW);

        if(ship.areSignificantEnemiesInRange() && !ship.getFluxTracker().isOverloadedOrVenting()){
            shield.toggleOn();
        }
    }
}
