package org.techtown.dang_guen.my_dang_guen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.techtown.dong_nae_life.R;

public class my_dang_guen_like_jung_go extends Fragment {

    @Override
    //View 프래그먼트는 뷰의 값을 반환을 해줘야 된다
    //LayoutInflater는 레이아웃을 객체로 인스턴트(레이아웃을 값으로 변환해준다)화 해준다
    //레이아웃을 객체화 해주고->레이아웃을 값이 변경이 안되게 해주고->레이아웃을 저장을 해준다
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //다시 현재 프래그먼트로 돌아왔을때 레이아웃으로 반환을 해줘야 된다
        return inflater.inflate(R.layout.activity_my_dang_guen_like_jung_go, container, false);
    }
}