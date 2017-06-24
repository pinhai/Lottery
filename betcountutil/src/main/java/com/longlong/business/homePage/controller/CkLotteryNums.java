package com.longlong.business.homePage.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.gson.reflect.TypeToken;

public class CkLotteryNums {

	static String json3 = "{222:'BJZ1',223:'BJZ2',224:'BJZ2',225:'BJZ3',226:'BJZ3',227:'DWD'}";
	static String json4 = "{233:'ZX3',234:'ZXDS3',235:'ZXHZ',236:'ZUS',237:'ZUL',159:'HHZX',238:'ZUSHZ',239:'ZULHZ',229:'ZX2',231:'ZU2',230:'ZX2',232:'ZU2',228:'DWD',171:'DWD',172:'DWD',242:'BDW1',240:'DXDS',241:'DXDS',243:'BDW2'}";
	static String json5 = "{107:'ZX5',108:'ZX5',105:'ZX4',106:'ZX4',88:'ZX3',89:'ZX3',92:'ZH3',90:'ZXHZ',91:'ZXKD',93:'ZUS',1148:'ZUS',94:'ZUL',1149:'ZUL',95:'HHZX',97:'ZUHZ',99:'ZU3BD',101:'HZWS',102:'TSH3',54:'ZX3',55:'ZX3',58:'ZH3',56:'ZXHZ',57:'ZXKD',59:'ZUS',1146:'ZUS',60:'ZUL',1147:'ZUL',61:'HHZX',63:'ZUHZ',65:'ZU3BD',67:'HZWS',68:'TSH3',42:'ZX2',43:'ZX2',44:'ZXHZ2',45:'ZXKD2',50:'ZU2',51:'ZU2',52:'ZUHZ2',53:'ZU2BD',38:'ZX2',39:'ZX2',40:'ZXHZ2',41:'ZXKD2',46:'ZU2',47:'ZU2',48:'ZUHZ2',49:'ZU2BD',37:'DWD',115:'BDW1',113:'BDW1',116:'BDW2',114:'BDW2',118:'BDW2',117:'BDW1',244:'BDW1',245:'BDW2',119:'BDW1',120:'BDW2',121:'BDW3',111:'DXDS',109:'DXDS',112:'DXDS',110:'DXDS',122:'RZX2',123:'RZX2',124:'ZXHZ2',125:'ZU2',126:'ZU2',127:'ZUHZ2',128:'RZX3',129:'RZX3',130:'ZXHZ',131:'ZUS',132:'ZUS',133:'ZUL',134:'ZUL',135:'HHZX',137:'ZUHZ',139:'RZX4',140:'RZX4',141:'SXZU24',142:'SXZU12',143:'SXZU6',144:'SXZU4'}";
	static String json7 = "{145:'HEZHI',146:'HEZHI',147:'HEZHI',148:'HEZHI',149:'HEZHI',150:'HEZHI',151:'HEZHI',152:'HEZHI',153:'HEZHI',154:'HEZHI',155:'HEZHI',156:'HEZHI',157:'HEZHI',158:'HEZHI',159:'HEZHI',160:'HEZHI',161:'STTX',162:'STDX',163:'SBBZ',164:'SBDT',165:'SLTX',166:'ETFX',167:'ETDX',168:'EBBZ',169:'EBDT'}";
	static String json8 = "{170:'LTZX3',171:'LTDSZX3',176:'LTZU3',177:'LTDSZU3',182:'LTDTZU3',185:'LTZX2',186:'LTZXDS2',189:'LTZU2',190:'LTZUDS2',193:'LTDTZU2',195:'LTBDW',454:'LTDWD',198:'LTDWD',453:'LTDWD',456:'LTDDS',458:'LTCZW',199:'LTRX1',207:'LTDSRX1',200:'LTRX2',208:'LTDSRX2',215:'LTRXDT2',201:'LTRX3',209:'LTDSRX3',216:'LTRXDT3',202:'LTRX4',210:'LTDSRX4',217:'LTRXDT4',203:'LTRX5',211:'LTDSRX5',218:'LTRXDT5',204:'LTRX6',212:'LTDSRX6',219:'LTRXDT6',205:'LTRX7',213:'LTDSRX7',220:'LTRXDT7',480:'LTRX8',221:'LTRXDT8',172:'LTZHIZ3',173:'LTZDSZ3',178:'LTZUZ3',179:'LTUDSZ3',183:'LTDTZ3',174:'LTZHIH3',175:'LTZDSH3',180:'LTZUH3',181:'LTUDSH3',184:'LTDTH3',187:'LTZHIH2',188:'LTZHDSH2',191:'LTZUH2',192:'LTZUDSH2','194':'LTDTH2',196:'LTBDWZ',197:'LTBDWH',206:'LTRX8',214:'LTDSRX8'}";
	static String json11 = "{15:'ZX3',17:'ZX3',19:'ZXHZ',21:'ZUS',23:'ZUL',25:'ZUSHZ',28:'ZULHZ',11:'ZU2',13:'ZU2',1:'DWD',33:'BDW1',35:'BDW2',29:'DXDS',31:'DXDS'}";
	static String json12 = "{16:'ZX3',18:'ZX3',20:'ZXHZ',22:'ZUS',24:'ZUL',27:'ZUSHZ',26:'ZULHZ',12:'ZU2',14:'ZU2',2:'DWD',34:'BDW1',36:'BDW2',30:'DXDS',32:'DXDS',1005:'ZX5'}";

