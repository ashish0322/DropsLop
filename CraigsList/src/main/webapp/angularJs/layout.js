angular.module("layoutService",[])

	.controller("layoutController",function($scope,$http,ApiService,ngDialog,$rootScope,$localStorage,userPersistenceService){
	
		$rootScope.authenticated = false;
		$rootScope.onlyAdmin = false;
		$rootScope.onlyUser = true;
		
		$rootScope.navBarClass  = "navbar navbar-inverse navbar-fixed-top";
		$rootScope.navBarClass1 = "dropdown navbar-inverse";
		$rootScope.footerNav = "navbar navbar-inverse";
		
		var username = "";
		
		username = userPersistenceService.getCookieData();
		console.log("username",username);
		if(username == "" || angular.isUndefined(username)){
		console.log("Inside logged user zero");

		$rootScope.authenticated = true;
		$rootScope.categories = true;
		$rootScope.onlyAdmin = true;
		
		}
		else{
			//retrieve user session from userPersistenceService
			
			var userData = userPersistenceService.getUserSessionData();
			
			console.log("retrieve user session from userPersistenceService",userData[1]);
			
			$rootScope.data = userData;
			$rootScope.displayName = userData.displayName;
			$rootScope.email = userData.email;
			$rootScope.lastLogin = userData.userInfo.lastLoginDate;
			if(userData.userInfo.role == "admin"){
				$rootScope.navBarClass = "navbar navbar-default";
				$rootScope.navBarClass1 ="dropdown navbar-default";
				$rootScope.footerNav = "navbar navbar-default";
			}
			else{
				$rootScope.authenticated = false;
				$rootScope.onlyAdmin = true;
				$rootScope.onlyUser = false;
			}
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
		
		
//		********************************************************* Load Ads Function *********************************************************
		
		$scope.loadAds = function($rootScope,$localStorage){	
		ApiService.call("/getApprovedAds")
			.success(function(data,status){
				if(data !=null){
					console.log("Get top 4 ads",data);
					$scope.products = data;
					
				}else{
					
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
		
		}
		
		$scope.loadAdss = function($rootScope,$localStorage){	
			ApiService.call("/getApprovedAds")
				.success(function(data,status){
					if(data !=null){
						console.log("Get next top 4 ads",data);
						$scope.products1 = data;
						
					}else{
						
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			
			}
		
		$scope.loadAds();
		$scope.loadAdss();
		
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
		
		
		