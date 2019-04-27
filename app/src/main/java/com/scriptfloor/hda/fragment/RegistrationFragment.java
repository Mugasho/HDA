package com.scriptfloor.hda.fragment;


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
import com.scriptfloor.hda.models.ParentDataItem;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    public ParentChildAdapter mAdapter;
    public static SQLiteHandler dbHandler;
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
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ParentChildAdapter(getDataToPass(), getActivity());
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
        ParentDataItem.setParentName("Registration Details");
        ParentDataItem.setItemImage(R.drawable.ic_group_add);
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Facility Name ");
        ChildDataItem.setChildDetail("Abayita Medical Center ");
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
        ChildDataItem.setChildDetail("Plot 27 Bukoto hill Road");
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
        ChildDataItem.setChildDetail("425665 ");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("License Status:");
        ChildDataItem.setChildDetail("Active");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Registration No:");
        ChildDataItem.setChildDetail("UG5673B");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Year of Registration:");
        ChildDataItem.setChildDetail("2015");
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
        ChildDataItem.setChildDetail("Okello John ");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Qualification:");
        ChildDataItem.setChildDetail("Clinical Officer");
        ChildDataItems.add(ChildDataItem);
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Contact:");
        ChildDataItem.setChildDetail("+25678254343");
        ChildDataItems.add(ChildDataItem);

        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Location:");
        ChildDataItem.setChildDetail("http://google.com");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        return DataItems;
    }
}
