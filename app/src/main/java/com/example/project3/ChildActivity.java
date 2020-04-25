package com.example.project3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ChildActivity extends AppCompatActivity {
    EditText et_id, etchild_name, etchild_age;
    ImageView img_child;
    Button child_submit,child_cancel, btn_take;
    RadioGroup rg;
    int PICK_IMAGE;
    private final int GET_GALLERY_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        child_submit = findViewById(R.id.child_submit);
        child_cancel = findViewById(R.id.child_cancel);
        btn_take = findViewById(R.id.btn_take);
        rg = findViewById(R.id.rg);

        etchild_name = findViewById(R.id.etchild_name);
        final RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        etchild_age = findViewById(R.id.etchild_age);
        img_child = findViewById(R.id.img_child);
        et_id = findViewById(R.id.et_id);

        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        child_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        child_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etchild_name.getText().toString();
                String sex = rd.getText().toString();
                String age = etchild_age.getText().toString();
                String img = img_child.getDrawable().toString();
                String id = et_id.getText().toString();

                try {
                    String result = new CustomTask().execute(name,sex,age,img,id,"child_join").get();

                    if (result.contains("0")) {
                        Toast.makeText(ChildActivity.this, "아이등록실패", Toast.LENGTH_SHORT).show();
                    } else if(result == null) {
                        Toast.makeText(ChildActivity.this,"아이등록실패", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChildActivity.this, "아이등록성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChildActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();}

                } catch (Exception e) {
                    Toast.makeText(ChildActivity.this, "아이등록 실패했음", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개값이 문자열 배열인데요. 보낼 값이 여러개일 경우를 위해 배열로 합니다.
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.56.1:8081/WellDeep/join_android.jsp"); //보낼 jsp 주소를 ""안에 작성합니다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "name="+strings[0]+"&sex="+strings[1]+"&age="+strings[2]+
                        "&img="+strings[3]+"&id="+strings[4]+"&type="+strings[5];
                //보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
                //회원가입처럼 보낼 데이터가 여러 개일 경우 &로 구분하여 작성합니다.
                osw.write(sendMsg);//OutputStreamWriter에 담아 전송합니다.
                osw.flush();
                //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    //jsp에서 보낸 값을 받겠죠?
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                    // 통신이 실패했을 때 실패한 이유를 알기 위해 로그를 찍습니다.
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //jsp로부터 받은 리턴 값입니다.
            return receiveMsg;
        }
    }
    @Override //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            img_child.setImageURI(selectedImageUri);

        }

    }}
