package com.haydideneyelim.smartgreen;

public class Apartment {
    boolean isLampOpen, isWaterOpen, isTvOpen;
    double powerLast, pvSum, mainsSum;
    double cost;
    public static double price = 0.72;
    public static double lampConsumption = 0.89, waterConsumption = 1.41, tvConsumption = 0.23;

    public Apartment() {
        isLampOpen = false;
        isWaterOpen = false;
        isTvOpen = false;

        powerLast = 0;
        pvSum = 0;
        mainsSum = 0;
        cost = 0;
    }

    public void updateCurrentPower() {
        powerLast = 0;
        if (isLampOpen) powerLast+=lampConsumption;
        if (isWaterOpen) powerLast+=waterConsumption;
        if (isTvOpen) powerLast+=tvConsumption;
    }

    public void updateSums(boolean isMains) {
        if (isMains) {
            mainsSum += powerLast;
            cost = mainsSum*price;
        }
        else
            pvSum += powerLast;
    }

    public void reset() {
        powerLast = 0;
        pvSum = 0;
        mainsSum = 0;
        cost = 0;
    }
}
