<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">



<title>Actual Client App</title>
</head>
<body>

<div align="center">
<img src="/restful/images/hurray.jpg" alt="Great Work" width="304" height="228">
<br><br>
<font size="5" face="verdana" color="orange"><b>HURRAY ! </b></font><font size="3" face="verdana" color="green"><b>You have accessed this Client App using </b></font>
<font size="4" face="verdana" color="green"><b><i>Authorisation Code Grant Flow</i></b></font>
<br>
<font size="3" face="verdana" color="purple"><b>Your Resources Accessed: </b></font><font size="4" face="verdana" color="purple"><b><i><%= request.getAttribute("scope") %></i></b></font>
<br><br>
</div>

<form name="access_app" action="/restful/clientapp" method="POST">
<div align="center">
<br><br>
<font size="3" face="verdana" color="blue"><b>Click on the Button to access the app<br>This html page has embedded javascript which shall carry the access token along</b></font>
<br><br>
<font size="2" face="verdana" color="black"><b><i>Access_token accessed by this page: </i></b><b><%= request.getAttribute("access_token") %></b></font>
<br><br><input type="submit" value="Going further"  onclick="doThis()">
<br><input type="hidden" id="state" name="state" value="" >

</div>
</form>

<div align="center">
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

<script>
//document.write( "hi ");
//document.write( "<br>" + get_client_id() );
//document.write( "<br>" + get_state() );
//document.write( "<br>" + get_access_token() );

function doThis() {
var state = document.getElementById('state');
state.value = '<%= request.getAttribute("state") %>';

}



</script>

</body>
</html>