package com.longlong.business.homePage.controller;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;



public class MJsonUtils {
	static Context context;

	public static void init(Context context){
		MJsonUtils.context = context;
	}

	@SuppressWarnings("unchecked")
	public static List<MethodOut> getMethods(String json){
//		json = "[{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前一\",\"label\":[{\"gtitle\":\"前一直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X\",\"code_sp\":\",\",\"methodid\":222,\"name\":\"直选复式\",\"methoddesc\":\"从第一名中至少选择1个号码组成一注。\",\"methodhelp\":\"从01-10中至少选择1个号码组成一注，所选号码与开奖号码中第一位相同即中奖。\",\"methodexample\":\"投注方案：01<br>开奖号码：01020304050607080910<br>即可中前一直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前二\",\"label\":[{\"gtitle\":\"前二直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X\",\"code_sp\":\"\",\"methodid\":223,\"name\":\"直选复式\",\"methoddesc\":\"从第一名、第二名中至少选择1个号码组成一注。\",\"methodhelp\":\"从冠军、亚军位中至少各选择一个号码组成一注，开奖号码中第一、第二位与选号按位相同，即为中奖。\",\"methodexample\":\"投注方案：冠军01，亚军02开奖号码：01020304050607080910即可中前二直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"},{\"selectarea\":{\"type\":\"input\",\"singletypetips\":\"0102\"},\"show_str\":\"X\",\"code_sp\":\"\",\"methodid\":224,\"name\":\"直选单式\",\"methoddesc\":\"手动输入号码，至少选择1个二位数号码组成一注。\",\"methodhelp\":\"手动输入两个号码组成一注，所选号码与开奖号码中第一、第二位相同，且顺序一致，即为中奖。\",\"methodexample\":\"投注方案：0102<br>开奖号码：01020304050607080910<br>即可中前二直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选单式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前三\",\"label\":[{\"gtitle\":\"前三直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1},{\"title\":\"季军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":2,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X,X\",\"code_sp\":\"\",\"methodid\":225,\"name\":\"直选复式\",\"methoddesc\":\"从第一名、第二名、第三名中至少选择1个号码组成一注。\",\"methodhelp\":\"从冠军、亚军、季军位中至少各选择一个号码组成一注，开奖号码中第一、第二、第三位与选号按位相同，即为中奖。\",\"methodexample\":\"投注方案：冠军01，亚军02，季军03开奖号码：01020304050607080910即可中前三直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"},{\"selectarea\":{\"type\":\"input\",\"singletypetips\":\"010203\"},\"show_str\":\"X\",\"code_sp\":\"\",\"methodid\":226,\"name\":\"直选单式\",\"methoddesc\":\"手动输入号码，至少选择1个三位数号码组成一注。\",\"methodhelp\":\"手动输入两个号码组成一注，所选号码与开奖号码中第一、第二、第三位相同，且顺序一致，即为中奖。\",\"methodexample\":\"投注方案：010203<br>开奖号码：01020304050607080910<br>即可中前三直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选单式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"定位胆\",\"label\":[{\"gtitle\":\"定位胆\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1},{\"title\":\"季军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":2,\"cols\":1},{\"title\":\"第四名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":3,\"cols\":1},{\"title\":\"第五名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":4,\"cols\":1},{\"title\":\"第六名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":5,\"cols\":1},{\"title\":\"第七名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":6,\"cols\":1},{\"title\":\"第八名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":7,\"cols\":1},{\"title\":\"第九名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":8,\"cols\":1},{\"title\":\"第十名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":9,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X,X,X,X,X,X,X,X,X\",\"code_sp\":\"\",\"methodid\":227,\"name\":\"定位胆\",\"methoddesc\":\"从第一名到第十名任意位置上选择1个或1个以上号码。\",\"methodhelp\":\"从冠军到第十名任意位置上至少选择1个或1个以上号码，每注由1个号码组成，所选号码与相同位置上的开奖号码一致，即为中奖。\",\"methodexample\":\"投注方案：冠军01开奖号码：01020304050607080910即中定位胆。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"定位胆\"}]}]}]";
		List<MethodOut> list = (List<MethodOut>)JsonUtils.fromJson(json , new TypeToken<ArrayList<MethodOut>>() {}.getType());
		return list;
	}
	@SuppressWarnings("unchecked")
	public static List<PlsMethodOut> getPlsMethods(String json){
//		json = "[{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前一\",\"label\":[{\"gtitle\":\"前一直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X\",\"code_sp\":\",\",\"methodid\":222,\"name\":\"直选复式\",\"methoddesc\":\"从第一名中至少选择1个号码组成一注。\",\"methodhelp\":\"从01-10中至少选择1个号码组成一注，所选号码与开奖号码中第一位相同即中奖。\",\"methodexample\":\"投注方案：01<br>开奖号码：01020304050607080910<br>即可中前一直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前二\",\"label\":[{\"gtitle\":\"前二直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X\",\"code_sp\":\"\",\"methodid\":223,\"name\":\"直选复式\",\"methoddesc\":\"从第一名、第二名中至少选择1个号码组成一注。\",\"methodhelp\":\"从冠军、亚军位中至少各选择一个号码组成一注，开奖号码中第一、第二位与选号按位相同，即为中奖。\",\"methodexample\":\"投注方案：冠军01，亚军02开奖号码：01020304050607080910即可中前二直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"},{\"selectarea\":{\"type\":\"input\",\"singletypetips\":\"0102\"},\"show_str\":\"X\",\"code_sp\":\"\",\"methodid\":224,\"name\":\"直选单式\",\"methoddesc\":\"手动输入号码，至少选择1个二位数号码组成一注。\",\"methodhelp\":\"手动输入两个号码组成一注，所选号码与开奖号码中第一、第二位相同，且顺序一致，即为中奖。\",\"methodexample\":\"投注方案：0102<br>开奖号码：01020304050607080910<br>即可中前二直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选单式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前三\",\"label\":[{\"gtitle\":\"前三直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1},{\"title\":\"季军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":2,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X,X\",\"code_sp\":\"\",\"methodid\":225,\"name\":\"直选复式\",\"methoddesc\":\"从第一名、第二名、第三名中至少选择1个号码组成一注。\",\"methodhelp\":\"从冠军、亚军、季军位中至少各选择一个号码组成一注，开奖号码中第一、第二、第三位与选号按位相同，即为中奖。\",\"methodexample\":\"投注方案：冠军01，亚军02，季军03开奖号码：01020304050607080910即可中前三直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"},{\"selectarea\":{\"type\":\"input\",\"singletypetips\":\"010203\"},\"show_str\":\"X\",\"code_sp\":\"\",\"methodid\":226,\"name\":\"直选单式\",\"methoddesc\":\"手动输入号码，至少选择1个三位数号码组成一注。\",\"methodhelp\":\"手动输入两个号码组成一注，所选号码与开奖号码中第一、第二、第三位相同，且顺序一致，即为中奖。\",\"methodexample\":\"投注方案：010203<br>开奖号码：01020304050607080910<br>即可中前三直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选单式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"定位胆\",\"label\":[{\"gtitle\":\"定位胆\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1},{\"title\":\"季军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":2,\"cols\":1},{\"title\":\"第四名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":3,\"cols\":1},{\"title\":\"第五名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":4,\"cols\":1},{\"title\":\"第六名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":5,\"cols\":1},{\"title\":\"第七名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":6,\"cols\":1},{\"title\":\"第八名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":7,\"cols\":1},{\"title\":\"第九名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":8,\"cols\":1},{\"title\":\"第十名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":9,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X,X,X,X,X,X,X,X,X\",\"code_sp\":\"\",\"methodid\":227,\"name\":\"定位胆\",\"methoddesc\":\"从第一名到第十名任意位置上选择1个或1个以上号码。\",\"methodhelp\":\"从冠军到第十名任意位置上至少选择1个或1个以上号码，每注由1个号码组成，所选号码与相同位置上的开奖号码一致，即为中奖。\",\"methodexample\":\"投注方案：冠军01开奖号码：01020304050607080910即中定位胆。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"定位胆\"}]}]}]";
		List<PlsMethodOut> list = (List<PlsMethodOut>)JsonUtils.fromJson(json , new TypeToken<ArrayList<PlsMethodOut>>() {}.getType());
		return list;
	}
	@SuppressWarnings("unchecked")
	public static List<ElvMethodOut> getElvMethods(String json){
//		json = "[{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前一\",\"label\":[{\"gtitle\":\"前一直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X\",\"code_sp\":\",\",\"methodid\":222,\"name\":\"直选复式\",\"methoddesc\":\"从第一名中至少选择1个号码组成一注。\",\"methodhelp\":\"从01-10中至少选择1个号码组成一注，所选号码与开奖号码中第一位相同即中奖。\",\"methodexample\":\"投注方案：01<br>开奖号码：01020304050607080910<br>即可中前一直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前二\",\"label\":[{\"gtitle\":\"前二直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X\",\"code_sp\":\"\",\"methodid\":223,\"name\":\"直选复式\",\"methoddesc\":\"从第一名、第二名中至少选择1个号码组成一注。\",\"methodhelp\":\"从冠军、亚军位中至少各选择一个号码组成一注，开奖号码中第一、第二位与选号按位相同，即为中奖。\",\"methodexample\":\"投注方案：冠军01，亚军02开奖号码：01020304050607080910即可中前二直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"},{\"selectarea\":{\"type\":\"input\",\"singletypetips\":\"0102\"},\"show_str\":\"X\",\"code_sp\":\"\",\"methodid\":224,\"name\":\"直选单式\",\"methoddesc\":\"手动输入号码，至少选择1个二位数号码组成一注。\",\"methodhelp\":\"手动输入两个号码组成一注，所选号码与开奖号码中第一、第二位相同，且顺序一致，即为中奖。\",\"methodexample\":\"投注方案：0102<br>开奖号码：01020304050607080910<br>即可中前二直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选单式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"前三\",\"label\":[{\"gtitle\":\"前三直选\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1},{\"title\":\"季军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":2,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X,X\",\"code_sp\":\"\",\"methodid\":225,\"name\":\"直选复式\",\"methoddesc\":\"从第一名、第二名、第三名中至少选择1个号码组成一注。\",\"methodhelp\":\"从冠军、亚军、季军位中至少各选择一个号码组成一注，开奖号码中第一、第二、第三位与选号按位相同，即为中奖。\",\"methodexample\":\"投注方案：冠军01，亚军02，季军03开奖号码：01020304050607080910即可中前三直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选复式\"},{\"selectarea\":{\"type\":\"input\",\"singletypetips\":\"010203\"},\"show_str\":\"X\",\"code_sp\":\"\",\"methodid\":226,\"name\":\"直选单式\",\"methoddesc\":\"手动输入号码，至少选择1个三位数号码组成一注。\",\"methodhelp\":\"手动输入两个号码组成一注，所选号码与开奖号码中第一、第二、第三位相同，且顺序一致，即为中奖。\",\"methodexample\":\"投注方案：010203<br>开奖号码：01020304050607080910<br>即可中前三直选。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"直选单式\"}]}]},{\"isrx\":\"0\",\"isdefault\":\"0\",\"title\":\"定位胆\",\"label\":[{\"gtitle\":\"定位胆\",\"label\":[{\"selectarea\":{\"type\":\"digital\",\"layout\":[{\"title\":\"冠军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":0,\"cols\":1},{\"title\":\"亚军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":1,\"cols\":1},{\"title\":\"季军\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":2,\"cols\":1},{\"title\":\"第四名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":3,\"cols\":1},{\"title\":\"第五名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":4,\"cols\":1},{\"title\":\"第六名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":5,\"cols\":1},{\"title\":\"第七名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":6,\"cols\":1},{\"title\":\"第八名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":7,\"cols\":1},{\"title\":\"第九名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":8,\"cols\":1},{\"title\":\"第十名\",\"no\":\"01|02|03|04|05|06|07|08|09|10\",\"place\":9,\"cols\":1}],\"noBigIndex\":5,\"isButton\":true},\"show_str\":\"X,X,X,X,X,X,X,X,X,X\",\"code_sp\":\"\",\"methodid\":227,\"name\":\"定位胆\",\"methoddesc\":\"从第一名到第十名任意位置上选择1个或1个以上号码。\",\"methodhelp\":\"从冠军到第十名任意位置上至少选择1个或1个以上号码，每注由1个号码组成，所选号码与相同位置上的开奖号码一致，即为中奖。\",\"methodexample\":\"投注方案：冠军01开奖号码：01020304050607080910即中定位胆。\",\"prize\":{\"1\":\"1700.00\"},\"dyprize\":[],\"modes\":[{\"modeid\":1,\"name\":\"元\",\"rate\":1},{\"modeid\":2,\"name\":\"角\",\"rate\":0.1},{\"modeid\":3,\"name\":\"分\",\"rate\":0.01}],\"maxcodecount\":0,\"isrx\":0,\"numcount\":0,\"defaultposition\":\"00000\",\"desc\":\"定位胆\"}]}]}]";
		List<ElvMethodOut> list = (List<ElvMethodOut>)JsonUtils.fromJson(json , new TypeToken<ArrayList<ElvMethodOut>>() {}.getType());
		return list;
	}
	
