package com.github.alycecil.hullmods.helpers;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.DynamicStatsAPI;

public class StatChanges {
    private StatChanges() {
    }

    public static void lostTechCommon(MutableShipStatsAPI stats, String id) {
        if(stats == null) return;

        DynamicStatsAPI dynamic = stats.getDynamic();
        if(dynamic == null) return;

        dynamic.getStat(Stats.DMOD_EFFECT_MULT).modifyMult(id, 1.1f);
    }

    public static void commonControlTeam(MutableShipStatsAPI stats, String id) {
        stats.getCargoMod().modifyMult(id, 0.95f);
        stats.getMinCrewMod().modifyFlat(id, 10f);
        stats.getMaxCrewMod().modifyFlat(id, 10f);
    }
}
