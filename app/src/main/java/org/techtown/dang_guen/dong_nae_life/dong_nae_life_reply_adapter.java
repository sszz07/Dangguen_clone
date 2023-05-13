package org.techtown.dang_guen.dong_nae_life;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dong_nae_life_reply_adapter extends RecyclerView.Adapter<dong_nae_life_reply_adapter.ItemViewHolder> {
    private Context context;
    private List<dong_nae_life_data> lists;
    Dialog dialog, dialog_delete;
    ArrayList<String> reply_like_array = new ArrayList<>();
    String key = "댓글 번호";
    int reply_like_number;

    public dong_nae_life_reply_adapter(Context context, List<dong_nae_life_data> lists, Dialog dialog_delete, Dialog dialog) {
        this.context = context;
        this.lists = lists;
        this.dialog_delete = dialog_delete;
        this.dialog = dialog;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_nae_life_reply_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        dong_nae_life_data data = lists.get(position);
        int reply_number = data.getIdx();

        holder.dong_nae_life_reply_update_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show(); // 다이얼로그 띄우기
                    /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

                    // 위젯 연결 방식은 각자 취향대로~
                    // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
                    // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.

                    // 아니오 버튼
                    // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
                    Button cancle_button = dialog.findViewById(R.id.product_my_select_cancle_dialog_button);
                    cancle_button.findViewById(R.id.product_my_select_cancle_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 원하는 기능 구현
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    });
                    // 네 버튼
                    dialog.findViewById(R.id.product_my_select_delete_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.getWindow().setGravity(Gravity.CENTER);
                            dialog_delete.show(); // 다이얼로그 띄우기
                            /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */
                            dialog.dismiss();
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
                                    dong_nae_life_info_reply_delete(reply_number);
                                    lists.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, lists.size());
                                    dialog_delete.dismiss();
                                }
                            });
                        }
                    });
                    dialog.findViewById(R.id.product_my_select_update_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            Intent intent = new Intent(context, dong_nae_life_reply_update.class);
                            intent.putExtra("reply_idx", data.getIdx());
                            intent.putExtra("reply_content", data.getContent());
                            intent.putExtra("reply_image", data.getImage());
                            context.startActivity(intent);
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


        /*텍스트ㅇ 이미지x */
        if (data.getImage() == null && data.getContent() != null && data.getImage() == null) {
            /*대댓글 이미지뷰 없애기*/
            holder.dong_nae_life_reply_image.setVisibility(View.GONE);

            /*대댓글 등록한 유저 이미지*/
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_reply_user_imageview);

            holder.dong_nae_life_reply_nick.setText(data.getNick());
            holder.dong_nae_life_reply_content.setText(data.getContent());
            holder.dong_nae_life_reply_time.setText(시간값);
        }

        /*텍스트ㅇ 이미지ㅇ (실시간) */
        else if (data.getImage() != null && data.getContent() != null) {
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_reply_user_imageview);
            holder.dong_nae_life_reply_nick.setText(data.getNick());
            holder.dong_nae_life_reply_content.setText(data.getContent());
            holder.dong_nae_life_reply_time.setText(시간값);

            Glide.with(context)
                    .load(data.getImage())
                    .into(holder.dong_nae_life_reply_image);
        }

        /*텍스트ㅇ 이미지ㅇ */
        else if (data.getImage() != null && data.getContent() != null) {
            holder.dong_nae_life_reply_image.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_reply_user_imageview);


            if (data.getImage() != null && data.getBoard_number() ==0) {
                Glide.with(context)
                        .load("http://52.79.180.89/dang_guen" + data.getImage())
                        .into(holder.dong_nae_life_reply_image);
            } else if(data.getImage() != null) {
                Glide.with(context)
                        .load(data.getImage())
                        .into(holder.dong_nae_life_reply_image);
            }
            holder.dong_nae_life_reply_nick.setText(data.getNick());
            holder.dong_nae_life_reply_content.setText(data.getContent());
            holder.dong_nae_life_reply_time.setText(시간값);
        }

        /*텍스트x 이미지ㅇ */
        else if (data.getImage() != null && data.getContent() == null && data.getBoard_number() == 0) {
            holder.dong_nae_life_reply_image.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_reply_user_imageview);
            holder.dong_nae_life_reply_nick.setText(data.getNick());
            holder.dong_nae_life_reply_content.setVisibility(View.GONE);
            holder.dong_nae_life_reply_time.setText(시간값);

            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getImage())
                    .into(holder.dong_nae_life_reply_image);
        }


        /*텍스트x 이미지ㅇ (실시간) */
        else if (data.getImage() != null && data.getBoard_number() == 1) {
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + data.getUser_image())
                    .apply(new RequestOptions()
                            .circleCrop())
                    .into(holder.dong_nae_life_reply_user_imageview);
            holder.dong_nae_life_reply_nick.setText(data.getNick());
            holder.dong_nae_life_reply_content.setVisibility(View.GONE);
            holder.dong_nae_life_reply_time.setText(시간값);

            Glide.with(context)
                    .load(data.getImage())
                    .into(holder.dong_nae_life_reply_image);
        }
    }


    /*대댓글 삭제*/
    private void dong_nae_life_info_reply_delete(int reply_number) {
        dong_nae_life_ApiInterface ApiInterface = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
        Call<dong_nae_life_data> call = ApiInterface.dong_nae_life_info_reply_delete(reply_number);
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

    public void add_reply(dong_nae_life_data data) {
        /**
         * 리사이클러뷰 아이템은 상단부터 추가가 되지만 content내용의 값은 변하지 않는다 왜냐하면
         * 리사이클러뷰 아이템이 쌓일때 리스트 0부터 가장 끝부분까지 쌓인다
         * 그래서 가장 상단에 쌓여도 0번째 내용이 있는것이 눈에 보이게 된다
         * */
        lists.add(data);
        notifyItemInserted(lists.size() - 1);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView dong_nae_life_reply_nick, dong_nae_life_reply_time, dong_nae_life_reply_content;
        public ImageView dong_nae_life_reply_user_imageview, dong_nae_life_reply_image;
        ImageButton dong_nae_life_reply_update_delete_button;
        Button dong_nae_life_reply_like, dong_nae_life_reply2_orange_button;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dong_nae_life_reply_nick = itemView.findViewById(R.id.dong_nae_life_reply_nick);
            dong_nae_life_reply_time = itemView.findViewById(R.id.dong_nae_life_reply_time);
            dong_nae_life_reply_image = itemView.findViewById(R.id.dong_nae_life_reply_image);
            dong_nae_life_reply_content = itemView.findViewById(R.id.dong_nae_life_reply_content);
            dong_nae_life_reply_user_imageview = itemView.findViewById(R.id.dong_nae_life_reply_user_imageview);
            dong_nae_life_reply_update_delete_button = itemView.findViewById(R.id.dong_nae_life_reply_update_delete_button);
            dong_nae_life_reply_like = itemView.findViewById(R.id.dong_nae_life_reply_like);
            dong_nae_life_reply2_orange_button = itemView.findViewById(R.id.dong_nae_life_reply_like_orange);
        }
    }


