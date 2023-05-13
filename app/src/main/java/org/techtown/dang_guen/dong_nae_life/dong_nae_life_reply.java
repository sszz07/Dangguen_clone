package org.techtown.dang_guen.dong_nae_life;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import org.techtown.dong_nae_life.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_reply extends AppCompatActivity {
    String comment_user_image, comment_user_content, comment_user_nick, 시간값, image, reply, comment_image;
    int comment_number, board_number;
    long present_time;
    TextView dong_nae_life_reply_reply_user_nick, dong_nae_life_reply_reply_present_time, dong_nae_life_reply_reply_content;
    ImageView dong_nae_life_reply_reply_user_image, dong_nae_life_reply_image_imageview_backgroundtint, dong_nae_life_reply_select_image, dong_nae_life_reply_comment_image;
    EditText dong_nae_life_reply_content_edittext;
    ImageButton dong_nae_life_reply_imagebutton, dong_nae_life_reply_entire_imagebutton, dong_nae_life_my_reply_cancel_imagebutton;
    private static final int IMG_REQUEST = 111;
    RecyclerView reply_recyclerView;
    Dialog dilaog, dialog_delete;
    List<dong_nae_life_data> list = new ArrayList<>();
    private Bitmap bitmap;
    dong_nae_life_reply_adapter reply_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_reply);
        /**
         * 1.밑에 } 풀어야 됨됨*/
    }
}


