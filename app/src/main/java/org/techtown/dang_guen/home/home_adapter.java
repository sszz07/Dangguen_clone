package org.techtown.dang_guen.home;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.techtown.dong_nae_life.R;

import java.util.List;

public class home_adapter extends RecyclerView.Adapter<home_adapter.image_board_ViewHolder> {
    private Context context;
    private List<product_data> lists;
    private final ItemClickListener itemClickListener;


    public home_adapter(Context context, List<product_data> lists, ItemClickListener itemClickListener) {
        this.context = context;
        this.lists = lists;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public image_board_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_recyclerview_item, parent, false);
        return new image_board_ViewHolder(view, itemClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull image_board_ViewHolder holder, int position) {
        product_data data = lists.get(position);
        holder.subject_text.setText(data.getSubject());
        holder.price_text.setText(data.getPrice());


        /*게시물 like 코드*/
        //like의 수가 존재한다면
        if (data.getLike_count() != 0) {
            holder.home_adapter_like_count.setVisibility(View.VISIBLE);
            holder.home_adapter_like_image_button.setVisibility(View.VISIBLE);
            holder.home_adapter_like_count.setText(String.valueOf(data.getLike_count()));
        }
        //like의 수가 존재하지 않는다면
        else {
            holder.home_adapter_like_count.setVisibility(View.INVISIBLE);
            holder.home_adapter_like_image_button.setVisibility(View.INVISIBLE);
        }

        //채팅 수가 존재한다면
        if (data.getChatting_count() != 0) {
            holder.home_adapter_chatting_count.setVisibility(View.VISIBLE);
            holder.home_adapter_chatting_image_button.setVisibility(View.VISIBLE);
            holder.home_adapter_chatting_count.setText(String.valueOf(data.getChatting_count()));
        }

        //채팅 수가 존재하지 않는다면
        else {
            holder.home_adapter_chatting_count.setVisibility(View.INVISIBLE);
            holder.home_adapter_chatting_image_button.setVisibility(View.INVISIBLE);
        }

        if (data.getStatus().equals("예약중")) {
            System.out.println(data.getStatus()+"data.getStatus()111");
            holder.home_reservation_textview.setVisibility(View.VISIBLE);

        } else if (data.getStatus().equals("판매중")) {
            System.out.println(data.getStatus()+"data.getStatus()222");
            holder.home_reservation_textview.setVisibility(View.GONE);

        }


        Glide.with(context)
                .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + data.getImage())
                .into(holder.image_title);

        /*setClipToOutline
         * 고정하다-절때 움직이지 않는다
         * 이미지뷰를 고정한다
         *
         * drawable 모양으로 뷰를 고정하려면 사용해야되는 메소드 이다
         * */
        holder.image_title.setClipToOutline(true);

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
        holder.home_time_textview.setText(시간값);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class image_board_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView subject_text, price_text, home_time_textview, home_adapter_chatting_count, home_adapter_like_count, home_reservation_textview;
        ImageView image_title;
        ItemClickListener itemClickListener;
        ImageButton home_adapter_chatting_image_button, home_adapter_like_image_button;


        public image_board_ViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            subject_text = view.findViewById(R.id.home_subject_textview);
            price_text = view.findViewById(R.id.home_price_textview);
            home_time_textview = view.findViewById(R.id.home_time_textview);
            image_title = view.findViewById(R.id.main_image);
            home_adapter_chatting_image_button = view.findViewById(R.id.home_adapter_chatting_image_button);
            home_adapter_like_image_button = view.findViewById(R.id.home_adapter_like_image_button);
            home_adapter_chatting_count = view.findViewById(R.id.home_adapter_chatting_count);
            home_adapter_like_count = view.findViewById(R.id.home_adapter_like_count);
            home_reservation_textview = view.findViewById(R.id.home_reservation_textview);
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
}