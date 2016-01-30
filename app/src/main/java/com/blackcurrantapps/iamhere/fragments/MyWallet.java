package com.blackcurrantapps.iamhere.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blackcurrantapps.iamhere.R;
import com.blackcurrantapps.iamhere.activities.MainActivityConnect;
import com.blackcurrantapps.iamhere.backend.userApi.model.AppUser;

import java.io.IOException;

/**
 * Created by Sanket on 30/01/16 at 12:48 PM.
 * Copyright (c) BlackcurrantApps
 */


public class MyWallet extends Fragment {

    MainActivityConnect mainActivityConnect;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityConnect = (MainActivityConnect) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wallet, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final TextView walletamount = (TextView)getView().findViewById(R.id.walletBalance);
        walletamount.setAlpha(0f);

        Button redeem = (Button)getView().findViewById(R.id.redeem);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Init Payment
            }
        });

        new AsyncTask<Void,Void,Boolean>(){

            AppUser appUser;

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    appUser = mainActivityConnect.getAppUserApi().getUser().execute();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (aBoolean){

                    walletamount.setText(appUser.getWalletBalance() + "\nBeacon Coins");
                    walletamount.animate().alpha(1f);

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
}
