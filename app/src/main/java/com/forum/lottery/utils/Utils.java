package com.forum.lottery.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public final class Utils {

	/**
	 * 私有构造方法，防止创建实例
	 */
	private Utils() {
	}

	private static DisplayMetrics dm = null;

	public static int barHeight = 0;

	public static void initDisplayMetrics(Activity activity) {
		dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	}

	public static int pxToDip(int size) {
		return (int) (size / dm.density + 0.5f);
	}

	public static int dipToPx(int size) {
		return (int) (size * dm.density + 0.5f);
	}

	public static DisplayMetrics getDisplayMetrics() {
		return dm;
	}

}
