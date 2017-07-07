package com.forum.lottery.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.forum.lottery.R;
import com.forum.lottery.adapter.ActionMenuAdapter;
import com.forum.lottery.model.PlayTypeA;
import com.forum.lottery.model.PlayTypeB;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题栏中间位置动作菜单
 * Created by admin on 2017/5/22.
 */

public class PlayWaySelectorPopup {

    private Context context;

    private MyGridView gv_playTypeA, gv_playTypeB;
    private ActionMenuAdapter adapterA, adapterB;
    private List<PlayTypeA> data;
    private List<String> dataA, dataB;
    private PopupWindow pw_recordType;
    private OnPlayTypeCheckListener playTypeCheckListener;

    private int playTypeAPosition;

    public PlayWaySelectorPopup(Context context, List<PlayTypeA> data, OnPlayTypeCheckListener listener){
        this.context = context;
        this.data = data;
        this.playTypeCheckListener = listener;

        initData();
        initPopupWindow();
    }

    public void show(View aboveView){
        pw_recordType.showAsDropDown(aboveView);
    }

    public void dismiss(){
        pw_recordType.dismiss();
    }

    private void initPopupWindow() {
        View popupView = LayoutInflater.from(context).inflate(R.layout.view_play_way_selector, null);
        pw_recordType = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        initPopupView(popupView);
        pw_recordType.setTouchable(true);
        pw_recordType.setOutsideTouchable(true);
        pw_recordType.setBackgroundDrawable(new BitmapDrawable());
//        pw_recordType.update();
//        pw_recordType.getContentView().setFocusableInTouchMode(true);
//        pw_recordType.getContentView().setFocusable(true);
//        pw_recordType.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void initData() {
        dataA = new ArrayList<>();
        dataB = new ArrayList<>();

        for(int i=0; i<data.size(); i++){
            dataA.add(data.get(i).getPlayTypeA());
        }
    }

    private void setDataB(int position){
        List<PlayTypeB> temp = data.get(position).getPlayTypeBs();
        dataB.clear();
        for(PlayTypeB b : temp){
            dataB.add(b.getPlayTypeB());
        }
        adapterB.initItemCheck();
        adapterB.notifyDataSetChanged();
    }

    private void initPopupView(View popupView) {
        adapterB = new ActionMenuAdapter(context, dataB, new ActionMenuAdapter.OnItemCheckedListener() {
            @Override
            public void onChecked(String value, int position, boolean manual) {
                PlayTypeB playTypeB = data.get(playTypeAPosition).getPlayTypeBs().get(position);
                playTypeCheckListener.playTypeChecked(data.get(playTypeAPosition), playTypeB, playTypeB.getPlayId());
            }
        });
        gv_playTypeB = (MyGridView) popupView.findViewById(R.id.gv_playTypeB);
        gv_playTypeB.setAdapter(adapterB);

        adapterA = new ActionMenuAdapter(context, dataA, new ActionMenuAdapter.OnItemCheckedListener() {
            @Override
            public void onChecked(String value, int position, boolean manual) {
                playTypeAPosition = position;
                setDataB(position);
            }
        });
        gv_playTypeA = (MyGridView) popupView.findViewById(R.id.gv_playTypeA);
        gv_playTypeA.setAdapter(adapterA);

    }

    public boolean isShowing() {
        return pw_recordType.isShowing();
    }

    public interface OnPlayTypeCheckListener{
        void playTypeChecked(PlayTypeA typeA, PlayTypeB typeB, String playId);
    }
}
