package com.blackcurrantapps.iamhere.fragments;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcurrantapps.iamhere.R;
import com.blackcurrantapps.iamhere.activities.MainActivityConnect;
import com.blackcurrantapps.iamhere.backend.userApi.model.Offer;
import com.blackcurrantapps.iamhere.backend.userApi.model.OfferCollection;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanket on 30/01/16 at 8:24 AM.
 * Copyright (c) BlackcurrantApps
 */

public class OffersList extends Fragment {

    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private OfferAdapter offerAdapter = new OfferAdapter();

    MainActivityConnect mainActivityConnect;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainActivityConnect = (MainActivityConnect) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_recyclerview, container, false);

        if (jazzyScrollListener == null) {
            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            jazzyScrollListener.setTransitionEffect(JazzyHelper.SLIDE_IN);
        }


        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rootRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(jazzyScrollListener);
        mRecyclerView.setScrollbarFadingEnabled(true);
        mRecyclerView.setItemViewCacheSize(10);


        RecyclerView.LayoutManager mLayoutManager;
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new LinearLayoutManager(getActivity());
        } else { //orientation landscape
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(offerAdapter);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        new AsyncTask<Void,Void,Boolean>(){

            List<Offer> offerList = new ArrayList<Offer>();

            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    OfferCollection list = mainActivityConnect.getAppUserApi().getOffers().execute();
                    offerList = list.getItems();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }

            @Override
            protected void onPostExecute(Boolean completed) {
                super.onPostExecute(completed);
                if (completed){
                    offerAdapter.offerList = offerList;
                    offerAdapter.notifyDataSetChanged();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class OfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Offer> offerList = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.card_offer, parent, false);
            return new OfferDisplay(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            final OfferDisplay offerDisplay = (OfferDisplay) holder;

            offerDisplay.locationName.setText(offerList.get(position).getLocationName());
            offerDisplay.offerValue.setText(offerList.get(position).getOfferValue().toString() + "Bacons !");

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handlePrimaryClick(position);
                }
            };

            offerDisplay.primaryButton.setOnClickListener(listener);
            offerDisplay.primaryClick.setOnClickListener(listener);

            Picasso.with(getActivity())
                    .load(offerList.get(position).getCreativeUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(offerDisplay.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            offerDisplay.progressWheel.animate()
                                    .alpha(0f)
                                    .setDuration(700)
                                    .setListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animator) {
                                            offerDisplay.progressWheel.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animator) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animator) {

                                        }
                                    }).start();
                        }

                        @Override
                        public void onError() {
                            Picasso.with(getActivity())
                                    .load(offerList.get(position).getCreativeUrl())
                                    .error(R.drawable.header)
                                    .into(offerDisplay.imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            offerDisplay.progressWheel.animate()
                                                    .alpha(0f)
                                                    .setDuration(700)
                                                    .setListener(new Animator.AnimatorListener() {
                                                        @Override
                                                        public void onAnimationStart(Animator animator) {

                                                        }

                                                        @Override
                                                        public void onAnimationEnd(Animator animator) {
                                                            offerDisplay.progressWheel.setVisibility(View.GONE);
                                                        }

                                                        @Override
                                                        public void onAnimationCancel(Animator animator) {

                                                        }

                                                        @Override
                                                        public void onAnimationRepeat(Animator animator) {

                                                        }
                                                    }).start();
                                        }

                                        @Override
                                        public void onError() {
                                            offerDisplay.progressWheel.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    });

        }

        private void handlePrimaryClick(int position){
            //TODO Add code here

            OfferDetail offerDetail = new OfferDetail();
            Bundle args = new Bundle();
            args.putString("UUID",offerList.get(position).getBeaconId());
            offerDetail.setArguments(args);
            mainActivityConnect.addFragment(offerDetail,false);

        }

        @Override
        public int getItemCount() {
            return offerList.size();
        }

        class OfferDisplay extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public Button primaryButton;
            public TextView offerValue;
            public View primaryClick;
            public TextView distanceAway;
            public TextView locationName;
            public ProgressWheel progressWheel;

            public OfferDisplay(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.card_photo);
                primaryButton = (Button) itemView.findViewById(R.id.card_primary_action);
                offerValue = (TextView) itemView.findViewById(R.id.offer_value);
                primaryClick = itemView.findViewById(R.id.card_primary_click);
                distanceAway = (TextView) itemView.findViewById(R.id.distance);
                locationName = (TextView) itemView.findViewById(R.id.location_text);
                progressWheel = (ProgressWheel) itemView.findViewById(R.id.progress_wheel);
            }
        }

    }


}
