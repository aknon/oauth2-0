<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">


<script src="/restful-js/scripts/oauth.js" type="text/javascript"></script>
<script src="scripts/jquery-2.0.0.js" type="text/javascript"></script>

<script type="text/javascript">

function setBeforeSubmit(obj) {
	
	//alert("setting values before submit");
	var access_allowed = "false";
	if ( obj == "allow") {
		access_allowed ="true";
	}
	
	var request_id = document.getElementById("request_id");
	request_id.value = '<%=request.getAttribute("request_id")%>';
	
	document.getElementById("scope").value = '<%=request.getAttribute("scope")%>';
	
	document.getElementById("access_allowed").value = access_allowed;
}

function handleLoginFailed(data) {
	var fragment = get_map(data);
	var errMsg = fragment["error_description"];
	$('div.login-error').html('');
	$('div.login-error').append( errMsg ).css( { "font-size":"3", "font-family":"verdana", "color":"red", "font-style": "italic", "font-weight":"bolder"   } );
}


function submitForm() {

	var frm = $('#authenticate_app');
	var action = "/restful-js/oauthlogin";
	
	// prints each form input value 
	//var values = {};
	////$.each($('#authenticate_app').serializeArray(), function(i, field) {
	  //  values[field.name] = field.value;
	  //  alert( field.name + ":" + field.value);
	//});
	
	$.ajax({
	         type: frm.attr('method'),
	         url: action,
	         data: frm.serialize(),
	         success: function (data) {
	        	 //alert('response text' + data);
	            
	             if ( data.indexOf("http") == -1 ) {
	            	  	   handleLoginFailed(data);
	             }else {
	          	   window.opener.handleRedirectUrl(data, window);   
	             }   
	              
	           },
	           fail: function(data,error) {
	        	   alert("error: " + error);
	           }
	         

	     });

	     return false;

}

</script>

<script type="text/javascript">

$(document).ready(function() {
	
	$("form").submit(submitForm);			
	
});
</script>



<title>Actual Client App</title>
</head>
<body id="scriptbody" >

	<div align="center" id="endpoint" class="endpoint">
		<br>
		<br> <img src="/restful-js/images/authenticate.jpg"
			alt="Great Work" width="304" height="228"> <br>
		<br> <font size="4" face="verdana" color="brown"><b>Authorisation
				EndPoint on the Authorisation Server</b></font> <br>
		<br> <br>
		<br> <font size="4" face="verdana" color="purple"><b><%=request.getAttribute("client_name")%>
		</b> </font><font size="3" face="verdana" color="green"><b>would like
				to Access Some of your protected Resources</b></font> <br>
		<font size="3" face="verdana" color="blue"><b>Your
				Resources that will be Accessed :</b></font><font size="4" face="verdana"
			color="purple"> <b><i> <%=request.getAttribute("scope")%>
			</i></b></font> <br>
		<br> 
		
		<font size="3" face="verdana" color="black">Enter
			your Credentials and click 'Allow Access' to <b>Allow Access</b> or
			Click 'Deny' to <b>Deny Access</b>
		</font> <br>
		<br>
	</div>

<div align="center" id="form" class="form">
	<form name="authenticate_app" id="authenticate_app"	 action="/restful-js/oauthlogin" method="POST" target="_parent" >
		
			<br> <font size="2" face="verdana" color="black"><b>Username/Email:
			</b></font><input type="text" name="user_name"><br> <font size="2"
				face="verdana" color="black"><b>Password: </b></font><input
				type="text" name="password"> <br>
				<br>
			<ul>
				<li><input type="submit" id="allow_access" name="Allow Access" value="Allow Access" onclick='setBeforeSubmit("allow")' /> 
				<input type="submit" id="deny"  name="Deny" value="Deny"  onclick='setBeforeSubmit("deny")'/>
				</li>
			</ul>
			
			<input type="hidden" id="request_id" name="request_id" value="">
			<input type="hidden" id="scope" name="scope" value=""> <input
				type="hidden" id="access_allowed" name="access_allowed" value="">
		
	</form>
	</div>
	
	<br><div id="login-error" class="login-error" align="center"></div>
	

	<div align="center" id="info" class="info">
		<br>
		<br> <font size="3" face="verdana" color="brown"><b>Your
				username and password shall be verified by the Authorisaton Server(
				you must trust this ) <br> On Successful authorisation server,
				will return a html page with some javascript code and a Redirect url
				<br>This Redirect Url has the Access Token in the Url hash
				fragment <br>
			<br> This script will extract the hash component of the Redirect
				Url as 'Access Token' save with the Browser's user-agent <br>
				This script will then redirect the Broswer's user-agent to the new
				Redirect Url which does not contain the hash fragment anymore <br>
				So 'Access Token' is issued right during authorsation and stored
				with the user-agent for subsequent requests
		</b></font>
	</div>

	<div align="center">
		<br>
		<br>
		<a target="_blank"
			href="http://restful-js-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See
			my Complete Tutotial For OAuth 2.0</a> <br>Thank You
	</div>


</body>
</html>