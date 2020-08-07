package com.example.paichaiproject.Gon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.paichaiproject.R;
import com.google.android.material.tabs.TabLayout;


public class CategoryGonfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 액티비티 실행 시 화면을 세로로 고정
        setContentView(R.layout.activity_gonfile);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        // gonfile.xml 의 텝 레이아웃이랑 연결
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Lesson));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Weather));
        // 텝 레이아웃에 텝을 추가하는데 텍스트를 레슨 패션
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        // 탭의 배치 형식을 설정(가능한 많이 채우기 위해 사용)

        final ViewPager viewPager = (ViewPager) findViewById(R.id.gonTab_viewPager);
        // gonfile.xml 의 뷰 페이저랑 연결
        final GonTab_Adapter pageAdapter = new GonTab_Adapter(getSupportFragmentManager(), 2);
        // GonTab_Adapter를 객체로 생성(프래그먼트와 상호작용을 위해 프래그먼트를 가져옴, 카운트를 보내줌)
        viewPager.setAdapter(pageAdapter);
        // 퓨페이져에 pageAdapter를 추가

        // 각 리스너에서 해당 위치의 int 값을 매개변수로 가짐
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        // 탭메뉴를 클릭하면 해당 프래그먼트로 변경 - 동기화
        // 선택된 페이지의 상태 변경에 응답하기위한 콜백 인터페이스
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 스크롤 상태가 변경하면 해당 프래그먼트로 변경 - 동기화
    }
}
class GonTab_Adapter extends FragmentStatePagerAdapter {
    int Tabs;
    // 탭의 갯수를 의미
    public GonTab_Adapter(FragmentManager fm, int Number) {
        super(fm);
        this.Tabs = Number;
    }
    //텝으로 프래그먼트 보는거
    @NonNull
    @Override
    public Fragment getItem(int position) {
        // 지정된 위치와 연관된 데이터 항목을 가져옴
        switch (position) {
            case 0:
                Gon_lesson gon_lesson = new Gon_lesson();
                return gon_lesson;
            case 1:
                Gon_weather gon_weather = new Gon_weather();
                return gon_weather;
        }

        return null;
    }

    @Override
    public int getCount() {
        // 항목의 수를 리턴
        return Tabs;
    }
}
