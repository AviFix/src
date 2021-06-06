package org.chromium.chrome.browser.kosher;

import android.widget.Toast;
import android.net.Uri;
import android.content.Intent;
import android.content.ComponentName;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import android.app.Activity;

import android.content.Context;

import java.text.ParseException;
import org.chromium.chrome.browser.kosher.KpDialog;

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
    public static final String PREFS = "kosherPlay";
    public static final String PK_REGISTRATION_STATE_KEY = "registrationState";

    public static void requestId(Context context) {
//        Toast.makeText(context, "Requesting id", Toast.LENGTH_LONG).show();

        Intent iM = new Intent();
        iM.setAction("com.sheps.comms");
        iM.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        iM.setComponent(new ComponentName(MARKET_PKG, MARKET_PKG + ".helpers.CommsReceiver"));
        iM.putExtra("get-id", "whats the id");
        context.sendBroadcast(iM);
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

    public static void logUrl(String url) {
//        Log.d("url_filter", url);
        sendCommandToKApp(url);
//        Toast.makeText(ContextUtils.getApplicationContext(), "Avi:" + url, Toast.LENGTH_LONG).show();
    }

    public static void launchAppOnPlay() {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + MARKET_PKG));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextUtils.getApplicationContext().startActivity(myIntent);
    }

    public static void launchAppOrStore() {
        Intent launchIntent = ContextUtils.getApplicationContext().getPackageManager().getLaunchIntentForPackage(MARKET_PKG);
        if (launchIntent != null) {
            ContextUtils.getApplicationContext().startActivity(launchIntent);//null pointer check in case package name was not found
        } else {
            Tools.launchAppOnPlay();
        }
    }

    public static void setKpRegistrationDate(Context cnx, Boolean avtive) {
        SharedPreferences pref = cnx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();



        if (avtive) {
            Log.i(PREFS, "Saving license");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
            String dateToSave = sdf.format(new Date());

//            Toast.makeText(ContextUtils.getApplicationContext(), "dateToSave:" + dateToSave, Toast.LENGTH_LONG).show();

            editor.putString(PK_REGISTRATION_STATE_KEY, dateToSave);
            editor.apply();
        } else {
            Log.i(PREFS, "Removing license");
//            Toast.makeText(ContextUtils.getApplicationContextlicationContext(), "Removing license", Toast.LENGTH_LONG).show();

            editor.remove(PK_REGISTRATION_STATE_KEY);
            editor.apply();

        }
    }

    public static Boolean isRegistered(Context cnx) {
        long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;


        SharedPreferences sharedPref = cnx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        String dateStr = sharedPref.getString(PK_REGISTRATION_STATE_KEY, "");

        if (!dateStr.equals("")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                Date date = sdf.parse(dateStr);
                long seconds = date.getTime();
                boolean moreThanDay = Math.abs(seconds - new Date().getTime()) > MILLIS_PER_DAY;
                Log.i(PREFS, "Is registered" + moreThanDay);
                return !moreThanDay;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void handleRegistrationBlocker(Activity cnx) {
        if (!isRegistered(cnx)) {
            KpDialog.dismiss();
            KpDialog.show(cnx, "http://kosherplay.com/mobile1/chrome.html");
        } else {
            KpDialog.dismiss();
        }

//        if (!isRegistered(cnx)) {
//            Tools.launchAppOrStore();
//        }
    }
}