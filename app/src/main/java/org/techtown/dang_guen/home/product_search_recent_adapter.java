package org.techtown.dang_guen.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.dang_guen.chatting.chatting_main_adapter;
import org.techtown.dong_nae_life.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class product_search_recent_adapter extends RecyclerView.Adapter<product_search_recent_adapter.ItemViewHolder> {
    private List<product_data> lists;
    Context context;
    String my_number;

    /*
 private final ItemClickListener itemClickListener;
역할?
ItemClickListener-아이템 클릭리스너 객체 만들기
itemClickListener-객체
책임?메인에서 받아온 아이템클릭리스너 객체값을 사용할수있게 해준다
협력?
this.itemClickListener = itemClickListener;
return new product_search_recent_adapter.ItemViewHolder(view,itemClickListener);

1.메인에서 아이템클릭리스너를 사용한다
2.아이템뷰를 생성을 할때 아이템 클릭리스너값도 매개변수에 넣어준다
*/
    private final ItemClickListener itemClickListener;

    public product_search_recent_adapter(Context context, List<product_data> list,String my_number,ItemClickListener itemClickListener) {
        this.context = context;
        this.lists = list;
        this.my_number = my_number;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_search_recent_item, parent, false);
        return new product_search_recent_adapter.ItemViewHolder(view,itemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        /**
         * 삭제하는 방법 어떻게 하는지 공부하기
         * position != RecyclerView.NO_POSITION 무엇인지 공부하기
         * */

        product_data data = lists.get(position);

        int idx_data = lists.get(position).getIdx();

        holder.product_search_recent_content.setText(data.getProduct_search());
        holder.product_search_recent_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_search_delete(idx_data);
                lists.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,lists.size());
            }
        });

    }


    @Override
    public int getItemCount() {
        return lists.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView product_search_recent_content;
        public ImageButton product_search_recent_cancel;

        ItemClickListener itemClickListener;


        /*
public ItemViewHolder(@NonNull View itemView, ItemClickListener itemClickListener)
역할? 메인 클래스에서 만든 아이템값을 넣어줌
onClick-현재 뷰를 인터페이스 클릭에 넣음
책임?view값을 넣어주기 위해서
*/
        public ItemViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layoutt);
            product_search_recent_cancel = itemView.findViewById(R.id.product_search_recent_cancel);
            product_search_recent_content = itemView.findViewById(R.id.product_search_recent_content);

            this.itemClickListener = itemClickListener;
            linearLayout.setOnClickListener(this);
        }


        /*
 public void onClick(View view) {
역할?
onClick-현재 뷰를 인터페이스 클릭에 넣음
책임?view값을 넣어주기 위해서
*/
        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }

    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



    //게시판 번호를 가져옴->다중이미지를 셀렉트하기 위해서
    private void product_search_delete(int idx) {
        product_ApiInterface service = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = service.product_search_delete(idx,my_number, null, null);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }



}