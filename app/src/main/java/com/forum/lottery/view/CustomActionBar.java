package com.forum.lottery.view;



import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.forum.lottery.R;


public class CustomActionBar {
	private TextView txtTitle;
	private View actionBarView;
	private DeleteOperate deleteOperate;
	private View deleteToolbar;
	private ImageView imgBack;
	public CustomActionBar(View view){
		actionBarView = view;
		txtTitle = (TextView) view.findViewById(R.id.txt_title);
        imgBack = (ImageView) view.findViewById(R.id.img_back);
	}
	
	public View getActionBarView() {
		return actionBarView;
	}
	
	public void setBackEnable(boolean isEnable){
        if(imgBack != null)
            imgBack.setVisibility(isEnable ? View.VISIBLE : View.GONE);
	}
	
	public void show(){
		actionBarView.setVisibility(View.VISIBLE);
	}
	
	public void hide(){
		actionBarView.setVisibility(View.GONE);
	}
	
	public void setTitle(CharSequence title){
		txtTitle.setText(title);
	}
	
	public void setTitle(int resId){
		txtTitle.setText(resId);
	}

	public void setImageTitle(int resId){
	}

	public void setImageTitle(Drawable drawable){
	}

	public void setDeleteToolbar(View deleteToolbar) {
		this.deleteToolbar = deleteToolbar;
	}

	public DeleteOperate buildDeleteOperate() {
		if(actionBarView.findViewById(R.id.img_delete) == null){
			throw new IllegalAccessError("ActionBar不是删除模式");
		}
		if(deleteToolbar == null){
			throw new IllegalAccessError("没有设置deleteToolbar");
		}
		deleteOperate = new DeleteOperate(actionBarView, deleteToolbar);
		return deleteOperate;
	}

	public void setAffirmClickListener(OnClickListener clickListener){
		actionBarView.findViewById(R.id.txt_affirm).setOnClickListener(clickListener);
	}

	public void setOnClickListener(OnClickListener onClickListener){
        if(imgBack != null)
            imgBack.setOnClickListener(onClickListener);

	}
}
