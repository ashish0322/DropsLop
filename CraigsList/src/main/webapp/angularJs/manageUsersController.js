angular.module("adminService")

		.controller("manageUsersController",function($scope,$rootScope,$http,ApiService,ngDialog,$localStorage,NotifyService){
			console.log("Inside manageUsersController");
			var title = "Manage Users";
			$scope.title = title;
			
			$scope.currentPage = 1;
			$scope.pageSize = 5;
			$scope.filteredUsers = [];
			$scope.users = [];
			
//			********************************************************* Load Users Function *********************************************************
			
			$scope.loadUsers = function($rootScope,$localStorage){	
			ApiService.call("/admin/getUsers")
				.success(function(data,status){
					if(data !=null){
						console.log("Get all users",data);
						$scope.users = data;
						
					}else{
						
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			
//			$scope.$watch('currentPage', function() {
//			    var begin = (($scope.currentPage - 1) * $scope.numPerPage)
//			    , end = begin + $scope.numPerPage;
//			    console.log("begin, end",begin, end,$scope.users);
//			    $scope.filteredUsers = $scope.users.slice(begin, end);
//			    console.log("$scope.filteredUsers",$scope.filteredUsers);
//			  });
			}
			
			
			
			$scope.loadUsers();
			
			 $scope.pageChangeHandler = function(num) {
			      console.log('users page changed to ' + num);
			  };
			
			
			$scope.changeStatus = function(status,id){
				console.log("changeStatus",status);
				
				ApiService.call('/admin/'+status+'/lockUnlockUser/'+id)
				.success(function(data,status){
					var updatedStatus = data.active;
					if(updatedStatus != status){
						if(updatedStatus == true){
							NotifyService.success("User account enabled successfully!!");
						}
						else{
							NotifyService.success("User account disabled successfully!!");
						}
						
						$scope.loadUsers();
					}
					else{
						
						NotifyService.warning("Error Message","Failed to disable account");
						$scope.loadUsers();
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
				
			}
			
		
		
			
		})
		

		.controller('OtherController',function OtherController($scope) {
			  $scope.pageChangeHandler = function(num) {
				    console.log('going to page ' + num);
				  };
				});

		


