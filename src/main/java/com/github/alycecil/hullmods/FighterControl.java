package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.DynamicStatsAPI;
import com.github.alycecil.hullmods.abstracts.FighterAction;

import static com.github.alycecil.hullmods.helpers.StatChanges.commonControlTeam;
import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;
import static com.github.alycecil.hullmods.helpers.WingAI.aggressive;
import static com.github.alycecil.hullmods.helpers.WingAI.aggressiveNearby;
import static com.github.alycecil.hullmods.helpers.WingAI.careAboutSafety;

public class FighterControl extends FighterAction {


    public static String MR_DATA_KEY = CORE_RELOAD_DATA_KEY+ "fighters";
    @Override
    protected String getDataKey() {
        return MR_DATA_KEY;
    }


    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if(stats == null) return;

        commonControlTeam(stats, id);

        stats.getDamageToFighters().modifyMult(id, 2f);


        //seems to do nothing... stats.getDynamic().getStat(Stats.ALL_FIGHTER_COST_MOD).modifyFlat(id, -1f);
        DynamicStatsAPI dynamic = stats.getDynamic();
        if(dynamic == null) return;

        lostTechCommon(stats, id);

        dynamic.getMod(Stats.BOMBER_COST_MOD).modifyMult(id, 0.5f);
        dynamic.getMod(Stats.FIGHTER_COST_MOD).modifyMult(id, 0.5f);
        dynamic.getMod(Stats.INTERCEPTOR_COST_MOD).modifyMult(id, 0.5f);

        dynamic.getStat(Stats.FIGHTER_CREW_LOSS_MULT).modifyMult(id, 0.75f);
    }

    @Override
    public boolean affectsOPCosts() {
        return true;
    }

    @Override
    protected void actionForFighter(ShipAPI ship, FighterWingAPI wing, ShipAPI wingMember) {
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

    @Override
    protected void actionForShip(ShipAPI ship) {
        //do nothing
    }
}
