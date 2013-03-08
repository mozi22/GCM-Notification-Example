<?php
/*
	Class to send push notifications using Google Cloud Messaging for Android

	Example usage
	-----------------------
	$an = new GCMPushMessage($apiKey);
	$an->setDevices($devices);
	$response = $an->send($message);
	-----------------------
	
	$apiKey Your GCM api key
	$devices An array or string of registered device tokens
	$message The mesasge you want to push out

	@author Matt Grundy

	Adapted from the code available at:
	http://stackoverflow.com/questions/11242743/gcm-with-php-google-cloud-messaging

*/
class GCMPushMessage {

	var $url = 'http://android.googleapis.com/gcm/send';
	var $serverApiKey = "SERVER_KEY";
	var $devices = array();
	

	function setDevices($deviceIds){
	
		if(is_array($deviceIds)){
			$this->devices = $deviceIds;
		} else {
			$this->devices = array($deviceIds);
		}
	
	}

	function send($message){
		
		if(!is_array($this->devices) || count($this->devices) == 0){
			$this->error("No devices set");
		}
		
		if(strlen($this->serverApiKey) < 8){
			$this->error("Server API Key not set");
		}
		
		$fields = array(
			'registration_ids'  => $this->devices,
			'data'              => array( "msg" => $message ),
		);
		
		$headers = array( 
			'Authorization: key=' . $this->serverApiKey,
			'Content-Type: application/json'
		);

		// Open connection
		$ch = curl_init();
		
		// Set the url, number of POST vars, POST data
		curl_setopt( $ch, CURLOPT_URL, $this->url );
		
		curl_setopt( $ch, CURLOPT_POST, true );
		curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers);
		curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
		
		curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $fields ) );
		
		// Execute post
		$result = curl_exec($ch);
		
		$this->StripResponseFromGCM(json_decode($result));

		// Close connection
		curl_close($ch);
		
		return $result;
	}
	
	function error($msg){
		echo "Android send notification failed with error:";
		echo "\t" . $msg;
		exit(1);
	}
	
	
	function StripResponseFromGCM($response)
	{
		//canonicalID's are the 
		if($response->failure == 0 && $response->canonical_ids == 0)
			return;
			
			
		for($i=0;$i<sizeof($response->results);$i++)
		{
			if(isset($response->results[$i]->registration_id))	//if new registrationID is sent as canonicalID
			{
					//update this registrationID in the database
			}
			else if($response->results[$i]->error == "Unavailable")
			{
				// user with index == $i is unavailable
			}
			else if($response->results[$i]->error == "InvalidRegistration")
			{
				// user with index == $i has InvalidRegistration ID
			}
			else if($response->results[$i]->error == "NotRegistered")
			{
				// user with index == $i is not registered				
			}
		}	
	}
}

	//Insert this id into the database. 
//	$id = $_POST['gcmID'];
	//Just showing you how to send a message using this class to a certain device(in this case my own device).	
	
	$msg = array ( 'data' => array('msg'=>'just a simple message'));
	
	
	$obj = new GCMPushMessage();
	$obj->setDevices($_POST['gcmID']);

	$obj->send($msg);
	
?>
