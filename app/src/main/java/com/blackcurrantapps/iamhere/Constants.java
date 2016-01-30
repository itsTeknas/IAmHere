package com.blackcurrantapps.iamhere;

/**
 * Created by Sanket on 02/06/15 at 4:48 PM.
 * Copyright (c) BlackcurrantApps
 */

/**
 * Used to Pass data between activities <--> Fragments reliably by having the KEY outside
 * Used to store cache keys
 */
public class Constants {

    public static final String emailPref = "EMAIL";
    public static final String namePref = "NAME";
    public static final String tokenPref = "TOKEN";
    public static final String phonePref = "PHONE";
    public static final String genderPref = "GENDER";
    public static final String institutionPref = "INSTITUTION";
    public static final String profilePicPref = "PROFILE_PIC";
    public static final String coverPicPref = "COVER_PIC";
    public static final String hasProfilePicPref = "HAS_DP";
    public static final String hasCoverPicPref = "HAS_COVER";
    public static final String savedSettings = "IAMHERE";
    public static final String setupComplete = "SETUP_COMPLETE";
    public static final String contactPref = "CONTACT_PREF";
    public static final String intrestsPref = "INTERESTS_PREF";
    public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/plus.profile.emails.read";
    public static final String ANDROID_AUDIENCE = "server:client_id:909484387499-dop8fak2790e8mflf3ssudu3k3urnuso.apps.googleusercontent.com";
    public static final String rootUrl = "http://i-amhere.appspot.com/_ah/api/";

    // for GCM Topic messaging
    public static final String[] TOPICS = {"EventPromos", "AdminMessage"};

    //Instagram
    public static final String instagram_APIURL = "https://api.instagram.com/v1/";
    public static final String instagramClientId = "edf8997e11884dcd844b1d5bc50ad1ff";

    //EmptyView
    public static final int EMPTY_VIEW = 123;

    //Notification Center
    public static final String notificationPref = "NOTIFICATION_PREF";
    public static final String notificationCount = "NOTIFICATION_COUNT";
    public static final String notificationTitle = "NOTIFICATION_TITLE";
    public static final String notificationMessage = "NOTIFICATION_MESSAGE";
    public static final String notificationType = "NOTIFICATION_TYPE";

    public static final int notification_TicketReceive = 1;
    public static final int notification_EventAnnouncement = 2;
    public static final int notification_AdminMessage = 3;

    //Offer Types
    public static final String DIRECT_URL_OPEN = "DIRECT_URL_OPEN";
    public static final String CALL_NUMBER = "CALL_NUMBER";
    public static final String  SHOW_CODE = "SHOW_CODE";
    public static final String SMS_NUMBER = "SMS_NUMBER";

}
