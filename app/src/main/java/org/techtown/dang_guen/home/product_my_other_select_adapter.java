package org.techtown.dang_guen.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.techtown.dong_nae_life.R;


import java.util.List;

public class product_my_other_select_adapter extends RecyclerView.Adapter<product_my_other_select_adapter.showimageViewHolder> {
    private Context context;
    private List<product_data> lists;


    public product_my_other_select_adapter(Context context, List<product_data> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public showimageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_my_other_select_image, parent, false);
        return new showimageViewHolder(view);
    }

    @Override
    //홀더에 맞게 데이터를 세팅을 해주는 구간
    public void onBindViewHolder(@NonNull showimageViewHolder holder, int position) {
        product_data data = lists.get(position);

        Glide.with(context)
                .asBitmap()
                .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + data.getImage())
                .into(holder.product_my_other_select_item);
    }

    @Override
    //어댑터에서 관리하는 아이템의 갯수를 반환을 한다 반환을 해야 원래 사용하는 리사이클러뷰 박스에 다른 데이터를 담을수가 있다

    public int getItemCount() {
        return lists.size();
    }


    public class showimageViewHolder extends RecyclerView.ViewHolder {
        public ImageView product_my_other_select_item;

        public showimageViewHolder(@NonNull View view) {
            super(view);

            product_my_other_select_item = view.findViewById(R.id.product_my_other_select_item);
        }
    }

}