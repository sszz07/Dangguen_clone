package org.techtown.dang_guen.my_dang_guen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.dong_nae_life.R;
import org.techtown.dang_guen.start_atcivity.user_ApiClient;
import org.techtown.dang_guen.start_atcivity.user_ApiInterface;
import org.techtown.dang_guen.start_atcivity.user_data;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class my_dang_guen extends Fragment {
    ImageView my_dang_guen_imageview;
    TextView my_dang_guen_nick_textview;
    Button my_dang_guen_setting_button,my_dang_guen_like_button,my_dang_guen_sell_button,my_dang_guen_purchase_button;

    public my_dang_guen() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_dang_guen,container,false);
        my_dang_guen_imageview = (ImageView) view.findViewById(R.id.my_dang_guen_image_view);
        my_dang_guen_nick_textview = (TextView) view.findViewById(R.id.my_dang_guen_text_view_nick);
        my_dang_guen_setting_button = (Button) view.findViewById(R.id.my_dang_guen_setting_button);
        my_dang_guen_like_button= (Button) view.findViewById(R.id.my_dang_guen_like_button);
        my_dang_guen_sell_button= (Button) view.findViewById(R.id.my_dang_guen_sell_button);
        my_dang_guen_purchase_button= (Button) view.findViewById(R.id.my_dang_guen_purchase_button);
        my_dang_guen_user_info();

        my_dang_guen_purchase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), my_dang_guen_buy.class);
                startActivity(intent);
            }
        });

        my_dang_guen_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), setting.class);
                startActivity(intent);
            }
        });

        my_dang_guen_like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), my_dang_guen_like.class);
                startActivity(intent);
            }
        });


        my_dang_guen_sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), my_dang_guen_sell.class);
                startActivity(intent);
            }
        });

        return view;
//        return inflater.inflate(R.layout.activity_my_dang_guen, container, false);
    }

    private void my_dang_guen_user_info() {
        //현재 사용하고있는 유저를 가지고 오기 위해서
        SharedPreferences preferences =  this.getActivity().getSharedPreferences("현재유저", 0);
        String 현재유저 = preferences.getString("user_number", "");

        user_ApiInterface user_ApiInterface = user_ApiClient.getApiClient().create(user_ApiInterface.class);
        Call<user_data> call = user_ApiInterface.user_select(현재유저);
        call.enqueue(new Callback<user_data>() {
            @Override
            public void onResponse(@NonNull Call<user_data> call, @NonNull Response<user_data> response) {
                String user_nick = response.body().getNick();
                String user_image = response.body().getImage();
                System.out.println(user_nick+"닉네임값 확인");
                System.out.println(user_image+"확인2");


                if(user_image != null){
                    Glide.with(getActivity())
                            .load("http://52.79.180.89/dang_guen" + user_image)// image url
                            .apply(new RequestOptions().circleCrop())
                            .into(my_dang_guen_imageview);
                }

                my_dang_guen_nick_textview.setText(user_nick);
            }
            @Override
            public void onFailure(Call<user_data> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        my_dang_guen_user_info();
    }
}