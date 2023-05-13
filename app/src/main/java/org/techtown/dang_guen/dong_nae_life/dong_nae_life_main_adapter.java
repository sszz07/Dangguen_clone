package org.techtown.dang_guen.dong_nae_life;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.techtown.dong_nae_life.R;

import java.util.List;

public class dong_nae_life_main_adapter extends RecyclerView.Adapter<dong_nae_life_main_adapter.image_board_ViewHolder> {
    private Context context;
    private List<dong_nae_life_data> lists;
    private final ItemClickListener itemClickListener;


    public dong_nae_life_main_adapter(Context context, List<dong_nae_life_data> lists, ItemClickListener itemClickListener) {
        this.context = context;
        this.lists = lists;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public image_board_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_nae_life_main_item, parent, false);
        return new image_board_ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull image_board_ViewHolder holder, int position) {
        dong_nae_life_data data = lists.get(position);



        if(data.getImage()==null){
            holder.dong_nae_life_main_category_toghther.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_subject.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_age.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_clock.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_date.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_person.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_nick.setVisibility(View.GONE);
            holder.dong_nae_life_main_모집중.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_content.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_personmyeong.setVisibility(View.GONE);
            holder.dong_nae_life_main_imageview.setVisibility(View.GONE);

            holder.dong_nae_life_main_subject.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_content.setVisibility(View.VISIBLE);

            holder.dong_nae_life_main_subject.setText(data.getSubject());
            holder.dong_nae_life_main_content.setText(data.getContent());
            holder.dong_nae_life_main_nick.setText(data.getNick());

        }else{
            holder.dong_nae_life_main_모집중.setVisibility(View.GONE);
            holder.dong_nae_life_main_category_toghther.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_subject.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_age.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_clock.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_date.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_nick.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_person.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_content.setVisibility(View.GONE);
            holder.dong_nae_life_main_together_personmyeong.setVisibility(View.GONE);
            System.out.println("마지막 확인");
            holder.dong_nae_life_main_imageview.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_subject.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_content.setVisibility(View.VISIBLE);

            holder.dong_nae_life_main_subject.setText(data.getSubject());
            holder.dong_nae_life_main_content.setText(data.getContent());
            holder.dong_nae_life_main_nick.setText(data.getNick());
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen/dang_guen_dong_nae_life_image/" + data.getImage())
                    .into(holder.dong_nae_life_main_imageview);

        }


        if(data.getCategory_together()!=null){
            holder.dong_nae_life_main_모집중.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_category_toghther.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_together_subject.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_together_age.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_together_clock.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_together_date.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_together_nick.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_together_person.setVisibility(View.VISIBLE);
            holder.dong_nae_life_main_together_personmyeong.setVisibility(View.VISIBLE);

            holder.dong_nae_life_main_imageview.setVisibility(View.GONE);
            holder.dong_nae_life_main_subject.setVisibility(View.GONE);
            holder.dong_nae_life_main_nick.setVisibility(View.GONE);
            holder.dong_nae_life_main_time.setVisibility(View.GONE);

            holder.dong_nae_life_main_category_toghther.setText(data.getCategory_together());
            holder.dong_nae_life_main_together_subject.setText(data.getSubject_together());
            holder.dong_nae_life_main_together_age.setText(data.getPerson_together());
            holder.dong_nae_life_main_together_clock.setText(data.getClock_together());
            holder.dong_nae_life_main_together_date.setText(data.getDate_together());
            holder.dong_nae_life_main_together_person.setText(data.getWho_together());
            holder.dong_nae_life_main_together_nick.setText(data.getNick());
            holder.dong_nae_life_main_together_content.setText(data.getContent_together());
        }

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
        holder.dong_nae_life_main_time.setText(시간값);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class image_board_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView dong_nae_life_main_subject, dong_nae_life_main_content, dong_nae_life_main_time,dong_nae_life_main_nick,dong_nae_life_main_category_toghther,dong_nae_life_main_모집중
                ,dong_nae_life_main_together_content,dong_nae_life_main_together_subject,dong_nae_life_main_together_age,dong_nae_life_main_together_clock,dong_nae_life_main_together_date,dong_nae_life_main_together_person
                ,dong_nae_life_main_together_nick,dong_nae_life_main_together_personmyeong,dong_nae_life_main_comment_count_textview;
        public ImageView dong_nae_life_main_imageview;
        ItemClickListener itemClickListener;


        public image_board_ViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            dong_nae_life_main_subject = view.findViewById(R.id.dong_nae_life_main_subject);
            dong_nae_life_main_content = view.findViewById(R.id.dong_nae_life_main_content);
            dong_nae_life_main_time = view.findViewById(R.id.dong_nae_life_main_time);
            dong_nae_life_main_nick = view.findViewById(R.id.dong_nae_life_main_nick);
            dong_nae_life_main_imageview = view.findViewById(R.id.dong_nae_life_main_imageview);
            dong_nae_life_main_together_personmyeong = view.findViewById(R.id.dong_nae_life_main_together_personmyeong);


            dong_nae_life_main_category_toghther = view.findViewById(R.id.dong_nae_life_main_category_toghther);
            dong_nae_life_main_모집중 = view.findViewById(R.id.dong_nae_life_main_모집중);
            dong_nae_life_main_together_content = view.findViewById(R.id.dong_nae_life_main_together_content);
            dong_nae_life_main_together_subject = view.findViewById(R.id.dong_nae_life_main_together_subject);
            dong_nae_life_main_together_age = view.findViewById(R.id.dong_nae_life_main_together_age);
            dong_nae_life_main_together_clock = view.findViewById(R.id.dong_nae_life_main_together_clock);
            dong_nae_life_main_together_date = view.findViewById(R.id.dong_nae_life_main_together_date);
            dong_nae_life_main_together_person = view.findViewById(R.id.dong_nae_life_main_together_person);
            dong_nae_life_main_together_nick = view.findViewById(R.id.dong_nae_life_main_together_nick);


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
