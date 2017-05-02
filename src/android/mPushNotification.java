package com.selfservit.notify;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova. * ;
import org.json.JSONObject;

import java.util.logging.Handler;

public class mPushNotification extends CordovaPlugin {
	//private static CallbackContext tokenRefreshCallbackContext;

	@Override
	public boolean execute(final String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		// FirebaseApp.initializeApp(cordova.getActivity());
		if (action.equals("GetToken")) {
			getToken(callbackContext);
		}
		return true;
	}
	public static void getToken(CallbackContext callbackContext){
		try {
			String token = FirebaseInstanceId.getInstance().getToken();
			callbackContext.success(token);
		} catch (Exception e) {
			callbackContext.error(e.getMessage());
		}
	}
}
