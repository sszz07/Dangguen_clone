package org.techtown.dang_guen.dong_nae_life;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.bumptech.glide.request.RequestOptions;

import org.techtown.dong_nae_life.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_comment_update extends AppCompatActivity {
    ImageView dong_nae_life_commet_update_image_imageview;
    EditText dong_nae_life_update_comment_content_edittext;
    ImageButton dong_nae_life_my_coment_cancle_imagebutton, dong_nae_life_comment_update_imagebutton;
    Button dong_nae_life_comment_update_button;
    private static final int IMG_REQUEST = 888;
    String image;
    Bitmap image_bitmap = null;
    String time;
    String update_comment_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_comment_update);

        //시간 객체
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        //Date 객체 사용
        Date date = new Date();
         time = simpleDateFormat.format(date);

        dong_nae_life_commet_update_image_imageview = (ImageView) findViewById(R.id.dong_nae_life_commet_update_image_imageview);
        dong_nae_life_update_comment_content_edittext = (EditText) findViewById(R.id.dong_nae_life_update_comment_content_edittext);
//        dong_nae_life_my_coment_cancle_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_my_coment_cancle_imagebutton);
        dong_nae_life_comment_update_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_comment_update_imagebutton);
        dong_nae_life_comment_update_button = (Button) findViewById(R.id.dong_nae_life_comment_update_button);


        Intent intent = getIntent();
        int update_idx = intent.getIntExtra("update_idx", 0);
        String comment_content = intent.getStringExtra("comment_content");
        String comment_image = intent.getStringExtra("comment_image");

        System.out.println(comment_content+"comment_contentcomment_contentcomment_content");

        try {
            byte[] arr = getIntent().getByteArrayExtra("comment_image_bitmap");
            image_bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        } catch (NullPointerException e) {

        }

        if (comment_image != null) {
            Glide.with(this)
                    .load("http://52.79.180.89/dang_guen" + comment_image)
                    .into(dong_nae_life_commet_update_image_imageview);
            dong_nae_life_update_comment_content_edittext.setText(comment_content);

        } else if (image_bitmap != null) {
            @SuppressLint("CutPasteId") ImageView BigImage = (ImageView) findViewById(R.id.dong_nae_life_commet_update_image_imageview);
            BigImage.setImageBitmap(image_bitmap);
            dong_nae_life_update_comment_content_edittext.setText(comment_content);
        } else {
            dong_nae_life_my_coment_cancle_imagebutton.setVisibility(View.GONE);
            dong_nae_life_commet_update_image_imageview.setVisibility(View.GONE);
            dong_nae_life_update_comment_content_edittext.setText(comment_content);
        }


        dong_nae_life_my_coment_cancle_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_null();
                dong_nae_life_comment_update_image(update_idx);
                dong_nae_life_my_coment_cancle_imagebutton.setVisibility(View.INVISIBLE);
                dong_nae_life_commet_update_image_imageview.setVisibility(View.INVISIBLE);
            }
        });


        dong_nae_life_comment_update_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        dong_nae_life_comment_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_comment_content = dong_nae_life_update_comment_content_edittext.getText().toString();

                if (image == null && update_comment_content.length() > 0) {
               System.out.println("여깅에 들어와야됨!!!!!");
                    dong_nae_life_comment_update(update_idx, null, update_comment_content, time);
                } else if (update_comment_content.equals("") || update_comment_content.length() == 0) {
                    dong_nae_life_comment_update(update_idx, image, null, time);
                }
                else {
                    dong_nae_life_comment_update(update_idx, image, update_comment_content, time);
                 }
            }
        });
    }


    private void dong_nae_life_comment_update(int idx, String image, String content, String time) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_comment_update(idx, image, content, time);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                onBackPressed();
            }
        });
    }


    private void dong_nae_life_comment_update_image(int idx) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_comment_update_image(idx,"image_canel");
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {

            }
        });
    }


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


    private String image_null() {
        return image = null;
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
                dong_nae_life_my_coment_cancle_imagebutton.setVisibility(View.VISIBLE);
                dong_nae_life_commet_update_image_imageview.setVisibility(View.VISIBLE);
                image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                Glide.with(dong_nae_life_comment_update.this).load(image_bitmap).into(dong_nae_life_commet_update_image_imageview);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        image = imageToString();

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
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgByte, Base64.DEFAULT);
        }
    }
}