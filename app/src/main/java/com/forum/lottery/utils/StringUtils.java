package com.forum.lottery.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin_h on 2017/6/10.
 */

public class StringUtils {

    public static boolean isChinese(String str){
        str = str.substring(0, 1);
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);

        return m.matches();
    }




//    Pattern p = Pattern.compile("[0-9]*");
//    Matcher m = p.matcher(txt);
//     if(m.matches() ){
//        Toast.makeText(Main.this,"输入的是数字", Toast.LENGTH_SHORT).show();
//    }
//    p=Pattern.compile("[a-zA-Z]");
//    m=p.matcher(txt);
//     if(m.matches()){
//        Toast.makeText(Main.this,"输入的是字母", Toast.LENGTH_SHORT).show();
//    }
//    p=Pattern.compile("[\u4e00-\u9fa5]");
//    m=p.matcher(txt);
//     if(m.matches()){
//        Toast.makeText(Main.this,"输入的是汉字", Toast.LENGTH_SHORT).show();
//    }
}
