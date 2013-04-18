<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<title>Hello jsp ! Funny !!</title>


<script src="/restful/scripts/oauth.js" type="text/javascript"></script>


</head>
<body >

<div align="center">
<img src="/restful/images/redirect.jpg" alt="Great Work" width="304" height="228">
<br><br>
<b>Redirected EnPoint on the ClienApp</b>
<br><br>
</div>
	

<form name="access_app" action="/restful/clientapp" method="POST">
<div align="center">
<br><br>
<b>Click on the Button to access the app. This html page<br>has a javascript which shall carry the access token along</b>
<br><br><i>Access_token accessed by this page:</i><b><script type="text/javascript">document.write(get_access_token());</script></b>
<br><br><input type="submit" value="Access My App" >
<br><input type="hidden" id="client_id" name="client_id" value="" >
<br><input type="hidden" id="state" name="state" value="" >
<br><input type="hidden" id="access_token" name="access_token" value="" >

<br><br><a href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You

</div>
</form>

<script>
//document.write( "hi ");
//document.write( "<br>" + get_client_id() );
//document.write( "<br>" + get_state() );
//document.write( "<br>" + get_access_token() );

var clientId = document.getElementById('client_id');
clientId.value = get_client_id();

var accessToken = document.getElementById('access_token');
accessToken.value = get_access_token();

var state = document.getElementById('state');
state.value = get_state();




</script>

</body>
</html>