package com.selfservit.notify;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova. * ;
import org.json.JSONObject;

public class mPushNotification extends CordovaPlugin {
	
	private FirebaseAnalytics mFirebaseAnalytics;
	@Override
	protected void pluginInitialize() {
		final Context context = this.cordova.getActivity().getApplicationContext();
		//final Bundle extras = this.cordova.getActivity().getIntent().getExtras();
		this.cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				Log.d("log", "Starting Firebase plugin");
				mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

			}
		});
	}
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
