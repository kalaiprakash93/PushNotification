package com.selfservit.util;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova. * ;
import org.json.JSONObject;

public class mPushNotification extends CordovaPlugin {
	 @ Override
	public boolean execute(final String action, JSONArray args, final CallbackContext callbackContext)throws JSONException {
		FirebaseApp.initializeApp(cordova.getActivity());
		if (action.equals("GetToken")) {
			String token = FirebaseInstanceId.getInstance().getToken();
			if(token != null) {
				callbackContext.success(token);
			}else{
				callbackContext.error("Token not yet generated");
			}
		}
		return true;
	}
}
