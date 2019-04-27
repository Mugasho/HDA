package com.scriptfloor.hda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scriptfloor.hda.models.NewsModel;
import com.scriptfloor.hda.utils.SQLiteHandler;

public class NewsActivity extends AppCompatActivity {

    TextView txt_content,txt_title;
    ImageView news_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        txt_content=findViewById(R.id.news_content_main);
        txt_title=findViewById(R.id.news_title_main);
        news_image=findViewById(R.id.news_image_main);
        Intent intent = getIntent();
        int id=intent.getIntExtra("id",0);
        NewsModel post;
        post=db.getPostByID(id);
        txt_content.setText(post.getNewsContent());
        txt_title.setText(post.getNewsTitle());
        Glide.with(getApplicationContext()).load(post.getNewsPic()).error(R.drawable.ic_image_empty).into(news_image);
        getSupportActionBar().setTitle(post.getNewsTitle());
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
