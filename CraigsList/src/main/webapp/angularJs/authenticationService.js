angular.module("dropslop")

	.factory('AuthService', function($http, $cookies) {
		  return {
		    isAuthenticated: function() {
		      return !!$cookies.get('c_user');
		    },
		    getUser: function() {
		      return $http.get('whoami')
		    }
		  };
		});
	
	    

	