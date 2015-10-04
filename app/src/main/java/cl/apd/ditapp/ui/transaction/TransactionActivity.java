package cl.apd.ditapp.ui.transaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cl.apd.ditapp.R;
import cl.apd.ditapp.ui.route_planning.RoutePlanningActivity;
import cl.apd.ditapp.util.CloseBroadcastReceiver;

/**
 * Created by Raimondz on 03-10-15.
 */
public class TransactionActivity extends AppCompatActivity {

    CloseBroadcastReceiver receiver=new CloseBroadcastReceiver(this,0);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        ListView lv= (ListView) findViewById(R.id.lv_transaction);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adapter.add("Deposito");
        adapter.add("Credito de consumo");
        adapter.add("Otro");
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onStepTwo(view);

            }
        });

        registerReceiver(receiver,new IntentFilter(CloseBroadcastReceiver.INTENT_ACTION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void onStepTwo(View v)
    {
        Intent intent=new Intent(TransactionActivity.this,RoutePlanningActivity.class);
        startActivity(intent);
    }
}
