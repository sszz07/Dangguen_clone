package org.techtown.dang_guen.dong_nae_life;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.techtown.dang_guen.home.product_data;
import org.techtown.dong_nae_life.R;


import java.util.List;

public class dong_nae_life_my_other_select_adapter extends RecyclerView.Adapter<dong_nae_life_my_other_select_adapter.showimageViewHolder> {
    private Context context;
    private List<dong_nae_life_data> lists;


    public dong_nae_life_my_other_select_adapter(Context context, List<dong_nae_life_data> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public dong_nae_life_my_other_select_adapter.showimageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_nae_life_select_my_other_item, parent, false);
        return new dong_nae_life_my_other_select_adapter.showimageViewHolder(view);
    }

    @Override
    //홀더에 맞게 데이터를 세팅을 해주는 구간
    public void onBindViewHolder(@NonNull dong_nae_life_my_other_select_adapter.showimageViewHolder holder, int position) {
        dong_nae_life_data data = lists.get(position);


        Glide.with(context)
                .asBitmap()
                .load("http://52.79.180.89/dang_guen/dang_guen_dong_nae_life_image/" + data.getImage())
                .into(holder.dong_nae_life_select_my_other_item);
    }

    @Override
    //어댑터에서 관리하는 아이템의 갯수를 반환을 한다 반환을 해야 원래 사용하는 리사이클러뷰 박스에 다른 데이터를 담을수가 있다

    public int getItemCount() {
        return lists.size();
    }


    public class showimageViewHolder extends RecyclerView.ViewHolder {
        public ImageView dong_nae_life_select_my_other_item;

        public showimageViewHolder(@NonNull View view) {
            super(view);

            dong_nae_life_select_my_other_item = view.findViewById(R.id.dong_nae_life_select_my_other_item);
        }
    }

}
