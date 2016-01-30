package com.blackcurrantapps.iamhere.events;

import com.mobstac.beaconstac.models.MSBeacon;

/**
 * Created by Sanket on 30/01/16 at 10:51 PM.
 * Copyright (c) BlackcurrantApps
 */
public class EventCampedOn {

    public MSBeacon msBeacon;

    public EventCampedOn(MSBeacon msBeacon){
        this.msBeacon = msBeacon;
    }
}
