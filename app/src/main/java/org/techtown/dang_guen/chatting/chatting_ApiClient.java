package org.techtown.dang_guen.chatting;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class chatting_ApiClient {
    //나의 서버와 연결을 하기 위해서 만드것
    /*
     * private-접근제어자로 chatting_ApiClient의 클래스에서만 사용을 할수가 있다
     * static-변하지 않는 일정한 값으로 정하기 위해서 만드것
     * 인스턴스 생성 없이 바로 사용가능 하기 때문에 프로그램 내에서 공통으로 사용되는 데이터들을 관리 할 때 이용한다.
     * final-수정이 불가능한 뜻 상속도 불가능 하다
     */


    private static final String BASE_URL = "http://52.79.180.89/dang_guen/"; //자신의 서버 주소값을 입력한다



    private static Retrofit retrofit;

    //지슨을 만든는 이유는 제이슨으로 받아온 값을 변환을 하기 위한것이다 지슨은 자바의 라이브러리=기술 이다
    //그리고 제이슨을 문자열로 바꿀수도 있다

    /*
    Gson gson = new GsonBuilder()-제이슨 형태로 받아온 값을 가지고 온다
    *.setLenient()-입력한 값이 제대로 들어오는건지 확인을 하기 위한것
    .create()-입력한 값이 제대로 들어와졌으면 지슨을 객체를 만든다
    * */


    public static Retrofit getApiClient() {


        final OkHttpClient okHttpClient = new OkHttpClient.Builder() //REST API, HTTP 통신을 간편하게 구현할 수 있도록 다양한 기능을 제공해주는 Java 라이브러리
                .readTimeout(60, TimeUnit.SECONDS) //서버에서 받아올때 시간이 초과가 되면 꺼지게 된다
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder() //서버에서 받아온 제이슨값을 String 형태로 값을 반환을 하기위함
                .setLenient()
                .create();



        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create()) //string으로 처리
                    .addConverterFactory(GsonConverterFactory.create(gson)) //gson으로 처리
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }
}

