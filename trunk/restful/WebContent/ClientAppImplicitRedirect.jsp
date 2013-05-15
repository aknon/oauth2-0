<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<title>Hello jsp ! Funny !!</title>


<script src="scripts/oauth.js" type="text/javascript"></script>
<script src="scripts/jquery-2.0.0.js"></script>

<script>

function doAct() {
	var appUrl = "/restful/clientapp";
	postAppUrl(appUrl);
}

function postAppUrl(appUrl) {

	var url = "http://" + get_host() + ":8080"+ appUrl;
		
	$.post( url, 
			{ 
		    
		    "client_id" : get_client_id(), 
		    "access_token" : get_access_token(),
		    "state" : get_state(),
		    "expires_in" : get_expires_in(),
		    "scope" : get_scope(),
		    "error" : get_error(),
		    "error_description" : get_error_description()
		    	
			} )
			.done( function(data) {
				//$('#toHide').hide();
				//$('#toReplace').html(data);
				
				//var itemtoReplaceContentOf = $('#toHide');
             // itemtoReplaceContentOf.replaceWith(data);


				//document.getElementById('#toHide').innerHtml(data);
				//$('.toHide').hide();
				//$('.toReplace').replaceWith(data);
//				$('.toHide').innerHtml('');
				$('.toHide').replaceWith(data);
				
			});
			
			
}

</script>

<script type="text/javascript">
//document.write( "hi ");
//document.write( "<br>" + get_client_id() );
//document.write( "<br>" + get_state() );
//document.write( "<br>" + get_access_token() );
	$(document).ready(function() {
		
		//$("button").click(loadMyApp());
		$("#access_my_app").click( doAct);
		
		

	});
</script>




</head>
<body >

<div id="toReplace"  class="toReplace" align="center"></div>


<div id="toHide"  class="toHide" align="center">
<img src="images/redirect.jpg" alt="Great Work" width="304" height="228">
<br><br>
<font size="4" face="verdana" color="brown"><b>Redirected EndPoint on the Client App</b></font>
<br><br>


	
<% if ( request.getAttribute("error") != null ) { %>

<font size="5" face="verdana" color="red"><b>Access Denied or Error Accessing App</b></font>
<br>
<font size="3" face="verdana" color="blue"><br>Reason For Error : <b><i><%= request.getAttribute("error_description") %></i></b></font>
<br>


<div id="footer" class="footer" align="center" >
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

<% } else  { %>



<br><br>
<div id="app_button" class="app_button" align="center" >
<font size="3" face="verdana" color="blue"><b>Click on the Button to access the app<br>This html page has embedded javascript which shall carry the access token along</b></font>
<br><br>
<font size="2" face="verdana" color="black"><b><i>Access_token accessed by this page: </i></b><b><script type="text/javascript">document.write(get_access_token());</script></b></font>
<br><br><button type="button" id="access_my_app" class="access_my_app" >Access My App</button>

</div>


<div id="footer" class="footer" align="center" >
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

</div>
<% } %>

</body>
</html>