package cl.apd.ditapp.ui.create_user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import cl.apd.ditapp.R;

/**
 * Created by Raimondz on 03-10-15.
 */
public class CreateUserActivity extends AppCompatActivity{

    final static private String TAG=CreateUserActivity.class.getCanonicalName();

    final static private int RESULT_BARCODE=0;

    private EditText email;
    private EditText phone;

    private TextView rut;

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        email= (EditText) findViewById(R.id.et_email);
        phone= (EditText) findViewById(R.id.et_phone);
        rut= (TextView) findViewById(R.id.tv_rut);
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
        //TODO realizar registro
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
                    rut.setText(scanned_text);
                }
                if(result.getFormatName().equals("PDF_417")) {
                    String scanned_text = result.getContents();
                    scanned_text = scanned_text.substring(0, 9);
                    scanned_text=scanned_text.substring(0,8)+"-"+scanned_text.substring(8,9);
                    rut.setText(scanned_text);
                }
                break;
            default:

        }
    }
}
