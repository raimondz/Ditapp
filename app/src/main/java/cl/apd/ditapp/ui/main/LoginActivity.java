package cl.apd.ditapp.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import cl.apd.ditapp.MainApp;
import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Login;
import cl.apd.ditapp.network.MainRest;
import cl.apd.ditapp.ui.create_user.CreateUserActivity;
import cl.apd.ditapp.ui.menu.MenuActivity;
import cl.apd.ditapp.util.Constants;
import cl.apd.ditapp.util.ValidationUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask authTask = null;

    private EditText rutText;
    private EditText passwordText;


    private SharedPreferences sharedPreferences;
    private String gcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gcmToken = sharedPreferences.getString(Constants.GCM_TOKEN, "");
        String userToken = sharedPreferences.getString(Constants.USER_TOKEN, "");

        if(gcmToken.isEmpty() || !userToken.isEmpty()) {
            finish();
        }

        rutText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {
        if (authTask != null) {
            return;
        }

        // Reset errors.
        rutText.setError(null);
        passwordText.setError(null);

        // Store values at the time of the login attempt.
        String rut = rutText.getText().toString();
        String password = passwordText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordText.setError(getResources().getString(R.string.login_text_password_error));
            focusView = passwordText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(rut)) {
            rutText.setError(getResources().getString(R.string.login_text_rut_empty));
            focusView = rutText;
            cancel = true;
        } /*else if (!ValidationUtil.rutValidation(rut)) {
            rutText.setError(getResources().getString(R.string.login_rut_email_error));
            focusView = rutText;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            authTask = new UserLoginTask(rut, password, gcmToken);
            authTask.execute((Void) null);
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return !password.isEmpty();
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String rut;
        private final String password;
        private final String gcm;

        private SweetAlertDialog dialog;

        private Login login;
        private MainRest service;

        UserLoginTask(String email, String password, String gcm) {
            this.rut = email;
            this.password = password;
            this.gcm = gcm;
        }

        @Override
        protected void onPreExecute() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(MainRest.class);

            dialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.lime));
            dialog.setTitleText(getResources().getString(R.string.login_text_login));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Call<Login> call = service.doLogin(rut, password, gcm);

            try {
                login = call.execute().body();
            } catch (IOException e) {
                Log.e(Constants.TAG, "error: ", e);
            }

            if(login != null) {
                if(login.status ==0) {
                    ((MainApp)getApplication()).setRut(rut);
                }else{
                    return false;
                }
            }else {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            dialog.cancel();

            if (success) {
                openMenuActivity();
                finish();
            } else {
                if(login != null) {

                }
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            dialog.cancel();
        }
    }

    public void onClickCreateUser(View v){
        Intent i=new Intent(this, CreateUserActivity.class);
        startActivity(i);
    }

    public void openMenuActivity(){
        Intent i=new Intent(this, MenuActivity.class);
        startActivity(i);
    }

}
