package org.techtown.dang_guen.home;


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
import java.util.Collections;


public class product_make_select_image_adpater extends RecyclerView.Adapter<product_make_select_image_adpater.ItemViewHolder> {
    ArrayList<Uri> list;
    Context context;

    public product_make_select_image_adpater(Context context, ArrayList<Uri> list) {
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
        Collections.reverse(list);
        holder.product_make_select_image.setImageURI(list.get(position));

        /**
         *  notifyItemRangeChanged(position, list.size());
         *  notifyItemRemoved(position); 그냥 삭제하게 되면 포지션값이 재정의가 되지 않아서 notifyItemRangeChanged(position, list.size());을 사용한다
         * */
        holder.product_make_cancle_imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    if (onItemClickListener != null) {
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());

                        for (int i = 0; i < list.size(); i++){
                            String valueA = list.get(i).toString();
                            System.out.println(valueA+"al1 object is Empty");
                            System.out.println(position+"position");
                        }

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
        public ImageView product_make_select_image;
        public ImageButton product_make_cancle_imagebutton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            product_make_select_image = itemView.findViewById(R.id.select_image);
            product_make_cancle_imagebutton = itemView.findViewById(R.id.cancle_imagebutton);
        }
    }
}