	/************************ 验证号码合法性以及计算单笔投注注数以及金额 ***********************/
	// ===================输入型检测
	private static boolean _HHZXcheck(String n, int len) {// 混合组选合法号码检测，合法返回TRUE，非法返回FALSE,n号码，len号码长度
		String[] a = new String[] {};
		if (len == 2) {// 两位
			a = new String[] { "00", "11", "22", "33", "44", "55", "66", "77",
					"88", "99" };
		} else {// 三位[默认]
			a = new String[] { "000", "111", "222", "333", "444", "555", "666",
					"777", "888", "999" };
		}
		n = n.toString();
		if (Arrays.asList(a).contains(n)) {// 不在非法列表中
			return true;
		}
		return false;
	};

	// 组三单式合法号码检测，合法返回TRUE，非法返回FALSE,n号码，len号码长度
	private static boolean _ZUSDScheck(String n, int len) {
		if (len != 3) {
			return false;
		}
		String first = "";
		String second = "";
		String third = "";
		int i = 0;
		for (i = 0; i < len; i++) {
			if (i == 0) {
				first = n.substring(i, i + 1);
			}
			if (i == 1) {
				second = n.substring(i, i + 1);
			}
			if (i == 2) {
				third = n.substring(i, i + 1);
			}
		}
		if (first == second && second == third) {
			return false;
		}
		if (first == second || second == third || third == first) {
			return true;
		}
		return false;
	};

	// 组六单式合法号码检测，合法返回TRUE，非法返回FALSE,n号码，len号码长度
	private static boolean _ZULDScheck(String n, int len) {
		if (len != 3) {
			return false;
		}
		String first = "";
		String second = "";
		String third = "";
		int i = 0;
		for (i = 0; i < len; i++) {
			if (i == 0) {
				first = n.substring(i, i + 1);
			}
			if (i == 1) {
				second = n.substring(i, i + 1);
			}
			if (i == 2) {
				third = n.substring(i, i + 1);
			}
		}
		if (first == second || second == third || third == first) {
			return false;
		} else {
			return true;
		}
	};

