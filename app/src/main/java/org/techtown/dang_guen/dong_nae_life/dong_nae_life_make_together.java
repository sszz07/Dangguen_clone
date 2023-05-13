package org.techtown.dang_guen.dong_nae_life;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.techtown.dang_guen.home.product_category;
import org.techtown.dang_guen.home.product_image_Utils;
import org.techtown.dang_guen.home.product_make;
import org.techtown.dang_guen.home.product_my_select;
import org.techtown.dong_nae_life.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_make_together extends AppCompatActivity {
    int y = 0, m = 0, d = 0, h = 0, mi = 0;
    String 클래스 = "dong_nae_life_make";
    //    private Button;
    Dialog dilaog;
    EditText dong_nae_life_make_together_subject_edittext, dong_nae_life_make_together_content_edittext, dong_nae_life_make_together_location_edittext;
    ImageButton dong_nae_life_together_person1_button, dong_nae_life_together_person2_button, dong_nae_life_together_age_button, dong_nae_life_together_date_button, dong_nae_life_together_time_button;
    TextView dong_nae_life_together_person_textview, dong_nae_life_together_age_textview, dong_nae_life_together_time_textview, dong_nae_life_together_date_textview;
    Button dong_nae_life_make_together_orange_button;
    TextView dong_nae_life_make_image_count;
    String category_subject, content, number,anyone,woman,man;
    int person = 3;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_nae_life_make_together);


        dilaog = new Dialog(dong_nae_life_make_together.this);       // Dialog 초기화
        dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog.setContentView(R.layout.dong_nae_life_together_time_dialog);

        dong_nae_life_make_together_subject_edittext = (EditText) findViewById(R.id.dong_nae_life_make_together_subject_edittext);
        dong_nae_life_make_together_content_edittext = (EditText) findViewById(R.id.dong_nae_life_make_together_content_edittext);
        dong_nae_life_make_together_location_edittext = (EditText) findViewById(R.id.dong_nae_life_make_together_location_edittext);
        dong_nae_life_together_person1_button = (ImageButton) findViewById(R.id.dong_nae_life_together_person1_button);
        dong_nae_life_together_person2_button = (ImageButton) findViewById(R.id.dong_nae_life_together_person2_button);
        dong_nae_life_make_together_orange_button = (Button) findViewById(R.id.dong_nae_life_make_together_orange_button);


        dong_nae_life_together_date_textview = (TextView) findViewById(R.id.dong_nae_life_together_date_textview);
        dong_nae_life_together_time_textview = (TextView) findViewById(R.id.dong_nae_life_together_time_textview);
        dong_nae_life_together_age_textview = (TextView) findViewById(R.id.dong_nae_life_together_age_textview);
        dong_nae_life_together_age_button = (ImageButton) findViewById(R.id.dong_nae_life_together_age_button);
        dong_nae_life_together_date_button = (ImageButton) findViewById(R.id.dong_nae_life_together_date_button);
        dong_nae_life_together_time_button = (ImageButton) findViewById(R.id.dong_nae_life_together_time_button);

        dong_nae_life_together_person_textview = findViewById(R.id.dong_nae_life_together_person_textview);

        dong_nae_life_together_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(dong_nae_life_make_together.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        dong_nae_life_together_date_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(dong_nae_life_make_together.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        dong_nae_life_together_date_textview.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        dong_nae_life_make_together_orange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dong_nae_life_together_insert(category_subject);

            }
        });

        dong_nae_life_together_person1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person--;
                number = Integer.toString(person);
                dong_nae_life_together_person_textview.setText(number);


            }
        });

        dong_nae_life_together_person2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (person <= 5) {
                    person++;
                    number = Integer.toString(person);
                    dong_nae_life_together_person_textview.setText(number);
                }
            }
        });


        dong_nae_life_together_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });


        dong_nae_life_together_age_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dong_nae_life_make_together_age.class);
                startActivityForResult(intent, 101);
            }
        });


        Spinner spinner_field = (Spinner) findViewById(R.id.dong_nae_life_together_spinner);
        String[] dong_nae_life_spinner = getResources().getStringArray(R.array.together_spinner_array);


        /**
         * Attempt to invoke virtual method 'java.lang.String java.lang.Object.toString()' on a null object reference
         * */


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, dong_nae_life_spinner);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner_field.setAdapter(adapter);

        spinner_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spinner_field.getSelectedItemPosition() > 0) {
                    category_subject = spinner_field.getItemAtPosition(i).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }


    //앨범 이미지 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //갤러리 이미지 가져오기
        if (requestCode == 101) {
             anyone = data.getStringExtra("누구나");
             woman = data.getStringExtra("여자만");
             man = data.getStringExtra("남자만");

            if (anyone != null) {
                dong_nae_life_together_age_textview.setText(anyone);
            } else if (woman != null) {
                dong_nae_life_together_age_textview.setText(woman);
            } else if (man != null) {
                dong_nae_life_together_age_textview.setText(man);
            }

        }
    } //end of onActivityResult

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);


        dong_nae_life_together_date_textview.setText(sdf.format(myCalendar.getTime()));
    }


    void showTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
                dong_nae_life_together_time_textview.setText(h + "시" + mi + "분");
            }
        }, 21, 12, true);


        timePickerDialog.setMessage("메시지");
        timePickerDialog.show();
    }


    //데이터를 서버에 보내는 메소드
    public void dong_nae_life_together_insert(String select_category ) {
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String user_nick = preferences.getString("user_nick", "");
        String user_image = preferences.getString("user_image", "");

        String location = dong_nae_life_make_together_location_edittext.getText().toString();
        String content = dong_nae_life_make_together_content_edittext.getText().toString();
        String subject = dong_nae_life_make_together_subject_edittext.getText().toString();
        String age = dong_nae_life_together_age_textview.getText().toString();
        String person = dong_nae_life_together_person_textview.getText().toString();
        String clock = dong_nae_life_together_time_textview.getText().toString();
        String date = dong_nae_life_together_date_textview.getText().toString();


        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_together_insert(subject,content,age,person,date,clock,select_category,location,user_nick,user_image);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(Call<dong_nae_life_data> call, Response<dong_nae_life_data> response) {
                onBackPressed();
            }

            @Override
            public void onFailure(Call<dong_nae_life_data> call, Throwable t) {

            }
        });
    }


}