package org.techtown.dang_guen.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.techtown.dong_nae_life.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_search_adapter extends RecyclerView.Adapter<product_search_adapter.product_search_ViewHolder> {
    private Context context;
    private List<product_search_data> lists;
    String my_number;



    public product_search_adapter(Context context, List<product_search_data> lists,String my_number) {
        this.context = context;
        this.lists = lists;
        this.my_number = my_number;

    }

    //===== [Click 이벤트 구현을 위해 추가된 코드] ==========================
    // OnItemClickListener 인터페이스 선언
    /*
    역할?인터페이스는 해당 기술을 가져오겠다는 의미
    책임?
    협력?
    */
    public interface OnItemClickListener {
        void onItemClicked(int position, String data);
    }

    // OnItemClickListener 참조 변수 선언
      /*
    역할?메인액티비티에서 클릭한 메세지를 얻기 위해서
    책임?
    협력?
    */
    private OnItemClickListener itemClickListener;

    // OnItemClickListener 전달 메소드
    public void setOnItemClickListener (OnItemClickListener listener) {
        itemClickListener = listener;
    }
    //===================================================================

    @NonNull
    @Override
    public product_search_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_search_item, parent, false);
        return new product_search_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull product_search_ViewHolder holder, int position) {
        holder.product_search_name.setText(lists.get(position).getProduct_name());
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void filterList(ArrayList<product_search_data> filteredList) {
        lists = filteredList;
        notifyDataSetChanged();
    }

    public class product_search_ViewHolder extends RecyclerView.ViewHolder {
        TextView product_search_name;
        public LinearLayout linearLayout;

        public TextView getTextView() {
            return product_search_name;
        }

        public product_search_ViewHolder(@NonNull View view){
            super(view);
            this.product_search_name = itemView.findViewById(R.id.product_search_name);


            //===== [Click 이벤트 구현을 위해 추가된 코드] ==========================
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String data = "";
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        data = getTextView().getText().toString();
                        product_recent_insert(my_number,data);
                    }
                    itemClickListener.onItemClicked(position, data);
                }
            });
            //======================================================================
        }
    }


    //검색어 인서트
    private void product_recent_insert(String my_number,String search) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.product_recent_insert(my_number,search);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(Call<product_data> call, Response<product_data> response) {

            }

            @Override
            public void onFailure(Call<product_data> call, Throwable t) {

            }
        });

    }
}