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

import com.ramotion.foldingcell.FoldingCell;
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
        int image=ParentDataItem.getItemImage()!=0?ParentDataItem.getItemImage():R.drawable.alerter_ic_face;
        holder.tv_image.setImageResource(image);
        holder.tv_parentName.setText(ParentDataItem.getParentName());
        holder.tv_childName.setText(ParentDataItem.getChildDataItems().get(0).getChildDetail());
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
        private TextView tv_parentName;
        private TextView tv_childName;
        private ImageView tv_image;
        private LinearLayout linearLayout_childItems;
        private FoldingCell fc;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            fc =itemView.findViewById(R.id.folding_cell);
            tv_image=itemView.findViewById(R.id.itemImage);
            tv_parentName = itemView.findViewById(R.id.title_from_address);
            linearLayout_childItems=itemView.findViewById(R.id.linearLayout_childItems);
            tv_childName=itemView.findViewById(R.id.title_to_address);
            fc.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            fc.toggle(true);
        }
    }
}
