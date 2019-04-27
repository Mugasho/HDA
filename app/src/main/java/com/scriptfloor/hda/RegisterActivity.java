package com.scriptfloor.hda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A Register screen that offers Register via email/password.
 */
public class RegisterActivity extends AppCompatActivity{

    private static final String TAG = com.scriptfloor.hda.RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private EditText inputEmail,inputUserName,inputPhone;
    private EditText inputPassword,inputRepeatPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputUserName = findViewById(R.id.username);
        inputPhone = findViewById(R.id.phone);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputRepeatPassword = findViewById(R.id.repeat_password);
        btnRegister = findViewById(R.id.btn_register);

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
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register button Click Event
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String UserName = inputUserName.getText().toString().trim();
                String phone = inputPhone.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString();
                String RepeatPassword = inputRepeatPassword.getText().toString();
                //check whether email is valid
                if (!Sentinel.isValid(email)) {
                    showAlert("Registration  Error", "Please check your email", R.color.red);
                }else if(!password.equals(RepeatPassword)) {
                    showAlert("Registration Error", "Passwords do not match", R.color.red);

                }else{
                    // Check for empty data in the form
                    if (!email.isEmpty() && !password.isEmpty()) {
                        // Register user
                        registerUser(UserName,phone,email, password);
                    } else {
                        // Prompt user to enter credentials
                        showAlert("Register Error", "Please check your credentials!", R.color.red);
                    }
                }

            }

        });


    }

    /**
     * function to verify Register details in mysql db
     */
    private void registerUser(final String UserName,final String phone,
                              final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_Register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Registration Response: " + response.toString());
                pDialog.setMessage("checking user ...");
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully registered
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
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } else {
                        // Error in Register. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        showAlert("Register Error", errorMsg, R.color.red);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    showAlert("Register Error", e.getMessage(), R.color.red);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Register Error: " + error.getMessage());
                showAlert("Register Error", "Check your internet connection", R.color.red);

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to Register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", UserName);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);
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

