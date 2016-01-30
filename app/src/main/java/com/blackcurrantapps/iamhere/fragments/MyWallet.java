package com.blackcurrantapps.iamhere.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blackcurrantapps.iamhere.Constants;
import com.blackcurrantapps.iamhere.QRScanner;
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

    EditText redeemAmount;

    AppUser appUser = new AppUser();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==123){
            if (resultCode == Activity.RESULT_OK) {
                final String qrString = data.getStringExtra(Constants.qrResult).trim();

                new AsyncTask<Void,Void,Boolean>(){

                    int reddemamt;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        reddemamt = Integer.valueOf(redeemAmount.getText().toString());
                        mainActivityConnect.showIntermediateProgress();

                    }

                    @Override
                    protected Boolean doInBackground(Void... voids) {

                        try {
                            mainActivityConnect.getAppUserApi().payToShop(qrString, reddemamt).execute();
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
                            mainActivityConnect.hideIntermediateProgress();
                        }

                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        final TextView walletamount = (TextView)getView().findViewById(R.id.walletBalance);
        walletamount.setAlpha(0f);

        redeemAmount = (EditText) getView().findViewById(R.id.redeemAmount);

        Button redeem = (Button)getView().findViewById(R.id.redeem);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("How to redeem")
                        .setMessage("Scan the QR Code of the Shop owner to pay a part of your bill using your Beacon Coins.")
                        .setPositiveButton("Scan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Integer.valueOf(redeemAmount.getText().toString())>=appUser.getWalletBalance())
                                startActivityForResult(new Intent(getActivity(), QRScanner.class), 123);
                            }
                        });
            }
        });

        new AsyncTask<Void,Void,Boolean>(){



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

                    walletamount.setText(appUser.getWalletBalance() + " Beacon Coins");
                    walletamount.animate().alpha(1f);

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
}
