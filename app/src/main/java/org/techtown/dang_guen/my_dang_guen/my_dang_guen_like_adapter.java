package org.techtown.dang_guen.my_dang_guen;

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

import org.techtown.dang_guen.home.home_adapter;

import org.techtown.dong_nae_life.R;

import java.util.List;

public class my_dang_guen_like_adapter extends RecyclerView.Adapter<my_dang_guen_like_adapter.ViewHolder> {
    private Context context;
    private List<my_dang_guen_data> lists;
    private final my_dang_guen_like_adapter.ItemClickListener itemClickListener;


    public my_dang_guen_like_adapter(Context context, List<my_dang_guen_data> lists, my_dang_guen_like_adapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.lists = lists;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public my_dang_guen_like_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_dang_guen_like_item, parent, false);
        return new my_dang_guen_like_adapter.ViewHolder(view, itemClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull my_dang_guen_like_adapter.ViewHolder holder, int position) {
        my_dang_guen_data data = lists.get(position);


        holder.my_dang_guen_like_item_product_subject.setText(data.getSubject());
        holder.my_dang_guen_like_item_product_price.setText(data.getPrice());

        holder.my_dang_guen_like_item_product_chatting_count.setVisibility(View.INVISIBLE);
        holder.my_dang_guen_like_item_product_chatting_imageview.setVisibility(View.INVISIBLE);


        /*게시물 like 코드*/
        //like의 수가 존재한다면
        if(data.getLike_count() !=0){
            holder.my_dang_guen_like_item_product_like_count.setVisibility(View.VISIBLE);
            holder.my_dang_guen_like_item_product_like_imageview.setVisibility(View.VISIBLE);

            holder.my_dang_guen_like_item_product_like_count.setText(String.valueOf(data.getLike_count()));

        }
//        like의 수가 존재하지 않는다면
        else{
            holder.my_dang_guen_like_item_product_like_imageview.setVisibility(View.INVISIBLE);
            holder.my_dang_guen_like_item_product_like_count.setVisibility(View.INVISIBLE);
        }

        /*--------------*/

        Glide.with(context)
                .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + data.getImage())
                .into(holder.my_dang_guen_like_item_imageview);

        /*setClipToOutline
         * 고정하다-절때 움직이지 않는다
         * 이미지뷰를 고정한다
         *
         * drawable 모양으로 뷰를 고정하려면 사용해야되는 메소드 이다
         * */
        holder.my_dang_guen_like_item_imageview.setClipToOutline(true);

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
        holder.my_dang_guen_like_item_product_time.setText(시간값);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView my_dang_guen_like_item_product_subject,my_dang_guen_like_item_product_time,my_dang_guen_like_item_product_price,my_dang_guen_like_item_product_chatting_count,my_dang_guen_like_item_product_like_count;
        ImageView my_dang_guen_like_item_imageview,my_dang_guen_like_item_product_chatting_imageview,my_dang_guen_like_item_product_like_imageview;
        my_dang_guen_like_adapter.ItemClickListener itemClickListener;
        ImageButton my_dang_guen_like_item_product_like_after_image_button,my_dang_guen_like_item_product_like_age_image_button;


        public ViewHolder(@NonNull View view, my_dang_guen_like_adapter.ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            my_dang_guen_like_item_product_subject = view.findViewById(R.id.my_dang_guen_like_item_product_subject);
            my_dang_guen_like_item_product_time = view.findViewById(R.id.my_dang_guen_like_item_product_time);
            my_dang_guen_like_item_product_price = view.findViewById(R.id.my_dang_guen_like_item_product_price);
            my_dang_guen_like_item_product_chatting_count = view.findViewById(R.id.my_dang_guen_like_item_product_chatting_count);
            my_dang_guen_like_item_product_like_count = view.findViewById(R.id.my_dang_guen_like_item_product_like_count);
            my_dang_guen_like_item_product_chatting_imageview = view.findViewById(R.id.my_dang_guen_like_item_product_chatting_imageview);
            my_dang_guen_like_item_product_like_imageview = view.findViewById(R.id.my_dang_guen_like_item_product_like_imageview);
            my_dang_guen_like_item_product_like_after_image_button = view.findViewById(R.id.my_dang_guen_like_item_product_like_after_image_button);
            my_dang_guen_like_item_product_like_age_image_button = view.findViewById(R.id.my_dang_guen_like_item_product_like_age_image_button);
            my_dang_guen_like_item_imageview = view.findViewById(R.id.my_dang_guen_like_item_imageview);

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
