package com.blackcurrantapps.iamhere;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.mobstac.beaconstac.core.Beaconstac;
import com.mobstac.beaconstac.utils.MSException;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCacheContextUtils;

public class Global extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        DualCacheContextUtils.setContext(getApplicationContext());

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);

        Beaconstac bstacInstance = Beaconstac.getInstance(this);
        bstacInstance.setRegionParams("F94DBB23-2266-7822-3782-57BEAC0952AC",
                "com.mobstac.beaconstacexample");

        try {
            bstacInstance.startRangingBeacons();
        } catch (MSException e) {
            e.printStackTrace();
        }
    }
}
