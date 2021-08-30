package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShieldAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipwideAIFlags;
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
    public void advanceInCombat(ShipAPI ship, float amount) {
        super.advanceInCombat(ship, amount);

        if (ship == null || !ship.isAlive()) return;

        ShieldAPI shield = ship.getShield();
        if (shield == null) return;

        setColor(shield);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        if (ship == null) return;

        ShieldAPI shield = ship.getShield();
        if (shield == null) return;

        setColor(shield);

        if (ship.areSignificantEnemiesInRange()
        ) {
            if (!ship.getFluxTracker().isOverloadedOrVenting() && ship.getFluxTracker().getFluxLevel() < 0.5f) {
                //shield.toggleOn();
                ship.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 1f);
            }
        } else {
            ship.getAIFlags().removeFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON);
        }
    }

    protected void setColor(ShieldAPI shield) {
        shield.setInnerColor(Color.CYAN);
        shield.setRingColor(Color.YELLOW);
    }
}
