angular.module('AdService', ['ui.bootstrap'])

	.controller("adController",function($scope,$http,ApiService,ngDialog,$localStorage,$rootScope,uploadService,NotifyService,$sce,$location){
		console.log("Inside adController");
		
		$scope.ad= {};
		var message = "Post your Ad here";
		$scope.msg = message;
		
		$scope.main = function(){
			console.log("Inside main tab");
			$scope.mainForm= true;
			
		}
		
		$scope.images = function(){
			console.log("Inside images tab");
			$scope.imagesForm= true;
			
			  $scope.$watch('file', function(newfile, oldfile) {
			      if(angular.equals(newfile, oldfile) ){
			        return;
			      }
			   $scope.ad.file = newfile;
			   console.log("$scope.ad.file",$scope.ad.file);
			  
			      uploadService.upload(newfile).then(function(res){
			        // DO SOMETHING WITH THE RESULT!
			        console.log("result", res);
			      })
			    });
			  
			console.log("$scope.fileinput",$scope.fileinput);
		}
		
		$scope.iconUpload = function(event) {
			console.log("$scope.iconUpload");
			var file = event.target.files[0];
			if (file != null && file != undefined) {
//				if (file.size > 51200 || file.type !== "image/png") {
					//file must be less than 50KB and of type png
				console.log("$scope.iconUpload",file);
				$scope.appIconError = false;
				$scope.imageFile = file; //FileList object
				console.log($scope.imageFile);
					

				} else {
					$scope.appIconError = true;
					$scope.imageFile = null;
				}
//			} else {
//				//cancel event
//				$scope.appIconError = false;
//				$scope.appIcon = null;
//
//			}

		};
		
		$scope.description = function(){
			console.log("Inside description tab");
			$scope.descriptionForm= true;
		}
		
		$scope.location = function(){
			console.log("Inside location tab");
			$scope.locationForm= true;
			$scope.hide = true;
			 initialize();
			
		}
		
		$scope.mainForm= true;
		
		    $scope.jumpToInvalidTab=function(){
		        $scope.tab=1;
		        if($scope.mainForm.$valid){
		            $scope.tab=2;
		        };
		    };
		    
//			********************************************************* Retrieving address from google maps api *********************************************************
		    $rootScope.yourLocation = "";
		    
		    $("#autocomplete").on('focus', function () {
		        geolocate();
		    });

		    var placeSearch, autocomplete;
		    var componentForm = {
		        street_number: 'short_name',
		        route: 'long_name',
		        locality: 'long_name',
		        administrative_area_level_1: 'short_name',
		        country: 'long_name',
		        postal_code: 'short_name'
		    };

		    function initialize() {
		        // Create the autocomplete object, restricting the search
		        // to geographical location types.
		        autocomplete = new google.maps.places.Autocomplete(
		        /** @type {HTMLInputElement} */ (document.getElementById('autocomplete')), {
		            types: ['geocode']
		        });
		        // When the user selects an address from the dropdown,
		        // populate the address fields in the form.
		        google.maps.event.addListener(autocomplete, 'place_changed', function () {
		            fillInAddress();
		            
		        });
		    }

		    // [START region_fillform]
		    function fillInAddress() {
		        // Get the place details from the autocomplete object.
		        var place = autocomplete.getPlace();
		        console.log("place is ",place.formatted_address);
		        $rootScope.yourLocation = place.formatted_address;
		        
		        console.log("$scope.yourLocation",$scope.yourLocation);
		        document.getElementById("latitude").value = place.geometry.location.lat();
		        document.getElementById("longitude").value = place.geometry.location.lng();

		        for (var component in componentForm) {
		            document.getElementById(component).value = '';
		            document.getElementById(component).disabled = false;
		        }

		        // Get each component of the address from the place details
		        // and fill the corresponding field on the form.
		        console.log("place.address_components",place.url);
		        
		        $rootScope.addressUrl = place.url;
		        var url = place.url+'&output=embed';
		        $rootScope.url = $sce.trustAsResourceUrl(url);
		        $rootScope.href = $sce.trustAsResourceUrl(url);
		        for (var i = 0; i < place.address_components.length; i++) {
		            var addressType = place.address_components[i].types[0];
		            if (componentForm[addressType]) {
		                var val = place.address_components[i][componentForm[addressType]];
		                document.getElementById(addressType).value = val;
		            }
		        }
		    }
		    // [END region_fillform]

		    // [START region_geolocation]
		    // Bias the autocomplete object to the user's geographical location,
		    // as supplied by the browser's 'navigator.geolocation' object.
		    function geolocate() {
		        if (navigator.geolocation) {
		            navigator.geolocation.getCurrentPosition(function (position) {
		                var geolocation = new google.maps.LatLng(
		                position.coords.latitude, position.coords.longitude);

		                var latitude = position.coords.latitude;
		                var longitude = position.coords.longitude;
		              
		                document.getElementById("latitude").value = latitude;
		                document.getElementById("longitude").value = longitude;

		                autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
		            });
		        }

		    }

//			********************************************************* Main Function to handle ad Form *********************************************************
		    $scope.postAdvertisement = function(ad){
		    	
		    	console.log("Inside submit of post ad");
		    	
		    	if (   
						!$scope.ad.title ||
						!$scope.ad.productName ||
						!$scope.ad.category ||
						!$scope.ad.subCategory ||
						!$scope.ad.purchasedYear ||
						!$scope.ad.price ||
						!$scope.imageFile ||
						!$scope.ad.description
						){
		    		console.log("Null check",$scope.ad);
					$scope.errorFlag = true;

				}
				
		    	else{
		    		console.log("Not null");
		    	$scope.file;
		    	console.log("$scope.file",$scope.file);
		    	var formData = new FormData();  
		    	formData.append('file',$scope.imageFile);
		    	
		    	var image = '?image='+formData;
		    	console.log("formData",formData);
		    	
		    	var title = $scope.ad.title;
		    	var productName = $scope.ad.productName;
		    	var category = $scope.ad.category;
		    	var subCategory = $scope.ad.subCategory;
		    	var purchasedYear = $scope.ad.purchasedYear;
		    	var price = $scope.ad.price;
		    	var file = $scope.imageFile;
		    	var description = $scope.ad.description;
		    	var address = $rootScope.yourLocation;
		    	var contact = $scope.ad.contact;
		    	var addressUrl = $rootScope.addressUrl;
		    	
		    	var postAd = {
		    			"title" : title,
		    			"productName" :productName,
		    			"category" :category,
		    			"subCategoryName":subCategory,
		    			"purchasedYear":purchasedYear,
		    			"price":price,
		    			"description": description,
		    			"address": address,
		    			"contact":contact,
		    			"addressUrl":addressUrl
		    	}
		    	formData.append('ad',angular.toJson(postAd,true));
		    	console.log("Filled post ad form details",postAd);
		    	var uploadUrl = 'http://localhost:8030/dropslop/api/user/'+$rootScope.email+'/postAd';
		    	$http.post(uploadUrl, formData,{
		            transformRequest: angular.identity,
		            headers: {'Content-Type': undefined}
		    		
		        })
		    	
//		    	ApiService.post('/user/'+$rootScope.email+'/postAd'+image,postAd)
				.success(function(data,status){
					if(data!=null){
								console.log("Inside post ad",data);
								NotifyService.success("Your Ad has been sent for admin approval!!");
								$location.path('/user/viewPostings');
								
							}
							else{
								NotifyService.warning("Error Message","Failed to submit Ad");
							}
						})
				.error(function(data,status){
					ApiService.exception(data,status);
				});
		    	
		      }
		    }
		    

	})
	
		    
//			********************************************************* Upload Service *********************************************************
	
	
			.service("uploadService", function($http, $q) {
			
			  
			    
			 // ---
			    // PRIVATE METHODS.
			    // ---
			  
			    function handleError(response, data) {
			      if (!angular.isObject(response.data) ||!response.data.message) {
			        return ($q.reject("An unknown error occurred."));
			      }
		
			      return ($q.reject(response.data.message));
			    }
		
			    function handleSuccess(response) {
			      return (response);
			    }
		
			  })
			  
			  
			   .directive("fileinput", [function() {
			    return {
			      scope: {
			        fileinput: "=",
			        filepreview: "="
			      },
			      link: function(scope, element, attributes) {
			    	 
			        element.bind("change", function(changeEvent) {
			          scope.fileinput = changeEvent.target.files[0];
			        
			          var reader = new FileReader();
			          reader.onload = function(loadEvent) {
			            scope.$apply(function() {
			              scope.filepreview = loadEvent.target.result;
			            });
			          }
			          reader.readAsDataURL(scope.fileinput);
			        });
			      }
			    }
		  }]);


