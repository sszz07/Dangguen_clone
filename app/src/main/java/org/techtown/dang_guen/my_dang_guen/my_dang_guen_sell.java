package org.techtown.dang_guen.my_dang_guen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.techtown.dong_nae_life.R;

public class my_dang_guen_sell extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dang_guen_sell);


/*
   ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
    역할?레이아웃에 있는 뷰페이저객체를 가져온다
    책임?뷰페이저 객체를 생성을 해서 뷰페이저가 보일수 있게 해야한다
    협력?viewPager.setAdapter(adapter);
    1.PagerAdapter에서 만들어진 어댑터 객체를 뷰페이저 레이아웃 객체에 넣어준다
    */
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        /*
  PagerAdapter adapter = new PageAdapter(getSupportFragmentManager());
    역할?현재 액티비티에서 프래그먼트를 사용하기 위한 어댑터 객체를 만들어 준다
    책임?프래그먼트를 사용할수 있는 PagerAdapter의 객체를 생성을 해준다
    협력?viewPager.setAdapter(adapter);
    1.만든 객체를 setAdapter에 적용을 해준다
    */
        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager());

        /*
   viewPager.setAdapter(adapter);
    역할?현재 액티비티에서 어댑터와 연결을 해준다
    책임?현재 액티비티에서 뷰페이저에 어댑터를 사용할수있게 해줘야 한다
    협력?PagerAdapter adapter = new PageAdapter(getSupportFragmentManager());
    1.만들어진 PagerAdapter 객체와 뷰페이저 객체를 연결을 해준다
    */
        viewPager.setAdapter(adapter);

        /*
   TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    역할?상단에 현재 위치를 알려주는 탭레이아웃이다
    책임?상단에 현재 위치를 알려주기 위해서 메인에 있는 객체를 가져온다
    협력?tabLayout.setupWithViewPager(viewPager);
    1.탭레이아웃 객체를 만들어서 어댑터와 연결된 뷰페이저와 사용할수있게 해준다
    */
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        /*
   tabLayout.setupWithViewPager(viewPager);
    역할?탭레이아웃으로 보여주기 위해 뷰페이저와 준비하는 메소드 이다
    책임?옆으로 스와이프 할때 뷰페이저는 준비를 해야 한다
    협력? viewPager.setAdapter(adapter);
    1.뷰페이저 객체와 함께 데이터 준비를 한다
    */
        tabLayout.setupWithViewPager(viewPager);
    }
}