package com.scriptfloor.hda.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gturedi.views.StatefulLayout;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.app.AppConfig;
import com.scriptfloor.hda.app.AppController;
import com.scriptfloor.hda.models.FacilityModel;
import com.scriptfloor.hda.adapter.FacilityAdapter;
import com.scriptfloor.hda.utils.SQLiteHandler;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FacilityFragment extends Fragment {

    public static List<FacilityModel> facilityList;
    public static FacilityAdapter mAdapter;
    public static SQLiteHandler dbHandler;
    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static StatefulLayout stateful;
    private SwipeRefreshLayout swipeContainer;

    public FacilityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facility, container, false);
        mRecyclerView = view.findViewById(R.id.facilityList);
        swipeContainer = view.findViewById(R.id.swipeFacility);
        stateful = view.findViewById(R.id.stateful);
        stateful.showLoading();
        dbHandler = new SQLiteHandler(getActivity());
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        facilityList = new ArrayList<>();
        facilityList = dbHandler.getFacilities("");
        if (facilityList.size() > 0) {
            mAdapter = new FacilityAdapter(getActivity(), facilityList);
            mRecyclerView.setAdapter(mAdapter);
            stateful.showContent();
        }else{
            addFacilities();
        }
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addFacilities();
            }
        });
        swipeContainer.setNestedScrollingEnabled(true);
        return view;
    }

    public void Search(String searchString, Context context) {
        stateful.showLoading();
        Log.d("Str", searchString);
        facilityList = dbHandler.getFacilities(searchString);
        mAdapter = new FacilityAdapter(context, facilityList);
        mAdapter.notifyDataSetChanged();
        if (facilityList.size() > 0) {
            stateful.showContent();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            stateful.showEmpty("No facilities match your search");
        }

    }

    private void addFacilities() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        stateful.showLoading();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_FACILITIES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        dbHandler.deleteFacilities();
                        facilityList.clear();
                        JSONArray jArray = jObj.optJSONArray("facility");
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            FacilityModel facility = new FacilityModel();
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
                            dbHandler.addFacility(facility);
                        }
                        facilityList = dbHandler.getFacilities("");
                        for (int i = 0; i < facilityList.size(); i++) {

                        }
                        if (facilityList != null) {
                            mAdapter.addAll(facilityList);
                            mAdapter.notifyDataSetChanged();
                        }
                        Log.d("HDA", "Facility loaded");

                    }
                    swipeContainer.setRefreshing(false);
                    stateful.showContent();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    showAlert("Json Error", e.getMessage(), R.color.red);
                    swipeContainer.setRefreshing(false);
                    stateful.showContent();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HDA", "Error: " + error.getMessage());
                showAlert("Failed to Update", "Check your internet connection", R.color.red);
                swipeContainer.setRefreshing(false);
                stateful.showContent();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        AppController appController = new AppController(mRequestQueue);
        // Adding request to request queue
        appController.addToRequestQueue(strReq, tag_string_req);
    }

    private void showAlert(String Title, String content, int colorInt) {
        Alerter.create(getActivity())
                .setTitle(Title)
                .setText(content)
                .setBackgroundColorRes(colorInt) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }
}
