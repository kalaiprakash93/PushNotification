package com.selfservit.util;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova. * ;
import org.json.JSONObject;

public class mPushNotification extends CordovaPlugin {
	private static CallbackContext tokenRefreshCallbackContext;

	@Override
	public boolean execute(final String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		// FirebaseApp.initializeApp(cordova.getActivity());
		if (action.equals("GetToken")) {
			try {
				String token = FirebaseInstanceId.getInstance().getToken();
				callbackContext.success(token);
			} catch (Exception e) {
				callbackContext.error(e.getMessage());
			}
		}else if(action.equals("TokenRefresh")){
			onTokenRefresh(callbackContext);
		}
		return true;
	}
	private void onTokenRefresh(final CallbackContext callbackContext) {
		mPushNotification.tokenRefreshCallbackContext = callbackContext;

		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				try {
					String currentToken = FirebaseInstanceId.getInstance().getToken();

					if (currentToken != null) {
						mPushNotification.sendToken(currentToken);
					}
				} catch (Exception e) {
					callbackContext.error(e.getMessage());
				}
			}
		});
	}
	public static void sendToken(String token) {
		if (mPushNotification.tokenRefreshCallbackContext == null) {
			return;
		}
		final CallbackContext callbackContext = mPushNotification.tokenRefreshCallbackContext;
		if (callbackContext != null && token != null) {
			PluginResult pluginresult = new PluginResult(PluginResult.Status.OK, token);
			pluginresult.setKeepCallback(true);
			callbackContext.sendPluginResult(pluginresult);
		}
	}
}
