package com.example.gcmtester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

public class HttpRequest {
	

	private String userName;
	private String imgPath;
	private String userEmail;
	private String userpassword;
	private String action;
	private String RequestType;
	private String gcmID;
	
	private HttpClient httpclient;
	private HttpGet httpget;
	private HttpPost httpPost;
	private HttpResponse response ;
	private HttpEntity entity;
	
	private List<NameValuePair> nameValuePairs;
	
	private Context ctx;	//because my general url is present in strings.xml. So I am using this to retrieve my url from there.
	
	public void setContext(final Context c)						{ this.ctx = c;					}	
	
	//all these parameters were of my use. You can add or subtract according to your needs.
	
	public void setUserName(final String username)    			{ this.userName = username;     }		
	public void setUserEmail(final String userEmail) 			{ this.userEmail = userEmail; 	}		
	public void setUserPassword(final String pwd)    			{ this.userpassword = pwd;    	}		
	public void setAction(final String action)					{ this.action	=	action;		}
	public void setImagePath(final String imgpath)				{ this.imgPath	= imgpath;  	}

	
	//This is inserted just to show the demo.
	public void setGCMID	(final String id)					{ this.gcmID	= id;			}
	
	public HttpRequest(String requestType)
	{
		this.RequestType = requestType;
		this.httpclient= new DefaultHttpClient();	
	}
	
	public void LoadResources()
	{
		if(this.RequestType == "GET")
			this.httpget = new HttpGet(this.GetUrl());
		else
		{
			this.httpPost = new HttpPost(this.GetUrl());
			this.CreateParameters();
		}
		
		
	}
	
	private void CreateParameters()
	{
		
		//You can edit this and insert Post Parameters according to your actions.
		//The third if condition is used to show the demo.
		
		/*This function will initialize the post parameters according to actions you perform.
		*
		*	Make sure you set the required parameters values before this function is called.
		*	
		*
		**/
		try 
	    {
		    if(this.action == "VerifyUserLogin")		
			{
			    this.nameValuePairs = new ArrayList<NameValuePair>(3);
			    this.nameValuePairs.add(new BasicNameValuePair("email",this.userEmail));
			    this.nameValuePairs.add(new BasicNameValuePair("pwd",this.userpassword));
				this.httpPost.setEntity(new UrlEncodedFormEntity(this.nameValuePairs));
			}		
			else if(this.action == "InsertNewUser" )
			{
			    this.nameValuePairs = new ArrayList<NameValuePair>(3);
			    this.nameValuePairs.add(new BasicNameValuePair("Email",this.userEmail));
			    this.nameValuePairs.add(new BasicNameValuePair("UserName",this.userName));
			    this.nameValuePairs.add(new BasicNameValuePair("Password",this.userpassword));
			    this.nameValuePairs.add(new BasicNameValuePair("ImagePath",this.imgPath));
			    this.httpPost.setEntity(new UrlEncodedFormEntity(this.nameValuePairs));
			}
			else if(this.action == "GcmTesting")	//demo
			{
			    this.nameValuePairs = new ArrayList<NameValuePair>(3);
			    this.nameValuePairs.add(new BasicNameValuePair("gcmID",this.gcmID));
			    this.httpPost.setEntity(new UrlEncodedFormEntity(this.nameValuePairs));
			}
		}
	    catch (UnsupportedEncodingException e) 
	    {
			e.printStackTrace();
		}
	}
	
	
	private String GetUrl() 
	{
		
		//You can set get requests also and specify parameters here in the url.
		//This function will return the url according to the action you want to perform.
		
		String url="";
		try {
			
		if(this.action == "VerifyUserLogin")
		{		
			url = this.ctx.getString(R.string.server_address)+"/login_srv.php";
			
		}
		
		else if(this.action == "InsertNewUser" )
		{
			url = this.ctx.getString(R.string.server_address)+"/AddUser.php";
		}
		
		else if(this.action == "GcmTesting")
		{
			
			url = this.ctx.getString(R.string.server_address)+"/GCMPushMessage.php";
		}
		
		} catch (Exception e) {
	
			Log.e("HttpPostRequestError",e.getMessage());
			e.printStackTrace();
		}
		return url;

	}
	
	public String ExecuteRequest()
	{
		
		//This function executes the request according to the request type you set and return the result which will
		// be sent from the server.
		
		try {
		
		if(this.RequestType == "GET")
			this.response = this.httpclient.execute(this.httpget);
		else if(this.RequestType == "POST")
			this.response = this.httpclient.execute(this.httpPost);
		
		this.entity = this.response.getEntity();
		
		if(entity!=null)
        {
	        	  InputStream inputStream=entity.getContent();
	        	  String result= convertStreamToString(inputStream);
	        	  Log.i("finalAnswer",result);
	        	  return result;
        }
	}
    catch (ClientProtocolException e)
    {
    
     	Log.e("errorhai",e.getMessage());
 	}
    catch (IOException e) 
    {
    	Log.e("errorhai",e.getMessage());
    }
		return "";		
	}
	
	
	
	private static String convertStreamToString(InputStream is) {
	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	            
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	 }	   
	       
	

}
