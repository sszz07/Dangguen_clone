package org.techtown.dang_guen.dong_nae_life;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_main extends Fragment {

    String 클래스 = "dong_nae_life_main";
    ImageButton dong_nae_life_category_button;
    RecyclerView recyclerView;
    dong_nae_life_main_adapter adapter;
    dong_nae_life_main_adapter.ItemClickListener itemClickListener;
    List<dong_nae_life_data> list = new ArrayList<>();
    View view;

    @Nullable
    @Override
    //View onCreateView
    //무엇인가? 콜백 메소드로 인플레이터가 필요한 시점에 자동으로 호출이 된다
    //왜 사용하는가? 다른 프레그먼트로 이동하기 위해서
    //어떻게 사용하는가? 자동으로 생성이 된다
    //괄호 왼쪽부터 xml을 객체로 반환하는값,해당 프래그먼트 레이아웃을 가린킨다,해당 프래그먼트를 저장한다
    //(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)

    //LayoutInflater inflater(부풀리다)
    //무엇인가?레이아웃을 인플레이트로 사용
    //왜 사용하는가?프래그먼트에서 레이아웃을 객체로 반환이 된다
    //어떻게 사용하는가?inflater.inflate(해당 클래스 레이아웃클래스명을 입력한다)

    //ViewGroup container
    //무엇인가? 프래그먼트를 포함한 액티비티의 레이아웃을 가리킨다.
    //왜 사용하는가? 해당 프래그먼트의 액티비티를 컴퓨터에 알리기 위해서
    //어떻게 사용하는가? onCreateView(@Nullable ViewGroup container)를 넣어줘야 된다

    //Bundle savedInstanceState
    //무엇인가? 프래그먼트의 상태를 저장했다가 다시 살려낼 때 사용한다.
    //왜 사용하는가? 만약 다른 프래그먼트로 이동을 할때 저장을 해야된다
    //어떻게 사용하는가?onCreateView(@Nullable Bundle savedInstanceState)

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //View view
        //무엇인가?레이아웃의 요소이다
        //왜 사용하는가? 프래그먼트안에 레이아웃을 사용하기 위해서
        //어떻게 사용하는가? View view클래스를 객체로 만든다 인플레이터를 시켜서 해당 프래그먼트 레이아웃을 가지고 온다 컨테이너를 넣는 이유는 어떤 레이아웃을 가르키는지 알기 위해서

        //attachToRoot
        //무엇인가?true이면 부모뷰에 자식뷰를 바로 붙이는 경우,false는 나중에 붙여도 되는 뷰
        //왜 사용하는가? 만약에 true로 하게 될 경우에 IllegalStateException처리가 된다 왜냐하면 해당 코드는 하위뷰로 붙일 의무가 없다 즉시 붙여주기 때문에 그래서 무조건 false 이다
        //어떻게 사용하는가?프래그먼트에서는 무조건 false!!!


        //IllegalStateException
        //무엇인가? 메소드가 요구된 처리를 하기에 적합한 상태에 있지 않을때
        view = inflater.inflate(R.layout.activity_dong_nae_life_main, container, false);
        dong_nae_life_category_button = (ImageButton) view.findViewById(R.id.dong_nae_life_category_button);


        /**
         * return 위치별 알기
         * */
        //        return view;


        /**
         Attempt to invoke virtual method 'void androidx.recyclerview.widget.RecyclerView.setLayoutManager(androidx.recyclerview.widget.RecyclerView$LayoutManager)' on a null object reference
         * */



        /*
         *SharedPreferences
         * 사전적 의미-공유(두사람 이상이 공동으로 사용)하는것을 선호(여러개 중에 특별히 종아함)
         *
         * 무엇인가? 앱 내부 로컬에 저장 또는 데이터를 파일로 저장을 한다
         *
         * 왜 사용하는가? 간단한 데이터를 저장 하며 사용하기 위해서
         *
         * 프래그먼트에서 어떻게 사용하는가?
         * 프래그먼트는 액티비티가 아니기에 this.getActivity()을 붙어줘야 하고 Context.MODE_PRIVATE 도 넣는다 그 다음은 같음
         * */
        SharedPreferences preferences = this.getActivity().getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String SharedPreferences_phone = preferences.getString("user_number", "");
        System.out.println(SharedPreferences_phone + "폰값 확인");


        itemClickListener = new dong_nae_life_main_adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int idx = list.get(position).getIdx();
                Log.e("idx 데이터 확인", String.valueOf(idx));

                String subject = list.get(position).getSubject();
                Log.e("subject 데이터 확인", subject);

                String content = list.get(position).getContent();
                Log.e("content 데이터 확인", content);

                String phone = list.get(position).getPhone();
                System.out.println(phone + "폰번호 확인");

                String board_user_image = list.get(position).getUser_image();
                Log.e("user_image 데이터 확인", board_user_image);

                String board_user_nick = list.get(position).getNick();
                Log.e("user_nick 데이터 확인", board_user_nick);

                long present_time = list.get(position).getPresent_time();

                //---------------------------------------------------------------------
                String dong_nae_life_main_together_subject = list.get(position).getSubject_together();
                Log.e("user_nick 데이터 확인", board_user_nick);
                String dong_nae_life_main_together_content = list.get(position).getContent_together();
                Log.e("user_nick 데이터 확인", board_user_nick);
                String dong_nae_life_main_together_age = list.get(position).getPerson_together();
                Log.e("user_nick 데이터 확인", board_user_nick);
                String dong_nae_life_main_together_clock = list.get(position).getClock_together();
                Log.e("user_nick 데이터 확인", board_user_nick);
                String dong_nae_life_main_together_date = list.get(position).getDate_together();
                Log.e("user_nick 데이터 확인", board_user_nick);
                String dong_nae_life_main_together_person = list.get(position).getPerson_together();
                Log.e("user_nick 데이터 확인", board_user_nick);
                String dong_nae_life_main_together_nick = list.get(position).getNick();
                Log.e("user_nick 데이터 확인", board_user_nick);
                String dong_nae_life_main_together_location = list.get(position).getLocation_together();
                Log.e("user_nick 데이터 확인", board_user_nick);


//                //int형일때는 == 이것을 사용하지만 String형태일때는 이퀄스를 사용한다
                if (SharedPreferences_phone.equals(phone)) {
                    /*
                     * Intent란?
                     * 사전적 의미-강한 관심(마음이 끌려감),몰두(일에 정신을 다함)
                     *
                     * 무엇인가? 4대 컴포넌트(여러가지 구성을 하나로 만드는것)를 통신(소식을 전하다,자료를 보내다)하는것
                     *
                     * 왜 사용하는가? 각 액티비티에 통신을 하기 위해서
                     *
                     * 어떻게 사용하는가?
                     * 인텐트로 데이터를 보내는법
                     * 1)Intent intent = new Intent(getContext(), product_my_select.class);
                     * -인텐트의 객체를 새롭게 만들고 매개변수 안에는(현재 클래스,데이터를 보낼 클래스)
                     * [현재 액티비티는 여기고 저 클래스로 데이터를 보낼거임]
                     *
                     * 2)intent.putExtra("idx", idx);
                     * -putExtra("키값",데이터값)
                     * [저 클래스로 보내고 키값은 idx이거고 이거 없으면 데이터 못얻음,데이터는 idx 이거임임
                     *
                     * 3)startActivity(intent);
                     * [이동하자!!]
                     * */
                    Intent intent = new Intent(getContext(), dong_nae_life_my_select.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    intent.putExtra("board_user_image", board_user_image);
                    intent.putExtra("board_user_nick", board_user_nick);
                    intent.putExtra("present_time", present_time);
                    startActivity(intent);
                }else if(dong_nae_life_main_together_subject!=null){
                    //---------------------------------------------------------------------
                    Intent intent = new Intent(getContext(), dong_nae_life_my_select_together.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("dong_nae_life_main_together_subject", dong_nae_life_main_together_subject);
                    intent.putExtra("dong_nae_life_main_together_content", dong_nae_life_main_together_content);
                    intent.putExtra("dong_nae_life_main_together_age", dong_nae_life_main_together_age);
                    intent.putExtra("dong_nae_life_main_together_date", dong_nae_life_main_together_date);
                    intent.putExtra("dong_nae_life_main_together_person", dong_nae_life_main_together_person);
                    intent.putExtra("dong_nae_life_main_together_nick", dong_nae_life_main_together_nick);
                    intent.putExtra("dong_nae_life_main_together_location", dong_nae_life_main_together_location);
                    intent.putExtra("dong_nae_life_main_together_clock", dong_nae_life_main_together_clock);
                    startActivity(intent);

                 }

                else {
                    Intent intent = new Intent(getContext(), dong_nae_life_other_select.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    intent.putExtra("board_user_image", board_user_image);
                    intent.putExtra("board_user_nick", board_user_nick);
                    intent.putExtra("present_time", present_time);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                }
            }
        };


        dong_nae_life_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PopupMenu
                //무엇인가?버튼이나 이미지 버튼을 눌렀을때 메뉴의 기능,사용하기위한 객체 생성
                //왜 사용하는가?
                //어떻게 사용하는가? res폴더에 메뉴폴더를 만들고 메뉴객체 넣는다
                //특징? OptionMenu나 Context메뉴처럼 액티비티에 create하는 메소드가 존재하지 않음.


                //getContext()
                //무엇인가?현재 실행되고 있는 뷰 context를 리턴한다 프래그먼트에서 현재 화면에 보이는 xml를 보고있는것 class명.this와 같은말
                //getApplicationContext는 영어를 해석한 그래도 앱 전체를 가지고 온다는 의미이다
                //왜 사용하는가?
                //어떻게 사용하는가?
                PopupMenu popupMenu = new PopupMenu(getContext(), dong_nae_life_category_button);


                //getMenuInflater
                //무엇인가?팝메뉴를 사용하기위한 메소드
                //왜 사용하는가?
                //어떻게 사용하는가? 프래그먼트에서는 어떤 객체인지 확실해야 되서 popupMenu를 붙여줘야 된다->인플레이터 함수를 이용해서 메뉴에 있는 값을 가지고 온다


                //inflate()
                //무엇인가?
                //왜 사용하는가?
                //어떻게 사용하는가?
                popupMenu.getMenuInflater().inflate(R.menu.dong_nae_life_make_button, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override

                    //boolean onMenuItemClick(MenuItem menuItem)
                    //무엇인가?팝업메뉴를 사용할때 사용한다
                    //왜 사용하는가?
                    //어떻게 사용하는가?
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item1:
                                Intent intent = new Intent(getActivity(), dong_nae_life_make_together.class);
                                startActivity(intent);
                                break;
                            case R.id.item2:
                                user_image();
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        //return
        //무엇인가?-현재 실행중인 메소드를 종료하고 호출한 메소드로 되돌아가는것
        //왜 사용하는가?함수는 기계임니다
        //
        //
        //
        //너가 버스를 탈 때 카드 찍고 탈수 잇는 것도
        //
        //카드 기계가 삑! 소리와 함께 초록불을 리턴 했기 때문입니다.
        //
        //
        //
        //계산기를 쓰는 것도 너가 입력한 값에 대한
        //
        //결과를 계산기가 리턴했기 때문입니다.
        //
        //
        //
        //너가 개드립에 글을 쓸 수 있는 것도 타자를 치면
        //
        //컴퓨터가 입력값을 리턴했기 때문입니다.
        //
        //
        //
        //결과를 받아야 하는 함수는 보통 리턴값이 있습니다.

        //함수가 있으면 '함수 자체' 가 있고 '그 함수를 부르는 놈'
        //
        //이렇게 2명이 있음
        //
        //리턴이라는 것은 그 함수를 부르는 놈에게 함수가 어떤 값을 쥐어준다는 것임.

        //어떻게 사용하는가?

        //프래그먼트에서 버튼을 누르게 되면 xml의 값을 반환을 해야 xml을 볼수가 있다
        return view;

    }





    //상품 추가 할때 유저 이미지 데이터를 불러오기 위함
    private void user_image() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String phone = preferences.getString("user_number", "");
        System.out.println(phone + "dong_nae_life_main 폰값 확인");

        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.user_select_image(phone);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("들어오는지 확인");
                    String user_image = response.body().getImage();
                    System.out.println(user_image + "이미지값 확인");
                    Intent intent = new Intent(getActivity(), dong_nae_life_make.class);
                    intent.putExtra("user_image", user_image);
                    startActivity(intent);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("text_select", "에러 : " + t.getMessage());
            }
        });
    }





    @Override
    public void onResume() {
        super.onResume();
        dong_nae_life_category_button = (ImageButton) view.findViewById(R.id.dong_nae_life_category_button);
        recyclerView = (RecyclerView) view.findViewById(R.id.dong_nae_life_main_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));


        dong_nae_life_recycerview_select();
    }


    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .
    private void  dong_nae_life_recycerview_select() {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<List<dong_nae_life_data>> call = ApiInterface.dong_nae_life_recyclerview_select();
        call.enqueue(new Callback<List<dong_nae_life_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<dong_nae_life_data>> call, @NonNull Response<List<dong_nae_life_data>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());
                    for (int i = 0; i < response.body().size(); i++) {


                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<dong_nae_life_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<dong_nae_life_data> lists) {
        adapter = new dong_nae_life_main_adapter(getActivity(), lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }



}