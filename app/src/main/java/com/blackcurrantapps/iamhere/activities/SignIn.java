package com.blackcurrantapps.iamhere.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcurrantapps.iamhere.Constants;
import com.blackcurrantapps.iamhere.R;
import com.blackcurrantapps.iamin.backend.userApi.UserApi;
import com.blackcurrantapps.iamin.backend.userApi.model.AppUser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;

public class SignIn extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_GET_TOKEN = 9001;

    ProgressDialog progressDialog = null;
    //Google
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        //Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_GET_TOKEN);
            }
        });
        setGooglePlusButtonText(signInButton);
    }

    protected void setGooglePlusButtonText(SignInButton signInButton) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(com.google.android.gms.R.string.common_signin_button_text_long);
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_TOKEN) {
            // [START get_id_token]
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                handleGoogleLoginSuccess(result.getSignInAccount());
            } else {
                Snackbar.make(findViewById(android.R.id.content),"Failed to sign in",Snackbar.LENGTH_LONG).show();
            }
            // [END get_id_token]
        }
    }

    private void handleGoogleLoginSuccess(GoogleSignInAccount acct){

        String email = acct.getEmail().toLowerCase().trim();
        String name = acct.getDisplayName();
        String firstName = "";
        String fullName = "";
        try {
            String[] names = name.split(" ");
            firstName = names[0];
            for (String n : names){
                fullName = fullName + " " + n;
            }
        } catch (NullPointerException e){
            firstName = name;
            fullName = name;
        }

        String profilePic = acct.getPhotoUrl().toString();

        onLonginSuccess(email, firstName, profilePic);

        Snackbar.make(findViewById(android.R.id.content),"Signed in as "+acct.getEmail(),Snackbar.LENGTH_LONG).show();
    }

    private void onLonginSuccess(@NonNull final String email, final String firstName, final String profilePic){

        SharedPreferences pref = getSharedPreferences(Constants.savedSettings, MODE_PRIVATE);
        final SharedPreferences.Editor edit = pref.edit();

        edit.putString(Constants.emailPref, email);
        edit.putString(Constants.namePref, firstName);
        edit.putString(Constants.profilePicPref, profilePic);

        edit.apply();

        new AsyncTask<Void, Exception, String> (){


            Context context;
            boolean completed = false;
            int backoff = 0;
            String token;

            AppUser appUser = new AppUser();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                appUser.setEmail(email);
                appUser.setName(firstName);
                appUser.setProfilePic(profilePic);
            }

            @Override
            protected String doInBackground(Void... params) {

                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

                backoff = 0;
                while (!completed) {
                    Register();
                    try {
                        Thread.sleep(backoff * 1000, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (backoff == 0) backoff = 1;
                    else backoff += backoff;

                    if (backoff > 4) break;
                }

                return null;
            }

            private boolean Register() {
                try {

                    InstanceID instanceID = InstanceID.getInstance(context);
                    token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                    appUser.setRegId(token);

                    GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(context, Constants.ANDROID_AUDIENCE);
                    credential.setSelectedAccountName(email);
                    UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), credential)
                            .setApplicationName("IAMIN")
                            .setRootUrl(Constants.rootUrl);

                    UserApi appUserApi = builder.build();

                    appUserApi.userApi().register(appUser).execute();

                    subscribeTopics(token);
                    completed = true;

                } catch (IOException e) {
                    publishProgress(e);
                    e.printStackTrace();
                }
                return completed;
            }

            private void subscribeTopics(String token) throws IOException {
                for (String topic : Constants.TOPICS) {
                    GcmPubSub pubSub = GcmPubSub.getInstance(getApplicationContext());
                    pubSub.subscribe(token, "/topics/" + topic, null);
                }
            }

            @Override
            protected void onProgressUpdate(Exception... values) {
                super.onProgressUpdate(values);
                Toast.makeText(context, "Network Error. " + "Trying again in " + backoff + " sec.", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (completed) {

                    Toast.makeText(context, "Registration complete", Toast.LENGTH_LONG).show();

                    SharedPreferences settings;
                    settings = getSharedPreferences(Constants.savedSettings, MODE_PRIVATE);
                    SharedPreferences.Editor edit = settings.edit();

                    edit.putString(Constants.tokenPref, token);

                    edit.putBoolean(Constants.setupComplete, true);

                    edit.apply();
                    finish();

                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Snackbar.make(findViewById(android.R.id.content), "Failed to Connect", Snackbar.LENGTH_LONG).show();
    }
}
