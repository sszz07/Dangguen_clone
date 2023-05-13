package org.techtown.dang_guen.my_dang_guen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.dang_guen.start_atcivity.user_ApiClient;
import org.techtown.dang_guen.start_atcivity.user_ApiInterface;
import org.techtown.dang_guen.start_atcivity.user_data;
import org.techtown.dang_guen.start_atcivity.user_make;
import org.techtown.dong_nae_life.R;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile_update extends AppCompatActivity {

    ImageView profile_update_image_view;
    EditText profile_update_nick_edittext;
    Button profile_update_update_button, profile_update_cancle;
    String nick_name, image;
    ImageButton profile_update_image_select_button;
    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        profile_update_image_select_button = (ImageButton) findViewById(R.id.update_selewct_image_button);
        profile_update_image_view = (ImageView) findViewById(R.id.update_image_view);
        profile_update_nick_edittext = (EditText) findViewById(R.id.update_user_nick_edittext);
        profile_update_update_button = (Button) findViewById(R.id.profile_update_button);
        profile_update_cancle = (Button) findViewById(R.id.profile_update_cancle);

        Intent intent = getIntent();
        //상품등록클래스에서 가져온 유어값
        nick_name = intent.getStringExtra("setting_user_nick");
        image = intent.getStringExtra("setting_user_image");

        System.out.println(nick_name + "마이당근 닉네임 인텐트 값");
        System.out.println(image + "마이당근 이미지 인텐트 값");


        if(image != null){
            Glide.with(getApplicationContext())
                    .load("http://52.79.180.89/dang_guen" + image)// image url
                    .apply(new RequestOptions().circleCrop())
                    .into(profile_update_image_view);
        }


        profile_update_nick_edittext.setText(nick_name);

        //이미지 갤러리 열기
        profile_update_image_select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        profile_update_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //수정하기
        profile_update_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_update_user_data();
            }
        });


    }

    //-----------------------------------------------------------------------------------------------------
    // 이미지 선택
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }


    //-----------------------------------------------------------------------------------------------------
    // 이미지 받는곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                Glide.with(profile_update.this)
                        .load(bitmap)
                        .apply(new RequestOptions().circleCrop())
                        .into(profile_update_image_view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------
    // 이미지 보내는곳
    private String imageToString() {
        for (int i = 0; true; i++) {
            if(bitmap==null){
                if(image != null){
                    System.out.println("여기인가?111");
                    return image;
                }else{
                    System.out.println("여기인가?222");
                    return null;
                }
            }else{
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imgByte = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(imgByte, Base64.DEFAULT);
            }
        }
    }


    //-----------------------------------------------------------------------------------------------------
    // 서버로 데이터 전달
    private void profile_update_user_data() {
        String nick = profile_update_nick_edittext.getText().toString();
        image = imageToString();

        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String phone = preferences.getString("user_number", "");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_nick", nick);
        editor.apply();

        //시간 객체
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Date 객체 사용
        Date date = new Date();
        String time = simpleDateFormat.format(date);


        Call<user_data> call = user_ApiClient.getApiClient().create(user_ApiInterface.class).user_update(nick, image, phone, time);
        call.enqueue(new Callback<user_data>() {
            @Override
            public void onResponse(Call<user_data> call, Response<user_data> response) {
               onBackPressed();
            }

            @Override
            public void onFailure(Call<user_data> call, Throwable t) {

            }
        });
    }
}