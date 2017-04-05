
angular.module('UIService',[])

	//Service notify
	.service('NotifyService', function($injector) {
		return {
			success: function(message){
				UIkit.notify({ 
					message : "<div class='text'>"+message+"</div>",
					timeout : 2000,
					status  : 'success',
				});
			},
		
			warning:  function(title, message){
				console.log("title :::::", title);
				UIkit.notify({ 
					message : "<div class='title'>"+title+"</div><div class='text'>"+message+"</div>",
					timeout : 2000,
					status  : 'warning',
				});
			}
		};
	})