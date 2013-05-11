<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">

<title>Client App Registration Response</title>


</head>

<body>

<script>

</script>

<div align="center">
<br>
<font size="3" face="verdana" color="green"><b>This Application </b></font>
<font size="3" face="verdana" color="purple">'<b><i><%=request.getAttribute("app_name") %></i></b>'</font>
<font size="3" face="verdana" color="green"><b> has been Registered with Following Credentials </b></font>

<br><br>
<font size="3" face="verdana" color="red"><b>Client Id: </b></font>
<font size="3" face="verdana" color="purple"><b><i><%=request.getAttribute("client_id") %></i></b></font>

<br>
<font size="3" face="verdana" color="red"><b>Client Key: </b></font>
<font size="3" face="verdana" color="purple"><b><i><%=request.getAttribute("client_key") %></i></b></font>

</div>

<div align="center">
<br><br>
<a href="<%=request.getAttribute("start_uri") %>" ><font size="4" face="verdana" color="blue"><b>Try Out this App</b></font>
<font size="5" face="verdana" color="purple">'<b><i><%=request.getAttribute("app_name") %></i></b>'</font>
</a>
</div>

</body>
</html>
