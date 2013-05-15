<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<!--  <meta http-equiv="refresh" content="15; URL=home.html"> -->

<title>Hello jsp ! Funny !!</title>
<script src="scripts/oauth.js" type="text/javascript"></script>
</head>
<body>

<input type="hidden" id="uri" value=""  name="uri"/>
<script type="text/JavaScript">
var redirect_uri = '<%= request.getAttribute("redirect_uri") %>';
document.getElementById("uri").value = redirect_uri;
window.opener.handleRedirectUrl(redirect_uri, this);

</script>
</body>
</html>
