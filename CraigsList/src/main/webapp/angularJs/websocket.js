var taskApp = angular.module("DropslopWebSocket",[])

	taskApp.controller("democtrl",function($scope){
		$scope.colors = ['Red','Blue','Green'];
	});

	taskApp.controller("TaskController",function($http,$scope,ApiService,$rootScope){
			
		// WebSocket Initialization
		//	var taskSocket = new WebSocket("ws://localhost:8888/websocket");
		var serviceUrl = ApiService.url.replace("http","");
		var url = "ws" + serviceUrl + "/dropslop/websocket"
		console.log("url is",url);
		//Web Socket Initialization 
		//'ws:// is defined as ServerEndpoint in TaskWebSoketHandler.java'
		var taskSocket = new WebSocket(url);
		
		
		taskSocket.onmessage = function(message) {
			console.log("Inside on message");
			$rootScope.commentsList = JSON.parse(message.data);
	        $scope.$apply();        
	    };
	    taskSocket.onclose = function() {
	        $scope.message = {
	            type: "danger",
	            short: "Socket error",
	            long: "An error occured with the WebSocket."
	        };
	        $scope.$apply();    
	    }

	});
	
	var x = document.getElementById("taskTitleFieldId");
	if(x == 9)
		alert("Enter valid input");