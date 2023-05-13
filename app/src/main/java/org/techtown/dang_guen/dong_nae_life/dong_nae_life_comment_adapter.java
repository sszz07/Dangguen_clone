package org.techtown.dang_guen.dong_nae_life;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.dang_guen.my_dang_guen.profile_update;
import org.techtown.dong_nae_life.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_comment_adapter extends RecyclerView.Adapter<dong_nae_life_comment_adapter.ItemViewHolder> {
    private Context context;
    private List<dong_nae_life_data> lists;
    private ArrayList<dong_nae_life_like_data> list_like;
    Dialog dilaog, dialog_delete;
    int comment_number;
    int update_idx, board_number;
    String my_number;

    public dong_nae_life_comment_adapter(Context context, List<dong_nae_life_data> lists, Dialog dilaog, Dialog dialog_delete, String my_number, ArrayList<dong_nae_life_like_data> list_like) {
        this.context = context;
        this.lists = lists;
        this.dilaog = dilaog;
        this.dialog_delete = dialog_delete;
        this.my_number = my_number;
        this.list_like = list_like;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_nae_life_comment_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dong_nae_life_data data = lists.get(position);
        holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);
        //내가 라이크한것 데이터값 가져오기
        holder.dong_nae_life_comment_update_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    dilaog.getWindow().setGravity(Gravity.CENTER);
                    dilaog.show();

                    comment_number = data.getIdx();
                    //실시간 댓글 idx값 가져오기 위해서
                    long time = data.getPresent_time();

                    dong_nae_life_comment_adapter_select_idx(time);

                    // 다이얼로그 띄우기
                    /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
                    // 위젯 연결 방식은 각자 취향대로~
                    // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
                    // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


                    // 아니오 버튼
                    // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
                    Button cancle_button = dilaog.findViewById(R.id.product_my_select_cancle_dialog_button);
                    cancle_button.findViewById(R.id.product_my_select_cancle_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 원하는 기능 구현
                            dilaog.dismiss(); // 다이얼로그 닫기
                        }
                    });
                    // 네 버튼
                    dilaog.findViewById(R.id.product_my_select_delete_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dilaog.getWindow().setGravity(Gravity.CENTER);
                            dialog_delete.show(); // 다이얼로그 띄우기
                            /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
                            dilaog.dismiss();
                            // 위젯 연결 방식은 각자 취향대로~
                            // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
                            // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


                            // 아니오 버튼
                            // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
                            Button cancle_button = dialog_delete.findViewById(R.id.product_my_select_cancle_check_button);
                            cancle_button.findViewById(R.id.product_my_select_cancle_check_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // 원하는 기능 구현
                                    dialog_delete.dismiss(); // 다이얼로그 닫기
                                }
                            });
                            // 네 버튼
                            dialog_delete.findViewById(R.id.product_my_select_delete_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dong_nae_life_info_comment_delete(comment_number);
                                    lists.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, lists.size());
                                    dialog_delete.dismiss();
                                }
                            });
                        }
                    });
                    dilaog.findViewById(R.id.product_my_select_update_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, dong_nae_life_comment_update.class);
                            if (data.getComment_image() == null) {
                                intent.putExtra("update_idx", update_idx);
                                intent.putExtra("comment_content", data.getContent());
                                intent.putExtra("comment_image", data.getImage());
                                context.startActivity(intent);
                            } else {
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                data.getComment_image().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] byteArray = stream.toByteArray();
                                intent.putExtra("update_idx", update_idx);
                                intent.putExtra("comment_content", data.getContent());
                                intent.putExtra("comment_image", data.getImage());
                                intent.putExtra("comment_image_bitmap", byteArray);
                                context.startActivity(intent);
                            }
                        }
                    });
                }
            }
        });

        /*
         * 시간계산 되는 방법
         *
         * 왜 long으로 계산할까?
         * currentTimeMillis() api 자체가 long으로 반환을 한다
         *
         * System.currentTimeMillis():현재 시간의 값을 long으로 반환을 해준다
         *
         * 어떻게 계산이 되는가?
         * 조건문에 초는 60 분은 60 시간은 24 일 30 순서대로 계산하면서 내려가게 된다
         *
         * */
        class TIME_MAXIMUM {
            public static final int SEC = 60;
            public static final int MIN = 60;
            public static final int HOUR = 24;
            public static final int DAY = 30;
            public static final int MONTH = 12;
        }

        long 현재시간 = System.currentTimeMillis();
        long 시간계산 = (현재시간 - data.getPresent_time()) / 1000;
        String 시간값 = null;
        if (시간계산 < TIME_MAXIMUM.SEC) {
            시간값 = "방금";
        } else if ((시간계산 /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            시간값 = 시간계산 + "분전";
        } else if ((시간계산 /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            시간값 = (시간계산) + "시간전";
        } else if ((시간계산 /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            시간값 = (시간계산) + "일전";
        } else if ((시간계산 /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            시간값 = (시간계산) + "달전";
        } else {
            시간값 = (시간계산) + "년전";
        }

        /**
         * 어댑터에서 액티비티 인텐트 하는 방법
         * 첫번째 괄호()-어댑터가 어느 클래스에 상속이 되어져 있는지 확인한다
         * 현재 어댑터의 아이템은 댓글 아이템이고 아이템은 dong_nae_life_my_select 상속이 되어 있다
         * 두번째 괄호()- context를 넣어야 된다
         * ?-context는 무엇인가? 현재 액티비티의 상태를 알수가 있다
         * */
        /*댓글 데이터 대댓글 페이지 인텐트값 보내기*/
        holder.dong_nae_life_comment2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long 현재시간 = System.currentTimeMillis();
                long 시간계산 = (현재시간 - data.getPresent_time()) / 1000;
                String 시간값 = null;
                if (시간계산 < TIME_MAXIMUM.SEC) {
                    시간값 = "방금";
                } else if ((시간계산 /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
                    시간값 = 시간계산 + "분전";
                } else if ((시간계산 /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
                    시간값 = (시간계산) + "시간전";
                } else if ((시간계산 /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
                    시간값 = (시간계산) + "일전";
                } else if ((시간계산 /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
                    시간값 = (시간계산) + "달전";
                } else {
                    시간값 = (시간계산) + "년전";
                }

                Intent intent = new Intent(context, dong_nae_life_reply.class);
                intent.putExtra("comment_idx", comment_number);
                intent.putExtra("comment_image", data.getImage());
                intent.putExtra("board_idx", data.getBoard_number());
                intent.putExtra("comment_user_image", data.getUser_image());
                intent.putExtra("comment_nick", data.getNick());
                intent.putExtra("시간값", 시간값);
                intent.putExtra("comment_content", data.getContent());
                ((dong_nae_life_my_select) context).startActivity(intent);
            }
        });

        /*댓글 텍스트ㅇ 이미지x*/
        if (data.getContent() != null && data.getImage() == null && data.getComment_image() == null && data.getComment_number() == 0) {

            holder.dong_nae_life_comment_image.setVisibility(View.GONE);
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);
            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setText(data.getContent());
            holder.dong_nae_life_comment_time.setText(시간값);
            //좋아요 확인
            comment_number = data.getIdx();
            String number = String.valueOf(comment_number);
            //좋아요 체크하기기
            try {
                if (list_like.size() == 0) {
                    holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                    holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);
                } else {

                    //list.size()나 true해도 상관 없음
                    for (int i = 0; true; i++) {
                        if (list_like.get(i).getComment_number().equals(number)) {
                            holder.dong_nae_life_comment_like.setVisibility(View.INVISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);

                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {

            }

        }
        /*댓글 텍스트x 이미지ㅇ 실시간*/
        else if (data.getContent() == null && data.getComment_image() != null) {
            holder.dong_nae_life_comment_image.setVisibility(View.VISIBLE);
            comment_number = data.getIdx();
            board_number = data.getBoard_number();
            String number = String.valueOf(comment_number);
            try {
                if (list_like.size() == 0) {
                    holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                    holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);
                } else {
                    for (int i = 0; true; i++) {
                        if (list_like.get(i).getComment_number().contains(number)) {

                            holder.dong_nae_life_comment_like.setVisibility(View.INVISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.VISIBLE);
                            break;
                        } else {

                            holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);

                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {

            }

            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);

            Glide.with(context)
                    .load(data.getComment_image())
                    .into(holder.dong_nae_life_comment_image);

            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setVisibility(View.GONE);
            holder.dong_nae_life_comment_time.setText(시간값);


        }   /*댓글 텍스트x 이미지ㅇ 이미지 저장값*/ else if (data.getContent() == null && data.getImage() != null) {
            holder.dong_nae_life_comment_image.setVisibility(View.VISIBLE);
            comment_number = data.getIdx();
            board_number = data.getBoard_number();
            String number = String.valueOf(comment_number);
            try {
                if (list_like.size() == 0) {
                    holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                    holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);
                } else {
                    for (int i = 0; true; i++) {
                        if (list_like.get(i).getComment_number().contains(number)) {

                            holder.dong_nae_life_comment_like.setVisibility(View.INVISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.VISIBLE);
                            break;
                        } else {

                            holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);

                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {

            }
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);

            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getImage())
                    .into(holder.dong_nae_life_comment_image);

            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setVisibility(View.GONE);
            holder.dong_nae_life_comment_time.setText(시간값);


        }
        /*댓글 텍스트ㅇ 이미지ㅇ 실시간*/
        else if (data.getComment_image() != null && data.getContent() != null) {
            holder.dong_nae_life_comment_image.setVisibility(View.VISIBLE);
            comment_number = data.getIdx();
            board_number = data.getBoard_number();
            String number = String.valueOf(comment_number);
            try {
                if (list_like.size() == 0) {
                    holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                    holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);
                } else {
                    for (int i = 0; true; i++) {
                        if (list_like.get(i).getComment_number().contains(number)) {

                            holder.dong_nae_life_comment_like.setVisibility(View.INVISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.VISIBLE);
                            break;
                        } else {

                            holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);

                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {

            }
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);

            Glide.with(context)
                    .load(data.getComment_image())
                    .into(holder.dong_nae_life_comment_image);
            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setText(data.getContent());
            holder.dong_nae_life_comment_time.setText(시간값);


        }
        /*댓글 텍스트ㅇ 이미지ㅇ 이미지 저장값*/
        else if (data.getContent() != null && data.getImage() != null && data.getComment_number() == 0) {
            holder.dong_nae_life_comment_image.setVisibility(View.VISIBLE);
            comment_number = data.getIdx();
            board_number = data.getBoard_number();
            String number = String.valueOf(comment_number);
            try {
                if (list_like.size() == 0) {
                    holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                    holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);
                } else {
                    for (int i = 0; true; i++) {
                        if (list_like.get(i).getComment_number().contains(number)) {

                            holder.dong_nae_life_comment_like.setVisibility(View.INVISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.VISIBLE);
                            break;
                        } else {

                            holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                            holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);

                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {

            }
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);

            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getImage())
                    .into(holder.dong_nae_life_comment_image);

            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setText(data.getContent());
            holder.dong_nae_life_comment_time.setText(시간값);
        }





        /*대댓글x 이미지ㅇ*/
        else if (data.getReply_image_db() != null && data.getContent() == null && data.getComment_number() != 0) {
            System.out.println("여기에5?");
            holder.dong_nae_life_comment_image.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            margin.setMargins(100, 50, 0, 0);
            holder.linearLayout.setLayoutParams(margin);


            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);


            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getReply_image_db())
                    .into(holder.dong_nae_life_comment_image);

            holder.dong_nae_life_comment_nick.setTextSize(15);
            holder.dong_nae_life_comment_time.setTextSize(13);
            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setVisibility(View.GONE);
            holder.dong_nae_life_comment2_button.setVisibility(View.GONE);
            holder.dong_nae_life_comment_time.setText(시간값);

        }

        /*대댓글ㅇ 이미지ㅇ*/
        else if (data.getReply_image_db() != null && data.getContent() != null && data.getComment_number() != 0) {
            System.out.println("여기에6?");
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            margin.setMargins(100, 50, 0, 0);
            holder.linearLayout.setLayoutParams(margin);
            holder.dong_nae_life_comment_image.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);

            holder.dong_nae_life_comment_nick.setTextSize(15);
            holder.dong_nae_life_comment_time.setTextSize(13);
            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setText(data.getContent());
            holder.dong_nae_life_comment2_button.setVisibility(View.GONE);
            holder.dong_nae_life_comment_time.setText(시간값);


            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getReply_image_db())
                    .into(holder.dong_nae_life_comment_image);

        }    /*대댓글ㅇ 이미지x*/ else if (data.getReply_image_db() == null && data.getContent() != null && data.getComment_number() != 0) {
            System.out.println("여기에6?");
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            margin.setMargins(100, 50, 0, 0);
            holder.linearLayout.setLayoutParams(margin);
            holder.dong_nae_life_comment_image.setVisibility(View.GONE);

            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_comment_user_imageview);

            holder.dong_nae_life_comment_nick.setTextSize(15);
            holder.dong_nae_life_comment_time.setTextSize(13);
            holder.dong_nae_life_comment_nick.setText(data.getNick());
            holder.dong_nae_life_comment_content.setText(data.getContent());
            holder.dong_nae_life_comment2_button.setVisibility(View.GONE);
            holder.dong_nae_life_comment_time.setText(시간값);
        }


        /*좋아요 버튼*/

        holder.dong_nae_life_comment_like.findViewById(R.id.dong_nae_life_comment_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = lists.get(position).getIdx();
                String number_string = String.valueOf(number);
                dong_nae_life_like_data data = new dong_nae_life_like_data(number_string + "");
                list_like.add(data);

                holder.dong_nae_life_comment_like.setVisibility(View.INVISIBLE);
                holder.dong_nae_life_comment_like_orange.setVisibility(View.VISIBLE);

            }
        });


        /*좋아요 취소 버튼*/
        holder.dong_nae_life_comment_like_orange.findViewById(R.id.dong_nae_life_comment_like_orange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = lists.get(position).getIdx();
                String number_string = String.valueOf(number);
                holder.dong_nae_life_comment_like.setVisibility(View.VISIBLE);
                holder.dong_nae_life_comment_like_orange.setVisibility(View.INVISIBLE);

                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("현재유저", Context.MODE_PRIVATE);
                    String JsonArrayData = sharedPreferences.getString("댓글좋아요", "");
                    JSONArray ja = new JSONArray(JsonArrayData);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject order = ja.getJSONObject(i);
                        String 번호 = order.getString("번호");
                        //쉐어드값과 포지션을 선택한값이 같으면 삭제하기
                        if (번호.equals(number_string)) {
                            list_like.remove(i);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (IndexOutOfBoundsException e){

                }
            }
        });
    }


    //텍스트 삭제
    private void dong_nae_life_info_comment_delete(int comment_number) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_info_comment_delete(comment_number);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public void add_comment(dong_nae_life_data data) {
        /**
         * 리사이클러뷰 아이템은 상단부터 추가가 되지만 content내용의 값은 변하지 않는다 왜냐하면
         * 리사이클러뷰 아이템이 쌓일때 리스트 0부터 가장 끝부분까지 쌓인다
         * 그래서 가장 상단에 쌓여도 0번째 내용이 있는것이 눈에 보이게 된다
         * */
        lists.add(data);
        if (lists.get(lists.size() - 1).getComment_recent() != null) {
            System.out.println("최신순 어댑터");
            notifyItemInserted(0);
        } else {
            System.out.println("등록순 어댑터");
            notifyItemInserted(lists.size() - 1);
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView dong_nae_life_comment_nick, dong_nae_life_comment_time, dong_nae_life_comment_content;
        public ImageView dong_nae_life_comment_user_imageview, dong_nae_life_comment_image;
        ImageButton dong_nae_life_comment_update_delete_button;
        Button dong_nae_life_comment_like, dong_nae_life_comment2_button, dong_nae_life_comment_like_orange;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.dong_nae_life_comment_layout);
            dong_nae_life_comment_nick = itemView.findViewById(R.id.dong_nae_life_comment_nick);
            dong_nae_life_comment_time = itemView.findViewById(R.id.dong_nae_life_comment_time);
            dong_nae_life_comment_image = itemView.findViewById(R.id.dong_nae_life_comment_image);
            dong_nae_life_comment_content = itemView.findViewById(R.id.dong_nae_life_comment_content);
            dong_nae_life_comment_user_imageview = itemView.findViewById(R.id.dong_nae_life_comment_user_imageview);
            dong_nae_life_comment_update_delete_button = itemView.findViewById(R.id.dong_nae_life_comment_update_delete_button);
            dong_nae_life_comment_like = itemView.findViewById(R.id.dong_nae_life_comment_like);
            dong_nae_life_comment2_button = itemView.findViewById(R.id.dong_nae_life_comment2_button);
            dong_nae_life_comment_like_orange = itemView.findViewById(R.id.dong_nae_life_comment_like_orange);
        }
    }


    //댓글의 idx값을 가져오기 위해서
    private void dong_nae_life_comment_adapter_select_idx(long present_time) {
        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = service.dong_nae_life_comment_adapter_select_idx(present_time);
        call.enqueue(new Callback<dong_nae_life_data>() {
            @Override
            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    update_idx = response.body().getIdx();
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
            }
        });
    }
}
