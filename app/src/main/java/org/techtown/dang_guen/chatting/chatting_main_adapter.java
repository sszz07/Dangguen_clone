package org.techtown.dang_guen.chatting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
public class chatting_main_adapter extends RecyclerView.Adapter<chatting_main_adapter.chattingViewHolder> {
    public static final String TAG = "chatting_page_Adaptor";
    //안드로이드에서 메인클래스와 인터페이스를 하여서 현재 상황의 데이터들을 뽑아오는 추상클래스
    //추상은 메인 클래스에서 속성을 가지고 올수 있다는 뜻
    private Context context;
    long date = System.currentTimeMillis();
    //어댑터에서 데이터를 가지고 올수 있게 만든 리스트
    private List<chatting_main_data> list;

    //ItemClickListener는 콜백리스너이다 콜백은 내가 선택한것을 호출하는것이다
    private ItemClickListener itemClickListener;
    private String shared_number;

    //------------------------------------------------------------------------------------------
    /*
     *채팅 페이지 클래스에서 데이터 값을 가지고 올수 있게 하기 위해서
     * */
    public chatting_main_adapter(Context context, List<chatting_main_data> list, ItemClickListener itemClickListener, String shared_number) {
        //this를 사용하는 이유는 채팅 페이지 리사이클러뷰에서 넘어오는 값과 혼동하지 않기 위해서
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
        this.shared_number = shared_number;
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
    public chattingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ViewGroup은 결국은 아이템을 만든다는 의미
        //n개의 View를 담을 수 있는 컨테이너로 ViewGroup 또한
        // View를 상속받아 만든 클래스 입니다.
        // 또 다른 말로 레이아웃이라고도 하며 대표적으로
        // LinearLayout, RelativeLayout, FrameLayout, ConstraintLayout 등이 있습니다.


        //LayoutInflater는 xml을 자바프로그램에서 뷰를 객체로 만드는 것이다
        //attachToRoot-지금 바로 추가할거 아니면 false로 해야 된다
        //만약 true이면 바로 추가가 되야 된다
        //View view 아이템을 만드는 과정
        View view = LayoutInflater.from(context).inflate(R.layout.chatting_main_item, parent, false);

        //왜 chatting_page_Adaptor이것을 넣으면 에러가 날까?
        return new chattingViewHolder(view, itemClickListener);
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
    public void onBindViewHolder(@NonNull chattingViewHolder holder, int position) {
        chatting_main_data main_data = list.get(position);
        String my_number = main_data.getChatting_main_my_number();
        String your_number = main_data.getChatting_main_your_number();

        //아이템에 들어갈 데이터
        String chatting_main_your_profile_image = main_data.getChatting_main_your_profile_image();
        String chatting_main_last_msg = main_data.getChatting_main_last_msg();
        String product_image = main_data.getChatting_main_product_image();
        String chatting_main_your_nick = main_data.getChatting_main_your_nick();
        String chatting_main_time = main_data.getChatting_main_time();
        String chatting_main_my_nick = main_data.getChatting_main_my_nick();
        int chatting_main_my_read_cehck = main_data.getChatting_main_my_read_check();
        int chatting_main_your_read_cehck = main_data.getChatting_main_your_read_check();

        String change_chatting_main_my_read_cehck = String.valueOf(chatting_main_my_read_cehck);
        String change_chatting_main_your_read_cehck = String.valueOf(chatting_main_your_read_cehck);


        System.out.println(chatting_main_my_nick+"chatting_main_my_nickchatting_main_my_nick");
        if (my_number.equals(shared_number)) {
            //대화상대 이미지
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + chatting_main_your_profile_image)
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.chatting_main_other_user_imageview);

            holder.chatting_main_last_message.setText(chatting_main_last_msg);

            //상품 이미지
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + product_image)
                    .into(holder.product_item_image);

            holder.chatting_main_other_user_nick.setText(chatting_main_your_nick);
            holder.chatting_main_time.setText(chatting_main_time);
            if(chatting_main_my_read_cehck==0){
                holder.chatting_main_read_check.setVisibility(View.GONE);
            }else{
                holder.chatting_main_read_check.setText(change_chatting_main_my_read_cehck);
            }

        }

        //유어 컬럼에 너가 있으면 가져와
        //0번에 값이 없으면 만들어라->눌값으로 하기
        //어레이리스트 0번에 방번호값이 존재하면 만들지 마라
        else if (your_number.equals(shared_number)) {
            //대화상대 이미지
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen" + chatting_main_your_profile_image)// image url
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.chatting_main_other_user_imageview);

            holder.chatting_main_last_message.setText(chatting_main_last_msg);

            //상품 이미지
            Glide.with(context)
                    .load("http://52.79.180.89/dang_guen/dang_guen_product_image/" + product_image)// image url
                    .into(holder.product_item_image);

            holder.chatting_main_other_user_nick.setText(chatting_main_my_nick);
            holder.chatting_main_time.setText(chatting_main_time);

            if(chatting_main_your_read_cehck==0){
                holder.chatting_main_read_check.setVisibility(View.GONE);
            }else{
                holder.chatting_main_read_check.setText(change_chatting_main_your_read_cehck);
            }
        }

    }


    @Override
    //리스트뷰에 추가가 되면 가장먼저 추가가 되는것이 getItemCount이다
    public int getItemCount() {
//        list.size();가 온크리에이트 뷰홀더에 리턴하게 된다
        return list.size();
    }

    /**
     * 2월 14일 화요일
     * 1.리스트값들을 순서를 바꾸기
     * 2.방번호를 기준을 하면 새로 만들때 방번호를 만들기가 애매하고 db의 방번호값도 바꿔줘야 된다
     * 3.상품이미지값, 유저 프로필 이미지 값,닉네임값,방번호값
     * 4.디비에서 셀렉트 할때 순서대로 가져올수 있도록 만들기
     * 5.3개의 유저가 있을때는 어떻게 변하는지 확인하기
     */
    public void reset_chatting_main(String room_check, chatting_main_data main) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getChatting_main_room_check().equals(room_check)) {
                list.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
        list.add(0, main);
    }

    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    //RecyclerView.ViewHolder는 값을 바인딩하고 아이템과 연결을 하기위해서
    //implements는 인터페이스 상속이다
    public static class chattingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //public LinearLayout linearLayout; 이게 뭐길래 아이템을 클릭을 했는데 안열릴까?
        public LinearLayout linearLayout;
        public TextView chatting_main_other_user_nick, chatting_main_time, chatting_main_last_message, chatting_main_read_check;
        ImageView chatting_main_other_user_imageview, product_item_image;
        ItemClickListener itemClickListener;


        //------------------------------------------------------------------------------------------
        /*
         *온크리에이트로 만든 아이템을 값을 넣는다
         * */
        @SuppressLint("CutPasteId")
        public chattingViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            //super()는 부모클래스로 부터 상속받은 필드나 메소드를 자식 클래스에서 참조하기 위해서
            //chattingViewHolder에서 사용하고있는 View를 사용하기 위해서
            super(view);
            linearLayout = view.findViewById(R.id.chatting_main_linear_layout);
            chatting_main_other_user_nick = view.findViewById(R.id.chatting_main_other_user_nick);
            chatting_main_time = view.findViewById(R.id.chatting_main_time);
            chatting_main_last_message = view.findViewById(R.id.chatting_main_last_message);
            chatting_main_read_check = view.findViewById(R.id.chatting_main_read_check);
            chatting_main_other_user_imageview = view.findViewById(R.id.chatting_main_other_user_imageview);
            product_item_image = view.findViewById(R.id.chatting_main_product_image);

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


}
