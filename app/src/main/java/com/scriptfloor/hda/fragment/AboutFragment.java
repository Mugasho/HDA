package com.scriptfloor.hda.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scriptfloor.hda.R;
import com.scriptfloor.hda.adapter.ParentChildAdapter;
import com.scriptfloor.hda.models.ChildDataItem;
import com.scriptfloor.hda.models.DrugModel;
import com.scriptfloor.hda.models.ParentDataItem;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    public static ArrayList<ParentDataItem> aboutList;
    public  ParentChildAdapter mAdapter;
    public static SQLiteHandler dbHandler;
    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        mRecyclerView = view.findViewById(R.id.about_list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new ParentChildAdapter(getDataToPass(),getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
    private ArrayList<ParentDataItem> getDataToPass() {
        ArrayList<ParentDataItem> DataItems = new ArrayList<>();
        ArrayList<ChildDataItem> ChildDataItems;
        ParentDataItem ParentDataItem;
        ChildDataItem ChildDataItem;
        /////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Detailed address");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Namugongo road kampala uganda ");
        ChildDataItems.add(ChildDataItem);
        //
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Vision and mission of the facility");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Vision:");
        ChildDataItem.setChildDetail("To be a leading health care provider");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Mission:");
        ChildDataItem.setChildDetail("Excelling in service delivery in uganda");
        ChildDataItems.add(ChildDataItem);
        //
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Services Offered");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("General medicine,Consultations ");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Pharmacy:");
        ChildDataItem.setChildDetail("24 hour services for in and Out patients");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Contact:");
        ChildDataItem.setChildDetail("077567788990");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Laboratory:");
        ChildDataItem.setChildDetail("Open: 7;30 am to 10:00 pm \nContact: 077567788688" );
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Radiography :");
        ChildDataItem.setChildDetail("Open: 7:30 to 10:00pm\n" +
                " X-ray,Ultrasound,MRI,CT – Scan, Endoscopy\n" +
                "Contact: 0774664746899\n");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Antenatal, Postnatal,Immunization, Family planning\n" +
                "All maternity services are free every Thursday and Friday 8:00 am to 5:00 pm\n");
        ChildDataItems.add(ChildDataItem);
        //
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Other Clinics");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Obstetrics and Gynecology");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Pediatrics");
        ChildDataItems.add(ChildDataItem);
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Diabetes");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        return DataItems;
    }
}
