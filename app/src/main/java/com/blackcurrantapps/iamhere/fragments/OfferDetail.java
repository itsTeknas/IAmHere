package com.blackcurrantapps.iamhere.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcurrantapps.iamhere.R;
import com.blackcurrantapps.iamhere.activities.MainActivityConnect;

import java.io.IOException;

/**
 * Created by Sanket on 30/01/16 at 4:13 PM.
 * Copyright (c) BlackcurrantApps
 */
public class OfferDetail extends Fragment {

    MainActivityConnect mainActivityConnect;
    ImageView imageView;
    TextView status;
    TextView instructions;

    Long offerID;
    String UUID;
    String instructionsText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityConnect = (MainActivityConnect) context;

        offerID = getArguments().getLong("OFFER_ID");
        UUID = getArguments().getString("UUID");
        instructionsText = getArguments().getString("INFO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_offer_details, container, false);

        imageView = (ImageView) view.findViewById(R.id.bluetoothIcon);
        status = (TextView) view.findViewById(R.id.beaconStatus);
        instructions = (TextView) view.findViewById(R.id.instructions);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_bluetooth_searching_black_48dp));
        status.setText("Searching Beacon");
        instructions.setText(instructionsText);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //IF Beacon, then claim
        claim();

    }


    private void claim(){
        new AsyncTask<Void,Void,Boolean>(){

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mainActivityConnect.getAppUserApi().claimOffer(offerID).execute();
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
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_48dp));
                    status.setText("Offer Claimed");
                    instructions.setVisibility(View.GONE);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
