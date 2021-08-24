package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.DynamicStatsAPI;
import com.github.alycecil.hullmods.abstracts.FighterAction;

import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;

public class LostTech extends FighterAction {

    public static final float DAMAGE_PENALTY = 0.50f;
    public static final float AMMO_FROM_CARRIER = 60f;
    public static final float CREW_LOSS_MULT = 0.25f;

    public static String MR_DATA_KEY = "core_reload_data_key_lostecha";

    @Override
     protected String getDataKey() {
        return LostTech.MR_DATA_KEY;
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if(stats == null) return;
        stats.getMissileAmmoBonus().modifyFlat(id, AMMO_FROM_CARRIER);

        stats.getArmorBonus().modifyMult(id, 2f);
        stats.getArmorDamageTakenMult().modifyMult(id, DAMAGE_PENALTY);

        stats.getMaxCrewMod().modifyFlat(id, AMMO_FROM_CARRIER);

        DynamicStatsAPI dynamic = stats.getDynamic();
        if(dynamic == null) return;

        dynamic.getStat(Stats.SURVEY_COST_MULT).modifyMult(id, DAMAGE_PENALTY);
        dynamic.getStat(Stats.FIGHTER_CREW_LOSS_MULT).modifyMult(id, CREW_LOSS_MULT);

        lostTechCommon(stats, id);
    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        return null;
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        //do nothing
    }

    @Override
    protected void actionForFighter(ShipAPI ship, FighterWingAPI wing, ShipAPI wingMember) {
        if(wingMember.getMaxHitpoints() > wingMember.getHitpoints() + wingMember.getHitpoints()){
            wingMember.setHitpoints(wingMember.getMaxHitpoints()/2);
        }
    }

}
