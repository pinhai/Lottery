package com.forum.lottery.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class SingleQuickAdapter<T> extends QuickAdapter<T> implements ViewConverter{
	private int layoutId;
	public SingleQuickAdapter(Context context, int layoutId, List<T> datas) {
		super(context, datas);
		this.layoutId = layoutId;
	}

	@Override
	protected ViewConverter getViewConverter(int viewType) {
		return this;
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return layoutId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onConvert(BaseAdapter adapter, ViewHoldHelper viewHoldHelper,
			int position) {
		setViewHolder(viewHoldHelper, (T) getItem(position), position);
	}
	
	protected abstract void setViewHolder(ViewHoldHelper holdHelper, T data,
			int position); 
}
