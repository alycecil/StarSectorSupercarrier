package com.github.alycecil.hullmods.data;

import com.fs.starfarer.api.characters.ShipSkillEffect;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.skills.AdvancedCountermeasures;

public class TitanLostTechData extends LostTechData {

    public TitanLostTechData(float minInterval, float maxInterval) {
        super(minInterval, maxInterval);
        this.level = Level.None;
    }

    public enum Level {
        None(null),
        One(new AdvancedCountermeasures.Level1()),
        Two(new AdvancedCountermeasures.Level2()),
        Three(new AdvancedCountermeasures.Level3A()),
        ULT(new AdvancedCountermeasures.Level3B());

        ShipSkillEffect effect;

        Level(ShipSkillEffect effect) {
            this.effect = effect;
        }

        public void unapply(ShipAPI ship) {
            if(effect!=null){

                effect.unapply(ship.getMutableStats(), ship.getHullSize(), TitanLostTechData.class.getName());
            }
        }

        public void apply(ShipAPI ship) {
            if(effect!=null){

                effect.apply(ship.getMutableStats(), ship.getHullSize(), TitanLostTechData.class.getName(), this.ordinal());
            }
        }
    }

    public Level level;
}
