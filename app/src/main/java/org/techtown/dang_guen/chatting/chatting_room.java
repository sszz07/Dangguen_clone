package org.techtown.dang_guen.chatting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppComponentFactory;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import org.jetbrains.annotations.NotNull;
import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dang_guen.home.product_make;
import org.techtown.dang_guen.home.product_my_select;
import org.techtown.dong_nae_life.R;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Part;

import static java.util.Objects.requireNonNull;


public class chatting_room extends AppCompatActivity {
    TextView chatting_room_nick, chatting_room_subject, chatting_room_price, chatting_room_price2_textview;
    ImageView chatting_room_product_image;
    String chatting_room_your_nick_data, chatting_room_subject_data, chatting_room_price_data, chatting_room_product_image_data, my_phone_number, your_phone_number, seller_image, chatting_room_my_nick_data, string_room_number, last_user, noti_my_check, noti_your_check;
    int product_number;
    String 현재페이지 = "chatting_room";
    RecyclerView recyclerView;
    static chatting_room_Adapter room_adapter;
    ImageButton chatting_room_send_button, chatting_room_plus_imagebutton, chatting_room_menu;
    String 시간, my_msg_send;
    EditText send_msg_edittext;
    PrintWriter sendWriter;
    ArrayList<Uri> uriList = new ArrayList<>();     // 이미지의 uri를 담을 ArrayList 객체
    int CODE_ALBUM_REQUEST = 111;
    chatting_room_data chat;
    // 이미지를 보여줄 리사이클러뷰
    // 리사이클러뷰에 적용시킬 어댑터
    int page = 0, limit = 10;
    int chating_int_room_number;
    //채팅목록의 아이템을 선택을 하게 되면 채팅방번호의 값을 넘길수 있게 한다
    //스텍틱으로 한 이유는 목록의 방번호의 값과 서버의 방번호값이 같아야 된다
    //그래야 다른 채팅방으로 넘겨지지 않는다
    static int chatting_room_number;
    //스텍틱 한 이유는 현재 클래스에서의 어댑터의 값과 서비스에서의 필요한 어댑터의 값이 같기 때문이다
    @SuppressLint("StaticFieldLeak")
    ArrayList<chatting_room_data> list = new ArrayList<>();
    SharedPreferences pref;
    String noti_on_off_shared;
    Dialog chatting_room_out;
    long chatting_main_out_my_check_time;
    String my_location;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        //1.옵션메뉴 버튼 2개 만들기 1번은 켜져있을때 2번은 꺼져있을때 조건문 만들기
        //인텐트로 my,your noti값 넘기기
        //레이아웃 메뉴에 꺼져있을때,켜져있을때 만들기

        //2.db에 my_noti_check,your_noti_check 만들어서 들어갈값들을 update하게 만들어서 값들이 제대로 들어가는지 확인하기
        //3.


        //상대방의 현재위치를 알기 위해서
        //getSystemService-앱의 현재 어떤 서비스를 사용하고 있는지 확인
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        //getRunningTasks()- //왜 List에 있을까?-최대 100개 까지의 시스템 서비스를 가질수 있다
        //1은 하나의 서비스만 사용하기 때문에
        //ActivityManager.RunningTaskInfo- 리스트에 getRunningTasks얻은 서비스들을 저장할수 있다
        List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(1);

        //topActivity-앱의 최상단 액티비티 클래스명값 가져오기
        //info-리스트에 저장되어있는 값
        //ComponentName-현재 액티비티의 패키지명,클래스명을 얻을수 있다
        ComponentName componentName = info.get(0).topActivity;

        //substring(1)-문자열에서 특정문자를 기준으로 자르는 메소드
        //String str1 = "1학년5반 손흥민";
        //substring(0, str1.indexOf(" ")); 맨 뒤에서 부터 " "공간이 있는곳까지 자름
        //결과는 1학년5반 나오게 된다
        //getShortClassName()-화면의 최상단에 보이기 위해서
        my_location = componentName.getShortClassName().substring(1);

        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        String 현재유저_image = preferences.getString("user_image", "");
        String 현재유저_nick = preferences.getString("user_nick", "");
        /*-------------------------------------------------------------------------------------------------------------*/


        chatting_room_out = new Dialog(chatting_room.this);       // Dialog 초기화
        chatting_room_out.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        chatting_room_out.setContentView(R.layout.chatting_room_out);

