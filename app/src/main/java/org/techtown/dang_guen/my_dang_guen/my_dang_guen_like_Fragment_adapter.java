package org.techtown.dang_guen.my_dang_guen;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class my_dang_guen_like_Fragment_adapter extends FragmentStateAdapter {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    public my_dang_guen_like_Fragment_adapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch( position ){
            case 0:
                return new my_dang_guen_like_all();
            case 1:
                return new my_dang_guen_like_jung_go();
            default:
                return new my_dang_guen_like_dong_nae_life();
        }
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}