package org.techtown.dang_guen.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.techtown.dong_nae_life.R;


public class product_category extends AppCompatActivity {

    private Button product_make_category_digital_button, product_registration_category_남성의류;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        product_make_category_digital_button = (Button) findViewById(R.id.product_make_category_digital_button);
        product_registration_category_남성의류 = (Button) findViewById(R.id.product_registration_category_남성의류);

        product_make_category_digital_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digital_button();
            }
        });

        product_registration_category_남성의류.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                man_cloth_button();
            }
        });

    }


    /**
     * onActivityResult꼭 다시 공부하기
     */
    public void digital_button() {
        Intent intent = new Intent();
        intent.putExtra("product_category_digital", "디지털 기기");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * onActivityResult꼭 다시 공부하기
     */
    public void man_cloth_button() {
        Intent intent = new Intent();
        intent.putExtra("man_cloth", "남성의류");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}