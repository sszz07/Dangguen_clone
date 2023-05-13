package org.techtown.dang_guen.dong_nae_life;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class dong_nae_life_data {
    @Expose
    @SerializedName("idx")
    private int idx;

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("image_number")
    private int image_number;

    @Expose
    @SerializedName("user_image")
    private String user_image;

    @Expose
    @SerializedName("subject")
    private String subject;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("user_number")
    private String user_number;

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    @Expose
    @SerializedName("nick")
    private String nick;

    public int getBoard_number() {
        return board_number;
    }

    public void setBoard_number(int board_number) {
        this.board_number = board_number;
    }

    @Expose
    @SerializedName("board_number")
    private int board_number;

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    @Expose
    @SerializedName("comment_number")
    private int comment_number;

    @Expose
    @SerializedName("phone")
    private String phone;

    public String getComment_recent() {
        return comment_recent;
    }

    public void setComment_recent(String comment_recent) {
        this.comment_recent = comment_recent;
    }

    @Expose
    @SerializedName("comment_recent")
    private String comment_recent;

    public Bitmap getComment_image() {
        return comment_image;
    }

    public void setComment_image(Bitmap comment_image) {
        this.comment_image = comment_image;
    }

    @Expose
    @SerializedName("comment_image")
    private Bitmap comment_image;


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Expose
    @SerializedName("time")
    private long time;

    public long getPresent_time() {
        return present_time;
    }

    public void setPresent_time(long present_time) {
        this.present_time = present_time;
    }

    @Expose
    @SerializedName("present_time")
    private long present_time;

    public int getImage_number() {
        return image_number;
    }

    public void setImage_number(int image_number) {
        this.image_number = image_number;
    }


    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Expose
    @SerializedName("subject_together")
    private String subject_together;

    @Expose
    @SerializedName("category_together")
    private String category_together;


    @Expose
    @SerializedName("person_together")
    private String person_together;

    public String getSubject_together() {
        return subject_together;
    }

    public void setSubject_together(String subject_together) {
        this.subject_together = subject_together;
    }

    public String getCategory_together() {
        return category_together;
    }

    public void setCategory_together(String category_together) {
        this.category_together = category_together;
    }

    public String getPerson_together() {
        return person_together;
    }

    public void setPerson_together(String person_together) {
        this.person_together = person_together;
    }

    public String getWho_together() {
        return who_together;
    }

    public void setWho_together(String who_together) {
        this.who_together = who_together;
    }

    public String getDate_together() {
        return date_together;
    }

    public void setDate_together(String date_together) {
        this.date_together = date_together;
    }

    public String getClock_together() {
        return clock_together;
    }

    public void setClock_together(String clock_together) {
        this.clock_together = clock_together;
    }

    public String getContent_together() {
        return content_together;
    }

    public void setContent_together(String content_together) {
        this.content_together = content_together;
    }

    public String getLocation_together() {
        return location_together;
    }

    public void setLocation_together(String location_together) {
        this.location_together = location_together;
    }

    @Expose
    @SerializedName("who_together")
    private String who_together;


    @Expose
    @SerializedName("date_together")
    private String date_together;


    @Expose
    @SerializedName("clock_together")
    private String clock_together;


    @Expose
    @SerializedName("content_together")
    private String content_together;

    @Expose
    @SerializedName("location_together")
    private String location_together;


    public Bitmap getReply_image() {
        return reply_image;
    }

    public void setReply_image(Bitmap reply_image) {
        this.reply_image = reply_image;
    }

    @Expose
    @SerializedName("reply_image")
    private Bitmap reply_image;


    public String getReply_image_db() {
        return reply_image_db;
    }

    public void setReply_image_db(String reply_image_db) {
        this.reply_image_db = reply_image_db;
    }

    @Expose
    @SerializedName("reply_image_db")
    private String reply_image_db;


    }

