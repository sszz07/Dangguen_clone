package org.techtown.dang_guen.dong_nae_life;

import org.techtown.dang_guen.start_atcivity.user_data;

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

public interface dong_nae_life_ApiInterface {


//    https://jaejong.tistory.com/33

//    REST통신이 무엇인지 설명
//    https://velog.io/@alwaysryu13/REST-%ED%86%B5%EC%8B%A0%EC%9D%B4%EB%9E%80-Java%EB%A1%9C-REST%ED%86%B5%EC%8B%A0%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EA%B8%B0

    //어노테이션 설명
//    https://velog.io/@soyoung-dev/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EC%BD%94%ED%8B%80%EB%A6%B0-anch8mfi

    //파일 형태로 보내는 방법
//    https://velog.io/@dev_thk28/Android-Retrofit2-Multipart%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-Java

    /*
     * --------------------------------------------------------------------------------------------------------------------------------
     * POST
     * 무엇인가?
     *POST는 클라이언트에서 서버로 리소스를 추가하거나 업데이트하기 위해 데이터를 보낼 때 사용 되는 메서드다
     *
     * POST 로 데이터를 전송할 때 길이 제한이 따로 없어 용량이 큰 데이터를 보낼 때 사용하거나 GET처럼 데이터가 외부적으로 드러나는건 아니라서 보안이 필요한 부분에 많이 사용된다
     * 하지만 암호화 하지 않으면 결국 body(클라이언트가 보내는 데이터)에도 보이는건 마찬가지이다
     *
     * 왜 사용하는가?
     * insert,update_delete를 하려고
     *
     * 어떻게 사용하는가?
     * @POST("dong_nae_life_info_insert.php")매개변수로 url
     *
     * 사용가능한 어노테이션
     * @FormUrlEncoded-데이터를 URL 인코딩 형태로 만들어 전송할 때 그래서 서버에서 아예 JSON형태로 옴
     *
     * @Field-인코딩 될 데이터에 추가하는 어노테이션,여러 데이터를 지정하려면 배열이나 List객체 사용.(두개는 무조건 같이 가야 한다)
     *
     * @Multipart
     * 서버로 파일을 전송을 할때
     * @Part
     * --------------------------------------------------------------------------------------------------------------------------------
     * GET
     * 클라이언트가 서버에 리소스로부터 정보를 요청하는 메소드
     * GET은 보안용으로 사용하면 안된다
     *
     *
     * 왜 사용하는가?
     * URL길이가 제한되기 때문에
     * 데이터의 값 그대로 가져올수 있어서
     *
     *
     * 어떻게 사용하는가?
     * @GET("dong_nae_life_multiimage_select.php")
     * 사용할수있는 어노테이션(주석)
     * Query(문의)-경로를 이용해서 데이터 가져올수도 있고 데이터를 서버에 보내기도 함

     * Call<dong_nae_life_data>
     * 무엇인가?
     * call은 응답이 왔을때 콜백으로 불려질 타입
     * <>-DTO 데이터의 응답을 받아서 객체화할 클래스 타입을 넣음
     * */


    //이미지 인서트
    @Multipart
    @POST("dong_nae_life_multiimage_insert.php")
    Call<dong_nae_life_data> dong_nae_life_multiimage_insert(
            @Part("dong_nae_life_board_number") int dong_nae_life_board_number,
            @Part List<MultipartBody.Part> image,
            @Part("time") String time
    );

    //셀렉트를 먼저 할때 이미지를 안쪽으로 먼저 넣어줘서 해야된다
    //이미지 셀렉트
    @GET("dong_nae_life_multiimage_select.php")
    Call<List<dong_nae_life_data>> dong_nae_life_multiimage_select(
            @Query("idx") int idx
    );


    //이미지를 삭제를 하기 위해서 먼저 이미지의 갯수를 먼저 보내서 해야된다 그렇게 한다음에는 3번째에 가능을 할수있도록 해야된다다    //이미지 삭제
    @FormUrlEncoded
    @POST("dong_nae_life_multiimage_delete.php")
    Call<dong_nae_life_data> dong_nae_life_multiimage_delete(
            @Field("idx") int idx
    );


