package com.scriptfloor.hda.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gturedi.views.StatefulLayout;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.models.FacilityModel;
import com.scriptfloor.hda.adapter.FacilityAdapter;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FacilityFragment extends Fragment {

    public static List<FacilityModel> facilityList;
    public static RecyclerView.Adapter mAdapter;
    public static SQLiteHandler dbHandler;
    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static StatefulLayout stateful;

    public FacilityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facility, container, false);
        mRecyclerView = view.findViewById(R.id.facilityList);
        stateful = view.findViewById(R.id.stateful);
        stateful.showLoading();
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            dbHandler = new SQLiteHandler(getActivity());
            facilityList = new ArrayList<>();
            facilityList = dbHandler.getFacilities("");
            if(facilityList.size()<1){
                dbHandler.addFacility("ab medical centre","mukono","private","clinic","");
                dbHandler.addFacility("abaita medical centre","kampala","private","clinic","");
                dbHandler.addFacility("abedober health centre ","gulu","private","hospital","");
                dbHandler.addFacility("aboke mission health centre ii ","lira","pnfp","clinic","");
                dbHandler.addFacility("mulago hospital","kampala","govt","hospital","");
                dbHandler.addFacility("mbarara hospital","mbarara","govt","hospital","");
                dbHandler.addFacility("adjumani mission health centre iii ","adjumani","pnfp","hospital","");
                dbHandler.addFacility("mubende hospital","mubende","govt","hospital","");
                dbHandler.addFacility("32 adwoki maternity home ","gulu","private","clinic","");
                dbHandler.addFacility("adcare medical centre ","masaka","private","clinic","");
                dbHandler.addFacility("nyakibale hospital","mukono","private","clinic","");
                dbHandler.addFacility("abedober health centre ","mukono","private","clinic","");
                dbHandler.addFacility("kisiizi hospital","mukono","private","clinic","");
                dbHandler.addFacility("gayaza diagnostic centre ","gayaza","private","clinic","");
                dbHandler.addFacility("489 giant medical clinic ","mukono","pnfp","clinic","");
            }
            // specify an adapter
            mAdapter = new FacilityAdapter(getActivity(), facilityList);
            mRecyclerView.setAdapter(mAdapter);
            stateful.showContent();
        } catch (NumberFormatException e) {
            Snackbar.make(view, "No Previous Records", Snackbar.LENGTH_LONG)
                    .setAction("Cancel", null).show();
        }
        return view;
    }

    public void Search(String searchString,Context context) {
        stateful.showLoading();
        Log.d("Str", searchString);
        facilityList = dbHandler.getFacilities(searchString);
        mAdapter = new FacilityAdapter(context, facilityList);
        mAdapter.notifyDataSetChanged();
        if(facilityList.size()>0){
            stateful.showContent();
            mRecyclerView.setAdapter(mAdapter);
        }else{
            stateful.showEmpty("No facilities match your search");
        }

    }
}
