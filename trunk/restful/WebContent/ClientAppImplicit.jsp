<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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
<body>

<div id="toHide"  class="toHide" align="center">

<div id="div-image" class="div-image" >
<img src="images/hurray.jpg" alt="Great Work" width="304" height="228">
<br><br>
<font size="5" face="verdana" color="orange"><b>HURRAY ! </b></font><font size="3" face="verdana" color="green"><b>You have accessed this Client App using </b></font>
<font size="4" face="verdana" color="green"><b><i>Implicit Grant FLow</i></b></font>
<br>
</div>

	
<% if ( request.getAttribute("error") != null ) { %>

<div id="div-errpr" class="div-error">

<font size="5" face="verdana" color="red"><b>Access Denied or Error Accessing App</b></font>
<br>
<font size="3" face="verdana" color="blue"><br>Reason For Error : <b><i><%= request.getAttribute("error_description") %></i></b></font>
<br>

<div id="footer" class="footer" align="center" >
<br><br><a target="_blank" href="http://restful-fundamentals.blogspot.in/2013/04/oauth-20-introducation.html">See my Complete Tutotial For OAuth 2.0</a>
<br>Thank You
</div>

</div>

<% } else  { %>

<br>

<div id="app_button" class="app_button" align="center" >

<div id="scope-id" class="scope-id">
<font size="3" face="verdana" color="purple"><b>Your Resources Accessed: </b></font>
<script type="text/javascript"  >
	//$('div.scope-id')( { "font-size":"300%", "font-family":"verdana", "color":"purple", "font-style": "italic", "font-weight":"bolder"   } ).html("Scope");
	$('div.scope-id').append('<%= request.getAttribute("scope") %>').css( { "font-size":"1", "font-family":"verdana", "color":"red", "font-style": "italic", "font-weight":"bolder"   } );
</script>
</div>

<div id="access-id" class="access-id">
<font size="3" face="verdana" color="purple"><b>Access_token accessed by this page: </b></font>
<script type="text/javascript"  >
	//$('div.scope-id')( { "font-size":"300%", "font-family":"verdana", "color":"purple", "font-style": "italic", "font-weight":"bolder"   } ).html("Scope");
	$('div.access-id').append( get_access_token() ).css( { "font-size":"1", "font-family":"verdana", "color":"red", "font-style": "italic", "font-weight":"bolder"   } );
</script>
</div>

<br><br>
<font size="2" face="verdana" color="blue"><b>Click on the Button to access the app</b></font>
<br>
<font size="3" face="verdana" color="purple"><b><i>This html page has embedded javascript which shall carry the access token along</i></b></font>
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


