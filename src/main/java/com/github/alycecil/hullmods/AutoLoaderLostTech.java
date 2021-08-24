package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.github.alycecil.hullmods.abstracts.CommonCombatHullmod;

import static com.github.alycecil.hullmods.helpers.ShipActions.reload;
import static com.github.alycecil.hullmods.helpers.StatChanges.commonControlTeam;
import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;

public class AutoLoaderLostTech extends CommonCombatHullmod {

    private static String MR_DATA_KEY = CORE_RELOAD_DATA_KEY + "loader";
    @Override
    protected String getDataKey() {
        return MR_DATA_KEY;
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        lostTechCommon(stats, id);
        commonControlTeam(stats, id);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        reload(ship);
    }
}
