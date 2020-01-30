package com.delta.deltaspeed;

import android.location.Location;

public class Clocation extends Location {
    private boolean useMetricUnits = false;

    public Clocation(Location l) {
        this(l, true);
    }

    public Clocation(Location l, boolean useMetricUnits) {
        super(l);
        this.useMetricUnits = useMetricUnits;
    }

    public boolean isUseMetricUnits() {
        return useMetricUnits;
    }

    public void setUseMetricUnits(boolean useMetricUnits) {
        this.useMetricUnits = useMetricUnits;
    }

    @Override
    public float distanceTo(Location dest) {
        float nDistance = super.distanceTo(dest);
        if (!this.isUseMetricUnits()) {
            nDistance = nDistance * 3.2f;
        }
        return nDistance;
    }

    @Override
    public double getAltitude() {
        double nAltitude = super.getAltitude();
        if (!this.isUseMetricUnits()) {
            nAltitude= nAltitude*3.2d;
        }
        return nAltitude;
    }

    @Override
    public float getSpeed() {
       float nSpeed = super.getSpeed()*3.6f;
       if (!this.isUseMetricUnits()){
           nSpeed = super.getSpeed()*2.36f;
       }
       return nSpeed;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = super.getAccuracy();
        if (!this.isUseMetricUnits()){
            nAccuracy = nAccuracy*3.2f;
        }
        return nAccuracy;
    }
}
