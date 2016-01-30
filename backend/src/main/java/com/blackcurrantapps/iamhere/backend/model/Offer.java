package com.blackcurrantapps.iamhere.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by Sanket on 30/01/16 at 10:24 AM.
 * Copyright (c) BlackcurrantApps
 */
@Entity
public class Offer {

    @Id
    public Long id;

    @Index
    public String creative_Url;

    @Index
    public Double latitude;

    @Index
    public Double longitude;

    @Index
    public Long offerValue;

    @Index
    public String locationName;

    @Unindex
    public String redeemInstructions;

    @Index
    public String beaconId;
}
