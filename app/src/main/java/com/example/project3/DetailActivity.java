package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

public class DetailActivity extends AppCompatActivity {

    private ImageView iv_img;
    TextView tv_test;
    Button btn_play, btn_stop, btn_call;
    MediaPlayer player;
    String img_url;
    String voice_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        iv_img = findViewById(R.id.iv_img);

        btn_play = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);
        btn_call = findViewById(R.id.btn_call);

        tv_test = findViewById(R.id.tv_test);
        Intent intent = getIntent();

        String[] num = intent.getExtras().getStringArray("num"); // 클릭한 알람 번호 가져오기
        tv_test.setText(num[0]); // 알람 번호 잘 가져오는지 테스트
        final String id_final = intent.getExtras().getString("loginid");

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player = MediaPlayer.create(getApplicationContext(),R.raw.anysong);

                player.start();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.stop();
            }
        });


        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(("tel:010-1111-2222")));
                startActivity(intent);
            }
        });

        final String alarm_num = num[0];

        try {
            String result  = new DetailActivity.CustomTask().execute(alarm_num).get();
            Log.d("받아온 값", result);

            JSONObject jsonObject = new JSONObject(result); //result를 인자로 넣어 jsonObject를 생성한다.

            JSONArray jsonArray = jsonObject.getJSONArray("dataSet"); //"dataSet"의 jsonObject들을 배열로 저장한다.

            for(int i=0; i<jsonArray.length(); i++) { //jsonObject에 담긴 두 개의 jsonObject를 jsonArray를 통해 하나씩 호출한다.
                jsonObject = jsonArray.getJSONObject(i);
                img_url = "http://192.168.56.1:8081/WellDeep/img/" + jsonObject.getString("i_file"); // 이미지파일 가져오기
                voice_url = "http://192.168.56.1:8081/WellDeep/voice/" + jsonObject.getString("v_file"); // 음성파일 가져오기
            }
            // Glide로 이미지 표시하기
        Glide.with(this).load(img_url).into(iv_img);

        }catch (Exception e) {
            Toast.makeText(DetailActivity.this,"오류발생",Toast.LENGTH_SHORT).show();
        }

    }
    // 알람 번호 서버에 전송
    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개값이 문자열 배열인데요. 보낼 값이 여러개일 경우를 위해 배열로 합니다.
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.56.1:8081/WellDeep/Alarm_get_android.jsp"); //보낼 jsp 주소를 ""안에 작성합니다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "num="+strings[0];//보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
                Log.e("send",sendMsg);
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