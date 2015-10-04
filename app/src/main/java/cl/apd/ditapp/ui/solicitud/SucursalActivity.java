package cl.apd.ditapp.ui.solicitud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Sucursal;
import cl.apd.ditapp.util.Constants;
import cl.apd.ditapp.view.adapter.SucursalAdapter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SucursalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal);

        final Bundle datos = getIntent().getExtras();

        ListView listView = (ListView) findViewById(R.id.sucursalesList);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmResults<Sucursal> notifications = realm.where(Sucursal.class).findAll();
        //notifications.sort("date", RealmResults.SORT_ORDER_DESCENDING);

        SucursalAdapter adapter = new SucursalAdapter(this, notifications, true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Sucursal sucursal = ((Sucursal) parent.getItemAtPosition(position));
                datos.putInt(Constants.SOLICITUD_SUCURSAL, sucursal.getId());
                datos.putString(Constants.SOLICITUD_SUCURSAL_DIRECCION, sucursal.getDireccion());

                Intent intent = new Intent(SucursalActivity.this, ConfirmarActivity.class);
                intent.putExtras(datos);
                startActivity(intent);
            }
        });

    }
}
