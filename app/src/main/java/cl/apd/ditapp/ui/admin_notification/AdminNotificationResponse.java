package cl.apd.ditapp.ui.admin_notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cl.apd.ditapp.R;

/**
 * Created by Raimondz on 04-10-15.
 */
public class AdminNotificationResponse extends AppCompatActivity{

    private EditText titulo;
    private EditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_response);
        titulo= (EditText) findViewById(R.id.et_title);
        descripcion= (EditText) findViewById(R.id.et_description);
    }

    public void onClickSend(View v)
    {
        String titulo=this.titulo.getText().toString();
        String descripcion=this.descripcion.getText().toString();
    }
}
