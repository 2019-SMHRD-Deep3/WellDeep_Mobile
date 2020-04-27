package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

public class RecordingActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;
    ArrayList<RecordingDTO> dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        Intent intent = getIntent();
        final String id_final = intent.getExtras().getString("loginid");

        items = new ArrayList<>();
        dto = new ArrayList<>();
        lv = findViewById(R.id.lv_page);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);

        dto.add(new RecordingDTO("녹음1", DetailActivity.class));

        for (int i = 0; i < dto.size(); i++) {
            items.add((i+1)+". "+dto.get(i).getTitle());
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position --> 클릭한 위치를 알려줌
                //최상단에 있는 item을 클릭시 --> position의 번호는 ??? 0
                Intent intent = new Intent(getApplicationContext(), dto.get(position).getPage());
                startActivity(intent);
            }
        });

    }

}