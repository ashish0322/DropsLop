angular.module("adminService")

		.controller("manageAdsController",function($scope,$rootScope,$http,ApiService,ngDialog,$localStorage,NotifyService,filterFilter){
			console.log("Inside manageAdsController");
			var title = "Manage Ads";
			$scope.title = title;
			
			$scope.approved = 'pending';
			
			$scope.currentPage = 1;
			$scope.pageSize = 5;
			$scope.filteredAds = [];
			$scope.users = [];
			
		//	********************************************************* Load Ads Function *********************************************************
			
			$scope.status = ['approved','Pending','expired'];
			
			$scope.adStatus = [];
			
			$scope.loadAds = function($rootScope,$localStorage){	
			ApiService.call("/admin/getAds")
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
			  
	//	********************************************************* Approve Ads Function *********************************************************
							
			
			$scope.changeStatus = function(status,id){
				console.log("changeStatus",status);
				
				ApiService.call('/admin/'+status+'/approveAdd/'+id)
				.success(function(data,status){
					var updatedStatus = data.approvalStatus;
					if(updatedStatus != status){
						if(updatedStatus == "approved"){
							NotifyService.success("Ad approved successfully!!");
						}
						
						$scope.loadAds();
					}
					else{
						
						NotifyService.warning("Error Message","Failed to approve ad");
						$scope.loadAds();
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
				
			}
			
//	********************************************************* Delete Ads Function *********************************************************
						
			$scope.deleteAd = function(id){
				$localStorage.deleteAdid = id;
				ApiService.call('/admin/'+id+'/getAd')
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
				ApiService.call('/admin/'+$localStorage.deleteAdid+'/deleteAd')
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
			
			
		
		$scope.loadAds();
		
//		********************************************************* Show/Hide Approved Ads Function *********************************************************
		
			$scope.showApproved = function(val){
				console.log("Inside showApproved",val);
				if(val == true){
				$scope.approved = 'approved';
				}
				else{
					$scope.approved = 'pending';	
				}
			}
		
			

		    $scope.filterStats = function () {
		    	console.log("Inside filterStats");
	        return function (p) {
	            for (var i in $scope.adStatus) {
	            	
	                if (p.approvalStatus == $scope.status[i] && $scope.adStatus[i]) {
	                	
	                    return true;
	                }
	            }
	        }
	    }
			
		})
	
		
		.controller('OtherController',function OtherController($scope) {
			  $scope.pageChangeHandler = function(num) {
				    console.log('going to page ' + num);
				  };
				});