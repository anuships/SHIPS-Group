package com.example.ships.myapplication.cognitiveTherapy;

import android.app.Activity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by user on 2017/7/30.
 */

public class WhatIfScenario{

    public static void initializeWhatIfScenario(ContentHandler db){
        db.addContent(new FactSheetContent(1, "1.\tWhat if one engine fails on a plane?", "A twin-engine plane can fly perfectly well on one engine, including taking-off and landing."));
        db.addContent(new FactSheetContent(2, "2.\tWhat if the cockpit is on fire or has smoke?", "The source of fire will be stopped first and then the fire’s food will be eliminated. That is, turn off the master switch to turn off all electrical power, then close all windows and vents to starve the fire of air. Emergency landing is necessary when the situation is severe."));
        db.addContent(new FactSheetContent(3, "3.\tWhat if there are obstacles on the runway when taking-off or landing?", "Delay the taking-off or landing and contact ground service immediately. If the situation is urgent, use a steeper glide angle to avoid collision."));
        db.addContent(new FactSheetContent(4, "4.\tWhat if a bird strike happens?", "The majority of bird strikes cause little damage to the aircraft. There is only 1 accident resulting in human death in one billion flying hours. "));
        db.addContent(new FactSheetContent(5, "5.\tWhat if there is an alternator/battery failure?", "Aircraft are redundant in generators, backup-generators, batteries, and back-up batteries. The odds of a total electrical failure are slim to none. "));
        db.addContent(new FactSheetContent(6, "6.\tWhat if there is a vacuum pump failure?", "Aircraft has redundant back-ups."));
        db.addContent(new FactSheetContent(7, "7.\tWhat if the radio signal is lost?", "Pilots are expected to exercise good judgment when confronted with a radio communications problem. They may deviate from the rules to the extent required to meet an emergency. "));
        db.addContent(new FactSheetContent(8, "8.\tWhat if there is a high number of aircraft in the traffic pattern?", "Airplanes are categorised into different classes which have different flying heights. Business aircrafts only fly in controlled areas which all planes in the traffic pattern should be visible."));
        db.addContent(new FactSheetContent(9, "9.\tWhat if the cabin pressure is lost?", "Oxygen masks are provided to cater with this situation. The altitude of aircraft is required to be kept at above sea level to reduce stress on the pressurised part of the fuselage."));
        db.addContent(new FactSheetContent(10, "10.\t What if there are thunderstorm cells seen ahead?", "The aircraft is circumnavigating on the upwind side to avoid the thunderstorm, regardless of whether that will provide the best winds. "));
    }


}
