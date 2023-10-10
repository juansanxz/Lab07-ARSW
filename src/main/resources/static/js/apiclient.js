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
		},

		putBlueprintsUpdates: function(authname, bpname, newPoints) {
		    return $.ajax({
                url: "http://localhost:8080/blueprints/" + authname + "/" + bpname,
                type: 'PUT',
                data: newPoints,
                contentType: "application/json"
            });
		},

		postBlueprintsUpdates: function(bp) {
            return $.ajax({
                url: "http://localhost:8080/blueprints",
                type: 'POST',
                data: bp,
                contentType: "application/json"
            });
        },

        deleteBlueprint: function(authname,bpname) {
            return $.ajax({
                url: "http://localhost:8080/blueprints/" + authname + "/" + bpname,
                type: 'DELETE'
            });
        }
	}

})();
