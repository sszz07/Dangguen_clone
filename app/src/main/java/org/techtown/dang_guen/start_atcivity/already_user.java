package org.techtown.dang_guen.start_atcivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.dang_guen.fragment_start;
import org.techtown.dong_nae_life.R;



public class already_user extends AppCompatActivity {
    Dialog dilaog;

    Button remake_user_dialog_button, remake_user_dialog_cancel_button, already_button, remake_user_button;
    ImageButton imageButton;
    TextView already_user_phone;
    ImageView already_user_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_user);


        dilaog = new Dialog(already_user.this);       // Dialog 초기화
        dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog.setContentView(R.layout.remake_user_dialog);


        //버튼id
        already_button = (Button) findViewById(R.id.already_user_not_make_button);
        already_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(already_user.this, fragment_start.class);
                startActivity(intent);
            }
        });


        remake_user_button = (Button) findViewById(R.id.already_user_remake_button);
        already_user_imageview = (ImageView) findViewById(R.id.already_user_imageview);
        //다이얼로그 버튼id
        remake_user_dialog_button = (Button) findViewById(R.id.remake_user_dialog_button);
        remake_user_dialog_cancel_button = (Button) findViewById(R.id.remake_user_dialog_cancel_button);

        //텍스트뷰id
        already_user_phone = findViewById(R.id.already_user_phone);

        //이미지버튼
        imageButton = (ImageButton) findViewById(R.id.phone_certification_back_button);


//phone_certification클래스에서 받아온것
        Intent intent = getIntent();
        String phone_number = intent.getStringExtra("phone_number");
        String user_image = intent.getStringExtra("user_image");


        already_user_phone.setText(phone_number);
        Glide.with(getApplicationContext())
                .load("http://52.79.180.89/dang_guen" + user_image)// image url
                .apply(new RequestOptions().circleCrop())
                .into(already_user_imageview);

        // 버튼: 커스텀 다이얼로그 띄우기
        remake_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(); // 아래 showDialog01() 함수 호출
            }
        });
    }


    public void dialog() {
        dilaog.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // 아니오 버튼
        Button noBtn = dilaog.findViewById(R.id.remake_user_dialog_cancel_button);
        noBtn.findViewById(R.id.remake_user_dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog.dismiss(); // 다이얼로그 닫기
            }
        });
//         네 버튼
        dilaog.findViewById(R.id.remake_user_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(already_user.this, user_make.class);
                startActivity(intent);
            }
        });
    }
}