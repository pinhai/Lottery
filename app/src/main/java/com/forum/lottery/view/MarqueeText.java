package com.forum.lottery.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/17.
 * 实现跑马灯效果的TextView
 */
public class MarqueeText extends android.support.v7.widget.AppCompatTextView {
//    public MarqueeText(Context context) {
//        super(context);
//    }
//    public MarqueeText(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//    public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    public MarqueeText(Context context) {

//一个参数的构造函数是要调用两个参数的构造函数的,看源码就知道,所以这里要改成this
//		super(context);
        this(context,null);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSingleLine();
        setFocusable(true);
        setFocusableInTouchMode(true);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
    }

    //返回textview是否处在选中的状态
    //而只有选中的textview才能够实现跑马灯效果
    @Override
    public boolean isFocused() {
        return true;
    }

    /*
	当我的焦点发生改变时,调用此方法
	boolean focused 代表当前的焦点状态
	如果有焦点(focused),走父类的回调,如果失去焦点,就不走父类,自己处理
	 */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if(focused)
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    /*
    窗体焦点发生改变,回调此方法
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus)
            super.onWindowFocusChanged(hasFocus);
    }

}
