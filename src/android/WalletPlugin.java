/*
 * Copyright (c) 2016, salesforce.com, inc.
 * All rights reserved.
 * Licensed under the BSD 3-Clause license.
 * For full license text, see LICENSE.txt file in the repo root  or https://opensource.org/licenses/BSD-3-Clause
 */
package com.hannaford.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.SharedPreferences;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.support.annotation.Nullable;

import com.worklight.wlclient.api.*;
/**
 * Wallet Plugin the primary class where the implementation is contained
 *
 * @author IBM India iXM team
 */

public class WalletPlugin extends CordovaPlugin {
	private final static String TAG = "~#MAIN ACTIVITY";
	private static final int LAUNCH_PAY_APP_REQUEST = 1;
	private Activity mContext;
	private boolean subscribe;
	private CallbackContext mCallbackContext;
	//TODO: remove if not needed
	/*private SharedPreferences sharedPreferences;*/

	/*TODO: remove if not needed*/
	public static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		mContext= cordova.getActivity();

		Log.d(TAG, "mContext: " + mContext);
		Log.d(TAG, "mCallbackContext: " + mCallbackContext);
		//TODO: remove if not needed
		/*sharedPreferences = mContext.getSharedPreferences("FoodlionApp",mContext.getBaseContext().MODE_PRIVATE);*/
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		mCallbackContext = callbackContext;
		if(action.equals("canAddToWallet")){
			canAddToWallet(args);
		}
		else if(action.equals("launchPayApp")){
			launchPayApp(args);
		}
		return true;
	}

	private void canAddToWallet(JSONArray args){

	}

	private void launchPayApp(JSONArray args) throws JSONException{

		String jwtToken = args.getString(0);
		Uri cardUrl = Uri.parse("https://pay.google.com/gp/v/save/" + jwtToken);
		Intent intent = new Intent(Intent.ACTION_VIEW, cardUrl);
		
		mContext.startActivity(intent);
	}

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// Check which request we're responding to
    	if (requestCode == LAUNCH_PAY_APP_REQUEST) {
        	// Make sure the request was successful
        	if (resultCode == Activity.RESULT_OK) {
        		Log.d(TAG, "Result OK");
        	} else if (resultCode == Activity.RESULT_CANCELED){
				Log.d(TAG, "Result Cancelled");
			}
    	}
	}*/
}
