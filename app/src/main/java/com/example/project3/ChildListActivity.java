package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChildListActivity extends AppCompatActivity {

    ArrayList<String> items;
    int count = 0;


    ArrayAdapter<String> adapter;
    ArrayList<ChildMoveDTO> dto;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);
        ListView lv;

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,items);

        dto = new ArrayList<>();
        lv = findViewById(R.id.lvPage);
        lv.setAdapter(adapter);

        dto.add(new ChildMoveDTO("쓰레드",ThreadActivity.class));

        for(int i =0; i<dto.size(); i++){
            items.add(++count+". "+dto.get(i).getName());
        }
    }
}
