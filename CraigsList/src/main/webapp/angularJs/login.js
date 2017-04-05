angular.module("loginServivce",[])


	.factory('authUsers',function($http, $rootScope, $location, $localStorage, $log, ApiService, $idle) {
		
		function clearUserData() {
			$localStorage.$reset(); // needed for Google Chrome
			// following needed for IE9
			for (key in localStorage) {
				delete localStorage[key];
			}
		}
		
		return {
		timeout : function() {
			if (this.isLoggedIn) {
				if ($localStorage.operatorPk) {
					ApiService.call('/timeout').success(
							function(data, status) {
								$rootScope.loggedUser = 0;
								clearUserData();
							}).error(function(data, status) {
						ApiService.exception(data, status);
					});
				}
				$location.path('/logout');
			}
		},
		isLoggedIn : function() {
			if ($rootScope.loggedUser !== undefined) {
				return $rootScope.loggedUser;
			} else {
				if ($localStorage.logged_user !== undefined) {
					return $localStorage.logged_user;
				} else {
					return 0;
				}
			}
		}
	}
		
	})

	.controller("loginController",function($scope,$http,ApiService,ngDialog,$location,$localStorage,$rootScope){
		
	//	********************************************************* Login Function *********************************************************
		$scope.login = function(user){
			console.log("inside login function");
		
			var user =	{
					email:$scope.user.email,
					password:$scope.user.password
				}
			ApiService.post("/login",user)
				.success(function(data,status){
					
					
					if(data.firstName != null){
					$rootScope.authenticated = true;
					$localStorage.useradmin="admin";
					$localStorage.showdropdown="false";
					
					$rootScope.useradmin=$localStorage.useradmin;
					$rootScope.showdropdown=$localStorage.showdropdown;
					
					$localStorage.logged_user = 1;
					$localStorage.user = data;
					$localStorage.userPk = user.userId;
					$localStorage.displayName = data.displayName;
					$rootScope.displayName = $localStorage.displayName;
					$rootScope.loggedUser = $localStorage.logged_user;
						console.log("Inside get user login",data);
						ngDialog.close();
					
						$location.path("/admin/adminDashboard");
						document.getElementById("userCheck").style.display = 'none';
						
						
					}
					else{
						$scope.userCheck = "Invalid Login";
						console.log("User login failed",data);
						document.getElementById("userCheck").style.display = 'block';
						console.log("$localStorage.showLogin",$localStorage.showLogin);
						
					}
						
				})
				.error(function(data,status){
						ApiService.exception(data,status);
				});
		}

	});
	