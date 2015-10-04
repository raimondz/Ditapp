package cl.apd.ditapp.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Raimondz on 04-10-15.
 */
public class CloseBroadcastReceiver extends BroadcastReceiver {

    final static public String BUNDLE_KEY_CLOSE_CODE = "CLOSE_CODE";
    final static public String INTENT_ACTION=CloseBroadcastReceiver.class.getCanonicalName();

    private int close_code;
    private Activity activity;

    public CloseBroadcastReceiver(Activity activity, int close_code) {
        this.close_code = close_code;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra = intent.getExtras();
        if (extra != null) {
            int intent_code = extra.getInt(BUNDLE_KEY_CLOSE_CODE);
            if (intent_code == close_code)
                activity.finish();
        }
    }
}
