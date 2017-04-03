/**
 * 
 */

var dropslop = angular.module("dropslop",["ngRoute",'config','services','ngDialog','ngSanitize','ngStorage','ngIdle',
								'layoutService',
								'loginServivce',
								'registrationService',
								'adminService',
								'ui.bootstrap']);

	dropslop.config(function($routeProvider){
			$routeProvider
				.when("/",{
					templateUrl : "login_SignUp/main.html",
					controller :  "layoutController"
				})
				.when("/logout",{
					templateUrl : "login_SignUp/main.html",
					controller :  "logoutController"
				})
				.when('/admin/adminDashboard', {
			      	access:'private', 
			      	templateUrl: 'admin/adminView.html', 
			      	controller: 'adminController'
			      	})
			    .when('/admin/manageCategories', {
			      	access:'private', 
			      	templateUrl: 'admin/categories/manageCategories.html', 
			      	controller: 'categoriesController'
			      	})
      	
			    .otherwise({ redirectTo: '/error' })
		
	})
	
	.config(['$keepaliveProvider', '$idleProvider', '$provide', function($keepaliveProvider, $idleProvider, $provide, $log) {
	$idleProvider.idleDuration(900);
	$idleProvider.warningDuration(10);
	$keepaliveProvider.interval(900);
	

    $provide.value("$locale", {
		"DATETIME_FORMATS": {
			"MONTH": [
			  "January",
			  "February",
			  "March",
			  "April",
			  "May",
			  "June",
			  "July",
			  "August",
			  "September",
			  "October",
			  "November",
			  "December"
			],
			"SHORTDAY": [
			  "Sun",
			  "Mon",
			  "Tue",
			  "Wed",
			  "Thu",
			  "Fri",
			  "Sat",
			]
		}
	});
}])
	
	.run(function($rootScope,$localStorage,$idle,authUsers){
			
		/* Start wathching for idle */
	 	$idle.watch();
	 	$rootScope.$on('$idleStart', function() {
			$rootScope.idlePopup=ngDialog.open({ 
				template: 'login_SignUp/idleDialog.html'
			});
		});

		$rootScope.$on('$idleEnd', function() {
			$rootScope.idlePopup.close();		
		});

		$rootScope.$on('$idleTimeout', function() {
			ngDialog.closeAll();		
			authUsers.timeout();
		});
		/* End watching for idle */
		$localStorage.logged_user = 0;
		if($localStorage.logged_user == 0){
		$localStorage.showLogin="true";
		$localStorage.useradmin="none";
		$localStorage.userlogged="false";
		$localStorage.showdropdown="true";
		
		$rootScope.showLogin=$localStorage.showLogin;
		$rootScope.useradmin=$localStorage.useradmin;
		$rootScope.userlogged=$localStorage.userlogged;
		$rootScope.showdropdown=$localStorage.showdropdown;
		}
	})
	
	
	

	
