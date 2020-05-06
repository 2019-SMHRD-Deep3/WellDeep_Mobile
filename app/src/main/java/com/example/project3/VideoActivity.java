
package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    Button btn_cam, btn_cam2, btn_cam3, btn_cam4;
    /*TextView tv_cam, tv_cam2, tv_cam3, tv_cam4;*/
    VideoView videoView, testvideo, testvideo2, testvideo3, testvideo4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

       btn_cam = findViewById(R.id.btn_cam);
       btn_cam2 = findViewById(R.id.btn_cam2);
       btn_cam3 = findViewById(R.id.btn_cam3);
       btn_cam4 = findViewById(R.id.btn_cam4);

      /* tv_cam = findViewById(R.id.tv_cam);
       tv_cam2 = findViewById(R.id.tv_cam2);
       tv_cam3 = findViewById(R.id.tv_cam3);
       tv_cam4 = findViewById(R.id.tv_cam4);
*/
       // 비디오 컴포넌트들을 변수에 지정
       videoView = (VideoView) findViewById(R.id.BigCam);
       testvideo = (VideoView) findViewById(R.id.Cam1);
       testvideo2 = (VideoView) findViewById(R.id.Cam2);
       testvideo3 = (VideoView) findViewById(R.id.Cam3);
       testvideo4 = (VideoView) findViewById(R.id.Cam4);

       // 들어오면 가장 큰 화면에 첫번째 영상 실행
        Uri video = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.video2);

        videoView.setVideoURI(video);
        videoView.requestFocus();
        videoView.start();



       // 아래쪽 4개화면에 각자 영상 실행

        // cam1
        Uri video2 = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.video2);

        testvideo.setVideoURI(video2);
        testvideo.requestFocus();
        testvideo.start();

        // cam2
        Uri video3 = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.testvideo);

        testvideo2.setVideoURI(video3);
        testvideo2.requestFocus();
        testvideo2.start();

        Uri video4 = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.video5);

        testvideo3.setVideoURI(video4);
        testvideo3.requestFocus();
        testvideo3.start();

        Uri video5 = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.video4);

        testvideo4.setVideoURI(video5);
        testvideo4.requestFocus();
        testvideo4.start();





        btn_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView videoView = (VideoView) findViewById(R.id.BigCam);

                Uri video = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.video2);

                videoView.setVideoURI(video);
                videoView.requestFocus();
                videoView.start();
            }
        });

        btn_cam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView videoView = (VideoView) findViewById(R.id.BigCam);

                Uri video = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.testvideo);

                videoView.setVideoURI(video);
                videoView.requestFocus();
                videoView.start();

            }
        });

        btn_cam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView videoView = (VideoView) findViewById(R.id.BigCam);

                Uri video = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.video5);

                videoView.setVideoURI(video);
                videoView.requestFocus();
                videoView.start();
            }
        });

        btn_cam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView videoView = (VideoView) findViewById(R.id.BigCam);

                Uri video = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.video4);

                videoView.setVideoURI(video);
                videoView.requestFocus();
                videoView.start();
            }
        });





    }
}


