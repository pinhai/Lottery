package com.forum.lottery.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ToolUtils {
    private static final int[] MONTH_DAYS = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final int LOGIN_CODE = 1100;
    private ToolUtils() {

    }

    public static String getSignature(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);
            return packageInfo.signatures[0].toCharsString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        if(TextUtils.isEmpty(phoneNumber))
            return false;
        if(phoneNumber.length() == 11){
            Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(phoneNumber);
            if(matcher.matches()){
                return true;
            }
        }
        Pattern pattern = Pattern.compile("^0\\d{2,3}-?\\d{7,8}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isEmailAddress(String emailAddress){
        Pattern pattern = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        return pattern.matcher(emailAddress).matches();
    }

    public static void hide(Activity activity) {
        if (activity.getCurrentFocus() == null)
            return;
        ((InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void show(Activity activity) {
        if (activity.getCurrentFocus() == null)
            return;
        ((InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(activity.getCurrentFocus(), 0);
    }

    public static long getFileSize(File file) {
        if (!file.exists()) {
            return 0;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return 0;
            }
            long size = 0;
            for (File temFile : files) {
                size += getFileSize(temFile);
            }
            return size;
        } else {
            return file.length();
        }
    }

    /**
     * 调用拨号界面
     *
     * @param context
     * @param phoneNumber
     */
    public static void takePhone(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void takePhoneSvcManager(Context context){
        takePhone(context, "4008055225");
    }

    /**
     * 调用发短信界面
     *
     * @param context
     * @param phoneNumber
     */
    public static void sendSMS(Context context, String phoneNumber) {

        Uri smsToUri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }


    public static void startMarket(Context context){
        Uri uri = Uri.parse("market://details?id=com.mm.babysitter");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static  <T> List<T> asList(T[] array){
        if(array == null){
            return null;
        }
        List<T> ls = new ArrayList<>(array.length);
        for(int i = 0; i < array.length; i++){
            ls.add(array[i]);
        }
        return ls;
    }

    /**
     * 实现文本复制功能
     * @param content
     */
    public static void copy(String content, Context context)
    {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, content));
    }
    /**
     * 实现粘贴功能
     * @param context
     * @return
     */
    public static String paste(Context context)
    {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = cmb.getPrimaryClip();
        if(clipData.getItemCount() == 0){
            return null;
        }else{
            return clipData.getItemAt(clipData.getItemCount() - 1).getText().toString();
        }
    }

    /**
     *
     * @param fromDate
     * @return int[0]是年差int[1]是月差int[2]是日差
     */
    public static int[] getDateDiffFromCurrentTime(String fromDate){
        String babyBirth = fromDate;
        String[] dates = babyBirth.split("-");
        int birthdayYear = Integer.parseInt(dates[0]);
        int birthdayMoth = Integer.parseInt(dates[1]) - 1;
        int birthdayDay = Integer.parseInt(dates[2]);

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int curYear = calendar.get(Calendar.YEAR);
        int curMoth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);

        if(birthdayMoth == 1 && birthdayDay == 29 && curMoth == 1 && curDay == 28 && !leapYear(curYear))
            return new int[]{ curYear - birthdayYear, 0, 0};

        int difDay = 0;
        int difMoth = 0;
        int difYear = 0;

        if(curDay >= birthdayDay){
            difDay = curDay - birthdayDay;
        }else {
            if(birthdayDay == 31 && curDay == MONTH_DAYS[curMoth] + getAddDays(curYear, curMoth)){
                difDay = 0;
            }else{
                if(curMoth == 0){
                    curYear--;
                    curMoth += 12;
                }
                curMoth --;
                difDay = curDay + MONTH_DAYS[curMoth] + getAddDays(curYear, curMoth) - birthdayDay;
            }
        }
        if(curMoth >= birthdayMoth){
            difMoth = curMoth - birthdayMoth;
        }else {
            curYear--;
            curMoth += 12;
            difMoth = curMoth - birthdayMoth;
        }
        difYear = curYear - birthdayYear;
        return new int[]{difYear, difMoth, difDay};
    }
    private static int getAddDays(int year, int month){
        if(month == 1 && leapYear(year))
            return 1;
        return 0;
    }
    public static boolean leapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static RequestBody createImageRequestBody(String imagePath) throws IllegalAccessException {
        File imageFile = new File(imagePath);
        if(imageFile.exists()){
            return RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        }else{
            throw new IllegalAccessException("图片不存在");
        }
    }

    public static boolean deleteDir(File dir){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean isUpLollipop(){
        return Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static String getContentType(String fileName){
        if(fileName.endsWith(".png")){
            return "image/png";
        }else if(fileName.endsWith(".jpg")){
            return "image/jpeg";
        }else {
            return "application/octet-stream";
        }
    }
}
