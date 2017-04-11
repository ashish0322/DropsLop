angular.module("layoutService",[])

	.controller("layoutController",function($scope,$http,ApiService,ngDialog,$rootScope,$localStorage,userPersistenceService){
	
		$rootScope.authenticated = false;
		$scope.navBarClass  = "navbar navbar-inverse navbar-fixed-top";
		
		var username = "";
		
		username = userPersistenceService.getCookieData();
		console.log("username",username);
		if(username == "" || angular.isUndefined(username)){
		console.log("Inside logged user zero");

		$rootScope.authenticated = true;
		$rootScope.categories = true;
		
		}
		else{
			//retrieve user session from userPersistenceService
			
			var userData = userPersistenceService.getUserSessionData();
			
			console.log("retrieve user session from userPersistenceService",userData[1]);
			
			$rootScope.data = userData;
			$rootScope.displayName = userData.displayName;
			$rootScope.email = userData.email;
		}
		
		
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
		
		
		$rootScope.categoriesList = {};

		$scope.loadCategoriesList = function(){
			console.log("inside loadCategoriesList");
		ApiService.call("/getCategories")
			.success(function(data,status){
				if(data !=null){
					console.log("Get all categories",data)
					
					$rootScope.categoriesList = data;
				}else{
					
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
		}
		
		$scope.loadCategoriesList();
		
		})

	.controller("logoutController",function(ApiService,$location,$rootScope,userPersistenceService){
			console.log(" logout>>>");
			ApiService.call("/logout")
				.success(function(data,status){
					console.log("Logout Success");
					$location.path('/');
					userPersistenceService.clearCookieData();

				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
		})
		
		
		