<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<title>Access Denied</title>
</head>
<body>

<div align="center">
<br><br><img src="/restful/images/access_denied.jpg" alt="Access Denied to this App" width="304" height="228">
<br><br>
<font size="5" face="verdana" color="red"><b>Access Denied</b> 
<% if ( request.getAttribute("access_token") != null ) { %>
for Access Token: <b><i><%= request.getAttribute("access_token") %></i></b>
<% } %>
</font>
<br><br>
<font size="3" face="verdana" color="blue"><b>Reason for Denial : </b></font><font size="3" face="verdana" color="red"><b><i><%= request.getAttribute("error_message") %></i></b></font>
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>
</body>
</html>