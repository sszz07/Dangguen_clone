package org.techtown.dang_guen.dong_nae_life;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dang_guen.home.product_update_image_adapter;
import org.techtown.dong_nae_life.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_update extends AppCompatActivity {
    String 클래스 = "dong_nae_life_update";
    private Button update_button;
    EditText content_edittext;
    int idx;
    String content, select_subject;
    ImageButton update_image_imagebutton;
    TextView dong_nae_life_update_image_count, dong_nae_life_update_image_count_10;
    private dong_nae_life_make_select_image_adapter select_image_adpater;
    private ArrayList<Uri> update_images = new ArrayList<>();
    List<dong_nae_life_data> list = new ArrayList<>();
    int CODE_ALBUM_REQUEST = 111;
    int image_10개초과 = 10;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_update);


        content_edittext = (EditText) findViewById(R.id.dong_nae_life_update_content_edittext);
        update_button = (Button) findViewById(R.id.dong_nae_life_update_button);
        update_image_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_update_imagebutton);
        dong_nae_life_update_image_count = (TextView) findViewById(R.id.dong_nae_life_update_image_count);
        dong_nae_life_update_image_count_10 = (TextView) findViewById(R.id.dong_nae_life_update_image_count_10);
        recyclerView = findViewById(R.id.dong_nae_life_update_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //
        dong_nae_life_update_image_count.setVisibility(View.INVISIBLE);
        dong_nae_life_update_image_count_10.setVisibility(View.INVISIBLE);

        //동네생활 my_select에서 선택한 클래스에서 넘어온 데이터값
        Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        content = intent.getStringExtra("content");
        select_subject = intent.getStringExtra("subject");
        content_edittext.setText(content);
        System.out.println(select_subject + 클래스);

        //이미지 리사이클러뷰에 보이게 하기
        dong_nae_life_update_get_image(idx);


        //스피너
        Spinner spinner_field = (Spinner) findViewById(R.id.dong_nae_life_spinner_update);
        String[] dong_nae_life_spinner = getResources().getStringArray(R.array.spinner_array_update);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, dong_nae_life_spinner);


        /**
         * 스피너에 값이 안들어가는 이유?
         * setAdapter에는
         * public void setAdapter(SpinnerAdapter adapter) {
         *         throw new RuntimeException("Stub!");
         *     }
         *
         *     스피너 어댑터만 파라미터로 받는다
         * */

        /*해결 방법
         *  spinner_field.setSelection(change_age_subject(spinner_field, "동네질문"));
         * setSelection()
         * 사전적 의미
         * set-위치하다,놓다
         *
         * selection-선택
         *
         * 무엇인가?
         * 리스트에서 원하는 데이터값을 선택
         *
         * 왜 사용하는가?
         * 원하는 데이터값을 선택하기 위해서
         *
         * 어떻게 사용하는가?
         * 반복문을 이용한 방법
         * 스피너id값.setSelection(change_age_subject(스피너 id값, "리스트에 있는 String값 선택"))
         *
         * 스피너id값.setSelection(스피너 리스트에 int값으로 넣어서 데이터 뽑음음
         * change_age_subject
         * */
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_field.setAdapter(adapter);

        if (spinner_field.getItemAtPosition(0).toString().equals(select_subject)) {
            spinner_field.setSelection(0);
        } else if (spinner_field.getItemAtPosition(1).toString().equals(select_subject)) {
            spinner_field.setSelection(1);
        } else if (spinner_field.getItemAtPosition(2).toString().equals(select_subject)) {
            spinner_field.setSelection(2);
        } else if (spinner_field.getItemAtPosition(3).toString().equals(select_subject)) {
            spinner_field.setSelection(3);
        } else if (spinner_field.getItemAtPosition(4).toString().equals(select_subject)) {
            spinner_field.setSelection(4);
        } else if (spinner_field.getItemAtPosition(5).toString().equals(select_subject)) {
            spinner_field.setSelection(5);
        } else if (spinner_field.getItemAtPosition(6).toString().equals(select_subject)) {
            spinner_field.setSelection(6);
        } else if (spinner_field.getItemAtPosition(7).toString().equals(select_subject)) {
            spinner_field.setSelection(7);
        } else if (spinner_field.getItemAtPosition(8).toString().equals(select_subject)) {
            spinner_field.setSelection(8);
        }


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






        int dong_nae_life_update_count = update_images.size();
        String count_change = Integer.toString(dong_nae_life_update_count);


        if (dong_nae_life_update_count != 0) {
            dong_nae_life_update_image_count.setVisibility(View.VISIBLE);
            dong_nae_life_update_image_count_10.setVisibility(View.VISIBLE);
            dong_nae_life_update_image_count.setText(count_change);
        } else if (dong_nae_life_update_count > 10) {
            Toast toast = Toast.makeText(this, "사진이 10개 초과가 되었습니다", Toast.LENGTH_SHORT);
            toast.show();
            dong_nae_life_update_image_count.setVisibility(View.VISIBLE);
            dong_nae_life_update_image_count_10.setVisibility(View.VISIBLE);
            dong_nae_life_update_image_count.setText(image_10개초과);
        }


        select_image_adpater = new dong_nae_life_make_select_image_adapter(this, update_images);
        select_image_adpater.setOnItemClickListener(new dong_nae_life_make_select_image_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, String image_count) {
                dong_nae_life_update_image_count.setText(image_count);
            }
        });

        recyclerView.setAdapter(select_image_adpater);


        //시간 객체
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Date 객체 사용
        Date date = new Date();
        String time = simpleDateFormat.format(date);

        //수정버튼을 눌렀을때
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dong_nae_life_update_image(idx, time);
                dong_nae_life_update_info(idx, time);
            }
        });


        //이미지 업데이트 버튼
        update_image_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, CODE_ALBUM_REQUEST);
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
                if (clipData.getItemCount() > 10) { // 10개 초과하여 이미지를 선택한 경우
                    Toast toast = Toast.makeText(this, "사진이 10개 초과가 되었습니다", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                } else if (clipData.getItemCount() >= 0 && clipData.getItemCount() <= 10) { //1개초과  11개 미만의 이미지선택한 경우
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri image = data.getClipData().getItemAt(i).getUri();
                        String imagePath = dong_nae_life_image_Utils.getPath(dong_nae_life_update.this, image);
                        if (update_images.size() >= 10) {
                            Toast toast = Toast.makeText(this, "사진이 10개 초과가 되었습니다", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        } else{
                            update_images.size();
                            update_images.add(Uri.parse(imagePath));
                        }
                    }
                }
            }


            dong_nae_life_update_image_count.setVisibility(View.VISIBLE);
            dong_nae_life_update_image_count_10.setVisibility(View.VISIBLE);


            int update_image_count = update_images.size();

            if (update_image_count <= 10) {
                String to = Integer.toString(update_image_count);
                dong_nae_life_update_image_count.setText(to);
                recyclerView.setAdapter(select_image_adpater);
            } else {
                String to = "10";
                dong_nae_life_update_image_count.setText(to);
                recyclerView.setAdapter(select_image_adpater);
            }

        }

    } //end of onActivityResult


    //이미지 업데이트
    public void dong_nae_life_update_image(int idx, String time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : update_images) {
            list.add(prepairFiles("file[]", uri));
        }

        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_multiimage_update(idx,time,list);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(Call<dong_nae_life_data> call, Response<dong_nae_life_data> response) {

            }

            @Override
            public void onFailure(Call<dong_nae_life_data> call, Throwable t) {

            }
        });
    }

    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }


    //텍스트 업데이트
    private void dong_nae_life_update_info(int idx, String time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : update_images) {
            list.add(prepairFiles("file[]", uri));
        }

        String update_content = content_edittext.getText().toString();


        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_info_update(idx,select_subject,list,time,update_content);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {


            }

            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("updatePerson()", "에러 : " + t.getMessage());
                onBackPressed();
                /**
                 * 레이아웃에 textview id값을 넣으면 나오는 에러코드
                 * androidx.appcompat.widget.AppCompatTextView cannot be cast to android.view.ViewGroup
                 * */
            }
        });
    }


    //이미지 서버에서 get하기
    private void dong_nae_life_update_get_image(int idx) {
        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<List<dong_nae_life_data>> call = service.image_select(idx);
        call.enqueue(new Callback<List<dong_nae_life_data>>() {
            @Override
            public void onResponse(Call<List<dong_nae_life_data>> call, Response<List<dong_nae_life_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<dong_nae_life_data>> call, Throwable t) {

            }
        });
    }

    private void onGetResult(List<dong_nae_life_data> lists) {
        dong_nae_life_update_image_adapter adapter = new dong_nae_life_update_image_adapter(this, lists);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }


//    @Override
//    protected void onResume() {
//        dong_nae_life_update_select(idx);
//        dong_nae_life_my_select_image(idx);
//    }
}