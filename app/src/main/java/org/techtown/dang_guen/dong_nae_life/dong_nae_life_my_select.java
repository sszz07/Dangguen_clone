package org.techtown.dang_guen.dong_nae_life;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dang_guen.home.product_my_other_select_adapter;
import org.techtown.dang_guen.home.product_my_select;
import org.techtown.dang_guen.home.product_update;
import org.techtown.dang_guen.start_atcivity.user_ApiClient;
import org.techtown.dang_guen.start_atcivity.user_ApiInterface;
import org.techtown.dang_guen.start_atcivity.user_data;
import org.techtown.dang_guen.start_atcivity.user_make;
import org.techtown.dong_nae_life.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;


/*본인이 등록한 게시물을 확인하는 클래스
 *
 * 수정,삭제가 가능하며, 채팅은 할수가 없다
 * 댓글과,대댓글을 추가 수정 삭제 확인이 가능하다
 *
 * 수정하는 파트,삭제하는 파트,댓글,대댓글 추가하는 파트를 나누기기*/

/**
 * 레이아웃 아이디값을 버튼 한곳으로 넣지 말고 현재 클래스에서 기능별로 나눌수 있도록 하기
 */
public class dong_nae_life_my_select extends AppCompatActivity {
    TextView subject_textview, content_textview, dong_nae_life_select_my_nick, dong_nae_life_select_my_time;
    ImageButton dong_nae_life_my_update_delete_button, dong_nae_life_comment_entire_imagebutton, dong_nae_life_my_comment_cancle_imagebutton, dong_nae_life_comment_imagebutton;
    int idx;
    String subject, content, board_user_image, board_user_nick, image, comment;
    String comment_최신순 = "최신순";
    RecyclerView recyclerView, recyclerView_comment;
    dong_nae_life_my_other_select_adapter adapter;
    dong_nae_life_comment_adapter comment_adapter;
    List<dong_nae_life_data> list = new ArrayList<>();
    ImageView dong_nae_life_comment_image_imageview, dong_nae_life_comment_image_imageview_backgroundtint;
    Dialog dilaog, dialog_delete;
    EditText dong_nae_life_comment_content_edittext;
    TextView asc_comment_button_textview, desc_comment_button_textview;
    Button asc_comment_button, desc_comment_button;
    private static final int IMG_REQUEST = 888;
    private Bitmap bitmap = null;
    SharedPreferences preferences2;
    String my_number;
    private ArrayList<dong_nae_life_like_data>list_like = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_my_select);



        //댓글 좋아요 액티비티 시작할때 댓글번호값 가져오기
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
            String JsonArrayData = sharedPreferences.getString("댓글좋아요", "");
            JSONArray ja = new JSONArray(JsonArrayData);

            for (int i = 0; i < ja.length(); i++) {
                JSONObject order = ja.getJSONObject(i);
                String 번호 = order.getString("번호");
                dong_nae_life_like_data data = new dong_nae_life_like_data(번호);
                list_like.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*댓글과 대댓글을 추가하기 위해서 유저의 닉네임과,유저의 이미지 데이터가 필요하다*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String user_nick = preferences.getString("user_nick", "");
        String user_image = preferences.getString("user_image", "");
        my_number = preferences.getString("user_number", "");

        /*어댑터에 실시간으로 추가하는것을 보여주기 위해서*/
        dong_nae_life_data data = new dong_nae_life_data();

        /*Dialog 초기화*/
        dialog_delete = new Dialog(dong_nae_life_my_select.this);
        dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_delete.setContentView(R.layout.product_my_select_delete_check_dialog);

        recyclerView = findViewById(R.id.dong_nae_life_my_image_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_comment = findViewById(R.id.dong_nae_life_my_comment_recyclerview);

        /*comment 리사이클러뷰 뷰가림*/
        recyclerView_comment.setVisibility(View.INVISIBLE);


        /*수정,삭제하기 위한 다이얼로그*/
        dilaog = new Dialog(dong_nae_life_my_select.this);
        /*앱 화면에 다이얼로그가 나오는것*/
        dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*다이얼로그 뷰를 가지고 오는 함수*/
        dilaog.setContentView(R.layout.product_my_select_update_delete_dialog);


        dong_nae_life_comment_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_comment_imagebutton);
        dong_nae_life_my_comment_cancle_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_my_comment_cancle_imagebutton);
        dong_nae_life_my_comment_cancle_imagebutton.setVisibility(View.INVISIBLE);
        dong_nae_life_comment_image_imageview = (ImageView) findViewById(R.id.dong_nae_life_comment_image_imageview);
        dong_nae_life_comment_image_imageview_backgroundtint = (ImageView) findViewById(R.id.dong_nae_life_comment_image_imageview_backgroundtint);

        dong_nae_life_comment_image_imageview_backgroundtint.setVisibility(View.GONE);
        dong_nae_life_comment_image_imageview.setVisibility(View.GONE);

        dong_nae_life_comment_content_edittext = (EditText) findViewById(R.id.dong_nae_life_comment_content_edittext);
        dong_nae_life_my_update_delete_button = (ImageButton) findViewById(R.id.dong_nae_life_my_update_delete_button);
        dong_nae_life_comment_entire_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_comment_entire_imagebutton);

        asc_comment_button_textview = (TextView) findViewById(R.id.asc_comment_button_textview);
        desc_comment_button_textview = (TextView) findViewById(R.id.desc_comment_button_textview);

        asc_comment_button = (Button) findViewById(R.id.asc_comment_button);
        desc_comment_button = (Button) findViewById(R.id.desc_comment_button);

        subject_textview = (TextView) findViewById(R.id.dong_nae_life_select_my_subject);
        content_textview = (TextView) findViewById(R.id.dong_nae_life_my_select_content_textview);
        dong_nae_life_select_my_nick = (TextView) findViewById(R.id.dong_nae_life_select_my_nick);
        dong_nae_life_select_my_time = (TextView) findViewById(R.id.dong_nae_life_select_my_time);
        ImageView dong_nae_life_select_my_image = (ImageView) findViewById(R.id.dong_nae_life_select_my_image);


        //버튼눌러서 이미지 가지고 오는 부분
        dong_nae_life_comment_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        /*dong_nae_life_main에서 유저가 등록한 데이터 받기기*/
        Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        subject = intent.getStringExtra("subject");
        content = intent.getStringExtra("content");
        board_user_image = intent.getStringExtra("board_user_image");
        board_user_nick = intent.getStringExtra("board_user_nick");
        long present_time = intent.getLongExtra("present_time", 0);

        /*서버에서 이미지 데이터 받기*/
        //Glide가 무엇인가? 안드로이드용 이미지를 로드(짐,화물 옮기다) 할수있는 프레임워크 이다
        //왜 사용하는가? 사용방법이 간단하다,알아서 디코딩 로드를 한다
        //어떻게 사용하는가?
        //.with() view의 현재 액티비티를 넣는것 매개변수로는 현재 액티비티를 넣는다
        //.load() 이미지 데이터를 로드하는 메소드 매개변수는 이미지 주소값을 넣는다
        //.apply(new RequestOptions()  RequestOptions-이미지의 여러가지 형태를 가지고 있는 클래스
        //.circleCrop())- 형태중에서 원모양
        //.into 어떤 뷰 객체에 넣을것인지 매개변수는 뷰 객체를 넣는다
        Glide.with(this)
                .load("http://52.79.180.89/dang_guen" + board_user_image)
                .apply(new RequestOptions()
                        .circleCrop())
                .into(dong_nae_life_select_my_image);


        class TIME_MAXIMUM {
            public static final int SEC = 60;
            public static final int MIN = 60;
            public static final int HOUR = 24;
            public static final int DAY = 30;
            public static final int MONTH = 12;
        }

        long 현재시간 = System.currentTimeMillis();
        long 시간계산 = (현재시간 - present_time) / 1000;

        String 시간값 = null;
        if (시간계산 < TIME_MAXIMUM.SEC) {
            시간값 = "방금";
        } else if ((시간계산 /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            시간값 = 시간계산 + "분전";
        } else if ((시간계산 /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            시간값 = (시간계산) + "시간전";
        } else if ((시간계산 /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            시간값 = (시간계산) + "일전";
        } else if ((시간계산 /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            시간값 = (시간계산) + "달전";
        } else {
            시간값 = (시간계산) + "년전";
        }


        //레이아웃 데이터값 설정
        dong_nae_life_select_my_nick.setText(board_user_nick);
        dong_nae_life_select_my_time.setText(시간값);
        subject_textview.setText(subject);
        content_textview.setText(content);



        /*현재 클래스에서 쉐어드의 함수값을 가지고 오게 함*/
        preferences = getSharedPreferences("현재유저", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String 댓글보기선택 = preferences.getString("댓글보기선택", "");
        System.out.println(댓글보기선택 + "댓글보기선택 위쪽");

        /*유저가 최신순을 선택한 상태*/
        if (댓글보기선택.equals("최신순")) {
            dong_nae_life_comment_info_recyclerview(idx);
            asc_comment_button.setTextColor(Color.GRAY);
            asc_comment_button_textview.setTextColor(Color.GRAY);

            desc_comment_button.setTextColor(Color.WHITE);
            desc_comment_button_textview.setTextColor(Color.parseColor("#DC9146"));

        }
        /*유저가 등록순을 선택한 상태*/
        else {
            dong_nae_life_comment_info_recyclerview(idx);
            desc_comment_button.setTextColor(Color.GRAY);
            desc_comment_button_textview.setTextColor(Color.GRAY);

            asc_comment_button.setTextColor(Color.WHITE);
            asc_comment_button_textview.setTextColor(Color.parseColor("#DC9146"));
        }


        /*최신순 버튼 눌렀을때*/
        desc_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("최신순 버튼을 눌렀을때");

                /*등록순 버튼*/
                asc_comment_button.setTextColor(Color.GRAY);
                asc_comment_button_textview.setTextColor(Color.GRAY);

                /*최신순 버튼*/
                desc_comment_button_textview.setTextColor(Color.parseColor("#DC9146"));
                desc_comment_button.setTextColor(Color.WHITE);

                editor.putString("댓글보기선택", comment_최신순);
                editor.apply();

                dong_nae_life_comment_info_recyclerview(idx);
            }
        });

        /*등록순 버튼 눌렀을때*/
        asc_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = null;
                bitmap = null;
                System.out.println("등록순 버튼 눌렀을때");
                /*최신순 버튼*/
                desc_comment_button.setTextColor(Color.GRAY);
                desc_comment_button_textview.setTextColor(Color.GRAY);

                /*등록순 버튼*/
                asc_comment_button_textview.setTextColor(Color.parseColor("#DC9146"));
                asc_comment_button.setTextColor(Color.WHITE);

                //remove도 apply()를 해줘야 됨
                editor.remove("댓글보기선택");
                editor.apply();

                dong_nae_life_comment_info_recyclerview(idx);
            }
        });


        //댓글 이미지 없애기
        dong_nae_life_my_comment_cancle_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dong_nae_life_comment_image_imageview.setVisibility(View.INVISIBLE);
                dong_nae_life_comment_image_imageview_backgroundtint.setVisibility(View.INVISIBLE);
                dong_nae_life_my_comment_cancle_imagebutton.setVisibility(View.INVISIBLE);
            }
        });


        //실시간 댓글 달기 및 댓글 저장
        dong_nae_life_comment_entire_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = imageToString();
                preferences2 = getSharedPreferences("현재유저", MODE_PRIVATE);
                String 댓글보기선택2 = preferences2.getString("댓글보기선택", "");
                comment = dong_nae_life_comment_content_edittext.getText().toString();
                dong_nae_life_comment_content_edittext.setText("");
                if (댓글보기선택2.equals("최신순")) {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    String time = simpleDateFormat.format(date);

                    /*텍스트ㅇ , 이미지x*/
                    if (image == null && comment.length() != 0) {
                        System.out.println("최신순 입력");
                        comment_insert(idx, 현재시간, time, image, comment);
                        data.setContent(comment);
                        data.setBoard_number(idx);
                        data.setComment_image(null);
                        data.setUser_image(user_image);
                        data.setNick(user_nick);
                        data.setPresent_time(현재시간);
                        data.setComment_recent(댓글보기선택);
                        comment_adapter.add_comment(data);
                    }

                    /*텍스트x 이미지ㅇ*/
                    else if (comment.length() == 0) {
                        comment_insert(idx, 현재시간, time, image, null);
                        data.setContent(null);
                        data.setBoard_number(idx);
                        data.setUser_image(user_image);
                        data.setComment_image(bitmap);
                        data.setNick(user_nick);
                        data.setPresent_time(현재시간);
                        data.setComment_recent(댓글보기선택);
                        comment_adapter.add_comment(data);
                        image_null();
                        dong_nae_life_comment_image_imageview.setVisibility(View.INVISIBLE);
                        dong_nae_life_comment_image_imageview_backgroundtint.setVisibility(View.INVISIBLE);
                        dong_nae_life_my_comment_cancle_imagebutton.setVisibility(View.INVISIBLE);
                    }

                    /*텍스트ㅇ 이미지ㅇ*/
                    else {
                        comment_insert(idx, 현재시간, time, image, comment);
                        data.setContent(comment);
                        data.setBoard_number(idx);
                        data.setUser_image(user_image);
                        data.setComment_image(bitmap);
                        data.setNick(user_nick);
                        data.setPresent_time(현재시간);
                        data.setComment_recent(댓글보기선택);
                        comment_adapter.add_comment(data);
                        image_null();
                        dong_nae_life_comment_image_imageview.setVisibility(View.INVISIBLE);
                        dong_nae_life_comment_image_imageview_backgroundtint.setVisibility(View.INVISIBLE);
                        dong_nae_life_my_comment_cancle_imagebutton.setVisibility(View.INVISIBLE);
                    }
                }



                /*등록순*/
                else {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    String time = simpleDateFormat.format(date);

                    /*텍스트ㅇ 이미지x*/
                    if (image == null && comment.length() > 0) {
                        comment_insert(idx, 현재시간, time, image, comment);
                        data.setContent(comment);
                        data.setBoard_number(idx);
                        data.setUser_image(user_image);
                        data.setComment_image(null);
                        data.setNick(user_nick);
                        data.setComment_recent(null);
                        data.setPresent_time(현재시간);
                        comment_adapter.add_comment(data);
                    }
                    /*텍스트x 이미지ㅇ*/
                    else if (image != null && comment.length() == 0) {
                        comment_insert(idx, 현재시간, time, image, null);
                        data.setContent(null);
                        data.setBoard_number(idx);
                        data.setComment_image(bitmap);
                        data.setUser_image(user_image);
                        data.setNick(user_nick);
                        data.setPresent_time(현재시간);
                        data.setComment_recent(null);
                        comment_adapter.add_comment(data);
                        image_null();
                        dong_nae_life_comment_image_imageview.setVisibility(View.INVISIBLE);
                        dong_nae_life_comment_image_imageview_backgroundtint.setVisibility(View.INVISIBLE);
                        dong_nae_life_my_comment_cancle_imagebutton.setVisibility(View.INVISIBLE);
                    }
                    /*텍스트ㅇ 이미지ㅇ*/
                    else {
                        comment_insert(idx, 현재시간, time, image, comment);
                        data.setContent(comment);
                        data.setBoard_number(idx);
                        data.setUser_image(user_image);
                        data.setComment_image(bitmap);
                        data.setNick(user_nick);
                        data.setPresent_time(현재시간);
                        data.setComment_recent(null);
                        comment_adapter.add_comment(data);
                        image_null();
                        dong_nae_life_comment_image_imageview.setVisibility(View.INVISIBLE);
                        dong_nae_life_comment_image_imageview_backgroundtint.setVisibility(View.INVISIBLE);
                        dong_nae_life_my_comment_cancle_imagebutton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        //삭제 버튼
        dong_nae_life_my_update_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
    }


    //텍스트 삭제
    private void dong_nae_life_info_delete(int idx) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_info_delete(idx);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {

            }
        });
    }

    //텍스트 삭제
    private void dong_nae_life_multiimage_delete(int idx) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_multiimage_delete(idx);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {

            }
        });
    }


    /*
     *
     * 사전적 의미-ApiInterface
     *
     * Api(application programming interface)
     * 앱이 실행중에 데이터를 주고받는 라이브러리
     *
     * Interface
     * 서로 다른 시스템을 이어주는것
     * 시스템-어떠한 기능을 실현하기 위해 여러 요소를 조합한 집합체
     * 이어주다-2개 이상의 뭐든 연결
     *
     * Client
     * 서버에 연결된 컴퓨터
     * 서버-정보의 제공이나 작업을 수행하는 컴퓨터 시스템
     * 연결-관계를 맺게함
     *
     *
     * dong_nae_life_ApiInterface
     * 무엇인가?
     *
     *
     * dong_nae_life_ApiClient
     * 무엇인가?
     *
     *
     *
     * 왜 사용하는가?
     *
     * 어떻게 사용하는가?
     * */
    private void dong_nae_life_update_select(int idx) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_info_update_select(idx);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String subject = response.body().getSubject();
                    String content = response.body().getContent();

                    //레이아웃에 보이게 하기
                    subject_textview.setText(subject);
                    content_textview.setText(content);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }

    //수정하고 다시 수정하기 위해서
    private void dong_nae_life_update_select2(int idx) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_info_update_select(idx);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String subject = response.body().getSubject();
                    String content = response.body().getContent();


                    //레이아웃에 보이게 하기
                    subject_textview.setText(subject);
                    content_textview.setText(content);

                    Intent intent = new Intent(dong_nae_life_my_select.this, dong_nae_life_update.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    startActivity(intent);

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }


    //다이얼로그 실행
    public void dialog() {
        dilaog.getWindow().setGravity(Gravity.CENTER);
        dilaog.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


        // 아니오 버튼
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        Button cancle_button = dilaog.findViewById(R.id.product_my_select_cancle_dialog_button);
        cancle_button.findViewById(R.id.product_my_select_cancle_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        dilaog.findViewById(R.id.product_my_select_delete_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_delete();
            }
        });
        dilaog.findViewById(R.id.product_my_select_update_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dong_nae_life_update_select2(idx);
            }
        });
    }

    //다이얼로그 실행
    public void dialog_delete() {
        dilaog.getWindow().setGravity(Gravity.CENTER);
        dialog_delete.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
        dilaog.dismiss();
        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


        // 아니오 버튼
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        Button cancle_button = dialog_delete.findViewById(R.id.product_my_select_cancle_check_button);
        cancle_button.findViewById(R.id.product_my_select_cancle_check_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog_delete.dismiss(); // 다이얼로그 닫기
//                dong_nae_life_info_update_select(idx);
            }
        });
        // 네 버튼
        dialog_delete.findViewById(R.id.product_my_select_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dong_nae_life_info_delete(idx);
                dong_nae_life_multiimage_delete(idx);
                onBackPressed();
            }
        });
    }


    //이미지 값 가져오기
    private void dong_nae_life_my_select_image(int idx) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<List<dong_nae_life_data>> call = ApiInterface.image_select(idx);
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
        adapter = new dong_nae_life_my_other_select_adapter(this, lists);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }


    //-----------------------------------------------------------------------------------------------------
    // 코멘트 insert
    private void comment_insert(int board_number, long present_time, String time, String image, String comment) {
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
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String user_nick = preferences.getString("user_nick", "");
        String user_image = preferences.getString("user_image", "");


        /*call이 무엇인지,<>을 무엇을 의미하는지,getClient()은 무엇인지,create는 무엇인지, */
        Call<dong_nae_life_data> call = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class).dong_nae_life_comment_insert(user_nick, user_image, comment, board_number, present_time, image, time);
        /*call이 무엇인지,enqueue을 무엇을 의미하는지,Callback은 무엇인지,create는 무엇인지, */
        call.enqueue(new Callback<dong_nae_life_data>() {
            /*왜 오버라이드를 사용하는지*/
            @Override
            /*onResponse는 무엇인지?*/
            public void onResponse(Call<dong_nae_life_data> call, Response<dong_nae_life_data> response) {
                dong_nae_life_comment_number(board_number);
            }

            @Override
            public void onFailure(Call<dong_nae_life_data> call, Throwable t) {

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
                dong_nae_life_my_comment_cancle_imagebutton.setVisibility(View.VISIBLE);
                dong_nae_life_comment_image_imageview.setVisibility(View.VISIBLE);
                dong_nae_life_comment_entire_imagebutton.setVisibility(View.VISIBLE);
                dong_nae_life_comment_image_imageview_backgroundtint.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                Glide.with(dong_nae_life_my_select.this).load(bitmap).into(dong_nae_life_comment_image_imageview);
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
            if (bitmap == null) {
                if (image != null) {
                    return image;
                } else {
                    return null;
                }
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imgByte = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(imgByte, Base64.DEFAULT);
            }
        }
    }


    private Bitmap image_null() {
        System.out.println("들어오는지 확인해보기");
        return bitmap = null;
    }


    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다
    private void dong_nae_life_comment_info_recyclerview(int idx) {
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 댓글보기선택 = preferences.getString("댓글보기선택", "");

        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<List<dong_nae_life_data>> call = ApiInterface.dong_nae_life_comment_info_recyclerview(idx, 댓글보기선택);
        call.enqueue(new Callback<List<dong_nae_life_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<dong_nae_life_data>> call, @NonNull Response<List<dong_nae_life_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult_comment(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<dong_nae_life_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult_comment(List<dong_nae_life_data> lists) {
        recyclerView_comment.setVisibility(View.VISIBLE);
        recyclerView_comment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        comment_adapter = new dong_nae_life_comment_adapter(this, lists, dilaog, dialog_delete, my_number,list_like);
        comment_adapter.notifyDataSetChanged();
        recyclerView_comment.setAdapter(comment_adapter);
        list = lists;
    }

    private void dong_nae_life_comment_number(int board_number) {
        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_comment_number(board_number);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dong_nae_life_comment_info_recyclerview(idx);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }


    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .


    @Override
    protected void onResume() {
        super.onResume();
        dilaog.dismiss();

        dong_nae_life_comment_info_recyclerview(idx);
        dong_nae_life_update_select(idx);
        dong_nae_life_my_select_image(idx);
    }


    @Override
    protected void onStop() {
        super.onStop();


        //화면에 나가게 되면 값들이 저장을 하는곳이다다
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("현재유저", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < list_like.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("번호", list_like.get(i).getComment_number());
                jArray.put(sObject);

            }
            editor.putString("댓글좋아요", String.valueOf(jArray));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}