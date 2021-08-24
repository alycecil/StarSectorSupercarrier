package com.github.alycecil.hullmods.helpers;

import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipwideAIFlags;

public class WingAI {
    private WingAI() {
    }

    public static void aggressive(FighterWingAPI wing, ShipAPI wingMember, boolean motherShipHullDamage, float ratio) {
        if (wing.isReturning(wingMember)) {
            if ((motherShipHullDamage || wingMember.getHullLevel() > ratio) && wingMember.areSignificantEnemiesInRange()) {
                wing.stopReturning(wingMember);
            }
        } else if (motherShipHullDamage || wingMember.getHullLevel() > ratio) {
            if (wingMember.areSignificantEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            } else if (wingMember.areAnyEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 4f);
            } else {
                wingMember.getAIFlags().removeFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON);
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.SAFE_VENT, 4f);
            }
        } else {
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            wing.orderReturn(wingMember);
        }
    }

    public static void aggressiveNearby(FighterWingAPI wing, ShipAPI wingMember, boolean motherShipHullDamage, float ratio) {
        if (wing.isReturning(wingMember)) {
            if ((motherShipHullDamage || wingMember.getHullLevel() > ratio) && wingMember.areSignificantEnemiesInRange()) {
                wing.stopReturning(wingMember);
            }
        } else if (motherShipHullDamage || wingMember.getHullLevel() > ratio) {
            if (wingMember.areSignificantEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            } else if (wingMember.areAnyEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 4f);
            } else {
                wingMember.getAIFlags().removeFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON);
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.SAFE_VENT, 4f);

                wing.orderReturn(wingMember);
            }
        } else {
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            wing.orderReturn(wingMember);
        }
    }

    public static void careAboutSafety(FighterWingAPI wing, ShipAPI wingMember, boolean motherShipHullDamage) {
        if (wing.isReturning(wingMember)) {
            if ((motherShipHullDamage || wingMember.getHullLevel() > 0.75f) && wingMember.areSignificantEnemiesInRange()) {
                wing.stopReturning(wingMember);
            }
        } else if (wingMember.getHullLevel() > 0.75f) {
            if (wingMember.areSignificantEnemiesInRange()) {
                wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            }
        } else {
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.KEEP_SHIELDS_ON, 10f);
            wingMember.getAIFlags().setFlag(ShipwideAIFlags.AIFlags.NEEDS_HELP);
            wing.orderReturn(wingMember);
        }
    }
}
