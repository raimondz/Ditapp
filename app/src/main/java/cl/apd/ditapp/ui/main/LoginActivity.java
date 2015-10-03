package cl.apd.ditapp.ui.main;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Login;
import cl.apd.ditapp.network.MainRest;
import cl.apd.ditapp.util.Constants;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask authTask = null;

    private EditText emailText;
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

        emailText = (EditText) findViewById(R.id.emailText);
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
        emailText.setError(null);
        passwordText.setError(null);

        // Store values at the time of the login attempt.
        String email = emailText.getText().toString();
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
        if (TextUtils.isEmpty(email)) {
            emailText.setError(getResources().getString(R.string.login_text_email_empty));
            focusView = emailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailText.setError(getResources().getString(R.string.login_text_email_error));
            focusView = emailText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            authTask = new UserLoginTask(email, password, gcmToken);
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

        private final String email;
        private final String password;
        private final String gcm;

        private SweetAlertDialog dialog;

        private Login login;
        private MainRest service;

        UserLoginTask(String email, String password, String gcm) {
            this.email = email;
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

            Call<Login> call = service.doLogin(email, password, gcm);

            try {
                login = call.execute().body();
            } catch (IOException e) {
                Log.e(Constants.TAG, "error: ", e);
            }

            if(login != null) {
                if(login.state != 3 && login.state != 4 && !login.token.isEmpty()) {
                    sharedPreferences.edit().putString(Constants.USER_TOKEN, login.token).apply();
                    sharedPreferences.edit().putBoolean(Constants.IS_DRIVER, login.id_driver != 0).apply();
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
                if(login.id_driver != 0) {
                    /*startActivity(new Intent(LoginActivity.this,
                            cl.fantasticsoft.dita.ui.driver.main.MenuActivity.class));*/
                }else{
                    /*startActivity(new Intent(LoginActivity.this,
                            cl.fantasticsoft.dita.ui.assignee.main.MenuActivity.class));*/
                }
                finish();
            } else {
                if(login != null) {
                    if(login.state == 3 || login.state == 4) {
                        emailText.setError(
                                getResources().getString(R.string.login_text_account_banned));
                        emailText.requestFocus();
                    }else{
                        emailText.setError(
                                getResources().getString(R.string.login_text_account_error));
                        emailText.requestFocus();
                    }
                }else {
                    passwordText.setError(
                            getResources().getString(R.string.login_text_password_error));
                    passwordText.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            dialog.cancel();
        }
    }
}
