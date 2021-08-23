package com.github.alycecil.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.util.IntervalUtil;

public class LostTech extends BaseHullMod {

    public static final float DAMAGE_PENALTY = 0.50f;
    public static final float DAMAGE_BONUS = 2.50f;
    public static final float RATE_BONUS = 3.30f;
    public static final float RANGE_BONUS = 60f;
    public static final float AMMO_FROM_CARRIER = 60f;
    public static final float CREW_LOSS_MULT = 0.25f;

    public static String MR_DATA_KEY = "core_reload_data_key_lostecha";

    public static class LostTechData {
        IntervalUtil interval = new IntervalUtil(10f, 15f);
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getMissileAmmoBonus().modifyFlat(id, AMMO_FROM_CARRIER);
        stats.getMissileRoFMult().modifyMult(id, RATE_BONUS);
        stats.getMissileWeaponDamageMult().modifyMult(id, DAMAGE_PENALTY);
        stats.getMissileWeaponRangeBonus().modifyFlat(id, RANGE_BONUS);
        stats.getBallisticWeaponRangeBonus().modifyFlat(id, RANGE_BONUS);
        stats.getEnergyWeaponRangeBonus().modifyFlat(id, RANGE_BONUS);

        stats.getArmorBonus().modifyMult(id, 2f);
        stats.getFluxCapacity().modifyMult(id, 2f);
        stats.getFluxDissipation().modifyMult(id, 2f);
        stats.getCargoMod().modifyFlat(id, 2f);
        stats.getMaxBurnLevel().modifyMult(id, 1.1f);
        stats.getFuelUseMod().modifyMult(id, 1.1f);
        stats.getAcceleration().modifyMult(id, 0.8f);
        stats.getTurnAcceleration().modifyMult(id, 1.2f);

        stats.getDynamic().getStat(Stats.DMOD_EFFECT_MULT).modifyMult(id, 3f);

        stats.getArmorDamageTakenMult().modifyMult(id, DAMAGE_PENALTY);

        stats.getMaxCrewMod().modifyFlat(id, AMMO_FROM_CARRIER);

        stats.getDynamic().getStat(Stats.SURVEY_COST_MULT).modifyMult(id, DAMAGE_PENALTY);
        stats.getDynamic().getStat(Stats.FIGHTER_CREW_LOSS_MULT).modifyMult(id, CREW_LOSS_MULT);

        stats.getDamageToMissiles().modifyMult(id, DAMAGE_BONUS);
        stats.getDamageToFighters().modifyMult(id, DAMAGE_BONUS);

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
        LostTechData data = (LostTechData) engine.getCustomData().get(key);
        if (data == null) {
            data = new LostTechData();
            engine.getCustomData().put(key, data);
        }

        data.interval.advance(amount);
        if (data.interval.intervalElapsed()) {
            doLoop(ship);
        }

    }

    private void doLoop(ShipAPI ship) {
        reload(ship);

        for (FighterWingAPI wing : ship.getAllWings()) {
            for (ShipAPI wingMember : wing.getWingMembers()) {
                if (!wingMember.isAlive()) {
                    continue;
                }
                fighterLoop(wing, wingMember);

            }
        }
    }

    private void fighterLoop(FighterWingAPI wing, ShipAPI wingMember) {
        reload(wingMember);
    }

    private void reload(ShipAPI ship) {
        for (WeaponAPI w : ship.getAllWeapons()) {
            if (w.usesAmmo()
                    && w.getAmmo() < w.getMaxAmmo()
                    && !ship.getFluxTracker().isOverloadedOrVenting()
            ) {
                w.setAmmo(w.getMaxAmmo());
                int ammo = w.getMaxAmmo() - w.getAmmo();

                if (ammo > 0) {
                    ship.getFluxTracker().increaseFlux(ammo, false);
                }
            }
            //w.repair();
        }
    }

}
