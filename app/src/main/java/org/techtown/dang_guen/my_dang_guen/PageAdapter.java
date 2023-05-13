package org.techtown.dang_guen.my_dang_guen;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;


import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {


    /*
   private ArrayList<Fragment> mData;
    역할?프래그먼트 클래스를 어레이리스트에 담기 위해서
    책임?어댑터로 보여주기 위해서 리스트에 클래스를 넣어줘야 한다
    협력?
       mData = new ArrayList<>();
        mData.add(new ButtonFragment());
        mData.add(new ItemFragment());
        mData.add(new ColorFragment());
        1.어레이 리스트 객체를 새로 만들고 각각 프래그먼트를 넣어준다
    */
    private ArrayList<Fragment> mData;

    /*
   public PageAdapter(@NonNull FragmentManager fm) {}
    역할?어댑터의 생성자로 메인에 데이터가 꼭 들어가야할 데이터를 설정을 해준다
    책임?어댑터에 꼭 넣어줘야 할 데이터를 넣어서 어떤 프래그먼트가 넣어질지 알아야 한다
    협력?메인 액티비티에 있는 PagerAdapter adapter = new PageAdapter(getSupportFragmentManager());
    1.메인에 꼭 매개변수에 데이터를 넣어줘야 한다
    */
    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);

         /*
mData = new ArrayList<>();
    역할?
    책임?
    협력?
    */
        //페이지 항목들을 ArrayList에 추가
        mData = new ArrayList<>();
        mData.add(new my_dang_guen_fragment_sell());
        mData.add(new my_dang_guen_fragment_complete());
    }


    /*
     public CharSequence getPageTitle(int position) {
        return (position+1)+ "번째";}
    역할?
    책임?
    협력?
    */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "판매중";
        }else if(position==1){
            return "거래완료";
        }
        return "없음";
    }


    /*
 public Fragment getItem(int position) {
        return mData.get(position);
    }
    역할?프래그먼트 클래스의 갯수를 포지션 형태로 가져오는 메소드 이다
    책임?프래그먼트 클래스를 인트형으로 리턴을 해줘야 한다
    협력?return mData.get(position);
    1.프래그먼트를 포지션값을 넣어준다
    */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }


    /*
   public int getCount() {
        return mData.size();
    }
       역할?인트형태로 클래스 갯수를 가져온다
    책임?프래그먼트 사이즈= 갯수대로 리턴을 해준다
    협력?
    */
    @Override
    public int getCount() {
        return mData.size();
    }
}
