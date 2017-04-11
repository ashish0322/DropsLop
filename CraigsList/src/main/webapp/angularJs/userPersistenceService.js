angular.module("dropslop")

		.factory("userPersistenceService", [
			"$cookies", function($cookies) {
				var userName = "";
				var data = {};
				console.log("Inside userPersistenceService");
				return {
					setCookieData: function(username) {
						console.log("Inside userPersistenceService setCookieData",username);
						userName = username;
						$cookies.put("userName", username);
					},
					getCookieData: function() {
						userName = $cookies.get("userName");
						return userName;
					},
					clearCookieData: function() {
						userName = "";
						$cookies.remove("userName");
						$cookies.remove("userSessionData");
					},
					
					storeUserSession: function(userData){
						console.log("Inside userPersistenceService storeUserSession",userData);
						data = userData;
						$cookies.putObject("userSessionData", data);
					},
					getUserSessionData: function() {
						data = $cookies.getObject("userSessionData");
						console.log("Inside getUserSessionData",data);
						return data;
					}
				}
			}
		]);