package com.forum.lottery.ui.buy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.forum.lottery.R;
import com.forum.lottery.adapter.BetSelectAdapter;
import com.forum.lottery.ui.BaseBetFragment;
import com.forum.lottery.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 定位胆选择
 * Created by admin on 2017/5/21.
 * @deprecated
 */

public class DingWeiDanSelectFragment extends BaseBetFragment {

    private GridView gv_wanwei, gv_qianwei, gv_baiwei, gv_shiwei, gv_gewei;
    private BetSelectAdapter adapterWanwei, adapterQianwei, adapterBaiwei, adapterShiwei, adapterGewei;
    private List<Boolean> selectedWanwei, selectedQianwei, selectedBaiwei, selectedShiwei, selectedGewei;

    public static DingWeiDanSelectFragment newInstance(BetSelectAdapter.OnCheckedListener checkedListener){
        Bundle args = new Bundle();
        args.putSerializable("checkedListener", checkedListener);

        DingWeiDanSelectFragment fragment = new DingWeiDanSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dwd_select,container, false);
    }

    @Override
    protected void initView() {
        gv_wanwei = findView(R.id.gv_waiwei);
        gv_qianwei = findView(R.id.gv_qianwei);
        gv_baiwei = findView(R.id.gv_baiwei);
        gv_shiwei = findView(R.id.gv_shiwei);
        gv_gewei = findView(R.id.gv_gewei);
        gv_wanwei.setAdapter(adapterWanwei);
        gv_qianwei.setAdapter(adapterQianwei);
        gv_baiwei.setAdapter(adapterBaiwei);
        gv_shiwei.setAdapter(adapterShiwei);
        gv_gewei.setAdapter(adapterGewei);

    }

    @Override
    protected void initData() {
        selectedWanwei = new ArrayList<>();
        selectedQianwei = new ArrayList<>();
        selectedBaiwei = new ArrayList<>();
        selectedShiwei = new ArrayList<>();
        selectedGewei = new ArrayList<>();
        for(int i=0; i<10; i++){
            selectedWanwei.add(false);
            selectedQianwei.add(false);
            selectedBaiwei.add(false);
            selectedShiwei.add(false);
            selectedGewei.add(false);
        }
        BetSelectAdapter.OnCheckedListener onCheckedListener = (BetSelectAdapter.OnCheckedListener) getArguments().getSerializable("checkedListener");
//        adapterWanwei = new BetSelectAdapter(getActivity(), selectedWanwei, onCheckedListener);
//        adapterQianwei = new BetSelectAdapter(getActivity(), selectedQianwei, onCheckedListener);
//        adapterBaiwei = new BetSelectAdapter(getActivity(), selectedBaiwei, onCheckedListener);
//        adapterShiwei = new BetSelectAdapter(getActivity(), selectedShiwei, onCheckedListener);
//        adapterGewei = new BetSelectAdapter(getActivity(), selectedGewei, onCheckedListener);
    }

    @Override
    public void clearCheckedBetting() {
        for(int i=0; i<selectedWanwei.size(); i++){
            selectedWanwei.set(i, false);
            selectedQianwei.set(i, false);
            selectedBaiwei.set(i, false);
            selectedShiwei.set(i, false);
            selectedGewei.set(i, false);
        }
        adapterWanwei.notifyDataSetChanged();
        adapterQianwei.notifyDataSetChanged();
        adapterBaiwei.notifyDataSetChanged();
        adapterShiwei.notifyDataSetChanged();
        adapterGewei.notifyDataSetChanged();

    }

    @Override
    public void notifyDataSetChanged(){

    }
}
