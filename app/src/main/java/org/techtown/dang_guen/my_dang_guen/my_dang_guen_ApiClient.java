package org.techtown.dang_guen.my_dang_guen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class my_dang_guen_ApiClient {
    //나의 서버와 연결을 하기 위해서 만드것
    /*
     * private-접근제어자로 boardApiClient의 클래스에서만 사용을 할수가 있다
     * static-변하지 않는 일정한 값으로 정하기 위해서 만드것
     * 인스턴스 생성 없이 바로 사용가능 하기 때문에 프로그램 내에서 공통으로 사용되는 데이터들을 관리 할 때 이용한다.
     * final-수정이 불가능한 뜻 상속도 불가능 하다
     */
    private static final String BASE_URL = "http://52.79.180.89/dang_guen/";

    //레트로핏 api를 연결하기 위함

    private static Retrofit retrofit;

    //지슨을 만든는 이유는 제이슨으로 받아온 값을 변환을 하기 위한것이다 지슨은 자바의 라이브러리=기술 이다
    //그리고 제이슨을 문자열로 바꿀수도 있다

    /*
    Gson gson = new GsonBuilder()-제이슨 형태로 받아온 값을 가지고 온다
    *.setLenient()-입력한 값이 제대로 들어오는건지 확인을 하기 위한것
    .create()-입력한 값이 제대로 들어와졌으면 지슨을 객체를 만든다
    * */
    public static Retrofit getApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //만약에 레트로값이 눌값이라면
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