    //유저 이미지 가져오기
    @FormUrlEncoded
    @POST("user_select.php")
    Call<dong_nae_life_data> user_select_image(
            @Field("phone") String phone
    );


    //이미지 업데이트
    @Multipart
    @POST("dong_nae_life_multiimage_update.php")
    Call<dong_nae_life_data> dong_nae_life_multiimage_update(
            @Part("idx") int idx,
            @Part("time") String time,
            @Part List<MultipartBody.Part> image

    );


    /**
     * @Multipart은 눌값이면 안되는 건가?
     */
    //dong_nae_life 추가
    @Multipart
    @POST("dong_nae_life_info_insert.php")
    Call<dong_nae_life_data> dong_nae_life_info_insert(
            @Part("subject") String subject,
            @Part("content") String content,
            @Part("user_image") String user_image,
            @Part("nick") String nick,
            @Part("phone") String phone,
            @Part("present_time") long present_time,
            @Part List<MultipartBody.Part> image,
            @Part("time") String time
    );


    //dong_nae_life 확인
    @GET("dong_nae_life_info_recyclerview.php")
    Call<List<dong_nae_life_data>> dong_nae_life_recyclerview_select();


    //dong_nae_life 삭제
    @FormUrlEncoded
    @POST("dong_nae_life_info_delete.php")
    Call<dong_nae_life_data> dong_nae_life_info_delete(
            @Field("idx") int idx
    );

    //dong_nae_life 수정
    @Multipart
    @POST("dong_nae_life_info_update.php")
    Call<dong_nae_life_data> dong_nae_life_info_update(
            @Part("idx") int idx,
            @Part("subject") String subject,
            @Part List<MultipartBody.Part> image,
            @Part("time") String time,
            @Part("content") String content
    );

    //dong_nae_life 수정(게시판 번호 불러오기)
    @FormUrlEncoded
    @POST("dong_nae_life_update_select_info.php")
    Call<dong_nae_life_data> dong_nae_life_info_update_select(
            @Field("idx") int idx

    );


    //dong_nae_life 다중이미지를 선택하기 위해서
    @FormUrlEncoded
    @POST("dong_nae_select_content.php")
    Call<dong_nae_life_data> dong_nae_life_info_content_select(
            @Field("content") String content
    );


    //dong_nae_life 다중이미지 선택
    @GET("dong_nae_life_multiimage_select.php")
    Call<List<dong_nae_life_data>> image_select(
            @Query("idx") int idx
    );


    //여기서부터 코멘트 부분
    //------------------------------------------------------------------------------------------------------------

    //댓글번호 get
    @GET("dong_nae_life_comment_number.php")
    Call<dong_nae_life_data> dong_nae_life_comment_number(
            @Query("board_number") int board_number
    );


    //코멘트 인서트
    @FormUrlEncoded
    @POST("dong_nae_life_comment_insert.php")
    Call<dong_nae_life_data> dong_nae_life_comment_insert(
            @Field("nick") String nick,
            @Field("user_image") String user_image,
            @Field("content") String content,
            @Field("idx") int idx,
            @Field("present_time") long present_time,
            @Field("image") String image,
            @Field("time") String time

    );

    //댓글 확인
    @GET("dong_nae_life_comment_info_recyclerview.php")
    Call<List<dong_nae_life_data>> dong_nae_life_comment_info_recyclerview(
            @Query("board_number") int board_number,
            @Query("comment_style") String 댓글보기선택
    );


    @GET("dong_nae_life_comment_like.php")
    Call<List<dong_nae_life_like_data>> dong_nae_life_comment_like(
            @Query("board_number") int board_number,
            @Query("user_number") String user_number
    );


