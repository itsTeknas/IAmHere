package com.blackcurrantapps.iamhere.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Sanket on 30/01/16 at 8:24 PM.
 * Copyright (c) BlackcurrantApps
 */
@Entity
public class LOG {
    @Id
    public Long Id;

    @Index
    public Long offerID;

    @Index
    public String userEmail;

    @Index
    public Long offerAmountClaimed;
}
