package org.techtown.dang_guen.dong_nae_life;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class dong_nae_life_make extends AppCompatActivity {
    String 클래스 = "dong_nae_life_make";
    private Button make_button;
    EditText make_content_edittext;
    ImageButton select_image_imagebutton;
    TextView back_button_textview;
    private RecyclerView dong_nae_life_make_recyclerview;
    private dong_nae_life_make_select_image_adapter select_image_adpater;
    private ArrayList<Uri> select_images = new ArrayList<>();
    int CODE_ALBUM_REQUEST = 111;
    TextView dong_nae_life_make_image_count;
    String select_subject, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_make);

        make_button = (Button) findViewById(R.id.dong_nae_life_make_button);

        back_button_textview = findViewById(R.id.dong_nae_life_back_button_textview);

        dong_nae_life_make_image_count = findViewById(R.id.dong_nae_life_make_image_count);

        make_content_edittext = (EditText) findViewById(R.id.dong_nae_life_make_content_edittext);

        select_image_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_make_imagebutton);

        //시간값 계산 코드
        long curTime = System.currentTimeMillis();


        Spinner spinner_field = (Spinner) findViewById(R.id.dong_nae_life_spinner);
        String[] dong_nae_life_spinner = getResources().getStringArray(R.array.spinner_array);


        /**
         * Attempt to invoke virtual method 'java.lang.String java.lang.Object.toString()' on a null object reference
         * */


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, dong_nae_life_spinner);



        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner_field.setAdapter(adapter);

        spinner_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spinner_field.getSelectedItemPosition() > 0) {
                    select_subject = spinner_field.getItemAtPosition(i).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

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
        Intent intent = getIntent();
        String user_image = intent.getStringExtra("user_image");
        System.out.println(user_image + 클래스 + "동네메인에서 인텐트 한 이미지값 확인");

        dong_nae_life_make_recyclerview = findViewById(R.id.dong_nae_life_make_recyclerview);

        dong_nae_life_make_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        int image_count = select_images.size();
        String String_image_count = Integer.toString(image_count);


        dong_nae_life_make_image_count.setText(String_image_count);

        select_image_adpater = new dong_nae_life_make_select_image_adapter(this, select_images);

        select_image_adpater.setOnItemClickListener(new dong_nae_life_make_select_image_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, String image_count) {
                dong_nae_life_make_image_count.setText(image_count);
            }
        });
        dong_nae_life_make_recyclerview.setAdapter(select_image_adpater);


        //시간 객체
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        //Date 객체 사용
        Date date = new Date();
        String time = simpleDateFormat.format(date);

        //버튼 클릭했을 때 갤러리 연다
        select_image_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, CODE_ALBUM_REQUEST);
            }
        });


        make_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                info_insert(select_subject, user_image, user_nick, phone, curTime,time);

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
                        String imagePath = dong_nae_life_image_Utils.getPath(dong_nae_life_make.this, image);
                        select_images.add(Uri.parse(imagePath));
                    }
                }
            }

            int image_count = select_images.size();

            String String_image_count = Integer.toString(image_count);

            System.out.println(String_image_count + "이미지 갯수 확인");

            dong_nae_life_make_image_count.setText(String_image_count);

            dong_nae_life_make_recyclerview.setAdapter(select_image_adpater);
        }
    }


    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    private void info_insert(String subject, String user_image, String user_nick, String phone, long present_time,String time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : select_images) {
            list.add(prepairFiles("file[]", uri));
        }
        content = make_content_edittext.getText().toString();
        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_info_insert(subject, content, user_image, user_nick, phone, present_time,list,time);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                //response.body()가 null이 나온 이유는 서버에서 json_encode를 해주지 않아서 이다 그리고 mysqli_query($con, $sql) 연결이 되어있는지 확인하기
                if (response.isSuccessful() && response.body() != null) {
                    dong_nae_life_info_subject_select(content, time);
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }


    //게시판 번호를 가져옴->다중이미지를 셀렉트하기 위해서
    private void dong_nae_life_info_subject_select(String content, String time) {
        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_info_content_select(content);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    /**
                     * 어떤 에러인지 확인하기
                     * End of input at line 1 column 1 path $
                     *
                     * */
                    int dong_nae_life_board_number = response.body().getIdx();
                    dong_nae_life_multiimage_insert(dong_nae_life_board_number, time);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("dong_nae_life_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }


    //이미지를 서버에 보내는 메소드
    public void dong_nae_life_multiimage_insert(int dong_nae_life_board_number, String time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : select_images) {
            list.add(prepairFiles("file[]", uri));
        }
        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_multiimage_insert(dong_nae_life_board_number, list, time);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(Call<dong_nae_life_data> call, Response<dong_nae_life_data> response) {

            }

            @Override
            public void onFailure(Call<dong_nae_life_data> call, Throwable t) {

            }
        });
    }


}