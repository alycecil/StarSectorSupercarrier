package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class AliceFighterMod extends BaseHullMod {

	public static final float DAMAGE_BONUS 		= 0.40f;
	public static final float RATE_BONUS 		= 3.30f;
	public static final float RANGE_BONUS 		= 160f;
	public static final float AMMO_FROM_CARRIER = 2f;
	
	@Override
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMissileAmmoBonus()			 .modifyFlat(id, AMMO_FROM_CARRIER);
		stats.getMissileRoFMult()			 .modifyMult(id, RATE_BONUS);
		stats.getMissileWeaponDamageMult()	 .modifyMult(id, DAMAGE_BONUS);

		stats.getMissileWeaponRangeBonus()   .modifyFlat(id, RANGE_BONUS);
		stats.getBallisticWeaponRangeBonus() .modifyFlat(id, RANGE_BONUS);
		stats.getEnergyWeaponRangeBonus()    .modifyFlat(id, RANGE_BONUS);

		stats.getArmorDamageTakenMult()		 .modifyMult(id, DAMAGE_BONUS);

		stats.getDamageToMissiles()			.modifyMult(id, 2f);
		stats.getDamageToFighters()			.modifyMult(id, 2f);

		stats.getBeamWeaponTurnRateBonus()	 .modifyMult(id, 2f);
		stats.getBeamWeaponRangeBonus()		 .modifyFlat(id, 300f);
		stats.getAutofireAimAccuracy()		 .modifyFlat(id, 1f);

		stats.getEngineDamageTakenMult()	 .modifyMult(id, 0.33f);

		stats.getDynamic().getMod(Stats.PD_IGNORES_FLARES).modifyFlat(id, 1f);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		return null;
	}

}
