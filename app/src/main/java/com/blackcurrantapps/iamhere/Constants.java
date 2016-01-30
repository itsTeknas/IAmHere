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
    public static final String ANDROID_AUDIENCE = "server:client_id:420353375334-oh72mt44v7rkh8692etuu0auiri6g6co.apps.googleusercontent.com";
    public static final String rootUrl = "https://iamin-events.appspot.com/_ah/api/";

    public static final String mainEventId = "MAIN_EVENT_ID";
    public static final String categoryId = "CATEGORY_ID";
    public static final String eventId = "EVENT_ID";
    public static final String ticketID = "TICKET_ID";
    public static final String iaminID = "IAMIN_ID";

    public static final String hashtag = "HASHTAG";

    public static final String mainEventName = "MAIN_EVENT_NAME";
    public static final String categoryName = "CATEGORY_NAME";
    public static final String eventName = "EVENT_NAME";

    public static final String fragmentType = "FRAG_TYPE";
    public static final String customMap = "CUSTOM_MAP";
    public static final String mainListingsFragment = "MAIN_LISTINGS_FRAGMENT";
    public static final String categoriesFragment = "CATEGORY_FRAGMENT";
    public static final String eventListFragment = "EVENT_LIST_FRAGMENT";
    public static final String iaminFragment = "I_AM_IN_FRAGMENT";
    public static final String ticketsFragmet = "TICKETS_FRAGMENT";
    public static final String prListFragment = "PR_LIST";
    public static final String managerListFragment = "MANAGER_LIST";
    public static final String eventDetailFragment = "EVENT_DETAIL_FRAGMENT";

    public static final String trendingFrag = "TRENDING_FRAG";
    public static final String upcomingFrag = "UPCOMING_FRAG";

    public static final String operationAdd = "OPERATION_ADD";
    public static final String operationEdit = "OPERATION_EDIT";

    public static final String intermediateType = "INTERMEDIATE_TYPE";
    public static final String intermediateManager = "INTERMEDIATE_MANAGER";
    public static final String intermediateEventHead = "INTERMEDIATE_EVENT_HEAD";
    public static final String intermediatePR = "INTERMEDIATE_PR";

    public static final String eventParticipation = "EventParticipation";
    public static final String multipleTicketsPerUser = "MULTIPLE_TICKETS";
    public static final String quantity = "QUANTITY";

    public static final int startActivityForQrTicket = 100;
    public static final int startActivityForQrCheckIn = 200;
    public static final int startActivityForQr = 300;
    public static final String qrResult = "QR_RESULT";

    public static final String savedClues = "SAVED_CLUES";
    public static final String savedCluesSize = "CLUES_SIZE";

    //GatePass Dialogue
    public static final String requiresGatePass = "REQUIRES_GATEPASS";

    //Cache keys
    public static final String cacheName = "IAMIN_CACHE";
    public static final String mainEventCacheKey = "maineventcachekey";
    public static final String categoryCacheKey = "categorycachekey";
    public static final String eventsListCacheKey = "eventslistcachekey";
    public static final String eventCacheKey = "eventcachekey";
    public static final String iaminCacheKey = "iaminscachekey";
    public static final String ticketsCacheKey = "ticketscachekey";
    public static final String ticketCacheKey = "ticketcachekey";
    public static final String userRolesCacheKey = "userrolescachekey";
    public static final String eventListPRCacheKey = "eventlistpr";
    public static final String appUserEventLogCacheKey = "appusereventlogcachekey";
    public static final String prEventLogCacheKey = "preventlogcachekey";
    public static final String offersListCacheKey = "offerlistcachekey";

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


    //Interests
    public static final int intrestCount = 11;
    public static final String[] intrests = {"Music Concerts", "Fashion", "Arts", "Sports", "Competitions",
            "Workshops", "Seminars", "Informals", "Literary", "Technical", "Games"};

}
