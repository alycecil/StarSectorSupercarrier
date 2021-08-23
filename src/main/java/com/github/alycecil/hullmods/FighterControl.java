package com.github.alycecil.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.IntervalUtil;

public class FighterControl extends BaseHullMod {


    public static String MR_DATA_KEY = "core_reload_data_key_fighter";

    public static class FighterControlData {
        IntervalUtil interval = new IntervalUtil(10f, 15f);
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getCargoMod().modifyMult(id, 0.95f);

        stats.getMinCrewMod().modifyFlat(id, 10f);
        stats.getMaxCrewMod().modifyFlat(id, 10f);

        stats.getDamageToFighters().modifyMult(id, 2);

        stats.getDynamic().getStat(Stats.ALL_FIGHTER_COST_MOD).modifyFlat(id, 1f);
        stats.getDynamic().getStat(Stats.BOMBER_COST_MOD).modifyMult(id, 0.5f);
        stats.getDynamic().getStat(Stats.FIGHTER_COST_MOD).modifyMult(id, 0.5f);
        stats.getDynamic().getStat(Stats.INTERCEPTOR_COST_MOD).modifyMult(id, 0.5f);


        stats.getDynamic().getStat(Stats.FIGHTER_CREW_LOSS_MULT).modifyMult(id, 0.75f);
    }

    @Override
    public boolean affectsOPCosts() {
        return true;
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        return null;
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        super.advanceInCombat(ship, amount);

        if (!ship.isAlive()) return;

        CombatEngineAPI engine = Global.getCombatEngine();

        String key = MR_DATA_KEY + "_" + ship.getId();
        FighterControlData data = (FighterControlData) engine.getCustomData().get(key);
        if (data == null) {
            data = new FighterControlData();
            engine.getCustomData().put(key, data);
        }

        data.interval.advance(amount);
        if (data.interval.intervalElapsed()) {
            doLoop(ship);
        }

    }

    private void doLoop(ShipAPI ship) {
        for (FighterWingAPI wing : ship.getAllWings()) {
            for (ShipAPI wingMember : wing.getWingMembers()) {
                if (!wingMember.isAlive()) {
                    continue;
                }
                fighterLoop(ship, wing, wingMember);

            }
        }
    }

    private void fighterLoop(ShipAPI ship, FighterWingAPI wing, ShipAPI wingMember) {
        boolean motherShipHullDamage = ship.getHullLevel() < Math.max(ship.getHullLevelAtDeployment() * 0.75f, 0.65f);


        if (wing.getRole() != null) {
            switch (wing.getRole()) {
                case BOMBER:
                    careAboutSafety(wing, wingMember, motherShipHullDamage);
                    break;
                case FIGHTER:
                    aggressive(wing, wingMember, motherShipHullDamage, 0.45f);
                    break;
                case INTERCEPTOR:
                    aggressiveNearby(wing, wingMember, motherShipHullDamage, 0.45f);
                    break;
                case ASSAULT:
                    aggressive(wing, wingMember, motherShipHullDamage, 0.25f);
                    break;
                case SUPPORT:
                    aggressiveNearby(wing, wingMember, motherShipHullDamage, 0.25f);
                    break;
            }
        }
    }

    private void aggressive(FighterWingAPI wing, ShipAPI wingMember, boolean motherShipHullDamage, float ratio) {
        if (wing.isReturning(wingMember)) {
            if ((motherShipHullDamage || wingMember.getHullLevel() > ratio) && wingMember.areSignificantEnemiesInRange()) {
                wing.stopReturning(wingMember);
            }
        } else if (motherShipHullDamage || wingMember.getHullLevel() > ratio) {
            if(wingMember.areSignificantEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            }else if(wingMember.areAnyEnemiesInRange()){
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 4f);
            }else{
                wingMember.getAIFlags().removeFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON);
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.SAFE_VENT, 4f);
            }
        }else{
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            wing.orderReturn(wingMember);
        }
    }

    private void aggressiveNearby(FighterWingAPI wing, ShipAPI wingMember, boolean motherShipHullDamage, float ratio) {
        if (wing.isReturning(wingMember)) {
            if ((motherShipHullDamage || wingMember.getHullLevel() > ratio) && wingMember.areSignificantEnemiesInRange()) {
                wing.stopReturning(wingMember);
            }
        } else if (motherShipHullDamage || wingMember.getHullLevel() > ratio) {
            if(wingMember.areSignificantEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            }else if(wingMember.areAnyEnemiesInRange()){
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 4f);
            }else{
                wingMember.getAIFlags().removeFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON);
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.SAFE_VENT, 4f);

                wing.orderReturn(wingMember);
            }
        }else{
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            wing.orderReturn(wingMember);
        }
    }

    private void careAboutSafety(FighterWingAPI wing, ShipAPI wingMember, boolean motherShipHullDamage) {
        if (wing.isReturning(wingMember)) {
            if ((motherShipHullDamage || wingMember.getHullLevel() > 0.75f) && wingMember.areSignificantEnemiesInRange()) {
                wing.stopReturning(wingMember);
            }
        } else if (wingMember.getHullLevel() > 0.75f) {
            if(wingMember.areSignificantEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            }
        }else{
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.NEEDS_HELP);
            wing.orderReturn(wingMember);
        }
    }
}
