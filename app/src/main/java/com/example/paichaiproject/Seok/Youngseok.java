package com.example.paichaiproject.Seok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.ViewFlipper;

import com.example.paichaiproject.R;

import static android.view.View.GONE;

public class Youngseok extends AppCompatActivity {
    //@@뷰플리퍼 생성
    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youngseok);


//@@이미지추가
        int images[] = {
                R.drawable.one,
                R.drawable.two,
                R.drawable.three
        };
//@@뷰플리퍼 생성 및 클릭시 행동
        v_flipper = findViewById(R.id.view);
        for(int image : images) {
            flipperImages(image);
        }
        v_flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//플리퍼 클릭시 목록으로가기
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pcu.ac.kr/www/01_intro/intro_0402.html"));
                startActivity(intent);
//플리퍼 클릭시 다음꺼 넘어가게하기
//v_flipper.showNext();
            }
        });


//!!하단메뉴
        final Button homeButton=(Button)findViewById(R.id.home);
        final Button noticeButton=(Button)findViewById(R.id.notice);
        final Button eatButton=(Button)findViewById(R.id.eat);
        final Button mapButton=(Button)findViewById(R.id.map);
        final Button guideButton=(Button)findViewById(R.id.guide);
        final TableLayout layout=(TableLayout) findViewById(R.id.frame);


        homeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                layout.setVisibility(GONE);
//버튼에 색주기
// homeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
// noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManger=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new HomeFragment());
                fragmentTransaction.commit();
            }
        });
//게시판 이동
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Youngseok.this, Notice.class);
                startActivity(intent);

            }
        });

        eatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                layout.setVisibility(GONE);
//버튼에 색주기
// homeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
// noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManger=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new EatFragment());
                fragmentTransaction.commit();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                layout.setVisibility(GONE);
//버튼에 색주기
// homeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
// noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManger=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new MapFragment());
                fragmentTransaction.commit();
            }
        });

        guideButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                layout.setVisibility(GONE);
//버튼에 색주기
// homeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
// noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManger=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new GuideFragment());
                fragmentTransaction.commit();
            }
        });


    }


    //@@뷰플리퍼를 이용하여 자동으로 이미지 넘기기
    private void flipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView); // 이미지 추가
        v_flipper.setFlipInterval(4000); // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_flipper.setAutoStart(true); // 자동 시작 유무 설정

// animation
        v_flipper.startFlipping();
// v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
// v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }


    //##중간메뉴 클릭시 사이트 연결
    public void onClick1(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pcu.ac.kr/www/index/index.html"));
//웹페이지 들어가기
        startActivity(intent);
//Intent 는 엑티비티 전환하기!
    }
    public void onClick2(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://guide.pcu.ac.kr/01-guide/guide-0101.html"));
//웹페이지 들어가기
        startActivity(intent);
//Intent 는 엑티비티 전환하기!
    }
    public void onClick3(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://guide.pcu.ac.kr/index/"));
//웹페이지 들어가기
        startActivity(intent);
//Intent 는 엑티비티 전환하기!
    }
    public void onClick4(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://course.pcu.ac.kr/login.php"));
//웹페이지 들어가기
        startActivity(intent);
//Intent 는 엑티비티 전환하기!
    }
    public void onClick5(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tis.pcu.ac.kr/projects/service/LaunchProject_EmulateIE8.jsp"));
//웹페이지 들어가기
        startActivity(intent);
//Intent 는 엑티비티 전환하기!
    }
    public void onClick6(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pcu.ac.kr/www/01_intro/intro_0601.html"));
//웹페이지 들어가기
        startActivity(intent);
//Intent 는 엑티비티 전환하기!
    }



}