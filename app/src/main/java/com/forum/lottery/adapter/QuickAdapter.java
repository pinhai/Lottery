package com.forum.lottery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class QuickAdapter<T> extends BaseAdapter {
	private List<T> datas;
	private Context context;
	private ViewConverter viewConverter;
	private View emptyView;
	public QuickAdapter(Context context, List<T> datas){
		this.datas = datas;
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	public T get(int position){
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewConverter = getViewConverter(getItemViewType(position));
		ViewHoldHelper viewHoldHelper = ViewHoldHelper.buildViewHoldHelper(context, convertView, viewConverter.getLayoutId());
		if(convertView == null){
			onPreView(viewHoldHelper, position);
		}
		viewConverter.onConvert(this, viewHoldHelper, position);
		return viewHoldHelper.getView();
	}
	
	protected void onPreView(ViewHoldHelper viewHoldHelper, int position){}
	
	protected abstract ViewConverter getViewConverter(int viewType);

	public void setEmptyView(View emptyView){
		this.emptyView = emptyView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		if(emptyView != null){
			if(isEmpty()){
				emptyView.setVisibility(View.VISIBLE);
			}else{
				emptyView.setVisibility(View.GONE);
			}
		}
	}

	public interface OnItemClickListener<T>{
		void onItemClick(View view, int position, T item);
	}
}
