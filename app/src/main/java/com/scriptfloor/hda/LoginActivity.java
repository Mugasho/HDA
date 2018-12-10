package com.scriptfloor.hda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scriptfloor.hda.app.AppConfig;
import com.scriptfloor.hda.app.AppController;
import com.scriptfloor.hda.utils.SQLiteHandler;
import com.scriptfloor.hda.utils.Sentinel;
import com.scriptfloor.hda.utils.SessionManager;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = com.scriptfloor.hda.LoginActivity.class.getSimpleName();
    private Button btnLogin;
    private TextView btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLinkToRegister = findViewById(R.id.linkToRegister);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                //check whether email is valid
                if (!Sentinel.isValid(email)) {
                    showAlert("Login Error", "Please check your email", R.color.red);
                }else{
                    // Check for empty data in the form
                    if (!email.isEmpty() && !password.isEmpty()) {
                        // login user
                        checkLogin(email, password);
                    } else {
                        // Prompt user to enter credentials
                        showAlert("Login Error", "Please check your credentials!", R.color.red);
                    }
                }

            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                /**Intent i = new Intent(getApplicationContext(),
                 RegisterActivity.class);
                 startActivity(i); **/
                showAlert("Notice", "Contact admin for account details", R.color.colorPrimary);
            }
        });

    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                pDialog.setMessage("checking db ...");
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        showAlert("Login Error", errorMsg, R.color.red);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    showAlert("Json Error", e.getMessage(), R.color.red);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                showAlert("Login Error", "Check your internet connection", R.color.red);

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("mobile","true");
                return params;
            }

        };

        AppController appController = new AppController( Volley.newRequestQueue(getApplicationContext()));
        appController.addToRequestQueue(strReq, tag_string_req);
        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void showAlert(String Title, String content, int colorInt) {
        Alerter.create(this)
                .setTitle(Title)
                .setText(content)
                .setBackgroundColorRes(colorInt) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }
}

