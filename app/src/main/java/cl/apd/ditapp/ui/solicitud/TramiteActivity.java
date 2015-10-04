package cl.apd.ditapp.ui.solicitud;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Sucursal;
import cl.apd.ditapp.util.CloseBroadcastReceiver;
import cl.apd.ditapp.util.Constants;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TramiteActivity extends AppCompatActivity {

    CloseBroadcastReceiver receiver=new CloseBroadcastReceiver(this,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tramite);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);

        realm.beginTransaction();
        Sucursal sucursal = realm.createObject(Sucursal.class); // Create a new object
        sucursal.setId(1);
        sucursal.setDireccion("La capilla, los vilos");
        sucursal.setLatitud(-22.12321);
        sucursal.setLatitud(33.657493);
        realm.commitTransaction();

        ArrayList<String> tramites = new ArrayList<String>();
        tramites.add("Transferencia");
        tramites.add("Solicitar Credito");
        tramites.add("Credito Hipotecario");

        ListView list = (ListView)findViewById(R.id.tramitesList);

        ArrayAdapter<String> tramitesAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tramites);

        list.setAdapter(tramitesAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TramiteActivity.this, SucursalActivity.class);
                Bundle datos = new Bundle();
                datos.putString(Constants.SOLICITUD_TRAMITE, ((String) parent.getItemAtPosition(position)));
                intent.putExtras(datos);

                startActivity(intent);
            }
        });

        registerReceiver(receiver, new IntentFilter(CloseBroadcastReceiver.INTENT_ACTION));

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
