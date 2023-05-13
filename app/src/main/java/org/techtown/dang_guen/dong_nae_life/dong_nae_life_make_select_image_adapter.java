package org.techtown.dang_guen.dong_nae_life;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.dong_nae_life.R;

import java.util.ArrayList;


public class dong_nae_life_make_select_image_adapter extends RecyclerView.Adapter<dong_nae_life_make_select_image_adapter.ItemViewHolder> {
    ArrayList<Uri> list;
    Context context;

    public dong_nae_life_make_select_image_adapter(Context context, ArrayList<Uri> list) {
        this.context = context;
        this.list = list;
    }


    /**
     * OnItemClickListener 공부하기
     */
    public interface OnItemClickListener {
        void onItemClick(int pos, String image_count);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.make_recyclerview_image_itme, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //에러가 나옴 이유가 뭘까?
        //ImageView.setImageURI(android.net.Uri)' 눌이값이라고 뜬다-해결방법:인플레이터에 이미지 아이템 xml를 가져와야 되는데 리사이클러뷰 xml을 가져와버렸다

        //리사이클러뷰 이미지가 안보인다-resolveUri failed on bad bitmap uri: /storage/emulated/0 에러는 핸드폰의 이미지 저장 파일에 접근할수있게 에뮬레이터를 설정을 해줘야 된다
        //매니페스트에  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        //    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> 꼭 넣자
        holder.select_image.setImageURI(list.get(position));


        /**
         * 삭제하는 방법 어떻게 하는지 공부하기
         * position != RecyclerView.NO_POSITION 무엇인지 공부하기
         * */
        holder.cancle_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    if (onItemClickListener != null) {
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());

                        int product_make_image_count1 = list.size();
                        String image_count = Integer.toString(product_make_image_count1);
                        onItemClickListener.onItemClick(position, image_count);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView select_image;
        public ImageButton cancle_imagebutton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            select_image = itemView.findViewById(R.id.select_image);
            cancle_imagebutton = itemView.findViewById(R.id.cancle_imagebutton);

        }
    }
}


