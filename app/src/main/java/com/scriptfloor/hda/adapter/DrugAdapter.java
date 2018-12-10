package com.scriptfloor.hda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.scriptfloor.hda.DrugDetailsActivity;
import com.scriptfloor.hda.MainActivity;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.models.DrugModel;

import java.util.List;

/**
 * Created by Lincoln on 8/4/2018.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> implements SectionTitleProvider {

    private static List<DrugModel> DrugList;
    private Context context;

    public DrugAdapter(Context context, List<DrugModel> DrugList) {
        DrugAdapter.DrugList = DrugList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.drug_item, null);
        itemLayoutView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));

        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.drugBrand.setText(limit(DrugList.get(position).getDrugBrand(), 30));
        holder.drugGeneric.setText(limit(DrugList.get(position).getDrugGeneric(), 30));
        holder.drugClass.setText(limit(DrugList.get(position).getDrugClass(), 30));
        holder.drugCompany.setText(limit(DrugList.get(position).getDrugCompany(), 50));
        holder.drugCountry.setText(limit(DrugList.get(position).getDrugCountry(), 30));
        switch (DrugList.get(position).getDrugType().toLowerCase()) {
            case "topical agent":
                holder.drugType.setImageResource(R.drawable.ic_paste);
                break;
            case "oral solution":
                holder.drugType.setImageResource(R.drawable.ic_solution);
                break;
            case "tablet":
                holder.drugType.setImageResource(R.drawable.ic_drugs);
                break;
            default:
                holder.drugType.setImageResource(R.drawable.ic_drugs);
        }
    }


    @Override
    public int getItemCount() {
        return DrugList.size();
    }

    @Override
    public String getSectionTitle(int position) {
        return DrugList.get(position).getDrugBrand().substring(0, 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView drugBrand, drugGeneric, drugClass, drugCompany, drugCountry;
        ImageView drugType;

        private ViewHolder(View drugLayoutView) {
            super(drugLayoutView);
            drugBrand = drugLayoutView.findViewById(R.id.brandName);
            drugGeneric = drugLayoutView.findViewById(R.id.genericName);
            drugClass = drugLayoutView.findViewById(R.id.drugClass);
            drugCompany = drugLayoutView.findViewById(R.id.company);
            drugCountry = drugLayoutView.findViewById(R.id.country);
            drugType = drugLayoutView.findViewById(R.id.drugType);
            drugLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,DrugDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("position",getAdapterPosition());
            intent.putExtra("brand",DrugList.get(getAdapterPosition()).getDrugBrand());
            intent.putExtra("generic",DrugList.get(getAdapterPosition()).getDrugGeneric());
            intent.putExtra("class",DrugList.get(getAdapterPosition()).getDrugClass());
            intent.putExtra("company",DrugList.get(getAdapterPosition()).getDrugCompany());
            context.startActivity(intent);
            //Toast.makeText(context, "you have clicked Drug " + DrugList.get(getAdapterPosition()).getDrugID(),Toast.LENGTH_LONG).show();
        }
    }

    public static String limit(String value, int length) {
        value=value.toLowerCase();
        StringBuilder buf = new StringBuilder(value);
        if (buf.length() > length) {
            buf.setLength(length);
            buf.append("...");
        }
        
        return buf.toString();
    }

}
