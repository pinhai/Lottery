package com.longlong.business.homePage.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class PlayedUtil {
	
	static String json3 = "{222:'BJZ1',223:'BJZ2',224:'BJZ2',225:'BJZ3',226:'BJZ3',227:'DWD'}";
	static String json4 = "{233:'ZX3',234:'ZXDS3',235:'ZXHZ',236:'ZUS',237:'ZUL',159:'HHZX',238:'ZUSHZ',239:'ZULHZ',229:'ZX2',231:'ZU2',230:'ZX2',232:'ZU2',228:'DWD',171:'DWD',172:'DWD',242:'BDW1',240:'DXDS',241:'DXDS',243:'BDW2'}";
	static String json5 = "{107:'ZX5',108:'ZX5',105:'ZX4',106:'ZX4',88:'ZX3',89:'ZX3',92:'ZH3',90:'ZXHZ',91:'ZXKD',93:'ZUS',1148:'ZUS',94:'ZUL',1149:'ZUL',95:'HHZX',97:'ZUHZ',99:'ZU3BD',101:'HZWS',102:'TSH3',54:'ZX3',55:'ZX3',58:'ZH3',56:'ZXHZ',57:'ZXKD',59:'ZUS',1146:'ZUS',60:'ZUL',1147:'ZUL',61:'HHZX',63:'ZUHZ',65:'ZU3BD',67:'HZWS',68:'TSH3',42:'ZX2',43:'ZX2',44:'ZXHZ2',45:'ZXKD2',50:'ZU2',51:'ZU2',52:'ZUHZ2',53:'ZU2BD',38:'ZX2',39:'ZX2',40:'ZXHZ2',41:'ZXKD2',46:'ZU2',47:'ZU2',48:'ZUHZ2',49:'ZU2BD',37:'DWD',115:'BDW1',113:'BDW1',116:'BDW2',114:'BDW2',118:'BDW2',117:'BDW1',244:'BDW1',245:'BDW2',119:'BDW1',120:'BDW2',121:'BDW3',111:'DXDS',109:'DXDS',112:'DXDS',110:'DXDS',122:'RZX2',123:'RZX2',124:'ZXHZ2',125:'ZU2',126:'ZU2',127:'ZUHZ2',128:'RZX3',129:'RZX3',130:'ZXHZ',131:'ZUS',132:'ZUS',133:'ZUL',134:'ZUL',135:'HHZX',137:'ZUHZ',139:'RZX4',140:'RZX4',141:'SXZU24',142:'SXZU12',143:'SXZU6',144:'SXZU4'}";
	static String json7 = "{145:'HEZHI',146:'HEZHI',147:'HEZHI',148:'HEZHI',149:'HEZHI',150:'HEZHI',151:'HEZHI',152:'HEZHI',153:'HEZHI',154:'HEZHI',155:'HEZHI',156:'HEZHI',157:'HEZHI',158:'HEZHI',159:'HEZHI',160:'HEZHI',161:'STTX',162:'STDX',163:'SBBZ',164:'SBDT',165:'SLTX',166:'ETFX',167:'ETDX',168:'EBBZ',169:'EBDT'}";
	static String json8 = "{170:'LTZX3',171:'LTDSZX3',176:'LTZU3',177:'LTDSZU3',182:'LTDTZU3',185:'LTZX2',186:'LTZXDS2',189:'LTZU2',190:'LTZUDS2',193:'LTDTZU2',195:'LTBDW',454:'LTDWD',198:'LTDWD',453:'LTDWD',456:'LTDDS',458:'LTCZW',199:'LTRX1',207:'LTDSRX1',200:'LTRX2',208:'LTDSRX2',215:'LTRXDT2',201:'LTRX3',209:'LTDSRX3',216:'LTRXDT3',202:'LTRX4',210:'LTDSRX4',217:'LTRXDT4',203:'LTRX5',211:'LTDSRX5',218:'LTRXDT5',204:'LTRX6',212:'LTDSRX6',219:'LTRXDT6',205:'LTRX7',213:'LTDSRX7',220:'LTRXDT7',480:'LTRX8',221:'LTRXDT8',172:'LTZHIZ3',173:'LTZDSZ3',178:'LTZUZ3',179:'LTUDSZ3',183:'LTDTZ3',174:'LTZHIH3',175:'LTZDSH3',180:'LTZUH3',181:'LTUDSH3',184:'LTDTH3',187:'LTZHIH2',188:'LTZHDSH2',191:'LTZUH2',192:'LTZUDSH2','194':'LTDTH2',196:'LTBDWZ',197:'LTBDWH',206:'LTRX8',214:'LTDSRX8'}";
	static String json11 = "{15:'ZX3',17:'ZX3',19:'ZXHZ',21:'ZUS',23:'ZUL',25:'ZUSHZ',28:'ZULHZ',11:'ZU2',13:'ZU2',1:'DWD',33:'BDW1',35:'BDW2',29:'DXDS',31:'DXDS'};";
	static String json12 = "{16:'ZX3',18:'ZX3',20:'ZXHZ',22:'ZUS',24:'ZUL',27:'ZUSHZ',26:'ZULHZ',12:'ZU2',14:'ZU2',2:'DWD',34:'BDW1',36:'BDW2',30:'DXDS',32:'DXDS',1005:'ZX5'};";
	
	static String [][]data_sel =null;
	
	static Map<Integer, String> methods = new HashMap<Integer, String>();
	
	// 号码检测,l:号码长度,e是否返回非法号码，true是,false返回合法注数,fun对每个号码的附加检测,sort:是否对每个号码排序
	private static int _inputCheck_Num(int l, boolean e, Object fun, boolean sort) { // file input from here
		int nums = data_sel[0].length;
		String[] error = new String[] {};
		String[] newsel = new String[] {};
		String partn = "";
		// l = Integer.parseInt(l);
		switch (l) {
		case 2:
			partn = "/^[0-9]{2}$/";
			break;
		case 5:
			partn = "/^[0-9\\s]{5}$/";
			break;
		case 8:
			partn = "/^[0-9\\s]{8}$/";
			break;
		case 11:
			partn = "/^[0-9\\s]{11}$/";
			break;
		case 14:
			partn = "/^[0-9\\s]{14}$/";
			break;
		case 17:
			partn = "/^[0-9\\s]{17}$/";
			break;
		case 20:
			partn = "/^[0-9\\s]{20}$/";
			break;
		case 23:
			partn = "/^[0-9\\s]{23}$/";
			break;
		default:
			partn = "/^[0-9]{3}$/";
			break;
		}
		// fun = $.isFunction(fun) ? fun : function (s) {
		// return true;
		// };
		for (int i = 0; i < data_sel[0].length; i++) {
			String n = data_sel[0][i].trim();
			boolean isMatch = Pattern.matches(partn, n);

			if (isMatch) {
				if (n.indexOf(" ") == -1) {
					String[] nn = n.split("");
					if (sort)
						Arrays.sort(nn);
					n = StringUtils.join(nn, "");
				} else {
					String[] nn = n.split(" ");
					if (sort)
						Arrays.sort(nn);
					n = StringUtils.join(nn, " ");
				}
			} else {// 不合格则注数减
				nums = nums - 1;
			}
		}
		if (e == true) {
			data_sel[0] = newsel;
		}
		return nums;
	};
	
	
	public static boolean _SDinputCheck() {
		String n=JsonUtils.toJson(data_sel[0]);
		int len=data_sel[0].length;
        String []t = n.split(" ");
        int l = t.length;
        for (int i = 0; i < l; i++) {
            if (Integer.parseInt((t[i])) > 11 || Integer.parseInt((t[i])) < 1) { //超过指定范围
                return false;
            }
            if (t[i].length() != 2) { //超过指定范围
                return false;
            }
            for (int j = i + 1; j < l; j++) {
                if (Integer.parseInt((t[i])) == Integer.parseInt((t[j]))) { //重复的号码
                    return false;
                }
            }
        }
        return true;
    }
	
	@SuppressWarnings("unchecked")
	public static int checkNum(String [][]data_selArray , int methodid, int lotteryId) { //时时计算投注注数与金额等
		
		data_sel=data_selArray;
        int nums = 0;
        
        String json = getMethodJsonStr(lotteryId);
		methods = (Map<Integer, String>) JsonUtils.fromJson(json,
				methods.getClass());
		String mname = methods.get(methodid+"");// 玩法的简写,如:"ZX3"
		// String mname = $.lt_method[$.lt_method_data.methodid];//玩法的简写,如:"ZX3"
		int modes = 1;
		int max_place = 0; // 最大place
		// 多少组码
		int plength = data_sel.length;

		InnerLabel label = MJsonUtils.queryMethod(methodid, lotteryId);

		String otype = label.getSelectarea().getType();

		Integer[] minchosen = new Integer[] {};// 每一位上最少选择的号码个数
		List<Integer> list_chosen = new ArrayList<Integer>();
		List<Layout> layouts = label.getSelectarea().getLayout();
		for (Layout lay : layouts) {
			max_place = max_place > lay.getPlace() ? max_place : lay.getPlace();
			list_chosen.add(lay.getPlace(),lay.getMinchosen());
//			minchosen[lay.getPlace()] = lay.getMinchosen();
		}
		minchosen = list_chosen.toArray(minchosen);
        
        //01:验证号码合法性并计算注数
        if (otype == "input") { //输入框形式的检测
            if (data_sel[0].length > 0) { //如果输入的有值
                switch (mname) {
                   
                    case "LTDSZX3":
                        //alert("yes");
                        nums = _inputCheck_Num(8, false, _SDinputCheck(), false);
                        break;
                    case "LTZDSH3":
                        //alert("yes");
                        nums = _inputCheck_Num(8, false, _SDinputCheck(), false);
                        //alert(nums);
                        break;
                    case "LTZDSZ3":
                        //alert("yes");
                        //alert(JSON.stringify(data_sel));
                        nums = _inputCheck_Num(8, false, _SDinputCheck(), false);
                        //alert(nums);
                        break;
                    case "LTDSZU3":
                        nums = _inputCheck_Num(8, false, _SDinputCheck(), true);
                        break;
                    case "LTUDSZ3":
                        nums = _inputCheck_Num(8, false, _SDinputCheck(), true);
                        break;
                    case "LTUDSH3":
                        nums = _inputCheck_Num(8, false, _SDinputCheck(), true);
                        break;
                    case "LTZXDS2":
                        nums = _inputCheck_Num(5, false, _SDinputCheck(), false);
                        break;
                    case "LTZHDSH2":
                        nums = _inputCheck_Num(5, false, _SDinputCheck(), false);
                        break;
                    case "LTZUDS2":
                        nums = _inputCheck_Num(5, false, _SDinputCheck(), true);
                        break;
                    case "LTZUDSH2":
                        nums = _inputCheck_Num(5, false, _SDinputCheck(), true);
                        break;
                    case "LTDSRX1":
                        nums = _inputCheck_Num(2, false, _SDinputCheck(), false);
                        break;
                    case "LTDSRX2":
                        nums = _inputCheck_Num(5, false, _SDinputCheck(), true);
                        break;
                    case "LTDSRX3":
                        nums = _inputCheck_Num(8, false, _SDinputCheck(), true);
                        break;
                    case "LTDSRX4":
                        nums = _inputCheck_Num(11, false, _SDinputCheck(), true);
                        break;
                    case "LTDSRX5":
                        nums = _inputCheck_Num(14, false, _SDinputCheck(), true);
                        break;
                    case "LTDSRX6":
                        nums = _inputCheck_Num(17, false, _SDinputCheck(), true);
                        break;
                    case "LTDSRX7":
                        nums = _inputCheck_Num(20, false, _SDinputCheck(), true);
                        break;
                    case "LTDSRX8":
                        nums = _inputCheck_Num(23, false, _SDinputCheck(), true);
                        break;
                    default:
                        break;
                }
            }
        } else { //其他选择号码形式[暂时就数字型和大小单双]
            Object tmp_nums = 1;
            switch (mname) { //根据玩法分类不同做不同处理
                case "LTZX3": //乐透11运前三直选
                    nums = 0;
                    if (data_sel[0].length > 0 && data_sel[1].length > 0 && data_sel[2].length > 0) {
                        for (int i = 0; i < data_sel[0].length; i++) {
                            for (int j = 0; j < data_sel[1].length; j++) {
                                for (int k = 0; k < data_sel[2].length; k++) {
                                    if (data_sel[0][i] != data_sel[1][j] && data_sel[0][i] != data_sel[2][k] && data_sel[1][j] != data_sel[2][k]) {
                                        nums++;
                                    }
                                }
                            }
                        }
                    }
                    // alert("前三直选");
                    break;
                case "LTZHIH3": //乐透11运hou三直选
                    nums = 0;
                    if (data_sel[0].length > 0 && data_sel[1].length > 0 && data_sel[2].length > 0) {
                        for (int i = 0; i < data_sel[0].length; i++) {
                            for (int j = 0; j < data_sel[1].length; j++) {
                                for (int k = 0; k < data_sel[2].length; k++) {
                                    if (data_sel[0][i] != data_sel[1][j] && data_sel[0][i] != data_sel[2][k] && data_sel[1][j] != data_sel[2][k]) {
                                        nums++;
                                    }
                                }
                            }
                        }
                    }
                    // alert("前三直选");
                    break;
                case "LTZHIZ3": //乐透11运中三直选
                    nums = 0;
                    if (data_sel[0].length > 0 && data_sel[1].length > 0 && data_sel[2].length > 0) {
                        for (int i = 0; i < data_sel[0].length; i++) {
                            for (int j = 0; j < data_sel[1].length; j++) {
                                for (int k = 0; k < data_sel[2].length; k++) {
                                    if (data_sel[0][i] != data_sel[1][j] && data_sel[0][i] != data_sel[2][k] && data_sel[1][j] != data_sel[2][k]) {
                                        nums++;
                                    }
                                }
                            }
                        }
                    }
                    //alert("中三直选");
                    //alert(JSON.stringify(data_sel));
                    break;
                case "LTZX2": //乐透十一运前二直选
                    nums = 0;
                    if (data_sel[0].length > 0 && data_sel[1].length > 0) {
                        int h = StringArray.intersect(data_sel[0], data_sel[1]).length;
                        nums = data_sel[0].length * data_sel[1].length - h;
                    }
                    break;
                case "LTZHIH2": //乐透十一运后二直选
                    nums = 0;
                    if (data_sel[0].length > 0 && data_sel[1].length > 0) {
                    	int h = StringArray.intersect(data_sel[0], data_sel[1]).length;
                        nums = data_sel[0].length * data_sel[1].length - h;
                    }
                    break;
                case "LTDWD": //乐透十一运定位胆
                case "LTDDS": //乐透十一运定单双
                    for (int i = 0; i <= max_place; i++) {
                        nums = Integer.parseInt(MethodCommon._common.util.Math_add(nums, data_sel[i].length).toString());
                    }
                    break;
                case "LTZU3": //乐透十一运前三组选
                case "LTZUZ3": //乐透十一运中三组选
                case "LTZUH3": //乐透十一运后三组选
                case "LTZU2": //乐透十一运前二组选
                case "LTZUH2": //乐透十一运后二组选
                case "LTBDW": //乐透十一运不定位
                case "LTBDWZ": //乐透十一运不定位中三
                case "LTBDWH": //乐透十一运不定位后三
                case "LTCZW": //乐透十一运猜中位
                case "LTRX1": //乐透十一运任选一
                case "LTRX2": //乐透十一运任选二
                case "LTRX3": //乐透十一运任选三
                case "LTRX4": //乐透十一运任选四
                case "LTRX5": //乐透十一运任选五
                case "LTRX6": //乐透十一运任选六
                case "LTRX7": //乐透十一运任选七
                case "LTRX8": //乐透十一运任选八
                    if (data_sel[0].length >= minchosen[0]) { //C(n,m)
                        nums += Combination(data_sel[0].length, minchosen[0]);
                    }
                    break;
                case "LTDTZU3": //前三组选胆拖
                case "LTDTH3": //后三组选胆拖
                case "LTDTZ3": //中三组选胆拖
                case "LTDTZU2": //前二组选胆拖
                case "LTDTH2": //前二组选胆拖
                case "LTRXDT2": //任选二胆拖
                case "LTRXDT3": //任选三胆拖
                case "LTRXDT4": //任选四胆拖
                case "LTRXDT5": //任选五胆拖
                case "LTRXDT6": //任选六胆拖
                case "LTRXDT7": //任选七胆拖
                case "LTRXDT8": //任选八胆拖
                	int danlen = data_sel[0].length; //胆码
                	int tuolen = data_sel[1].length; //拖码
                    // alert(mname);
                	int sellen =Integer.parseInt(mname.substring(mname.length() - 1));
                    // alert(sellen);
                    if (danlen < 1 || tuolen < 1 || danlen >= sellen) {
                        nums = 0;
                    } else {
                        nums = Combination(tuolen, sellen - danlen);
                    }
                    //alert("abc");
                    //alert(nums);
                    break;
                default: //默认情况
                    for (int i = 0; i <= max_place; i++) {
                        if (data_sel[i].length == 0) { //有位置上没有选择
                            tmp_nums = 0;
                            break;
                        }
                        tmp_nums = MethodCommon.accMul(tmp_nums, data_sel[i].length);
                    }
                    nums = Integer.parseInt(tmp_nums.toString());
                    break;
            }
        }
        //03:计算金额
//        int times = parseInt($($.lt_id_data.id_sel_times).val(), 10);
//        if (Double.isNaN(times)) {
//            times = 1;
//            $($.lt_id_data.id_sel_times).val(1);
//        }
//        int money = Math.round(times * nums * $.lt_price_unit * ($.lt_method_data.modes[modes].rate * 1000)) / 1000; //倍数*注数*单价 * 模式
//        money = Double.isNaN(money) ? 0 : money;
//        $($.lt_id_data.id_sel_num).html(nums); //写入临时的注数
//        $($.lt_id_data.id_sel_money).html(money); //写临时单笔价格
		return nums;
    }
    
	public static int Combination(int c, int b) {
	    if (b < 0 || c < 0) {
	        return 0;
	    }
	    if (b == 0 || c == 0) {
	        return 1;
	    }
	    if (b > c) {
	        return 0;
	    }
	    if (b > c / 2) {
	        b = c - b;
	    }
	    double a = 0;
	    for (int i = c; i >= (c - b + 1); i--) {
	        a += Math.log(i);
	    }
	    for (int i = b; i >= 1; i--) {
	        a -= Math.log(i);
	    }
	    a =  Math.exp(a);
	    return Integer.parseInt(Math.round(a)+"");
	}
	
	public static String getMethodJsonStr(int lotteryId) {
		String json = "";
		switch(lotteryId){
		case 1 :
			json = json11;
			break;
		case 2 :
			json = json12;
			break;
		case 9 :
		case 52 :
			json = json3;
			break;
		case 3 :
			json = json4;
			break;
		case 4 :
		case 7 :
		case 51 :
		case 73 :
			json = json5;
			break;
		case 11 :
			json = json7;
			break;
		case 12 :
		case 13 :
		case 14 :
		case 15 :
			json = json8;
			break;
		}
		return json;
	}
	
	public static void main(String[] args) {
		String[][] nums = new String[][] {};

		List<String[]> list = new ArrayList<String[]>();
		List<String> sub_nums = new ArrayList<String>();
		sub_nums.add("01");
		sub_nums.add("02");
		sub_nums.add("03");
		list.add(sub_nums.toArray(new String[]{}));
		sub_nums = new ArrayList<String>();
		sub_nums.add("02");
		sub_nums.add("04");
		list.add(sub_nums.toArray(new String[]{}));
		sub_nums = new ArrayList<String>();
		sub_nums.add("03");
		sub_nums.add("05");
		list.add(sub_nums.toArray(new String[]{}));
		
		int methodid = 170;
		int lotteryId = 12;
		
		int i_nums = checkNum(list.toArray(nums), methodid, lotteryId);
		//System.out.println(i_nums);
	}
	
}
