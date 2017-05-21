package com.forum.lottery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import com.forum.lottery.R;
import com.forum.lottery.ui.TabBaseFragment;
import com.forum.lottery.view.TabBar;

import java.util.List;


/**
 * Created by Su on 2015/8/12.
 */
public class MainTabAdapter  extends PagerAdapter implements TabBar.IconProvider{
    private boolean[] isNetInit;
    private List<TabBaseFragment> fragments;
    private FragmentManager fragmentManager;
    private int curPosition = -1;
    private OnPageChangeListener onPageChangeListener;
    public MainTabAdapter(FragmentManager fragmentManager, List<TabBaseFragment> fragments) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        isNetInit = new boolean[fragments.size()];
        initFragments(fragments);
    }

    private void initFragments(List<TabBaseFragment> fragments){
        int count = fragments.size();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < count; i++){
            Fragment checkFragment = fragmentManager.findFragmentByTag(makeFragmentName(i));
            if(checkFragment != null)
                fragmentTransaction.hide(checkFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String checkName = makeFragmentName(position);
        Fragment checkFragment = fragmentManager.findFragmentByTag(checkName);
        if(checkFragment == null){
            checkFragment = fragments.get(position);
            fragmentTransaction.add(R.id.main_container, checkFragment, checkName);
        }else{
            fragmentTransaction.show(checkFragment);
        }

        if(curPosition != -1){
            String curCheckName = makeFragmentName(curPosition);
            Fragment curCheckFragment = fragmentManager.findFragmentByTag(curCheckName);
            if(curCheckFragment != null){
                fragmentTransaction.hide(curCheckFragment);
            }
        }
        fragmentTransaction.commit();
        curPosition = position;

        if(!isNetInit[position]){
            isNetInit[position] = ((TabBaseFragment) checkFragment).initNet();
        }

        if(onPageChangeListener != null)
            onPageChangeListener.onChanged(checkFragment, position);
    }

    public int getCurPosition() {
        return curPosition;
    }

    private String makeFragmentName(int position) {
        return "changeTab:" + position;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getIconResId(int position) {
        return fragments.get(position).getIcon();
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public interface OnPageChangeListener{
        void onChanged(Fragment fragment, int position);
    }
}
