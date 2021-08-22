package com.github.alycecil.hullmods;


import java.util.Iterator;
import java.util.List;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class AliceWingCommanderMod extends BaseHullMod {

	public static final float DAMAGE_PENALTY 	= 0.50f;
	public static final float DAMAGE_BONUS 		= 2.50f;
	public static final float RATE_BONUS 		= 3.30f;
	public static final float RANGE_BONUS 		= 60f;
	public static final float AMMO_FROM_CARRIER = 60f;
	public static final float CREW_LOSS_MULT 	= 0.25f;
	public static final float MAX_RECHARGE_MULT = 1000f;
	
	
	@Override
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
//		stats.getMissileAmmoBonus()			 .modifyFlat(id, AMMO_FROM_CARRIER);
//		stats.getMissileRoFMult()			 .modifyMult(id, RATE_BONUS);
//		stats.getMissileWeaponDamageMult()	 .modifyMult(id, DAMAGE_PENALTY);
//		stats.getMissileWeaponRangeBonus()   .modifyFlat(id, RANGE_BONUS);
//		stats.getBallisticWeaponRangeBonus() .modifyFlat(id, RANGE_BONUS);
//		stats.getEnergyWeaponRangeBonus()    .modifyFlat(id, RANGE_BONUS);
//
//		stats.getDynamic().getStat(Stats.FIGHTER_CREW_LOSS_MULT).modifyMult(id, CREW_LOSS_MULT);
//
//		stats.getDamageToMissiles()			.modifyMult(id, DAMAGE_BONUS);
//		stats.getDamageToFighters()			.modifyMult(id, DAMAGE_BONUS);
//
//		stats.getFighterRefitTimeMult()		.modifyMult(id, MAX_RECHARGE_MULT);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		return null;
	}

}
