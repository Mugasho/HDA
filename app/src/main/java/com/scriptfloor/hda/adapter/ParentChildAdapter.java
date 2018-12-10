package com.scriptfloor.hda.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scriptfloor.hda.R;
import com.scriptfloor.hda.models.ParentDataItem;

import java.util.ArrayList;

/**
 * Created by LINCOLN on 10/23/2018.
 */

public class ParentChildAdapter extends RecyclerView.Adapter<ParentChildAdapter.ViewHolder> {
    private ArrayList<ParentDataItem> ParentDataItems;
    private Context context;

    public ParentChildAdapter(ArrayList<ParentDataItem> parentDataItems, Context context) {
        ParentDataItems = parentDataItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_listing, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParentChildAdapter.ViewHolder holder, int position) {
        ParentDataItem ParentDataItem = ParentDataItems.get(position);
        holder.textView_parentName.setText(ParentDataItem.getParentName());

        //
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int noOfChild = ParentDataItem.getChildDataItems().size();
        if (noOfChild > 0) {
            for (int i = 0; i < noOfChild; i++) {
                View convertView = inflater.inflate(R.layout.child_item_layout, null);
                TextView txtListChildTitle = convertView.findViewById(R.id.txt_child_title);
                TextView txtListChildDetail = convertView.findViewById(R.id.txt_child_detail);
                String title = ParentDataItems.get(position).getChildDataItems().get(i).getChildName();
                if (title != null) {
                    txtListChildTitle.setText(title);
                    txtListChildTitle.setVisibility(View.VISIBLE);
                }
                txtListChildDetail.setText(ParentDataItems.get(position).getChildDataItems().get(i).getChildDetail());
                holder.linearLayout_childItems.addView(convertView);
            }
        } else {
            holder.linearLayout_childItems.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return ParentDataItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView textView_parentName;
        private LinearLayout linearLayout_childItems;
        private TextView txtListChildDetail;
        private View convertView;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);

           /* LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int intMaxNoOfChild = 0;
            for (int index = 0; index < ParentDataItems.size(); index++) {
                int intMaxSizeTemp = ParentDataItems.get(index).getChildDataItems().size();
                if (intMaxSizeTemp > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp;
            }
            for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {
*//*                TextView textView = new TextView(context);
                textView.setId(indexView);
                textView.setPadding(5, 5, 5, 5);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setOnClickListener(this);
                linearLayout_childItems.addView(textView, layoutParams);*//*
                //

                convertView=inflater.inflate(R.layout.child_item_layout,null);
                txtListChildDetail = convertView.findViewById(R.id.txt_child_detail);
                txtListChildDetail.setText(ParentDataItems.get(indexView).getChildDataItems().get(textViewIndex).getChildName());
                linearLayout_childItems.addView(convertView);
            }*/
            textView_parentName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_parentName) {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linearLayout_childItems.setVisibility(View.GONE);
                } else {
                    linearLayout_childItems.setVisibility(View.VISIBLE);
                }
            } else {
                TextView textViewClicked = (TextView) view;
                Toast.makeText(context, "" + textViewClicked.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
