package org.techtown.dang_guen.start_atcivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import org.techtown.dang_guen.chatting.chatting_service;
import org.techtown.dang_guen.fragment_start;
import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dong_nae_life.R;


import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class phone_certification extends AppCompatActivity {

    /*
     * CountDownTimer
     *앞에 abstract는 추상클래스이다
     * 추상이란? 여러 가지 사물이나 개념에서 공통되는 특성이나 속성 따위를 추출하여 파악하는 작용
     * 추상 클래스란?
     * 추상메소드가 포함된 클래스를 추상클래스
     *
     * 추상 메소드란?
     * 자식 클래스에서 반드시 오버라이딩해야만 사용할 수 있는 메소드를 의미합니다.
     *
     * 중보되는 부분을 구현하지 않고 이를 받아서 내가 필요한것만 재정의 해서 사용하게 된다
     *
     * 왜 CountDownTimer는 추상클래스일까?
     *
     *
     * 일정한 간격으로 콜백을 해준다
     *public abstract void onTick(long var1);

     * //종료하는 메소드
     * public abstract void onFinish();
     * */
    private CountDownTimer mCountDownTimer;
    //숫자를 크게한 이유는 countdowninterval가 1000의 1초이기 때문이다
    private long mTimeLeftInMillis = 300000;
    private String ip = "192.168.56.1";
    private int port = 1234;

    Button certification_number_get_button, certification_number_get_rebutton, certification_number_check_gray_button, certification_number_check_orange_button, button_countdown;
    ImageButton phone_certification_back_button;
    EditText phone_number_edittext, certification_number_edittext;
    TextView 안녕하세요_textview, 휴대폰번호로입력해주세요_textview, 휴대폰번호는안전하게_textview, 어떠한경우에도_textview, 인증번호확인_textview;
    static final int SMS_SEND_PERMISSON = 1;
    String check_nubmer;
    Dialog dilaog;
    SharedPreferences preferences;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_certification);

        dilaog = new Dialog(phone_certification.this);       // Dialog 초기화
        dilaog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog.setContentView(R.layout.make_user_dialog);

        preferenceHelper = new PreferenceHelper(this);

        //TextView id
        안녕하세요_textview = findViewById(R.id.안녕하세요_textview);
        휴대폰번호로입력해주세요_textview = findViewById(R.id.휴대폰번호로입력해주세요_textview);
        휴대폰번호는안전하게_textview = findViewById(R.id.휴대폰번호는안전하게_textview);
        어떠한경우에도_textview = findViewById(R.id.어떠한경우에도_textview);
        인증번호확인_textview = findViewById(R.id.인증번호확인_textview);


        //EditText id
        certification_number_edittext = (EditText) findViewById(R.id.certification_number_edittext);
        phone_number_edittext = (EditText) findViewById(R.id.phone_number_edittext);

        String phone_number = phone_number_edittext.getText().toString();


        certification_number_get_rebutton = (Button) findViewById(R.id.certification_number_get_rebutton);
        certification_number_check_gray_button = (Button) findViewById(R.id.certification_number_check_gray_button);
        certification_number_check_orange_button = (Button) findViewById(R.id.certification_number_check_orange_button);
        certification_number_get_button = (Button) findViewById(R.id.certification_number_get_button);
        button_countdown = (Button) findViewById(R.id.Button_countdown);

        //첫화면에 Gone하는 뷰
        //이유-인증문자 받기를 클릭하게되면 활성화되는 뷰
        certification_number_get_rebutton.setVisibility(View.GONE);
        certification_number_edittext.setVisibility(View.GONE);
        어떠한경우에도_textview.setVisibility(View.GONE);
        certification_number_check_gray_button.setVisibility(View.GONE);
        certification_number_check_orange_button.setVisibility(View.GONE);
        인증번호확인_textview.setVisibility(View.INVISIBLE);
        button_countdown.setVisibility(View.INVISIBLE);


        //ImageButton id
        phone_certification_back_button = (ImageButton) findViewById(R.id.phone_certification_back_button);

        Intent intent = getIntent();
        String 이미존재 = intent.getStringExtra("이미존재함");
