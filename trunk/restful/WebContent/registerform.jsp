<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">


<script src="scripts/jquery-2.0.0.js" type="text/javascript"></script>
<script src="/restful/scripts/oauth.js" type="text/javascript"></script>

<title>Client App Registration</title>


<script type="text/javascript">



function submitForm() {

	var frm = $('#register_form');
	var action = "/restful/register";
	
	// prints each form input value 
	//var values = {};
	//$.each($('#register_form').serializeArray(), function(i, field) {
	  // values[field.name] = field.value;
	    //alert( field.name + ":" + field.value);
	//});
	
	
	$.ajax({
	         type: frm.attr('method'),
	         url: action,
	         data: frm.serialize(),
	         success: function (data) {
	        	 //  $("#register_info").hide();
	           // $("#register_app").hide();
	           $("#div-all").html('');
	            
	            $("#register_response").html(data);
	              
	           },
	           fail: function(data,error) {
	        	   alert("error: " + error);
	           }
	         

	     });

	     return false;

}

</script>

<script type="text/javascript">

$(document).ready(function() {
	
	$('input[name=app_name]').val('<%= request.getAttribute("app_name")%>');
	$('input[name=home_uri]').val('<%= request.getAttribute("home_uri")%>');
	$('input[name=redirect_uri]').val('<%= request.getAttribute("redirect_uri")%>');
	
	$("form").submit(submitForm);			
	
});

</script>
</head>

<body>



<div align="center" id="register_img" class="register_img" >
<br><img src="/restful/images/register.png" alt="Great Work" width="504" height="328">
</div>


<div align="center" id="register_response" class="register_response" >
</div>


<div align="center" id="div-all" class="div-all">
<br><br><br>

<div align="center" id="register_info" class="register_info" >
<font size="3" face="verdana" color="green"><b>Register your Client App</b></font>
<br><font size="3" face="verdana" color="purple">This Registration will Issues a ClientId and a Client Key</font>
<br><br>
</div>

<div align="center" id="register_app" class="register_app">
<form id="register_form" name="register_form" action="/restful/register" method="POST" target="_parent">
<br>
<font size="2" face="verdana" color="black"><b>App name: </b></font><input type="text" name="app_name" id="app_name" value="" size="50"><br>
<font size="2" face="verdana" color="black"><b>Home URI: </b></font><input type="text" name="home_uri" id="home_uri" value="" size="50"><br>
<font size="2" face="verdana" color="black"><b>Redirect URI: </b></font><input type="text" name="redirect_uri" id="redirect_uri" value="" size="50">
<br><br>
<input type="submit" value="Register"  />

</form>
</div>

<div align="center">
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

</div>

</body>
</html>
