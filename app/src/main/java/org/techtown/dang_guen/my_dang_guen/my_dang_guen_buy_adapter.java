package org.techtown.dang_guen.my_dang_guen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.dang_guen.chatting.chatting_main_data;
import org.techtown.dang_guen.home.product_ApiClient;
import org.techtown.dang_guen.home.product_ApiInterface;
import org.techtown.dang_guen.home.product_data;
import org.techtown.dong_nae_life.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//extends-상속
//상속이란?상위 클래스에서 가지고 있는 변수나 함수들을 사용할수가 있다
//무엇을 상속할까?
//RecyclerView.Adapter-클래스를 상속하는 이유는 리사이클러뷰 아이템을 만들고 아이템에 데이터를 바인딩 하기 위해서
//<chatting_page_Adaptor.chattingViewHolder>-chatting_page_Adaptor클래스안에 있는 chattingViewHolder클래스에서 나오는 xml 아이디값만을 사용한다라는 뜻
public class my_dang_guen_buy_adapter extends RecyclerView.Adapter<my_dang_guen_buy_adapter.ViewHolder> {
    //안드로이드에서 메인클래스와 인터페이스를 하여서 현재 상황의 데이터들을 뽑아오는 추상클래스
    //추상은 메인 클래스에서 속성을 가지고 올수 있다는 뜻
    private Context context;
    //어댑터에서 데이터를 가지고 올수 있게 만든 리스트
    private List<product_data> list;

    //ItemClickListener는 콜백리스너이다 콜백은 내가 선택한것을 호출하는것이다
    private ItemClickListener itemClickListener;

    Dialog dialog;


    //------------------------------------------------------------------------------------------
    /*
     *채팅 페이지 클래스에서 데이터 값을 가지고 올수 있게 하기 위해서
     * */
    public my_dang_guen_buy_adapter(Context context, List<product_data> list, ItemClickListener itemClickListener,Dialog dialog) {
        //this를 사용하는 이유는 채팅 페이지 리사이클러뷰에서 넘어오는 값과 혼동하지 않기 위해서
        this.context = context;
        this.list = list;
        this.dialog = dialog;
        this.itemClickListener = itemClickListener;

    }


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */

    //NonNull-눌값을 허용하지 않는다
    @NonNull

    //@Override 왜 오버라이드를 하는것일까?

    //ViewGroup-텍스트 상자 위젯이라고 생각하면 된다

    //viewType-지정된 뷰유형을 보여주기 위해서

    //onCreateViewHolder-리사이클러뷰의 xml을 객체로 만들고 쉽게 얘기하면 아이템을 만든것

    //가장 처음에 온크리에이트 뷰홀더로 뷰객체를 만들게 된다
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ViewGroup은 결국은 아이템을 만든다는 의미
        //n개의 View를 담을 수 있는 컨테이너로 ViewGroup 또한
        // View를 상속받아 만든 클래스 입니다.
        // 또 다른 말로 레이아웃이라고도 하며 대표적으로
        // LinearLayout, RelativeLayout, FrameLayout, ConstraintLayout 등이 있습니다.


        //LayoutInflater는 xml을 자바프로그램에서 뷰를 객체로 만드는 것이다
        //attachToRoot-지금 바로 추가할거 아니면 false로 해야 된다
        //만약 true이면 바로 추가가 되야 된다
        //View view 아이템을 만드는 과정
        View view = LayoutInflater.from(context).inflate(R.layout.my_dang_guen_buy_item, parent, false);

