package org.techtown.dang_guen.dong_nae_life;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.techtown.dong_nae_life.R;

public class dong_nae_life_make_together_age extends AppCompatActivity {
    private RadioButton r_btn1, r_btn2,r_btn3,r_btn4,r_btn5;
    private RadioGroup radioGroup,radioGroup2;
    EditText dong_nae_life_make_together_age1_edittext,dong_nae_life_make_together_age2_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_make_together_age);



        dong_nae_life_make_together_age1_edittext= (EditText) findViewById(R.id.dong_nae_life_make_together_age1_edittext);
        dong_nae_life_make_together_age2_edittext= (EditText) findViewById(R.id.dong_nae_life_make_together_age2_edittext);

        dong_nae_life_make_together_age1_edittext.setVisibility(View.GONE);
        dong_nae_life_make_together_age2_edittext.setVisibility(View.GONE);


        r_btn1 = (RadioButton) findViewById(R.id.anyone);
        r_btn2 = (RadioButton) findViewById(R.id.only_woman);
        r_btn3 = (RadioButton) findViewById(R.id.only_man);
        r_btn4 = (RadioButton) findViewById(R.id.anyone_age);
        r_btn5 = (RadioButton) findViewById(R.id.age_insert);

        //라디오 버튼 설정
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);


        //라디오 그룹 설정
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup2.setOnCheckedChangeListener(radioGroupButtonChangeListener2);


    }

    //라디오 버튼 클릭 리스너
    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
        @Override
        public void onClick(View view) {
            System.out.println(r_btn1.getText().toString()+"r_btn2");

        }
    };

    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup,  int i) {
            if(i == R.id.anyone){
                anyone();
            }
            else if(i == R.id.only_woman){
                woman();
            }
            else if(i == R.id.only_man){
                man();
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup,  int i) {
            if(i == R.id.anyone_age){

            }
            else if(i == R.id.age_insert){
                dong_nae_life_make_together_age1_edittext.setVisibility(View.VISIBLE);
                dong_nae_life_make_together_age2_edittext.setVisibility(View.VISIBLE);
                String age1 = dong_nae_life_make_together_age1_edittext.getText().toString();
                String age2 = dong_nae_life_make_together_age2_edittext.getText().toString();
            }
        }
    };

    public void anyone() {
        Intent intent = new Intent();
        intent.putExtra("누구나", "누구나");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void woman() {
        Intent intent = new Intent();
        intent.putExtra("여자만", "여자만");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void man() {
        Intent intent = new Intent();
        intent.putExtra("남자만", "남자만");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}