angular.module("adminService",[])

	.controller("adminController",function($scope,$http,ApiService,ngDialog,$localStorage){
		console.log("Inside admin controller",$localStorage.user);
		var message = "Welcome Admin";
		$scope.msg = message;
		
		
		
//		********************************************************* Get Number of users Function *********************************************************
		
		
		$scope.loadNumberOfUsers = function($rootScope,$localStorage){
		
		ApiService.call("/admin/getUserCount")
			.success(function(data,status){
				if(data !=null){
					console.log("Get users count",data)
					$scope.totalUsers = data;
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
		}
		
		
//		********************************************************* Get Pending Ads Function *********************************************************
		
		$scope.loadNumberOfPendingAds = function($rootScope,$localStorage){
			
			ApiService.call("/admin/getPendingAdsCount")
				.success(function(data,status){
					if(data !=null){
						console.log("Get PendingAds count",data)
						$scope.pendingAds = data;
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			}
		
		
		
//		********************************************************* Get Page Views Function *********************************************************

		$scope.loadNumberOfPageViews = function($rootScope,$localStorage){
					
					ApiService.call("/admin/getPageViewsCount")
						.success(function(data,status){
							if(data !=null){
								console.log("Get PageViews count",data)
								$scope.pageViews = data;
							}
						})
						.error(function(data,status){
							ApiService.exception(data,status);
						});
					}

//		********************************************************* Get Number of blocked users Function *********************************************************
		
		
		$scope.loadNumberOfBlockedUsers = function($rootScope,$localStorage){
		
		ApiService.call("/admin/getBlockedUserCount")
			.success(function(data,status){
				if(data !=null){
					console.log("Get blocked users count",data)
					$scope.blockedUsers = data;
				}
			})
			.error(function(data,status){
				ApiService.exception(data,status);
			});
		}		
		
		$scope.loadNumberOfPageViews();
		$scope.loadNumberOfBlockedUsers();
		$scope.loadNumberOfPendingAds();
		$scope.loadNumberOfUsers();
	})
	