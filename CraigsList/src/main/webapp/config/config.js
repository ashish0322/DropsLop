/**
 * 
 */

angular.module('config',[])

	.constant('URL',document.location.protocol+'//'+document.location.host)
		//protocol is https: 
	.constant('APP','/craigslist/api')