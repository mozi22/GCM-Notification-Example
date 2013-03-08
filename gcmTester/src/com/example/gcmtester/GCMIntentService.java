package com.example.gcmtester;


import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gcm.GCMBaseIntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class GCMIntentService extends GCMBaseIntentService {

	public static int notificationID = 1;
	private static NotificationManager manager;

	
//Remove the notification from the top. This will be called from NotificationResultActivity
public static void RemoveNotificationIcon()
{
	manager.cancel(notificationID);
}

	@Override
protected void onError(Context arg0, String arg1) {
    // TODO Auto-generated method stub

}

@Override
protected void onMessage(Context arg0, Intent msg) {

	String title = this.StripJSON(msg.getStringExtra("msg"));
	
	String ns=Context.NOTIFICATION_SERVICE;
	manager=(NotificationManager) getSystemService(ns);
	
	int icon=R.drawable.not;
	long when = System.currentTimeMillis();

	Notification notification = new Notification(icon, title, when);
	
	Context context = getApplicationContext();
	CharSequence contentTitle = "My notification";
	CharSequence contentText = "This is a test notification";
	
	Intent notificationIntent = new Intent(this,NotificationResultActivity.class);
	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

	notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	
	this.PlayNotificationSound();
	manager.notify(1, notification);
}

private void PlayNotificationSound()
{
    try {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
    } catch (Exception e) {}
}

private String StripJSON(String data)
{
	try 
	{
		String a = new JSONObject(data).getJSONObject("data").getString("msg");
		return a;
	} 
	catch (JSONException e) 
	{
		e.printStackTrace();
	}
	return "";
}

@Override
protected void onRegistered(Context arg0, String regID) {

	HttpRequest request = new HttpRequest("POST");	//GET OR POST 
	request.setAction("GcmTesting");
	request.setContext(getApplicationContext());
	request.setGCMID(regID);
	request.LoadResources();		//never forget to call this after params are set.
	request.ExecuteRequest();		//this returns a string which you echoed on the server.You can catch it if you want.
}

@Override
protected void onUnregistered(Context arg0, String arg1) {
    // TODO Auto-generated method stub

}


}