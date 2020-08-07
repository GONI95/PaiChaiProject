package com.example.paichaiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paichaiproject.Gon.CategoryGonfile;
import com.example.paichaiproject.Kyo.IlkyoGoogleMap;
import com.example.paichaiproject.Kyo.Introduction;
import com.example.paichaiproject.Seok.Youngseok;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    // YouTubeBaseActivity 상속 / YouTubePlayer.OnInitializedListener 인터페이스 구현
    YouTubePlayerView youTubePlayerView;
    // YouTubePlayerView 객체 생성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        youTubePlayerView.initialize(String.valueOf(R.string.Api_ID),MainActivity.this);
        // initialize(k, l) -> 동영상을 재생, 제어를 위해 초기화를 진행하여 성공, 실패에 따라 Listener의 콜백 중 하나가 호출
        //============String.valueOf은 char배열을 문자열로 변환하는 메서드

        final TextView person1 = (TextView) findViewById(R.id.person1);
        final TextView person2 = (TextView) findViewById(R.id.person2);
        final TextView person3 = (TextView) findViewById(R.id.person3);
        final TextView person4 = (TextView) findViewById(R.id.person4);
        //해당 프래그먼트를 눌렀을 때 화면이 바뀌는 레이아웃

        person1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryGonfile.class);
                MainActivity.this.startActivity(intent);

            }
        });

        person2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IlkyoGoogleMap.class);
                MainActivity.this.startActivity(intent);

            }
        });

        person3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Youngseok.class);
                MainActivity.this.startActivity(intent);

            }
        });
        person4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Introduction.class);
                MainActivity.this.startActivity(intent);

            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        // 플레이어가 초기화될 때 호출되는 메소드
        if(!b) {
            youTubePlayer.loadVideo("rFZAQlToN0A");
            // youyube 에서 id를 입력하면 됩니다. (https://www.youtube.com/watch?v= "id ->" rFZAQlToN0A)
            //cuevideo 쓰니까 자동재생이 안되고 재생버튼을 눌러줘야함
            youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        // 플레이어가 초기화될 때 호출 되는 메소드
        Toast.makeText(MainActivity.this, R.string.Fail_Initialize, Toast.LENGTH_SHORT).show();
    }

    private long lastTimeBackPressed;
     public void onBackPressed() {
         // 제한 된 시간 내에 버튼을 뒤로가기 버튼을 두번 누르면 어플이 종료
         if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
         // System.currentTimeMillis() -> 1970년 1월1일 자정부터 현재까지 카운트된 시간을 ms 단위로 표시
         {
             finish();
             return;
         }
         lastTimeBackPressed = System.currentTimeMillis();
     }
}
