package com.scriptfloor.hda.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gturedi.views.StatefulLayout;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.adapter.DrugAdapter;
import com.scriptfloor.hda.adapter.FacilityAdapter;
import com.scriptfloor.hda.adapter.HwAdapter;
import com.scriptfloor.hda.models.HwModel;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HWFragment extends Fragment {

    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter mAdapter;
    public static StatefulLayout stateful;
    public static SQLiteHandler dbHandler;
    public static List<HwModel> HwList;

    public HWFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hw, container, false);
        mRecyclerView = view.findViewById(R.id.hw_list);
        stateful = view.findViewById(R.id.hw_stateful);
        stateful.showLoading();
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            dbHandler = new SQLiteHandler(getActivity());
            HwList = new ArrayList<>();
            HwModel hw=new HwModel();
            hw.setSurName("bob");
            hw.setOtherNames("john");
            hw.setQualification("Doctor");
            dbHandler.addHw(hw);
            HwList = dbHandler.getHealthWorkers(null,null,null);
            if (HwList!=null) {
                mAdapter = new HwAdapter(getActivity(), HwList);
                mAdapter.notifyDataSetChanged();
                stateful.showContent();
                mRecyclerView.setAdapter(mAdapter);
            } else {
                stateful.showEmpty("No Health workers to show");
            }
        } catch (NumberFormatException e) {
            Snackbar.make(view, "No Previous Records", Snackbar.LENGTH_LONG)
                    .setAction("Cancel", null).show();
        }

        return view;
    }

}
