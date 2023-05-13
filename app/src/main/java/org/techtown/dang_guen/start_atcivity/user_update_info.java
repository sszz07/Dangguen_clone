package org.techtown.dang_guen.start_atcivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;

import org.techtown.dang_guen.home.product_menu;
import org.techtown.dong_nae_life.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class user_update_info extends AppCompatActivity {

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    private SharedPreferences preferences;
    ImageView update_imageview;
    EditText update_nick_edittext;
    Button user_update_button;
    ImageButton update_select_image_button;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);



        /**
         * 여기는 다시 봐야 됨
         * */

        //이미지 뷰
        update_imageview = findViewById(R.id.update_image_view);

        //닉네임 입력
//        update_nick_edittext = findViewById(R.id.update_user_nick);


//        user_update_button = findViewById(R.id.user_update_button);

        //이미지 선택버튼
//        update_select_image_button = (ImageButton) findViewById(R.id.update_select_image_button);
        preferences = getSharedPreferences("현재 유저", MODE_PRIVATE);

        //현재 이미지,닉네임 값 서버에서 얻기
        server_get_user_info();


        //이미지 갤러리 열기
        update_select_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        user_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_user_info();
                Intent intent = new Intent(user_update_info.this, product_menu.class);
                startActivity(intent);
            }
        });

    }


    //-----------------------------------------------------------------------------------------------------
    // 서버로 데이터 전달
    private void update_user_info() {
        String nick = update_nick_edittext.getText().toString();
        String image = imageToString();


        Log.e("닉네임값:", nick);
        Log.e("이미지값:", image);

        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String phone = preferences.getString("user_number", "");
        System.out.println(phone+"폰값 확인");

        //시간 객체
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Date 객체 사용
        Date date = new Date();
        String time = simpleDateFormat.format(date);


        Call<user_data> call = user_ApiClient.getApiClient().create(user_ApiInterface.class).user_update(nick, image, phone,time);
        call.enqueue(new Callback<user_data>() {
            @Override
            public void onResponse(Call<user_data> call, Response<user_data> response) {


            }

            @Override
            public void onFailure(Call<user_data> call, Throwable t) {

            }
        });
    } // performEdit메소드 끝


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
                update_imageview.setImageBitmap(bitmap);
                update_imageview.setVisibility(View.VISIBLE);
                update_imageview.setEnabled(false);
                update_select_image_button.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------
    // 이미지 보내는곳
    private String imageToString() {
        for (int i = 0; true; i++) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgByte, Base64.DEFAULT);
        }
    }


    //-----------------------------------------------------------------------------------------------------
    //디비에 있는 내 프로필값을 가져오는곳
    private void server_get_user_info() {
        //현재 사용하고있는 유저를 가지고 오기 위해서
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저 = preferences.getString("user_number", "");

        user_ApiInterface user_ApiInterface = user_ApiClient.getApiClient().create(user_ApiInterface.class);
        Call<user_data> call = user_ApiInterface.user_select(현재유저);
        call.enqueue(new Callback<user_data>() {
            @Override
            public void onResponse(@NonNull Call<user_data> call, @NonNull Response<user_data> response) {
                String user_nick = response.body().getNick();
                String user_image = response.body().getImage();
                System.out.println(user_nick+"확인");
                Glide.with(getApplicationContext())
                        .load("http://52.79.180.89/dang_guen_user_info/" + user_image)// image url
                        .into(update_imageview);

                update_nick_edittext.setText(user_nick);
            }

            @Override
            public void onFailure(Call<user_data> call, Throwable t) {

            }
        });
    }
}