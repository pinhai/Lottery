package com.forum.lottery.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;


import com.forum.lottery.R;
import com.forum.lottery.adapter.QuickAdapter;
import com.forum.lottery.network.RxHttp;
import com.forum.lottery.permission.SubscriberHandler;
import com.forum.lottery.utils.AccountManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rx.Single;
import rx.SingleSubscriber;


public abstract class BaseActivity extends AppCompatActivity {
	private List<OnActivityResultListener> onActivityResultListeners;
	private RxHttp rxHttp;
	private SparseArray<SubscriberHandler> sparseHandlers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getSupportActionBar() != null)
			getSupportActionBar().hide();
		if(savedInstanceState != null){
			initParams(savedInstanceState);
		}else if(getIntent() != null && getIntent().getExtras() != null){
			initParams(getIntent().getExtras());
		}
        AccountManager.getInstance().onRestoreInstanceState(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id) {
		return (T) findViewById(id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onActivityResultListeners = null;
	}
	
	protected void initParams(Bundle bundle){
		
	}
	protected abstract void initView();
	protected abstract void initData();

	/**
	 * 创建网络service
	 * @param serviceName
	 * @param <T>
	 * @return
	 */
	public <T> T createHttp(Class<T> serviceName){
		if(rxHttp == null)
			rxHttp = new RxHttp();
		return rxHttp.create(serviceName);
	}

	/**
	 * 获取网络请求
	 * @return
	 */
	public RxHttp getHttpClient(){
		if(rxHttp == null)
			rxHttp = new RxHttp();
		return rxHttp;
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

	public Single<String[]> requestPermissions(int requestCode, String[] permissions){
		if(sparseHandlers == null)
			sparseHandlers = new SparseArray<>();
		final String[] temPermissions = permissions;
		final int temRequestCode = requestCode;
		return Single.create(new Single.OnSubscribe<String[]>() {
			@Override
			public void call(SingleSubscriber<? super String[]> singleSubscriber) {
				if(canMakeSmores()){
					List<String> requestPermissions = new LinkedList<String>();
					for(String permission : temPermissions){
						if(ContextCompat.checkSelfPermission(BaseActivity.this, permission) != PackageManager.PERMISSION_GRANTED){
							requestPermissions.add(permission);
						}
					}
					if(requestPermissions.size() > 0){
						sparseHandlers.put(temRequestCode, new SubscriberHandler(singleSubscriber));
						ActivityCompat.requestPermissions(BaseActivity.this, requestPermissions.toArray(new String[requestPermissions.size()]), temRequestCode);
					}else{
						singleSubscriber.onSuccess(new String[0]);
					}
				}else{
					singleSubscriber.onSuccess(new String[0]);
				}
			}
		});
	}

	private boolean canMakeSmores() {
		return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(sparseHandlers != null){
			SubscriberHandler subHandler = sparseHandlers.get(requestCode);
			if (subHandler != null){
				subHandler.postResult(permissions, grantResults);
				sparseHandlers.delete(requestCode);
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		AccountManager.getInstance().onSaveInstanceState(outState);
	}


	public void toast(String text){
		Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}


	public BaseActivity self(){
		return this;
	}

	public void addActivityResultListener(OnActivityResultListener onActivityResultListener){
		if(onActivityResultListeners == null){
			onActivityResultListeners = new ArrayList<>();
		}
		onActivityResultListeners.add(onActivityResultListener);
	}
	
	public void removeActivityResultListener(OnActivityResultListener onActivityResultListener){
		if(onActivityResultListeners != null){
			onActivityResultListeners.remove(onActivityResultListener);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(onActivityResultListeners != null){
			for(OnActivityResultListener onActivityResultListener : onActivityResultListeners){
				onActivityResultListener.onActivityResult(requestCode, resultCode, data);
			}
		}
	}
	
	public interface OnActivityResultListener{
		void onActivityResult(int requestCode, int resultCode, Intent data);
	}
}