        //채팅 플러 버튼
        /*-------------------------------------------------------------------------------------------------------------*/
        chatting_room_plus_imagebutton = (ImageButton) findViewById(R.id.chatting_room_plus_imagebutton);
        chatting_room_plus_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.chatting_room_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.chatting_room_gellery) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            //핸드폰에 내장된 갤러리로 이동하는 코드드
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(intent, CODE_ALBUM_REQUEST);
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        /*-------------------------------------------------------------------------------------------------------------*/


        //상품정보
        /*-------------------------------------------------------------------------------------------------------------*/
        Intent intent = getIntent();
        chatting_room_number = intent.getIntExtra("room_number", 0);
        string_room_number = String.valueOf(chatting_room_number);
        product_number = intent.getIntExtra("product_number", 0);
        chatting_room_your_nick_data = intent.getStringExtra("your_nick");
        chatting_room_my_nick_data = intent.getStringExtra("my_nick");
        chatting_room_product_image_data = intent.getStringExtra("product_image");
        chatting_room_price_data = intent.getStringExtra("product_price");
        chatting_room_subject_data = intent.getStringExtra("product_subject");
        seller_image = intent.getStringExtra("other_user_image");
        last_user = intent.getStringExtra("last_user");
        your_phone_number = intent.getStringExtra("your_phone_number");
        my_phone_number = intent.getStringExtra("my_phone_number");
        product_number = intent.getIntExtra("product_number", 0);
        chatting_room_your_nick_data = intent.getStringExtra("your_nick");
        chatting_room_product_image_data = intent.getStringExtra("product_image");
        chatting_room_price_data = intent.getStringExtra("product_price");
        chatting_room_subject_data = intent.getStringExtra("product_subject");
        seller_image = intent.getStringExtra("other_user_image");
        chatting_main_out_my_check_time = intent.getLongExtra("chatting_main_out_my_check_time", 0);


        if (현재유저_phone_number.equals(my_phone_number)) {
            chatting_main_read_check_update(chatting_room_number, 현재유저_phone_number, null);
        } else if (현재유저_phone_number.equals(your_phone_number)) {
            chatting_main_read_check_update(chatting_room_number, null, 현재유저_phone_number);
        }


        //레이아웃
        //상품이미지
        chatting_room_product_image = (ImageView) findViewById(R.id.chatting_room_product_image);
        //채팅방 상대방 닉네임
        chatting_room_nick = (TextView) findViewById(R.id.chatting_room_nick);
        //상품 제목
        chatting_room_subject = (TextView) findViewById(R.id.chatting_room_subject);
        //상품 가격
        chatting_room_price = (TextView) findViewById(R.id.chatting_room_price);

        chatting_room_price2_textview = (TextView) findViewById(R.id.chatting_room_price2);


        //채팅방 상단 유저 닉네임 설정
        if (현재유저_phone_number.equals(my_phone_number)) {
            chatting_room_nick.setText(chatting_room_your_nick_data);
        } else {
            chatting_room_nick.setText(chatting_room_my_nick_data);
        }


        if (chatting_room_product_image_data == null) {
            chatting_room_product_image.setVisibility(View.GONE);
            chatting_room_nick.setVisibility(View.GONE);
            chatting_room_subject.setVisibility(View.GONE);
            chatting_room_price.setVisibility(View.GONE);
            chatting_room_price2_textview.setVisibility(View.GONE);
        } else {
            chatting_room_price.setText(chatting_room_price_data);
            chatting_room_subject.setText(chatting_room_subject_data);

            Glide.with(this)
                    .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + chatting_room_product_image_data)
                    .into(chatting_room_product_image);
        }


        chatting_room_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        chatting_room_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        chatting_room_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        /*-------------------------------------------------------------------------------------------------------------*/


        //채팅방 채팅메세지 소켓서버로 넘기기
        /*-------------------------------------------------------------------------------------------------------------*/
        //리사이클러뷰 설정
        recyclerView = findViewById(R.id.chatting_room_recyclerview);
        chatting_room_send_button = (ImageButton) findViewById(R.id.chatting_room_send_button);
        send_msg_edittext = (EditText) findViewById(R.id.chatting_room_edittext);

        Bundle extras = getIntent().getExtras();
        chating_int_room_number = extras.getInt("server_send_room_number");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (chating_int_room_number != 0) {
            chatting_room_select(chating_int_room_number, page, limit, chatting_main_out_my_check_time);

        } else {
            chatting_room_select(chatting_room_number, page, limit, chatting_main_out_my_check_time);
        }

        notificationManager.cancel(chating_int_room_number);
        //노티피케이션 제거 왜 제거 하는거지?


        //LinearLayoutManager는 리사이클러뷰의 수평으로 보여줄것인지 수직으로 보여줄것인지 설정하는것

