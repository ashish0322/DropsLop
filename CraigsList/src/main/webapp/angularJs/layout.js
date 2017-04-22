angular.module("layoutService",[])

	.controller("layoutController",function($scope,$http,ApiService,ngDialog,$rootScope,$localStorage,userPersistenceService,$location){
	
		$rootScope.authenticated = false;
		$rootScope.onlyAdmin = false;
		$rootScope.onlyUser = true;
		$rootScope.search = false;
		$scope.selectedProduct = {};
		 $scope.images = [ 1, 2, 3, 4, 5, 6, 7];

		$rootScope.navBarClass  = "navbar navbar-inverse navbar-fixed-top";
		$rootScope.navBarClass1 = "dropdown navbar-inverse";
		$rootScope.footerNav = "navbar navbar-inverse";
		
		$rootScope.homePage = "#/";
		
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
			 $rootScope.wrapper = "page-wrapper";
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
					
					var products = [];
					var products2=[];
					for(var i=0 ;i<=3; i++){
						products.push(data[i]);
					}
					for(i=4;i<=7;i++){
						products2.push(data[i]);
					}
					$scope.products = products;
					$scope.products2 = products2;
					console.log("$scope.produts",$scope.products );
					console.log("$scope.produts2",$scope.products2 );
				}else{
					
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
		
		}
		
		$scope.loadAdss = function($rootScope,$localStorage){	
			ApiService.call("/getApprovedAds1")
				.success(function(data,status){
					if(data !=null){
						var products1 = [];
						var products3=[];
						for(var i=0 ;i<=2; i++){
							products1.push(data[i]);
						}
						for(i=3;i<=5;i++){
							products3.push(data[i]);
						}
						$scope.products1 = products1;
						$scope.products3 = products3;
//						console.log("Get next top 4 ads",data);
//						$scope.products1 = data;
						
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
					$rootScope.wrapper = "";
					$rootScope.homePage = "#/";
					$location.path('/');
					
					userPersistenceService.clearCookieData();

				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
		})
		

		
		
		
	
		
		
		

		
		