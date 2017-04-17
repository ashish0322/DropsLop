angular.module("userService",[])

	.controller("userController",function($scope,$http,ApiService,ngDialog,$localStorage,$rootScope,NotifyService){
		console.log("Inside user controller",$localStorage.user);
		var message = "Your Postings Summary";
		var user = $localStorage.user;
		$scope.msgg = message;
		
		
		$scope.currentPage = 1;
		$scope.pageSize = 5;
		$scope.filteredAds = [];
		$scope.users = [];
		
		
//		********************************************************* Load User postings Function *********************************************************
	
		$scope.loadAds = function($rootScope,$localStorage){	
			ApiService.call('/user/'+user.email+'/getPostings')
				.success(function(data,status){
					if(data !=null){
						console.log("Get all ads",data);
						$scope.ads = data;
						
					}else{
						
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			
			}
			
		
			
		 $scope.pageChangeHandler = function(num) {
		      console.log('ads page changed to ' + num);
		      
		  };
		  
		  
//			********************************************************* Change posted ad to expired  *********************************************************
	 
			$scope.deletePosting = function(id){
				$localStorage.deleteAdid = id;
				ApiService.call('/user/'+id+'/getAd')
				.success(function(data,status){
					if(data.productId != null){
						console.log("editCategory",data);
						$rootScope.msg = data.title;
						ngDialog.open({ 
							template: 'html/confirmYesNoDialog.html',
					         scope:$scope
							});
					}
					else{
						NotifyService.warning("Error Message","Failed to retrieve category");
					}
					
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
				
			}
			
			$scope.confirm = function(){
				console.log("Inside delete function",$localStorage.deleteAdid);
				ApiService.call('/user/'+$localStorage.deleteAdid+'/deleteAd')
				.success(function(data,status){
					if(data == "Ad removed Successfully"){
						ngDialog.close();
						NotifyService.success("Ad Deleted Succefully!!");
						$scope.loadAds();
					}
					else{
						NotifyService.warning("Error Message","Failed to delete Ad");
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
			}
			
//			********************************************************* Edit posted ad  *********************************************************
			$scope.editPosting = function(id){
				$localStorage.deleteAdid = id;
				ApiService.call('/user/'+id+'/getAd')
				.success(function(data,status){
					if(data.productId != null){
						console.log("editAd",data);
						$scope.productData = data;
						$scope.actualTitle = data.title;
						ngDialog.open({ 
							template: 'html/user/modifyAd.html',
							scope:$scope
							});
					}
					else{
						NotifyService.warning("Error Message","Failed to retrieve category");
					}
					
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
				
			}
			
//			********************************************************* Update Posting Function *********************************************************
			
			$scope.updateProduct = function(productData){
				console.log("Inside update ad posting",$scope.productData);
				var ad =	{
						title:$scope.productData.title,
						price:$scope.productData.price,
						contact:$scope.productData.contact
					}
				ApiService.post('/user/'+$scope.productData.productId+'/updateAd',ad)
					.success(function(data,status){
						if(data == "Ad updated Successfully"){
							ngDialog.close();
							NotifyService.success("Ad updated Successfully");
							$scope.loadAds(); 
						}
						else{
							ngDialog.close();
							NotifyService.warning("Error Message","Failed to update Ad");
						}
					})
					.error(function(data,status){
						ApiService.exception(data,status);
					})
			}
			

		  $scope.loadAds(); 
	})
	
	.controller('OtherController',function OtherController($scope) {
			  $scope.pageChangeHandler = function(num) {
				    console.log('going to page ' + num);
				  };
				});