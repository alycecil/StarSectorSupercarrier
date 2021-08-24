package com.github.alycecil.hullmods;

import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.github.alycecil.hullmods.abstracts.FighterAction;

import static com.github.alycecil.hullmods.helpers.ShipActions.reload;
import static com.github.alycecil.hullmods.helpers.StatChanges.commonControlTeam;
import static com.github.alycecil.hullmods.helpers.StatChanges.lostTechCommon;

public class HotResupply extends FighterAction {


    public static String MR_DATA_KEY = CORE_RELOAD_DATA_KEY+"hotresupply";

    @Override
     protected String getDataKey() {
        return HotResupply.MR_DATA_KEY;
    }


    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        return null;
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        lostTechCommon(stats, id);
        commonControlTeam(stats, id);
    }

    @Override
    protected void actionForShip(ShipAPI ship) {
        //do nothing
    }

    @Override
    protected void actionForFighter(ShipAPI ship, FighterWingAPI wing, ShipAPI wingMember) {
        reload(wingMember, true);
    }

}
