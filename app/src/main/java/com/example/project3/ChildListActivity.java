package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ChildListActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ArrayList<ChildMoveDTO> dto;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);
        Intent intent = getIntent();
        final String id_final = intent.getExtras().getString("loginid");

        items = new ArrayList<>();
        dto = new ArrayList<>();
        lv = findViewById(R.id.lvPage);


        try {
            String result = new CustomTask().execute(id_final).get();
            Log.d("받아온 값", result);

            JSONObject jsonObject = new JSONObject(result); //result를 인자로 넣어 jsonObject를 생성한다.

            JSONArray jsonArray = jsonObject.getJSONArray("dataSet2"); //"dataSet"의 jsonObject들을 배열로 저장한다.

            ArrayList<String> list = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) { //jsonObject에 담긴 두 개의 jsonObject를 jsonArray를 통해 하나씩 호출한다.
                jsonObject = jsonArray.getJSONObject(i);
                list.add(jsonObject.getString("c_name") + " " + jsonObject.getString("c_age") + " ");
                dto.add(new ChildMoveDTO(list.get(i), ChildUpdateActivity.class));
            }

        } catch (Exception e) {
            Toast.makeText(ChildListActivity.this, "오류발생", Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);


        for (int i = 0; i < dto.size(); i++) {
            items.add((i + 1) + ". " + dto.get(i).getName());
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position --> 클릭한 위치를 알려줌
                //최상단에 있는 item을 클릭시 --> position의 번호는 ??? 0
                Intent intent = new Intent(getApplicationContext(), dto.get(position).getPage());
                intent.putExtra("loginid", id_final);
                startActivity(intent);
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
                    URL url = new URL("http://192.168.56.1:8081/WellDeep/childlist_android.jsp"); //보낼 jsp 주소를 ""안에 작성합니다.
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id=" + strings[0];//보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
                    Log.e("send", sendMsg);
                    //회원가입처럼 보낼 데이터가 여러 개일 경우 &로 구분하여 작성합니다.
                    osw.write(sendMsg);//OutputStreamWriter에 담아 전송합니다.
                    osw.flush();
                    //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        //jsp에서 보낸 값을 받겠죠?
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();

                    } else {
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
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

