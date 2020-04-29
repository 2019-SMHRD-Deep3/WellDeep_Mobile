package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static android.os.Build.VERSION_CODES.O;

public class LoginActivity extends AppCompatActivity {

    Button btn_join, btn_login;
    EditText user_id, user_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_join = findViewById(R.id.btn_join);
        btn_login = findViewById(R.id.btn_login);
        user_id = findViewById(R.id.user_id);
        user_pw = findViewById(R.id.user_pw);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginid = user_id.getText().toString();
                String loginpw = user_pw.getText().toString();

                try {
                    String result  = new CustomTask().execute(loginid,loginpw,"login").get();
                    Log.d("통신 결과", result);

                    JSONObject jsonObject = new JSONObject(result); //result를 인자로 넣어 jsonObject를 생성한다.

                    JSONArray jsonArray = jsonObject.getJSONArray("dataSet"); //"dataSet"의 jsonObject들을 배열로 저장한다.
                    jsonObject = jsonArray.getJSONObject(0);

                    String pw =  jsonObject.getString("p_pw");
                    String addr = jsonObject.getString("p_addr");
                    String phone = jsonObject.getString("p_phone");
                    String name = jsonObject.getString("p_name");
                    String sex =  jsonObject.getString("p_sex");

                    if(result.contains("p_pw")) {
                        Toast.makeText(LoginActivity.this,"로그인", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("loginid",loginid);
                        intent.putExtra("pw", pw);
                        intent.putExtra("addr", addr);
                        intent.putExtra("phone", phone);
                        intent.putExtra("name", name);
                        intent.putExtra("sex", sex);
                        startActivity(intent);
                        finish();}

                }catch (Exception e) {
                    Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 틀렸음",Toast.LENGTH_SHORT).show();
                    user_id.setText("");
                    user_pw.setText("");
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
                sendMsg = "id="+strings[0]+"&pw="+strings[1]+"&type="+strings[2];//보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
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