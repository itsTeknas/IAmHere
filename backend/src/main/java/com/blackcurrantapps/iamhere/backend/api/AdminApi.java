package com.blackcurrantapps.iamhere.backend.api;

import com.blackcurrantapps.iamhere.backend.Constants;
import com.blackcurrantapps.iamhere.backend.model.Manager;
import com.blackcurrantapps.iamhere.backend.model.Offer;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import static com.blackcurrantapps.iamhere.backend.OfyService.ofy;

/**
 * Created by Sanket on 30/01/16 at 12:51 PM.
 * Copyright (c) BlackcurrantApps
 */
@Api(name = "adminApi",
        version = "v1",
        description = "Add, edit and update app users, get Offers",
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.ANDROID_CLIENT_ID_DEBUG,
                Constants.ANDROID_CLIENT_ID_RELEASE,
                com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID  // Comment this line when production ready
        },
        scopes = {Constants.EMAIL_SCOPE, com.google.api.server.spi.Constant.API_EMAIL_SCOPE},
        audiences = {Constants.ANDROID_AUDIENCE},
        namespace = @ApiNamespace(ownerDomain = "Backend.iamhere.BlackCurrantApps.com",
                ownerName = "Backend.iamhere.BlackCurrantApps.com",
                packagePath = ""))
public class AdminApi {


    public Offer addOffer(com.blackcurrantapps.iamhere.backend.model.Offer offer, User auth) throws OAuthRequestException {
        if (auth.getEmail().toLowerCase().trim().equalsIgnoreCase("sanket.berde@gmail.com")){
            ofy().save().entity(offer).now();
            return offer;
        } else throw new OAuthRequestException("Login Please");
    }

    public Manager addManager(Manager manager,User auth) throws OAuthRequestException {
        if (auth.getEmail().toLowerCase().trim().equalsIgnoreCase("sanket.berde@gmail.com")){
            ofy().save().entity(manager).now();
            return manager;
        } else throw new OAuthRequestException("Login Please");
    }

}
