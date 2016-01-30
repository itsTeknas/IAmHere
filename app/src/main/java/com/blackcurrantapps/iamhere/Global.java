package com.blackcurrantapps.iamhere;

import android.app.Application;
import android.support.multidex.MultiDex;

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
    }
}
