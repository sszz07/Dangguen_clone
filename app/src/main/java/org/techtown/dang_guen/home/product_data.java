package org.techtown.dang_guen.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class product_data {

    @Expose
    @SerializedName("phone_number")
    private String phone_number;

    @Expose
    @SerializedName("product_search")
    private String product_search;

    @Expose
    @SerializedName("idx")
    private int idx;

    @Expose
    @SerializedName("success") private Boolean success;

    @Expose
    @SerializedName("message") private String message;

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("like_count")
    private int like_count;


    @Expose
    @SerializedName("chatting_count")
    private int chatting_count;


    public int getChatting_count() {
        return chatting_count;
    }

    public void setChatting_count(int chatting_count) {
        this.chatting_count = chatting_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getImage_number() {
        return image_number;
    }

    public void setImage_number(int image_number) {
        this.image_number = image_number;
    }

    @Expose
    @SerializedName("image_number")
    private int image_number;

    @Expose
    @SerializedName("user_image")
    private String user_image;



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Expose
    @SerializedName("time")
    private String time;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChatting() {
        return chatting;
    }

    public void setChatting(String chatting) {
        this.chatting = chatting;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Expose
    @SerializedName("subject")
    private String subject;


    @Expose
    @SerializedName("like_phone")
    private String like_phone;


    public String getLike_phone() {
        return like_phone;
    }

    public void setLike_phone(String like_phone) {
        this.like_phone = like_phone;
    }

    public String getUser_like() {
        return user_like;
    }

    public void setUser_like(String user_like) {
        this.user_like = user_like;
    }

    @Expose
    @SerializedName("user_like")
    private String user_like;

    @Expose
    @SerializedName("content")
    private String content;

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    @Expose
    @SerializedName("hits")
    private int hits;

    @Expose
    @SerializedName("nick")
    private String nick;

    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("chatting")
    private String chatting;

    @Expose
    @SerializedName("price")
    private String price;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Expose
    @SerializedName("phone")
    private String phone;


    public long getPresent_time() {
        return present_time;
    }

    public void setPresent_time(long present_time) {
        this.present_time = present_time;
    }

    @Expose
    @SerializedName("present_time")
    private long present_time;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProduct_search() {
        return product_search;
    }

    public void setProduct_search(String product_search) {
        this.product_search = product_search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
