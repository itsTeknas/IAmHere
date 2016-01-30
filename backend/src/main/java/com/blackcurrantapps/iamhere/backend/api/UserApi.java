package com.blackcurrantapps.iamhere.backend.api;

import com.blackcurrantapps.iamhere.backend.Constants;
import com.blackcurrantapps.iamhere.backend.model.AppUser;
import com.blackcurrantapps.iamhere.backend.model.Offer;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import java.util.List;
import java.util.logging.Logger;

import static com.blackcurrantapps.iamhere.backend.OfyService.ofy;

/**
 * Created by Sanket on 30/01/16 at 7:59 AM.
 * Copyright (c) BlackcurrantApps
 */
@Api(name = "userApi",
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
public class UserApi {

    private static final Logger log = Logger.getLogger(UserApi.class.getName());

    public void register(AppUser appUser, User auth) throws UnauthorizedException {
        if (auth != null) {
            appUser.email = appUser.email.toLowerCase();
            if (findDuplicate(auth.getEmail().toLowerCase()) != null) {
                log.info("User already exists, Editing User");
                if (appUser.email.equalsIgnoreCase(auth.getEmail())) {
                    ofy().save().entity(appUser);
                } else throw new UnauthorizedException("You are not who you say you are");
            } else {
                log.info("new user, registering");
                ofy().save().entity(appUser);
            }
        } else throw new UnauthorizedException("Please authenticate first.");
    }

    public List<Offer> getOffers(){
        return ofy().load().type(Offer.class).list();
    }

    public Offer claimOffer(@Named("offerID") Long offerID,User auth){

        return new Offer();
    }

    protected AppUser findDuplicate(String email) {
        return ofy().load().type(AppUser.class).id(email).now();
    }
}
