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

	.controller("loginController",function($scope,$http,ApiService,ngDialog,$location,$localStorage,$rootScope,AuthenticationService,userPersistenceService){
		
	//	********************************************************* Login Function *********************************************************
		$scope.login = function(user){
			console.log("inside login function");
		
			var user =	{
					email:$scope.user.email,
					password:$scope.user.password
				}

//	            AuthenticationService.ClearCredentials();
//			AuthenticationService.Login(user,function(data)
//					{
//					if(data.firstName != null)
//					  {
					
			ApiService.post("/login",user)
			.success(function(data,status){
					if(data.firstName != null){
//					AuthenticationService.SetCredentials($scope.user.email, $scope.user.password);
					$scope.navBarClass  = "navbar navbar-inverse navbar-admin";
					$rootScope.categories = false;
					$rootScope.authenticated = false;
					$localStorage.useradmin="admin";
					$localStorage.showdropdown="false";
					
					$rootScope.useradmin=$localStorage.useradmin;
					$rootScope.showdropdown=$localStorage.showdropdown;
					
					userPersistenceService.setCookieData(data.displayName);
					username = userPersistenceService.getCookieData();
					console.log("userPersistenceService.getCookieData",username);
					
					userPersistenceService.storeUserSession(data);
					$rootScope.displayName = username;
					$rootScope.email = data.email;
					
					
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
		}

	});
	