angular.module("layoutService",[])

	.controller("layoutController",function($scope,$http,ApiService,ngDialog,$rootScope,$localStorage,userPersistenceService,$location){
	
		$rootScope.authenticated = false;
		$rootScope.onlyAdmin = false;
		$rootScope.onlyUser = true;
		
		$scope.selectedProduct = {};
		
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
		
	.controller("productViewController",function(ApiService,$location,$rootScope,userPersistenceService,$scope, $localStorage,$routeParams,NotifyService,$sce){
			console.log(" productViewController>>>");
			$rootScope.comments = false;
			$scope.selectedProduct = $routeParams.productId;
			console.log("$scope.selectedProduct",$scope.selectedProduct);
//			********************************************************* Load selected ad Function *********************************************************
						
			
//				$scope.url = $sce.trustAsResourceUrl('https://www.angularjs.org');
			
			ApiService.call('/getSelectedProduct/'+$scope.selectedProduct)
			.success(function(data,status){
				if(data !=null){
					console.log("Get Selected product",data);
					$scope.selectedProduct = data;
					var url = 'https://maps.google.com/maps?q='+data.latitude+','+data.longitude+'&hl=es;z=14&output=embed';
					$scope.url = $sce.trustAsResourceUrl(url);
					var href = 'https://maps.google.com/maps?q='+data.latitude+','+data.longitude+'&hl=es;z=14&output=embed'
					$scope.href= $sce.trustAsResourceUrl(href);
					console.log("$scope.selectedProduct ",$scope.selectedProduct );
				}else{
					
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
//			********************************************************* Load comments Function *********************************************************
			
			$scope.loadComments = function(){
				
				ApiService.call('/user/getComments/'+$scope.selectedProduct)
					.success(function(data,status){
						if(data !=null &&  !($.isEmptyObject(data))){
							console.log("Get all comments",data)
							$rootScope.comments = true;
							$scope.comments = data;
						}else{
							$rootScope.comments = false;
						}
					})
					.error(function(data,status){
						ApiService.exception(data,status);
					});
				}
			
//			********************************************************* Add Comment function *********************************************************
			
			$scope.addComment = function(productId){
				var user = $localStorage.user;
				console.log("Inside addComment",productId,user.email);
				
				var comment={
						"userId" :user.email,
						"description":$scope.comment,
						"productId":productId,
						"commenterName":user.displayName
				}
				
				console.log("add Comment",comment);
				ApiService.post("/user/postComment",comment)
				.success(function(data,status){
							if(data =="Comment added Successfully"){
								NotifyService.success("Comment added successfully");	
								
							}
							else{
								NotifyService.warning("Error adding comment!!","Please try again");
							}
						})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			}
			
			$scope.loadComments();
		})
		
		
		
		.controller("productsViewController",function(ApiService,$location,$rootScope,userPersistenceService,$scope, $routeParams){
			console.log(" productsViewController>>>");
			$scope.selectedSubCat = $routeParams.subCatName;
			console.log("$scope.selectedProduct",$scope.selectedSubCat);
//			********************************************************* Load selected ad Function *********************************************************
						
			ApiService.call('/getSubCatProducts/'+$scope.selectedSubCat)
			.success(function(data,status){
				if(data !=null){
					console.log("Get product under sub category",data);
					$scope.subCatProducts = data;
					console.log("$scope.subCatProducts  ",$scope.subCatProducts  );
				}else{
					
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
			
		})
		
		