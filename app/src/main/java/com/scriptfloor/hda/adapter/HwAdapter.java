package com.scriptfloor.hda.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.scriptfloor.hda.DrugDetailsActivity;
import com.scriptfloor.hda.HwProfileActivity;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.models.HwModel;

import java.util.List;

/**
 * Created by LINCOLN on 10/8/2018.
 */

public class HwAdapter extends RecyclerView.Adapter<HwAdapter.ViewHolder> implements SectionTitleProvider {
    private static List<HwModel> HwList;
    private Context context;

    public HwAdapter(Context context, List<HwModel> HwList) {
        this.HwList = HwList;
        this.context = context;
    }

    @Override
    public HwAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.hw_item, null);
        itemLayoutView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));

        // create ViewHolder
        return new HwAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(HwAdapter.ViewHolder holder, int position) {
        holder.HwName.setText(HwList.get(position).getNames());
        holder.HwQualification.setText(HwList.get(position).getQualification());
        holder.HwTitle.setText(HwList.get(position).getTitle());
        if(HwList.get(position).getLicenceStatus()=="inactive"){
            holder.HwStatus.setTextColor(Color.MAGENTA);}
        holder.HwStatus.setText(HwList.get(position).getLicenceStatus());
    }

    @Override
    public int getItemCount() {
        return HwList.size();
    }

    @Override
    public String getSectionTitle(int position) {
        return HwList.get(position).getNames().substring(0, 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView HwName, HwTitle, HwQualification, HwStatus;
        ImageView HwPhoto, HwID;

        private ViewHolder(View hwLayoutView) {
            super(hwLayoutView);
            HwName = hwLayoutView.findViewById(R.id.HwName);
            HwQualification = hwLayoutView.findViewById(R.id.HwQualification);
            HwTitle = hwLayoutView.findViewById(R.id.HwTitle);
            HwID = hwLayoutView.findViewById(R.id.HwID);
            HwStatus = hwLayoutView.findViewById(R.id.HwStatus);
            HwPhoto = hwLayoutView.findViewById(R.id.HwPhoto);
            hwLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, HwProfileActivity.class);
            intent.putExtra("id", HwList.get(getAdapterPosition()).getID());
            intent.putExtra("name", HwList.get(getAdapterPosition()).getNames());
            intent.putExtra("title", HwList.get(getAdapterPosition()).getTitle());
            intent.putExtra("qualification", HwList.get(getAdapterPosition()).getQualification());
            intent.putExtra("council", HwList.get(getAdapterPosition()).getCouncil());
            intent.putExtra("license", HwList.get(getAdapterPosition()).getLicence());
            intent.putExtra("status", HwList.get(getAdapterPosition()).getLicenceStatus());
            intent.putExtra("regno", HwList.get(getAdapterPosition()).getRegNo());
            intent.putExtra("regdate", HwList.get(getAdapterPosition()).getRegDate());
            context.startActivity(intent);
            //Toast.makeText(context, "you have clicked Drug " + HwList.get(getAdapterPosition()).getDrugID(),Toast.LENGTH_LONG).show();
        }
    }

    public static String limit(String value, int length) {
        value = value.toLowerCase();
        StringBuilder buf = new StringBuilder(value);
        if (buf.length() > length) {
            buf.setLength(length);
            buf.append("...");
        }

        return buf.toString();
    }
}
