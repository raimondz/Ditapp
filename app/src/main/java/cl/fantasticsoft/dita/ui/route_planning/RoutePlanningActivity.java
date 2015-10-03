package cl.fantasticsoft.dita.ui.route_planning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cl.fantasticsoft.dita.R;
import cl.fantasticsoft.dita.ui.info_sucursal.InfoSucursalActivity;

/**
 * Created by Raimondz on 03-10-15.
 */
public class RoutePlanningActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning);

    }

    public void onClickStep3(View v){
        Intent i=new Intent(this, InfoSucursalActivity.class);
        startActivity(i);

    }
}
