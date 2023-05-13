package org.techtown.dang_guen.home;

import org.techtown.dang_guen.chatting.chatting_main_data;

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


/*
 *interface
 * 사전적 의미-서로 다른 두 시스템(필요한 기능을 실현하기 위하여 관련 요소), 장치(어떤 목적에 따라 기능하도록 기계, 도구 따위를 그 장소에 장착함), 소프트웨어 따위를 서로 이어 주는 부분 또는 그런 접속 장치
 *
 * 무엇인가?
 *
 * 왜 사용하는가?
 *
 * 어떻게 사용하는가?
 *
 * */
public interface product_ApiInterface {

    //이미지 인서트
    @Multipart
    @POST("product_multiimage_insert.php")
    Call<product_data> product_multiimage_insert(
            @Part("product_number") int product_number,
            @Part List<MultipartBody.Part> image,
            @Part("time") String time
    );

    //이미지 셀렉트
    @GET("product_multiimage_select.php")
    Call<List<product_data>> product_multiimage_select(
            @Query("index") int index
    );


    //이미지 삭제
    @FormUrlEncoded
    @POST("product_multiimage_delete.php")
    Call<product_data> product_multiimage_delete(
            @Field("idx") int idx
    );


    //유저 이미지 가져오기
    @FormUrlEncoded
    @POST("user_select.php")
    Call<product_data> user_select_image(
            @Field("phone") String phone
    );


    @GET("home_recyclerview_select.php")
    Call<List<product_data>> my_dang_guen_buy_select(
            @Query("my_number")
                    String my_number
    );

    //이미지 업데이트
    @Multipart
    @POST("product_multiimage_update.php")
    Call<product_data> product_multiimage_update(
            @Part("product_number") int product_number,
            @Part("time") String time,
            @Part List<MultipartBody.Part> image

    );


    //home_main_리사이클러뷰 데이터값
    @GET("home_recyclerview_select.php")
    Call<List<product_data>> home_recyclerview_select(
            @Query("page")
                    int page,
            @Query("limit")
                    int limit
    );

    @GET("my_dang_guen_fragment_sell_recycerview_select.php")
    Call<List<product_data>> my_dang_guen_fragment_sell_recycerview_select(
            @Query("phone")
                    String phone
    );

    @GET("my_dang_guen_fragment_complete_recycerview_select.php")
    Call<List<product_data>> my_dang_guen_fragment_complete_recycerview_select(
            @Query("phone")
                    String phone
    );



    //home_main_리사이클러뷰 데이터값
    @GET("product_search_result.php")
    Call<List<product_data>> product_search_result(
            @Query("product_search") String product_search,
            @Query("style_recent") String style_recent,
            @Query("low_cost") int low_cost,
            @Query("high_cost") int high_cost
    );


    @GET("my_dang_guen_like_select.php")
    Call<List<product_data>> my_dang_guen_like_select(
            @Query("SharedPreferences_user_number") String SharedPreferences_user_number
    );


    //-----------------------------------------------------------------------------------------------
    //product_info

    /**
     * @Multipart은 눌값이면 안되는 건가?
     */
    @Multipart
    @POST("product_info_insert.php")
    Call<product_data> product_info_insert(
            @Part("subject") String subject,
            @Part("content") String content,
            @Part("hits") String hits,
            @Part("user_image") String user_image,
            @Part("nick") String nick,
            @Part("category") String category,
            @Part("chatting") String chatting,
            @Part("user_like") String user_like,
            @Part("price") String price,
            @Part("phone") String phone,
            @Part("time") String time,
            @Part("present_time") long present_time,
            @Part List<MultipartBody.Part> image
    );
    //-----------------------------------------------------------------------------------------------


    @FormUrlEncoded
    @POST("product_info_select_subject.php")
    Call<product_data> product_info_subject_select(
            @Field("subject") String subject

    );

