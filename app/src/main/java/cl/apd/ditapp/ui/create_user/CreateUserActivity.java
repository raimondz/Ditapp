package cl.apd.ditapp.ui.create_user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import cl.apd.ditapp.MainApp;
import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Respuesta;
import cl.apd.ditapp.network.MainRest;
import cl.apd.ditapp.ui.menu.MenuActivity;
import cl.apd.ditapp.util.Constants;
import cl.apd.ditapp.util.ValidationUtil;
import cl.apd.ditapp.util.genericAsyncTask.GenericAsyncTask;
import cl.apd.ditapp.util.genericAsyncTask.GenericAsyncTaskDelegate;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Raimondz on 03-10-15.
 */
public class CreateUserActivity extends AppCompatActivity implements GenericAsyncTaskDelegate{

    final static private String TAG=CreateUserActivity.class.getCanonicalName();

    final static private int RESULT_BARCODE=0;

    private EditText et_email;
    private EditText et_phone;

    private String email,phone,rut;

    private TextView et_rut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_rut = (TextView) findViewById(R.id.tv_rut);
    }

    public void onClickScan(View v)
    {
        IntentIntegrator intent=new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt(getString(R.string.create_user_barcode_scan_prompt));
        intent.setBeepEnabled(true);
        intent.initiateScan();
    }

    public void onCreateClick(View v)
    {
        SweetAlertDialog dialog=new SweetAlertDialog(this);
        dialog.setTitleText(getString(R.string.create_user_error_title));
        email=et_email.getText().toString();
        phone=et_phone.getText().toString();
        rut=et_rut.getText().toString();

        if(email.isEmpty())
        {
            dialog.setContentText(getString(R.string.create_user_error_no_email));
            dialog.show();
            return;
        }

        if(!ValidationUtil.emailValidation(email))
        {
            dialog.setContentText(getString(R.string.create_user_error_no_valid_email));
            dialog.show();
            return;
        }

        if(phone.isEmpty())
        {
            dialog.setContentText(getString(R.string.create_user_error_no_phone));
            dialog.show();
            return;
        }

        if(rut.isEmpty())
        {
            dialog.setContentText(getString(R.string.create_user_error_no_rut));
            dialog.show();
            return;
        }

        GenericAsyncTask logintask=new GenericAsyncTask(this);
        logintask.setDelegate(this);
        logintask.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK)
            return;

        switch(requestCode)
        {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult result=IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if(result==null)
                    return;
                if(result.getFormatName().equals("QR_CODE"))
                {
                    String scanned_text=result.getContents();
                    Integer run_index=scanned_text.indexOf("RUN=");
                    scanned_text=scanned_text.substring(run_index+4,run_index+4+10);
                    //Toast.makeText(this,,Toast.LENGTH_SHORT).show();
                    et_rut.setText(scanned_text);
                }
                if(result.getFormatName().equals("PDF_417")) {
                    String scanned_text = result.getContents();
                    scanned_text = scanned_text.substring(0, 9);
                    scanned_text=scanned_text.substring(0,8)+"-"+scanned_text.substring(8,9);
                    et_rut.setText(scanned_text);
                }
                break;
            default:

        }
    }

    private MainRest service;
    private Respuesta login;

    @Override
    public void onPreExecute() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MainRest.class);
    }

    @Override
    public void onBackground() {

        Call<Respuesta> call = service.doLogin(rut, email, phone);

        try {
            login = call.execute().body();
        } catch (IOException e) {
            Log.e(Constants.TAG, "error: ", e);
        }

        if(login != null) {
            if(login.status ==0) {
                ((MainApp)getApplication()).setRut(rut);
            }
        }

    }

    @Override
    public void onComplete(Boolean is_canceled) {
        if(login.status==0)
        {
            Intent i=new Intent(this, MenuActivity.class);
            startActivity(i);

            finish();
        }
    }
}
