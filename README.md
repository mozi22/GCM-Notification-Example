GCM-Notification-Example
========================

This is a complete android+PHP program that will help you understand how can you use **Google Cloud Messaging** 
to send notifications to a device.

<h2>What this Program will do </h2>
This program will register the android device to get a device registrationID from GCM servers and then send it
to the PHP script(GCMPushMessage.php) which in turn will send a notification back to this device using the sent 
registrationID.

<h2>How to use</h2>

<ul>
<li>Follow <a href="http://developer.android.com/google/gcm/gs.html">this</a> tutorial and get the 
<b>ProjectID</b> and <b>API KEY</b>.</li>

<li>Now open the android project and open <b>MainActivity</b> and replace the <b>SENDER_ID</b> there with your 
<b>ProjectID</b>.</li>

<li>Then open <b>GCMPushMessage.php</b> and replace the <b>SERVER_key</b> with your <b>API KEY</b>.</li>
<li>Goto Android <b>strings.xml</b> and replace the server_address with the general path of your server.
(For e.g <b>http://localhost</b>).
</li>
</ul>


<h2>How Does it work</h2>
When you will run the program the following operations occur.
> ##  Operations
>     Android (gcmTester)           PHP(GCMPushMessage.php)                     GCM Servers
>     |                                |          Get Device RegistrationID         |
>     |--------------------------------|------------------------------------------->|
>     |  Send RegistrationID to server |                                            |
>     |------------------------------->|                                            |
>     |                                |   Send Notification to this registrationID |
>     |                                |------------------------------------------->|
>     |                                |   Send Notification To Device with RegID   |
>     |<-------------------------------|--------------------------------------------|
>     |                                |                                            |

**Note :** The author of GCMPushMessage.php is Matt Grundy. I've slightly modified it to handle trouble situations also as
mentioned in the developer docs.


## License
```
Copyright 2013 Muazzam Ali

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