	private List<String[]> _pk10check(List<String[]> errorarr, int methodid,
			int lotteryId, String[][] data_sel) {
		int mid = methodid;// 玩法id
		if (lotteryId == 9 && (mid == 224 || mid == 226)) { // 9:pk10 224:前二直选单式
															// 226:前三直选单式
			String m = "";// 返回处理后的投注
			for (int i = 0; i < data_sel[0].length; i++) {
				String tmpV = data_sel[0][i];
				int isContinue = 0;
				int tmpL = tmpV.length();
				if (mid == 224 && tmpL != 4) {
					isContinue = 1;
				}
				if (mid == 226 && tmpL != 6) {
					isContinue = 1;
				}
				String[] tmpArr = new String[] {};
				for (int j = 0; j < (tmpL / 2); j++) {// 检测是否有错误号码
					String hao = tmpV.substring(j * 2, j * 2 + 2);
					tmpArr[j] = hao;
					if (Integer.parseInt(hao) > 10
							|| Integer.parseInt(hao) == 0) {// 检测错误号码
						isContinue = 1;
						break;
					}
				}
				// 检测重复
				if (mid == 224 && tmpArr.length == 2 && tmpArr[0] == tmpArr[1]) {// 前二直选
					isContinue = 1;
				} else if (mid == 226
						&& tmpArr.length == 3
						&& (tmpArr[0] == tmpArr[1] || tmpArr[0] == tmpArr[2] || tmpArr[1] == tmpArr[2])) {// 前三直选
					isContinue = 1;
				}
				if (isContinue == 1) {// 有错误号码,进行下次循环
					errorarr.add(tmpArr);
					continue;
				}
				if (m != "" && m != null) {
					m += " ";
				}
				m += tmpV;
			}
			data_sel[0] = m.split(" ");
		}
		return errorarr;
	};

