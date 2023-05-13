package org.techtown.dang_guen.my_dang_guen;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import org.techtown.dang_guen.dong_nae_life.dong_nae_life_reply_update;
import org.techtown.dang_guen.home.home_adapter;
import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dong_nae_life.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class my_dang_guen_fragment_complete_adapter extends RecyclerView.Adapter<my_dang_guen_fragment_complete_adapter.ViewHolder> {
    private Context context;
    private List<product_data> lists;
    Dialog dialog_delete,dialog;
    private final my_dang_guen_fragment_complete_adapter.ItemClickListener itemClickListener;

    public my_dang_guen_fragment_complete_adapter(Context context, List<product_data> lists, my_dang_guen_fragment_complete_adapter.ItemClickListener itemClickListener,Dialog dialog_delete,Dialog dialog) {
        this.context = context;
        this.lists = lists;
        this.dialog_delete = dialog_delete;
        this.dialog = dialog;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public my_dang_guen_fragment_complete_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_dang_guen_completion_item, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull my_dang_guen_fragment_complete_adapter.ViewHolder holder, int position) {
        product_data data = lists.get(position);


        Glide.with(context)
                .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + data.getImage())
                .into(holder.my_dang_guen_complete_image);
        holder.my_dang_guen_complete_subject.setText(data.getSubject());
        holder.my_dang_guen_complete_price.setText(data.getPrice());


        class TIME_MAXIMUM {
            public static final int SEC = 60;
            public static final int MIN = 60;
            public static final int HOUR = 24;
            public static final int DAY = 30;
            public static final int MONTH = 12;
        }

        long 현재시간 = System.currentTimeMillis();
        long 시간계산 = (현재시간 - data.getPresent_time()) / 1000;
        System.out.println(시간계산);
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
        holder.my_dang_guen_complete_time.setText(시간값);




        holder.my_dang_guen_complete_menu.setOnClickListener(new View.OnClickListener() {
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
                    Button cancle_button = dialog.findViewById(R.id.my_dang_guen_fragment_cancle_dialog_button);
                    cancle_button.findViewById(R.id.my_dang_guen_fragment_cancle_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 원하는 기능 구현
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    });
                    // 네 버튼
                    dialog.findViewById(R.id.my_dang_guen_fragment_delete_dialog_button).setOnClickListener(new View.OnClickListener() {
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
//                dong_nae_life_info_update_select(idx);
                                }
                            });
                            // 네 버튼
                            dialog_delete.findViewById(R.id.product_my_select_delete_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    my_dang_guen_fragment_complete_delete(data.getIdx());
                                    lists.remove(position);
                                    notifyItemRemoved(position);
                                    dialog_delete.dismiss();
                                }
                            });
                        }
                    });
                    dialog.findViewById(R.id.my_dang_guen_fragment_sell_update_dialog_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            my_dang_guen_fragment_sell_complete_update(data.getIdx(),"판매중");
                            lists.remove(position);
                            notifyItemRemoved(position);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView my_dang_guen_complete_subject, my_dang_guen_complete_time, my_dang_guen_complete_price, my_dang_guen_complete_certification, home_adapter_like_count;
        ImageView my_dang_guen_complete_image;
        my_dang_guen_fragment_complete_adapter.ItemClickListener itemClickListener;
        ImageButton my_dang_guen_complete_menu;

        public ViewHolder(@NonNull View view, my_dang_guen_fragment_complete_adapter.ItemClickListener itemClickListener) {
            super(view);

            linearLayout = view.findViewById(R.id.linear_layout);
            my_dang_guen_complete_image = view.findViewById(R.id.my_dang_guen_complete_image);
            my_dang_guen_complete_subject = view.findViewById(R.id.my_dang_guen_complete_subject);
            my_dang_guen_complete_time = view.findViewById(R.id.my_dang_guen_complete_time);
            my_dang_guen_complete_price = view.findViewById(R.id.my_dang_guen_complete_price);
            my_dang_guen_complete_menu = view.findViewById(R.id.my_dang_guen_complete_menu);


            this.itemClickListener = itemClickListener;
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



    //텍스트 삭제
    private void my_dang_guen_fragment_complete_delete(int idx) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.my_dang_guen_fragment_complete_delete(idx);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }


    //텍스트 삭제
    private void my_dang_guen_fragment_sell_complete_update(int idx,String stauts) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.my_dang_guen_fragment_sell_complete_update(idx,stauts,null);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }
}
