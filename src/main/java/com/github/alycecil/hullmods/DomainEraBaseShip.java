package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;

import java.util.HashMap;
import java.util.Map;

public class DomainEraBaseShip
        extends BaseLogisticsHullMod {

    public static final float NUM_BAYS = 2f;
    public static float MAINTENANCE_MULT = 0.75f;
    public static float SMOD_MODIFIER = 0.35f;

    public static float MIN_FRACTION = 0.3f;

    public static float REPAIR_RATE_BONUS = 50f;
    public static float CR_RECOVERY_BONUS = 50f;

    private static Map<ShipAPI.HullSize, Float> mag = new HashMap<>();
    static {
        mag.put(ShipAPI.HullSize.FRIGATE, 50f);
        mag.put(ShipAPI.HullSize.DESTROYER, 100f);
        mag.put(ShipAPI.HullSize.CRUISER, 200f);
        mag.put(ShipAPI.HullSize.CAPITAL_SHIP, 400f);
    }

    @Override
    public void applyEffectsBeforeShipCreation(
            ShipAPI.HullSize hullSize,
            MutableShipStatsAPI stats,
            String id
    ) {
        boolean sMod = isSMod(stats);

        super.applyEffectsBeforeShipCreation(hullSize, stats, id);
        float numBays = NUM_BAYS;
        numBays += stats.getDynamic().getMod(Stats.CONVERTED_HANGAR_MOD).computeEffective(0f);
        stats.getNumFighterBays().modifyFlat(id, numBays);

        stats.getDynamic().getMod(Stats.DEPLOYMENT_POINTS_MOD).modifyMult(id, MAINTENANCE_MULT - (sMod ? SMOD_MODIFIER : 0));

        stats.getMinCrewMod().modifyMult(id, MAINTENANCE_MULT - (sMod ? SMOD_MODIFIER : 0));
        stats.getSuppliesPerMonth().modifyMult(id, MAINTENANCE_MULT - (sMod ? SMOD_MODIFIER : 0));
        stats.getFuelUseMod().modifyMult(id, MAINTENANCE_MULT - (sMod ? SMOD_MODIFIER : 0));

        stats.getBaseCRRecoveryRatePercentPerDay().modifyPercent(id, CR_RECOVERY_BONUS);
        stats.getRepairRatePercentPerDay().modifyPercent(id, REPAIR_RATE_BONUS);

        if (sMod) {
            addCrew(hullSize, stats, id);
            addFuel(hullSize, stats, id);
            addCargo(hullSize, stats, id);
        }
    }

    public void addCargo(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float mod = (Float) mag.get(hullSize);
        if (stats.getVariant() != null) {
            mod = Math.max(stats.getVariant().getHullSpec().getCargo() * MIN_FRACTION, mod);
        }
        stats.getCargoMod().modifyFlat(id, mod);
    }

    public void addFuel(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float mod = mag.get(hullSize);
        if (stats.getVariant() != null) {
            mod = Math.max(stats.getVariant().getHullSpec().getFuel() * MIN_FRACTION, mod);
        }
        stats.getFuelMod().modifyFlat(id, mod);
    }

    public void addCrew(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float mod = mag.get(hullSize);
        if (stats.getVariant() != null) {
            mod = Math.max(stats.getVariant().getHullSpec().getMaxCrew() * MIN_FRACTION, mod);
        }
        stats.getMaxCrewMod().modifyFlat(id, mod);
    }

    public String getSModDescriptionParam(int index, ShipAPI.HullSize hullSize, ShipAPI ship) {
        if (index == 0) return "" + (int) Math.round(SMOD_MODIFIER * 100f) + "%";
        if (index == 1) return "" + ((Float) mag.get(ShipAPI.HullSize.FRIGATE)).intValue();
        if (index == 2) return "" + ((Float) mag.get(ShipAPI.HullSize.DESTROYER)).intValue();
        if (index == 3) return "" + ((Float) mag.get(ShipAPI.HullSize.CRUISER)).intValue();
        if (index == 4) return "" + ((Float) mag.get(ShipAPI.HullSize.CAPITAL_SHIP)).intValue();
        if (index == 5) return "" + (int) Math.round(MIN_FRACTION * 100f) + "%";
        return null;
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize, ShipAPI ship) {
        if (index == 0) return "" + NUM_BAYS;
        if (index == 1) return "" + (int) Math.round((1f - MAINTENANCE_MULT) * 100f) + "%";
        if (index == 2) return "" + (int) Math.round(CR_RECOVERY_BONUS) + "%";
        return null;
    }
}
