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

	.controller("loginController",function($scope,$http,ApiService,ngDialog,$location,$localStorage,$rootScope,userPersistenceService){
		
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
					if(data.firstName != null && data.active == true){
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
					
					var userDta = {
							userId:data.userId,
							firstName:data.firstName,
							lastName:data.lastName,
							email:data.email,
							userInfo:data.userInfo,
							displayName:data.displayName
					}
					
					userPersistenceService.storeUserSession(userDta);
					$rootScope.displayName = username;
					$rootScope.email = data.email;
					$rootScope.lastLogin = data.userInfo.lastLoginDate;
					$localStorage.user = data;
						console.log("Inside get user login",data);
						ngDialog.close();
						
						// WebSocket Initialization
						//	var taskSocket = new WebSocket("ws://localhost:8888/websocket");
						var serviceUrl = ApiService.url.replace("http","");
						var url = "ws" + serviceUrl + "/dropslop/websocket"
						console.log("url is",url);
						//Web Socket Initialization 
						//'ws:// is defined as ServerEndpoint in TaskWebSoketHandler.java'
						var taskSocket = new WebSocket(url);
						
						
						taskSocket.onmessage = function(message) {
							console.log("Inside on message");
					        $scope.tasks = JSON.parse(message.data);
					        $scope.$apply();        
					    };
					    $rootScope.wrapper = "page-wrapper";
						document.getElementById("userCheck").style.display = 'none';
						if(data.userInfo.role == 'admin'){
						$rootScope.navBarClass = "navbar navbar-default";
						$rootScope.navBarClass1 ="dropdown navbar-default";
						$rootScope.footerNav = "navbar navbar-default";
						$rootScope.onlyAdmin = false;
						$location.path("/admin/adminDashboard");
						}
						else{
							$rootScope.authenticated = false;
							$rootScope.onlyAdmin = true;
							$rootScope.onlyUser = false;
							$location.path("/user/userDashboard");
						}
						
					}
					else if(data.firstName != null && data.active == false){
						$scope.userCheck = "Your account is disabled";
						document.getElementById("userCheck").style.display = 'block';
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
	