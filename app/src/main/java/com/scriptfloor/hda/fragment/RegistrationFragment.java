package com.scriptfloor.hda.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scriptfloor.hda.R;
import com.scriptfloor.hda.adapter.ParentChildAdapter;
import com.scriptfloor.hda.models.ChildDataItem;
import com.scriptfloor.hda.models.FacilityModel;
import com.scriptfloor.hda.models.ParentDataItem;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    public ParentChildAdapter mAdapter;
    public static SQLiteHandler db;
    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        mRecyclerView = view.findViewById(R.id.hw_detail_list);
        db = new SQLiteHandler(getActivity());
        mRecyclerView.setHasFixedSize(true);
        int id = Integer.parseInt(getArguments().getString("id"));
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
       mAdapter = new ParentChildAdapter(getDataToPass(id), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private ArrayList<ParentDataItem> getDataToPass(int id) {
        FacilityModel facility=db.getFacilityByID(id);
        ArrayList<ParentDataItem> DataItems = new ArrayList<>();
        ArrayList<ChildDataItem> ChildDataItems;
        ParentDataItem ParentDataItem;
        ChildDataItem ChildDataItem;
        /////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Registration Details");
        ParentDataItem.setItemImage(R.drawable.ic_group_add);
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Facility Name ");
        ChildDataItem.setChildDetail(facility.getFacilityName());
        ChildDataItems.add(ChildDataItem);
        //
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Address");
        ParentDataItem.setItemImage(R.drawable.ic_near_me);
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail(facility.getFacilityAddress());
        ChildDataItems.add(ChildDataItem);
        //
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Licensing");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("License No:");
        ChildDataItem.setChildDetail(facility.getFacilityLicense());
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("License Status:");
        ChildDataItem.setChildDetail("Active");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Registration No:");
        ChildDataItem.setChildDetail(facility.getFacilityLicense());
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Year of Registration:");
        ChildDataItem.setChildDetail(facility.getCreatedAt());
        ChildDataItems.add(ChildDataItem);
        //
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Contacts");
        ParentDataItem.setItemImage(R.drawable.ic_local_phone_black_24dp);
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Registered practitioner:");
        ChildDataItem.setChildDetail(facility.getContact());
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Qualification:");
        ChildDataItem.setChildDetail(facility.getQualification());
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Contact:");
        ChildDataItem.setChildDetail(facility.getContact());
        ChildDataItems.add(ChildDataItem);

        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Location:");
        ChildDataItem.setChildDetail(facility.getFacilityLocation());
        ChildDataItems.add(ChildDataItem);

        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        return DataItems;
    }

public void getData(int id){
    ArrayList<ParentDataItem> data=getDataToPass(id);
    mAdapter = new ParentChildAdapter(data, getActivity());
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.notifyDataSetChanged();
}

    public void setArguments(Intent intent) {
    }
}
