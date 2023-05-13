package org.techtown.dang_guen.start_atcivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.dang_guen.fragment_start;
import org.techtown.dong_nae_life.R;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class user_make extends AppCompatActivity {

    EditText make_nickname_edittext;
    ImageButton make_select_image_button;
    ImageView make_image_view;
    Button make_user_main_menu;
    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    private SharedPreferences preferences;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_make);
        preferences = getSharedPreferences("현재유저", MODE_PRIVATE);

        // 레이아웃 연결
        make_nickname_edittext = findViewById(R.id.make_nickname_edittext);
        make_user_main_menu = findViewById(R.id.make_user_main_menu);
        make_image_view = findViewById(R.id.make_user_imageview);
        make_select_image_button = findViewById(R.id.make_select_image_button);

        //버튼눌러서 픽사베이 이미지 가지고 오는 부분
        make_select_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        make_user_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_user_info();
            }
        });



        /*
         *edittext에서 입력시 버튼활성화/비활성화
         * */
        make_nickname_edittext.addTextChangedListener(new TextWatcher() {
            //beforeTextChanged-입력하여 변화가 생기기 전에 처리하는 부분
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //onTextChanged-입력난에 변화가 있을시
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            //afterTextChanged-입력이 끝났을때
            //Editable-EditText를 편집이 가능하게 하는 클래스
            //setClickable
            //setBackgroundColor-버튼 컬러 설정
            public void afterTextChanged(Editable editable) {
                //인증번호 로컬에 저장

                if (editable.length() > 1) {
                    //글자수가 8개부터 버튼이 활성화가 되고 텍스트 컬러가 보이게 된다
                    //프로필 완성
                    make_user_main_menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 메소드 실행
                            insert_user_info();
                        }
                    });
                }
                else if(editable.length() == 1) {
                    make_user_main_menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(user_make.this, "2자리 이상 만들어주세요", Toast.LENGTH_SHORT).show();
                        }
                    });
                }     else if(editable.length() == 0) {
                    make_user_main_menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(user_make.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    } // onCreate 끝


    //-----------------------------------------------------------------------------------------------------
    // 서버로 데이터 전달
    private void insert_user_info() {
        String nick = make_nickname_edittext.getText().toString();
        String image = imageToString();

        System.out.println(image+"imageimageimage");

        //폰 데이터값
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");


        Log.e("닉네임값:", nick);
        Log.e("폰넘버:", phone);

        /*
         *SharedPreferences
         * 사전적 의미-공유(두사람 이상이 공동으로 사용)하는것을 선호(여러개 중에 특별히 종아함)
         *
         * 무엇인가? 앱 내부 로컬에 저장 또는 데이터를 파일로 저장을 한다
         *
         * 왜 사용하는가? 간단한 데이터를 저장 하며 사용하기 위해서
         *
         * 어떻게 사용하는가?
         * 데이터 저장하는 법
         * 1)preferences = getSharedPreferences("현재유저", MODE_PRIVATE);
         * -파일을 만든다
         *
         * 2) SharedPreferences.Editor editor = preferences.edit();
         * 데이터 편집기를 가지고 온다
         *
         * 3)editor.putString("user_nick", nick);
         * putString을 이용해서("키값",데이터 값)을 넣는다
         * */
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_nick", nick);
        editor.putString("user_number", phone);
        //항상 commit & apply 를 해주어야 저장이 된다.
        editor.apply();


        /**
         * 닉네임값이 입력안해도 만드는 코드를 안만들었다
         * */
        if(nick.equals("")){
            Toast.makeText(user_make.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else {
            Call<user_data> call = user_ApiClient.getApiClient().create(user_ApiInterface.class).user_make(nick, image, phone);
            call.enqueue(new Callback<user_data>() {
                @Override
                public void onResponse(Call<user_data> call, Response<user_data> response) {
                    String image = response.body().getImage();
                    //putString(KEY,VALUE)
                    editor.putString("user_image", image);
                    //항상 commit & apply 를 해주어야 저장이 된다.
                    editor.apply();
                    Intent intent = new Intent(user_make.this, fragment_start.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<user_data> call, Throwable t) {
                    Toast.makeText(user_make.this, "닉네임이 이미 존재합니다", Toast.LENGTH_SHORT).show();
                }
            });
        }
    } // performEdit메소드 끝


    //-----------------------------------------------------------------------------------------------------
    // 이미지 선택
    /*
     * 갤러리에 이미지 선택하는법
     * 사전적 의미-
     *
     * 무엇인가?
     *
     * 왜 사용하는가?
     *
     * 어떻게 사용하는가?
     * 1)<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     * -매니패스트에 먼저 설정을 한다 해석하면 말그래도 외부 저장소를 읽는다는 의미이다
     *
     * 2)intent.setType("image/*");
     * -이미지를 얻을때 원하는 타입(JPG,JPEG(사이즈를 줄일수 있는 파일),png)
     *
     * 3)intent.setAction(Intent.ACTION_GET_CONTENT);
     * -실제 이미지의 값을 얻는 메소드
     *
     * */
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }


    //-----------------------------------------------------------------------------------------------------
    // 이미지 받는곳
    /*
     *
     * 사전적 의미-
     *
     * 무엇인가?
     *
     * 왜 사용하는가?
     *
     * 어떻게 사용하는가?
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                Glide.with(user_make.this)
                        .load(bitmap)
                        .apply(new RequestOptions().circleCrop())
                        .into(make_image_view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //-----------------------------------------------------------------------------------------------------
    // 이미지 보내는곳
    /*
     *
     * 사전적 의미-
     *
     * 무엇인가?
     *
     * 왜 사용하는가?
     *
     * 어떻게 사용하는가?
     * */
    private String imageToString() {
        for (int i = 0; true; i++) {
            if(bitmap==null){
                        return null;
            }else{
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imgByte = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(imgByte, Base64.DEFAULT);
            }
        }
    }
}