	// 号码检测,l:号码长度,e是否返回非法号码，true是,false返回合法注数,fun对每个号码的附加检测,sort:是否对每个号码排序
	private static int _inputCheck_Num(int l, boolean e, boolean fun,
			boolean sort, String[][] data_sel) { // file input from here
		int nums = data_sel[0].length;
		String[] error = new String[] {};
		String[] newsel = new String[] {};
		String partn = "";
		// l = Integer.parseInt(l);
		switch (l) {
		case 2:
			partn = "/^[0-9]{2}$/";
			break;
		case 4:
			partn = "/^[0-9]{4}$/";
			break;
		case 5:
			partn = "/^[0-9]{5}$/";
			break;
		case 6:
			partn = "/^[0-9]{6}$/";
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

			if (isMatch && fun) {
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

	// 号码注数检测
	private static int _inputCheckBjscNum(int l, String[][] data_sel) {
		int nums = data_sel[0].length;
		for (int i = 0; i < data_sel[0].length; i++) {
			String n = data_sel[0][i].trim();
			if (n.length() != l) {
				nums--;
			} else {
				for (int j = 0; i < (l / 2); j++) {
					String hao = n.substring(j * 2, j * 2 + 2);
					if (Integer.parseInt(hao) > 10) {
						nums--;
						break;
					}
				}
			}
		}
		return nums;
	}

	@SuppressWarnings({ "unchecked" })
	public static int checkNum(String[][] data_sel, int methodid, int lotteryId) {// 时时计算投注注数与金额等
		Map<Integer, String> methods = new HashMap<Integer, String>();
		String json = getMethodJsonStr(lotteryId);
		methods = (Map<Integer, String>) JsonUtils.fromJson(json,new TypeToken<Map<Integer, String>>(){}.getType());
		int nums = 0;
		String mname = methods.get(methodid+"");// 玩法的简写,如:"ZX3"
		if(StringUtils.isBlank(mname))
			mname = methods.get(methodid);
		// String mname = $.lt_method[$.lt_method_data.methodid];//玩法的简写,如:"ZX3"
		int modes = 1;
		int max_place = data_sel.length; // 最大place
		// 多少组码
		int plength = data_sel.length;
		int isrx = 0;
		String otype = "";
		List<Layout> layouts = new ArrayList<>();
		if(lotteryId != 11){
			InnerLabel label = MJsonUtils.queryMethod(methodid, lotteryId);
			isrx = label.getIsrx();// 是否是任选
			otype = label.getSelectarea().getType();
			layouts = label.getSelectarea().getLayout();
		}else{
			PlsInnerLabel label = MJsonUtils.queryPlsMethod(methodid, lotteryId);
			isrx = label.getIsrx();// 是否是任选
			otype = label.getSelectarea().getType();
			layouts = label.getSelectarea().getLayout();
		}

		Integer[] minchosen = new Integer[] {};// 每一位上最少选择的号码个数
		List<Integer> list_chosen = new ArrayList<Integer>();
		for (Layout lay : layouts) {
			max_place = max_place > lay.getPlace() ? max_place : lay.getPlace();
			list_chosen.add(lay.getPlace(),lay.getMinchosen());
//			minchosen[lay.getPlace()] = lay.getMinchosen();
		}
		minchosen = list_chosen.toArray(minchosen);
		// 01:验证号码合法性并计算注数
		if (otype == "input") {// 输入框形式的检测
			if (data_sel[0].length > 0) {// 如果输入的有值
				String n = StringUtils.join(data_sel[0], "");
				int len = n.length();
				switch (mname) {
				case "ZX5":
					nums = _inputCheck_Num(5, false, true, false, data_sel);
					break;
				case "ZX4":
					nums = _inputCheck_Num(4, false, true, false, data_sel);
					break;
				case "ZX3":
				case "ZXDS3":
					nums = _inputCheck_Num(3, false, true, false, data_sel);
					break;
				case "ZUS":
					nums = _inputCheck_Num(3, false, _ZUSDScheck(n, len), true,
							data_sel);
					if (isrx == 1) {// 任选玩法：考虑选择的位置
						nums *= plength == 0 ? 0 : MethodCommon.Combination(
								plength, 3);
					}
					break;
				case "ZUL":
					nums = _inputCheck_Num(3, false, _ZULDScheck(n, len), true,
							data_sel);
					if (isrx == 1) {// 任选玩法：考虑选择的位置
						nums *= plength == 0 ? 0 : MethodCommon.Combination(
								plength, 3);
					}
					break;
				case "HHZX":
					nums = _inputCheck_Num(3, false, _HHZXcheck(n, len), true,
							data_sel);
					if (isrx == 1) {// 任选玩法：考虑选择的位置
						nums *= plength == 0 ? 0 : MethodCommon.Combination(
								plength, 3);
					}
					break;
				case "ZX2":
					nums = _inputCheck_Num(2, false, true, true, data_sel);
					break;
				case "ZU2":
					nums = _inputCheck_Num(2, false, _HHZXcheck(n, len), true,
							data_sel);
					if (isrx == 1) {// 任选玩法：考虑选择的位置
						nums *= plength == 0 ? 0 : MethodCommon.Combination(
								plength, 2);
					}
					break;
				case "RZX2":// 任选二直选单式
				case "RZX3":// 任选三直选单式
				case "RZX4":// 任选四直选单式
					String sellen = mname.substring(mname.length() - 1);
					nums = _inputCheck_Num(Integer.parseInt(sellen), false,
							true, true, data_sel);
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, Integer.parseInt(sellen));
					break;
				case "BJZ2":
					nums = _inputCheckBjscNum(4, data_sel);
					break;
				case "BJZ3":
					nums = _inputCheckBjscNum(6, data_sel);
					break;
				default:
					break;
				}
			}
		} else {// 其他选择号码形式[暂时就数字型和大小单双]
			int tmp_nums = 1;
			switch (mname) {// 根据玩法分类不同做不同处理
			case "ZH5":
			case "ZH4":
			case "ZH3":
				for (int i = 0; i < max_place; i++) {
					if (data_sel[i].length == 0) {// 有位置上没有选择
						tmp_nums = 0;
						break;
					}
					tmp_nums = MethodCommon
							.accMul(tmp_nums, data_sel[i].length).intValue();
				}
				nums = tmp_nums * (int) (mname.charAt(mname.length() - 1));
				break;
			case "WXZU120":
				int s = data_sel[0].length;
				if (s > 4) {// 必须选5位或者以上
					nums += MethodCommon.Combination(s, 5);
				}
				break;
			case "WXZU60":
			case "WXZU30":
			case "WXZU20":
			case "WXZU10":
			case "WXZU5":
				if (data_sel[0].length >= minchosen[0]
						&& data_sel[1].length >= minchosen[1]) {
					int h = MethodCommon.intersect(data_sel[0], data_sel[1])
							.size();
					tmp_nums = MethodCommon.Combination(data_sel[0].length,
							minchosen[0])
							* MethodCommon.Combination(data_sel[1].length,
									minchosen[1]);
					if (h > 0) {
						// C(m,1)*C(n,3)-C(h,1)*C(n-1,2)
						if (mname == "WXZU60") {
							tmp_nums -= MethodCommon.Combination(h, 1)
									* MethodCommon.Combination(
											data_sel[1].length - 1, 2);
						}
						// C(m,2)*C(n,1)-C(h,2)*C(2,1)-C(h,1)*C(m-h,1)
						else if (mname == "WXZU30") {
							tmp_nums -= MethodCommon.Combination(h, 2)
									* MethodCommon.Combination(2, 1);
							if (data_sel[0].length - h > 0) {
								tmp_nums -= MethodCommon.Combination(h, 1)
										* MethodCommon.Combination(
												data_sel[0].length - h, 1);
							}
						}
						// C(m,1)*C(n,2)-C(h,1)*C(n-1,1)
						else if (mname == "WXZU20") {
							tmp_nums -= MethodCommon.Combination(h, 1)
									* MethodCommon.Combination(
											data_sel[1].length - 1, 1);
						}
						// C(m,1)*C(n,1)-C(h,1)
						else if (mname == "WXZU10" || mname == "WXZU5") {
							tmp_nums -= MethodCommon.Combination(h, 1);
						}
					}
					nums += tmp_nums;
				}
				break;
			case "SXZU24":
				int s24 = data_sel[0].length;
				if (s24 > 3) {// 必须选4位或者以上
					nums += MethodCommon.Combination(s24, 4);
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 4);
				}
				break;
			case "SXZU6":
				if (data_sel[0].length >= minchosen[0]) {
					// C(n,2)
					nums += MethodCommon.Combination(data_sel[0].length,
							minchosen[0]);
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 4);
				}
				break;
			case "SXZU12":
			case "SXZU4":
				if (data_sel[0].length >= minchosen[0]
						&& data_sel[1].length >= minchosen[1]) {
					int h = MethodCommon.intersect(data_sel[0], data_sel[1])
							.size();
					tmp_nums = MethodCommon.Combination(data_sel[0].length,
							minchosen[0])
							* MethodCommon.Combination(data_sel[1].length,
									minchosen[1]);
					if (h > 0) {
						// C(m,1)*C(n,2)-C(h,1)*C(n-1,1)
						if (mname == "SXZU12") {
							tmp_nums -= MethodCommon.Combination(h, 1)
									* MethodCommon.Combination(
											data_sel[1].length - 1, 1);
						}
						// C(m,1)*C(n,1)-C(h,1)
						else if (mname == "SXZU4") {
							tmp_nums -= MethodCommon.Combination(h, 1);
						}
					}
					nums += tmp_nums;
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 4);
				}
				break;
			case "ZXKD": // 直选跨度3特殊算法
				String zxkd_cc = "{\"0\":10,\"1\":54,\"2\":96,\"3\":126,\"4\":144,\"5\":150,\"6\":144,\"7\":126,\"8\":96,\"9\":54}";
				Map<String, Integer> zxkd_map = new HashMap<String, Integer>();
				zxkd_map = (Map<String, Integer>) JsonUtils.fromJson(zxkd_cc,
						new TypeToken<HashMap<String, Integer>>(){}.getType());
				for (int i = 0; i < max_place; i++) {
					int zxkd_s = data_sel[i].length;
					for (int j = 0; j < zxkd_s; j++) {
						nums += zxkd_map.get(Integer.parseInt(data_sel[i][j],
								10) + "");
					}
				}
				break;
			case "ZXKD2": // 直选跨度2特殊算法
				String zxkd2_cc = "{\"0\":10,\"1\":18,\"2\":16,\"3\":14,\"4\":12,\"5\":10,\"6\":8,\"7\":6,\"8\":4,\"9\":2}";
				Map<String, Integer> zxkd2_map = new HashMap<String, Integer>();
				zxkd2_map = (Map<String, Integer>) JsonUtils.fromJson(
						zxkd2_cc, new TypeToken<HashMap<String, Integer>>(){}.getType());
				for (int i = 0; i < max_place; i++) {
					int d2 = data_sel[i].length;
					for (int j = 0; j < d2; j++) {
						nums += zxkd2_map.get(Integer.parseInt(data_sel[i][j],
								10) + "");
					}
				}
				break;
			case "ZXHZ": // 直选和值特殊算法
			case "ZUHZ": // 混合组选特殊算法
				String hzstr = "{\"0\":1,\"1\":3,\"2\":6,\"3\":10,\"4\":15,\"5\":21,\"6\":28,\"7\":36,\"8\":45,\"9\":55,\"10\":63,\"11\":69,\"12\":73,\"13\":75,\"14\":75,\"15\":73,\"16\":69,\"17\":63,\"18\":55,\"19\":45,\"20\":36,\"21\":28,\"22\":21,\"23\":15,\"24\":10,\"25\":6,\"26\":3,\"27\":1}";
				Map<String, Integer> hzmap = new HashMap<String, Integer>();
				if (mname == "ZUHZ") {
					hzstr = "{\"1\":1,\"2\":2,\"3\":2,\"4\":4,\"5\":5,\"6\":6,\"7\":8,\"8\":10,\"9\":11,\"10\":13,\"11\":14,\"12\":14,\"13\":15,\"14\":15,\"15\":14,\"16\":14,\"17\":13,\"18\":11,\"19\":10,\"20\":8,\"21\":6,\"22\":5,\"23\":4,\"24\":2,\"25\":2,\"26\":1}";
				}
				hzmap = (Map<String, Integer>) JsonUtils.fromJson(hzstr,new TypeToken<HashMap<String, Integer>>(){}.getType());
				for (int i = 0; i < max_place; i++) {
					int zuhzs = data_sel[i].length;
//					hzmap = new HashMap<Integer, Integer>();
					for (int j = 0; j < zuhzs; j++) {
						nums += hzmap.get(Integer.parseInt(data_sel[i][j], 10) + "");
					}
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 3);
				}
				break;
			case "ZUSHZ":
				String zushz_cc = "{\"1\":1,\"2\":2,\"3\":1,\"4\":3,\"5\":3,\"6\":3,\"7\":4,\"8\":5,\"9\":4,\"10\":5,\"11\":5,\"12\":4,\"13\":5,\"14\":5,\"15\":4,\"16\":5,\"17\":5,\"18\":4,\"19\":5,\"20\":4,\"21\":3,\"22\":3,\"23\":3,\"24\":1,\"25\":2,\"26\":1}";
				Map<String, Integer> zushz_map = new HashMap<String, Integer>();
				zushz_map = (Map<String, Integer>) JsonUtils.fromJson(
						zushz_cc, new TypeToken<HashMap<String, Integer>>(){}.getType());
				for (int i = 0; i < max_place; i++) {
					int zushz_s = data_sel[i].length;
					for (int j = 0; j < zushz_s; j++) {
						nums += zushz_map.get(Integer.parseInt(data_sel[i][j],
								10) + "");
					}
				}
				break;
			case "ZULHZ":
				String zulhz_cc = "{\"3\":1,\"4\":1,\"5\":2,\"6\":3,\"7\":4,\"8\":5,\"9\":7,\"10\":8,\"11\":9,\"12\":10,\"13\":10,\"14\":10,\"15\":10,\"16\":9,\"17\":8,\"18\":7,\"19\":5,\"20\":4,\"21\":3,\"22\":2,\"23\":1,\"24\":1}";
				Map<String, Integer> zulhz_map = new HashMap<String, Integer>();
				zulhz_map = (Map<String, Integer>) JsonUtils.fromJson(
						zulhz_cc, new TypeToken<HashMap<String, Integer>>(){}.getType());
				for (int i = 0; i < max_place; i++) {
					int zulhz_s = data_sel[i].length;
					for (int j = 0; j < zulhz_s; j++) {
						nums += zulhz_map.get(Integer.parseInt(data_sel[i][j],
								10) + "");
					}
				}
				break;
			case "ZUS": // 组三
				for (int i = 0; i < max_place; i++) {
					int zus_s = data_sel[i].length;
					if (zus_s > 1) {// 组三必须选两位或者以上
						nums += zus_s * (zus_s - 1);
					}
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 3);
				}
				break;
			case "ZUL": // 组六
				for (int i = 0; i < max_place; i++) {
					int zul_s = data_sel[i].length;
					if (zul_s > 2) {// 组六必须选三位或者以上
						nums += zul_s * (zul_s - 1) * (zul_s - 2) / 6;
					}
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 3);
				}
				break;
			case "ZXHZ2":// 直选和值2
				String zxhz2_cc = "{\"0\":1,\"1\":2,\"2\":3,\"3\":4,\"4\":5,\"5\":6,\"6\":7,\"7\":8,\"8\":9,\"9\":10,\"10\":9,\"11\":8,\"12\":7,\"13\":6,\"14\":5,\"15\":4,\"16\":3,\"17\":2,\"18\":1}";
				Map<String, Integer> zxhz2_map = new HashMap<String, Integer>();
				zxhz2_map = (Map<String, Integer>) JsonUtils.fromJson(
						zxhz2_cc, new TypeToken<HashMap<String, Integer>>(){}.getType());
				for (int i = 0; i < max_place; i++) {
					int zxhz2_s = data_sel[i].length;
					for (int j = 0; j < zxhz2_s; j++) {
						nums += zxhz2_map.get(Integer.parseInt(data_sel[i][j],
								10) + "");
					}
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 2);
				}
				break;
			case "ZUHZ2":// 组选和值2
				String zuhz2_cc = "{\"0\":0,\"1\":1,\"2\":1,\"3\":2,\"4\":2,\"5\":3,\"6\":3,\"7\":4,\"8\":4,\"9\":5,\"10\":4,\"11\":4,\"12\":3,\"13\":3,\"14\":2,\"15\":2,\"16\":1,\"17\":1,\"18\":0}";
				Map<String, Integer> zuhz2_map = new HashMap<String, Integer>();
				zuhz2_map = (Map<String, Integer>) JsonUtils.fromJson(
						zuhz2_cc, new TypeToken<HashMap<String, Integer>>(){}.getType());
				for (int i = 0; i < max_place; i++) {
					int zuhz2_s = data_sel[i].length;
					for (int j = 0; j < zuhz2_s; j++) {
						nums += zuhz2_map.get(Integer.parseInt(data_sel[i][j],
								10) + "");
					}
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 2);
				}
				break;
			case "ZU3BD":
				nums = data_sel[0].length * 54;
				break;
			case "ZU2BD":
				nums = data_sel[0].length * 9;
				break;
			case "BDW3":
				for (int i = 0; i < max_place; i++) {
					int bdw3_s = data_sel[i].length;
					if (bdw3_s > 2) {// 三码不定位必须选3位或者以上
						nums += MethodCommon.Combination(data_sel[i].length, 3);
					}
				}
				break;
			case "BDW2": // 二码不定位
			case "ZU2": // 2位组选
				for (int i = 0; i < max_place; i++) {
					int zu2_s = data_sel[i].length;
					if (zu2_s > 1) {// 二码不定位必须选两位或者以上
						nums += zu2_s * (zu2_s - 1) / 2;
					}
				}
				if (isrx == 1) {// 任选玩法：考虑选择的位置
					nums *= plength == 0 ? 0 : MethodCommon.Combination(
							plength, 2);
				}
				break;
			case "DWD": // 定位胆所有在一起特殊处理
				for (int i = 0; i < max_place; i++) {
					nums += data_sel[i].length;
				}
				break;
			// 时时彩任选玩法
			case "RZX2":// 任二直选
			case "RZX3":// 任三直选
			case "RZX4":// 任四直选
				List<String> aCodePosition = new ArrayList<String>();
				for (int i = 0; i < max_place; i++) {
					int codelen = data_sel[i].length;// 指定位置上的号码长度
					if (codelen > 0) {
						aCodePosition.add(i+"");
					}
				}
				int sellen = Integer
						.parseInt(mname.substring(mname.length() - 1));
				List<String> list =  MethodCommon.getCombination(
						aCodePosition.toArray(new String[] {}), sellen);
				String[] aPositionCombo = CollectionUtils.isEmpty(list) ? null : list.toArray(new String[]{});
				int iComboLen = aPositionCombo.length;// 组合个数
				String[] aCombo = {};
				int iLen = 0;
				int tmpNums = 1;
				for (int j = 0; j < iComboLen; j++) {
					aCombo = aPositionCombo[j].split(",");
					iLen = aCombo.length;
					tmpNums = 1;
					for (int h = 0; h < iLen; h++) {
						tmpNums *= data_sel[Integer.parseInt(aCombo[h])].length;
					}
					nums += tmpNums;
				}
				break;
			case "BJZ2":
				for (int i = 0; i < max_place; i++) {
					if (data_sel[i].length == 0) {// 有位置上没有选择
						tmp_nums = 0;
						break;
					}
					tmp_nums = MethodCommon
							.accMul(tmp_nums, data_sel[i].length).intValue();
				}
				nums = tmp_nums;
				int len1 = data_sel[0].length;
				int len2 = data_sel[1].length;
				int same_nums = 0;
				for (int i = 0; i < len1; i++) {
					for (int j = 0; j < len2; j++) {
						if (data_sel[0][i] == data_sel[1][j]) {
							same_nums++;
							break;
						}
					}
				}
				nums -= same_nums;
				break;
			case "BJZ3":
				len1 = data_sel[0].length;
				len2 = data_sel[1].length;
				int len3 = data_sel[2].length;
				nums = 0;
				for (int i = 0; i < len1; i++) {
					for (int j = 0; j < len2; j++) {
						if (data_sel[0][i] == data_sel[1][j]) {
							continue;
						}
						for (int k = 0; k < len3; k++) {
							if (data_sel[0][i] == data_sel[2][k]
									|| data_sel[1][j] == data_sel[2][k]) {
								continue;
							} else {
								nums++;
							}
						}
					}
				}
				break;
			default: // 默认情况
				for (int i = 0; i < max_place; i++) {
					if (data_sel[i].length == 0) {// 有位置上没有选择
						tmp_nums = 0;
						break;
					}
					tmp_nums = MethodCommon
							.accMul(tmp_nums, data_sel[i].length).intValue();
				}
				nums = tmp_nums;
				break;
			}
		}
		return nums;
		// 03:计算金额
		// var times = Integer.parseInt($($.lt_id_data.id_sel_times).val(), 10);
		// if (isNaN(times)) {
		// times = 1;
		// $($.lt_id_data.id_sel_times).val(1);
		// }
		// var money = Math.round(times * nums * $.lt_price_unit *
		// ($.lt_method_data.modes[modes].rate * 1000)) / 1000;//倍数*注数*单价 * 模式
		// money = isNaN(money) ? 0 : money;
		// $($.lt_id_data.id_sel_num).html(nums); //写入临时的注数
		// $($.lt_id_data.id_sel_money).html(money);//写临时单笔价格
	}

	public static String getMethodJsonStr(int lotteryId) {
		String json = "";
		switch (lotteryId) {
		case 1:
			json = json11;
			break;
		case 2:
			json = json12;
			break;
		case 9:
		case 52:
			json = json3;
			break;
		case 3:
			json = json4;
			break;
		case 4:
		case 7:
		case 51:
		case 73:
			json = json5;
			break;
		case 11:
			json = json7;
			break;
		case 12:
		case 13:
		case 14:
		case 15:
			json = json8;
			break;
		}
		return json;
	};

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
		
		int methodid = 54;
		int lotteryId = 73;
		
//		int i_nums = checkNum(list.toArray(nums), methodid, lotteryId);
		//System.out.println(JsonUtils.toJson(list.toArray(nums)));
	}

}
