package com.forum.lottery.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forum.lottery.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/5.
 */
public class DeleteOperate {
    private View actionBarView;
    private View deleteToolbar;
    private ImageView imgDelete;
    private TextView txtDelete;
    private TextView txtCancel;
    private TextView txtChooseAll;
    private boolean isDeleting = false;
    private Set<Integer> keySets;
    private OnDeleteOperateListener onDeleteOperateListener;
    DeleteOperate(View actionBarView, View deleteToolbar){
        this.actionBarView = actionBarView;
        this.deleteToolbar = deleteToolbar;
        imgDelete = (ImageView) actionBarView.findViewById(R.id.img_delete);
        txtDelete = (TextView) deleteToolbar.findViewById(R.id.txt_delete);
        txtChooseAll = (TextView) deleteToolbar.findViewById(R.id.txt_choose_all);
        txtCancel = (TextView) actionBarView.findViewById(R.id.txt_cancel);
        keySets = new HashSet<>();
    }

    public void initDeleteState(){
        setViewState(isDeleting);
        txtDelete.setText(String.format("删除(%d)", keySets.size()));
        imgDelete.setOnClickListener(onClickListener);
        txtDelete.setOnClickListener(onClickListener);
        txtCancel.setOnClickListener(onClickListener);
        txtChooseAll.setOnClickListener(onClickListener);
    }

    public boolean isDeleting() {
        return isDeleting;
    }

    public void addKey(int key){
        keySets.add(key);
        txtDelete.setText(String.format("删除(%d)", keySets.size()));
    }

    public void removeKey(int key){
        keySets.remove(key);
        txtDelete.setText(String.format("删除(%d)", keySets.size()));
    }

    private void setViewState(boolean isDeleting){
        if(isDeleting){
            imgDelete.setVisibility(View.GONE);
            deleteToolbar.setVisibility(View.VISIBLE);
            txtCancel.setVisibility(View.VISIBLE);
        }else {
            imgDelete.setVisibility(View.VISIBLE);
            deleteToolbar.setVisibility(View.GONE);
            txtCancel.setVisibility(View.GONE);
        }
    }

    private void setDeleting(boolean isDeleting){
        if(this.isDeleting == isDeleting){
            return;
        }
        setViewState(isDeleting);
        this.isDeleting = isDeleting;
        if(onDeleteOperateListener != null)
            onDeleteOperateListener.onStateChange(this.isDeleting);
        if(!isDeleting){
            keySets.clear();
            txtDelete.setText("删除(0)");
        }
    }

    public boolean isHasKey(int key){
        return keySets.contains(key);
    }

    private int[] getArrayFormSet(Set<Integer> sets){
        int[] arrayValues = new int[sets.size()];
        int i = 0;
        for(Integer value : sets){
            arrayValues[i] = value;
            i++;
        }
        return arrayValues;
    }

    public void setOnDeleteOperateListener(OnDeleteOperateListener onDeleteOperateListener) {
        this.onDeleteOperateListener = onDeleteOperateListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_delete:
                    setDeleting(true);
                    break;
                case R.id.txt_delete:
                    if(onDeleteOperateListener != null)
                        onDeleteOperateListener.onDelete(getArrayFormSet(keySets));
                    setDeleting(false);
                    break;
                case R.id.txt_cancel:
                    setDeleting(false);
                    break;
                case R.id.txt_choose_all:
                    if(onDeleteOperateListener != null)
                        onDeleteOperateListener.onChooseAll();
                    break;
            }
        }
    };

    public interface OnDeleteOperateListener{
        void onStateChange(boolean isDeleting);
        void onDelete(int[] keys);
        void onChooseAll();
    }
}