        //왜 chatting_page_Adaptor이것을 넣으면 에러가 날까?
        return new ViewHolder(view, itemClickListener);
    }


    //------------------------------------------------------------------------------------------
    /*
     * 실제 값을 넣기 위해서
     * */
    @Override
    //onBindViewHolder는 각각의 xml의 아이디값을 가지고 와서 아이디값에 데이터 값을 넣을수 있도록 한다
    //뷰홀더는 뷰를 보관하는 객체이다
    //각 아이템이 보여질 때마다 inflate되는 것을 최소화하여 성능을 높이기 위해 ViewHolder에 View를 저장하여 사용한다.
    //재활용 되는 뷰가 호출하여 실행되는 메소드, 뷰 홀더를 전달하고 어댑터는 position의 데이터를 결합시킵니다.
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product_data data = list.get(position);


        holder.my_dang_guen_buy_subject.setText(data.getSubject());
        holder.my_dang_guen_buy_price.setText(data.getPrice());

        Glide.with(context)
                .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + data.getImage())
                .into(holder.my_dang_guen_buy_image);

        /*setClipToOutline
         * 고정하다-절때 움직이지 않는다
         * 이미지뷰를 고정한다
         * drawable 모양으로 뷰를 고정하려면 사용해야되는 메소드 이다
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
        holder.my_dang_guen_buy_time.setText(시간값);






        holder.my_dang_guen_buy_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION) {
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show(); // 다이얼로그 띄우기

                    dialog.findViewById(R.id.product_my_select_delete_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            my_dang_guen_buy_delete(data.getIdx());
                            list.remove(position);
                            notifyItemRemoved(position);
                            dialog.dismiss();
                        }
                    });


                    dialog.findViewById(R.id.product_my_select_cancle_check_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

    }


    @Override
    //리스트뷰에 추가가 되면 가장먼저 추가가 되는것이 getItemCount이다
    public int getItemCount() {
//        list.size();가 온크리에이트 뷰홀더에 리턴하게 된다
        return list.size();
    }



    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    //RecyclerView.ViewHolder는 값을 바인딩하고 아이템과 연결을 하기위해서
    //implements는 인터페이스 상속이다
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //public LinearLayout linearLayout; 이게 뭐길래 아이템을 클릭을 했는데 안열릴까?
        public LinearLayout linearLayout;
        public TextView my_dang_guen_buy_subject,my_dang_guen_buy_time,my_dang_guen_buy_price;
        ImageView my_dang_guen_buy_image,my_dang_guen_buy_menu;
        ItemClickListener itemClickListener;


        //------------------------------------------------------------------------------------------
        /*
         *온크리에이트로 만든 아이템을 값을 넣는다
         * */
        @SuppressLint("CutPasteId")
        public ViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            //super()는 부모클래스로 부터 상속받은 필드나 메소드를 자식 클래스에서 참조하기 위해서
            //chattingViewHolder에서 사용하고있는 View를 사용하기 위해서
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            my_dang_guen_buy_subject = view.findViewById(R.id.my_dang_guen_buy_subject);
            my_dang_guen_buy_time = view.findViewById(R.id.my_dang_guen_buy_time);
            my_dang_guen_buy_price = view.findViewById(R.id.my_dang_guen_buy_price);
            my_dang_guen_buy_image = view.findViewById(R.id.my_dang_guen_buy_image);
            my_dang_guen_buy_menu = view.findViewById(R.id.my_dang_guen_buy_menu);

            this.itemClickListener = itemClickListener;
            linearLayout.setOnClickListener(this);
        }


        //아이템 클릭 이벤트 처리하기
        //getAdapterPosition-현재 자신의 위치를 알수가 있다
        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    //------------------------------------------------------------------------------------------
    /*
     *
     * */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    //텍스트 삭제
    private void my_dang_guen_buy_delete(int idx) {
        product_ApiInterface ApiInterface = product_ApiClient.getClient().create(product_ApiInterface.class);
        Call<product_data> call = ApiInterface.my_dang_guen_buy_delete(idx);
        call.enqueue(new Callback<product_data>() {
            @Override
            public void onResponse(@NonNull Call<product_data> call, @NonNull Response<product_data> response) {
            }

            @Override
            public void onFailure(@NonNull Call<product_data> call, @NonNull Throwable t) {

            }
        });
    }
}