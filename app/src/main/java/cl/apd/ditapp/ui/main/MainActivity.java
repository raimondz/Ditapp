package cl.apd.ditapp.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import cl.apd.ditapp.R;
import cl.apd.ditapp.network.gcm.RegistrationIntentService;
import cl.apd.ditapp.util.Constants;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String gcmToken = sharedPreferences.getString(Constants.GCM_TOKEN, "");
        String userToken = sharedPreferences.getString(Constants.USER_TOKEN, "");
        Boolean isDriver = sharedPreferences.getBoolean(Constants.IS_DRIVER, true);

        if (gcmToken.isEmpty()) {
            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.lime));
                dialog.setTitleText(getResources().getString(R.string.main_text_loading));
                dialog.setCancelable(false);
                dialog.show();
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        } else {
            if (userToken.isEmpty()) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else {
                if (isDriver) {
                    /*startActivity(new Intent(MainActivity.this,
                            cl.fantasticsoft.dita.ui.driver.main.MenuActivity.class));*/
                } else {
                    /*startActivity(new Intent(MainActivity.this,
                            cl.fantasticsoft.dita.ui.assignee.main.MenuActivity.class));*/
                }
            }
            finish();
        }


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dialog.cancel();
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                String gcmToken = sharedPreferences.getString(Constants.GCM_TOKEN, "");
                String userToken = sharedPreferences.getString(Constants.USER_TOKEN, "");
                Boolean isDriver = sharedPreferences.getBoolean(Constants.IS_DRIVER, true);

                if (gcmToken.isEmpty()) {
                    Log.i(Constants.TAG, "Esto no deberia ocurrir");
                    finish();
                } else {
                    if (userToken.isEmpty()) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    } else {
                        if (isDriver) {
                            /*startActivity(new Intent(MainActivity.this,
                                    cl.fantasticsoft.dita.ui.driver.main.MenuActivity.class));*/
                        } else {
                            /*startActivity(new Intent(MainActivity.this,
                                    cl.fantasticsoft.dita.ui.assignee.main.MenuActivity.class));*/
                        }
                    }
                    finish();
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        Constants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(Constants.TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
