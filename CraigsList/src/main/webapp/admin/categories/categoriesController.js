angular.module("adminService")
	
		.controller("categoriesController",function($scope,$http,ApiService,ngDialog,$localStorage){
			console.log("Inside categoriesController");
			var title = "Categories";
			$scope.title = title;
			
		})