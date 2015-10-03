package cl.apd.ditapp.ui.info_sucursal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cl.apd.ditapp.R;

/**
 * Created by Raimondz on 03-10-15.
 */
public class InfoSucursalActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sucursal);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        ListView lv_info_sucursal= (ListView) findViewById(R.id.lv_info_sucursal);
        adapter.add("Tiene Rampa: Si");
        adapter.add("Tiene Estacionamiento: Si");
        adapter.add("Dato Adicional: ........");

        lv_info_sucursal.setAdapter(adapter);
    }

    public void onClickStep4(View v){
        Toast.makeText(this,"Viaje agendado",Toast.LENGTH_SHORT).show();
    }
}
