package com.example.project3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    Button child_submit, child_cancel, btn_take;
    RadioGroup rg;
    int PICK_IMAGE;
    private final int GET_GALLERY_IMAGE = 200;
    String imgName;
    //사진올리기
/*    private final int REQ_CODE_SELECT_IMAGE = 100;
    private String img_path = new String();
    private String serverURL = "http://192.168.56.1:8081/WellDeep/ImgUpload.java";  //<<서버주소
    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    private String imageName = null;*/
    //----------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);


/*
        //사진올리기
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());
        //-----------------------------------------------
*/


        child_submit = findViewById(R.id.child_submit);
        child_cancel = findViewById(R.id.child_cancel);
        btn_take = findViewById(R.id.btn_take);
        rg = findViewById(R.id.rg);

        etchild_name = findViewById(R.id.etchild_name);
        final RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        etchild_age = findViewById(R.id.etchild_age);
        img_child = findViewById(R.id.img_child);
        et_id = findViewById(R.id.et_id);

        Intent intent = getIntent();
        final String id_final = intent.getExtras().getString("loginid");
        final String name = intent.getExtras().getString("name");

        et_id.setText(id_final);
        et_id.setEnabled(false);



        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        child_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildActivity.this, MainActivity.class);
                intent.putExtra("loginid", id_final);
                intent.putExtra("name", name);
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


                    String result = new CustomTask().execute(name, sex, age, imgName, id, "child_join").get();
                    Toast.makeText(ChildActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();

                    //사진 올리기
                    //DoFileUpload(serverURL, img_path);
                   /* Toast.makeText(getApplicationContext(), "이미지 전송 성공", Toast.LENGTH_SHORT).show();
                    Log.d("Send", "Success");*/


                    if (result.contains("0")) {
                        Toast.makeText(ChildActivity.this, "아이등록실패", Toast.LENGTH_SHORT).show();
                    } else if (result == null) {
                        Toast.makeText(ChildActivity.this, "아이등록실패", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChildActivity.this, "아이등록성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChildActivity.this, MainActivity.class);
                        intent.putExtra("loginid", id_final);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(ChildActivity.this, "아이등록 실패했음", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //사진올리기

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getBaseContext(), "resultCode : " + data, Toast.LENGTH_SHORT).show();


        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
                    Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
                    Log.v("img_path",img_path);
                    //이미지를 비트맵형식으로 반환
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    //사용자 단말기의 width , height 값 반환
                    int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
                    int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());

                  *//*  Uri selectedImageUri = data.getData();
                     img_child.setImageURI(selectedImageUri);*//*

                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                    image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                    ImageView image = (ImageView) findViewById(R.id.img_child);  //이미지를 띄울 위젯 ID값
                    image.setImageBitmap(image_bitmap_copy);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//end of onActivityResult()*/
    //------------------------------------------

/*    //사진올리기
    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(ChildActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;

        return imgName;
    }//end of getImagePathToUri()*/
//------------------------------------------------------------------
public String getImagePathToUri(Uri data) {
    //사용자가 선택한 이미지의 정보를 받아옴
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor cursor = managedQuery(data, proj, null, null, null);
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();

    //이미지의 경로 값
    String imgPath = cursor.getString(column_index);
    Log.d("test", imgPath);

    //이미지의 이름 값
    String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
    Toast.makeText(ChildActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
    this.imgName = imgName;

    return imgName;
}//end of getImagePathToUri()*/



    //-------------------------------------------------------

    //사진올리기
 /*   public void DoFileUpload(String apiUrl, String absolutePath) {
        HttpFileUpload(apiUrl, "", absolutePath);
    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";*/

  /*  public void HttpFileUpload(String urlString, String params, String fileName) {
        try {

            FileInputStream mFileInputStream = new FileInputStream(fileName);
            URL connectUrl = new URL(urlString);
            Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // HttpURLConnection 통신
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush();
            // finish upload...

            // get response
            InputStream is = conn.getInputStream();

            StringBuffer b = new StringBuffer();
            for (int ch = 0; (ch = is.read()) != -1; ) {
                b.append((char) ch);
            }
            is.close();
            Log.e("Test", b.toString());


        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
    } // end of HttpFileUpload()*/




    //--------------------------------------------------------------


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
                sendMsg = "name=" + strings[0] + "&sex=" + strings[1] + "&age=" + strings[2] +
                        "&img=" + strings[3] + "&id=" + strings[4] + "&type=" + strings[5];
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

    @Override //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            img_child.setImageURI(selectedImageUri);
            getImagePathToUri(selectedImageUri);
            Toast.makeText(ChildActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        }

    }}
