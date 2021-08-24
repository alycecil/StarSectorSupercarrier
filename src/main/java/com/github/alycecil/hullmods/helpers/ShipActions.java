package com.github.alycecil.hullmods.helpers;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;

public class ShipActions {
    private ShipActions() {
    }

    public static void reload(ShipAPI ship) {
        reload(ship, false);
    }

    public static void reload(ShipAPI ship, boolean hardFlux) {
        if(ship.getFluxTracker().isOverloadedOrVenting()) return;

        for (WeaponAPI w : ship.getAllWeapons()) {
            if (w.usesAmmo()
                    && w.getAmmo() < w.getMaxAmmo()
            ) {
                w.setAmmo(w.getMaxAmmo());
                int ammo = w.getMaxAmmo() - w.getAmmo();

                if (ammo > 0) {
                    ship.getFluxTracker().increaseFlux(ammo, hardFlux);
                }
            }
            //w.repair();
        }
    }
}
