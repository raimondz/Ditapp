package cl.apd.ditapp.ui.solicitud;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.IOException;
import java.util.Calendar;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Login;
import cl.apd.ditapp.model.Respuesta;
import cl.apd.ditapp.network.MainRest;
import cl.apd.ditapp.util.Constants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ConfirmarActivity extends AppCompatActivity {

    private SolicitudTask solicitudTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        final Bundle datos = getIntent().getExtras();

        TextView tramiteText = (TextView)findViewById(R.id.tramiteText);
        TextView sucursalText = (TextView)findViewById(R.id.sucursalText);
        final TextView horaText = (TextView)findViewById(R.id.horaText);

        Button horaButton = (Button)findViewById(R.id.horaButton);
        Button confirmarButton = (Button)findViewById(R.id.confirmarButton);

        tramiteText.setText(datos.getString(Constants.SOLICITUD_TRAMITE));
        sucursalText.setText(datos.getString(Constants.SOLICITUD_SUCURSAL_DIRECCION));

        horaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ConfirmarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute > 9) {
                            horaText.setText(selectedHour + ":" + selectedMinute);
                        } else {
                            horaText.setText(selectedHour + ":0" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitudTask = new SolicitudTask(
                        // TODO poner el rut real
                        "117678482",
                        datos.getString(datos.getString(Constants.SOLICITUD_TRAMITE)),
                        datos.getInt(Constants.SOLICITUD_SUCURSAL),
                        horaText.getText().toString()
                );
            }
        });

    }

    public class SolicitudTask extends AsyncTask<Void, Void, Boolean> {

        private final String rut;
        private final String tramite;
        private final int sucursal;
        private final String hora;

        private SweetAlertDialog dialog;

        //private Login login;
        private Respuesta respuesta;
        private MainRest service;

        SolicitudTask(String rut, String tramite, int sucursal, String hora) {
            this.rut = rut;
            this.tramite = tramite;
            this.sucursal = sucursal;
            this.hora = hora;
        }

        @Override
        protected void onPreExecute() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(MainRest.class);

            dialog = new SweetAlertDialog(ConfirmarActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.lime));
            dialog.setTitleText(getResources().getString(R.string.login_text_login));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Call<Respuesta> call = service.solicitud(rut, tramite, sucursal, hora);

            try {
                respuesta = call.execute().body();
            } catch (IOException e) {
                Log.e(Constants.TAG, "error: ", e);
            }

            if(respuesta != null) {
                return respuesta.status == 0;
            }else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            solicitudTask = null;
            dialog.cancel();

            if (success) {
                //TODO crear notificacion
            } else {

            }
            finish();
        }

        @Override
        protected void onCancelled() {
            solicitudTask = null;
            dialog.cancel();
        }
    }
}
