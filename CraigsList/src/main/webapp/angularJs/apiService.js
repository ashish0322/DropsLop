/**
 * 
 */
angular.module('services', [])

//Service for connect with the RestfulAPI
.service('ApiService', function($http, URL,APP, $log, $q, $rootScope) {
	function buildParamRequest (obj) {
		return '?'+$.param(obj);
	}
	
	return {
	    url : URL,
	    app : APP,
	    call: function(resource){
	      var request = this.url+this.app+resource;
	    //  logger = $log.getInstance('ApiService');
	    //  logger.debug('GET ' + request);
	      console.log("call ::: ",request);
	      return $http({method: 'GET', url: request, withCredentials:true});
	    },

	     callWithTimeout: function(resource, timeoutPromise){
				var request = this.url+this.app+resource;
			//	logger = $log.getInstance('ApiService');
			//	logger.debug('GET ' + request);
				return $http({method: 'GET', url: request, withCredentials:true, timeout: timeoutPromise});
			},
			multipleCall: function(){
				// Use: ApiService.multipleCall('route1', 'route2', 'routen')
				// TODO: Revise call. One internal call for call and multiple call?
				// Call with parameters?
				var resources = Array.prototype.slice.call(arguments);
	            var callList = [];
	            var resource;
	            for (var i = 0; i < resources.length; i++) {
	                resource = resources[i];
	                callList.push($http({
	                	method: 'GET',
	                	url: this.url+this.app+resource,
	                	withCredentials:true
	                }));
	            }
	            return $q.all(callList);
	        },
			delete: function(resource, message){
				var request = this.url+this.app+resource;
		//		logger = $log.getInstance('ApiService');
		//		logger.debug('DELETE ' + request);
				var opts = {method: 'DELETE',  url: request, 
					headers: {'Content-Type': 'application/json'}, withCredentials:true};
				if (message) opts.data = message;
				return $http(opts);
			},
			post: function(resource, message){
				var request = this.url+this.app+resource;
			//	logger = $log.getInstance('ApiService');
			//	logger.debug('POST ' + request + ' Data ' + angular.toJson(message));
				return $http({method: 'POST',  url: request, 
					headers: {'Content-Type': 'application/json'}, data: message, withCredentials:true });
			},
			postWithImage: function(resource, message){
				var request = this.url+this.app+resource;
			//	logger = $log.getInstance('ApiService');
			//	logger.debug('POST ' + request + ' Data ' + angular.toJson(message));
				return $http({method: 'POST',  url: request, 
					headers: {'Content-Type': undefined}, data: message, withCredentials:true });
			},
			put: function(resource, message){
				var request = this.url+this.app+resource;
			//	logger = $log.getInstance('ApiService');
		//		logger.debug('PUT ' + request + ' Data ' + angular.toJson(message));
				return $http({method: 'PUT',  url: request, 
					headers: {'Content-Type': 'application/json'}, data: message, withCredentials:true });
			},
			postForm: function(resource, message){
				var request = this.url+this.app+resource;
		//		logger = $log.getInstance('ApiService');
		//		logger.debug('FORM-POST ' + request + ' Data ' + message);
				return $http({method: 'POST',  url: request, 
					headers: {'Content-Type': 'application/x-www-form-urlencoded'}, data: encodeURIComponent(message), withCredentials:true});
			},
			exception: function(data, status){
		//		logger = $log.getInstance('ApiService');
				var code;
				if (data.data) {
					//If data is an object with code and data, then show the internal exception message stored in the data property
					code = data.code;
					data = data.data;
				}
				switch(status){
					case 401: //Unauthorized 
				//		logger.warn(data);
				//		NotifyService.warning('', data);
						if (code && code.indexOf('D-4') !== -1) {
				//			logger.error('Forcing login as there is no session in the server related to this browser session.');
							$rootScope.$broadcast('NoSessionStarted');
						}
						break;				
					case 403: //Forbidden (Business logic issue)
				//		logger.warn(data);
				//		NotifyService.warning('', data);
						break;				
					case 404: //Not found
				//		logger.warn(data);
				//		NotifyService.warning('', data);
						break;
					case 500: //Internal server error
				//		logger.error(data);
				//		NotifyService.error(status, data);
						break;
					default:
				//		logger.error(data);
						// Check if its buisness error
				//		if (code && code.charAt(0) === 'B')
				//			NotifyService.warning('', data);
				//		else
				//			NotifyService.error(status, data);
				}
			},
			log: function(level, message){
				var request = this.url+this.app+'/log/' + level;
				return $http({method: 'POST',  url: request, 
					headers: {'Content-Type': 'application/json'}, data: message, withCredentials:true });
			},
	   


	    };
});