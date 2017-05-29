package com.forum.lottery.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.forum.lottery.R;
import com.forum.lottery.adapter.ActionMenuAdapter;
import com.forum.lottery.entity.ResultData;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题栏中间位置动作菜单
 * Created by admin_h on 2017/5/22.
 */

public class ActionMenuPopup{

    private Context context;

    private MyGridView gv_actionType;
    private ActionMenuAdapter adapter;
    private List<String> actionMenu;
    private PopupWindow pw_recordType;
    private OnItemCheckedListener itemCheckedListener;

    public ActionMenuPopup(Context context, List<String> actionMenu, OnItemCheckedListener listener){
        this.context = context;
        this.actionMenu = actionMenu;
        itemCheckedListener = listener;

        initPopupWindow();
    }

    public ActionMenuPopup(Context context, List<String> actionMenu){
        this.context = context;
        this.actionMenu = actionMenu;

        initPopupWindow();
    }

    public void show(View aboveView){
        pw_recordType.showAsDropDown(aboveView);
    }

    public void dismiss(){
        pw_recordType.dismiss();
    }

    private void initPopupWindow() {
        View popupView = LayoutInflater.from(context).inflate(R.layout.view_record_type, null);
        pw_recordType = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        initPopupView(popupView);
        pw_recordType.setTouchable(true);
        pw_recordType.setOutsideTouchable(true);
//        pw_recordType.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popup_bg));
        pw_recordType.setBackgroundDrawable(new BitmapDrawable());
//        pw_recordType.update();
//        pw_recordType.getContentView().setFocusableInTouchMode(true);
//        pw_recordType.getContentView().setFocusable(true);
//        pw_recordType.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void initPopupView(View popupView) {
        adapter = new ActionMenuAdapter(context, actionMenu, new ActionMenuAdapter.OnItemCheckedListener() {
            @Override
            public void onChecked(String value, int position, boolean manual) {
                if(itemCheckedListener != null){
                    itemCheckedListener.onCheck(value, position, manual);
                }
            }
        });
        gv_actionType = (MyGridView) popupView.findViewById(R.id.gv_actionType);
        gv_actionType.setAdapter(adapter);

    }

    public boolean isShowing() {
        return pw_recordType.isShowing();
    }

    public interface OnItemCheckedListener{
        void onCheck(String value, int position, boolean manual);
    }
}
