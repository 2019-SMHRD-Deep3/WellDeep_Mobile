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
import java.util.concurrent.ExecutionException;

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
        final TextView tv_number = convertView.findViewById(R.id.tv_num);

        tv_name.setText(items.get(position).getC_name());
        tv_age.setText(items.get(position).getC_age());
        tv_sex.setText(items.get(position).getC_sex());
        tv_number.setText(items.get(position).getC_number());

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
        return convertView;
    }
}