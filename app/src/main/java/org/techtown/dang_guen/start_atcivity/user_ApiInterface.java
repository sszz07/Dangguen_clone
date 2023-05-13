package org.techtown.dang_guen.start_atcivity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface user_ApiInterface {

    /*
     * api인터페이스는 왜 필요할까?
     * 인터페이스-연결 결부-하나로 만든다/데이터값을 이동할수 있게 만드는 클래스
     * 어떤 클래스와 연결을 하는것일까?
     * boardPerson 클래스와 getNameHobby()->board.java클래스를 조화를 이루고 php의 클래스를 가져올수 있게 한다
     * 순서를 보면 먼저 1.php의 클래스를 가지고 오고 2.boardPersonddml 클래스에서 데이터의 요청을 한다음 3.board클래스에 값을 넣을수 있게 한다
     * <p>
     * 1.셀렉트는 겟인데 델리트 인서트 업데이트는 왜 포스트 값일까?
     * GET : 데이터를 서버에서 얻는 행위
     * POST : 데이터를 서버에 제출하는 행위
     * <p>
     * <p>
     * Call<>--<> 안에 자료형은 JSON 데이터를 <> 안에 자료형으로 받겠다는 뜻입니다.
     * getNameHobby()--
     *
     * @FormUrlEncoded--
     * @Field()--
     */

    String LOGIN_URL = "http://52.79.180.89/dang_guen/";

    @FormUrlEncoded
    @POST("user_phone_number_check.php")
    Call<String> phone_identify(
            @Field("phone") String phone
    );


    @FormUrlEncoded
    @POST("user_make.php")
    Call<user_data> user_make(@Field("nick") String nick,
                              @Field("image") String image,
                              @Field("phone") String phone
    );


    @FormUrlEncoded
    @POST("user_select.php")
        //@Field을 하는이유는 서버에서 잘 받아와야 되기 때문이다
    Call<user_data> user_select(
            @Field("phone") String phone

    );


    @FormUrlEncoded
    @POST("user_update.php")
        //@Field을 하는이유는 서버에서 잘 받아와야 되기 때문이다
    Call<user_data> user_update(@Field("nick") String nick,
                                @Field("image") String image,
                                @Field("phone") String phone,
                                @Field("time") String time
    );
}
