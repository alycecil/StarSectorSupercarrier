package com.github.alycecil.hullmods.data;

import com.fs.starfarer.api.util.IntervalUtil;

public class LostTechData {
    public IntervalUtil interval;

    public LostTechData() {
        this(10f, 15f);
    }

    public LostTechData(float minInterval, float maxInterval) {
        interval = new IntervalUtil(minInterval, maxInterval);
    }
}