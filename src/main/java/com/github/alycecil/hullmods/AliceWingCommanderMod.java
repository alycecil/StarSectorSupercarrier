package com.github.alycecil.hullmods;


import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class AliceWingCommanderMod extends BaseHullMod {
    public static final float MAX_RECHARGE_MULT = 1000f;

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getFighterRefitTimeMult().modifyMult(id, MAX_RECHARGE_MULT);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        return null;
    }

}
