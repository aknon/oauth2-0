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
<script src="/restful-js/scripts/oauth.js" type="text/javascript"></script>
<script src="scripts/jquery-2.0.0.js"></script>

<script>

function handleRedirect(urlStr) {
	
	alert("transfer");
	
	window.opener.handleRedirectUrl( urlStr, window );
}
</script>


</head>

<body>

<script type="text/javascript">

var urlStr = '<%= request.getAttribute("redirect_uri") %>';
handleRedirect(urlStr);

</script>


</body>
</html>
