package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

import static com.fs.starfarer.api.impl.campaign.skills.CommandAndControl.CM_BONUS;
import static com.fs.starfarer.api.impl.hullmods.AdvancedGroundSupport.GROUND_BONUS;

public class SensorDishView extends BaseHullMod {

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if(stats == null) return;

        //combat
        //see far
        stats.getSightRadiusMod().modifyFlat(id, 4000.0F);

        //CCC
        stats.getDynamic().getMod(Stats.COORDINATED_MANEUVERS_MAX).modifyFlat(id, CM_BONUS);


        //combar ground, aid
        stats.getDynamic().getMod(Stats.FLEET_GROUND_SUPPORT).modifyFlat(id, GROUND_BONUS, "Active CCC");
        stats.getDynamic().getMod(Stats.FLEET_BOMBARD_COST_REDUCTION).modifyMult(id, 0.5f, "Active CCC");

        //non combat
        //explore
        stats.getDynamic().getMod(Stats.getSurveyCostReductionId(Commodities.HEAVY_MACHINERY)).modifyMult(id, 0.5f, "Active CCC");
        stats.getDynamic().getMod(Stats.getSurveyCostReductionId(Commodities.SUPPLIES)).modifyMult(id, 0.5f, "Active CCC");

        //salvage
        stats.getDynamic().getMod(Stats.SALVAGE_VALUE_MULT_MOD).modifyFlat(id, (Float) (1.4f));

        //game view
        stats.getDynamic().getMod(Stats.HRS_SENSOR_RANGE_MOD).modifyFlat(id, 2000f,"Over-sized Scanning Array");
    }
}
