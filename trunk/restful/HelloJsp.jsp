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
</head>
<body>
	<script type="text/JavaScript">
document.write("Funy Jsp ! <br>");


var urlStr = "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html?client_id=client123&state=clientstate&url=aljghaljg?a=b#access_token=aaga"; 
	//var urlStr = window.location.href;


document.write(urlStr);


var queryStr = get_query(urlStr);
var queryParams = get_queryMap(queryStr);


var urlFragment = get_urlfragment(urlStr);
var fragment = get_fragmentMap(urlFragment);

set_cookie("access_token", fragment["access_token"], 1, "localhost");
set_cookie("client_id", queryParams["client_id"], 1, "localhost");
set_cookie("state", queryParams["state"], 1, "localhost");

var fetchedCookie = get_cookie("client_id");


var originalUrl = get_url(urlStr);
//relocate(originalUrl);


function get_query(urlStr) {
	var hashIndex = urlStr.indexOf("#", 0 );
	var query_index = urlStr.indexOf("?", 0);
	return urlStr.substring(query_index+1, hashIndex);
	
}

// returns Url Fragment
function get_urlfragment(urlStr) {
	var hashIndex = urlStr.indexOf("#", 0 );
	var urlFragment = urlStr.substring(hashIndex+1, urlStr.length);
	return urlFragment;
}

// Returns Url String without the Url Fragment
function get_url(urlStr) {
	var hashIndex = urlStr.indexOf("#", 0 );
	var originalUrl = urlStr.substring(0,  hashIndex);
	return originalUrl;
}

// redirect
function relocate(originalUrl){
	window.location = originalUrl;
}

// map of the fragment params
function get_fragmentMap(urlFragment) {
	
	//alert( "Funct fragt..." + urlFragment );
	return get_map(urlFragment);
	
}

// generic map construction of a '&' separated string
function get_map(str) {
	
   var fragment = {};
	var fragmentItemStrings = str.split('&');
	for (var i in fragmentItemStrings) {
	  var fragmentItem = fragmentItemStrings[i].split('=');
	  if (fragmentItem.length !== 2) {
	    continue;
	  }
	  fragment[fragmentItem[0]] = fragmentItem[1];
	}
	return fragment;
}

// returns map of query params
function get_queryMap(queryStr) {
	
	//alert( "Query Map..." + queryStr );
	return get_map(queryStr);
}

// sets cookie
function set_cookie ( cookie_name, cookie_value,
    lifespan_in_days, valid_domain )
{
    // http://www.thesitewizard.com/javascripts/cookies.shtml
    var domain_string = (valid_domain != null ) ?
                       ("; domain=" + valid_domain) : '' ;
   coo  = cookie_name +
                       "=" + encodeURIComponent( cookie_value ) +
                       "; max-age=" + 60 * 60 *
                       24 * lifespan_in_days +
                       "; path=/" + domain_string ;
     // alert( coo );                 
     document.cookie = coo;
     
}


// returns cookie with the cookie_name. Strange cookies can be comma separated or semi-colon seaprated or semi-colon space seaprated
function get_cookie ( cookie_name )
{
var thisCookie = document.cookie.split(";");

var index = thisCookie.indexOf(",");
if ( index != -1 ) {
	thisCookie = document.cookie.split(",");
}

	for (var i=0; i<thisCookie.length; i++) {
		var namevaluePair = thisCookie[i].split("=");
		var name = namevaluePair[0];
		var value= namevaluePair[1];
		
			
		if (name.trim() == cookie_name ) {
			return value;
		}
	}
	return '';
}

</script>
</body>
</html>