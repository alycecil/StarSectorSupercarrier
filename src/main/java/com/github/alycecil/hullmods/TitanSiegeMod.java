package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipwideAIFlags;
import com.github.alycecil.hullmods.abstracts.CommonCombatHullmod;
import com.github.alycecil.hullmods.data.TitanLostTechData;

public class TitanSiegeMod extends CommonCombatHullmod<TitanLostTechData> {
    public static final String KEY = CORE_RELOAD_DATA_KEY + "siege";

    @Override
    protected String getDataKey() {
        return KEY;
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        ship.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.BIGGEST_THREAT);
        ship.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.NEEDS_HELP);
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getDamageToCapital().modifyMult(id, 1.5f);
        stats.getDamageToFighters().modifyMult(id, 0.35f);
        stats.getDamageToMissiles().modifyMult(id, 0.35f);
        stats.getDamageToFighters().modifyMult(id, 0.5f);
        stats.getDamageToDestroyers().modifyMult(id, 0.75f);
        stats.getDamageToCruisers().modifyMult(id, 0.85f);
        stats.getEnergyAmmoBonus().modifyMult(id, 2f);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        //No Action, controlled by doLoop overload
    }

    @Override
    protected void doLoop(ShipAPI ship, TitanLostTechData data) {

        TitanLostTechData.Level level = getNextLevel(ship, data);

        if (level.equals(data.level)) {
            return;
        }

        if (level.ordinal() < data.level.ordinal()) {
            data.level.unapply(ship);
        } else {
            level.apply(ship);
        }
        data.level = level;

        for (FighterWingAPI wing : ship.getAllWings()) {
            for (ShipAPI wingMember : wing.getWingMembers()) {
                wingMember.getMutableStats().getDamageToCapital()
                        .modifyMult(this.getClass().getName(), 2f);
            }
        }

    }

    private TitanLostTechData.Level getNextLevel(ShipAPI ship, TitanLostTechData data) {
        if (ship.areAnyEnemiesInRange()) {
            switch (data.level) {
                case None:
                    return TitanLostTechData.Level.One;
                case One:
                    return TitanLostTechData.Level.Two;
                case Two:
                    return TitanLostTechData.Level.Three;
                case Three:
                    return TitanLostTechData.Level.ULT;
                case ULT:
                    return TitanLostTechData.Level.ULT;
            }
        } else {
            switch (data.level) {
                case None:
                    return TitanLostTechData.Level.None;
                case One:
                    return TitanLostTechData.Level.None;
                case Two:
                    return TitanLostTechData.Level.One;
                case Three:
                    return TitanLostTechData.Level.Two;
                case ULT:
                    return TitanLostTechData.Level.Three;
            }
        }
        return data.level;
    }

    @Override
    public TitanLostTechData buildData() {
        return new TitanLostTechData(30f, 45f);
    }
}
