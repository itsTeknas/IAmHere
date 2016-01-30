package com.blackcurrantapps.iamhere.fragments;

import android.content.res.Configuration;
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
import com.blackcurrantapps.iamin.backend.userApi.model.Offer;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanket on 30/01/16 at 8:24 AM.
 * Copyright (c) BlackcurrantApps
 */
public class OffersList extends Fragment {

    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private OfferAdapter offerAdapter = new OfferAdapter();


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

    class OfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Offer> offerList = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.card_offer, parent, false);


            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
                distanceAway = (Button) itemView.findViewById(R.id.distance);
                locationName = (TextView) itemView.findViewById(R.id.location_text);
                progressWheel = (ProgressWheel) itemView.findViewById(R.id.progress_wheel);
            }
        }

    }


}
