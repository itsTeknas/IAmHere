package com.blackcurrantapps.iamhere.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackcurrantapps.iamhere.activities.MainActivityConnect;

/**
 * Created by Sanket on 30/01/16 at 4:13 PM.
 * Copyright (c) BlackcurrantApps
 */
public class OfferDetail extends Fragment {

    MainActivityConnect mainActivityConnect;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityConnect = (MainActivityConnect) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