//        dong_nae_life_data data = new dong_nae_life_data();
//
//        dialog_delete = new Dialog(dong_nae_life_reply.this);/*Dialog 초기화*/
//        dialog_delete.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_delete.setContentView(R.layout.product_my_select_delete_check_dialog);
//
//        dilaog = new Dialog(dong_nae_life_reply.this);
//        dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dilaog.setContentView(R.layout.product_my_select_update_delete_dialog);
//        /*
//         * 쉐어드로 유저 닉네임,유저 이미지값 가져오기
//         * */
//        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
//        String user_nick = preferences.getString("user_nick", "");
//        String user_image = preferences.getString("user_image", "");
//
//        /*
//         * 인텐트로 댓글값 가져오기
//         * */
//        Intent intent = getIntent();
//
//        comment_image = intent.getStringExtra("comment_image");
//        comment_user_image = intent.getStringExtra("comment_user_image");
//        comment_user_content = intent.getStringExtra("comment_content");
//        comment_user_nick = intent.getStringExtra("comment_nick");
//        comment_number = intent.getIntExtra("comment_idx", 0);
//        board_number = intent.getIntExtra("board_idx", 0);
//        시간값 = intent.getStringExtra("시간값");
//        dong_nae_life_reply_info_recyclerview(comment_number);
//
//
//        System.out.println(comment_user_image + "comment_user_image");
//        System.out.println(comment_user_content + "comment_user_content");
//        System.out.println(comment_user_nick + "comment_user_nick");
//        System.out.println(시간값 + "시간값");
//        System.out.println(comment_number + "comment_number");
//        System.out.println(board_number + "board_number");
//        System.out.println(comment_image + "comment_image");
//
//        dong_nae_life_reply_reply_user_nick = (TextView) findViewById(R.id.dong_nae_life_reply_reply_user_nick);
//        dong_nae_life_reply_reply_present_time = (TextView) findViewById(R.id.dong_nae_life_reply_reply_present_time);
//        dong_nae_life_reply_reply_content = (TextView) findViewById(R.id.dong_nae_life_reply_reply_content);
//        dong_nae_life_reply_reply_user_image = (ImageView) findViewById(R.id.dong_nae_life_reply_reply_user_image);
//        dong_nae_life_reply_image_imageview_backgroundtint = (ImageView) findViewById(R.id.dong_nae_life_reply_image_imageview_backgroundtint);
//
//
//        dong_nae_life_reply_comment_image = (ImageView) findViewById(R.id.dong_nae_life_reply_comment_image);
//        dong_nae_life_reply_comment_image.setVisibility(View.GONE);
//        dong_nae_life_reply_select_image = (ImageView) findViewById(R.id.dong_nae_life_reply_select_image);
//        dong_nae_life_reply_select_image.setVisibility(View.INVISIBLE);
//        dong_nae_life_reply_image_imageview_backgroundtint.setVisibility(View.INVISIBLE);
//
//        reply_recyclerView = findViewById(R.id.dong_nae_life_reply_recyclerview);
//
//
//        dong_nae_life_reply_content_edittext = (EditText) findViewById(R.id.dong_nae_life_reply_content_edittext);
//        dong_nae_life_reply_entire_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_reply_entire_imagebutton);
//        dong_nae_life_reply_entire_imagebutton.setVisibility(View.INVISIBLE);
//
//        dong_nae_life_reply_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_reply_imagebutton);
//        dong_nae_life_my_reply_cancel_imagebutton = (ImageButton) findViewById(R.id.dong_nae_life_my_reply_cancel_imagebutton);
//        dong_nae_life_my_reply_cancel_imagebutton.setVisibility(View.INVISIBLE);
//
//        dong_nae_life_reply_reply_user_nick.setText(comment_user_nick);
//        dong_nae_life_reply_reply_present_time.setText(시간값);
//
//        Glide.with(getApplicationContext())
//                .load("http://52.79.180.89/dang_guen" + comment_user_image)// image url
//                .apply(new RequestOptions().circleCrop())
//                .into(dong_nae_life_reply_reply_user_image);
//
//        if (comment_image != null) {
//            dong_nae_life_reply_comment_image.setVisibility(View.VISIBLE);
//            Glide.with(getApplicationContext())
//                    .load("http://52.79.180.89/dang_guen" + comment_image)// image url
//                    .into(dong_nae_life_reply_comment_image);
//        }
//        if (comment_user_content == null) {
//            dong_nae_life_reply_reply_content.setVisibility(View.GONE);
//        } else {
//            dong_nae_life_reply_reply_content.setText(comment_user_content);
//        }
//
//
//
//
//        dilaog.findViewById(R.id.product_my_select_delete_dialog_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dilaog.getWindow().setGravity(Gravity.CENTER);
//                dialog_delete.show(); // 다이얼로그 띄우기
//                /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
//                dilaog.dismiss();
//                // 위젯 연결 방식은 각자 취향대로~
//                // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
//                // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
//
//
//                // 아니오 버튼
//                // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
//                Button cancle_button = dialog_delete.findViewById(R.id.product_my_select_cancle_check_button);
//                cancle_button.findViewById(R.id.product_my_select_cancle_check_button).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // 원하는 기능 구현
//                        dialog_delete.dismiss(); // 다이얼로그 닫기
//                    }
//                });
//                // 네 버튼
//                dialog_delete.findViewById(R.id.product_my_select_delete_button).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog_delete.dismiss();
//                    }
//                });
//            }
//        });
//        dilaog.findViewById(R.id.product_my_select_update_dialog_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(dong_nae_life_reply.this, dong_nae_life_reply_update.class);
//                if (comment_image == null) {
//                    intent.putExtra("update_idx", );
//                    intent.putExtra("content", data.getContent());
//                    startActivity(intent);
//                } else {
////                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                    data.getComment_image().compress(Bitmap.CompressFormat.JPEG, 100, stream);
////                    byte[] byteArray = stream.toByteArray();
////                    intent.putExtra("update_idx", update_idx);
////                    intent.putExtra("comment_content", data.getContent());
////                    intent.putExtra("comment_image", data.getImage());
////                    intent.putExtra("comment_image_bitmap", byteArray);
////                    startActivity(intent);
//                }
//            }
//        });
//
//
//
//
//        /*현재 시간*/
//        class TIME_MAXIMUM {
//            public static final int SEC = 60;
//            public static final int MIN = 60;
//            public static final int HOUR = 24;
//            public static final int DAY = 30;
//            public static final int MONTH = 12;
//        }
//
//        long 현재시간 = System.currentTimeMillis();
//        long 시간계산 = (현재시간 - present_time) / 1000;
//
//        String 시간값 = null;
//        if (시간계산 < TIME_MAXIMUM.SEC) {
//            시간값 = "방금";
//        } else if ((시간계산 /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
//            시간값 = 시간계산 + "분전";
//        } else if ((시간계산 /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
//            시간값 = (시간계산) + "시간전";
//        } else if ((시간계산 /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
//            시간값 = (시간계산) + "일전";
//        } else if ((시간계산 /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
//            시간값 = (시간계산) + "달전";
//        } else {
//            시간값 = (시간계산) + "년전";
//        }
//
//
//
//            dong_nae_life_reply_entire_imagebutton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                  int 실시간대댓글 = 1;
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                    Date date = new Date();
//                    String time = simpleDateFormat.format(date);
//                    String reply_null = null;
//                    dong_nae_life_reply_insert(comment_number, 현재시간, time, user_nick, reply_null, user_image, board_number, image);
//                    data.setContent(null);
//                    data.setUser_image(user_image);
//                    data.setNick(user_nick);
//                    data.setBoard_number(실시간대댓글);
//                    data.setPresent_time(현재시간);
//                    reply_adapter.add_reply(data);
//                    dong_nae_life_reply_image_imageview_backgroundtint.setVisibility(View.GONE);
//                    dong_nae_life_reply_select_image.setVisibility(View.GONE);
//                    dong_nae_life_my_reply_cancel_imagebutton.setVisibility(View.GONE);
//                }
//            });
//
//
//
//
//
//        /*edittext 값 존재 버튼 유무*/
//        dong_nae_life_reply_content_edittext.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                /*텍스트ㅇ 이미지x */
//                if (dong_nae_life_reply_content_edittext.length() > 0 && image == null) {
//
//                    dong_nae_life_reply_entire_imagebutton.setVisibility(View.VISIBLE);
//                }
//                /*텍스트ㅇ 이미지ㅇ*/
//                else if (dong_nae_life_reply_content_edittext.length() > 0 && image != null) {
//
//                    dong_nae_life_reply_entire_imagebutton.setVisibility(View.VISIBLE);
//                }
//
//                //대댓글 insert
//                dong_nae_life_reply_entire_imagebutton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        reply = dong_nae_life_reply_content_edittext.getText().toString();
//                        dong_nae_life_reply_content_edittext.setText("");
//
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                        Date date = new Date();
//                        String time = simpleDateFormat.format(date);
//
//                        /*텍스트ㅇ 이미지x */
//                        if (reply.length() != 0 && image == null || bitmap == null) {
//                            image = null;
//                            dong_nae_life_reply_insert(comment_number, 현재시간, time, user_nick, reply, user_image, board_number, image);
//                            //대댓글 내용
//                            data.setContent(reply);
//                            //대댓글 유저 이미지
//                            data.setUser_image(user_image);
//                            //대댓글 유저 닉네임
//                            data.setNick(user_nick);
//                            //대댓글 이미지값 눌
//                            //대댓글 올린 시간
//                            data.setPresent_time(현재시간);
//                            reply_adapter.add_reply(data);
//                        }
//                        /*텍스트ㅇ 이미지ㅇ*/
//                        else {
//                            System.out.println("/*텍스트ㅇ 이미지ㅇ*/");
//                            dong_nae_life_reply_insert(comment_number, 현재시간, time, user_nick, reply, user_image, board_number, image);
//                            data.setContent(reply);
//                            data.setUser_image(user_image);
//                            data.setNick(user_nick);
//                            data.setPresent_time(현재시간);
//                            reply_adapter.add_reply(data);
//                            dong_nae_life_reply_image_imageview_backgroundtint.setVisibility(View.GONE);
//                            dong_nae_life_reply_select_image.setVisibility(View.GONE);
//                            dong_nae_life_my_reply_cancel_imagebutton.setVisibility(View.GONE);
//                        }
//
//                    }
//                });
//
//
//                /**
//                 * edittext에 값이 없을때 다시 버튼이 보이려면 어떻게 해야 될까?
//                 *.length()를 이용하기
//                 * */
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // 입력하기 전에 조치
//            }
//        });
//
//
//
//        /*갤러리 선택*/
//        dong_nae_life_reply_imagebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage();
//            }
//        });
//
//        /*이미지 취소*/
//        dong_nae_life_my_reply_cancel_imagebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                image = null;
//                bitmap = null;
//                dong_nae_life_my_reply_cancel_imagebutton.setVisibility(View.INVISIBLE);
//                dong_nae_life_reply_image_imageview_backgroundtint.setVisibility(View.INVISIBLE);
//                dong_nae_life_reply_select_image.setVisibility(View.INVISIBLE);
//                dong_nae_life_reply_entire_imagebutton.setVisibility(View.INVISIBLE);
//                /*edittext 값 존재 버튼 유무*/
//                dong_nae_life_reply_content_edittext.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        if (image == null && dong_nae_life_reply_content_edittext.length() == 0) {
//                            dong_nae_life_reply_entire_imagebutton.setVisibility(View.INVISIBLE);
//                        } else if (image == null) {
//                            dong_nae_life_reply_entire_imagebutton.setVisibility(View.INVISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable arg0) {
//
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                        // 입력하기 전에 조치
//                    }
//                });
//            }
//        });
//
//    }
//
//
//    private String imageToString() {
//        for (int i = 0; true; i++) {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            byte[] imgByte = byteArrayOutputStream.toByteArray();
//            return Base64.encodeToString(imgByte, Base64.DEFAULT);
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
//
//            Uri path = data.getData();
//            try {
//                dong_nae_life_my_reply_cancel_imagebutton.setVisibility(View.VISIBLE);
//                dong_nae_life_reply_select_image.setVisibility(View.VISIBLE);
//                dong_nae_life_reply_image_imageview_backgroundtint.setVisibility(View.VISIBLE);
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//
//                dong_nae_life_reply_entire_imagebutton.setVisibility(View.VISIBLE);
//
//                Glide.with(dong_nae_life_reply.this).load(bitmap).into(dong_nae_life_reply_select_image);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        image = imageToString();
//    }
//
//
//    private void selectImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, IMG_REQUEST);
//    }
//
//
//    /*대댓글 insert */
//    private void dong_nae_life_reply_insert(int reply_number, long present_time, String time, String user_nick, String reply, String user_image, int board_number, String image) {
//        Call<dong_nae_life_data> call = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class).dong_nae_life_reply_insert(user_image, present_time, reply, user_nick, reply_number, time, board_number, image);
//        call.enqueue(new Callback<dong_nae_life_data>() {
//            @Override
//            public void onResponse(Call<dong_nae_life_data> call, Response<dong_nae_life_data> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<dong_nae_life_data> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//    /*대댓글 select*/
//    private void dong_nae_life_reply_info_recyclerview(int comment_number) {
//        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
//        Call<List<dong_nae_life_data>> call = ApiInterface.dong_nae_life_reply_info_recyclerview(comment_number);
//        call.enqueue(new Callback<List<dong_nae_life_data>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<dong_nae_life_data>> call, @NonNull Response<List<dong_nae_life_data>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    System.out.println(response.body() + "response.body()");
//                    onGetResult_reply(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<dong_nae_life_data>> call, @NonNull Throwable t) {
//            }
//        });
//    }
//
//    private void onGetResult_reply(List<dong_nae_life_data> lists) {
//        reply_recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        reply_adapter = new dong_nae_life_reply_adapter(this, lists, dialog_delete, dilaog);
//        reply_adapter.notifyDataSetChanged();
//        reply_recyclerView.setAdapter(reply_adapter);
//        list = lists;
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        dong_nae_life_reply_info_recyclerview(comment_number);
//    }
