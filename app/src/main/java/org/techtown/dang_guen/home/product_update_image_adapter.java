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


public class product_update_image_adapter extends RecyclerView.Adapter<product_update_image_adapter.ItemViewHolder> {
    private Context context;
    private List<product_data> server_list;


    public product_update_image_adapter(Context context, List<product_data> server_list) {
        this.context = context;
        this.server_list = server_list;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.product_update_image_recyclerview, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //에러가 나옴 이유가 뭘까?
        //ImageView.setImageURI(android.net.Uri)' 눌이값이라고 뜬다-해결방법:인플레이터에 이미지 아이템 xml를 가져와야 되는데 리사이클러뷰 xml을 가져와버렸다
        product_data data = server_list.get(position);


        Glide.with(context)
                .asBitmap()
                .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + data.getImage())
                .into(holder.product_update_image);


        //리사이클러뷰 이미지가 안보인다-resolveUri failed on bad bitmap uri: /storage/emulated/0 에러는 핸드폰의 이미지 저장 파일에 접근할수있게 에뮬레이터를 설정을 해줘야 된다
        //매니페스트에  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        //    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> 꼭 넣자
    }


    @Override
    public int getItemCount() {
        return server_list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView product_update_image;
//        public ImageButton product_make_cancle_imagebutton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            product_update_image = itemView.findViewById(R.id.product_update_image);
//            product_make_cancle_imagebutton = itemView.findViewById(R.id.product_make_cancle_imagebutton);


        }
    }
}

