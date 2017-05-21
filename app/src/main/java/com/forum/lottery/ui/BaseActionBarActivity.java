package com.forum.lottery.ui;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.forum.lottery.R;
import com.forum.lottery.view.CustomActionBar;


public abstract class BaseActionBarActivity extends BaseActivity {
	private FrameLayout customContainer;
	private CustomActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(getLayoutId());
		customContainer = findView(R.id.custom_container);
		actionBar = new CustomActionBar(findView(R.id.my_action_bar));
		actionBar.setTitle(getTitle());
		actionBar.setOnClickListener(onClickListener);
	}
	
	protected int getLayoutId(){
		return R.layout.activity_actionbar;
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(View.inflate(this, layoutResID, null), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
	}
	
	@Override
	public void setContentView(View view) {
		setContentView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
	}
	
	@Override
	public void setContentView(View view, LayoutParams params) {
		customContainer.addView(view, params);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		actionBar.setTitle(title);
	}
	
	public CustomActionBar getCustomActionBar() {
		return actionBar;
	}
	
	@Override
	public void setTitle(int titleId) {
		super.setTitle(titleId);
		actionBar.setTitle(titleId);
	}

	public void createFragmentMenu(BaseFragment fragment){
		fragment.onMenuCreate(actionBar);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
}
