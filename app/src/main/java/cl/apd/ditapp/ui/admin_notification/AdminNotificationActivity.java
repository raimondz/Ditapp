package cl.apd.ditapp.ui.admin_notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Notificacion;
import cl.apd.ditapp.view.adapter.NotificacionAdapter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Raimondz on 03-10-15.
 */
public class AdminNotificationActivity extends AppCompatActivity {

    private ListView notification;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adapter.add("Notificacion");

        notification= (ListView) findViewById(R.id.lv_admin_notification);
        notification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickNotificationContent(view);
            }
        });

        notification.setAdapter(adapter);
        //init();
    }

    public void onClickNotificationContent(View v){
        Intent i=new Intent(this,AdminNotificationContentActivity.class);
        startActivity(i);
    }

    protected void init(){

        ListView listView = (ListView) findViewById(R.id.lv_admin_notification);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmResults<Notificacion> notifications = realm.where(Notificacion.class).findAll();
        notifications.sort("fecha", RealmResults.SORT_ORDER_DESCENDING);

        NotificacionAdapter adapter = new NotificacionAdapter(this, notifications, true);
        listView.setAdapter(adapter);


    }


}
