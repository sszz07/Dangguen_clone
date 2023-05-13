package org.techtown.dang_guen.chatting;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import org.techtown.dang_guen.home.home_adapter;
import org.techtown.dang_guen.home.junggo_category;
import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.chatting.chatting_main_data;
import org.techtown.dang_guen.home.product_make;
import org.techtown.dang_guen.home.product_my_select;
import org.techtown.dang_guen.home.product_other_select;
import org.techtown.dang_guen.home.product_search;
import org.techtown.dong_nae_life.R;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chatting_main extends Fragment {

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    chatting_main_adapter.ItemClickListener itemClickListener;
    static List<chatting_main_data> list = new ArrayList<>();
    View view;
    int page = 0, limit = 10;
    static chatting_main_adapter main_adapter;
    PrintWriter sendWriter;

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
        view = inflater.inflate(R.layout.activity_chatting_main, container, false);

        //유저의 현재 방확인


        /*게시물 죄회수 코드----------------------------------------------------------------------------------------------------------------*/
        nestedScrollView = view.findViewById(R.id.scroll_view_chatting_main);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.e("페이징 확인", String.valueOf(page));
                    Log.e("리미트 확인", String.valueOf(limit));
                    limit += 10;
                    Log.e("page +", String.valueOf(page));
                    Log.e("limit", String.valueOf(limit));
                    chatting_main_select(page, limit);
                }
            }
        });

        /*-------------------------------------------------------------------------------------------------------------------------------*/
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


        itemClickListener = new chatting_main_adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

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

                String product_image = list.get(position).getChatting_main_product_image();
                String your_nick = list.get(position).getChatting_main_your_nick();
                String my_nick = list.get(position).getChatting_main_my_nick();
                String product_price = list.get(position).getChatting_main_price();
                String product_subject = list.get(position).getChatting_main_subject();
                String your_phone_number = list.get(position).getChatting_main_your_number();
                int room_number = list.get(position).getChatting_main_idx();
                String my_phone_number = list.get(position).getChatting_main_my_number();
                String last_user = list.get(position).getChatting_main_last_user();
                long chatting_main_out_my_check_time = list.get(position).getChatting_main_out_my_check_time();
                long chatting_main_out_your_check_time = list.get(position).getChatting_main_out_your_check_time();

                /*-------------------------------------------------------------------------------------------------------------*/
                SharedPreferences preferences = getActivity().getSharedPreferences("현재유저", Context.MODE_PRIVATE);
                String 현재유저_phone_number = preferences.getString("user_number", "");
                String 현재유저_image = preferences.getString("user_image", "");
                String 현재유저_nick = preferences.getString("user_nick", "");
                /*-------------------------------------------------------------------------------------------------------------*/


                if (현재유저_phone_number.equals(my_phone_number)) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Socket socket = chatting_service.getSocket();
                                sendWriter = new PrintWriter(socket.getOutputStream());
                                sendWriter.print("null" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(현재유저_phone_number + ",");
                                sendWriter.print(your_phone_number + ",");
                                sendWriter.print("-1" + ",");
                                sendWriter.print("chatting_room" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print("-1" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print("입장" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.println("사진없음");
                                sendWriter.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } else if (현재유저_phone_number.equals(your_phone_number)) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Log.e("chatting_main", "b가 들어왔음을 알림");
                                Socket socket = chatting_service.getSocket();
                                sendWriter = new PrintWriter(socket.getOutputStream());
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(현재유저_phone_number + ",");
                                sendWriter.print(my_phone_number + ",");
                                sendWriter.print("-1" + ",");
                                sendWriter.print("chatting_room" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print("-1" + ",");
                                sendWriter.print(null + ",");
                                sendWriter.print("입장" + ",");
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




                Intent intent = new Intent(getContext(), chatting_room.class);
                intent.putExtra("chatting_main_out_my_check_time", chatting_main_out_my_check_time);
                intent.putExtra("product_image", product_image);
                intent.putExtra("your_nick", your_nick);
                intent.putExtra("product_price", product_price);
                intent.putExtra("product_subject", product_subject);
                intent.putExtra("your_phone_number", your_phone_number);
                intent.putExtra("my_phone_number", my_phone_number);
                intent.putExtra("my_nick", my_nick);
                intent.putExtra("room_number", room_number);
                intent.putExtra("last_user", last_user);
                startActivity(intent);
            }
        };
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) view.findViewById(R.id.chatting_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        chatting_main_select(page, limit);
    }


    // INSERT 구문 은 WHERE 절을 지원하지 않으므로 쿼리가 실패합니다. id열이 고유하거나 기본 키 라고 가정합니다 .
    private void chatting_main_select(int page, int limit) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String SharedPreferences_phone = preferences.getString("user_number", "");

        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<List<chatting_main_data>> call = chatting_Interface.chatting_main_select(SharedPreferences_phone, page, limit);
        call.enqueue(new Callback<List<chatting_main_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<chatting_main_data>> call, @NonNull Response<List<chatting_main_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<chatting_main_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<chatting_main_data> lists) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String SharedPreferences_phone = preferences.getString("user_number", "");
        System.out.println(SharedPreferences_phone+lists.size()+"SharedPreferences_phoneSharedPreferences_phoneSharedPreferences_phone");

        main_adapter = new chatting_main_adapter(getActivity(), lists, itemClickListener, SharedPreferences_phone);
        main_adapter.notifyDataSetChanged();
        recyclerView.setAdapter(main_adapter);
        list = lists;
    }
}
