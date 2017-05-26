package com.forum.lottery.ui;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

	private ProgressDialog progressDialog2;

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

	/**
	 * 点击返回键，将结束当前Activity
	 */
	public void showProgressDialog() {
		if (progressDialog2 == null) {
			progressDialog2 = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
			progressDialog2.setTitle(getString(R.string.prompt));
			progressDialog2.setMessage(getString(R.string.loading));
			progressDialog2.setCancelable(true);
			progressDialog2.setCanceledOnTouchOutside(false);
		}
		progressDialog2.show();
		progressDialog2.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
	}

	public void dismissProgressDialog() {
		if (progressDialog2 != null && progressDialog2.isShowing()) {
			progressDialog2.dismiss();
		}
	}

	public void showProgressDialog(boolean cancelable) {
		if (progressDialog2 == null) {
			progressDialog2 = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
			progressDialog2.setTitle(getString(R.string.prompt));
			progressDialog2.setMessage(getString(R.string.loading));
			progressDialog2.setCancelable(cancelable);
		}
		progressDialog2.show();
	}

	public boolean isShowingProgressDialog() {
		if (progressDialog2 != null) {
			return progressDialog2.isShowing();
		}
		return false;
	}
}
