package cl.apd.ditapp.ui.admin_notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;

import cl.apd.ditapp.R;

/**
 * Created by Raimondz on 03-10-15.
 */
public class AdminNotificationContentActivity extends AppCompatActivity {

    private ListView lv_entrada, lv_salida;

    private PairAdapter adapter_entrada;
    private PairAdapter adapter_salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification_content);

        lv_entrada = (ListView) findViewById(R.id.lv_entrada);
        lv_salida= (ListView) findViewById(R.id.lv_salida);

        adapter_entrada =new PairAdapter(this);
        adapter_entrada.add(new Pair<String, String>("Hora de confirmacion", "X"));
        adapter_entrada.add(new Pair<String, String>("Hora de llegada", "X"));
        adapter_entrada.add(new Pair<String, String>("Hora real de llegada", "X"));


        adapter_salida =new PairAdapter(this);
        adapter_salida.add(new Pair<String, String>("Hora de confirmacion", "X"));
        adapter_salida.add(new Pair<String, String>("Hora de salida", "X"));
        adapter_salida.add(new Pair<String, String>("Hora real de salida", "X"));

        lv_entrada.setAdapter(adapter_entrada);
        lv_salida.setAdapter(adapter_salida);

    }

    public void onClickEntrada(View v){
        lv_entrada.setVisibility(View.VISIBLE);
        lv_salida.setVisibility(View.GONE);
    }

    public void onClickSalida(View v)
    {
        lv_entrada.setVisibility(View.GONE);
        lv_salida.setVisibility(View.VISIBLE);
    }


    public void onClickResponse(View v)
    {
        Intent i=new Intent(this,AdminNotificationResponse.class);
        startActivity(i);
    }
}
