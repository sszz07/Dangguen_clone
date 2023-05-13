package org.techtown.dang_guen.chatting;

import org.techtown.dang_guen.dong_nae_life.dong_nae_life_data;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface chatting_Interface {
    //채팅방 만들기
    @FormUrlEncoded //field형식을 사용할때 form이 encoding을 해주어야 된다 //사용하는이유 안드로이드에서 일반값으로는 서버로 보낼수가 없다
    @POST("chatting_room_make.php")
    Call<String> chatting_room_make(//<chatting_data> 클래스안에 있는 형식으로 서버에서 값을 받아옵니다
                                    @Field("your_profile_image") String your_profile_image, //@Field형식은 키 밸류 값으로 구성이 되어있다 GET방식은 사용불가가
                                    @Field("time") String time,
                                    @Field("last_msg") String last_msg,
                                    @Field("product_image") String product_image,
                                    @Field("my_number") String my_number,
                                    @Field("your_number") String your_number,
                                    @Field("your_nick") String your_nick,
                                    @Field("my_profile_image") String my_profile_image,
                                    @Field("my_nick") String my_nick,
                                    @Field("subject") String subject,
                                    @Field("price") String price,
                                    @Field("chatting_main_date") long chatting_main_date,
                                    @Field("product_number") int product_number
    );

    //채팅 메인페이지 대화 나눈 목록 가지고 오기
    @GET("chatting_main_select.php")
    Call<List<chatting_main_data>> chatting_main_select(
            @Query("chatting_main_my_number")
                    String chatting_main_my_number,
            @Query("page")
                    int page,
            @Query("limit")
                    int limit
    );

    //채팅 메인페이지 대화 나눈 목록 가지고 오기
    @GET("my_dang_guen_complete_select_user_select.php")
    Call<List<chatting_main_data>> my_dang_guen_complete_select_user_select(
            @Query("my_number")
                    String my_number
    );


    //채팅룸 이미지 insert
    @Multipart //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_send_image_insert.php")
    Call<chatting_room_data> chatting_room_image_insert(
            @Part List<MultipartBody.Part> image,//1.이미지값
            @Part("chatting_room_time") String chatting_room_time,//2.채팅시간
            @Part("my_number") String my_number,//3.나의 번호값
            @Part("your_number") String your_number,// 4.너의 번호값
            @Part("room_number") int room_number,//5채팅룸넘버값
            @Part("your_profile_image") String your_profile_image,//기존에 없던것 6나의 프로필 이미지값
            @Part("chatting_room_read_check") String chatting_room_read_check,//기존에 없던것 7.
            @Part("chatting_room_my_read_check") int chatting_room_my_read_check,//기존에 없던것 8.나의 채팅룸 읽음체크
            @Part("chatting_room_your_read_check") int chatting_room_your_read_check,//기존에 없던것 9.너의 채팅룸 읽음체크
            @Part("chatting_room_date") long chatting_room_date//10채팅 나갔을때 기준을 만드는 데이터

    );

    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_send_msg_insert.php")
    Call<chatting_room_data> chatting_room_msg_insert(
            @Field("chatting_room_msg") String chatting_room_msg,
            @Field("chatting_room_time") String chatting_room_time,
            @Field("my_number") String my_number,
            @Field("your_number") String your_number,
            @Field("room_number") int room_number,
            @Field("your_profile_image") String your_profile_image,
            @Field("chatting_room_read_check") String chatting_room_read_check,
            @Field("chatting_room_my_read_check") int chatting_room_my_read_check,
            @Field("chatting_room_your_read_check") int chatting_room_your_read_check,
            @Field("chatting_room_date") long chatting_room_date

    );

    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_delete.php")
    Call<chatting_room_data> chatting_room_delete(
            @Field("chatting_room_msg") String chatting_room_msg,
            @Field("chatting_room_image") String chatting_room_image,
            @Field("chatting_room_idx") int chatting_room_idx
    );


    //채팅룸 업데이트
    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_read_update.php")
    Call<chatting_room_data> chatting_room_read_update(
            @Field("chatting_room_idx") int chatting_room_idx
    );


    //채팅룸 업데이트
    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_out_my_update.php")
    Call<chatting_room_data> chatting_room_out_my_update(
            @Field("chatting_main_idx") int chatting_main_idx,
            @Field("delete") String delete,
            @Field("my_number") String my_number,
            @Field("your_number") String your_number,
            @Field("out_msg") String out_msg,
            @Field("chatting_main_out_check_time") long chatting_main_out_check_time
    );

    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_out_your_update.php")
    Call<chatting_room_data> chatting_room_out_your_update(
            @Field("chatting_main_idx") int chatting_main_idx,
            @Field("delete") String delete,
            @Field("my_number") String my_number,
            @Field("your_number") String your_number,
            @Field("out_msg") String out_msg,
            @Field("chatting_main_out_check_time") long chatting_main_out_check_time
    );


    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_main_date_update.php")
    Call<chatting_room_data> chatting_main_date_update(
            @Field("chatting_main_idx") int chatting_main_idx,
            @Field("chatting_main_date") long chatting_main_date
    );


    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_main_read_check_update.php")
    Call<chatting_room_data> chatting_main_read_check_update(
            @Field("chatting_main_idx") int chatting_main_idx,
            @Field("my_number") String my_number,
            @Field("your_number") String your_number
    );


    //나의 메세지 값을 가지고 오는것
    @GET("chatting_room_select.php")
    Call<ArrayList<chatting_room_data>> chatting_room_select( //뒤에 파라미터가 없고 JSON의 값을 여러개를 받을수가 있다 게시판에서 사용할때 유용
                                                              @Query("room_number") int room_number,
                                                              @Query("page")
                                                                      int page,
                                                              @Query("limit")
                                                                      int limit,
                                                              @Query("chatting_main_out_my_check_time")
                                                                      long chatting_main_out_my_check_time
    );


    //방나가기 하고 다시 들어왔을때 방값을 얻기 위해서
    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_first_make_select_room_number.php")
    Call<chatting_main_data> chatting_main_room_number_select(
            @Field("my_number") String my_number,
            @Field("your_number") String your_number
    );

    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_room_first_make_select_room_number.php")
    Call<chatting_main_data> chatting_room_first_make_select_room_number(
            @Field("my_number") String my_number,
            @Field("your_number") String your_number
    );

    @FormUrlEncoded //http 프로토콜의 바디 부분에 데이터를 여러 부분으로 나눠서 보내는 것.
    @POST("chatting_main_delete_date_update.php")
    Call<chatting_room_data> chatting_main_delete_date_update(
            @Field("chatting_main_idx") int chatting_main_idx,
            @Field("my_number") String my_number
    );
}

