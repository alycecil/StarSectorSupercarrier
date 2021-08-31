package com.github.alycecil.hullmods.abstracts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.github.alycecil.hullmods.data.LostTechData;

public abstract class CommonCombatHullmod<T extends LostTechData> extends BaseHullMod {
    public static final String CORE_RELOAD_DATA_KEY = "core_reload_data_key_losttecha_";

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        super.advanceInCombat(ship, amount);

        if (!ship.isAlive()) return;

        CombatEngineAPI engine = Global.getCombatEngine();

        String key = getDataKey() + "_" + ship.getId();
        LostTechData data = (LostTechData) engine.getCustomData().get(key);
        if (data == null) {
            data = buildData();
            engine.getCustomData().put(key, data);
        }

        data.interval.advance(amount);
        if (data.interval.intervalElapsed()) {
            doLoop(ship, (T)data);
        }

    }

    public T buildData() {
        return (T) new LostTechData();
    }

    protected abstract String getDataKey();

    protected void doLoop(ShipAPI ship, T data) {
        actionForShip(ship);
    }

    protected abstract void actionForShip(ShipAPI ship);
}
