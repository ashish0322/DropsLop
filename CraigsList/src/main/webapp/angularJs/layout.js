angular.module("layoutService",[])

	.controller("layoutController",function($scope,$http,ApiService,ngDialog,$rootScope,$localStorage){
	
		$rootScope.authenticated = false;
		
		
		$scope.login = function(){
			console.log("Entering login Dialog");
			ngDialog.open({ 
				template: 'html/login.html',
				controller :  "loginController"
				});
		}	
		
		$scope.signUp = function(){
				console.log("Entering signUp Dialog");
				ngDialog.open({ 
					template: 'html/registration.html',
					controller :  "RegistrationController"
				});
		}
		
		})

	.controller("logoutController",function(ApiService,$location,$rootScope){
			console.log(" logout>>>");
			ApiService.call("/logout")
				.success(function(data,status){
					console.log("Logout Success");
					$location.path('/');
					$rootScope.showLogin="true";
					$rootScope.useradmin="none";
					$rootScope.userlogged="false";
					$rootScope.showdropdown="true";
					
				
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
		})