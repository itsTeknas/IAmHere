package com.blackcurrantapps.iamhere.backend.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

/**
 * Created by Sanket on 30/01/16 at 7:52 AM.
 * Copyright (c) BlackcurrantApps
 */

@Entity
@Cache(expirationSeconds = 600)
public class AppUser {
    @Id
    public String email; // natural primary key, there can only be one email per user

    @Index
    public String name;

    @Unindex
    public String profilePic;

    @Unindex
    public String regId; //Used to send push messages to the user
}
