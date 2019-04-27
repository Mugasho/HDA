package com.scriptfloor.hda.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.scriptfloor.hda.BrowserActivity;
import com.scriptfloor.hda.NewsActivity;
import com.scriptfloor.hda.R;
import com.scriptfloor.hda.models.NewsModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LINCOLN on 3/24/2019.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static List<NewsModel> newsList;
    private static Context context;

    public NewsAdapter(Context context, List<NewsModel> newsList) {
        NewsAdapter.newsList = newsList;
        NewsAdapter.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.news_item, null);
        itemLayoutView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));

        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.newsTitle.setText(limit(newsList.get(position).getNewsTitle(), 30));
        holder.newsContent.setText(limit(newsList.get(position).getNewsContent(), 50));
        String url=newsList.get(position).getNewsPic();
        Glide.with(context).load(url).error(R.drawable.ic_image_empty)
                .into(holder.newsImage);
        //getPic(url,holder.newsImage);

    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView newsTitle,newsContent;

        public ImageView newsImage;

        public ViewHolder(View newsLayoutView) {
            super(newsLayoutView);
            newsTitle = newsLayoutView.findViewById(R.id.news_title);
            newsContent = newsLayoutView.findViewById(R.id.news_content);
            newsImage = newsLayoutView.findViewById(R.id.news_image);
            newsLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id",newsList.get(getAdapterPosition()).getNewsID());
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
        newsList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<NewsModel> news) {
        newsList=news;
        notifyDataSetChanged();
    }
    protected Uri saveImageToInternalStorage(Bitmap bitmap){
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(context);

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images",MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, "UniqueFileName"+".jpg");

        try{
            // Initialize a new OutputStream
            OutputStream stream = null;

            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            // Flushes the stream
            stream.flush();

            // Closes the stream
            stream.close();

        }catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        return savedImageURI;
    }


}
