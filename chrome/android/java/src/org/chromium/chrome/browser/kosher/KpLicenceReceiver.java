
package org.chromium.chrome.browser.kosher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import org.chromium.base.ContextUtils;

public class KpLicenceReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent == null) {
      return;
    }
    String idReceived = intent.getStringExtra("chrome.active");
    Toast.makeText(ContextUtils.getApplicationContext(), "Received url:" + idReceived, Toast.LENGTH_LONG).show();

//    Utilities.setKpRegistrationDate(context, idReceived.equals("active"));
  }
}
