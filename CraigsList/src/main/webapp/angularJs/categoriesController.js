angular.module("adminService")
	
		.controller("categoriesController",function($scope,$rootScope,$http,ApiService,ngDialog,$localStorage,NotifyService){
			console.log("Inside categoriesController");
			var title = "Categories";
			$scope.title = title;
			
			$scope.addCategory = function(){
				ngDialog.open({ 
					template: 'html/admin/categoryForm.html',
					controller : "categoriesController"
					});
				}
			
			
			$scope.createCategory = function(category){
				console.log("Inside create Category");
				$rootScope.successsTitle="Category added successfully";
				$rootScope.errorTitle="Error adding category!!";
				$rootScope.errorMessage = "Please try again";
				var category =	{
						title:$scope.category.title,
						description:$scope.category.description
					}
				console.log("createCategory",category);
				ApiService.post("/addCategory",category)
				.success(function(data,status){
							if(data =="Category added Successfully"){
								ngDialog.close();
								NotifyService.success($rootScope.successsTitle);
								loadCategories();
							}
							else{
								ngDialog.close();
								NotifyService.warning($rootScope.errorTitle,$rootScope.errorMessage);
							}
						})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			}
			
			var loadCategories = function($rootScope){
			
			ApiService.call("/getCategories")
				.success(function(data,status){
					if(data !=null){
						console.log("Get all categoried",data)
						$scope.cat = true;
						$scope.categories = data;
					}else{
						$scope.cat = false;
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			
			}
			
			loadCategories();
			
			$scope.editCategory = function(id){
				ApiService.call("/editCategory/"+id)
					.success(function(data,status){
						if(data.categoryId != null){
							$scope.editCategory = data;
							ngDialog.open({ 
								template: 'html/admin/modifyCategory.html',
								controller : "categoriesController"
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
			
			$scope.updateCategory = function(category){
				ApiService.post("/updateCategory"+category)
					.success(function(data,status){
						
					})
					.error(function(data,status){
						ApiService.exception(data,status);
					})
			}
			
			$scope.deleteCategory = function(id){
				ApiService.call("/deleteCategory"+id)
				.success(function(data,status){
					if(data.categoryId != null){
						NotifyService.success("Category Deleted Succefully!!");
					}
					else{
						NotifyService.warning("Error Message","Failed to retrieve category");
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
			}
			
		})