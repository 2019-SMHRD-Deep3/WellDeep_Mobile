package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.os.Build.VERSION_CODES.O;

public class ChildActivity extends AppCompatActivity {
    Button child_submit,child_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        child_submit = findViewById(R.id.child_submit);
        child_cancel = findViewById(R.id.child_cancel);

        child_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        child_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
