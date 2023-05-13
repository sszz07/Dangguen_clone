package org.techtown.dang_guen.start_atcivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class user_data {
    @Expose
    @SerializedName("phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SerializedName("nick")
    private String nick;

    @SerializedName("image")
    private String image;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
