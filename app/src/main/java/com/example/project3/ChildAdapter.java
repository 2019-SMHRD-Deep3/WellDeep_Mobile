package com.example.project3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ChildAdapter extends BaseAdapter {

    private Context context;
    private int child_item;
    private ArrayList<ChildDTO> items;
    private LayoutInflater inflater;


    public ChildAdapter(Context context, int child_item, ArrayList<ChildDTO> items) {

        this.context = context;
        this.child_item = child_item;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(child_item, parent, false);
        }

        final TextView tv_name = convertView.findViewById(R.id.tv_name);
        final TextView tv_age = convertView.findViewById(R.id.tv_age);
        final TextView tv_sex = convertView.findViewById(R.id.tv_sex);
        final  TextView tv_number = convertView.findViewById(R.id.tv_num);
        final Button btn_del = convertView.findViewById(R.id.btn_del);


        tv_name.setText(items.get(position).getC_name());
        tv_age.setText(items.get(position).getC_age());
        tv_sex.setText(items.get(position).getC_sex());
        tv_number.setText(items.get(position).getC_number());




        Log.d("number2Adapter",items.get(position).getC_number());
        Log.d("name2Adapter",items.get(position).getC_name());
        Log.d("age2Adapter",items.get(position).getC_age());
        Log.d("sex2Adapter",items.get(position).getC_sex());



//        String num = items.get(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChildUpdateActivity.class);

                intent.putExtra("c_name", tv_name.getText().toString());
                intent.putExtra("c_age", tv_age.getText().toString());
                intent.putExtra("c_sex", tv_sex.getText().toString());
                intent.putExtra("c_number", tv_number.getText().toString());





                context.startActivity(intent);
            }
        });


        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show();
                items.remove(position);
                notifyDataSetChanged();*/
                String num = tv_number.getText().toString();
                Log.d("가져온 넘버",num);

            }
        });




        return convertView;


    }


    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        // doInBackground의 매개값이 문자열 배열인데요. 보낼 값이 여러개일 경우를 위해 배열로 합니다.
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.56.1:8081/WellDeep/Childlist_android.jsp"); //보낼 jsp 주소를 ""안에 작성합니다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id=" + strings[0] + "&pw=" + strings[1] + "&addr=" + strings[2] +
                        "&tel=" + strings[3] + "&name=" + strings[4] + "&sex=" + strings[5] + "&type=" + strings[6];
                //보낼 정보인데요. GET방식으로 작성합니다. ex) "id=rain483&pwd=1234";
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