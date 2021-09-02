package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.github.alycecil.hullmods.abstracts.CommonCombatHullmod;

public class TitanMod extends CommonCombatHullmod {
    private static String MR_DATA_KEY = CORE_RELOAD_DATA_KEY + "titan";

    @Override
    protected String getDataKey() {
        return MR_DATA_KEY;
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getSensorProfile().modifyFlat(id, 1000f);
        stats.getSensorStrength().modifyFlat(id, 4000f);

        stats.getEnergyWeaponDamageMult().modifyMult(id, 2f);
        stats.getEnergyDamageTakenMult().modifyMult(id, 2f);

        //no fighter mod
        stats.getDamageToFrigates().modifyMult(id, 0.75f);
        stats.getDamageToDestroyers().modifyMult(id, 0.75f);
        stats.getDamageToCruisers().modifyMult(id, 0.88f);
        stats.getDamageToMissiles().modifyMult(id, 0.4f);
        stats.getDamageToCapital().modifyMult(id, 1.5f);

        stats.getWeaponDamageTakenMult().modifyMult(id, 0.3f);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        if (ship.getHitpoints() + ship.getHitpoints() < ship.getMaxHitpoints()) {
            //slowly heal to half
            ship.setHitpoints(ship.getHitpoints() + 100f);
        }
        //stop overloads, because its unbalanced af, and i want it daddy.
        if (ship.getFluxTracker().isOverloaded() && ship.getFluxTracker().getOverloadTimeRemaining() < 10f) {
            ship.getFluxTracker().stopOverload();
        }

        for (FighterWingAPI wing : ship.getAllWings()) {
            for (ShipAPI wingMember : wing.getWingMembers()) {
                if (wingMember.getFluxTracker().isOverloaded()) {
                    wingMember.getFluxTracker().stopOverload();
                }
            }
        }

    }
}
