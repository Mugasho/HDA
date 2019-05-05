package com.scriptfloor.hda;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.scriptfloor.hda.adapter.FacilityPageAdapter;
import com.scriptfloor.hda.adapter.ParentChildAdapter;
import com.scriptfloor.hda.fragment.RegistrationFragment;
import com.scriptfloor.hda.fragment.VerifyFragment;
import com.scriptfloor.hda.models.ChildDataItem;
import com.scriptfloor.hda.models.FacilityModel;
import com.scriptfloor.hda.models.ParentDataItem;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.util.ArrayList;

public class FacilityActivity extends AppCompatActivity {
String facility;
    TabLayout tabLayout;
    ViewPager viewPager;
    SQLiteHandler db;
    RecyclerView reg_details,about,hw_list;
    LinearLayout lay_reg,lay_about,lay_hw;
    protected FacilityPageAdapter adapter;
    public ParentChildAdapter mAdapter,aboutAdapter;
    RecyclerView.LayoutManager mLayoutManager,RLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);
        db=new SQLiteHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        facility=intent.getStringExtra("facility");
        final int id=Integer.parseInt(intent.getStringExtra("id"));
        Log.d("Facility",id+"");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(facility);
        tabLayout = findViewById(R.id.facility_tabs);
        viewPager = findViewById(R.id.facility_viewpager);
        lay_about=findViewById(R.id.lay_about);
        lay_reg=findViewById(R.id.lay_registration);
        lay_hw=findViewById(R.id.lay_hw);
        /*adapter = new FacilityPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);*/

        reg_details = findViewById(R.id.hw_detail_list);
        about=findViewById(R.id.about_list);
        reg_details.setHasFixedSize(true);
        about.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RLayoutManager = new LinearLayoutManager(getApplicationContext());
        reg_details.setLayoutManager(mLayoutManager);
        mAdapter = new ParentChildAdapter(getDataToPass(id), getApplicationContext());
        reg_details.setAdapter(mAdapter);

        /////
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*viewPager.setCurrentItem(tab.getPosition());
                Fragment fragment = adapter.getItem(tabLayout.getSelectedTabPosition());
                if (fragment != null) {

                }*/
                switch (tab.getPosition()) {
                    case 0:
                        lay_reg.setVisibility(View.VISIBLE);
                        lay_about.setVisibility(View.GONE);
                        lay_hw.setVisibility(View.GONE);
                        break;
                    case 1:
                        lay_reg.setVisibility(View.GONE);
                        lay_about.setVisibility(View.VISIBLE);
                        lay_hw.setVisibility(View.GONE);
                        about.setLayoutManager(RLayoutManager);
                        aboutAdapter = new ParentChildAdapter(getAboutData(id), getApplicationContext());
                        about.setAdapter(aboutAdapter);
                        break;
                    case 2:
                        lay_reg.setVisibility(View.GONE);
                        lay_about.setVisibility(View.GONE);
                        lay_hw.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private ArrayList<ParentDataItem> getDataToPass(int id) {

        FacilityModel facility=db.getFacilityByID(id);
        ArrayList<ParentDataItem> DataItems = new ArrayList<>();
        ArrayList<ChildDataItem> ChildDataItems;
        ParentDataItem ParentDataItem;
        ChildDataItem ChildDataItem;
        ////heading////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Registration Details");
        ParentDataItem.setItemImage(R.drawable.ic_group_add);
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildName("Facility Name ");
        ChildDataItem.setChildDetail(facility.getFacilityName());
        ChildDataItems.add(ChildDataItem);

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

        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////heading////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Address");
        ParentDataItem.setItemImage(R.drawable.ic_near_me);
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail(facility.getFacilityLocation());
        ChildDataItems.add(ChildDataItem);

        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        return DataItems;
    }

    private ArrayList<ParentDataItem> getAboutData(int id) {
        FacilityModel facility=db.getFacilityByID(id);
        ArrayList<ParentDataItem> DataItems = new ArrayList<>();
        ArrayList<ChildDataItem> ChildDataItems;
        ParentDataItem ParentDataItem;
        ChildDataItem ChildDataItem;
        /////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Detailed address");
        ParentDataItem.setItemImage(R.drawable.ic_place);
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail(facility.getFacilityLocation());
        ChildDataItems.add(ChildDataItem);
        //
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setItemImage(R.drawable.ic_library_books_black_24dp);
        ParentDataItem.setParentName("Vision and mission");
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
        ParentDataItem.setItemImage(R.drawable.ic_local_pharmacy);
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
        ParentDataItem.setItemImage(R.drawable.ic_more_black_24dp);
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
