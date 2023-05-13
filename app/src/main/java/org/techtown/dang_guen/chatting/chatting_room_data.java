package org.techtown.dang_guen.chatting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class chatting_room_data {
    private int viewType;

    @Expose
    @SerializedName("chatting_room_idx")
    private int chatting_room_idx;

    @Expose
    @SerializedName("delete_position")
    private int delete_position;



//    public chatting_room_data(int viewType, int chatting_room_idx, String chatting_room_msg, String chatting_room_read_check, String chatting_room_time, String your_profile_image,int delete_position,String my_number) {
//        this.viewType = viewType;
//        this.chatting_room_idx = chatting_room_idx;
//        this.chatting_room_msg = chatting_room_msg;
//        this.chatting_room_read_check = chatting_room_read_check;
//        this.chatting_room_time = chatting_room_time;
//        this.your_profile_image = your_profile_image;
//        this.delete_position = delete_position;
//        this.my_number = my_number;
//    }



    @Expose
    @SerializedName("chatting_room_msg")
    private String chatting_room_msg;

    @Expose
    @SerializedName("chatting_room_read_check")
    private String chatting_room_read_check;

    @Expose
    @SerializedName("chatting_room_time")
    private String chatting_room_time;

    @Expose
    @SerializedName("chatting_main_other_user_nick")
    private String chatting_main_other_user_nick;

    @Expose
    @SerializedName("my_number")
    private String my_number;

    @Expose
    @SerializedName("your_number")
    private String your_number;

    @Expose
    @SerializedName("room_number")
    private int room_number;

    @Expose
    @SerializedName("your_profile_image")
    private String your_profile_image;

    @Expose
    @SerializedName("send_select_image")
    private String send_select_image;

    @Expose
    @SerializedName("page")
    private int page;

    @Expose
    @SerializedName("limit")
    private int limit;

    @Expose
    @SerializedName("parent_activity")
    private String parent_activity;


    @Expose
    @SerializedName("delete")
    private String delete;



    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }


    public String getParent_activity() {
        return parent_activity;
    }

    public void setParent_activity(String parent_activity) {
        this.parent_activity = parent_activity;
    }

    public String getYour_profile_image() {
        return your_profile_image;
    }

    public void setYour_profile_image(String your_profile_image) {
        this.your_profile_image = your_profile_image;
    }


    public String getChatting_room_msg() {
        return chatting_room_msg;
    }


    /**chatting_room_data-생성자로 되어있으면 안된다
     * 눌값으로 리턴을 받게 된다*/

    public String getChatting_room_time() {
        return chatting_room_time;
    }

    public void setChatting_room_time(String chatting_room_time) {
        this.chatting_room_time = chatting_room_time;
    }

    public String getChatting_main_other_user_nick() {
        return chatting_main_other_user_nick;
    }

    public void setChatting_main_other_user_nick(String chatting_main_other_user_nick) {
        this.chatting_main_other_user_nick = chatting_main_other_user_nick;
    }

    public String getMy_number() {
        return my_number;
    }

    public void setMy_number(String my_number) {
        this.my_number = my_number;
    }

    public String getYour_number() {
        return your_number;
    }

    public void setYour_number(String your_number) {
        this.your_number = your_number;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }



    public String getSend_select_image() {
        return send_select_image;
    }

    public void setSend_select_image(String send_select_image) {
        this.send_select_image = send_select_image;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public int getChatting_room_idx() {
        return chatting_room_idx;
    }

    public void setChatting_room_idx(int chatting_room_idx) {
        this.chatting_room_idx = chatting_room_idx;
    }

    public void setChatting_room_msg(String chatting_room_msg) {
        this.chatting_room_msg = chatting_room_msg;
    }

    public int getDelete_position() {
        return delete_position;
    }

    public void setDelete_position(int delete_position) {
        this.delete_position = delete_position;
    }

    public String getChatting_room_read_check() {
        return chatting_room_read_check;
    }

    public void setChatting_room_read_check(String chatting_room_read_check) {
        this.chatting_room_read_check = chatting_room_read_check;
    }
}
