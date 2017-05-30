package com.forum.lottery.ui;



import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.forum.lottery.R;
import com.forum.lottery.adapter.QuickAdapter;
import com.forum.lottery.network.RxHttp;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.view.CustomActionBar;


public abstract class BaseFragment extends Fragment {

	private Dialog indeterminateDialog; // 单个旋转图片对话框

	private View view;
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.view = view;
		initView();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			initParams(savedInstanceState);
		}else if(getArguments() != null){
			initParams(getArguments());
		}
		initData();
	}

	protected void initParams(Bundle bundle){

	}
	
	@SuppressWarnings("unchecked")
	protected <T extends View> T findView(int id) {
		return (T) view.findViewById(id);
	}
	
	protected abstract void initView();
	protected abstract void initData();

	public void requestMenuCreate(){
		if(getActivity() instanceof BaseActionBarActivity){
			((BaseActionBarActivity)getActivity()).createFragmentMenu(this);
		}
	}

	public void setEmptyView(int resId, AbsListView listView){
		TextView txtEmpty = findView(R.id.txt_empty);
		txtEmpty.setText(resId);
		listView.setEmptyView(txtEmpty);
	}

	public void setEmptyView(int resId, QuickAdapter<?> adapter){
		TextView txtEmpty = findView(R.id.txt_empty);
		txtEmpty.setText(resId);
		adapter.setEmptyView(txtEmpty);
	}
	
	public void toast(String text){
		Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

	public void toast(int strRes){
		Toast.makeText(getActivity().getApplicationContext(), getString(strRes), Toast.LENGTH_SHORT).show();
	}

	public void onMenuCreate(CustomActionBar toolbar){
	}

	/**
	 * 创建网络service
	 * @param serviceName
	 * @param <T>
	 * @return
	 */
	public <T> T createHttp(Class<T> serviceName){
		return ((BaseActivity)getActivity()).createHttp(serviceName);
	}

	/**
	 * 获取网络请求
	 * @return
	 */
	public RxHttp getHttpClient(){
		return ((BaseActivity)getActivity()).getHttpClient();
	}

	/**
	 * 显示单个旋转图片对话框
	 */
	public void showIndeterminateDialog() {
		if (indeterminateDialog == null) {
			indeterminateDialog = new Dialog(getActivity(), R.style.new_circle_progress);
			indeterminateDialog.setCancelable(true);
			indeterminateDialog.setCanceledOnTouchOutside(false);
			View v = LayoutInflater.from(getActivity()).inflate(R.layout.view_indeterminate_progress, null);
			indeterminateDialog.setContentView(v);
		}
		indeterminateDialog.show();
	}

	public void dismissIndeterminateDialog() {
		if (indeterminateDialog != null && indeterminateDialog.isShowing()) {
			indeterminateDialog.dismiss();
		}
	}

	public boolean isShowIndeterminateDialog(){
		return indeterminateDialog.isShowing();
	}

}
