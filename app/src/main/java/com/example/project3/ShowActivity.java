package com.example.project3;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ShowActivity extends AppCompatActivity {

    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ivImage = findViewById(R.id.iv_image);

        // Glide로 이미지 표시하기

        String imageUrl = "http://192.168.56.1:8081/WellDeep/img/tree.jpg";
        Glide.with(this).load(imageUrl).into(ivImage);
    }
}