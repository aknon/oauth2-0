<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">


<script src="/restful/scripts/oauth.js" type="text/javascript"></script>

<title>Actual Client App</title>
</head>
<body>

<div align="center">
<br><br>
<img src="/restful/images/authenticate.jpg" alt="Great Work" width="304" height="228">
<br><br>
<font size="4" face="verdana" color="purple"><b><%= request.getAttribute("client_name") %> </b> </font><font size="3" face="verdana" color="green"><b>would like to Access Some of protected Resource</b></font>
<br><font size="3" face="verdana" color="blue"><b>Your Resources that will be Accessed :</b></font><font size="4" face="verdana" color="purple"> <b><i> <%= request.getAttribute("scope") %> </i></b></font>
<br><br>
<font size="2" face="verdana" color="black">Enter your Credentials and click 'Allow Access' to <b>Allow Access</b> or Click 'Deny' to <b>Deny Access</b></font>
<br><br>
</div>

<form name="authenticate_app" action="/restful/oauthlogin" method="POST">
<div align="center">
<br>
<font size="2" face="verdana" color="black">Username/Email: </font><input type="text" name="user_name"><br>
<font size="2" face="verdana" color="black">Password: </font><input type="text" name="password">
<br><input type="hidden" id="login" name="login" value="login" >

<br><br>
<ul>
<li>
<input type="submit" value="Allow Access"  onclick="javascriopt:submitThis(this)"/>
<input type="submit" value="Deny" onclick="javascriopt:submitThis(this)"/>
</li>
</ul>
<input type="hidden" id="request_id" name="request_id" value="" >
<input type="hidden" id="scope" name="scope" value="" >
<input type="hidden" id="access_allowed" name="access_allowed" value="" >
</div>
</form>

<div align="center">
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

<script>



function submitThis(obj) {
	var access_allowed = "false";
	if ( obj.value == "Allow Access") {
		access_allowed ="true";
	}
	
	var request_id = document.getElementById("request_id");
	request_id.value = '<%=request.getAttribute( "request_id" )%>';
	
	document.getElementById("scope").value = '<%=request.getAttribute( "scope" )%>';
	
	document.getElementById("access_allowed").value = access_allowed;
}


</script>

</body>
</html>