package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.github.alycecil.hullmods.abstracts.CommonCombatHullmod;
import com.github.alycecil.hullmods.data.LostTechData;

public class HullTank extends CommonCombatHullmod {
    private static String MR_DATA_KEY = CORE_RELOAD_DATA_KEY+ "htank";
    @Override
    protected String getDataKey() {
        return MR_DATA_KEY;
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if(stats == null) return;

        //TODO probably should remove and tighten the speed of the action for ship
        stats.getWeaponDamageTakenMult().modifyMult(id, 0.5f);
        stats.getEngineDamageTakenMult().modifyMult(id, 0.5f);

        //always hit armor; armored supper structure.
        stats.getMinArmorFraction().modifyMult(id, 25f);

        //"slow" repair rate 1/1000 of a large number is still a pretty big number
        stats.getHullCombatRepairRatePercentPerSecond().modifyFlat(id, 0.1f);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        if(ship == null || ship.getAllWeapons() == null || !ship.areAnyEnemiesInRange()) return;

        for (WeaponAPI weapon : ship.getAllWeapons()) {
            if(!weapon.isPermanentlyDisabled()) {
                float delta = weapon.getMaxHealth() - weapon.getCurrHealth();

                weapon.repair();

                //if we actually fixed it
                if(!weapon.isDisabled()) {
                    //ping the holding ship
                    float hitpoints = ship.getHitpoints();
                    if (hitpoints > delta) {
                        ship.setHitpoints(hitpoints - delta);
                    }

                    if(ship.getFluxTracker() != null){
                        ship.getFluxTracker().increaseFlux(Math.min(delta, 100f), false);
                    }
                }
            }
        }
    }

    @Override
    public LostTechData buildData() {
        //how often to do the thing in seconds range
        return new LostTechData(1f, 3f);
    }
}
