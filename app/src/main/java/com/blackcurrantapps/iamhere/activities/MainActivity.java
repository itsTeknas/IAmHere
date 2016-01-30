package com.blackcurrantapps.iamhere.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blackcurrantapps.iamhere.Constants;
import com.blackcurrantapps.iamhere.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Drawer drawer = null;
    private Toolbar toolbar;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.savedSettings,MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constants.setupComplete,false)){
            startActivity(new Intent(this,SignIn.class));
        }

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SetUpDrawer setUpDrawerTask = new SetUpDrawer(this, savedInstanceState);
        setUpDrawerTask.execute();

    }

    public void addFragment(android.support.v4.app.Fragment fragment, boolean isRoot) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isRoot) {
            //clear entire back stack
            if (fragmentManager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
                fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.abc_fade_out);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.replace(R.id.frame_container, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public void onUpwardNavigation() {
        fragmentManager.popBackStack();
    }

    private class SetUpDrawer extends AsyncTask<Void, Void, Void> {

        private Context context;
        private Bitmap profilePic = null;
        private SharedPreferences sharedPreferences;
        private Bundle savedInstanceState;
        private DrawerBuilder drawerBuilder;


        SetUpDrawer(Context c, Bundle savedInstanceState) {
            this.context = c;
            this.savedInstanceState = savedInstanceState;
            sharedPreferences = context.getSharedPreferences(Constants.savedSettings, MODE_PRIVATE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                if (sharedPreferences.getBoolean(Constants.hasProfilePicPref, false)) {
                    profilePic = Picasso.with(context).load(getSharedPreferences(Constants.savedSettings, 0).getString(Constants.profilePicPref, "")).get();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            final IProfile profile;

            if (profilePic == null) {
                profile = new ProfileDrawerItem()
                        .withName(getSharedPreferences(Constants.savedSettings, 0).getString(Constants.namePref, ""))
                        .withEmail(getSharedPreferences(Constants.savedSettings, 0).getString(Constants.emailPref, ""));
            } else {
                profile = new ProfileDrawerItem()
                        .withName(getSharedPreferences(Constants.savedSettings, 0).getString(Constants.namePref, ""))
                        .withEmail(getSharedPreferences(Constants.savedSettings, 0).getString(Constants.emailPref, ""))
                        .withIcon(profilePic);
            }

            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(MainActivity.this)
                    .withCompactStyle(false)
                    .addProfiles(
                            profile
                    )
                    .withSavedInstance(savedInstanceState)
                    .withHeaderBackground(R.drawable.header)
                    .build();

            drawerBuilder = new DrawerBuilder()
                    .withActivity(MainActivity.this)
                    .withToolbar(toolbar)
                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                    .withSelectedItem(0)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName("Track Folks").withIcon(GoogleMaterial.Icon.gmd_my_location),
                            new PrimaryDrawerItem().withName("Notifications").withIcon(GoogleMaterial.Icon.gmd_notifications)

                    ) // add the items we want to use with our Drawer
                    .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                        @Override
                        public boolean onNavigationClickListener(View clickedView) {
                            //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                            //if the back arrow is shown. close the activity
                            //ComplexHeaderDrawerActivity.this.finish();
                            //return true if we have consumed the event
                            onUpwardNavigation();
                            return true;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            switch (position) {

                                case 1:

                                    break;
                            }
                            return false;
                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            drawer = drawerBuilder.build();
            drawer.setSelectionAtPosition(1, false);
        }
    }
}
