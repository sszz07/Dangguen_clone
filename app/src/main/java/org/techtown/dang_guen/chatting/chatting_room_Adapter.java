package org.techtown.dang_guen.chatting;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.techtown.dang_guen.dong_nae_life.dong_nae_life_make_select_image_adapter;
import org.techtown.dang_guen.home.product_make_select_image_adpater;
import org.techtown.dang_guen.start_atcivity.phone_certification;
import org.techtown.dang_guen.start_atcivity.user_make;
import org.techtown.dong_nae_life.R;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.techtown.dang_guen.chatting.chatting_room.room_adapter;

/*
 * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
 * */
public class chatting_room_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String 현재유저번호, your_number, my_profile_image;
    private Context context;
    ArrayList<chatting_room_data> list;
    Dialog dilaog_text_msg, dilaog_image_msg;
    PrintWriter sendWriter;


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    public chatting_room_Adapter(Context context, ArrayList<chatting_room_data> list, String 현재유저번호, PrintWriter sendWriter, String your_number, String my_profile_image) {
        this.context = context;
        this.list = list;
        this.현재유저번호 = 현재유저번호;
        this.sendWriter = sendWriter;
        this.your_number = your_number;
        this.my_profile_image = my_profile_image;
    }

    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    @SuppressLint("LongLogTag")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chatting_room_right_chat, parent, false);
                //왜 new로만 될까?
                return new my_right_ViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chatting_room_left_chat, parent, false);
                return new your_left_ViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatting_room_right_chat, parent, false);
        return new my_right_ViewHolder(view);
    }


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        dilaog_text_msg = new Dialog(context);       // Dialog 초기화
        dilaog_text_msg.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog_text_msg.setContentView(R.layout.chatting_room_delete_dialog);

        dilaog_image_msg = new Dialog(context);       // Dialog 초기화
        dilaog_image_msg.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog_image_msg.setContentView(R.layout.chatting_room_delete_dialog);

        chatting_room_data data = list.get(position);
        int delete_postion = list.get(position).getDelete_position();
