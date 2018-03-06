package com.rollingStones.utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class FacePlusPlusTest {
	private static final String API_KEY = "g1MipZY9yzfOhXFI48zsMjSUGQoHzgGb";
	private static final String API_SECRET = "eSig8sh_OfqhXyGCd4AOt34Jv4KYTHvh";

	/**
	 * 传入图片进行人脸检测和人脸分析。
	 * 可以检测图片内的所有人脸，对于每个检测出的人脸，会给出其唯一标识 face_token，可用于后续的人脸分析、人脸比对等操作。对于正式 API Key，支持指定图片的某一区域进行人脸检测。
	 * 本 API 支持对检测到的人脸直接进行分析，获得人脸的关键点和各类属性信息。对于试用 API Key，最多只对人脸框面积最大的 5 个人脸进行分析，
	 * 其他检测到的人脸可以使用 Face Analyze API 进行分析。对于正式 API Key，支持分析所有检测到的人脸。
	 */
	public static void detect(String imgUrl){
		String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    	
    	Map<String, String> map = Maps.newHashMap();
    	map.put("api_key", API_KEY);
    	map.put("api_secret", API_SECRET);
    	map.put("image_url", imgUrl);
    	map.put("return_landmark", "0");
    	map.put("return_attributes", "gender,age,beauty,ethnicity,emotion,skinstatus");
    	
    	String result = HttpUtils.doHttpsPost(url, map,"utf-8");
    	System.out.println(result);
	}
	
	/**
	 * 传入在 Detect API 检测出的人脸标识 face_token，分析得出人脸关键点，人脸属性信息。一次调用最多支持分析 5 个人脸。
	 */
	public static void faceAnalyze(String token){
		String url = "https://api-cn.faceplusplus.com/facepp/v3/face/analyze";
		
		Map<String, String> map = Maps.newHashMap();
    	map.put("api_key", API_KEY);
    	map.put("api_secret", API_SECRET);
    	map.put("face_tokens", token);
    	map.put("return_landmark", "0");
    	map.put("return_attributes", "gender,age,beauty,ethnicity,emotion,skinstatus");
    	
    	String result = HttpUtils.doHttpsPost(url, map,"utf-8");
    	System.out.println(result);
	}
	
	public static void main(String[] args) {
		detect("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=665718205,2978073373&fm=27&gp=0.jpg");
		faceAnalyze("bfe9ad2079978e954d9ae03232ef37b0");
	}
}
