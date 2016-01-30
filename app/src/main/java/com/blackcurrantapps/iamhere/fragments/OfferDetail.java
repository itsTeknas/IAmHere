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
import com.blackcurrantapps.iamhere.events.EventCampedOn;

import java.io.IOException;

import de.greenrobot.event.EventBus;

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
        EventBus.getDefault().registerSticky(this);
    }

    public void onEventMainThread(EventCampedOn event) { //Called from EventBus
        String UUID = event.msBeacon.getBeaconUUID().toString()+","+event.msBeacon.getMinor()+","+event.msBeacon.getMajor();

        if (UUID.equalsIgnoreCase(this.UUID)){
            if (getView()!=null) claim();
        }
    }




    private void claim(){

        mainActivityConnect.showIntermediateProgress();

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
                    if (getView()!=null){
                        mainActivityConnect.hideIntermediateProgress();
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_bluetooth_connected_black_48dp));
                        status.setText("Offer Claimed");
                        instructions.setVisibility(View.GONE);
                    }
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
