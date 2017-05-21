package com.forum.lottery.adapter;

import android.widget.BaseAdapter;

public interface ViewConverter {
	int getLayoutId();
	void onConvert(BaseAdapter adapter, ViewHoldHelper viewHoldHelper, int position);
}
