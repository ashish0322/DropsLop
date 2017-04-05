angular.module("adminService",[])

	.controller("adminController",function($scope,$http,ApiService,ngDialog,$localStorage){
		console.log("Inside admin controller",$localStorage.user);
		var message = "Welcome Admin";
		$scope.msg = message;
		
	})
	