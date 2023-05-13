package org.techtown.dang_guen.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_update extends AppCompatActivity {

    private Button product_update_category_button, update_button;
    EditText product_update_subject_edittext, product_update_content_edittext, product_update_price_edittext;
    ImageButton product_update_image_imagebutton;
    TextView  product_update_image_count, product_update_image_count_10;
    private product_make_select_image_adpater select_image_adpater;
    private ArrayList<Uri> product_update_images = new ArrayList<>();
    List<product_data> list = new ArrayList<>();
    int CODE_ALBUM_REQUEST = 111;
    String subject, content, price, category;
    int idx;
    int image_10개초과 = 10;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);


        product_update_subject_edittext = (EditText) findViewById(R.id.product_update_product_subject);
        product_update_content_edittext = (EditText) findViewById(R.id.product_update_content);
        product_update_price_edittext = (EditText) findViewById(R.id.product_update_price);

        product_update_category_button = (Button) findViewById(R.id.product_update_category);
        product_update_image_imagebutton = (ImageButton) findViewById(R.id.product_update_image_button);
        update_button = (Button) findViewById(R.id.product_update_button);
        product_update_image_count = (TextView) findViewById(R.id.product_update_image_count);

        product_update_image_count_10 = (TextView) findViewById(R.id.product_update_image_count_10);

        product_update_image_count.setVisibility(View.INVISIBLE);
        product_update_image_count_10.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.product_product_image_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

//메인에서 선택한 클래스에서 넘어온 데이터값
        Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        subject = intent.getStringExtra("subject");
        content = intent.getStringExtra("content");
        price = intent.getStringExtra("price");
        category = intent.getStringExtra("category");
        System.out.println(category + "카테고리값");

        //가져온 데이터값 셋팅
        product_update_subject_edittext.setText(subject);
        product_update_content_edittext.setText(content);
        product_update_price_edittext.setText(price);
        product_update_category_button.setText(category);

        //이미지 리사이클러뷰에 보이게 하기
        product_update_get_image(idx);

        int product_make_image_count1 = product_update_images.size();
        String to = Integer.toString(product_make_image_count1);
        if (product_make_image_count1 != 0) {
            System.out.println("c");
            product_update_image_count.setVisibility(View.VISIBLE);
            product_update_image_count_10.setVisibility(View.VISIBLE);
            product_update_image_count.setText(to);
        } else if (product_make_image_count1 > 10) {
            Toast toast = Toast.makeText(this, "사진이 10개 초과가 되었습니다", Toast.LENGTH_SHORT);
            toast.show();
            System.out.println("d");
            product_update_image_count.setVisibility(View.VISIBLE);
            product_update_image_count_10.setVisibility(View.VISIBLE);
            product_update_image_count.setText(image_10개초과);
        }


        select_image_adpater = new product_make_select_image_adpater(this, product_update_images);
        select_image_adpater.setOnItemClickListener(new product_make_select_image_adpater.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, String image_count) {
                product_update_image_count.setText(image_count);
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
                product_update_image(idx, time);
                product_update_info(idx, time);


            }
        });

        product_update_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), product_category.class);
                startActivityForResult(intent, 101);
            }
        });

        //이미지 업데이트 버튼
        product_update_image_imagebutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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
                        String imagePath = product_image_Utils.getPath(product_update.this, image);
                        System.out.println(product_update_images.size() + "확인");
                        if (product_update_images.size() >= 10) {
                            System.out.println("이거인가?");
                            Toast toast = Toast.makeText(this, "사진이 10개 초과가 되었습니다", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        } else if (product_update_images.size() < 10) {
                            product_update_images.add(Uri.parse(imagePath));
                        }
                    }
                }
            }


            product_update_image_count.setVisibility(View.VISIBLE);
            product_update_image_count_10.setVisibility(View.VISIBLE);


            int product_make_image_count1 = product_update_images.size();

            if (product_make_image_count1 <= 10) {
                String to = Integer.toString(product_make_image_count1);
                System.out.println(product_make_image_count1 + "위에 이미지 갯수 확인");
                product_update_image_count.setText(to);
                recyclerView.setAdapter(select_image_adpater);
            } else if (product_make_image_count1 > 10) {
                String to = "10";
                System.out.println(product_make_image_count1 + "밑에 이미지 갯수 확인");
                product_update_image_count.setText(to);
                recyclerView.setAdapter(select_image_adpater);
            }

        }
        if (requestCode == 101) {
            String product_category_digital = data.getStringExtra("product_category_digital");
            String product_category_man_cloth = data.getStringExtra("man_cloth");
            if (product_category_digital != null) {
                product_update_category_button.setText(product_category_digital);
            } else if (product_category_man_cloth != null) {
                product_update_category_button.setText(product_category_man_cloth);
            }
        }
    } //end of onActivityResult


    //이미지 서버로 보내기
    public void product_update_image(int idx, String time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : product_update_images) {
            list.add(prepairFiles("file[]", uri));
        }

        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_multiimage_update(idx, time, list);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(Call<product_data> call, Response<product_data> response) {

            }

            @Override
            public void onFailure(Call<product_data> call, Throwable t) {

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
    private void product_update_info(int idx, String time) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : product_update_images) {
            list.add(prepairFiles("file[]", uri));
        }

        String update_subject = product_update_subject_edittext.getText().toString();
        String update_content = product_update_content_edittext.getText().toString();
        String update_price = product_update_price_edittext.getText().toString();
        String category = product_update_category_button.getText().toString();

        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_info_update(idx, update_subject, update_content, update_price, time, category, list);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {


            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("updatePerson()", "에러 : " + t.getMessage());
                onBackPressed();
                /**
                 * 레이아웃에 textview id값을 넣으면 나오는 에러코드
                 * androidx.appcompat.widget.AppCompatTextView cannot be cast to android.view.ViewGroup
                 *
                 * */
            }
        });
    }


    //이미지 값 가져오기
    private void product_update_get_image(int idx) {
        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = service.image_select(idx);
        call.enqueue(new Callback<List<product_data>>() {
            @Override
            public void onResponse(Call<List<product_data>> call, Response<List<product_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<product_data>> call, Throwable t) {

            }
        });
    }

    private void onGetResult(List<product_data> lists) {
        product_update_image_adapter adapter = new product_update_image_adapter(this, lists);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }

}