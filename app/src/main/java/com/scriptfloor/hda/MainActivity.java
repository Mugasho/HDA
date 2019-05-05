package com.scriptfloor.hda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.scriptfloor.hda.adapter.NewsAdapter;
import com.scriptfloor.hda.adapter.PageAdapter;
import com.scriptfloor.hda.app.AppConfig;
import com.scriptfloor.hda.app.AppController;
import com.scriptfloor.hda.fragment.DrugsFragment;
import com.scriptfloor.hda.fragment.FacilityFragment;
import com.scriptfloor.hda.fragment.VerifyFragment;
import com.scriptfloor.hda.models.DrugModel;
import com.scriptfloor.hda.models.FacilityModel;
import com.scriptfloor.hda.models.NewsModel;
import com.scriptfloor.hda.utils.SQLiteHandler;
import com.scriptfloor.hda.utils.SessionManager;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = com.scriptfloor.hda.MainActivity.class.getSimpleName();
    protected SQLiteHandler db;
    protected SessionManager session;
    protected MaterialSearchBar searchBar;
    protected ViewPager viewPager;
    protected PageAdapter adapter;
    protected NewsAdapter newsAdapter;
    List<NewsModel> newsModelList;
    protected TabLayout tabLayout;
    private ProgressDialog pDialog;
    protected DrawerLayout drawer;
    protected RecyclerView recyclerNews;
    protected LinearLayout helpLayout, newsLayout;
    protected LinearLayoutManager linearLayoutManager;
    protected CardView helpHw, helpFacility, moreHelpHw, moreHelpFacility;
    protected Button btnHelpHw, btnHelpFacility;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        recyclerNews = findViewById(R.id.recycler_news);
        newsLayout = findViewById(R.id.new_layout);
        drawer = findViewById(R.id.drawer_layout);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        viewPager = findViewById(R.id.viewpager);
        helpLayout = findViewById(R.id.help_layout);
        adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        newsModelList = db.getPosts();
        newsAdapter = new NewsAdapter(getApplicationContext(), newsModelList);
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

        searchBar = findViewById(R.id.searchBar);
        searchBar.inflateMenu(R.menu.main);
        searchBar.setCardViewElevation(10);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if (tabLayout.getSelectedTabPosition() == 1) {
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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        helpHw = findViewById(R.id.help_hw);

        moreHelpHw = findViewById(R.id.more_help_hw);
        helpFacility = findViewById(R.id.help_facility);
        moreHelpFacility = findViewById(R.id.more_help_facility);
        btnHelpFacility = findViewById(R.id.btn_feedback_facility);
        btnHelpHw = findViewById(R.id.btn_feedback_hw);


        helpFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreHelpFacility.getVisibility() == View.GONE) {
                    moreHelpFacility.setVisibility(View.VISIBLE);
                } else {
                    moreHelpFacility.setVisibility(View.GONE);
                }
            }
        });

        helpHw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreHelpHw.getVisibility() == View.GONE) {
                    moreHelpHw.setVisibility(View.VISIBLE);
                } else {
                    moreHelpHw.setVisibility(View.GONE);
                }
            }
        });

        btnHelpHw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                intent.putExtra("title", "Feedback about Health worker");
                intent.putExtra("reason", "reason");
                startActivity(intent);
            }
        });

        btnHelpFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                intent.putExtra("title", "Feedback about facility");
                intent.putExtra("reason", "reason");
                startActivity(intent);
            }
        });


        // Session manager
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        if (session.isFirstTime() && session.isLoggedIn()) {
            addDrugs();
            addFacilities();
        }

        newsModelList = db.getPosts();
        if (newsModelList.size() > 0) {
            newsAdapter = new NewsAdapter(this, newsModelList);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerNews.setLayoutManager(linearLayoutManager);
            recyclerNews.setAdapter(newsAdapter);
        } else {
            getNews();
        }

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
            }
        });
        swipeContainer.setNestedScrollingEnabled(true);


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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    helpLayout.setVisibility(View.GONE);
                    newsLayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    helpLayout.setVisibility(View.GONE);
                    newsLayout.setVisibility(View.VISIBLE);
                    searchBar.setHint("Search News");
                    return true;
                case R.id.navigation_notifications:
                    helpLayout.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    newsLayout.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

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

    private void addDrugs() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Loading Drugs ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_DRUGS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Drugs: " + response);
                db.createDrugTable();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.d("here", "error");
                    // Check for error node in json
                    if (!error) {
                        JSONArray jArray = jObj.optJSONArray("drugs");
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
                    }
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
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        AppController appController = new AppController(mRequestQueue);
        // Adding request to request queue
        appController.addToRequestQueue(strReq, tag_string_req);
    }

    private void addFacilities() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Loading Facilities ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_FACILITIES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Facility: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        db.deleteFacilities();
                        JSONArray jArray = jObj.optJSONArray("facility");
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            FacilityModel facility=new FacilityModel();
                            facility.setFacilityID(json_data.getString("id"));
                            facility.setFacilityName(json_data.getString("facility"));
                            facility.setFacilityAddress(json_data.getString("address"));
                            facility.setFacilitySector(json_data.getString("sector"));
                            facility.setFacilityCategory(json_data.getString("category"));
                            facility.setFacilityLicense(json_data.getString("license"));
                            facility.setContact(json_data.getString("contact"));
                            facility.setPhone(json_data.getString("phone"));
                            facility.setEmail(json_data.getString("email"));
                            facility.setQualification(json_data.getString("qualification"));
                            facility.setFacilityLocation(json_data.getString("location"));
                            facility.setCreatedAt(json_data.getString("created_at"));
                            db.addFacility(facility);
                        }
                        Log.d("HDA","Facility loaded");
                        session.setFirstTime(false);
                    }
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
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        AppController appController = new AppController(mRequestQueue);
        // Adding request to request queue
        appController.addToRequestQueue(strReq, tag_string_req);
    }
    private void getNews() {
        String tag_string_req = "req_news";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_NEWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "News Response: " + response.toString());
                /*pDialog.setMessage("checking db ...");
                hideDialog();*/
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.d("here", "error");
                    // Check for error node in json
                    if (!error) {
                        db.deletePosts();
                        newsAdapter.clear();
                        newsModelList.clear();
                        JSONArray jArray = jObj.optJSONArray("posts");
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            NewsModel post = new NewsModel();
                            post.setNewsID(json_data.getInt("id"));
                            post.setNewsTitle(json_data.getString("title"));
                            post.setNewsContent(json_data.getString("content"));
                            post.setNewsAuthor(json_data.getString("author"));
                            post.setNewsStatus(json_data.getInt("status"));
                            post.setNewsPic(json_data.getString("blog_pic"));
                            post.setNewsCategory(json_data.getString("category"));
                            post.setDateAdded(json_data.getString("date_added"));


                            db.addPost(post.getNewsID(), post.getNewsTitle(), post.getNewsContent()
                                    , post.getNewsAuthor(), post.getNewsStatus(), post.getNewsPic(), post.getNewsCategory(), post.getDateAdded());
                        }
                        newsModelList = db.getPosts();
                        for (int i = 0; i < newsModelList.size(); i++) {

                        }
                        if (newsModelList != null) {
                            newsAdapter.addAll(newsModelList);
                            newsAdapter.notifyDataSetChanged();
                        }
                        swipeContainer.setRefreshing(false);
                    } else {
                        // Error in Fetching posts.
                        String errorMsg = jObj.getString("error_msg");
                        showAlert("News", errorMsg, R.color.red);
                        swipeContainer.setRefreshing(false);

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    showAlert("News Error", e.getMessage(), R.color.red);
                    swipeContainer.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                showAlert("Posts Error", error.getMessage(), R.color.red);
                swipeContainer.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                //params.put("limit", "");
                return params;
            }

        };

        AppController appController = new AppController(Volley.newRequestQueue(getApplicationContext()));
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