    @FormUrlEncoded
    @POST("product_update_select_info.php")
    Call<product_data> product_info_update_select(
            @Field("idx") int idx

    );

    @FormUrlEncoded
    @POST("product_search_delete.php")
    Call<product_data> product_search_delete(
            @Field("idx") int idx,
            @Field("my_number") String my_number,
            @Field("all") String all,
            @Field("search") String search
    );

    @FormUrlEncoded
    @POST("product_search_insert.php")
    Call<product_data> product_search_insert(
            @Field("my_number") String my_number,
            @Field("search") String search
    );


    @Multipart
    @POST("product_info_update.php")
    Call<product_data> product_info_update(
            @Part("idx") int idx,
            @Part("subject") String subject,
            @Part("content") String content,
            @Part("price") String price,
            @Part("time") String time,
            @Part("category") String category,
            @Part List<MultipartBody.Part> image
    );


    @FormUrlEncoded
    @POST("my_dang_guen_fragment_complete_delete.php")
    Call<product_data> my_dang_guen_fragment_complete_delete(
            @Field("idx") int idx
    );

    @FormUrlEncoded
    @POST("my_dang_guen_fragment_sell_complete_update.php")
    Call<product_data> my_dang_guen_fragment_sell_complete_update(
            @Field("idx") int idx,
            @Field("stauts") String stauts,
            @Field("phone") String phone
    );


    @FormUrlEncoded
    @POST("my_dang_guen_buy_delete.php")
    Call<product_data> my_dang_guen_buy_delete(
            @Field("idx") int idx
    );


    @FormUrlEncoded
    @POST("product_info_delete.php")
    Call<product_data> product_info_delete(
            @Field("idx") int idx
    );


    @FormUrlEncoded
    @POST("my_dang_guen_my_complete.php")
    Call<product_data> my_dang_guen_my_complete(
            @Field("idx") int idx,
            @Field("phone") String phone
    );


    //이미지 셀렉트
    @GET("product_multiimage_select.php")
    Call<List<product_data>> image_select(
            @Query("idx") int idx
    );

    //이미지 셀렉트
    @GET("product_recent_select.php")
    Call<List<product_data>> product_recent_select(
            @Query("my_number") String my_number
    );


    //검색어 인서트
    @FormUrlEncoded
    @POST("product_recent_insert.php")
    Call<product_data> product_recent_insert(
            @Field("my_number") String my_number,
            @Field("search") String search
    );




    //좋아요_insert
    @FormUrlEncoded
    @POST("product_like_insert.php")
    Call<product_data> product_like_insert(
            @Field("subject") String subject,
            @Field("content") String content,
            @Field("user_image") String user_image,
            @Field("nick") String nick,
            @Field("category") String category,
            //현재 위치가 원래 chatting 위치
            @Field("price") String price,
            @Field("image") String image,
            @Field("product_number") int product_number,
            @Field("like_phone") String like_phone,
            @Field("present_time") long present_time,
            @Field("product_info_like_count") int product_info_like_count,
            @Field("jung_go") String jung_go
    );

    //좋아요_select
    @GET("product_like_select.php")
    Call<List<product_data>> product_like_select(
            @Query("product_number") int product_number
    );

    //좋아요_delete
    @FormUrlEncoded
    @POST("product_like_delete.php")
    Call<product_data> product_like_delete(
            @Field("like_phone") String like_phone,
            @Field("product_number") int product_number,
            @Field("product_info_like_count") int product_info_like_count
    );


    //hits_업데이트
    @FormUrlEncoded
    @POST("product_info_hits_update.php")
    Call<product_data> product_info_hits_update(
            @Field("idx") int idx,
            @Field("hits") int hits
    );


    //jung_go_category
    @GET("jung_go_category_recyclerview_select.php")
    Call<List<product_data>> junggo_category_recycerview_select(
            @Query("jung_go_category") String jung_go_category
    );
}
