package org.techtown.dang_guen.home;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.techtown.dang_guen.dong_nae_life.dong_nae_life_ApiClient;
import org.techtown.dang_guen.dong_nae_life.dong_nae_life_ApiInterface;
import org.techtown.dang_guen.dong_nae_life.dong_nae_life_data;
import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Fragment
//무엇인가?레이아웃안에 레이아웃이 있는것,레이아웃이나 소스코드를 한 번만 정의하고 재사용할 수 있도록 만든 것이 프래그먼트이다.
//프래그먼트에서 명령이나 데이터 이동은 메소드를 통해서 전달이 된다
//프래그먼트가 작동이 되지 않는것은 액티비티위에 있지 않아서 이다

//왜 사용하는가? 두 개 이상의 Activity를 하나의 화면에 구성하는 것이 불가능하기 때문에 나오게 된 것이 바로 Fragment이다
//어떻게 사용하는가?
public class home extends Fragment {
    NestedScrollView nestedScrollView;
    ImageButton home_product_make_button, home_category_image_button,home_search_image_button;
    RecyclerView recyclerView;
    home_adapter adapter;
    home_adapter.ItemClickListener itemClickListener;
    List<product_data> list = new ArrayList<>();
    View view;
    int page = 0, limit = 10;

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
        view = inflater.inflate(R.layout.activity_home, container, false);
        home_product_make_button = (ImageButton) view.findViewById(R.id.home_category_button);
        home_category_image_button = (ImageButton) view.findViewById(R.id.home_category_image_button);
        home_search_image_button = (ImageButton) view.findViewById(R.id.home_search_image_button);


        /*게시물 죄회수 코드----------------------------------------------------------------------------------------------------------------*/
        nestedScrollView = view.findViewById(R.id.scroll_view);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.e("페이징 확인", String.valueOf(page));
                    Log.e("리미트 확인", String.valueOf(limit));
                    limit += 10;
                    Log.e("page +", String.valueOf(page));
                    Log.e("limit", String.valueOf(limit));
                    home_recycerview_select(page, limit);
                }
            }
        });
        /*-------------------------------------------------------------------------------------------------------------------------------*/


        /**
         Attempt to invoke virtual method 'void androidx.recyclerview.widget.RecyclerView.setLayoutManager(androidx.recyclerview.widget.RecyclerView$LayoutManager)' on a null object reference
         * */

        user_image();
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


        itemClickListener = new home_adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                int idx = list.get(position).getIdx();
                Log.e("idx 데이터 확인", String.valueOf(idx));

                String subject = list.get(position).getSubject();
                Log.e("subject 데이터 확인", subject);

                String content = list.get(position).getContent();
                Log.e("content 데이터 확인", content);

                String price = list.get(position).getPrice();
                System.out.println(price + "price 확인");

                String phone = list.get(position).getPhone();
                System.out.println(phone + "폰번호 확인");

                String seller_image = list.get(position).getUser_image();
                Log.e("user_image 데이터 확인", seller_image);

                String seller_nick = list.get(position).getNick();
                Log.e("user_nick 데이터 확인", seller_nick);

                String category = list.get(position).getCategory();
                Log.e("category 데이터 확인", category);

                String chatting = list.get(position).getChatting();
                Log.e("chatting 데이터 확인", chatting);


                String image = list.get(position).getImage();
                Log.e("image 데이터 확인", image);

                int hits_count = list.get(position).getHits();

                long present_time = list.get(position).getPresent_time();

                int like_count = list.get(position).getLike_count();



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

                    Intent intent = new Intent(getContext(), product_my_select.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    intent.putExtra("price", price);
                    intent.putExtra("seller_image", seller_image);
                    intent.putExtra("seller_nick", seller_nick);
                    intent.putExtra("category", category);
                    intent.putExtra("like_count", like_count);
                    intent.putExtra("present_time", present_time);
                    intent.putExtra("hits_count", hits_count);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), product_other_select.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", subject);
                    intent.putExtra("content", content);
                    intent.putExtra("price", price);
                    intent.putExtra("seller_image", seller_image);
                    intent.putExtra("seller_nick", seller_nick);
                    intent.putExtra("category", category);
                    intent.putExtra("chatting", chatting);
                    intent.putExtra("image", image);
                    intent.putExtra("phone", phone);
                    intent.putExtra("like_count", like_count);
                    intent.putExtra("present_time", present_time);
                    intent.putExtra("hits_count", hits_count);
                    startActivity(intent);
                }
            }
        };


        home_product_make_button.setOnClickListener(new View.OnClickListener() {
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
                PopupMenu popupMenu = new PopupMenu(getContext(), home_product_make_button);


                //getMenuInflater
                //무엇인가?팝메뉴를 사용하기위한 메소드
                //왜 사용하는가?
                //어떻게 사용하는가? 프래그먼트에서는 어떤 객체인지 확실해야 되서 popupMenu를 붙여줘야 된다->인플레이터 함수를 이용해서 메뉴에 있는 값을 가지고 온다


                //inflate()
                //무엇인가?
                //왜 사용하는가?
                //어떻게 사용하는가?
                popupMenu.getMenuInflater().inflate(R.menu.home_make_product_button, popupMenu.getMenu());


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
                                Intent intent = new Intent(getActivity(), product_make.class);
                                startActivity(intent);
                                break;


                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });


        home_category_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), junggo_category.class);
                startActivity(intent);


            }
        });



        home_search_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), product_search.class);
                startActivity(intent);


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
        System.out.println(phone + "폰값 확인");
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.user_select_image(phone);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String user_image = response.body().getImage();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_image", user_image);
                    editor.apply();
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("text_select", "에러 : " + t.getMessage());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        home_recycerview_select(page,limit);
    }




    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .
    private void home_recycerview_select(int page, int limit) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = ApiInterface.home_recyclerview_select(page, limit);
        call.enqueue(new Callback<List<product_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<product_data>> call, @NonNull Response<List<product_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        System.out.println(response.body().get(i).getIdx() + "idx번호 확인");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<product_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<product_data> lists) {
        adapter = new home_adapter(getActivity(), lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }
}