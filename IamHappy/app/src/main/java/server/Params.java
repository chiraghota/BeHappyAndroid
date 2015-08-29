/**
 * 
 */
package server;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;

public final class Params {

	private static Context appContext;

	private static String EMAIL = "email";
	private static String ACCESS_TOKEN = "access_token";
	private static String NAME = "name";


	public static void setAppContext(Context context){
		appContext = context;
	}

	public static Map<String, String> getProviderUserParamsFrom(String email,
			String access_token, String name) {
		Map<String, String> userParams = new HashMap<String, String>();
		userParams.put(EMAIL, email);
		userParams.put(ACCESS_TOKEN, access_token);
		userParams.put(NAME, name);
		return userParams;
	}

//	private static void setDefaultParams(StringBuilder apiData){
//		apiData.append(PLATFORM);
//		apiData.append(EQUALS);
//		apiData.append(ANDROID);
//		apiData.append("&");
//		apiData.append(VERSION);
//		apiData.append(EQUALS);
//		apiData.append(AppUtil.getAppVersion(appContext));
//		apiData.append("&");
//		apiData.append(CITY);
//		apiData.append(EQUALS);
//		apiData.append(CITY_SELECTED);
//		apiData.append("&");
//		apiData.append(DEVICE_ID);
//		apiData.append(EQUALS);
//		apiData.append(AppUtil.getDeviceId(appContext));
//		apiData.append("&");
//		apiData.append(DEVICE_MAKE);
//		apiData.append(EQUALS);
//		apiData.append(AppUtil.getDeviceName().replaceAll("\\s+",""));
//	}

	private static String encode(StringBuilder apiData){
		return apiData.toString().replaceAll(" ", "%20");
	}

}
