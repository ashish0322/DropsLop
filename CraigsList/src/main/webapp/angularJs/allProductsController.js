angular.module("layoutService")


		.controller("allProductsViewController",function(ApiService,$location,$rootScope,userPersistenceService,$scope, $routeParams){
			console.log(" allProductsViewController>>>");
			
			
			$scope.currentPage = 1;
			$scope.pageSize = 5;
			$scope.filteredProducts = [];
			$rootScope.allProducts = [];

			
		//	********************************************************* Load all products Function *********************************************************
						
			ApiService.call('/getAllProducts')
			.success(function(data,status){
				if(data !=null){
					console.log("Get all product ",data);
					$rootScope.allProducts = data;
					
				}else{
					
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
			
		})
		
		
		.controller("productsViewController",function(ApiService,$location,$rootScope,userPersistenceService,$scope, $routeParams){
			console.log(" productsViewController>>>");
			$scope.selectedSubCat = $routeParams.subCatName;
			console.log("$scope.selectedProduct",$scope.selectedSubCat);
			
			$scope.currentPage = 1;
			$scope.pageSize = 5;
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
					var url = data.addressUrl+'&output=embed';
					$scope.url = $sce.trustAsResourceUrl(url);
					
					$scope.href= $sce.trustAsResourceUrl(url);
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
								$scope.loadComments();
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
		
		
		.controller('OtherController',function OtherController($scope) {
			  $scope.pageChangeHandler = function(num) {
				    console.log('going to page ' + num);
				  };
				});