    //댓글 대댓글 리사이클러뷰
    @GET("dong_nae_life_comment_reply_info_recyclerview.php")
    Call<List<dong_nae_life_data>> dong_nae_life_comment_reply_info_recyclerview(
    );


    //댓글 삭제
    @FormUrlEncoded
    @POST("dong_nae_life_comment_delete.php")
    Call<dong_nae_life_data> dong_nae_life_info_comment_delete(
            @Field("comment_number") int comment_number
    );

    //댓글 수정
    @FormUrlEncoded
    @POST("dong_nae_life_comment_update.php")
    Call<dong_nae_life_data> dong_nae_life_comment_update(
            @Field("idx") int idx,
            @Field("image") String image,
            @Field("content") String content,
            @Field("time") String time
    );


    @FormUrlEncoded
    @POST("dong_nae_life_comment_update.php")
    Call<dong_nae_life_data> dong_nae_life_comment_update_image(
            @Field("idx") int idx,
            @Field("image") String image
    );


    //댓글 좋아요
    @GET("dong_nae_life_comment_like.php")
    Call<dong_nae_life_data> dong_nae_life_comment_like_insert(
            @Query("board_number") int board_number
    );

    //댓글 번호 인서트
    @FormUrlEncoded
    @POST("dong_nae_life_comment_adapter_select_idx.php")
    Call<dong_nae_life_data> dong_nae_life_comment_adapter_select_idx(
            @Field("present_time") long present_time
    );


    //댓글 번호 인서트
    @FormUrlEncoded
    @POST("dong_nae_life_comment_like_insert.php")
    Call<dong_nae_life_data> dong_nae_life_comment_like_insert(
            @Field("comment_number") int comment_number,
            @Field("board_number") int board_number,
            @Field("my_number") String my_number
    );

    //댓글 번호 삭제
    @FormUrlEncoded
    @POST("dong_nae_life_comment_like_delete.php")
    Call<dong_nae_life_data> dong_nae_life_comment_like_delete(
            @Field("comment_number") int comment_number,
            @Field("my_number") String my_number
    );


    //여기서부터 같이해요 부분
    //------------------------------------------------------------------------------------------------------------
    @FormUrlEncoded
    @POST("dong_nae_life_together_insert.php")
    Call<dong_nae_life_data> dong_nae_life_together_insert(
            @Field("subject_together") String subject_together,
            @Field("content_together") String content_together,
            @Field("person_together") String person_together,
            @Field("who_together") String who_together,
            @Field("date_together") String date_together,
            @Field("clock_together") String clock_together,
            @Field("category_together") String category_together,
            @Field("location_together") String location_together,
            @Field("nick") String nick,
            @Field("user_image") String user_image
    );


    //여기서부터 대댓글 부분
    //------------------------------------------------------------------------------------------------------------
    /*대댓글 insert*/
    @FormUrlEncoded
    @POST("dong_nae_life_reply_insert.php")
    Call<dong_nae_life_data> dong_nae_life_reply_insert(
            @Field("user_image") String user_image,
            @Field("present_time") long present_time,
            @Field("content") String content,
            @Field("nick") String nick,
            @Field("comment_number") int comment_number,
            @Field("time") String time,
            @Field("board_number") int board_number,
            @Field("image") String image
    );

    /*대댓글 select*/
    @GET("dong_nae_life_reply_info_recyclerview.php")
    Call<List<dong_nae_life_data>> dong_nae_life_reply_info_recyclerview(
            @Query("comment_number") int comment_number
    );

    //
//
    /*대댓글 delete*/
    @FormUrlEncoded
    @POST("dong_nae_life_reply_delete.php")
    Call<dong_nae_life_data> dong_nae_life_info_reply_delete(
            @Field("reply_number") int reply_number
    );

    //
    /*대댓글 update*/
    @FormUrlEncoded
    @POST("dong_nae_life_reply_update.php")
    Call<dong_nae_life_data> dong_nae_life_reply_update(
            @Field("idx") int idx,
            @Field("image") String image,
            @Field("content") String content,
            @Field("time") String time
    );
}
