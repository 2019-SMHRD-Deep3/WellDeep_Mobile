package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinUpdateActivity extends AppCompatActivity {

    Button btn_cancel,btn_submit;
    EditText et_name, et_id, et_pw, et_addr, et_tel;
    RadioGroup rg;
    RadioButton radio_man, radio_women;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_update);

        Intent intent = getIntent();
        final String id_final = intent.getExtras().getString("loginid");
        final String pw = intent.getExtras().getString("pw");
        final String addr = intent.getExtras().getString("addr");
        final String phone = intent.getExtras().getString("phone");
        final String name = intent.getExtras().getString("name");
        final String sex = intent.getExtras().getString("sex");


        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        rg = findViewById(R.id.rg);

        et_id = findViewById(R.id.et_id);
        et_id.setText(id_final);
        et_id.setEnabled(false);
        et_pw = findViewById(R.id.et_pw);
        et_pw.setText(pw);
        et_addr = findViewById(R.id.et_addr);
        et_addr.setText(addr);
        et_tel = findViewById(R.id.et_tel);
        et_tel.setText(phone);
        et_name = findViewById(R.id.et_name);
        et_name.setText(name);
        radio_man = findViewById(R.id.radio_man);
        radio_women = findViewById(R.id.radio_women);

        if(sex.contains("남")) {
            radio_man.setChecked(true);
            radio_women.setChecked(false);
        } else {
            radio_women.setChecked(true);
            radio_man.setChecked(false);
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinUpdateActivity.this, MainActivity.class);
                intent.putExtra("loginid",id_final);
                intent.putExtra("pw",pw);
                intent.putExtra("addr",addr);
                intent.putExtra("phone",phone);
                intent.putExtra("name",name);
                intent.putExtra("sex",sex);
                startActivity(intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();
                String addr = et_addr.getText().toString();
                String tel = et_tel.getText().toString();
                String name = et_name.getText().toString();
                String sex = rd.getText().toString();

                Log.d("성별",sex);

                try {
                    String result = new CustomTask().execute(id,pw,addr,tel,name,sex,"update").get();
                    Log.d("결과",result);

                    if (result.contains("0")) {
                        Toast.makeText(JoinUpdateActivity.this, "회원수정실패", Toast.LENGTH_SHORT).show();
                    } else if(result == null) {
                        Toast.makeText(JoinUpdateActivity.this,"회원수정실패", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(JoinUpdateActivity.this, "회원수정성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinUpdateActivity.this, MainActivity.class);
                        intent.putExtra("loginid",id_final);
                        intent.putExtra("pw",pw);
                        intent.putExtra("addr",addr);
                        intent.putExtra("phone",phone);
                        intent.putExtra("name",name);
                        intent.putExtra("sex",sex);
                        startActivity(intent);
                        finish();}

                } catch (Exception e) {
                    Toast.makeText(JoinUpdateActivity.this, "회원수정 실패했음", Toast.LENGTH_SHORT).show();
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
                sendMsg = "id="+strings[0]+"&pw="+strings[1]+"&addr="+strings[2]+
                        "&tel="+strings[3]+"&name="+strings[4]+"&sex="+strings[5]+"&type="+strings[6];
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
}
