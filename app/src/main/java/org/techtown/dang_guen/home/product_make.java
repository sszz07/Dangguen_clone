package org.techtown.dang_guen.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.dong_nae_life.R;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_make extends AppCompatActivity {

    private Button product_category_button, make_button;
    EditText product_make_subject_edittext, product_make_content_edittext, product_make_price_edittext;
    CheckBox product_make_price_checkbox;
    ImageButton product_make_image_imagebutton;
    TextView back_button_textview;
    private RecyclerView product_make_image_recyclerview;
    private product_make_select_image_adpater select_image_adpater;
    private ArrayList<Uri> product_make_images = new ArrayList<>();
    int CODE_ALBUM_REQUEST = 111;
    TextView product_make_image_count;
    String subject, content, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_make);
        product_category_button = (Button) findViewById(R.id.product_make_category_button);
        make_button = (Button) findViewById(R.id.make_button);

        long present_time = System.currentTimeMillis();

        back_button_textview = findViewById(R.id.back_button_textview);


        product_make_image_imagebutton = (ImageButton) findViewById(R.id.product_make_image_imagebutton);


        product_make_subject_edittext = (EditText) findViewById(R.id.product_make_subject_edittext);
        product_make_content_edittext = (EditText) findViewById(R.id.product_make_content_edittext);
        product_make_price_edittext = (EditText) findViewById(R.id.product_make_price_edittext);

        product_make_image_count = findViewById(R.id.product_make_image_count);

        /*
        *SharedPreferences
        * 사전적 의미-공유(두사람 이상이 공동으로 사용)하는것을 선호(여러개 중에 특별히 종아함)
        *
        * 무엇인가? 앱 내부 로컬에 저장 또는 데이터를 파일로 저장을 한다
        *
        * 왜 사용하는가? 간단한 데이터를 저장 하며 사용하기 위해서
        *
        * 어떻게 사용하는가?
        * 데이터를 get하는법
        *
        * 1)SharedPreferences preferences;
        * -필드에 객체를 생성해 준다
        *
        * 2) preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        * -getSharedPreferences("파일 명", MODE_PRIVATE(생성한 앱에서만 사용이 가능))
        * SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        * -매개변수에 현재 파일명 입력하고 가지고 오고
        * String user_nick = preferences.getString("user_nick", "");
        * -파일에 저장된 데이터 key값을 넣는다
        * */
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String user_image = preferences.getString("user_image", "");
        String user_nick = preferences.getString("user_nick", "");
        String phone = preferences.getString("user_number", "");


        /*
         * Intent란?
         * 사전적 의미-강한 관심(마음이 끌려감),몰두(일에 정신을 다함)
         *
         * 무엇인가? 4대 컴포넌트(여러가지 구성을 하나로 만드는것)를 통신(소식을 전하다,자료를 보내다)하는것
         * 1)명시적 인텐트
         * -
         *
         * 2)암시적 인텐트
         *
         * 왜 사용하는가? 각 액티비티에 통신을 하기 위해서
         *
         * 어떻게 사용하는가?
         * 인텐트로 데이터를 받는법
         * 1)Intent intent = getIntent();
         * -getIntent() 전 액티비티의 데이터를 받기위해 호출
         *
         * 2) String user_image = intent.getStringExtra("user_image");
         * intent.getStringExtra("키값")전 액티비티의 데이터를 받아와서 String 형태의 값을 받는다는 의ㅣ
         * */
        product_make_image_recyclerview = findViewById(R.id.product_make_image_recyclerview);
        product_make_image_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        int product_make_image_count1 = product_make_images.size();
        String to = Integer.toString(product_make_image_count1);

        product_make_image_count.setText(to);

        select_image_adpater = new product_make_select_image_adpater(this, product_make_images);

        select_image_adpater.setOnItemClickListener(new product_make_select_image_adpater.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, String image_count) {
                product_make_image_count.setText(image_count);
            }
        });
        product_make_image_recyclerview.setAdapter(select_image_adpater);




        //시간 객체
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Date 객체 사용
        Date date = new Date();
        String time = simpleDateFormat.format(date);

        //버튼 클릭했을 때 갤러리 연다
        product_make_image_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, CODE_ALBUM_REQUEST);
            }
        });

        String hits = "1", chatting = "2", user_like = "2";

        make_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //게시판 인서트 매개변수(subject,content,hints,user_image,nick,category,chatting,like,price)
                product_info_insert(hits, user_image, user_nick, chatting, user_like, phone, time,present_time);


            }
        });

        product_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), product_category.class);
                startActivityForResult(intent, 101);
            }
        });
    }


    //앨범 이미지 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //갤러리 이미지 가져오기
        if (requestCode == CODE_ALBUM_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                if (clipData.getItemCount() >= 11) { // 10개 초과하여 이미지를 선택한 경우
                    Toast.makeText(this, "사진은 10장까지 입니다", Toast.LENGTH_SHORT).show();
                    return;
                } else if (clipData.getItemCount() >= 0 && clipData.getItemCount() <= 10) { //1개초과  10개 이하의 이미지선택한 경우
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        //시간 객체
                        Uri image = data.getClipData().getItemAt(i).getUri();
                        String imagePath = product_image_Utils.getPath(product_make.this, image);
                        product_make_images.add(Uri.parse(imagePath));
                    }
                }
            }

            int product_make_image_count1 = product_make_images.size();

            String to = Integer.toString(product_make_image_count1);

            System.out.println(product_make_image_count1 + "이미지 갯수 확인");

            product_make_image_count.setText(to);

            product_make_image_recyclerview.setAdapter(select_image_adpater);
        }
        if (requestCode == 101) {
            String product_category_digital = data.getStringExtra("product_category_digital");
            String product_category_man_cloth = data.getStringExtra("man_cloth");
            if (product_category_digital != null) {
                product_category_button.setText(product_category_digital);
            } else if (product_category_man_cloth != null) {
                product_category_button.setText(product_category_man_cloth);
            }
        }
    } //end of onActivityResult


    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    private void product_info_insert(String hits, String user_image, String user_nick, String chatting, String user_like, String phone, String time,long present_time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : product_make_images) {
            list.add(prepairFiles("file[]", uri));
        }

        String category = product_category_button.getText().toString();
        subject = product_make_subject_edittext.getText().toString();
        content = product_make_content_edittext.getText().toString();
        price = product_make_price_edittext.getText().toString();


        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_info_insert(subject, content, hits, user_image, user_nick, category, chatting, user_like, price, phone, time, present_time,list);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                //response.body()가 null이 나온 이유는 서버에서 json_encode를 해주지 않아서 이다 그리고 mysqli_query($con, $sql) 연결이 되어있는지 확인하기
                if (response.isSuccessful() && response.body() != null) {
                    product_info_subject_select(subject, time);
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }


    //게시판 번호를 가져옴->다중이미지를 셀렉트하기 위해서
    private void product_info_subject_select(String subject, String time) {
        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_info_subject_select(subject);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    /**
                     * 어떤 에러인지 확인하기
                     * End of input at line 1 column 1 path $
                     *
                     * */
                    int product_number = response.body().getIdx();
                    product_multiimage_insert(product_number, time);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }


    //이미지를 서버에 보내는 메소드
    public void product_multiimage_insert(int product_number, String time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : product_make_images) {
            list.add(prepairFiles("file[]", uri));
        }

        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_multiimage_insert(product_number, list, time);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(Call<product_data> call, Response<product_data> response) {
                System.out.println("동작을 하는지 확인");
            }

            @Override
            public void onFailure(Call<product_data> call, Throwable t) {

            }
        });
    }
}