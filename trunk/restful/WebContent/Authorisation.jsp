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
<script src="/restful/scripts/oauth.js" type="text/javascript"></script>
</head>
<body>

<script type="text/JavaScript">
document.write("Funy Jsp ! <br>");

//var urlStr = "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html?client_id=client123&state=clientstate&url=aljghaljg?a=b#access_token=aaga";
var urlStr = '<%= request.getAttribute("redirect_url") %>';
	//var urlStr = window.location.href;


document.write(urlStr);
document.write(location.host);


var queryStr = get_query(urlStr);
var queryParams = get_queryMap(queryStr);



var urlFragment = get_urlfragment(urlStr);
var fragment = get_fragmentMap(urlFragment);

var host = get_host();
document.write( "<br> Host :" + host );
set_cookie("access_token", fragment["access_token"], 1, host);
set_cookie("client_id", queryParams["client_id"], 1, host);
set_cookie("state", queryParams["state"], 1, host);

//document.write( "<br>" + get_cookie("client_id") );
//document.write( "<br>" + get_cookie("state") );
//document.write( "<br>" + get_cookie("access_token") );


var originalUrl = get_url(urlStr);

document.write("<br>Redirecting to : " + originalUrl);

relocate(originalUrl);
</script>
</body>
</html>
