
package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        // 생성한 비디오뷰를 bind
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        // 비디오뷰를 커스텀하기 위해서 미디어컨트롤러 객체 생성
        // 안드로이드 res폴더에 raw폴더를 생성 후 재생할 동영상파일을 넣습니다.
        // 경로에 주의할 것
        // 실제 모바일에서 테스트 할 것
        // 위 두가지를 대충 넘겼다가 많은 시간을 허비했다. ㅜㅜ...
        Uri video = Uri.parse("android.resource://" + getPackageName()+ "/"+R.raw.testvideo);
        /*
외부파일의 경우
Uri video = Uri.parse("http://해당 url/mp4_file_name.mp4") 와 같이 사용한다.
*/

        //비디오뷰에 재생할 동영상주소를 연결
        videoView.setVideoURI(video);
        //비디오뷰를 포커스하도록 지정
        videoView.requestFocus();
        //동영상 재생
        videoView.start();



    }
}


