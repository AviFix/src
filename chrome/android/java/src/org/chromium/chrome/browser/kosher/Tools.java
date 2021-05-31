package org.chromium.chrome.browser.kosher;

import android.widget.Toast;
import android.content.Intent;
import android.content.ComponentName;
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
        Intent intent = new Intent();
        intent.setAction(MARKET_ACTION);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(new ComponentName(MARKET_PKG, MARKET_PKG_RECEIVER));
        intent.putExtra(MARKET_ACTION, url);
        BackendFactory.getContextInstance().sendBroadcast(intent);
    }

    public static void logUrl(String url) {
        Log.d("url_filter", url);
        Toast.makeText(ContextUtils.getApplicationContext(), url, Toast.LENGTH_LONG).show();
    }


}