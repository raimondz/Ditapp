package cl.apd.ditapp.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cl.apd.ditapp.MainApp;
import cl.apd.ditapp.R;
import cl.apd.ditapp.ui.notificacion.NotificacionActivity;
import cl.apd.ditapp.ui.solicitud.TramiteActivity;

/**
 * Created by Raimondz on 03-10-15.
 */
public class MenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickLogout(View v)
    {
        ((MainApp)getApplication()).setRut(null);
        finish();
    }

    public void onClickTramite(View v)
    {
        Intent i=new Intent(this, TramiteActivity.class);
        startActivity(i);
    }

    public void onClickNotification(View v)
    {
        Intent i=new Intent(this, NotificacionActivity.class);
        startActivity(i);
    }
}