System.out.println(이미존재+"이미존재");


        /*
         * ContextCompat 앱에 권한을 주기 위한것
         * */
        //checkSelfPermission()-어떤것이 권한으로 되어있는지 확인
        int permissonCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        //PackageManager.PERMISSION_GRANTED-허용이 되어있는지 아닌지
        if (permissonCheck != PackageManager.PERMISSION_GRANTED) {

            //shouldShowRequestPermissionRationale() 메서드는 사용자가 이전에 권한 요청을 거부한 경우 true 값을 넘겨주게 되어 있습니다.
            // 그 결과를 이용하여 앱을 사용하려면 권한이 필요함을 사용자에게 알려 주는 안내를 추가 해야 합니다.
            //권한이 필요한 이유
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                Toast.makeText(this, "권한이 필요합니다", Toast.LENGTH_SHORT).show();

            }

//requestPermissions-권한 요청의 SMS_SEND_PERMISSON값에 1로 세팅이 되어있음 권한이 되면 1이 존재함으로 계속 권한이 되어진다
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSON);
        }


        //백버튼
        phone_certification_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                //finsh(); //액티비티 파괴 메서드
            }
        });



        /*
         * 키보드 자동 올림 메소드*/
        phone_number_edittext.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);





        /*
         *edittext에서 입력시 버튼활성화/비활성화
         * */
        phone_number_edittext.addTextChangedListener(new TextWatcher() {
            //beforeTextChanged-입력하여 변화가 생기기 전에 처리하는 부분
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //onTextChanged-입력난에 변화가 있을시
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            //afterTextChanged-입력이 끝났을때
            //Editable-EditText를 편집이 가능하게 하는 클래스
            //setClickable
            //setBackgroundColor-버튼 컬러 설정
            public void afterTextChanged(Editable editable) {
                //인증번호 로컬에 저장

                if (editable.length() > 7) {
                    //글자수가 8개부터 버튼이 활성화가 되고 텍스트 컬러가 보이게 된다
                    certification_number_get_button.setClickable(true);
                    certification_number_get_button.setTextColor(Color.WHITE);
                    certification_number_get_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            server_get_phone_number();

                        }
                    });
                }


                //11자리 번호 미입력시 버튼 비활성화
                else {
                    certification_number_get_button.setClickable(false);
                    certification_number_get_button.setTextColor(Color.GRAY);
                }
            }
        });


    }
    /*
    여기까지 온크리에이트----------------------------------------------------------------------------------------------------------------------
     */


    /*
     *휴대폰 인증
     *
     *  PendingIntent
     * Pending-곧, 있을법한
     *
     * SmsManager
     * 권한이 부여가 되어있지 않으면 프로그램이 종료가 된다ㄴ
     * */
