package com.scriptfloor.hda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class HwProfileActivity extends AppCompatActivity {

    TextView hwName,hwTitle,hwQualification,hwCouncil,hwLicense,hwStatus,hwRegno,hwRegDate;
    String surname,firstName,otherNames,title,qualification,council,license,status,regno,regdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        surname = intent.getStringExtra("surname");
        firstName = intent.getStringExtra("first_name");
        otherNames = intent.getStringExtra("other_names");
        title = intent.getStringExtra("title");
        qualification = intent.getStringExtra("qualification");
        council = intent.getStringExtra("council");
        license = intent.getStringExtra("license");
        status = intent.getStringExtra("status");
        regno = intent.getStringExtra("regno");
        regdate = intent.getStringExtra("regdate");
        hwName = findViewById(R.id.hwName);
        hwName.setText(String.format("%s%s %s %s", getString(R.string.names), surname, firstName, otherNames));
        hwTitle = findViewById(R.id.hwTitle);
        hwTitle.setText("Title:"+title);
        hwQualification = findViewById(R.id.hwQualification);
        hwQualification.setText("Qualification:"+qualification);
        hwCouncil = findViewById(R.id.hwCouncil);
        hwCouncil.setText("Professional Council:"+council);
        hwLicense=findViewById(R.id.hwLicense);
        hwLicense.setText("License No:"+license);
        hwStatus=findViewById(R.id.hwStatus);
        hwStatus.setText("License Status:"+status);
        hwRegno=findViewById(R.id.hwRegNo);
        hwRegno.setText("RegNo:"+regno);
        hwRegDate=findViewById(R.id.hwRegDate);
        hwRegDate.setText("Registration Date:"+regdate);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
