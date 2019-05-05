package com.scriptfloor.hda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scriptfloor.hda.FacilityActivity;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.fragment.RegistrationFragment;
import com.scriptfloor.hda.models.FacilityModel;

import java.util.List;

/**
 * Created by Lincoln on 8/15/2018.
 */

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {
    private static List<FacilityModel> facilities;
    private static Context context;

    public FacilityAdapter(Context context, List<FacilityModel> facilityModelList) {
        FacilityAdapter.facilities=facilityModelList;
        FacilityAdapter.context =context;
    }

    @Override
    public FacilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.facility_item, null);
        itemLayoutView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(FacilityAdapter.ViewHolder holder, int position) {
        holder.facilityName.setText(limit(facilities.get(position).getFacilityName(),100));
        String loc=facilities.get(position).getFacilityLocation();
        //TO DO: Check for null location
        String[] addresses=facilities.get(position).getFacilityLocation()!=null?facilities.get(position).getFacilityLocation().split(","):null;
        String location=addresses[2]!=null?addresses[2]:facilities.get(position).getFacilityLocation();
        holder.facilityAddress.setText(limit(location,100));
        holder.facilitySector.setText(limit(facilities.get(position).getFacilitySector(),100));
        holder.facilityCategory.setText(limit(facilities.get(position).getFacilityCategory(),20));
    }

    @Override
    public int getItemCount() {
        return facilities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView facilityName, facilityAddress,facilitySector,facilityCategory;
        public ImageView facilityImage;

        public ViewHolder(View facilityLayoutView) {
            super(facilityLayoutView);
            facilityName = facilityLayoutView.findViewById(R.id.facility_name);
            facilityAddress = facilityLayoutView.findViewById(R.id.facility_address);
            facilitySector = facilityLayoutView.findViewById(R.id.facility_sector);
            facilityCategory = facilityLayoutView.findViewById(R.id.facility_category);
            facilityImage = facilityLayoutView.findViewById(R.id.facility_image);

            facilityLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, FacilityActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("position",getAdapterPosition());
            intent.putExtra("id",facilities.get(getAdapterPosition()).getFacilityID());
            intent.putExtra("facility",facilities.get(getAdapterPosition()).getFacilityName());
            intent.putExtra("sector",facilities.get(getAdapterPosition()).getFacilitySector());
            RegistrationFragment fragobj = new RegistrationFragment();
            fragobj.setArguments(intent);
            context.startActivity(intent);
        }
    }

    public static String limit(String value, int length)
    {
        StringBuilder buf = new StringBuilder(value);
        if (buf.length() > length)
        {
            buf.setLength(length);
            buf.append("...");
        }

        return buf.toString();
    }

    public void clear() {
        facilities.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<FacilityModel> facility) {
        facilities.addAll(facility);
        notifyDataSetChanged();
    }
}
