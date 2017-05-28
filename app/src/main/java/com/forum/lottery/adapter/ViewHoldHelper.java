package com.forum.lottery.adapter;


import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import com.forum.lottery.R;

public class ViewHoldHelper {
	private SparseArray<View> viewSparseArray;
	private View view;
	public static ViewHoldHelper buildViewHoldHelper(Context context, View convertView, int layoutId){
		ViewHoldHelper viewHoldHelper = null;
//		if(convertView == null){
			convertView = View.inflate(context, layoutId, null);
			viewHoldHelper = new ViewHoldHelper(convertView);
			convertView.setTag(R.id.tag_view_hold, viewHoldHelper);
//		}else{
//			viewHoldHelper = (ViewHoldHelper) convertView.getTag(R.id.tag_view_hold);
//		}
		return viewHoldHelper;
	}
	
	public ViewHoldHelper(View view){
		this.view = view;
		viewSparseArray = new SparseArray<View>();
	}
	
	public View getView() {
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id){
		View view = viewSparseArray.get(id);
		if(view == null){
			view = this.view.findViewById(id);
			viewSparseArray.put(id, view);
			return (T) view;
		}else{
			return (T) view;
		}
	}
}
