package org.chromium.chrome.browser.kosher;

import android.widget.Toast;
import android.content.Intent;
import android.content.ComponentName;
import android.content.SharedPreferences;

import org.chromium.base.ContextUtils;
import org.chromium.base.Log;

public class Tools {
    public static final String MARKET_PKG = "com.shapsplus.kmarket";
    public static final String CHROME_REGISTRATION = "com.shapsplus.kmarket";
    public static final String MARKET_ACTION = "com.shapsplus.kmarket.comms";
    public static final String MARKET_PKG_RECEIVER = "com.shapsplus.kmarket.helpers.CommsReceiver";
    public static final String HOME_PAGE = "chrome://newtab/#most_visited";
    public static final String DEVICE_ID = "device_id";
    public static final String LAST_REQUEST = "last_request_timestamp";
    public static final String BLACK_LIST = "blackList";
    public static final String WHITE_LIST = "whitelist";
    public static final String PREFS = "my_prefs";


    public static void requestId() {
        Intent iM = new Intent();
        iM.setAction("com.sheps.comms");
        iM.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        iM.setComponent(new ComponentName(MARKET_PKG, MARKET_PKG + ".helpers.CommsReceiver"));
        iM.putExtra("get-id", "whats the id");
        ContextUtils.getApplicationContext().sendBroadcast(iM);
    }


    public static void sendCommandToKApp(String url) {
        String action = "com.sheps.url";
        String pkg = "com.shapsplus.kmarket";
        String receiverClass = pkg + ".helpers.KsUrlReceiver";

        Intent intent = new Intent();
        intent.setAction(action);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(new ComponentName(pkg, receiverClass));
        intent.putExtra("url", url);
        ContextUtils.getApplicationContext().sendBroadcast(intent);
    }

    public static void setCustomHomePage() {
        final String PREF_HOMEPAGE_ENABLED = "homepage";
        final String PREF_HOMEPAGE_CUSTOM_URI = "homepage_custom_uri";
        final String PREF_HOMEPAGE_USE_DEFAULT_URI = "homepage_partner_enabled";
        SharedPreferences mSharedPreferences = ContextUtils.getAppSharedPreferences();

//        Boolean aa = mSharedPreferences.getBoolean(PREF_HOMEPAGE_ENABLED, true);

        // if (!aa) {

        SharedPreferences.Editor sharedPreferencesEditor0 = mSharedPreferences.edit();
        sharedPreferencesEditor0.putBoolean(PREF_HOMEPAGE_USE_DEFAULT_URI, false);
        sharedPreferencesEditor0.apply();


        SharedPreferences.Editor sharedPreferencesEditor2 = mSharedPreferences.edit();
        sharedPreferencesEditor2.putString(PREF_HOMEPAGE_CUSTOM_URI, "chrome://newtab");
        sharedPreferencesEditor2.apply();

        SharedPreferences.Editor sharedPreferencesEditor = mSharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(PREF_HOMEPAGE_ENABLED, true);
        sharedPreferencesEditor.apply();


        //}
    }

    public static void logUrl(String url) {
//        Log.d("url_filter", url);
        sendCommandToKApp(url);
        Toast.makeText(ContextUtils.getApplicationContext(), "Avi:" + url, Toast.LENGTH_LONG).show();
    }

}