//package com.forum.lottery.utils.betUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//import org.apache.commons.lang3.StringUtils;
//
///**
// * app接口专用controller
// *
// * @author DELL
// *
// */
//public class BallNumController {
//
//	public Map<String, Object> getLotteryNum(
//			String balls, int lotteryId, int methodid) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("count", 0);
//		if (StringUtils.isBlank(balls))
//			return result;
//		String[][] ball_array = new String[][] {};
//		ball_array = (String[][]) JsonUtils.fromJson(balls,
//				ball_array.getClass());
//		if (lotteryId == 12 || lotteryId == 13 || lotteryId == 14
//				|| lotteryId == 15) {
//			result.put("count",
//					PlayedUtil.checkNum(ball_array, methodid, lotteryId));
//		} else {
//			result.put("count",
//					CkLotteryNums.checkNum(ball_array, methodid, lotteryId));
//		}
//		return result;
//	}
//}
