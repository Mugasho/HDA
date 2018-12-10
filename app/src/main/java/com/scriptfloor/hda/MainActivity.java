package com.scriptfloor.hda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.scriptfloor.hda.adapter.PageAdapter;
import com.scriptfloor.hda.app.AppConfig;
import com.scriptfloor.hda.app.AppController;
import com.scriptfloor.hda.fragment.DrugsFragment;
import com.scriptfloor.hda.fragment.FacilityFragment;
import com.scriptfloor.hda.fragment.VerifyFragment;
import com.scriptfloor.hda.models.DrugModel;
import com.scriptfloor.hda.utils.SQLiteHandler;
import com.scriptfloor.hda.utils.SessionManager;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = com.scriptfloor.hda.MainActivity.class.getSimpleName();
    protected SQLiteHandler db;
    protected SessionManager session;
    protected MaterialSearchBar searchBar;
    protected ViewPager viewPager;
    protected PageAdapter adapter;
    protected TabLayout tabLayout;
    private ProgressDialog pDialog;
    protected DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        drawer = findViewById(R.id.drawer_layout);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());
        if(!session.isLoggedIn()){logoutUser();}
        if (session.isFirstTime()&& session.isLoggedIn()) {
            addData();
        }
        viewPager = findViewById(R.id.viewpager);
        adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //Hide fab if checkup is currently selected page
                Fragment fragment = adapter.getItem(tabLayout.getSelectedTabPosition());
                if (fragment != null) {
                    switch (tab.getPosition()) {
                        case 0:
                            searchBar.setHint("Search Drugs");
                            break;
                        case 1:
                            searchBar.setHint("Verify Batch");
                            break;
                        case 2:
                            searchBar.setHint("Search Facilities");
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchBar = findViewById(R.id.searchBar);
        searchBar.inflateMenu(R.menu.main);
        searchBar.setCardViewElevation(10);

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if(tabLayout.getSelectedTabPosition()==1){
                    Fragment fragment = adapter.getItem(tabLayout.getSelectedTabPosition());
                    if (fragment != null) {

                        ((VerifyFragment) fragment).Search(searchBar.getText(), getApplicationContext());
                    }
                }

            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode) {
                    case MaterialSearchBar.BUTTON_NAVIGATION:
                        drawer.openDrawer(Gravity.LEFT);
                        break;
                    case MaterialSearchBar.BUTTON_SPEECH:
                        Toast.makeText(MainActivity.this, "Speech ", Toast.LENGTH_SHORT).show();

                        break;
                    case MaterialSearchBar.BUTTON_BACK:
                        searchBar.disableSearch();
                        break;
                }
            }
        });
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String searchText = searchBar.getText();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Fragment fragment = adapter.getItem(tabLayout.getSelectedTabPosition());
                if (fragment != null) {
                    switch (tabLayout.getSelectedTabPosition()) {
                        case 0:
                            ((DrugsFragment) fragment).Search(searchBar.getText(), getApplicationContext());
                            break;
                        case 1:
                            //((VerifyFragment) fragment).Search(searchBar.getText(), getApplicationContext());
                            break;
                        case 2:
                            ((FacilityFragment) fragment).Search(searchBar.getText(), getApplicationContext());

                            break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        // Configure the search info and add any event listeners...
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            logoutUser();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_call) {
            dialPhone("100");
        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from  users table
     */

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showAlert(String Title, String content, int colorInt) {
        Alerter.create(this)
                .setTitle(Title)
                .setText(content)
                .setBackgroundColorRes(colorInt) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }

    private void dialPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void addData() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Loading Data ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DRUGS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                db.createDrugTable();
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        DrugModel drugModel = new DrugModel();
                        drugModel.setDrugBrand(json_data.getString("name_of_drug"));
                        drugModel.setDrugGeneric(json_data.getString("generic_name_of_drug"));
                        drugModel.setDrugClass(json_data.getString("strength_of_drug"));
                        drugModel.setDrugCompany(json_data.getString("local_technical_representative"));
                        drugModel.setDrugCountry(json_data.getString("country_of_manufacture"));
                        drugModel.setDrugType(json_data.getString("pack_size"));
                        db.addDrug(drugModel.getDrugBrand(), drugModel.getDrugGeneric(), drugModel.getDrugClass(),
                                drugModel.getDrugCompany(), drugModel.getDrugCountry(), drugModel.getDrugType(), null);
                    }
                    session.setFirstTime(false);
                    pDialog.setMessage("Loading completed");
                    finish();
                    startActivity(getIntent());
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    showAlert("Json Error", e.getMessage(), R.color.red);
                }
                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                showAlert("Failed to Update", "Check your internet connection", R.color.red);
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("table", "drugs");
                params.put("data", "all");

                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        AppController appController = new AppController(mRequestQueue);
        // Adding request to request queue
        appController.addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
