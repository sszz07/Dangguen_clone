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


public class dong_nae_life_update_image_adapter extends RecyclerView.Adapter<dong_nae_life_update_image_adapter.ItemViewHolder> {
    private Context context;
    private List<dong_nae_life_data> server_list;


    public dong_nae_life_update_image_adapter(Context context, List<dong_nae_life_data> server_list) {
        this.context = context;
        this.server_list = server_list;
    }


    @NonNull
    @Override
    public dong_nae_life_update_image_adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dong_nae_life_select_my_other_item, parent, false);
        dong_nae_life_update_image_adapter.ItemViewHolder viewHolder = new dong_nae_life_update_image_adapter.ItemViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull dong_nae_life_update_image_adapter.ItemViewHolder holder, int position) {
        //에러가 나옴 이유가 뭘까?
        //ImageView.setImageURI(android.net.Uri)' 눌이값이라고 뜬다-해결방법:인플레이터에 이미지 아이템 xml를 가져와야 되는데 리사이클러뷰 xml을 가져와버렸다
        dong_nae_life_data data = server_list.get(position);


        Glide.with(context)
                .asBitmap()
                .load("http://52.79.180.89/dang_guen/dang_guen_dong_nae_life_image/" + data.getImage())
                .into(holder.dong_nae_life_select_my_other_item);


        //리사이클러뷰 이미지가 안보인다-resolveUri failed on bad bitmap uri: /storage/emulated/0 에러는 핸드폰의 이미지 저장 파일에 접근할수있게 에뮬레이터를 설정을 해줘야 된다
        //매니페스트에  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        //    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> 꼭 넣자


    }


    @Override
    public int getItemCount() {
        return server_list.size();
    }




    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView dong_nae_life_select_my_other_item;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dong_nae_life_select_my_other_item = itemView.findViewById(R.id.dong_nae_life_select_my_other_item);


        }
    }
}

