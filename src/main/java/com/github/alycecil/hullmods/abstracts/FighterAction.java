package com.github.alycecil.hullmods.abstracts;

import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.ShipAPI;

public abstract class FighterAction extends CommonCombatHullmod {
    @Override
    protected void doLoop(ShipAPI ship) {
        actionForShip(ship);

        for (FighterWingAPI wing : ship.getAllWings()) {
            for (ShipAPI wingMember : wing.getWingMembers()) {
                if (!wingMember.isAlive()) {
                    continue;
                }
                actionForFighter(ship, wing, wingMember);

            }
        }
    }

    protected abstract void actionForFighter(ShipAPI ship, FighterWingAPI wing, ShipAPI wingMember);
}
