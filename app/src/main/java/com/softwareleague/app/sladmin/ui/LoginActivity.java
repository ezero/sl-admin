package com.softwareleague.app.sladmin.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softwareleague.app.sladmin.R;
import com.softwareleague.app.sladmin.data.api.SoftwareLeagueApi;
import com.softwareleague.app.sladmin.data.api.response.UserLoginTask;
import com.softwareleague.app.sladmin.data.model.Affiliate;
import com.softwareleague.app.sladmin.data.model.ApiError;
import com.softwareleague.app.sladmin.data.model.LoginBody;
import com.softwareleague.app.sladmin.data.prefs.SessionPrefs;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private static final String DUMMY_USER_ID = "admin";
    private static final String DUMMY_PASSWORD = "password";
    private SoftwareLeagueApi mSoftLeagueApi;

    // UI references.
    private ImageView mLogoView;
    private EditText mUserIdView;
    private EditText mPasswordView;
    private TextInputLayout mFloatLabelUserId;
    private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mLoginFormView;
    // comprueba
    private UserLoginTask mAuthTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Crear adaptador Retrofit
        Retrofit mRestAdapter = new Retrofit.Builder()
                .baseUrl(SoftwareLeagueApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Crear conexión a la API de SoftwareLeague
        mSoftLeagueApi = mRestAdapter.create(SoftwareLeagueApi.class);

        mLogoView = (ImageView) findViewById(R.id.image_logo);
        mUserIdView = (EditText) findViewById(R.id.user_id);
        mPasswordView = (EditText) findViewById(R.id.password);
        mFloatLabelUserId = (TextInputLayout) findViewById(R.id.float_label_user_id);
        mFloatLabelPassword = (TextInputLayout) findViewById(R.id.float_label_password);

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // Setup
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if (!isOnline()) {
                        showLoginError(getString(R.string.error_network));
                        return false;
                    }
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();

            }
        });

    }

    public class UserLoginTask extends AsyncTask<Void,Void,Integer> {
        private final String mUserId;
        private final String mPassword;

        public UserLoginTask(String mUserId, String mPassword) {
            this.mUserId = mUserId;
            this.mPassword = mPassword;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 4;
            }

            if (!mUserId.equals(DUMMY_USER_ID)) {
                return 2;
            }

            if (!mPassword.equals(DUMMY_PASSWORD)) {
                return 3;
            }

            return 1;
        }
    }
    private void attemptLogin() {

        // Reset errors.
        mFloatLabelUserId.setError(null);
        mFloatLabelPassword.setError(null);

        // Store values at the time of the login attempt.
        String userId = mUserIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mFloatLabelPassword.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mFloatLabelPassword.setError(getString(R.string.error_invalid_password));
            focusView = mFloatLabelPassword;
            cancel = true;
        }

        // Verificar si el ID tiene contenido.
        if (TextUtils.isEmpty(userId)) {
            mFloatLabelUserId.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelUserId;
            cancel = true;
        } else if (!isUserIdValid(userId)) {
            mFloatLabelUserId.setError(getString(R.string.error_invalid_user_id));
            focusView = mFloatLabelUserId;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Mostrar el indicador de carga y luego iniciar la petición asíncrona.
            showProgress(true);
            mAuthTask = new UserLoginTask(userId, password);
            mAuthTask.execute((Void) null);
            onPostExecute(mAuthTask.doInBackground((Void) null));
            /*
            Call<Affiliate> loginCall = (Call<Affiliate>) mSoftLeagueApi.Users(new LoginBody(userId, password));
            loginCall.enqueue(new Callback<Affiliate>() {
                @Override
                public void onResponse(Call<Affiliate> call, Response<Affiliate> response) {
                    // Mostrar progreso
                    showProgress(false);

                    // Procesar errores
                    if (!response.isSuccessful()) {
                        String error = "Ha ocurrido un error. Contacte al administrador";
                        if (response.errorBody()
                                .contentType()
                                .subtype()
                                .equals("json")) {
                            ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                            error = apiError.getMessage();
                            Log.d("LoginActivity", apiError.getDeveloperMessage());
                        } else {
                            try {
                                // Reportar causas de error no relacionado con la API
                                Log.d("LoginActivity", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        showLoginError(error);
                        return;
                    }

                    // Guardar afiliado en preferencias
                    // SessionPrefs.get(LoginActivity.this).saveAffiliate(response.body());

                    // Ir al Layout Principal
                    showMainScreen();
                }

                @Override
                public void onFailure(Call<Affiliate> call, Throwable t) {
                    showProgress(false);
                    showLoginError(t.getMessage());
                }
            });
            */
        }
    }

    protected void onPostExecute(final Integer success) {
        //mAuthTask = null;
        showProgress(false);

        switch (success) {
            case 1:
                // Guardar afiliado en preferencias
                //String id, String nombre, String tipo, String login, String clave, String estado, String fechaCreacion, String fechaModificacion, String token
                SessionPrefs.get(LoginActivity.this).saveAffiliate(new Affiliate("1","Administrador","Admin","admin","password","Activo","12/04/2017","12/04/2017",""));
                showMainScreen();
                break;
            case 2:
            case 3:
                showLoginError("Cuenta de identificación o contraseña inválidos");
                break;
            case 4:
                showLoginError(getString(R.string.error_server));
                break;
        }
    }
    private boolean isUserIdValid(String userId) {
        return userId.length() >= 5;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        mLogoView.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }

    private void showMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}
