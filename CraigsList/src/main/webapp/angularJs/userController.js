angular.module("userService",[])

	.controller("userController",function($scope,$http,ApiService,ngDialog,$localStorage){
		console.log("Inside user controller",$localStorage.user);
		var message = "Your Postings Summary";
		var user = $localStorage.user;
		$scope.msg = message;
		
		
		$scope.currentPage = 1;
		$scope.pageSize = 5;
		$scope.filteredAds = [];
		$scope.users = [];
		
		
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
		  
		  $scope.loadAds(); 
	})
	
	.controller('OtherController',function OtherController($scope) {
			  $scope.pageChangeHandler = function(num) {
				    console.log('going to page ' + num);
				  };
				});