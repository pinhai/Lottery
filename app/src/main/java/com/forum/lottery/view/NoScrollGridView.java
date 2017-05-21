package com.forum.lottery.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2017/5/2.
 */

public class NoScrollGridView extends ViewGroup {

    private BaseAdapter mAdapter;

    public NoScrollGridView(Context context) {
        this(context, null);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public <T> void setViewBuilder(ViewBuilder<T> builder){

    }

    public interface ViewBuilder<T>{
        View getItemView(ViewGroup parentView);
        void covertView(T item, View itemView);
    }

    public class DataHolder<T>{
        private DataHolder(ViewBuilder<T> builder){

        }
    }
}
