package com.scriptfloor.hda.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gturedi.views.StatefulLayout;
import com.scriptfloor.hda.FeedbackActivity;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.models.BatchModel;
import com.scriptfloor.hda.utils.SQLiteHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class VerifyFragment extends Fragment {
    public static StatefulLayout stateful;
    public static SQLiteHandler dbHandler;
    public static TextView batch_no_txt, batch_found, batch_alert, safety_alert, instruction_txt, govt_txt;
    public static ImageView verifyImage;
    public static LinearLayout lay_verify;
    public static String searchString, reason;

    public static Button btnReport, btn_info;

    public VerifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verify, container, false);
        batch_no_txt = view.findViewById(R.id.batch_no_txt);
        btn_info = view.findViewById(R.id.btn_drug_info);
        safety_alert = view.findViewById(R.id.safety_txt);
        instruction_txt = view.findViewById(R.id.instruction_txt);
        batch_found = view.findViewById(R.id.batch_found);
        govt_txt = view.findViewById(R.id.govt_txt);
        batch_alert = view.findViewById(R.id.batch_alert);
        verifyImage = view.findViewById(R.id.image_verify);
        lay_verify = view.findViewById(R.id.lay_verify);
        stateful = view.findViewById(R.id.stateful);
        btnReport = view.findViewById(R.id.btnReport);
        lay_verify.setVisibility(View.GONE);
        stateful.showEmpty("Enter a batch no to verify");
        dbHandler = new SQLiteHandler(getActivity());
        if (dbHandler.getBatchNo("67B55TXT").size() < 1) {
            dbHandler.addBatch("67B55TXT", "1", "5-6-2017", "1-1-2017", null);
            dbHandler.addBatch("68B55TXT", "32", "5-6-2018", "1-1-2021", null);
            dbHandler.addBatch("69B55TXT", "15", "5-6-2016", "1-1-2020", null);
            dbHandler.addBatch("70B55TXT", "45", "5-6-2017", "1-1-2019", null);
            dbHandler.addBatch("71B55TXT", "132", "5-6-2017", "1-1-2020", null);
            dbHandler.addBatch("72B55TXT", "78", "5-6-2017", "1-1-2020", null);
            dbHandler.addBatch("73B55TXT", "90", "5-6-2017", "1-1-2020", null);
            dbHandler.addBatch("74B55TXT", "345", "5-6-2016", "1-1-2018", null);
        }
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    public void Search(final String searchString, Context context) {
        this.searchString = searchString;
        Date made, expiry;
        Log.d("Str", searchString);
        stateful.showLoading();
        if (searchString == "") {
            stateful.showEmpty("Enter a batch no to verify");
        } else try {
            dbHandler = new SQLiteHandler(context);
            List<BatchModel> Batch;
            Batch = dbHandler.getBatchNo(searchString);
            lay_verify.setVisibility(View.VISIBLE);
            batch_no_txt.setText(searchString);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            if (Batch.size() < 1) {
                batch_found.setText(R.string.batch_not_found);
                batch_alert.setText(R.string.possible_reason);
                safety_alert.setText(R.string.warning);
                safety_alert.setTextColor(Color.RED);
                instruction_txt.setText(R.string.not_found_reason);
                verifyImage.setImageResource(R.drawable.ic_error_black_24dp);
                btn_info.setText(R.string.about_unregistered);

            } else {
                try {
                    expiry = dateFormat.parse(Batch.get(0).getBatchExpiry());
                    if (new Date().getTime() > expiry.getTime()) {
                        batch_found.setText(R.string.batch_found);
                        safety_alert.setText(R.string.expired);
                        safety_alert.setVisibility(View.VISIBLE);
                        batch_alert.setText(R.string.not_safe);
                        safety_alert.setTextColor(Color.RED);
                        instruction_txt.setText(R.string.nda_info);
                        govt_txt.setVisibility(View.GONE);
                        verifyImage.setImageResource(R.drawable.ic_cancel);
                        btnReport.setBackgroundResource(R.drawable.red_round_btn);
                        btn_info.setText(R.string.medicine_info);
                    } else {
                        batch_found.setText(R.string.batch_found);
                        safety_alert.setVisibility(View.GONE);
                        batch_alert.setText(R.string.safe);
                        batch_alert.setTextColor(Color.parseColor("#2e7d32"));
                        instruction_txt.setText(R.string.instruction);
                        govt_txt.setVisibility(View.GONE);
                        verifyImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
                        btnReport.setBackgroundResource(R.drawable.green_round_btn);
                        btn_info.setText(R.string.medicine_info);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            stateful.showContent();
            Log.d("Str", searchString);
        } catch (NumberFormatException e) {
            Log.d("Str", "Search failed");
        }


    }

}
