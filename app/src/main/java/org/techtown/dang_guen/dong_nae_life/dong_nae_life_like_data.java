package org.techtown.dang_guen.dong_nae_life;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class dong_nae_life_like_data {


    public String getComment_number() {
        return comment_number;
    }

    public void setComment_number(String comment_number) {
        this.comment_number = comment_number;
    }

    private String comment_number;

    public dong_nae_life_like_data(String comment_number) {
        this.comment_number = comment_number;
    }
}
