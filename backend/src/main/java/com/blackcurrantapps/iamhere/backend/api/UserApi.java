package com.blackcurrantapps.iamhere.backend.api;

import com.blackcurrantapps.iamhere.backend.Constants;
import com.blackcurrantapps.iamhere.backend.model.AppUser;
import com.blackcurrantapps.iamhere.backend.model.LOG;
import com.blackcurrantapps.iamhere.backend.model.Manager;
import com.blackcurrantapps.iamhere.backend.model.Offer;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sun.jvm.hotspot.debugger.AddressException;

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

    public Offer claimOffer(@Named("offerID") Long offerID,User auth) throws OAuthRequestException {

        if (auth!=null){
            AppUser appUser = ofy().load().type(AppUser.class).id(auth.getEmail().toLowerCase()).now();
            Offer offer = ofy().load().type(Offer.class).id(offerID).now();

            appUser.walletBalance += offer.offerValue;

            ofy().save().entity(appUser);

            LOG log = new LOG();
            log.offerAmountClaimed = offer.offerValue;
            log.userEmail = auth.getEmail().toLowerCase().trim();
            log.offerID = offerID;

            ofy().save().entity(log).now();

            return offer;

        } else throw new OAuthRequestException("Login");
    }

    public AppUser getUser(User auth){
        return ofy().load().type(AppUser.class).id(auth.getEmail().toLowerCase()).now();
    }

    public void payToShop(@Named("managerEmail") String managerEmail, @Named("amount") int amount, User auth){

        Manager manager = ofy().load().type(Manager.class).id(managerEmail).now();
        AppUser appUser = ofy().load().type(AppUser.class).id(auth.getEmail().toLowerCase().trim()).now();

        if (appUser.walletBalance>amount){
            appUser.walletBalance -= amount;
            manager.amountOwed += amount;
            ofy().save().entity(manager);
            ofy().save().entity(appUser);

            String msgBody = 	"You have received "+amount+" Beacon Coins from "+auth.getEmail().toLowerCase()+"\nYour current balance is "+manager.amountOwed;

            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            try {

                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("sanket.berde@gmail.com", "I AM HERE Admin"));
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(String.valueOf(managerEmail)));
                msg.setSubject("Payment Confirmation");
                msg.setText(msgBody);
                Transport.send(msg);


            } catch (AddressException e) {
                // ...
            } catch (MessagingException e) {
                // ...
            } catch (Exception e){

            }

        }

    }

    protected AppUser findDuplicate(String email) {
        return ofy().load().type(AppUser.class).id(email).now();
    }
}
