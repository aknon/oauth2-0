
//relocate(originalUrl);

function doOnLoad1() {

	var urlStr = "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html?client_id=client123&state=clientstate&url=aljghaljg?a=b#access_token=aaga"; 
	
	var queryStr = get_query(urlStr);
	var queryParams = get_queryMap(queryStr);
	document.write( "<br> all query params : " + queryParams );


	var urlFragment = get_urlfragment(urlStr);
	var fragment = get_fragmentMap(urlFragment);

	set_cookie("access_token", fragment["access_token"], 1, "localhost");
	set_cookie("client_id", queryParams["client_id"], 1, "localhost");
	set_cookie("state", queryParams["state"], 1, "localhost");

}

function get_query(urlStr) {
	var hashIndex = urlStr.indexOf("#", 0 );
	var query_index = urlStr.indexOf("?", 0);
	return urlStr.substring(query_index+1, hashIndex);
	
}

// returns Url Fragment
function get_urlfragment(urlStr) {
	var hashIndex = urlStr.indexOf("#", 0 );
	if ( hashIndex == -1)
		{
		return null;
		}
	var urlFragment = urlStr.substring(hashIndex+1, urlStr.length);
	return urlFragment;
}

// Returns Url String without the Url Fragment
function get_url(urlStr) {
	var hashIndex = urlStr.indexOf("#", 0 );
	if ( hashIndex == -1) {
		return urlStr;
	}
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
  // alert("get the str input :" + str);
	var fragmentItemStrings = str.split('&');
	
	for (var i in fragmentItemStrings) {
	  var fragmentItem = fragmentItemStrings[i].split('=');
	  //alert( "nth query param :" + fragmentItemStrings[i] );
	 // alert( "= separated :" + fragmentItem );
	  if (fragmentItem.length !== 2) {
	    continue;
	  }
	  fragment[fragmentItem[0]] = fragmentItem[1];
	  //alert(fragment["client_id"]);
	  //alert(fragmentItem[0] + ":" + fragmentItem[1]);
	}
	return fragment;
}

function get_host() {
	var host = location.host;
	var index = host.indexOf(":");
	if ( index == -1 )
		return host;
	var splitStr = host.split(":");
	return splitStr[0];
		
}

// returns map of query params
function get_queryMap(queryStr) {
	
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
    // alert( " cookie_value:" + cookie_value );
     //alert( document.cookie );
     
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

function get_submit_value() {
	var ret = "client_id=" + get_cookie("client_id") + "&state=" + get_cookie("state") + "&access_token=" + get_cookie("access_token");
	return ret;
}

function get_client_id() {
	return get_cookie("client_id");
}

function get_state() {
	return get_cookie("state");
}

function get_access_token() {
	return get_cookie("access_token");
}


