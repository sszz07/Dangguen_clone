package org.techtown.dang_guen.my_dang_guen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import org.techtown.dang_guen.start_atcivity.user_ApiClient;
import org.techtown.dang_guen.start_atcivity.user_ApiInterface;
import org.techtown.dang_guen.start_atcivity.user_data;
import org.techtown.dong_nae_life.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class setting extends AppCompatActivity {
    ImageView setting_image_view;
    ImageButton setting_back_button;
    TextView setting_nick_textview;
    Button setting_profile_update_button;
    my_dang_guen my_dang_guen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting_image_view = (ImageView) findViewById(R.id.setting_image_view);
        setting_nick_textview = (TextView) findViewById(R.id.setting_nick_textview);
        setting_profile_update_button = (Button) findViewById(R.id.setting_profile_update_button);
        setting_back_button = (ImageButton) findViewById(R.id.setting_back_button);

        my_dang_guen_user_info();

        setting_profile_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_dang_guen_user_info_setting_intent();
            }
        });

        setting_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    private void my_dang_guen_user_info() {
        //현재 사용하고있는 유저를 가지고 오기 위해서
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저 = preferences.getString("user_number", "");

        user_ApiInterface user_ApiInterface = user_ApiClient.getApiClient().create(user_ApiInterface.class);
        Call<user_data> call = user_ApiInterface.user_select(현재유저);
        call.enqueue(new Callback<user_data>() {
            @Override
            public void onResponse(@NonNull Call<user_data> call, @NonNull Response<user_data> response) {
                String user_nick = response.body().getNick();
                String user_image = response.body().getImage();
                String phone =response.body().getPhone();
                System.out.println(phone + "phonephone");
                System.out.println(user_image + "확인2");

                if(user_image != null){
                    Glide.with(getApplicationContext())
                            .load("http://52.79.180.89/dang_guen" + user_image)// image url
                            .apply(new RequestOptions().circleCrop())
                            .into(setting_image_view);
                }

                setting_nick_textview.setText(user_nick);

            }

            @Override
            public void onFailure(Call<user_data> call, Throwable t) {

            }
        });
    }

    private void my_dang_guen_user_info_setting_intent() {
        //현재 사용하고있는 유저를 가지고 오기 위해서
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저 = preferences.getString("user_number", "");

        user_ApiInterface user_ApiInterface = user_ApiClient.getApiClient().create(user_ApiInterface.class);
        Call<user_data> call = user_ApiInterface.user_select(현재유저);
        call.enqueue(new Callback<user_data>() {
            @Override
            public void onResponse(@NonNull Call<user_data> call, @NonNull Response<user_data> response) {
                String user_nick = response.body().getNick();
                String user_image = response.body().getImage();
                Intent intent = new Intent(setting.this, profile_update.class);
                intent.putExtra("setting_user_nick", user_nick);
                intent.putExtra("setting_user_image", user_image);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<user_data> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        my_dang_guen_user_info();
    }
}