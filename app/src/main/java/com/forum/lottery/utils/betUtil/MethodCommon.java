//package com.forum.lottery.utils.betUtil;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import com.longlong.base.util.JsonUtils;
//
//public class MethodCommon {
//
//	   //求两个数组的交集
//	   public static List intersect(Object[] arr1, Object[] arr2) {
//	       Map<String, Boolean> map = new HashMap<String, Boolean>();
//	       LinkedList<String> list = new LinkedList<String>();
//	       for (Object str : arr1) {
//	           if (!map.containsKey(str)) {
//	               map.put(str.toString(), Boolean.FALSE);
//	           }
//	       }
//	       for (Object str : arr2) {
//	           if (map.containsKey(str)) {
//	               map.put(str.toString(), Boolean.TRUE);
//	           }
//	       }
//
//	       for (Entry<String, Boolean> e : map.entrySet()) {
//	           if (e.getValue().equals(Boolean.TRUE)) {
//	               list.add(e.getKey());
//	           }
//	       }
//
//	       return list;
//	   }
//
//	public static String JsRound(String cc, String aa, boolean b) {
//		int a = Integer.parseInt(aa);
//		double c = Double.parseDouble(cc);
//		if (a < 0) {
//			a = Math.abs(a);
//			return Math.round(c / Math.pow(10, a)) * Math.pow(10, a) + "";
//		} else {
//			if (a == 0) {
//				return Math.round(c) + "";
//			}
//		}
//		c = Math.round(c * Math.pow(10, a)) / Math.pow(10, a);
//		if (b) {
//			String e = "";
//			int d = 0;
//			String c1 = c + "";
//			if (c1.indexOf(".") == -1) {
//				c1 = "" + c1 + ".0";
//			}
//			String[] data = c1.split(".");
//			for (d = data[1].length(); d < a; d++) {
//				e += "0";
//			}
//			return "" + c + "" + e;
//		}
//		return c + "";
//	}
//
//
//	public static int Combination(int c, int b) {
//		// if (b < 0 || c < 0) {
//		// return false;
//		// }
//		if (b == 0 || c == 0) {
//			return 1;
//		}
//		if (b > c) {
//			return 0;
//		}
//		if (b > c / 2) {
//			b = c - b;
//		}
//		int a = 0;
//		for (int i = c; i >= (c - b + 1); i--) {
//			a += Math.log(i);
//		}
//		for (int i = b; i >= 1; i--) {
//			a -= Math.log(i);
//		}
//		double da = Math.exp(a);
//		return (int) Math.round(da);
//	}
//
//	/**
//	 *
//	 * @param {type} arg1
//	 * @param {type} arg2
//	 * @returns {Integer.parseInt}
//	 * 乘法精确运算
//	 */
//	public static Double accMul(Object arg1,Object arg2) {
//		Double d1 = Double.parseDouble(arg1 + "");
//		Double d2 = Double.parseDouble(arg2 + "");
//		return d1 * d2;
//	}
//
//	public static String movestring(String a) {
//	    String h = "";
//	    String k = "01";
//	    String b = "";
//	    String f = "";
//	    String j = "";
//	    boolean g = false;
//	    boolean c = false;
//	    for (int e = 0; e < a.length(); e++) {
//	        if (g == false) {
//	            h += a.substring(e, e + 1);
//	        }
//	        if (g == false && a.substring(e, e + 1) == "1") {
//	            c = true;
//	        } else {
//	            if (g == false && c == true && a.substring(e, e + 1) == "0") {
//	                g = true;
//	            } else {
//	                if (g == true) {
//	                    b += a.substring(e, e + 1);
//	                }
//	            }
//	        }
//	    }
//	    h = h.substring(0, h.length() - 2);
//	    for (int d = 0; d < h.length(); d++) {
//	        if (h.substring(d, d + 1) == "1") {
//	            f += h.substring(d, d + 1);
//	        } else {
//	            if (h.substring(d, d + 1) == "0") {
//	                j += h.substring(d, d + 1);
//	            }
//	        }
//	    }
//	    h = f + j;
//	    return h + k + b;
//	}
//
//	static List<String> getCombination(String[] o, int c) {
//	    int l = o.length;
//	    List<String> r = new ArrayList<String>();
//	    String[] f = new String[]{};
//	    if (c > l) {
//	        return r;
//	    }
//	    if (c == 1) {
//	        return ArrayUtils.isEmpty(o) ? r : Arrays.asList(o);
//	    }
//	    if (l == c) {
//	        r.add(0, StringUtils.join(o , ","));
//	        return r;
//	    }
//	    String a = "";
//	    String b = "";
//	    String s = "";
//	    for (int g = 0; g < c; g++) {
//	        a += "1";
//	        b += "1";
//	    }
//	    for (int e = 0; e < l - c; e++) {
//	        a += "0";
//	    }
//	    for (int d = 0; d < c; d++) {
//	        s += o[d] + ",";
//	    }
//	    r.add(0, s.substring(0, s.length() - 1));
//	    int h = 1;
//	    s = "";
//	    while (!a.substring(0 , a.length() - c - 1).equals(b)) {
//	        a = movestring(a);
//	        for (int d = 0; d < l; d++) {
//	            if (a.substring(1).equals("1")) {
//	                s += o[d] + ",";
//	            }
//	        }
//	        r.add(h , s.substring(0, s.length() - 1));
//	        s = "";
//	        h++;
//	    }
//	    return r;
//	}
//
//	public static void main(String[] args) {
//		//System.out.println(Combination(3, 3));
//	}
//
//
//	public static class _common{
//		public static class util{
//			//将一个json格式的object对象，转成json标准格式的字符串
//		    public static String jsonToString(String []jsonObj ){
//		        String jStr = "{";
//		        for(int item =0; item < jsonObj.length;){
//		            jStr += "\""+item+"\":\""+jsonObj[item]+"\",";
//		        }
//		        jStr = jStr.substring(0,jStr.length()-1);
//		        jStr += "}";
//		        return jStr;
//		    }
//
//		    public static Object Math_temp(Object a,Object b)
//		    {
//		    	double length_a = 0,length_b= 0,length= 0;
//		        if( a.toString().indexOf("e")>=0 && a.toString().split("e")[1]!="" )
//		        {
//		            length_a += Double.parseDouble(a.toString().split("e")[1]) *(-1);
//		            a = a.toString().split("e")[0];
//		        }
//		        if( a.toString().indexOf(".") >= 0 )
//		        {
//		            length_a += a.toString().split(".")[1].length();
//		            a = a.toString().split(".")[0]+""+a.toString().split(".")[1];
//		        }
//		        if(b.toString().indexOf("e")>=0&&b.toString().split("e")[1]!="")
//		        {
//		            length_b += Double.parseDouble(b.toString().split("e")[1])*(-1) ;
//		            b = b.toString().split("e")[0];
//		        }
//		        if( b.toString().indexOf(".") >= 0  )
//		        {
//		            length_b += b.toString().split(".")[1].length() ;
//		            b = b.toString().split(".")[0]+""+b.toString().split(".")[1];
//		        }
//		        if( length_b > length_a )
//		        {
//		            a = Integer.parseInt(a.toString())*Math.pow(10,length_b-length_a);
//		            b = Integer.parseInt(b.toString());
//		            length = length_b;
//		        }
//		        else
//		        {
//		            b = Integer.parseInt(b.toString())*Math.pow(10,length_a-length_b);
//		            a = Integer.parseInt(a.toString());
//		            length = length_a;
//		        }
//		        return "{\"a\":"+a+",\"b\":"+b+",\"length\":"+length+"}";
//		    }
//
//
//		    public static Object Math_result( Object result )
//		    {
//		        if( result.toString().indexOf("e")>=0 && result.toString().split("e")[1]!="" )
//		        {
//		            int length =Integer.parseInt(result.toString().split("e")[1]) *(-1) ;
//		            result = result.toString().split("e")[0];
//		            if( result.toString().indexOf(".") >= 0 )
//		            {
//		                length += result.toString().split(".")[1].length();
//		                result = result.toString().split(".")[0]+""+result.toString().split(".")[1];
//		            }
//		            if( 0 == length )
//		            {
//		                return result;
//		            }
//		            if( length < 0 )
//		            {
//		                length = length*(-1);
//		                String temp = "";
//		                while(length>0)
//		                {
//		                    temp += "0";
//		                    length--;
//		                }
//		                result = result+temp;
//		            }
//		            else
//		            {
//		                if( result.toString().length() > length )
//		                {
//		                    result = result.toString().substring(0,result.toString().length()-length)+"."+result.toString().substring(result.toString().length()-length,length);
//		                }
//		                else
//		                {
//		                    String temp = "";
//		                    while(length>result.toString().length())
//		                    {
//		                        temp += "0";
//		                        length--;
//		                    }
//		                    result = "0."+temp+result;
//		                }
//		            }
//		        }
//		        return result;
//		    }
//
//		    /**
//		     * 用于计算两个数的加法（包括带小数的浮点）
//		     * @demo  _common.util.Math_add(0.1515,0.0000003)
//		     * @return // 0.1515+0.0000003 =  0.1515003
//		     */
//		    public static Object Math_add(Object a, Object b)
//		    {
//		        Map<String,Object> map= (Map<String, Object>) JsonUtils.fromJson(Math_temp(a,b).toString(), Map.class);
//		        Object result = (Double.parseDouble(map.get("a").toString()) + Double.parseDouble(map.get("b").toString()))/Math.pow(10, map.size());
//		        return Math_result(result);
//		    }
//
//		    /**
//		     * 用于计算两个数的减法（包括带小数的浮点）
//		     * @demo  _common.util.Math_sub(0.1515,0.0000003)
//		     * @return // 0.1515 - 0.0000003
//		     */
//		    public static Object Math_sub (Object a, Object b)
//		    {
//		    	Map<String,Object> map= (Map<String, Object>) JsonUtils.fromJson(Math_temp(a,b).toString(), Map.class);
//		        Object result = (Double.parseDouble(map.get("a").toString()) - Double.parseDouble(map.get("b").toString()))/Math.pow(10, map.size());
//		        return Math_result(result);
//		    }
//
//		    /**
//		     * 用于计算两个数的乘法（包括带小数的浮点）
//		     * @demo  _common.util.Math_mul(0.1515,0.0000003)
//		     * @return // 0.1515 * 0.0000003
//		     */
//		    public static Object Math_mul (Object a, Object b)
//		    {
//		    	Map<String,Object> map= (Map<String, Object>) JsonUtils.fromJson(Math_temp(a,b).toString(), Map.class);
//		        Object result = (Double.parseDouble(map.get("a").toString()) * Double.parseDouble(map.get("b").toString()))/Math.pow(10, map.size());
//		        return Math_result(result);
//		    }
//
//		    /**
//		     * 用于计算两个数的除法（包括带小数的浮点）
//		     * @demo  _common.util.Math_div(0.1515,0.0000003)
//		     * @return //   0.1515/0.0000003 =  505
//		     */
//		    public static Object Math_div (Object a, Object b)
//		    {
//		    	Map<String,Object> map= (Map<String, Object>) JsonUtils.fromJson(Math_temp(a,b).toString(), Map.class);
//		        Object result = (Double.parseDouble(map.get("a").toString()) / Double.parseDouble(map.get("b").toString()));
//		        return Math_result(result);
//		    }
//		}
//	}
//}
