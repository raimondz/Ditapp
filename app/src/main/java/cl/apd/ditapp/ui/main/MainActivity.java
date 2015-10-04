package cl.apd.ditapp.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.IOException;
import java.util.List;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Sucursal;
import cl.apd.ditapp.model.SucursalRest;
import cl.apd.ditapp.network.MainRest;
import cl.apd.ditapp.network.gcm.RegistrationIntentService;
import cl.apd.ditapp.util.Constants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SweetAlertDialog dialog;

    private SucursalesTask sucursalesTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String gcmToken = sharedPreferences.getString(Constants.GCM_TOKEN, "");
        boolean sucursalesCheck = sharedPreferences.getBoolean(Constants.SUCURSALES_CHECK, false);

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

            if(!sucursalesCheck) {
                sucursalesTask = new SucursalesTask();
                sucursalesTask.execute((Void) null);
            }else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                dialog.cancel();
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                String gcmToken = sharedPreferences.getString(Constants.GCM_TOKEN, "");
                boolean sucursalesCheck = sharedPreferences.getBoolean(Constants.SUCURSALES_CHECK, false);

                if (gcmToken.isEmpty()) {
                    Log.i(Constants.TAG, "Esto no deberia ocurrir");
                    finish();
                } else {
                    if(!sucursalesCheck) {
                        sucursalesTask = new SucursalesTask();
                        sucursalesTask.execute((Void) null);
                    }else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
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

    public class SucursalesTask extends AsyncTask<Void, Void, Boolean> {

        MainRest service;
        List<SucursalRest> sucursales;

        private SweetAlertDialog dlg;

        SucursalesTask() {
        }

        @Override
        protected void onPreExecute() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(MainRest.class);

            dlg = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            dlg.getProgressHelper().setBarColor(getResources().getColor(R.color.lime));
            dlg.setTitleText(getResources().getString(R.string.login_text_login));
            dlg.setCancelable(false);
            dlg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Call<List<SucursalRest>> call = service.sucursales();

            try {
                sucursales = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(Constants.TAG, "error: ", e);
            }

            if(sucursales != null) {

                RealmConfiguration realmConfig = new RealmConfiguration.Builder(MainActivity.this).build();
                Realm realm = Realm.getInstance(realmConfig);

                for (final SucursalRest sucursal : sucursales) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Sucursal su = realm.createObject(Sucursal.class);
                            su.setId(sucursal.id_sucursal);
                            su.setNombre(sucursal.nombre_sucursal);
                            su.setDireccion(sucursal.direccion);
                            su.setLatitud(sucursal.latitud);
                            su.setLongitud(sucursal.longitud);
                        }
                    });

                }

                return true;
            }else{
                Log.e(Constants.TAG, "sucursales vacio!");
                return false;
            }


        }

        @Override
        protected void onPostExecute(final Boolean success) {
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            sharedPreferences.edit().putBoolean(Constants.SUCURSALES_CHECK, success).apply();
            dlg.cancel();
            sucursalesTask = null;
            if(success) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            finish();
        }

        @Override
        protected void onCancelled() {
            dlg.cancel();
            sucursalesTask = null;
        }
    }
}
