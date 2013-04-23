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
<br><br><img src="/restful/images/authenticate.jpg" alt="Great Work" width="304" height="228">
<br><br>
<b>Authenticate Yourself with the Authentication Server. Your account Credentials for the Client App required</b>
<br><br>
</div>

<form name="login_app" action="/restful/loginapp" method="POST">
<div align="center">
<br><br>
Username/Email: <input type="text" name="user_name"><br>
Password: <input type="text" name="password">
<br><input type="hidden" id="login" name="login" value="login" >
<br><br><input type="submit" value="Log In" >
</div>
</form>

<div align="center">
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

</body>
</html>