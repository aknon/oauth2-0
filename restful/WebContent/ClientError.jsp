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
<br><br><img src="/restful/images/register-error.png" alt="Validation Failed" width="304" height="128">
<br>
<font size="4" face="verdana" color="red"><b>Validation Failed</b></font>
<br>
<br>

<font size="3" face="verdana" color="green"><b>Reason for Denial : </b></font><font size="3" face="verdana" color="red"><b><i><%= request.getAttribute("error_description") %></i></b></font>
</div>
</body>
</html>