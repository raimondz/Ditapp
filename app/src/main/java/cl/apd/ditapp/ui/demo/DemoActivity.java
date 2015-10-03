package cl.apd.ditapp.ui.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cl.apd.ditapp.R;
import cl.apd.ditapp.ui.transaction.TransactionActivity;

/**
 * Created by Raimondz on 03-10-15.
 */
public class DemoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickStep1(View v){
        Intent i=new Intent(this, TransactionActivity.class);
        startActivity(i);
    }
}
