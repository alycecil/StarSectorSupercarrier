package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;

import java.awt.*;

import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;

public class WiringLostTech extends BaseHullMod {

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        super.applyEffectsBeforeShipCreation(hullSize, stats, id);

        if(stats == null) return;
        stats.getFluxCapacity().modifyMult(id, 2f);
        stats.getFluxDissipation().modifyMult(id, 2f);
        stats.getCargoMod().modifyFlat(id, 2f);

        stats.getAcceleration().modifyMult(id, 0.8f);
        stats.getTurnAcceleration().modifyMult(id, 1.2f);

        stats.getMaxBurnLevel().modifyMult(id, 1.1f);
        stats.getFuelUseMod().modifyMult(id, 1.1f);

        lostTechCommon(stats, id);
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        super.applyEffectsAfterShipCreation(ship, id);
        if (ship == null) return;
        ship.setVentCoreColor(Color.CYAN);
        ship.setVentFringeColor(Color.YELLOW);
        ship.setAffectedByNebula(false);
    }
}
