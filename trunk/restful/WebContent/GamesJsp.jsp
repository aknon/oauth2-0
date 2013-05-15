<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<title>My App. OAuth Tutorial starts here</title>

<script src="scripts/jquery-2.0.0.js"></script>
<script src="scripts/oauth.js" type="text/javascript"></script>


<script type="text/javascript">	
</script>

<script type="text/javascript">
	$(document).ready(function() {
		
		//$("button").click(loadMyApp());
		//$(".auth-redirect").click();
		
		$(".auth-redirect").click(function() {
			var url = "<%= request.getAttribute("authorisation_uri") %>";
			
			//loadMyApp_new();
			authoriseInNewWindow(url);
		});
		
		$(".auth-implicit-redirect").click(function() {
			var url = "<%= request.getAttribute("authorisation_uri_implicit") %>";
			
			//loadMyApp_new();
			authoriseInNewWindow(url);
		});
		
		

	});
	

</script>

<script type="text/javascript">

function login1() {
	var url = "<%= request.getAttribute("authorisation_uri") %>";
	alert(url );
	//loadMyApp_new();
	loadMyApp_new(url);
}

function loginImplicit() {
	loadMyApp_new('<%= request.getAttribute("authorisation_uri_implicit") %>');
}

</script>
</head>
<body>

<div align="center">
<br><br>
<font size="4" face="verdana" color="gray"><b>Demonstration for the OAuth 2.0 Protocol, Authorisation Code Grant Flow</b></font>
<br><br><br><br>
<a href='<%= request.getAttribute("authorisation_uri") %>' ><img src="/restful/images/myapp.jpg" alt="Great Work" width="304" height="228" /></a>
<br><br>

<ul>
<li><button id="auth-redirect" class="auth-redirect"><font size="2" face="verdana" color="blue">Authorisation Code Grant Flow</font></button>
&nbsp;&nbsp;&nbsp;<button id="auth-implicit-redirect" class="auth-implicit-redirect"><font size="2" face="verdana" color="blue">Implicit Grant Flow</font></button>
</ul>

<br>
<font size="3" face="verdana" color="blue"><b>This is my App as displayed on my SmartPhone</b>
<br>
<font size="3" face="verdana" color="green"><b>This App shall access my  protected resource </b></font>
<a target="#" href='<%= request.getAttribute("authorisation_uri") %>' ><font size="3" face="verdana" color="purple"><b><i><%= request.getAttribute("scope") %></i></b></font></a>
</font>

<br><br><font size="3" face="verdana" color="brown"><b>Clicking on the Above Icon will take me to the <i>'Authorisation End Point'</i></b></font>

<br><br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

</body>
</html>
