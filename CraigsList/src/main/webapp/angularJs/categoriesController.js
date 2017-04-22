angular.module("adminService")
	
		.controller("categoriesController",function($scope,$rootScope,$http,ApiService,ngDialog,$localStorage,NotifyService){
			console.log("Inside categoriesController");
			var title = "Categories";
			$scope.title = title;
			
			$scope.addCategory = function(){
				$scope.category = {};
				ngDialog.open({ 
					template: 'html/admin/categoryForm.html',
					scope:$scope
					});
				};
				
				
//		********************************************************* Category Validate Function *********************************************************
				
				// called during new category creation
				
				$scope.checkCategory = function(){
					if($scope.category.title !=null){
						var categoryTitle="";
						categoryTitle = $scope.category.title;
					console.log(categoryTitle);
					ApiService.call('/admin/validateCategory/'+categoryTitle)
						.success(function(data,status){
							$scope.categoryCheck = data;
							
							if(data == '"Category already present"' || data == 'Category already present'){
								console.log(data);
								document.getElementById("categoryCheck").style.display = 'block';
							}
							else{
								console.log("Category not present",data);
								document.getElementById("categoryCheck").style.display = 'none';
							}
						})
						.error(function(data,status){
							ApiService.exception(data,status);
						});
					}
					
				}
				
				// called during updating category 
				$scope.checkCategoryy = function(){
					if($scope.categoryData.title !=null){
						var categoryTitle="";
						categoryTitle = $scope.categoryData.title;
					console.log(categoryTitle);
					ApiService.call('/admin/validateCategory/'+categoryTitle)
						.success(function(data,status){
							$scope.categoryCheck = data;
							
							if((data == '"Category already present"' || data == 'Category already present') 
									&& ($scope.categoryData.title != $scope.actualTitle)){
								console.log(data);
								document.getElementById("categoryCheck").style.display = 'block';
							}
							else{
								console.log("Category not present",data);
								document.getElementById("categoryCheck").style.display = 'none';
							}
						})
						.error(function(data,status){
							ApiService.exception(data,status);
						});
					}
					
				}
				
				
			
//	 ********************************************************* Create Category Function *********************************************************
					
			$scope.createCategory = function(){
				console.log($scope.category);
				var category =	{
						title:$scope.category.title,
						description:$scope.category.description
					}
				console.log("createCategory",category);
					ApiService.post("/admin/addCategory",category)
					.success(function(data,status){
								if(data =="Category added Successfully"){
									NotifyService.success("Category added successfully");	
									$scope.loadCategories();
								}
								else{
									NotifyService.warning("Error adding category!!","Please try again");
								}
								ngDialog.closeAll();
							})
					.error(function(data,status){
						ApiService.exception(data,status);
					});
			}

//			********************************************************* Load Categories Function *********************************************************
			
			$scope.currentPage = 1;
			$scope.pageSize = 5;
			
			$scope.loadCategories = function($rootScope,$localStorage){
			
			ApiService.call("/getCategories")
				.success(function(data,status){
					if(data !=null){
						console.log("Get all categories",data)
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
			
			
		
//			********************************************************* Edit category Function *********************************************************
					
			$scope.editCategory = function(id){
				ApiService.call('/admin/'+id+'/editCategory')
					.success(function(data,status){
						if(data.categoryId != null){
							console.log("editCategory",data);
							$scope.categoryData = data;
							$scope.actualTitle = data.title;
							ngDialog.open({ 
								template: 'html/admin/modifyCategory.html',
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
//			********************************************************* Update Category Function *********************************************************
			
			$scope.updateCategory = function(categoryData){
				console.log("Inside update Category",$scope.categoryData);
				var category =	{
						title:$scope.categoryData.title,
						description:$scope.categoryData.description,
					}
				ApiService.post('/admin/'+$scope.categoryData.categoryId+'/updateCategory',category)
					.success(function(data,status){
						if(data == "Category updated Successfully"){
							ngDialog.close();
							NotifyService.success("Category updated Successfully");
							$scope.loadCategories();
						}
						else{
							ngDialog.close();
							NotifyService.warning("Error Message","Failed to update category");
						}
					})
					.error(function(data,status){
						ApiService.exception(data,status);
					})
			}
			
//			********************************************************* Delete Category Function *********************************************************
			
			
			$scope.deleteCategory = function(id){
				
				$localStorage.deleteid = id;
				ApiService.call('/admin/'+id+'/editCategory')
				.success(function(data,status){
					if(data.categoryId != null){
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
				console.log("Inside delete function",$localStorage.deleteid);
				ApiService.call('/admin/'+$localStorage.deleteid+'/deleteCategory')
				.success(function(data,status){
					if(data == "Category deleted Successfully"){
						ngDialog.close();
						NotifyService.success("Category Deleted Succefully!!");
						$scope.loadCategories();
					}
					else{
						NotifyService.warning("Error Message","Failed to delete category");
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
			}
			
			
			//initial data load
			$scope.loadCategories();
			
//			********************************************************* Create Sub-Category Function *********************************************************

			$scope.addSubCategory = function(categoryId){
				$scope.subCategory = {};
				$scope.categoryId = categoryId;
				ngDialog.open({ 
					template: 'html/admin/subCategoryForm.html',
					scope:$scope
					});
				}
		
			// sub category validate function 
			$scope.checkSubCategory = function(){
				if($scope.subCategory.name !=null){
					var subCategoryTitle="";
					subCategoryTitle = $scope.subCategory.name;
				console.log(subCategoryTitle);
				ApiService.call('/admin/validateSubCategory/'+subCategoryTitle)
					.success(function(data,status){
						$scope.subCategoryCheck = data;
						console.log(data);
						if(data == '"Sub Category already present"' || data == 'Sub Category already present'){
							console.log(data);
							document.getElementById("subCategoryCheck").style.display = 'block';
						}
						else{
							console.log("Sub Category not present",data);
							document.getElementById("subCategoryCheck").style.display = 'none';
						}
					})
					.error(function(data,status){
						ApiService.exception(data,status);
					});
				}
				
			}
			
			
			// sub category create function 
			$scope.createSubCategory = function(){
				console.log($scope.subCategory);
				
				var subCategory =	{
						name:$scope.subCategory.name
					}
				
				console.log("createSubCategory",subCategory);
					ApiService.post('/admin/'+$scope.categoryId+'/addSubCategory',subCategory)
					.success(function(data,status){
								if(data =="Sub Category added Successfully"){
									NotifyService.success("Sub Category added successfully");	
									$scope.loadCategories();
								}
								else{
									NotifyService.warning("Error adding Sub category!!","Please try again");
								}
								ngDialog.closeAll();
							})
					.error(function(data,status){
						ApiService.exception(data,status);
					});
			}
//			********************************************************* Load sub Categories Function *********************************************************
			
			
			
			$scope.editSubCatClick = function(field){
				 $scope.editing = $scope.subCategories.indexOf(field);
			     $scope.newField = angular.copy(field);
				
			}
			
			$scope.cancelUpdate = function(){
				console.log("Inside cancel update");
				   if ($scope.editing !== false) {
			            $scope.subCategories[$scope.editing] = $scope.newField;
			            $scope.editing = false;
			        }    
				
			}
			
			$scope.subCat ="";
			$scope.viewSubCategory = function(categoryId){
				
				$scope.categoryId = categoryId;
				
				ngDialog.open({ 
					template: 'html/admin/subCategoriesListView.html',
					scope:$scope
					});
				
				
			ApiService.call('/getSubCategories/'+categoryId)
				.success(function(data,status){
					console.log($.isEmptyObject(data));
					if(data != null && !($.isEmptyObject(data))){
						console.log("Get all sub categories",data)
						$scope.subCat = true;
						
						$scope.subCategories = data;
					}else{
						console.log("Inside no cat found");
						$scope.subCat = false;
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			}
			
//			********************************************************* Update sub Categories Function *********************************************************
			$scope.updateSubCat = function(id){
				
				if ($scope.editing !== false) {
				 var SubCategory = "";
				 SubCategory =  $scope.subCategories[$scope.editing];
				console.log("Inside update Sub Category",SubCategory);

				ApiService.post('/admin/'+id+'/updateSubCategory',SubCategory)
					.success(function(data,status){
						if(data == "Sub Category updated Successfully"){
							ngDialog.close();
							
							NotifyService.success("Sub Category updated Successfully");
							
						}
						else{
							ngDialog.close();
							NotifyService.warning("Error Message","Failed to update Subcategory");
						}
					})
					.error(function(data,status){
						ApiService.exception(data,status);
					})
					 $scope.editing = false;
				}
				
			}
					
//			********************************************************* Delete sub Categories Function *********************************************************
			$scope.deleteSubCategory = function(id){
				console.log("Inside DeletesubCat",id);
				
				$localStorage.deleteid = id;
				ApiService.call('/admin/'+id+'/getSubCategory')
				.success(function(data,status){
					if(data.subCategoryId != null){
						console.log("editSubCategory",data);
						$rootScope.msg = data.name;
						ngDialog.open({ 
							template: 'html/confirmYesNoDialogg.html',
					         scope:$scope
							});
					}
					else{
						NotifyService.warning("Error Message","Failed to retrieve sub category");
					}
					
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
				
			}
			
			$scope.confirmm = function(){
				console.log("Inside delete function",$localStorage.deleteid);
				ApiService.call('/admin/'+$localStorage.deleteid+'/deleteSubCategory')
				.success(function(data,status){
					if(data == "Sub Category deleted Successfully"){
						ngDialog.close();
						
						NotifyService.success("Sub Category Deleted Succefully!!");
						$scope.loadCategories();
					}
					else{
						NotifyService.warning("Error Message","Failed to delete Subcategory");
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				})
			}
			

		})