package org.techtown.dang_guen.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.techtown.dang_guen.start_atcivity.MainActivity;
import org.techtown.dang_guen.start_atcivity.phone_certification;
import org.techtown.dong_nae_life.R;

public class junggo_category extends AppCompatActivity {

    Button 인기매물,디지털기기,생활가전,가구인테리어;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junggo_category);

        인기매물 = (Button)findViewById(R.id.인기매물);
        인기매물.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(junggo_category.this, junggo_category_ingi.class);
                intent.putExtra("인기매물","인기매물");
                startActivity(intent);
            }
        });

        디지털기기 = (Button)findViewById(R.id.디지털기기);
        디지털기기.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(junggo_category.this, junggo_category_digital.class);
                intent.putExtra("디지털기기","디지털기기");
                startActivity(intent);
            }
        });

        생활가전 = (Button)findViewById(R.id.생활가전);
        생활가전.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(junggo_category.this, junggo_category_life_digital.class);
                intent.putExtra("생활가전","생활가전");
                startActivity(intent);
            }
        });

        가구인테리어 = (Button)findViewById(R.id.가구인테리어);
        가구인테리어.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(junggo_category.this, junggo_category_gagu.class);
                startActivity(intent);
            }
        });
    }
}