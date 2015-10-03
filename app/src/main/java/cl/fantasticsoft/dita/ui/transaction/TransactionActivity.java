package cl.fantasticsoft.dita.ui.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.Route;

import cl.fantasticsoft.dita.R;
import cl.fantasticsoft.dita.ui.route_planning.RoutePlanningActivity;

/**
 * Created by Raimondz on 03-10-15.
 */
public class TransactionActivity extends AppCompatActivity {

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
    }

    public void onStepTwo(View v)
    {
        Intent intent=new Intent(TransactionActivity.this,RoutePlanningActivity.class);
        startActivity(intent);
    }
}
