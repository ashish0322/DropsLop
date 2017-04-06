/**
 * 
 */

var dropslop = angular.module("dropslop",["ngRoute",'config','services','ngDialog','ngSanitize','ngStorage','ngIdle',
								'layoutService',
								'loginServivce',
								'registrationService',
								'adminService',
								'ui.bootstrap','UIService','DropslopWebSocket']);

	dropslop.config(function($routeProvider,$httpProvider){
			$routeProvider
				.when("/",{
					templateUrl : "html/main.html",
					controller :  "layoutController"
				})
				.when("/logout",{
					templateUrl : "html/main.html",
					controller :  "logoutController"
				})
				.when('/admin/adminDashboard', {
			      	access:'private', 
			      	templateUrl: 'html/admin/adminView.html', 
			      	controller: 'adminController'
			      	})
			    .when('/admin/manageCategories', {
			      	access:'private', 
			      	templateUrl: 'html/admin/categories.html', 
			      	controller: 'categoriesController'
			      	})
      	
			    .otherwise({ redirectTo: '/error' })
			    
			    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		
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
	
	.run(function($rootScope,$localStorage,$idle,authUsers,ngDialog){
			
		/* Start wathching for idle */
	 	$idle.watch();
	 	$rootScope.$on('$idleStart', function() {
			$rootScope.idlePopup=ngDialog.open({ 
				template: 'html/idleDialog.html'
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
			
		$localStorage.useradmin="none";
		$localStorage.showdropdown="true";
		
		$rootScope.authenticated = false;
		
		$rootScope.useradmin=$localStorage.useradmin;
		$rootScope.showdropdown=$localStorage.showdropdown;
		}
	})
	
	
	

	
