package com.HashMaker.hasher;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView mNavigationView;
    BottomNavigationView bottomNavigationView;
    ListView listView;

    public static Context mContext;
    public static InterstitialAd mInterstitialAd;
    private ConsentForm consentForm = null;
    public static int clickCount = 0;
    public static final int clickCountToShowAds = 2;
    public ConsentStatus userConsentStatusChoice = ConsentStatus.UNKNOWN;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawerlayout_main);
        mNavigationView = findViewById(R.id.navigation_view);
        mContext = getApplicationContext();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new FragmentAllHashtags()).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new FragmentAllHashtags()).commit();
                        break;
                    case R.id.trending_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new TrendFragment()).commit();
                        break;
                    case R.id.most_used_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new MostUsedFragment()).commit();
                        break;
                }
                return true;
            }
        });

        initializeAdmobAds();
        updateConsentStatus();

        mNavigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void updateConsentStatus() {
        final ConsentInformation consentInformation = ConsentInformation.getInstance(mContext);
        String[] publisherIds = {getString(R.string.publisher_id)};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                if (consentInformation.isRequestLocationInEeaOrUnknown()) {
                    if (consentStatus == ConsentStatus.PERSONALIZED) {
                        Log.d("CONSENT CHOSE", "USER CHOSE PERSONALIZED ADS");
                        consentInformation.setConsentStatus(consentStatus);
                        userConsentStatusChoice = consentStatus;

                    }
                    if (consentStatus == ConsentStatus.NON_PERSONALIZED) {
                        Log.d("CONSENT CHOICE", "USER CHOICE NON --- PERSONALIZED ADS");
                        consentInformation.setConsentStatus(consentStatus);
                        userConsentStatusChoice = consentStatus;

                    }
                    if (consentStatus == ConsentStatus.UNKNOWN) {
                        Log.d("CONSENT CHOICE", "USER CHOICE UNKNOWN !!!");
                        showConsentAdmobForm();
                    }

                } else {
                    removeMenuToUpdateConsent();
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                Log.d("CONSENT UPDATE", errorDescription);
            }
        });
    }

    public void showConsentAdmobForm() {
        URL privacyUrl = null;
        try {
            privacyUrl = new URL(getString(R.string.privacy_policy_url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        consentForm = new ConsentForm.Builder(MainActivity.this, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        Log.d("CONSENT FORM", "LOADED FORM");
                        showForm();
                    }

                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        Log.d("CONSENT FORM", errorDescription.toString());
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        consentForm.load();

    }

    public void showForm() {
        consentForm.show();
    }

    private void removeMenuToUpdateConsent() {
        mNavigationView.getMenu().removeItem(R.id.update_consent_eu);
    }

    private void initializeAdmobAds() {
        MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial_ad));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
                if (userConsentStatusChoice == ConsentStatus.NON_PERSONALIZED) {
                    Bundle extras = new Bundle();
                    extras.putString("npa", "1");

                    AdRequest adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            .build();
                    mInterstitialAd.loadAd(adRequest);
                    Log.d("CONSENT ADS", "BUILD NON PERSONALIZED AD REQUEST");
                } else {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    Log.d("CONSENT ADS", "BUILD SIMPLE AD REQUEST");
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.dashboard_drawer_menu: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new FragmentAllHashtags()).addToBackStack(null).commit();
                break;
            }

            case R.id.about_us_drawer_menu: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new FragmentAboutUs()).addToBackStack(null).commit();
                break;
            }
            case R.id.contact_us_drawer_menu: {

                getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new FragmentContactUs()).addToBackStack(null).commit();
                break;

            }
            case R.id.privacy_policy_drawer_menu: {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_framelayout, new FragmentPrivacyPolicy()).addToBackStack(null).commit();
                break;
            }
            case R.id.rate_us_drawer_menu: {
                Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
                }
                break;
            }

            case R.id.share_drawer_menu: {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;
            }
            case R.id.update_consent_eu: {
                showConsentAdmobForm();
                break;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        if (MainActivity.clickCount >= MainActivity.clickCountToShowAds) {
            if (MainActivity.mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                MainActivity.clickCount = 0;
            }
        } else {
            MainActivity.clickCount++;
        }
        return true;
    }


}

