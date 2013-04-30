<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="/restful-js/scripts/oauth.js" type="text/javascript"></script>
<script src="scripts/jquery-2.0.0.js"></script>



</head>
<body>

<div id="1" align="center">
<img src="/restful-js/images/hurray.jpg" alt="Great Work" width="304" height="228">
<br><br>
<font size="5" face="verdana" color="orange"><b>HURRAY ! </b></font><font size="3" face="verdana" color="green"><b>You have accessed this Client App using </b></font>
<font size="4" face="verdana" color="green"><b><i>Implicit Grant FLow</i></b></font>
<br>

<div id="scope-id" class="scope-id">
<font size="3" face="verdana" color="purple"><b>Your Resources Accessed: </b></font>
</div>


<script type="text/javascript"  >
	//$('div.scope-id')( { "font-size":"300%", "font-family":"verdana", "color":"purple", "font-style": "italic", "font-weight":"bolder"   } ).html("Scope");
	$('div.scope-id').append('<%= request.getAttribute("scope") %>').css( { "font-size":"3", "font-family":"verdana", "color":"purple", "font-style": "italic", "font-weight":"bolder"   } );
</script>






<br><br>
</div>

</body>
</html>


