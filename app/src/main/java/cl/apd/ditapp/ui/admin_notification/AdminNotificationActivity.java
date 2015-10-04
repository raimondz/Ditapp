package cl.apd.ditapp.ui.admin_notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cl.apd.ditapp.R;

/**
 * Created by Raimondz on 03-10-15.
 */
public class AdminNotificationActivity extends AppCompatActivity {

    private ListView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);
        notification= (ListView) findViewById(R.id.lv_admin_notification);
        notification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickNotificationContent(view);
            }
        });
    }

    public void onClickNotificationContent(View v){
        Intent i=new Intent(this,AdminNotificationContentActivity.class);
        startActivity(i);
    }
}
