package com.blackcurrantapps.iamhere.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Sanket on 30/01/16 at 1:01 PM.
 * Copyright (c) BlackcurrantApps
 */
@Entity
public class Manager {

    @Id
    public String email;

    @Index
    public String beaconUID;

    @Index
    public Long amountOwed;

}
