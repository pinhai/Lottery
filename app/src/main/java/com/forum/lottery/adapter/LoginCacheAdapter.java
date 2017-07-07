package com.forum.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.forum.lottery.R;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.utils.AccountManager;

import java.util.List;

public class LoginCacheAdapter extends BaseAdapter {

	private Context context;
	private List<UserVO> relist;
	private OnItemSelectedListener itemSelectedListener;

	public LoginCacheAdapter(Context ctx, List<UserVO> relist, OnItemSelectedListener itemSelectedListener) {
		this.context = ctx;
		this.relist = relist;
		this.itemSelectedListener = itemSelectedListener;
	}

	@Override
	public int getCount() {
		return relist == null ? 0 : relist.size();
	}

	@Override
	public Object getItem(int position) {
		return relist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler viewHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.cache_item, null);
			viewHodler = new ViewHodler();
			viewHodler.img_delete = (ImageButton) convertView.findViewById(R.id.img_delete);
			viewHodler.tv_loginNo = (TextView) convertView.findViewById(R.id.tv_loginNo);
			convertView.setTag(viewHodler);
		} else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		UserVO cache = relist.get(position);
		viewHodler.tv_loginNo.setText(cache.getAccount());
		viewHodler.img_delete.setOnClickListener(new OnDeleteClick(position, cache.getId()));
		viewHodler.tv_loginNo.setOnClickListener(new OnSelectClick(cache));
		return convertView;
	}

	class ViewHodler {
		private TextView tv_loginNo;
		private ImageButton img_delete;
	}

	class OnSelectClick implements OnClickListener {
		UserVO cache;

		private OnSelectClick(UserVO cache) {
			this.cache = cache;
		}

		@Override
		public void onClick(View v) {
			itemSelectedListener.onItemSelected(cache);
//			Bundle bundle = new Bundle();
//			bundle.putSerializable(Constants.SELECTE_CACHE, cache);
//			Intent intent = new Intent(Constants.SELECTE_CACHE);
//			intent.putExtras(bundle);
//			context.sendBroadcast(intent);
		}
	}

	class OnDeleteClick implements OnClickListener {
		private String id;

		private OnDeleteClick(int position, String id) {
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			AccountManager.getInstance().deleteUserPsw(id);
			notifyDataSetChanged();
		}
	}

	public interface OnItemSelectedListener {
		void onItemSelected(UserVO userVO);
	}

}
