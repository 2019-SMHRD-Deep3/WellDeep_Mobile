package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QuestionActivity extends AppCompatActivity {

    EditText et_question;
    Button btn_cancel, btn_submit;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        et_question = findViewById(R.id.et_question);
        spinner = findViewById(R.id.spinner);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        spinner = findViewById(R.id.spinner);

        Intent intent = getIntent();
        final String id_final = intent.getExtras().getString("loginid");
        final String name = intent.getExtras().getString("name");

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.spinnerArray, android.R.layout.simple_spinner_dropdown_item);
        //R.array.test는 저희가 정의해놓은 1월~12월 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식입니다.
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter); //어댑터에 연결해줍

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                intent.putExtra("loginid",id_final);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String context = et_question.getText().toString();
            String title = spinner.getSelectedItem().toString();

                try {
                String result = new CustomTask().execute(context,id_final,title, "question").get();
                Log.d("통신 결과", result);

                if (result.contains("0")) {
                    Toast.makeText(QuestionActivity.this, "질문실패", Toast.LENGTH_SHORT).show();
                } else if (result == null) {
                    Toast.makeText(QuestionActivity.this, "질문실패", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuestionActivity.this, "질문성공", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }catch(
            Exception e)
            { Toast.makeText(QuestionActivity.this, "질문등록 실패", Toast.LENGTH_SHORT).show();
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
            URL url = new URL("http://192.168.56.1:8081/WellDeep/post_android.jsp"); //보낼 jsp 주소를 ""안에 작성합니다.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "context="+strings[0]+"&id="+strings[1]+"&title="+strings[2];//보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
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
                Log.d("받아온 값",receiveMsg);

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