//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
//recyclerView.stack

        /*reverselayout으로 리사이클러뷰 아이템의 스택이 뒤에서 부터 시작한다*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));


        //읽음 안읽음 표시가 안되어서 잠깐 끔끔
//       /*온크리에이트에서 시작은 가장최근의 데이터를 10개 불러온다*/
//        /*addOnScrollListener*/
//        /*무엇인가?-리사이클러뷰의 현재 위치를 알수있는 메소드*/
//        /*OnScrollListener*/
//        /*스크롤을 인지하는 클래스*/
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            /*onScrolled*/
//            /* 무엇인지?-현재 항목범위를 업데이트 하기위해서*/
//            /*  int dx, int dy 각각 수직 수평의 스크롤이 얼마나 되어있는지 확인*/
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                System.out.println("여기에 들어오는거나?");
//                // 리사이클러뷰 가장 마지막 index
//                /*LinearLayoutManager-현재 레이아웃의 상태*/
//                /*requireNonNull-마지막 아이템값이 눌포인트 npe가 나올수있기 때문에 매개변수로 가짐*/
//                /*getLayoutManager()-현재 리사이클러뷰의 레이아웃을 가져온다*/
//                /*findLastCompletelyVisibleItemPosition-마지막 아이템값을 int형으로 반환을 해준다*/
//                int lastPosition = ((LinearLayoutManager) (requireNonNull(recyclerView.getLayoutManager())))
//                        .findLastCompletelyVisibleItemPosition();
//
//
//                // 스크롤을 맨 끝까지 한 것!
//                /*lastPosition 마지막의 값과 limit은 10-1하면 9이니 스크롤해서 마지막과 같다면*/
//                /*limit +=10과 =+10의 차이점?-=+10은 쓰지도 않고 존재하지도 않음 0+10과 같다*/
//                /*chatting_room_select하면 10개 20개 30개씩 데이터를 불러오게 된다*/
//                if (lastPosition == limit - 1 && chating_int_room_number == 0) {
//                    // 이곳에 스크롤이 맨 끝에 왔을 경우
//                    // 행동할 코드 입력
//                    limit += 10;
//                    chatting_room_select(chatting_room_number, page, limit, chatting_main_out_my_check_time);
//                } else if (lastPosition == limit - 1 && chating_int_room_number != 0) {
//                    chating_int_room_number = extras.getInt("server_send_room_number");
//                    chatting_room_select(chating_int_room_number, page, limit, chatting_main_out_my_check_time);
//                }
//            }
//        });


        //나의 메세지값 저장하기
        chatting_room_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_msg_send = send_msg_edittext.getText().toString();
                send_msg_edittext.setText("");

                //서비스 시작
                destory(my_msg_send, 현재유저_nick, chatting_room_number, your_phone_number);


                //시간 코드
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);

                SimpleDateFormat simpleDate = new SimpleDateFormat("hh");
                String get_hour = simpleDate.format(mDate);

                SimpleDateFormat simpleDate2 = new SimpleDateFormat("mm");
                String get_minute = simpleDate2.format(mDate);

                int int_get_hour = Integer.parseInt(get_hour);
                if (int_get_hour > 12) {
                    int int_현재시간 = int_get_hour - 12;
                    String string_현재시간 = String.valueOf(int_현재시간);
                    시간 = "오후" + string_현재시간 + ":" + get_minute;
                } else {
                    시간 = "오전" + get_hour + ":" + get_minute;
                }
                if (현재유저_phone_number.equals(my_phone_number)) {
                    chatting_room_make(seller_image, 시간, my_msg_send, chatting_room_product_image_data, 현재유저_phone_number, your_phone_number, chatting_room_your_nick_data, 현재유저_image, 현재유저_nick, chatting_room_subject_data, chatting_room_price_data,product_number);
                } else if (현재유저_phone_number.equals(your_phone_number)) {
                    chatting_room_make(seller_image, 시간, my_msg_send, chatting_room_product_image_data, 현재유저_phone_number, my_phone_number, chatting_room_your_nick_data, 현재유저_image, 현재유저_nick, chatting_room_subject_data, chatting_room_price_data,product_number);
                }


                /**
                 * 1.chat = new chatting_room_data();가 온크리에이트에 있으면 한번만 객체가 생성이 된다 그래서 추가하고 어댑터에서 갱신을 할때 앞에있는 인덱스까지 같은 객체를 사용하니 같이 갱신이 되는것이다다
                 * */
                chat = new chatting_room_data();
                if (현재유저_phone_number.equals(my_phone_number)) {
                    boolean 확인 = chatting_service.user_확인.contains(your_phone_number);
                    if (chatting_room_number > 0) {
                        if (확인) {
                            System.out.println("메세지값1111");
                            showItemList();
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_msg_insert(my_msg_send, 시간, 현재유저_phone_number, your_phone_number, chatting_room_number, 현재유저_image, "null", 0, 0, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_read_check("null");
                            chat.setChatting_room_time(시간);
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        } else if (!확인) {
                            showItemList();
                            System.out.println("메세지값2222");
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_msg_insert(my_msg_send, 시간, 현재유저_phone_number, your_phone_number, chatting_room_number, 현재유저_image, "안읽음", 1, 0, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_time(시간);
                            chat.setChatting_room_read_check("안읽음");
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        }
                    } else {
                        if (확인) {
                            showItemList();
                            System.out.println("메세지값3333");
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_first_make_select_room_number(my_msg_send, 시간, 현재유저_phone_number, your_phone_number, 현재유저_image, "null", chatting_room_product_image_data, 0, 0, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_read_check("null");
                            chat.setChatting_room_time(시간);
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        } else if (!확인) {
                            showItemList();
                            System.out.println("메세지값4444");
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_first_make_select_room_number(my_msg_send, 시간, 현재유저_phone_number, your_phone_number, 현재유저_image, "안읽음", chatting_room_product_image_data, 1, 0, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_time(시간);
                            chat.setChatting_room_read_check("안읽음");
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        }
                    }
                } else if (현재유저_phone_number.equals(your_phone_number)) {
                    boolean 확인 = chatting_service.user_확인.contains(my_phone_number);
                    if (chatting_room_number > 0) {
                        if (확인) {
                            showItemList();
                            System.out.println("메세지값55555");
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_msg_insert(my_msg_send, 시간, 현재유저_phone_number, my_phone_number, chatting_room_number, 현재유저_image, "null", 0, 0, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_read_check("null");
                            chat.setChatting_room_time(시간);
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        } else if (!확인) {
                            showItemList();
                            System.out.println("메세지값6666");
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_msg_insert(my_msg_send, 시간, 현재유저_phone_number, my_phone_number, chatting_room_number, 현재유저_image, "안읽음", 0, 1, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_time(시간);
                            chat.setChatting_room_read_check("안읽음");
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        }
                    } else {
                        if (확인) {
                            showItemList();
                            System.out.println("메세지값7777");
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_first_make_select_room_number(my_msg_send, 시간, 현재유저_phone_number, my_phone_number, 현재유저_image, "null", chatting_room_product_image_data, 0, 0, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_read_check("null");
                            chat.setChatting_room_time(시간);
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        } else if (!확인) {
                            showItemList();
                            System.out.println("메세지값8888");
                            chatting_main_date_update(chatting_room_number, now);
                            chatting_room_first_make_select_room_number(my_msg_send, 시간, 현재유저_phone_number, my_phone_number, 현재유저_image, "안읽음", chatting_room_product_image_data, 0, 1, now);
                            chat.setChatting_room_msg(my_msg_send);
                            chat.setMy_number(현재유저_phone_number);
                            chat.setChatting_room_time(시간);
                            chat.setChatting_room_read_check("안읽음");
                            chat.setViewType(ViewType.RIGHT_CHAT);
                            room_adapter.addChat(chat);
                        }
                    }

                }

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Socket socket = chatting_service.getSocket();
                            sendWriter = new PrintWriter(socket.getOutputStream());
                            if (my_msg_send != null) {
                                if (현재유저_phone_number.equals(my_phone_number)) {
                                    sendWriter.print(my_msg_send + ",");
                                    sendWriter.print(시간 + ",");
                                    sendWriter.print(현재유저_phone_number + ",");
                                    sendWriter.print(your_phone_number + ",");
                                    sendWriter.print(string_room_number + ",");
                                    sendWriter.print(현재페이지 + ",");
                                    sendWriter.print(현재유저_image + ",");
                                    sendWriter.print(null + ",");
                                    sendWriter.print("-1" + ",");
                                    sendWriter.print(my_location + ",");
                                    sendWriter.print("읽음" + ",");
                                    sendWriter.print(chatting_room_product_image_data + ",");
                                    //현재유저 닉네임값 나중에 꼭 넣기
                                    sendWriter.print(현재유저_nick + ",");
                                    sendWriter.println("사진없음");
                                    sendWriter.flush();
                                } else if (현재유저_phone_number.equals(your_phone_number)) {
                                    sendWriter.print(my_msg_send + ",");
                                    sendWriter.print(시간 + ",");
                                    sendWriter.print(현재유저_phone_number + ",");
                                    sendWriter.print(my_phone_number + ",");
                                    sendWriter.print(string_room_number + ",");
                                    sendWriter.print(현재페이지 + ",");
                                    sendWriter.print(현재유저_image + ",");
                                    sendWriter.print(null + ",");
                                    sendWriter.print("-1" + ",");
                                    sendWriter.print(my_location + ",");
                                    sendWriter.print("읽음" + ",");
                                    sendWriter.print(chatting_room_product_image_data + ",");
                                    //현재유저 닉네임값 나중에 꼭 넣기
                                    sendWriter.print(현재유저_nick + ",");
                                    sendWriter.println("사진없음");
                                    sendWriter.flush();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }


    // 앨범에서 액티비티로 돌아온 후 실행되는 메서드
    /*-------------------------------------------------------------------------------------------------------------*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        long now = System.currentTimeMillis();
        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        String 현재유저_image = preferences.getString("user_image", "");
        String 현재유저_nick = preferences.getString("user_nick", "");
        /*-------------------------------------------------------------------------------------------------------------*/


        if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        } else {   // 이미지를 하나라도 선택한 경우
            if (data.getClipData() == null) {

                Toast.makeText(getApplicationContext(), "사진을 1장 선택을 하셨습니다.", Toast.LENGTH_LONG).show();
                Uri imageUri = data.getData();
                String imagePath = chatting_room_image_FileUtils.getPath(chatting_room.this, imageUri);
                uriList.add(imageUri);
            } else {      // 이미지를 여러장 선택한 경우
                ClipData clipData = data.getClipData();

                if (clipData.getItemCount() > 10) {   // 선택한 이미지가 11장 이상인 경우
                    Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                } else {   // 선택한 이미지가 1장 이상 10장 이하인 경우


                    //채팅 목록 아이템을 선택하면 가져오는 값
                    Intent intent = getIntent();
                    //상품등록클래스에서 가져온 유어값
                    your_phone_number = intent.getStringExtra("your_phone_number");
                    my_phone_number = intent.getStringExtra("my_phone_number");

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri image = data.getClipData().getItemAt(i).getUri();
                        String imagePath = chatting_room_image_FileUtils.getPath(chatting_room.this, image);
                        uriList.add(Uri.parse(imagePath)); // 선택한 이미지들의 uri를 가져온다.

                        //시간 객체
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        //Date 객체 사용
                        long now_image = System.currentTimeMillis();
                        Date mDate = new Date(now);

                        SimpleDateFormat simpleDate = new SimpleDateFormat("hh");
                        String get_hour = simpleDate.format(mDate);

                        SimpleDateFormat simpleDate2 = new SimpleDateFormat("mm");
                        String get_minute = simpleDate2.format(mDate);

                        int int_get_hour = Integer.parseInt(get_hour);
                        if (int_get_hour > 12) {
                            int int_현재시간 = int_get_hour - 12;
                            String string_현재시간 = String.valueOf(int_현재시간);
                            시간 = "오후" + string_현재시간 + ":" + get_minute;
                        } else {
                            시간 = "오전" + get_hour + ":" + get_minute;
                        }


                        String[] user_message = imagePath.split("/");
                        String image_adapter = user_message[5];
                        chatting_room_data chat = new chatting_room_data();
                        if (현재유저_phone_number.equals(my_phone_number)) {
                            boolean 확인 = chatting_service.user_확인.contains(your_phone_number);//유저가 들어왔는지 확인
                            if (chatting_room_number > 0) {
                                //유저가 들어와 있으면
                                if (확인) {
                                    System.out.println("여기인가?111");
                                    showItemList();
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, your_phone_number, chatting_room_number, 현재유저_image, "null", 0, 0, now_image);
                                    chat.setSend_select_image(image_adapter);//1.이미지값
                                    chat.setMy_number(현재유저_phone_number);//2.나의 번호값
                                    chat.setChatting_room_read_check("null");
                                    chat.setChatting_room_time(시간);//3
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                } else if (!확인) {
                                    System.out.println("여기인가?222");
                                    showItemList();
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, your_phone_number, chatting_room_number, 현재유저_image, "안읽음", 1, 0, now_image);
                                    chat.setSend_select_image(image_adapter);
                                    chat.setMy_number(현재유저_phone_number);
                                    chat.setChatting_room_time(시간);
                                    chat.setChatting_room_read_check("안읽음");
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                }
                            }

                            //들어와 있지 않을때때
                           else {
                                if (확인) {
                                    showItemList();
                                    System.out.println("여기인가?333");
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, your_phone_number, chatting_room_number, 현재유저_image,"null",0,0,now_image);
                                    chat.setSend_select_image(image_adapter);//1.이미지값
                                    chat.setMy_number(현재유저_phone_number);//2.나의 번호값
                                    chat.setChatting_room_read_check("null");
                                    chat.setChatting_room_time(시간);//3
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                } else if (!확인) {
                                    showItemList();
                                    System.out.println("여기인가?444");
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, your_phone_number, chatting_room_number, 현재유저_image,"안읽음",1,0,now_image);
                                    chat.setSend_select_image(image_adapter);//1.이미지값
                                    chat.setMy_number(현재유저_phone_number);
                                    chat.setChatting_room_time(시간);
                                    chat.setChatting_room_read_check("안읽음");
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                }
                            }
                        }

                        else if (현재유저_phone_number.equals(your_phone_number)) {
                            boolean 확인 = chatting_service.user_확인.contains(my_phone_number);
                            if (chatting_room_number > 0) {
                                if(확인) {
                                    System.out.println("여기인가?555");
                                    showItemList();
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, my_phone_number, chatting_room_number, 현재유저_image, "null", 0, 0, now_image);
                                    chat.setSend_select_image(image_adapter);//1.이미지값
                                    chat.setMy_number(현재유저_phone_number);//2.나의 번호값
                                    chat.setChatting_room_read_check("null");
                                    chat.setChatting_room_time(시간);//3
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                } else if(!확인) {
                                    System.out.println("여기인가?666");
                                    showItemList();
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, my_phone_number, chatting_room_number, 현재유저_image, "안읽음", 0, 1, now_image);
                                    chat.setSend_select_image(image_adapter);//1.이미지값
                                    chat.setMy_number(현재유저_phone_number);//2.나의 번호값
                                    chat.setChatting_room_read_check("안읽음");
                                    chat.setChatting_room_time(시간);//3
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                }

                            }else {
                                if(확인) {
                                    System.out.println("여기인가?777");
                                    showItemList();
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, my_phone_number, chatting_room_number, 현재유저_image, "null", 0, 0, now_image);
                                    chat.setSend_select_image(image_adapter);//1.이미지값
                                    chat.setMy_number(현재유저_phone_number);//2.나의 번호값
                                    chat.setChatting_room_read_check("null");
                                    chat.setChatting_room_time(시간);//3
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                } else if(!확인) {
                                    System.out.println("여기인가?888");
                                    showItemList();
                                    chatting_main_date_update(chatting_room_number, now);
                                    chatting_room_image_insert(시간, 현재유저_phone_number, my_phone_number, chatting_room_number, 현재유저_image, "안읽음", 0, 1, now_image);
                                    chat.setSend_select_image(image_adapter);//1.이미지값
                                    chat.setMy_number(현재유저_phone_number);//2.나의 번호값
                                    chat.setChatting_room_read_check("안읽음");
                                    chat.setChatting_room_time(시간);//3
                                    chat.setViewType(ViewType.RIGHT_CHAT);
                                    room_adapter.addChat(chat);
                                }
                            }
                        }


                        //------------------------------------------------------------------------------------------
                        /*나의 이미지값을 서버에 보내는곳*/
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    Socket socket = chatting_service.getSocket();
                                    sendWriter = new PrintWriter(socket.getOutputStream());
                                    if (my_msg_send == null) {
                                        if (현재유저_phone_number.equals(my_phone_number)) {
                                            //순서
                                            //메세지,시간,쉐어드 폰넘버,너의 폰넘버,채팅룸 넘버,현재페이지,룸넘버,선택한 이미지,현재유저 이미지,사진이 도착했습니다
                                            sendWriter.print(my_msg_send + ",");//1.메세지값
                                            sendWriter.print(시간 + ",");//2.현재시간
                                            sendWriter.print(현재유저_phone_number + ",");//3나의번호
                                            sendWriter.print(your_phone_number + ",");//4너의번호
                                            sendWriter.print(string_room_number + ",");//5채팅룸넘버
                                            sendWriter.print(현재페이지 + ",");//6현재페이지
                                            sendWriter.print(현재유저_image + ",");//7.나의 유저 이미지
                                            sendWriter.print(image_adapter + ",");//8이미지보내기
                                            sendWriter.print("-1" + ",");//9삭제할아이템번호
                                            sendWriter.print(my_location + ",");//10나의현재위치
                                            sendWriter.print("읽음" + ",");//11읽음표시
                                            sendWriter.print(chatting_room_product_image_data + ",");//12상품이미지
                                            sendWriter.print(현재유저_nick + ",");//13나의 닉네임
                                            sendWriter.println("사진이 도착했습니다");//14사진도착유무
                                            sendWriter.flush();

                                        } else if (현재유저_phone_number.equals(your_phone_number)) {
                                            sendWriter.print(my_msg_send + ",");//1.메세지값
                                            sendWriter.print(시간 + ",");//2.현재시간
                                            sendWriter.print(현재유저_phone_number + ",");//3나의번호
                                            sendWriter.print(my_phone_number + ",");//4너의번호
                                            sendWriter.print(string_room_number + ",");//5채팅룸넘버
                                            sendWriter.print(현재페이지 + ",");//6현재페이지
                                            sendWriter.print(현재유저_image + ",");//7.나의 유저 이미지
                                            sendWriter.print(image_adapter + ",");//8이미지보내기
                                            sendWriter.print("-1" + ",");//9삭제할아이템번호
                                            sendWriter.print(my_location + ",");//10나의현재위치
                                            sendWriter.print("읽음" + ",");//11읽음표시
                                            sendWriter.print(chatting_room_product_image_data + ",");//12상품이미지
                                            sendWriter.print(현재유저_nick + ",");//13나의 닉네임
                                            sendWriter.println("사진이 도착했습니다");//14사진도착유무
                                            sendWriter.flush();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        try {

                        } catch (Exception e) {

                        }
                    }

                }
            }
        }
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    //채팅방 만들기
    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_room_make(String your_profile_image, String time, String last_msg, String product_image, String my_number, String your_number, String your_nick, String my_profile_image, String my_nick, String subject, String price,int product_number) {
        long now = System.currentTimeMillis();
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<String> call = chatting_Interface.chatting_room_make(your_profile_image, time, last_msg, product_image, my_number, your_number, your_nick, my_profile_image, my_nick, subject, price, now,product_number);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    //채팅방 이미지 insert

    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_room_image_insert(String 시간,String 현재유저_phone_number,String your_phone_number, int chatting_room_number, String your_profile_image, String chatting_room_read_check, int chatting_room_my_read_check,int chatting_room_your_read_check,long chatting_room_date) {
        List<MultipartBody.Part> list = new ArrayList<>();
        for (Uri uri : uriList) {
            list.add(prepairFiles("file[]", uri));
        }

        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        System.out.println(list+"list  "+시간+"시간  "+현재유저_phone_number+"현재유저_phone_number  "+your_phone_number+"your_phone_number  "+chatting_room_number+"chatting_room_number  "+your_profile_image+"your_profile_image  "+chatting_room_read_check+"chatting_room_read_check  "+chatting_room_my_read_check+"chatting_room_my_read_check  "+chatting_room_your_read_check+"chatting_room_your_read_check  "+chatting_room_date+"chatting_room_date  ");
        Call<chatting_room_data> call = chatting_Interface.chatting_room_image_insert(list,시간,현재유저_phone_number,your_phone_number,chatting_room_number,your_profile_image,chatting_room_read_check,chatting_room_my_read_check,chatting_room_your_read_check,chatting_room_date);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }


    //URI를 파일로 바꾸는 과정 왜냐하면 서버로 파일 형태로 보내야 되기 때문에
    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());

        // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
        //위에서 URI를 파일형태로 바꾸었는데 그것을 또 객체로 만듬
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        //MultipartBody를 객체로 형성이 된것을 String형태로 바꿔줌
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    //채팅방 텍스트 메세지 insert
    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_room_msg_insert(String chatting_room_msg, String chatting_room_time, String my_number, String your_number, int room_number, String your_profile_image, String read, int chatting_room_my_read_check, int chatting_room_your_read_check, long chatting_room_date) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_room_msg_insert(chatting_room_msg, chatting_room_time, my_number, your_number, room_number, your_profile_image, read, chatting_room_my_read_check, chatting_room_your_read_check, chatting_room_date);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    //채팅방 데이터값 가져오기
    private void chatting_room_select(int room_number, int page, int limit, long chatting_main_out_my_check_time) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<ArrayList<chatting_room_data>> call = chatting_Interface.chatting_room_select(room_number, page, limit, chatting_main_out_my_check_time);
        call.enqueue(new Callback<ArrayList<chatting_room_data>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<chatting_room_data>> call, @NonNull Response<ArrayList<chatting_room_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());
                }
            }


            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<ArrayList<chatting_room_data>> call, @NonNull Throwable t) {
                Log.e("chatting_room_select_my_message", "에러 : " + t.getMessage());
            }
        });
    }


    private void onGetResult(ArrayList<chatting_room_data> lists) {
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        String 현재유저_image = preferences.getString("user_image", "");
        room_adapter = new chatting_room_Adapter(this, lists, 현재유저_phone_number, sendWriter, your_phone_number, 현재유저_image);
        recyclerView.setAdapter(room_adapter);
        recyclerView.scrollToPosition(limit - 10);
        list = lists;
    }


    //채팅방 데이터값 가져오기
    private void chatting_room_first_make_select_room_number(String chatting_room_msg, String chatting_room_time, String my_number, String your_number, String your_profile_image, String read, String chatting_room_product_image_data, int chatting_room_my_read_check, int chatting_room_your_read_check, long chatting_room_date) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_main_data> call = chatting_Interface.chatting_room_first_make_select_room_number(my_number, your_number);
        call.enqueue(new Callback<chatting_main_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_main_data> call, @NonNull Response<chatting_main_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int room_nubmer = response.body().getChatting_main_idx();
                    chatting_room_msg_insert(chatting_room_msg, chatting_room_time, my_number, your_number, room_nubmer, your_profile_image, read, chatting_room_my_read_check, chatting_room_your_read_check, chatting_room_date);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<chatting_main_data> call, @NonNull Throwable t) {
                Log.e("chatting_room_select_my_message", "에러 : " + t.getMessage());
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        String 현재유저_image = preferences.getString("user_image", "");
        String 현재유저_nick = preferences.getString("user_nick", "");
        /*-------------------------------------------------------------------------------------------------------------*/
        chatting_room_read_update(chatting_room_number);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Socket socket = chatting_service.getSocket();
                    sendWriter = new PrintWriter(socket.getOutputStream());
                    sendWriter.print(null + ",");
                    sendWriter.print(null + ",");
                    sendWriter.print(현재유저_phone_number + ",");
                    sendWriter.print(your_phone_number + ",");
                    sendWriter.print("-1" + ",");
                    sendWriter.print("chatting_room" + ",");
                    sendWriter.print(null + ",");
                    sendWriter.print(null + ",");
                    sendWriter.print("-1" + ",");
                    sendWriter.print(null + ",");
                    sendWriter.print("나감" + ",");
                    sendWriter.print(null + ",");
                    sendWriter.print(null + ",");
                    sendWriter.println("사진없음");
                    sendWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    static class chatting_room_Handler_user extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String server_send_your_phone_number = bundle.getString("server_send_your_phone_number");
            if (server_send_your_phone_number == null) {
            } else {
                boolean 확인 = chatting_service.user_확인.contains(server_send_your_phone_number);
                if (확인) {
                    room_adapter.read_check();
                }
            }

        }
    }


    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_room_read_update(int chatting_room_number) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_room_read_update(chatting_room_number);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_main_date_update(int chatting_room_number, long date) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_main_date_update(chatting_room_number, date);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    public void showItemList() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(0);
            }
        }, 100);
    }


    public void destory(String msg, String nick, int room_number, String your_phone_number) {
        // 앱 실행시 Background Service 실행
        Intent serviceintent = new Intent(this, chatting_service.class);
        serviceintent.putExtra("server_send_msg", msg);
        serviceintent.putExtra("server_send_nick", nick);
        serviceintent.putExtra("server_send_number", your_phone_number);
        serviceintent.putExtra("server_send_room_number", room_number);
        startService(serviceintent);
    }


    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_main_read_check_update(int chatting_room_number, String my_number, String your_number) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_main_read_check_update(chatting_room_number, my_number, your_number);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    @Override
    public void onResume() {
        super.onResume();
        chatting_room_menu = (ImageButton) findViewById(R.id.chatting_room_menu_on);
        //알림 on -> off

        chatting_room_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*-------------------------------------------------------------------------------------------------------------*/
                SharedPreferences preferences = getSharedPreferences("현재유저", 0);
                String 현재유저_phone_number = preferences.getString("user_number", "");

                pref = getSharedPreferences(string_room_number + 현재유저_phone_number, MODE_PRIVATE);
                noti_on_off_shared = pref.getString("알림상태", "null");
                System.out.println(noti_on_off_shared + "noti_on_off_shared");


                //알림 off -> on
                if (noti_on_off_shared.equals("off")) {
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), chatting_room_menu);
                    //getMenuInflater
                    //무엇인가?팝메뉴를 사용하기위한 메소드
                    //왜 사용하는가?
                    //어떻게 사용하는가? 프래그먼트에서는 어떤 객체인지 확실해야 되서 popupMenu를 붙여줘야 된다->인플레이터 함수를 이용해서 메뉴에 있는 값을 가지고 온다
                    popupMenu.getMenuInflater().inflate(R.menu.noti_on_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override

                        //boolean onMenuItemClick(MenuItem menuItem)
                        //무엇인가?팝업메뉴를 사용할때 사용한다
                        //왜 사용하는가?
                        //어떻게 사용하는가?
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.noti_on:
                                    if (현재유저_phone_number.equals(my_phone_number)) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.remove("알림상태");
                                        editor.apply();
                                    } else if (현재유저_phone_number.equals(your_phone_number)) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.remove("알림상태");
                                        editor.apply();
                                    }
                                    break;
                                case R.id.noti_on_chatting_room_out:
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("알림상태");
                                    editor.apply();
                                    dialog();
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }      //알림 on -> off
                else if (noti_on_off_shared.equals("null")) {
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), chatting_room_menu);

                    //getMenuInflater
                    //무엇인가?팝메뉴를 사용하기위한 메소드
                    //왜 사용하는가?
                    //어떻게 사용하는가? 프래그먼트에서는 어떤 객체인지 확실해야 되서 popupMenu를 붙여줘야 된다->인플레이터 함수를 이용해서 메뉴에 있는 값을 가지고 온다
                    popupMenu.getMenuInflater().inflate(R.menu.noti_off_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("NonConstantResourceId")
                        @Override
                        //boolean onMenuItemClick(MenuItem menuItem)
                        //무엇인가?팝업메뉴를 사용할때 사용한다
                        //왜 사용하는가?
                        //어떻게 사용하는가?
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.noti_off:
                                    if (현재유저_phone_number.equals(my_phone_number)) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("알림상태", "off");
                                        editor.apply();
                                    } else if (현재유저_phone_number.equals(your_phone_number)) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("알림상태", "off");
                                        editor.apply();
                                    }
                                    break;
                                case R.id.noti_off_chatting_room_out:
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("알림상태");
                                    editor.apply();
                                    dialog();
                                    break;

                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            }
        });
    }


    //다이얼로그 실행
    public void dialog() {
        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        String 현재유저_nick = preferences.getString("user_nick", "");
//        dilaog.getWindow().setGravity(Gravity.BOTTOM);
        chatting_room_out.show(); // 다이얼로그 띄우기
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


        // 아니오 버튼
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        Button cancle_button = chatting_room_out.findViewById(R.id.chatting_room_out_cancle_check_button);
        cancle_button.findViewById(R.id.chatting_room_out_cancle_check_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                chatting_room_out.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        chatting_room_out.findViewById(R.id.chatting_room_out_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (현재유저_phone_number.equals(my_phone_number)) {

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Socket socket = chatting_service.getSocket();
                                sendWriter = new PrintWriter(socket.getOutputStream());
                                sendWriter.print("나갔습니다" + ",");
                                sendWriter.print(시간 + ",");
                                sendWriter.print(현재유저_phone_number + ",");
                                sendWriter.print(your_phone_number + ",");
                                sendWriter.print(string_room_number + ",");
                                sendWriter.print(현재페이지 + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print("-1" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(chatting_room_product_image_data + ",");
                                //현재유저 닉네임값 나중에 꼭 넣기
                                sendWriter.print(현재유저_nick + ",");
                                sendWriter.println("사진없음");
                                sendWriter.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    chatting_room_out_my_update(chatting_room_number, 현재유저_phone_number, your_phone_number, 현재유저_nick);
                    onBackPressed();
                } else if (현재유저_phone_number.equals(your_phone_number)) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Socket socket = chatting_service.getSocket();
                                sendWriter = new PrintWriter(socket.getOutputStream());
                                sendWriter.print("나갔습니다" + ",");
                                sendWriter.print(시간 + ",");
                                sendWriter.print(현재유저_phone_number + ",");
                                sendWriter.print(my_phone_number + ",");
                                sendWriter.print(string_room_number + ",");
                                sendWriter.print(현재페이지 + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print("-1" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(chatting_room_product_image_data + ",");
                                //현재유저 닉네임값 나중에 꼭 넣기
                                sendWriter.print(현재유저_nick + ",");
                                sendWriter.println("사진없음");
                                sendWriter.flush();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    chatting_room_out_your_update(chatting_room_number, 현재유저_phone_number, my_phone_number);
                    onBackPressed();
                }
            }
        });
    }


    //my 삭제하기 업데이트
    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_room_out_my_update(int chating_int_room_number, String my_number, String your_number, String 현재유저_nick) {
        long now = System.currentTimeMillis();
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_room_out_my_update(chating_int_room_number, "삭제하기", my_number, your_number, "나갔습니다", now);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    //your 노티알림 업데이트
    /*-------------------------------------------------------------------------------------------------------------*/
    private void chatting_room_out_your_update(int chating_int_room_number, String my_number, String your_number) {
        long now = System.currentTimeMillis();
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_room_out_your_update(chating_int_room_number, "삭제하기", my_number, your_number, "나갔습니다", now);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()_your", "에러 : " + t.getMessage());
            }
        });
    }
    /*-------------------------------------------------------------------------------------------------------------*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
         * 다이얼로그는 대화라는 뜻인데 대화하다가 헤어질때 그냥 가는것이 아니라 '먼저 들어가겠습니다'라고 말하는 것처럼 컴퓨터도 dismiss()를 해줘야 컴퓨터는 대화를 멈추겠다는 신호이다
         * 그래서 dismiss를 디스토리에 넣든지 닫기전에 dismiss를 넣어줘야 된다
         * */
        chatting_room_out.dismiss();
    }
}








