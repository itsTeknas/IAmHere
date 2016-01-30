package com.blackcurrantapps.iamhere.activities;

import com.blackcurrantapps.iamhere.backend.userApi.UserApi;

/**
 * Created by Sanket on 30/01/16 at 1:22 PM.
 * Copyright (c) BlackcurrantApps
 */
public interface MainActivityConnect {

    void addFragment(android.support.v4.app.Fragment fragment, boolean isRoot);

    void onUpwardNavigation();

    void showIntermediateProgress();

    void hideIntermediateProgress();

    void setToolbarTitle(String title);

    UserApi.UserApiOperations getAppUserApi();

    void refreshbackIcon();

}
