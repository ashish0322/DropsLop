/**
 * 
 */

var dropslop = angular.module("dropslop",["ngRoute",'config','services','ngDialog','ngSanitize','ngStorage','ngIdle','ngCookies',
								'layoutService',
								'loginServivce',
								'registrationService',
								'adminService',
								'userService',
								'AdService',
								'ui.bootstrap','UIService','DropslopWebSocket','angularUtils.directives.dirPagination']);

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
			    .when('/admin/manageUsers', {
			      	access:'private', 
			      	templateUrl: 'html/admin/manageUsers.html', 
			      	controller: 'manageUsersController'
			      	})
			     .when('/user/userDashboard', {
			      	access:'private', 
			      	templateUrl: 'html/user/userView.html', 
			      	controller: 'userController'
			      	})
			      .when('/user/postAd', {
			      	access:'private', 
			      	templateUrl: 'html/user/postAd.html', 
			      	controller: 'adController'
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
	

 	run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
	function run($rootScope,$localStorage,$idle,authUsers,ngDialog,$location,$cookies,$http){
		
		
		 $rootScope.globals = $cookies.getObject('globals') || {};
	        if ($rootScope.globals.currentUser) {
	            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
	        }
	 
	        $rootScope.$on('$locationChangeStart', function (event, next, current) {
	            // redirect to login page if not logged in and trying to access a restricted page
	            var restrictedPage = $.inArray($location.path(), ['/']) === -1;
	            var loggedIn = $rootScope.globals.currentUser;
	            if (restrictedPage && !loggedIn) {
	                $location.path('/');
	            }
	        });
			
	     
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
		
	
		
	}
	
	
	

	
