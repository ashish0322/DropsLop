angular.module("userService",[])

	.controller("userController",function($scope,$http,ApiService,ngDialog,$localStorage){
		console.log("Inside user controller",$localStorage.user);
		var message = "Welcome User";
		$scope.msg = message;
		
	})