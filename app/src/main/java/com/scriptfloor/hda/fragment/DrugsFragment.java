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
import com.scriptfloor.hda.adapter.DrugAdapter;
import com.scriptfloor.hda.models.DrugModel;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrugsFragment extends Fragment {
    public static List<DrugModel> DrugList;
    public static RecyclerView.Adapter mAdapter;
    public static SQLiteHandler dbHandler;
    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static StatefulLayout stateful;

    public DrugsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drugs, container, false);
        mRecyclerView = view.findViewById(R.id.drugList);
        stateful = view.findViewById(R.id.stateful);
        stateful.showLoading();
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            dbHandler = new SQLiteHandler(getActivity());
            DrugList = new ArrayList<>();
            DrugList = dbHandler.getDrugList("");
            mAdapter = new DrugAdapter(getActivity(), DrugList);
            mAdapter.notifyDataSetChanged();
            if (DrugList.size() > 0) {
                stateful.showContent();
                mRecyclerView.setAdapter(mAdapter);
            } else {
                stateful.showEmpty("No drugs to show");
            }
        } catch (NumberFormatException e) {
            Snackbar.make(view, "No Previous Records", Snackbar.LENGTH_LONG)
                    .setAction("Cancel", null).show();
        }
        return view;
    }

    public void Search(String searchString, Context context) {
        stateful.showLoading();
        DrugList = dbHandler.getDrugList(searchString);
        Log.d("Str", searchString);
        mAdapter = new DrugAdapter(context, DrugList);
        mAdapter.notifyDataSetChanged();
        if (DrugList.size() > 0) {
            stateful.showContent();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            stateful.showEmpty("No drugs match your search");
        }


    }


}

