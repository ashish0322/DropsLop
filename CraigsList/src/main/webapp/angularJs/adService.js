angular.module('AdService', ['ui.bootstrap'])

	.controller("adController",function($scope,$http,ApiService,ngDialog,$localStorage,$rootScope){
		console.log("Inside adController");
		
		$scope.userAd = {};
		var message = "Post your Ad here";
		$scope.msg = message;
		
		$scope.main = function(){
			console.log("Inside main tab");
			$scope.mainForm= true;
			
			
		}
		
		$scope.images = function(){
			console.log("Inside images tab");
			$scope.imagesForm= true;
		}
		
		$scope.description = function(){
			console.log("Inside description tab");
			$scope.descriptionForm= true;
		}
		
		$scope.location = function(){
			console.log("Inside location tab");
			$scope.locationForm= true;
			
			$scope.initMap();
		}
		
		$scope.mainForm= true;
		
		    $scope.jumpToInvalidTab=function(){
		        $scope.tab=1;
		        if($scope.mainForm.$valid){
		            $scope.tab=2;
		        };
		    };
		    
//			********************************************************* Retrieving address from google maps api *********************************************************
		
		var input = "";
	    
		$scope.initMap  = function() {
			console.log("Inside initmap");
	        var map = new google.maps.Map(document.getElementById('map'), {
	          center: {lat: -33.8688, lng: 151.2195},
	          zoom: 13
	        }) ;
	        
	        var card = document.getElementById('pac-card');
	        $scope.getAddress = function(){
				console.log($scope.userAd.streetAdress);
				input = $scope.userAd.streetAdress;
				
			}
	        
	        
	        map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);
	        var autocomplete = new google.maps.places.Autocomplete(input);
	        autocomplete.bindTo('bounds', map);
	        
	        var infowindow = new google.maps.InfoWindow();
	        var infowindowContent = document.getElementById('infowindow-content');
	        infowindow.setContent(infowindowContent);
	        var marker = new google.maps.Marker({
	          map: map,
	          anchorPoint: new google.maps.Point(0, -29)
	        });
	        
	        autocomplete.addListener('place_changed', function() {
	            infowindow.close();
	            marker.setVisible(false);
	            var place = autocomplete.getPlace();
	            if (!place.geometry) {
	              // User entered the name of a Place that was not suggested and
	              // pressed the Enter key, or the Place Details request failed.
	              window.alert("No details available for input: '" + place.name + "'");
	              return;
	            }
	            
            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
              map.fitBounds(place.geometry.viewport);
            } else {
              map.setCenter(place.geometry.location);
              map.setZoom(17);  // Why 17? Because it looks good.
            }
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);
	        
            var address = '';
            if (place.address_components) {
              address = [
                (place.address_components[0] && place.address_components[0].short_name || ''),
                (place.address_components[1] && place.address_components[1].short_name || ''),
                (place.address_components[2] && place.address_components[2].short_name || '')
              ].join(' ');
            }

            infowindowContent.children['place-icon'].src = place.icon;
            infowindowContent.children['place-name'].textContent = place.name;
            infowindowContent.children['place-address'].textContent = address;
            infowindow.open(map, marker);
          });
	    }
		
	})