	public static void main(String[] args) {
		InnerLabel label = queryMethod(222, 9);
	}
	
	public static PlsInnerLabel queryPlsMethod(int methodid , int lotteryId){
		PlsInnerLabel resultLabel = new PlsInnerLabel();
//		String filePath = geJsonPath(lotteryId);
//		String json = ReadFile(filePath);

		String json = getJsonFile(String.valueOf(lotteryId));
		List<PlsMethodOut> list = getPlsMethods(json);
		for(PlsMethodOut out : list){
			List<PlsMethodLabel> labels = out.getLabel();
			for(PlsMethodLabel label : labels){
				List<PlsInnerLabel> inlabels = label.getLabel();
				for(PlsInnerLabel ilabel : inlabels){
					if(ilabel.getMethodid().containsValue(methodid)){
						resultLabel = ilabel;
						break;
					}
				}
			}
		}

		return resultLabel;
	}

	private static String getJsonFile(String lotteryId){
		String fileName = "";

		if(lotteryId.equals("51") || lotteryId.equals("7") || lotteryId.equals("4") || lotteryId.equals("73")){
			fileName = "face.5.json";
		}else if(lotteryId.equals("2")){
			fileName = "face.12.json";
		}else if(lotteryId.equals("1")){
			fileName = "face.11.json";
		}else if(lotteryId.equals("3")){
			fileName = "face.4.json";
		}else if(lotteryId.equals("12") ||lotteryId.equals("13") ||lotteryId.equals("14") ||lotteryId.equals("15")){
			fileName = "face.8.json";
		}else if(lotteryId.equals("11")){
			fileName = "face.7_1.json";
		}else if(lotteryId.equals("9") || lotteryId.equals("52")){
			fileName = "face.3.json";
		}else if(lotteryId.equals("41") || lotteryId.equals("42")){
			fileName = "face.41.json";
		}else if(lotteryId.equals("18")){
			fileName = "lhc.json";
		}

		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) );
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line;
			while((line = bufReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	public static InnerLabel queryMethod(int methodid , int lotteryId){
		InnerLabel resultLabel = new InnerLabel();
//		String filePath = geJsonPath(lotteryId);
//		String json = ReadFile(filePath);

		String json = getJsonFile(String.valueOf(lotteryId));
		if(lotteryId == 12 ||lotteryId == 13 || lotteryId == 14 || lotteryId == 15){
			List<ElvMethodOut> list = getElvMethods(json);
			for(ElvMethodOut out : list){
				List<InnerLabel> inlabels = out.getLabel();
				for(InnerLabel ilabel : inlabels){
					if(ilabel.getMethodid() == methodid){
						resultLabel = ilabel;
						break;
					}
				}
			}
		}else{
			List<MethodOut> list = getMethods(json);
			for(MethodOut out : list){
				List<MethodLabel> labels = out.getLabel();
				for(MethodLabel label : labels){
					List<InnerLabel> inlabels = label.getLabel();
					for(InnerLabel ilabel : inlabels){
						if(ilabel.getMethodid() == methodid){
							resultLabel = ilabel;
							break;
						}
					}
				}
			}
		}

		return resultLabel;
	}
	
	public static String ReadFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			InputStream inputStream =MJsonUtils.class.getResourceAsStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return laststr;
	}
	
	public static String geJsonPath(int lotteryId) {
		String json = "/json/";
		switch(lotteryId){
		case 1 :
			json += "face.11.json";
			break;
		case 2 :
			json += "face.12.json";
			break;
		case 9 :
		case 52 :
			json += "face.3.json";
			break;
		case 3 :
			json += "face.4.json";
			break;
		case 4 :
		case 7 :
		case 51 :
		case 73 :
			json += "face.5.json";
			break;
		case 11 :
			json += "face.7.json";
			break;
		case 12 :
		case 13 :
		case 14 :
		case 15 :
			json += "face.8.json";
			break;
		}
		return json;
	}
	
	public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {  
        ArrayList<T> mList = new ArrayList<T>();  
        JsonArray array = new JsonParser().parse(json).getAsJsonArray(); 
        Gson mGson = new Gson();
        for(final JsonElement elem : array){  
            mList.add(mGson.fromJson(elem, cls));  
        }  
        return mList;  
    } 
}
