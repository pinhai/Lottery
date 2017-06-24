package com.longlong.business.homePage.controller;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtils {
	
	private static Gson gson = new Gson();
	
	public static Object fromJson(String json,Class<Object> classOfT){
		return gson.fromJson(json, classOfT);
	}
	
	public static Object fromJson(String json,Type type){
		return gson.fromJson(json, type);
	}
	
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}

}
