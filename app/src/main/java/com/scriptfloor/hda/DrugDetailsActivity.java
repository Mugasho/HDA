package com.scriptfloor.hda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scriptfloor.hda.adapter.ParentChildAdapter;
import com.scriptfloor.hda.models.ChildDataItem;
import com.scriptfloor.hda.models.ParentDataItem;

import java.util.ArrayList;

public class DrugDetailsActivity extends AppCompatActivity {
    int position = 0;
    String brand, generic, drug_class, drug_company;
    TextView genericName, drugClass, drugCompany;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        brand = intent.getStringExtra("brand");
        generic = intent.getStringExtra("generic");
        drug_class = intent.getStringExtra("class");
        drug_company = intent.getStringExtra("company");
        position = intent.getIntExtra("position", 0);
        genericName = findViewById(R.id.drugGeneric);
        genericName.setText(generic);
        drugClass = findViewById(R.id.drug_class);
        drugClass.setText(drug_class);
        drugCompany = findViewById(R.id.drug_company);
        drugCompany.setText(drug_company);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startshare();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(brand);

        ParentChildAdapter mAdapter;
        mRecyclerView = findViewById(R.id.drugsList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new ParentChildAdapter(getDataToPass(),this);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void startshare() {
        String fullList = "*" + getString(R.string.drug) + " :* " + brand
                + " *" + getString(R.string.generic_name) + ":* " + generic
                + " *" + getString(R.string.drug_company) + ":* " + drug_company
                + " https://hdaproject.org";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, fullList);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share) + " " + getString(R.string.drug)));
    }

    private ArrayList<ParentDataItem> getDataToPass() {
        ArrayList<ParentDataItem> DataItems = new ArrayList<>();
        ArrayList<ChildDataItem> ChildDataItems;
        ParentDataItem ParentDataItem;
        ChildDataItem ChildDataItem;
        /////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Medicine Description");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Capsule with a blue colored cap and white colored body printed with 150mg and Z09 containing a white to off white powder. ");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Dose");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Use as directed by the prescriber");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Dose & Frequency");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Once daily or as directed by the prescriber ");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Pharmacology");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Antifungal agent, which acts by damaging the cell membrane, producing alterations in cell membrane function and permeability");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Administration");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Swallow the whole capsule with water ");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Contraindications");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Hypersensitivity to fluconazole and other antifungal agents or any of constituents; renal impairment. Consult your prescriber");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Interactions");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Astemizole, Cisapride, erythromycin, indapamide, quinidine, terfenadine," +
                " amiodarone, theophylline, phenytoin, Sulphonyl-urea (anti-diabetes) amitriptyline, artemether lumefantrine, chlorpromazine, clarithromycin, clomipramine, warfarin, zidovudine");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Pregnancy & Breastfeeding");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("There is an increased possible risk of miscarriage. Antifungal products should be avoided in pregnancy except " +
                "in patients with severe life threatening conditions. It is secreted in milk hence breast feeding mothers should use it with caution");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Side effects");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Mainly associated with overdose: Lack of sleep, Irritability, Vomiting, Diarrhea," +
                " Abdominal pains/cramps, Lack of appetite, Bulging fontanel, depressed mood, Numbness of the tongue, itching, arthralgia, fatigue");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Precautions during use");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Should be cautiously used in patients with the above symptoms. Medicine may worsen them.");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Storage");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("Store below 250C. Protect from light. Keep the blisters in carton until required for use." +
                " Keep out of reach of children.");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);

        ////////
        ParentDataItem = new ParentDataItem();
        ParentDataItem.setParentName("Pharmacists Advice");
        ChildDataItems = new ArrayList<>();
        //
        ChildDataItem = new ChildDataItem();
        ChildDataItem.setChildDetail("As relates to AMR, Self-medication, under/incomplete dosing");
        ChildDataItems.add(ChildDataItem);
        ParentDataItem.setChildDataItems(ChildDataItems);
        DataItems.add(ParentDataItem);
        return DataItems;
    }
}

