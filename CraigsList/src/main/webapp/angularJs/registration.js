angular.module("registrationService",[])


	.controller("RegistrationController",function($scope,$http,ApiService,ngDialog,$location,$rootScope){
		
	//	********************************************************* Email Validate Function *********************************************************
		$scope.checkEmail = function(){
			if($scope.user.email !=null){
			var emailId = $scope.user.email;
			console.log(emailId);
			ApiService.call('/validateEmail/'+emailId)
				.success(function(data,status){
					$scope.emailCheck = data;
					
					if(data == '"Email already Registered"' || data == 'Email already Registered'){
						console.log(data);
						document.getElementById("emailCheck").style.display = 'block';
					}
					else{
						console.log("Email unregistered",data);
						document.getElementById("emailCheck").style.display = 'none';
					}
				})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
			}
			
		}
	//	********************************************************* Register Function *********************************************************
		$scope.register = function(user){
				console.log("inside register function");
				console.log($scope.user.first_name);
				console.log(ApiService.app);
				var user =	{
								firstName:$scope.user.first_name,
								lastName:$scope.user.last_name,
								displayName:$scope.user.display_name,
								email:$scope.user.email,
								password:$scope.user.password
							}
				
	//		url :'http://localhost:8080/craigslist/signup',
			ApiService.post("/signup",user)
					.success(function(data,status){
								if(data!=""){
									console.log("Inside get user signup",data);
									ngDialog.close();
									$rootScope.registrationSuccessMsg=data;
									ngDialog.open({ 
										template: 'login_SignUp/msgboxDialog.html',
										showClose: false,
										scope: $rootScope
										
									});
								}
							})
					.error(function(data,status){
						ApiService.exception(data,status);
					});
		}
		
	});