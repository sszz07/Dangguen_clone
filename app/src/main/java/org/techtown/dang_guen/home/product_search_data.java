package org.techtown.dang_guen.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class product_search_data {
    String product_name;

    public product_search_data(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}

