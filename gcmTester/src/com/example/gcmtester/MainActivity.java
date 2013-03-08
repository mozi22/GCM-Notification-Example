package com.example.gcmtester;

import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);

			    final String regId = GCMRegistrar.getRegistrationId(this);

			    if (regId.equals("")) {				// if regId not present get one.
			        GCMRegistrar.register(this, "SENDER_ID");
			    } else {						//regID already present just send a request to server to get back a notification.
					HttpRequest request = new HttpRequest("POST");	//GET OR POST 
					request.setAction("GcmTesting");
					request.setContext(getApplicationContext());
					request.setGCMID(regId);
					request.LoadResources();		//never forget to call this after params are set.
					request.ExecuteRequest();		//this function returns a string which you echoed on the server.
													//You can catch it if you want.
			    }
			    

	}
}
