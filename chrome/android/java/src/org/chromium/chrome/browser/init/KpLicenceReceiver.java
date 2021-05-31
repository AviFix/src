
package org.chromium.chrome.browser.init;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class KpLicenceReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    if (intent == null) {
      return;
    }
    String idReceived = intent.getStringExtra("chrome.active");
    Toast.makeText(ContextUtils.getApplicationContext(), "Received:" + idReceived, Toast.LENGTH_LONG).show();

//    Utilities.setKpRegistrationDate(context, idReceived.equals("active"));
  }
}
