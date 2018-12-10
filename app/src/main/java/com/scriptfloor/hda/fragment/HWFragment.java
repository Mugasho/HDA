package com.scriptfloor.hda.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gturedi.views.StatefulLayout;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.adapter.FacilityAdapter;
import com.scriptfloor.hda.adapter.HwAdapter;
import com.scriptfloor.hda.models.HwModel;

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
        HwList = new ArrayList<>();
        HwModel hWorker= new HwModel();
        hWorker.setID("1");
        hWorker.setNames("John Doe");
        hWorker.setTitle("Pharmacist");
        hWorker.setQualification("Bachelor Pharmacist");
        hWorker.setRegNo("UPS100023");
        hWorker.setRegDate("08-08-2013");
        hWorker.setCouncil("Pharmacuetical council");
        hWorker.setLicenceStatus("Active");
        HwList.add(hWorker);
        HwModel hWorker2= new HwModel();
        hWorker2.setID("2");
        hWorker2.setNames("kimanzi jacob");
        hWorker2.setTitle("Doctors");
        hWorker2.setRegNo("VR5576");
        hWorker2.setRegDate("09-07-1998");
        hWorker2.setQualification("Bachelor of Medicine and Surgery ");
        hWorker2.setCouncil("Uganda Medical and Dental practitioners council");
        hWorker2.setLicenceStatus("Active");
        HwList.add(hWorker2);
        HwModel hWorker3= new HwModel();
        hWorker3.setID("3");
        hWorker3.setNames("Kasadha Godfrey");
        hWorker3.setTitle("Clinical Officer");
        hWorker3.setRegNo("AH10050");
        hWorker3.setRegDate("12-07-2015");
        hWorker3.setQualification("Diploma in clinical Medicine ");
        hWorker3.setCouncil("Allied Health Professionals  council");
        hWorker3.setLicenceStatus("Active");
        HwList.add(hWorker3);
        HwModel hWorker4= new HwModel();
        hWorker4.setID("4");
        hWorker4.setNames("Akello Marry");
        hWorker4.setTitle("Enrolled Nurse");
        hWorker4.setQualification("Diploma in Nursing ");
        hWorker4.setRegNo("NC135013");
        hWorker4.setRegDate("07-08-2005");
        hWorker4.setCouncil("Uganda Nursing and midwifery council");
        hWorker4.setLicenceStatus("Active");
        HwList.add(hWorker4);
        HwModel hWorker5= new HwModel();
        hWorker5.setID("5");
        hWorker5.setNames("Namaganda safina");
        hWorker5.setTitle("Lab Technologist");
        hWorker5.setRegNo("LT9005");
        hWorker5.setRegDate("09-08-2000");
        hWorker5.setQualification("Diploma in Medical Laboratory");
        hWorker5.setCouncil("Allied Health Professionals  council");
        hWorker5.setLicenceStatus("inactive");
        HwList.add(hWorker5);
        mAdapter = new HwAdapter(getActivity(),HwList);
        mRecyclerView.setAdapter(mAdapter);
        stateful.showContent();

        return view;
    }

}
