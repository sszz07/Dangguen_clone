package org.techtown.dang_guen.start_atcivity;

/*
import
사전적 의미-수입하다(수입:다른곳에서 상품이나 물품을 받아옴)
무엇인가? 다른 패키지에 있는 클래스를 사용하기 위해서 만듬
예를 들어 AppCompatActivity의 패키지는 package androidx.appcompat.app;인데 매번 패키지를 붙이면 힘들어서 붙인것
*주의 사항* 클래스명이 겹치면 사용할수가 없다!!!!!!
* */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.dong_nae_life.R;
import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    Button start_button;
    TextView already_user_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      /*
     findViewById(R.id.)-R은 리소스(레이아웃에 있는 모든 뷰를 말한다)
      레이아웃에서 아이디값을 찾다

      무엇인가? 레이아웃에 있는 버튼 이미지버튼등등 객체를 이벤트(반응)를 받고 싶으면 메소드가 필요하다
     * */

        start_button = (Button) findViewById(R.id.main_activity_시작하기버튼);
        already_user_button = (TextView) findViewById(R.id.already_user_button);


            /*
     setOnClickListener
     리스너란?어떤 이벤트의 발생 여부에 귀를 기울인다라는 뜻
     이벤트를 발생 시킬 버튼을 함수로 구현하는것뿐
      * */
        start_button.setOnClickListener(new View.OnClickListener() {

            /*
     onClick
     무엇인가? 클릭을 할수있는 메소드
     * */
            @Override
            public void onClick(View view) {
                   /*
     Intent-강한 관심,몰두하는/~을 하려고 하다,생각하다
      * */

                Intent intent = new Intent(MainActivity.this, phone_certification.class);

                   /*
     startActivity
      * */
                startActivity(intent);
            }
        });

        already_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, phone_certification.class);
                intent.putExtra("이미존재함", "이미존재");
                startActivity(intent);
            }
        });
    }
}