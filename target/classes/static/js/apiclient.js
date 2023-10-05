apiclient=(function(){
    return {
		getBlueprintsByAuthor:function(authname,callback){
            $.get("http://localhost:8080/blueprints/" + authname, function(data){
			    callback(data);
			});
		},

		getBlueprintsByNameAndAuthor:function(authname,bpname,callback){
            $.get("http://localhost:8080/blueprints/" + authname + "/" + bpname, function(data){
            	callback(data);
            });
		}
	}

})();
