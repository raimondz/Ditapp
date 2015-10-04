package cl.apd.ditapp.ui.notificacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Notificacion;
import cl.apd.ditapp.view.adapter.NotificacionAdapter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class NotificacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        ListView listView = (ListView) findViewById(R.id.notificationList);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmResults<Notificacion> notifications = realm.where(Notificacion.class).findAll();
        notifications.sort("fecha", RealmResults.SORT_ORDER_DESCENDING);

        NotificacionAdapter adapter = new NotificacionAdapter(this, notifications, true);
        listView.setAdapter(adapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Notificacion notification = ((Notificacion) parent.getItemAtPosition(position));
                Bundle data = new Bundle();
                data.putString(Constant.MESSAGE_TITLE, notification.getTitle());
                data.putString(Constant.MESSAGE_CONTENT, notification.getContent());
                data.putString(Constant.MESSAGE_VOICE, notification.getVoice());
                data.putInt(Constant.MESSAGE_TYPE, notification.getType());
                data.putInt(Constant.MESSAGE_PRIORITY, notification.getPriority());
                data.putBoolean(Constant.SCREEN_LOCK, false);

                Intent intent = new Intent(NotificacionActivity.this, ViewActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
        */
    }
}
