package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.project3.fragment.FirstFragment;
import com.example.project3.fragment.SecondFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    Button btn_question, btn_modify, btn_view, btn_child, btn_childlist, btn_video;
    TextView tv_id;
    FragmentPagerAdapter adapterViewPager;
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = findViewById(R.id.viewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapterViewPager);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vp);

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
        tv_id.setText(name);

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

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM Log", "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM Log", "FCM 토큰 : " + token);
                    }
                });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FirstFragment.newInstance(0, "Page # 1");
                case 1:
                    return SecondFragment.newInstance(1, "Page # 2");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}

