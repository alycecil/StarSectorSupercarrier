package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class LostTech extends BaseHullMod {

    public static final float DAMAGE_PENALTY 	= 0.50f;
    public static final float DAMAGE_BONUS 		= 2.50f;
    public static final float RATE_BONUS 		= 3.30f;
    public static final float RANGE_BONUS 		= 60f;
    public static final float AMMO_FROM_CARRIER = 60f;
    public static final float CREW_LOSS_MULT 	= 0.25f;

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMissileAmmoBonus()			 .modifyFlat(id, AMMO_FROM_CARRIER);
        stats.getMissileRoFMult()			 .modifyMult(id, RATE_BONUS);
        stats.getMissileWeaponDamageMult()	 .modifyMult(id, DAMAGE_PENALTY);
        stats.getMissileWeaponRangeBonus()   .modifyFlat(id, RANGE_BONUS);
        stats.getBallisticWeaponRangeBonus() .modifyFlat(id, RANGE_BONUS);
        stats.getEnergyWeaponRangeBonus()    .modifyFlat(id, RANGE_BONUS);

        stats.getArmorDamageTakenMult()		 .modifyMult(id, DAMAGE_PENALTY);
        stats.getMaxCrewMod()				 .modifyFlat(id, AMMO_FROM_CARRIER);

        stats.getDynamic().getStat(Stats.SURVEY_COST_MULT).modifyMult(id, DAMAGE_PENALTY);
        stats.getDynamic().getStat(Stats.FIGHTER_CREW_LOSS_MULT).modifyMult(id, CREW_LOSS_MULT);

        stats.getDamageToMissiles()			.modifyMult(id, DAMAGE_BONUS);
        stats.getDamageToFighters()			.modifyMult(id, DAMAGE_BONUS);

    }

    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        return null;
    }

}
