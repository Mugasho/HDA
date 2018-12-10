package com.scriptfloor.hda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DrugDetailsActivity extends AppCompatActivity {
    int position = 0;
    String brand, generic, drug_class, drug_company;
    TextView genericName, drugClass, drugCompany;


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
}
