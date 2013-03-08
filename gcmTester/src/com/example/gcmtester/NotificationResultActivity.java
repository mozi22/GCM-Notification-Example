package com.example.gcmtester;

import android.app.Activity;
import android.os.Bundle;

public class NotificationResultActivity extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_not);
		
		GCMIntentService.RemoveNotificationIcon();
		
	}

}
