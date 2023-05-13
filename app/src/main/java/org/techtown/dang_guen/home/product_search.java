package org.techtown.dang_guen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_search extends AppCompatActivity {

     /*
    ArrayList<FoodItem> foodItemArrayList, filteredList;
    역할?데이터의 목록의 값을 넣기 위해서
    책임?데이터의 목록의 값들을 넣어야만 한다
    협력?어레이리스트 객체를 만들기와 어댑터에 데이터값 메세지 보내기와 변수에 리스트 넣기와for문에 값 확인와 조건문으로 와~를 함께한다
    1.foodItemArrayList = new ArrayList<>(); 데이터 담을 객체 만들기
    2.foodAdapter = new FoodAdapter(foodItemArrayList, this); 어댑터에 데이터값 메세지 보내기
    3.foodItemArrayList.add(new FoodItem("asd")); 리스트에 데이터 추가하기
    4.for (int i = 0; i < foodItemArrayList.size(); i++) 으로 데이터 사이즈값 확인
    5.if (foodItemArrayList.get(i).getFoodName().toLowerCase().contains(searchText.toLowerCase()))????????
    6.filteredList.add(foodItemArrayList.get(i));????????/
    7.
    */

       /*
    ArrayList<FoodItem>  filteredList;
    역할?
    책임?
    협력?
    1.filteredList=new ArrayList<>();
    2.filteredList.clear();
    3.filteredList.add(foodItemArrayList.get(i));
    4.foodAdapter.filterList(filteredList);
    */
    ArrayList<product_search_data> foodItemArrayList, filteredList;
    List<product_data> product_recent_list = new ArrayList<>();


    /*
    product_search_recent_adapter.ItemClickListener itemClickListener;
역할?
product_search_recent_adapter-어댑터클래스 불러오기
ItemClickListener-어댑터에 있는 아이템 클릭 리스너 인터페이스 가져오기
책임?현재 클래스에서 만든 클릭아이템 객체를 어댑터를 새로 생성을 할때 넣어준다
협력? 1.itemClickListener = new product_search_recent_adapter.ItemClickListener()
  2.product_search_recent_adapter = new product_search_recent_adapter(this, lists, 현재유저_phone_number,itemClickListener);
1.아이템을 클릭을 할수있게 버튼을 만들어준다
2.아이템 클릭 객체를 어댑터의 매개변수에 넣어준다->setAdapter를 실행을 해주면 값이 들어가게 된다
*/
    product_search_recent_adapter.ItemClickListener itemClickListener;

    String searchText;
    /*
    FoodAdapter foodAdapter;
   역할?어레이리스트의 데이터값을 메인화면에 보일수있도록 연결해준다
   책임?메인화면에 데이터값을 보여주는 책임이 있다
   협력?
   1.foodAdapter = new FoodAdapter(foodItemArrayList, this); 연결해줄 어댑터객체를 생성
   2.recyclerView.setAdapter(foodAdapter); 리사이클러뷰와 어댑터를 연결해준다
   3.foodAdapter.notifyDataSetChanged(); 어댑터 새로고침해준다
   4.foodAdapter.filterList(filteredList);???????
   */
    product_search_adapter product_search_adapter;

    product_search_recent_adapter product_search_recent_adapter;
    /*
    RecyclerView recyclerView;
   역할?메인화면에 리사이클러뷰로 보여주기 위해서
   책임?리사이클러뷰 id값 가져오기,레이아웃 매니저 설정,어댑터와 연결해줄 책임이 있다
   협력?
   1.recyclerView = findViewById(R.id.recyclerview); 레이아웃 리사이클러뷰 아이디값 가져오기
   2.recyclerView.setLayoutManager(linearLayoutManager); 리사이클러뷰에 어떤 레이아웃으로 들어가는지 확인
   3.recyclerView.setAdapter(foodAdapter); 어댑터와 연결하기 위해서
   */
    RecyclerView recyclerView, recyclerview_recent_search;
    /*
   역할?
   책임?
   협력?
   */
    LinearLayoutManager linearLayoutManager;
    /*
    EditText searchET;
   역할?검색하기 위해서
   책임?내가 검색한 키워드가 리사이클러뷰에 보이는 책임이 있다
   협력?
   1.searchET = findViewById(R.id.searchFood);  아이디값 가져오기
   2.searchET.addTextChangedListener(new TextWatcher() {  ???????
   3.String searchText = searchET.getText().toString(); 스트링값으로 변환하기
   */
    EditText product_search_edittext;
    Button product_search_all_cancel_button;
    String 현재유저_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);


        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        현재유저_phone_number = preferences.getString("user_number", "");
        product_recent_select(현재유저_phone_number);
 /*
 recyclerView = findViewById(R.id.recyclerview);
    역할?레이아웃에 있는 리사이클러뷰객체 가져오기 위해서
    책임?
    협력?
    */
        recyclerView = findViewById(R.id.recyclerview);
        recyclerview_recent_search = findViewById(R.id.recyclerview_recent_search);

        product_search_edittext = findViewById(R.id.product_search_edittext);

        filteredList = new ArrayList<>();

        foodItemArrayList = new ArrayList<>();
         /*
         foodAdapter = new FoodAdapter(foodItemArrayList, this);
    역할?어댑터에 데이터값을 보내주기 위해서 현재 위치도 보내야 되는 이유는 어디에서 데이터를 보내는지 알기 위해서
    책임?
    협력?
    */
        product_search_adapter = new product_search_adapter(this, foodItemArrayList, 현재유저_phone_number);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(product_search_adapter);
        recyclerView.setVisibility(View.GONE);



        product_search_adapter.setOnItemClickListener(new product_search_adapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, String data) {
                Intent intent = new Intent(product_search.this, product_search_result.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });



        itemClickListener = new product_search_recent_adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String product_search = product_recent_list.get(position).getProduct_search();
                Intent intent = new Intent(product_search.this, product_search_result.class);
                intent.putExtra("data", product_search);
                startActivity(intent);
            }
        };



        foodItemArrayList.add(new product_search_data("아이폰"));
        foodItemArrayList.add(new product_search_data("아이폰미니"));
        foodItemArrayList.add(new product_search_data("아이폰6"));
        foodItemArrayList.add(new product_search_data("아이폰7"));
        foodItemArrayList.add(new product_search_data("아이폰8"));
        foodItemArrayList.add(new product_search_data("아이폰xe"));
        foodItemArrayList.add(new product_search_data("아이폰11"));
        foodItemArrayList.add(new product_search_data("아이폰12미니"));
        foodItemArrayList.add(new product_search_data("아이폰12"));
        foodItemArrayList.add(new product_search_data("아이폰12프로"));
        foodItemArrayList.add(new product_search_data("아이폰13"));
        foodItemArrayList.add(new product_search_data("아이폰13미니"));
        foodItemArrayList.add(new product_search_data("아이폰13프로"));
        foodItemArrayList.add(new product_search_data("아이폰 선"));
        foodItemArrayList.add(new product_search_data("아이폰14"));
        foodItemArrayList.add(new product_search_data("아이폰14프로"));
        foodItemArrayList.add(new product_search_data("아이폰7프로"));
        foodItemArrayList.add(new product_search_data("아이폰 충천기"));
        foodItemArrayList.add(new product_search_data("14프로 케이스"));
        foodItemArrayList.add(new product_search_data("미개봉"));
        foodItemArrayList.add(new product_search_data("12미니 케이스"));
        product_search_adapter.notifyDataSetChanged();




         /*
         addTextChangedListener,new TextWatcher()
         why?왜 이름 addTextChangedListener일까 컴퓨터에게 에딕트텍스트안에 바뀐 데이터값을 추가한것
    역할?new TextWatcher()은 인터페이스로 전,현재,후 상황을 파악한다
    책임?
    협력?
    */
        product_search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    searchText = product_search_edittext.getText().toString();

                    searchFilter(searchText);
                }
            }
        });


        product_search_all_cancel_button = (Button) findViewById(R.id.product_search_all_cancel);
        product_search_all_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_search_delete(현재유저_phone_number);
                recyclerview_recent_search.setVisibility(View.GONE);
            }
        });
    }


    public void searchFilter(String searchText) {
         /*
         filteredList.clear();
    역할?검색한 내용 저장
    책임?검색한 내용을 저장을 해줘야 된다
    협력?filteredList.add(foodItemArrayList.get(i));에서 리스트를 계속 추가를 한다 쉽게 말해서 검색한 내용이 계속 저장 되어있으면 안된다
    */

        filteredList.clear();

        for (int i = 0; i < foodItemArrayList.size(); i++) {
             /*
              if (foodItemArrayList.get(i).getFoodName().toLowerCase().contains(searchText.toLowerCase())) {
    역할?리스트를 사이즈만큼 돌려서 getFoodName()값을 가져온다
    책임?
    협력?
    */
            if (foodItemArrayList.get(i).getProduct_name().contains(searchText)) {
                filteredList.add(foodItemArrayList.get(i));
            }
        }
        product_search_adapter.filterList(filteredList);
    }



    private void product_recent_select(String my_number) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<List<product_data>> call = ApiInterface.product_recent_select(my_number);
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
        if (lists.size() == 0) {
            recyclerview_recent_search.setVisibility(View.GONE);

            product_search_recent_adapter = new product_search_recent_adapter(this, lists, 현재유저_phone_number,itemClickListener);
            product_search_recent_adapter.notifyDataSetChanged();
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerview_recent_search.setLayoutManager(linearLayoutManager);
            recyclerview_recent_search.setAdapter(product_search_recent_adapter);
            product_recent_list = lists;
        } else {
            recyclerview_recent_search.setVisibility(View.VISIBLE);
            product_search_recent_adapter = new product_search_recent_adapter(this, lists, 현재유저_phone_number,itemClickListener);
            product_search_recent_adapter.notifyDataSetChanged();
            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerview_recent_search.setLayoutManager(linearLayoutManager);
            recyclerview_recent_search.setAdapter(product_search_recent_adapter);
            product_recent_list = lists;
        }
    }


    //게시판 번호를 가져옴->다중이미지를 셀렉트하기 위해서
    private void product_search_delete(String 현재유저_phone_number) {
        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_search_delete(0, 현재유저_phone_number, "all", null);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        현재유저_phone_number = preferences.getString("user_number", "");
        product_recent_select(현재유저_phone_number);
    }
}