//    private void dong_nae_life_comment_like(int board_number) {
//        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
//        Call<dong_nae_life_data> call = service.dong_nae_life_comment_like(board_number);
//        call.enqueue(new Callback<dong_nae_life_data>() {
//            @Override
//            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    int fdjafd = response.body().getComment_number();
//                    System.out.println(fdjafd+"fdjafd");
//                }
//            }
//
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
//                Log.e("product_info_subject_select", "에러 : " + t.getMessage());
//            }
//        });
//    }


//    /*댓글 좋아요 쉐어드 저장*/
//    private void setStringArrayPref(String key, ArrayList<String> values) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        JSONArray a = new JSONArray();
//
//        for (int i = 0; i < values.size(); i++) {
//            a.put(values.get(i));
//        }
//
//        if (!values.isEmpty()) {
//            editor.putString(key, a.toString());
//        } else {
//            editor.putString(key, null);
//        }
//
//        editor.apply();
//    }


//    /*댓글 좋아요 쉐어드 저장갑 불러오기*/
//    private ArrayList getStringArrayPref(String key) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String json = prefs.getString(key, null);
//        ArrayList urls = new ArrayList();
//
//        if (json != null) {
//            try {
//                JSONArray a = new JSONArray(json);
//
//                for (int i = 0; i < a.length(); i++) {
//                    String url = a.optString(i);
//                    urls.add(url);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return urls;
//    }


//    //좋아요 번호 인서트
//    private void dong_nae_life_reply_like_insert(int reply_number, int board_number) {
//        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
//        Call<dong_nae_life_data> call = service.dong_nae_life_reply_like_insert(reply_number, board_number);
//        call.enqueue(new Callback<dong_nae_life_data>() {
//            @Override
//            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    reply_like_number = response.body().getComment_number();
//                    System.out.println(reply_like_number + "reply_like_number1");
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
//                Log.e("insertPerson()", "에러 : " + t.getMessage());
//            }
//        });
//    }
//
//
//    //좋아요 번호삭제
//    private void dong_nae_life_like_delete(int reply_number) {
//        dong_nae_life_ApiInterface service = dong_nae_life_ApiClient.getClient().create(dong_nae_life_ApiInterface.class);
//        Call<dong_nae_life_data> call = service.dong_nae_life_reply_like_delete(reply_number);
//        call.enqueue(new Callback<dong_nae_life_data>() {
//            @Override
//            public void onResponse(@NonNull Call<dong_nae_life_data> call, @NonNull Response<dong_nae_life_data> response) {
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<dong_nae_life_data> call, @NonNull Throwable t) {
//
//            }
//        });
//    }


}
