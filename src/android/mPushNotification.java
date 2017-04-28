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
			cordova.getThreadPool().execute(new Runnable() {
			    public void run() {
				try {
				    String token = FirebaseInstanceId.getInstance().getToken();
				    callbackContext.success(token);
				} catch (Exception e) {
				    callbackContext.error(e.getMessage());
				}
			    }
			});
			
		}
		return true;
	}
}