//메세지를 보내는 메소드
    private void sendSMS(String phoneNumber, String message) {

//        PendingIntent pi = PendingIntent.getActivities(this, 0, new Intent[]{new Intent(this, MainActivity.class)}, 0);

        //인증번호 보내는 클래스
        SmsManager sms = SmsManager.getDefault();
        PendingIntent pi = null;

        //sendTextMessage()의 인자는 주소와 텍스트만 할당하여 전송가능
        //null 처리한 인자는 상대 디바이스가 해당 문자를 받았는지 확인 하기 위해 사용하는 인자들임
        //인자 1번-상대방 전화번호 2번-모름 3번-보낼 문자열 뒤에 모름
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }

    //인증번호를 랜덤으로 만드는 클래스
    //len-생성할 번호 난수
    //dupcd-중복 허용 여부 1-중복허용 2-중복제거

    public static String numberGen(int len, int dupCd) {
        Random random = new Random();
        String numStr = "";

        for (int i = 0; i < len; i++) {
            //0~9까지 난수 생성
            String ran = Integer.toString(random.nextInt(10));

            //중복을 허용하지 않을시 중복된 값이 있는지 검사한다
            if (dupCd == 1) {
                //중복된 값이 없으면 numStr에 적용
                numStr += ran;

            } else if (dupCd == 2) {
                if (!numStr.contains(ran)) {
                    numStr += ran;
                } else {
                    i -= 1;
                }
            }
        }
        return numStr;
    }


    /*
     * 타이머 메소드
     * */
    //재생하는 버튼
    private void startTimer() {
        //CountDownTimer 객체를 만드는 법
        //countdowninterval-콜백을 받기까지의 시간 간격
        //첫번째 인자의 값이 5000이면 5초이고,두번째 인자가 1000이면 1초이다
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            //onTick-위에서 두번째 인자값이 1000이면 onTick은 1초에 한번씩 자동이 된다
            @Override
            public void onTick(long millisUntilFinished) {
                //millisUntilFinished-완료 될때까지의 시간

                //시간을 변수mTimeLeftInMillis에 넣은것
                mTimeLeftInMillis = millisUntilFinished;


                updateCountDownText();
            }

            @Override
            public void onFinish() {
                //시간이 초과가 되면 시간과 같이 있는 버튼 안보이게 하기
                button_countdown.setVisibility(View.INVISIBLE);
                //시간이 초과되면 로컬삭제
                SharedPreferences pref = getSharedPreferences("휴대폰인증번호", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("휴대폰인증번호");
                editor.apply();
            }

        }.start();
    }


    //리셋을 할때 mTimeLeftInMillis을 온크리에이트에서 설정한 값을 재설정을 해준다
    /**
     * 다시 받았을때 19초로 되어있는 이유
     * mTimeLeftInMillis = 20000;으로 설정이 되어있었다
     * 왜 2000으로 하면 안되는가? 2000은 19초 계산이 된다
     * 왜 19초로 계산이 되는가?
     * updateCountDownText()에서 int minutes = (int) (mTimeLeftInMillis / 1000) / 60; 계산 하면 19초로 계산 된다
     * */
    private void resetTimer() {
        mTimeLeftInMillis = 300000;
        updateCountDownText();
        startTimer();
    }


    //
    private void updateCountDownText() {

        //
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;

        //
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        //
        String timeLeftFormatted = String.format(Locale.getDefault(), "인증문자 다시받기" + "(%02d:%02d)", minutes, seconds);

        //타이머 값을 보여주게 한다
        button_countdown.setText(timeLeftFormatted);

    }


    //휴대폰번호 유무 확인
    private void server_get_phone_number() {
        //입력한 번호데이터
        String phone_number = phone_number_edittext.getText().toString();
        Log.e("핸드폰 번호", phone_number);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(user_ApiInterface.LOGIN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        user_ApiInterface user_ApiInterface = retrofit.create(user_ApiInterface.class);
        Call<String> call = user_ApiInterface.phone_identify(phone_number);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String jsonResponse = response.body();
                System.out.println(jsonResponse);
                parseLoginData(jsonResponse, phone_number);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
            }
        });
    }


    private void parseLoginData(String response, String phone_number) {



                SharedPreferences preferences2 = getSharedPreferences("현재유저", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = preferences2.edit();
                editor2.putString("user_number", phone_number);
                editor2.apply();


                //인증번호 로컬에 저장
                preferences = getSharedPreferences("휴대폰인증번호", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();


                //인증번호 요청이 나오게 되면 밑에 뷰들은 안보이게 한다
                안녕하세요_textview.setVisibility(View.GONE);
                휴대폰번호로입력해주세요_textview.setVisibility(View.GONE);
                휴대폰번호는안전하게_textview.setVisibility(View.GONE);
                certification_number_get_button.setVisibility(View.GONE);


                //인증번호 요청시 보이게 되는 뷰
                certification_number_get_rebutton.setVisibility(View.VISIBLE);
                어떠한경우에도_textview.setVisibility(View.VISIBLE);
                certification_number_check_gray_button.setVisibility(View.VISIBLE);
                certification_number_edittext.setVisibility(View.VISIBLE);
                button_countdown.setVisibility(View.VISIBLE);


                //인증받은 번호를 로컬에 저장함 저장한값과 같으면 인텐트
                check_nubmer = numberGen(4, 1);
                //메소드 호출
                editor.putString("휴대폰인증번호", check_nubmer);
                editor.apply();
                sendSMS(phone_number_edittext.getText().toString(), "" + check_nubmer);

                //타이머 시작
                startTimer();


                //인증번호 입력
                certification_number_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }


                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() == 4 || preferences.getString("휴대폰인증번호", "").equals(certification_number_edittext.getText().toString())) {
                            //인증번호 4자리를 입력시 '인증번호확인(오렌지색)'버튼을 활성화
                            certification_number_check_orange_button.setClickable(true);
                            certification_number_check_orange_button.setVisibility(View.VISIBLE);
                            certification_number_check_orange_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    user_image();
                                }
                            });
                            if (editable.length() < 4 || !preferences.getString("휴대폰인증번호", "").equals(certification_number_edittext.getText().toString())) {
                                certification_number_check_orange_button.setClickable(true);
                                certification_number_check_orange_button.setVisibility(View.VISIBLE);
//                                certification_number_edittext.setBackgroundResource(R.drawable.phone_certification_edittext_white);
                                인증번호확인_textview.setVisibility(View.INVISIBLE);
                                certification_number_check_orange_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        certification_number_check_orange_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                어떠한경우에도_textview.setVisibility(View.INVISIBLE);
                                                인증번호확인_textview.setVisibility(View.VISIBLE);
                                                인증번호확인_textview.setText("인증번호를 다시입력해주세요");
//                                                certification_number_edittext.setBackgroundResource(R.drawable.phone_certification_edittext_red);  // 적색 테두리 적용
                                            }
                                        });

                                    }
                                });
                            }
                        } else {
                            //4자리 이하 '인증번호확인(오렌지색)' 비활성화
                            certification_number_check_orange_button.setClickable(false);
                            certification_number_check_orange_button.setVisibility(View.INVISIBLE);

                        }
                    }
                });


                //인증번호 재전송(시간)
                button_countdown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_countdown.setVisibility(View.VISIBLE);
                        //이유
                        //첫 버튼을 클릭하면 무조건 타이머가 실행이 되어야 되서 startTimer()넣었고
                        //재실행 하기 위해 객체의 값이 들어있는 상태에서는 타이머가 동시에 작동이 된다
                        //그래서 mCountDownTimer 값을 눌러 함으로써 객체를 제거하고 재실행을 했을때 동시에 작동이 되지 않는다
                        mCountDownTimer.cancel();
                        mCountDownTimer = null;
                        resetTimer();


                        //인증받은 번호를 로컬에 저장함
                        check_nubmer = numberGen(4, 1);
                        //메소드 호출
                        editor.putString("", check_nubmer);
                        editor.apply();
                        sendSMS(phone_number_edittext.getText().toString(), "휴대폰인증번호 : " + check_nubmer);
                    }
                });

                //인증번호 재전송
                certification_number_get_rebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_countdown.setVisibility(View.VISIBLE);
                        //이유
                        //첫 버튼을 클릭하면 무조건 타이머가 실행이 되어야 되서 startTimer()넣었고
                        //재실행 하기 위해 객체의 값이 들어있는 상태에서는 타이머가 동시에 작동이 된다
                        //그래서 mCountDownTimer 값을 눌러 함으로써 객체를 제거하고 재실행을 했을때 동시에 작동이 되지 않는다
                        mCountDownTimer.cancel();
                        mCountDownTimer = null;
                        resetTimer();


                        //인증받은 번호를 로컬에 저장함
                        check_nubmer = numberGen(4, 1);
                        //메소드 호출
                        editor.putString("휴대폰인증번호", check_nubmer);
                        editor.apply();
                        sendSMS(phone_number_edittext.getText().toString(), "휴대폰인증번호 : " + check_nubmer);
                    }
                });

    }

    private void saveInfo(String response) {
        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putName(dataobj.getString("phone_number"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void dialog() {
        dilaog.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


        // 아니오 버튼
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        Button noBtn = dilaog.findViewById(R.id.make_user_dialog_cancel_button);
        noBtn.findViewById(R.id.make_user_dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        dilaog.findViewById(R.id.make_user_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_number = phone_number_edittext.getText().toString();
                Intent intent = new Intent(phone_certification.this, user_make.class);
                intent.putExtra("phone", phone_number);
                startActivity(intent);
            }
        });
    }

    //상품 추가 할때 유저 이미지 데이터를 불러오기 위함
    private void user_image() {
        SharedPreferences preferences = this.getSharedPreferences("현재유저", Context.MODE_PRIVATE);
        String phone_number = preferences.getString("user_number", "");


        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.user_select_image(phone_number);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
                if (response.isSuccessful() && response.body() != null) {


                    String user_image = response.body().getImage();
                    Intent intent2 = new Intent(phone_certification.this, fragment_start.class);
                    intent2.putExtra("phone_number", phone_number);
                    intent2.putExtra("user_image", user_image);
                    startActivity(intent2);


                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {
                Log.e("text_select", "에러 : " + t.getMessage());
            }
        });
    }
}