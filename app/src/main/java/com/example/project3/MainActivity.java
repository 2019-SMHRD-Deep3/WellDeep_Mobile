package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn_question, btn_modify, btn_view, btn_child, btn_childlist, btn_video;
    TextView tv_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_view = findViewById(R.id.btn_view);
        btn_question = findViewById(R.id.btn_question);
        btn_modify = findViewById(R.id.btn_modify);
        btn_child = findViewById(R.id.btn_child);
        btn_childlist = findViewById(R.id.btn_childlist);
        btn_video = findViewById(R.id.btn_video);
        tv_id = findViewById(R.id.tv_id);

        Intent intent = getIntent();
        final String id_final = intent.getExtras().getString("loginid");
        final String pw = intent.getExtras().getString("pw");
        final String addr = intent.getExtras().getString("addr");
        final String phone = intent.getExtras().getString("phone");
        final String name = intent.getExtras().getString("name");
        final String sex = intent.getExtras().getString("sex");
        tv_id.setText(id_final);

        btn_video.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        }));

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                intent.putExtra("loginid",id_final);
                startActivity(intent);
            }
        });
        btn_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                intent.putExtra("loginid",id_final);
                startActivity(intent);
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinUpdateActivity.class);
                intent.putExtra("loginid",id_final);
                intent.putExtra("pw",pw);
                intent.putExtra("addr",addr);
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                intent.putExtra("sex",sex);
                startActivity(intent);
            }
        });

        btn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChildActivity.class);
                intent.putExtra("loginid",id_final);
                startActivity(intent);
            }
        });
        btn_childlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChildListActivity.class);
                intent.putExtra("loginid",id_final);
                startActivity(intent);
            }
        });


    }



}