String send_image = list.get(position).getSend_select_image();

        /**IndexOutOfBoundsException- 배열,문자,열 같은 종류의 인덱스가 범위를 벗어 났다는 의미
         * 객체만 받는 리스트에 비어 있다는 의미이다 */
        if (현재유저번호.equals(data.getMy_number())) {
            my_right_ViewHolder my_holder = (my_right_ViewHolder) holder;
            if (data.getSend_select_image() == null) {
                my_holder.my_message.setVisibility(View.VISIBLE);
                my_holder.chatting_room_my_text_time.setVisibility(View.VISIBLE);
                my_holder.my_select_image.setVisibility(View.GONE);
                my_holder.chatting_room_my_image_time.setVisibility(View.GONE);
                my_holder.chatting_room_my_read_check_image.setVisibility(View.GONE);

                if (data.getChatting_room_read_check().equals("읽음")) {
                    my_holder.chatting_room_my_read_check.setVisibility(View.VISIBLE);
                    my_holder.chatting_room_my_read_check.setText(data.getChatting_room_read_check());
                } else if (data.getChatting_room_read_check().equals("안읽음")) {
                    my_holder.chatting_room_my_read_check.setVisibility(View.VISIBLE);
                    my_holder.chatting_room_my_read_check.setText(data.getChatting_room_read_check());
                } else if (data.getChatting_room_read_check().equals("null")) {
                    my_holder.chatting_room_my_read_check.setVisibility(View.GONE);
                }
                my_holder.my_message.setText(data.getChatting_room_msg());
                my_holder.chatting_room_my_text_time.setText(data.getChatting_room_time());
            }

            //나의 이미지값이 보일떄
            else if (data.getChatting_room_msg() == null) {
                //메세지 레이아웃 없애기
                my_holder.my_message.setVisibility(View.GONE);

                //텍스트 시간 없애기
                my_holder.chatting_room_my_text_time.setVisibility(View.GONE);

                //내가 선택한 이미지값
                my_holder.my_select_image.setVisibility(View.VISIBLE);

                //텍스트 읽음처리
                my_holder.chatting_room_my_read_check.setVisibility(View.GONE);

                //이미지선택 시간값
                my_holder.chatting_room_my_image_time.setVisibility(View.VISIBLE);



                Glide.with(context)
                        .load("http://52.79.180.89/dang_guen/dang_guen_chatting_image/" + send_image)
                        .into(my_holder.my_select_image);

                if (data.getChatting_room_read_check().equals("읽음")) {
                    my_holder.chatting_room_my_read_check_image.setVisibility(View.VISIBLE);
                    my_holder.chatting_room_my_read_check_image.setText(data.getChatting_room_read_check());
                } else if (data.getChatting_room_read_check().equals("안읽음")) {
                    my_holder.chatting_room_my_read_check_image.setVisibility(View.VISIBLE);
                    my_holder.chatting_room_my_read_check_image.setText(data.getChatting_room_read_check());
                } else if (data.getChatting_room_read_check().equals("null")) {
                    my_holder.chatting_room_my_read_check_image.setVisibility(View.GONE);
                }

                my_holder.chatting_room_my_image_time.setText(data.getChatting_room_time());
            }
        } else {
            //상대방이 메세지값만 보일때
            if (data.getSend_select_image() == null) {
                if (delete_postion != 0) {
                    String 삭제되었습니다 = "삭제 되었습니다";
                    list.get(delete_postion).setChatting_room_msg(삭제되었습니다);
                    my_right_ViewHolder my_holder = (my_right_ViewHolder) holder;
                    your_left_ViewHolder your_holder = (your_left_ViewHolder) holder;
                    your_holder.your_message.setVisibility(View.VISIBLE);
                    your_holder.chatting_room_your_text_time.setVisibility(View.VISIBLE);
                    your_holder.your_send_image.setVisibility(View.GONE);
                    your_holder.chatting_room_your_image_time.setVisibility(View.GONE);
                    Glide.with(context)
                            .load("http://52.79.180.89/dang_guen" + data.getYour_profile_image())// image url
                            .apply(new RequestOptions().circleCrop())
                            .into(your_holder.chatting_room_your_profie_image);
                    String insert_삭제하기 = list.get(delete_postion).getChatting_room_msg();
                    your_holder.your_message.setText(insert_삭제하기);
                    your_holder.chatting_room_your_text_time.setText(data.getChatting_room_time());
                    my_holder.chatting_room_my_read_check.setVisibility(View.GONE);

                } else {
                    your_left_ViewHolder your_holder = (your_left_ViewHolder) holder;
                    if(!data.getChatting_room_msg().equals("나갔습니다")){
                        your_holder.your_message.setVisibility(View.VISIBLE);
                        your_holder.chatting_room_your_text_time.setVisibility(View.VISIBLE);
                        your_holder.your_send_image.setVisibility(View.GONE);
                        your_holder.chatting_room_your_image_time.setVisibility(View.GONE);
                        your_holder.your_message.setText(data.getChatting_room_msg());
                        Glide.with(context)
                                .load("http://52.79.180.89/dang_guen" + data.getYour_profile_image())// image url
                                .apply(new RequestOptions().circleCrop())
                                .into(your_holder.chatting_room_your_profie_image);
                        your_holder.chatting_room_your_text_time.setText(data.getChatting_room_time());
                    }else{
                        your_holder.your_message.setVisibility(View.GONE);
                        your_holder.chatting_room_your_text_time.setVisibility(View.GONE);
                        your_holder.chatting_room_your_profie_image.setVisibility(View.GONE);
                        your_holder.your_send_image.setVisibility(View.GONE);
                        your_holder.chatting_room_your_image_time.setVisibility(View.GONE);
                        your_holder.chatting_room_my_out_message.setText("상대방이 "+data.getChatting_room_msg());
                    }
                }
            }
            //상대방이 이미지값을 보냈을때
            else if (data.getChatting_room_msg() == null) {
                System.out.println("몇번들어오는지 확인!!!!!");
                your_left_ViewHolder your_holder = (your_left_ViewHolder) holder;
                your_holder.your_send_image.setVisibility(View.VISIBLE);
                your_holder.chatting_room_your_image_time.setVisibility(View.VISIBLE);
                your_holder.your_message.setVisibility(View.GONE);
                your_holder.chatting_room_your_text_time.setVisibility(View.GONE);

                Glide.with(context)
                        .load("http://52.79.180.89/dang_guen" + data.getYour_profile_image())// image url
                        .apply(new RequestOptions().circleCrop())
                        .into(your_holder.chatting_room_your_profie_image);


                Glide.with(context)
                        .load("http://52.79.180.89/dang_guen/dang_guen_chatting_image/" + send_image)// image url
                        .into(your_holder.your_send_image);

                your_holder.chatting_room_your_image_time.setText(data.getChatting_room_time());
            }
        }
    }


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    @Override
    public int getItemCount() {
        return list.size();
    }


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    public void addChat(chatting_room_data chat) {
        list.add(0, chat);
        notifyItemInserted(0);
    }


    public void delete_Chat(int position) {
        list.get(position).setChatting_room_msg("삭제 되었습니다");
        notifyItemChanged(position); //갱신
    }

    public void read_check() {
        try{
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getChatting_room_read_check().equals("안읽음")) {
                    list.get(i).setChatting_room_read_check("읽음");
                    notifyItemRangeChanged(i,list.size());
                }
            }
        }catch (NullPointerException e){

        }
    }


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getMy_number().equals(현재유저번호)) {
            return 0;
        } else {
            return 1;
        }
    }


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    public class my_right_ViewHolder extends RecyclerView.ViewHolder {
        TextView my_message;
        LinearLayout linearLayout;
        ImageView my_select_image;
        TextView chatting_room_my_text_time, chatting_room_my_read_check, chatting_room_my_image_time,chatting_room_your_out_message,chatting_room_my_read_check_image;

        //------------------------------------------------------------------------------------------
        public my_right_ViewHolder(View itemView) {
            super(itemView);
            chatting_room_my_read_check = itemView.findViewById(R.id.chatting_room_my_read_check);
            my_message = itemView.findViewById(R.id.chatting_room_my_message);
            my_select_image = itemView.findViewById(R.id.chatting_room_my_select_image);
            chatting_room_my_text_time = itemView.findViewById(R.id.chatting_room_my_text_time);
            chatting_room_my_image_time = itemView.findViewById(R.id.chatting_room_my_image_time);
            chatting_room_your_out_message = itemView.findViewById(R.id.chatting_room_your_out_message);
            chatting_room_my_read_check_image = itemView.findViewById(R.id.chatting_room_my_read_check_image);
            linearLayout = itemView.findViewById(R.id.chatmessage_item_linear);

            //포지션값을 위에 놓으면 어떤 아이템인지 클라이언트는 알수가 없다 아이템 갯수만큼 나오기 때문에
//            int position = getAdapterPosition();
//            System.out.println(position + "position+++");

            //상대방의 아이템을 클릭할수없게 my_right_ViewHolder에 넣어놨다


            /*삭제하기*/
            // itemView.setOnLongClickListener(new View.OnLongClickListener() { 아이템을 롱클릭 했을때
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                //public boolean onLongClick(View view) 반환하는 값은 boolean이고 view값을 매개변수로 받는다
                //왜 boolean값을 반환을 할까? 인터페이스를 불리언값을 받음
                //왜 view를 매개변수를 받을까?

                //    public interface OnLongClickListener {
                //        boolean onLongClick(View var1);
                //    }
                public boolean onLongClick(View view) {
                    //클릭한 해당 아이템 번호값을 가져온다
                    int position = getAdapterPosition();
                    //클릭한 해당 아이템 채팅룸번호를 가져온다
                    int chatting_room_idx = list.get(position).getChatting_room_idx();
                    String 삭제_msg = list.get(position).getChatting_room_msg();
                    String 삭제_image = list.get(position).getSend_select_image();
                    System.out.println(chatting_room_idx + "chatting_room_idx" + position + "position롱타임" + "삭제_msg" + 삭제_msg + "삭제_image" + 삭제_image);

                    //만약에 이미지 값이 눌값이고
                    if (삭제_image == null) {
                        //메세지값이 삭제되었습니다가 아니라면
                        if (!삭제_msg.equals("삭제 되었습니다")) {
                            dialog_text_msg(chatting_room_idx, position);

                            //true로 반환해서 롱클릭이 작동될수 있게 해라
                            return true;
                        } else {
                            //false이면 반환이 안되어서 롱클릭이 작동 안되게 해라
                            return false;
                        }
                    }
                    if (삭제_msg == null) {
                        if (!삭제_image.equals("삭제 되었습니다")) {
                            dialog_image_msg(chatting_room_idx, position);
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                    //다이얼로그 매개변수로 채팅룸방번호값,해당 포지션값을 보낸다
                }
            });

        }
    }


    //------------------------------------------------------------------------------------------
    /*
     * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
     * */
    public class your_left_ViewHolder extends RecyclerView.ViewHolder {
        ImageView your_send_image, chatting_room_your_profie_image;
        TextView your_message;
        TextView chatting_room_your_image_time, chatting_room_your_text_time,chatting_room_my_out_message;

        //------------------------------------------------------------------------------------------
        /*
         * 채팅목록에서 나의 값과 상대방의 값을 받아오기 위한것
         * */
        public your_left_ViewHolder(View itemView) {
            super(itemView);
            your_message = itemView.findViewById(R.id.chatting_room_your_message);
            chatting_room_your_text_time = itemView.findViewById(R.id.chatting_room_your_text_time);
            chatting_room_your_image_time = itemView.findViewById(R.id.chatting_room_your_image_time);
            your_send_image = itemView.findViewById(R.id.chatting_room_your_send_image);
            chatting_room_your_profie_image = itemView.findViewById(R.id.chatting_room_your_profie_image);
            chatting_room_my_out_message = itemView.findViewById(R.id.chatting_room_my_out_message);
        }

    }


    /*삭제하기*/
    public void dialog_text_msg(int chating_room_idx, int position) {
        dilaog_text_msg.show();// 다이얼로그 객체를 전역변수로 만들어서 .show() 화면에 보여주기로 하기


        /**주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.*/
        Button noBtn = dilaog_text_msg.findViewById(R.id.chatting_room_dialog_cancel_button);
        noBtn.findViewById(R.id.chatting_room_dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog_text_msg.dismiss(); // 다이얼로그 닫기
            }
        });

        // 네 버튼
        dilaog_text_msg.findViewById(R.id.chatting_room_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String 삭제되었습니다 = "삭제 되었습니다";

                //삭제하기 http통신으로 1.채팅방번호->삭제할 채팅메세지 쿼리문 where값 2.텍스트 메세지값 3.이미지 텍스트 값은 눌값
                chatting_room_delete(chating_room_idx, 삭제되었습니다, null);

                //해당 아이템 값의 메세지값 변경하기
                list.get(position).setChatting_room_msg(삭제되었습니다);

                //socket 통신으로 보내는 삭제한 포지션값 int값은 안되니 Stringr값으로 반환을 해준다
                String delete_position = String.valueOf(position);

                //socket 서버로 데이터값 보내기
                new Thread() {
                    @Override
                    //
                    public void run() {
                        super.run();
                        try {
                            //서비스에 만들어놓은 소켓 객체 가져옴
                            Socket socket = chatting_service.getSocket();
                            //socket.getOutputStream() 출력스트림 PrintWriter()-출력해주는 메소드 outputstream을 받아준다
                            //sendWriter는 chatting_room에서 받아옴
                            sendWriter = new PrintWriter(socket.getOutputStream());
                            //메세지값,현재시간,나의번호,너의번호,룸넘버,현재페이지,유저이미지,
                            sendWriter.print("삭제 되었습니다" + ",");
                            sendWriter.print("null" + ",");
                            sendWriter.print(현재유저번호 + ",");
                            sendWriter.print(your_number + ",");
                            sendWriter.print(chating_room_idx + ",");
                            sendWriter.print("chatting_room" + ",");
                            sendWriter.print(my_profile_image + ",");
                            sendWriter.print("null" + ",");
                            sendWriter.print(delete_position + ",");
                            sendWriter.print("null" + ",");
                            sendWriter.print("null" + ",");
                            sendWriter.print("null" + ",");
                            sendWriter.print("null" + ",");
                            sendWriter.println("사진없음");

                            //flush()-출력해주는 메소드
                            sendWriter.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                        //왜 run()이 아니라 start() 인가?
                        .start();

                room_adapter.notifyItemChanged(position);
                dilaog_text_msg.dismiss();
            }
        });
    }


    public void dialog_image_msg(int chatting_room_idx, int position) {
        dilaog_image_msg.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.


        // 아니오 버튼
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.
        Button noBtn = dilaog_image_msg.findViewById(R.id.chatting_room_dialog_cancel_button);
        noBtn.findViewById(R.id.chatting_room_dialog_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dilaog_image_msg.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        dilaog_image_msg.findViewById(R.id.chatting_room_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String 삭제되었습니다 = "삭제 되었습니다";
                chatting_room_delete(chatting_room_idx, null, 삭제되었습니다);
                list.get(position).setSend_select_image(삭제되었습니다);
                room_adapter.notifyItemChanged(position);
                dilaog_image_msg.dismiss();
            }
        });
    }

    /*삭제하기*/
    private void chatting_room_delete(int chatting_room_idx, String chatting_room_msg, String chatting_room_image) {
        chatting_Interface chatting_Interface = chatting_ApiClient.getApiClient().create(chatting_Interface.class);
        Call<chatting_room_data> call = chatting_Interface.chatting_room_delete(chatting_room_msg, chatting_room_image, chatting_room_idx);
        call.enqueue(new Callback<chatting_room_data>() {
            @Override
            public void onResponse(@NonNull Call<chatting_room_data> call, @NonNull Response<chatting_room_data> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(@NonNull Call<chatting_room_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }
}

