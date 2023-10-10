var blueprintsModule = (function()  {
    var author;
    var bps;
    var currentBp;
    var currentModule = apiclient;
    var tempPoints;

    putBlueprints =  function() {
        return currentModule.putBlueprintsUpdates(author, currentBp.name, JSON.stringify(tempPoints));
    };

    createBlueprint = function(bpName) {
        var newBp = {"author":author,"points":[],"name":bpName};
        return currentModule.postBlueprintsUpdates(JSON.stringify(newBp));
    };

    deleteBlueprint = function() {
        return currentModule.deleteBlueprint(author, currentBp.name);
    };

    return {
      setAuthor: function(newAuthor) {
        author = newAuthor;
        blueprintsModule.setBpsByAuthor(author);
        $("#authorNameText").text("Name of Author: " + author);
        $("#newButton").remove();
        var newButton = $("<button id='newButton' type='button' onclick='blueprintsModule.chainedPromisesToCreate();'>Create New Blueprint</button>");
        $("#newBpDiv").append(newButton);
      },

      getAuthor: function() {
        return author;
      },

      setBpsByAuthor: function(authorToFind) {
        currentModule.getBlueprintsByAuthor(authorToFind, function(blueprints) {
            var transformedBlueprints = blueprints.map(function(bp) {
                return {
                    name: bp.name,
                    numPoints: bp.points.length
                }
            });
            bps = transformedBlueprints;
            var table = $("#blueprintTable");
            var rowsToAdd= bps.map(function(bp) {
                var newRow = $("<tr>");
                newRow.append("<td>" + bp.name + "</td>");
                newRow.append("<td>" + bp.numPoints + "</td>");
                newRow.append("<td><button onclick='blueprintsModule.loadBlueprintsModuleFunctions(this);'>Open</button></td>");
                newRow.append("</tr>");
                return newRow;
            });
            $("#blueprintTable tr:not(:first-child)").remove();
            table.append(rowsToAdd);
            var pointsNumber = bps.reduce(function(sum, bp) {
                return sum + bp.numPoints;
            },0);

            $("#totalUserPoints").text("Total user points: " + pointsNumber);
        });

      },

      paintBp: function(buttonElement) {
        var row = $(buttonElement).closest('tr');
        var bpName = row.find('td:first-child').text();
        currentModule.getBlueprintsByNameAndAuthor(author, bpName, function(bp) {
            currentBp = bp;
            var points = currentBp.points;
            var canvas = document.getElementById("myCanvas");
            var ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            var currentPoint;
            var nextPoint;
            for (var i = 0; i < points.length - 1; i++) {
                if (points.length == 1) {
                    currentPoint = points[i];
                    nextPoint = currentPoint;
                } else {
                    currentPoint = points[i];
                    nextPoint = points[i + 1];
                }
                ctx.beginPath();
                ctx.moveTo(currentPoint.x, currentPoint.y);
                ctx.lineTo(nextPoint.x ,nextPoint.y);
                ctx.stroke();
                ctx.closePath();
            }
            $("#bpNameText").text("Current Blueprint: " + bpName);
        });
      },

      getClicks: function(buttonElement){
        var canvas = document.getElementById("myCanvas");
        var ctx = canvas.getContext("2d");
        var points = currentBp.points;
        var pointToAdd;
        var canvasRect = canvas.getBoundingClientRect();
        if(window.PointerEvent) {
            canvas.addEventListener("pointerdown", function(event){
                pointToAdd = {"x":event.pageX - canvasRect.left, "y":event.pageY - canvasRect.top};
                points.push(pointToAdd);
                tempPoints = points;
                blueprintsModule.fakePaintBp(points);
            });
        } else {
            canvas.addEventListener("mousedown", function(event){
                pointToAdd = {"x":event.clientX  - canvasRect.left,"y":event.clientY  - canvasRect.top};
                points.push(pointToAdd);
                tempPoints = points;
                blueprintsModule.fakePaintBp(points);
            });
        }
        $("#saveButton").remove();
        var newButton = $("<button id='saveButton' type='button' onclick='blueprintsModule.chainedPromises();'>Save/Update</button>");
        $("#buttonDiv").append(newButton);
        $("#deleteButton").remove();
        var newDelButton = $("<button id='deleteButton' type='button' onclick='blueprintsModule.chainedPromisesToDelete();'>Delete</button>");
        $("#buttonDiv").append(newDelButton);
      },

      loadBlueprintsModuleFunctions: function(buttonElement) {
        // Llama a blueprintsModule.paintBp
        blueprintsModule.paintBp(buttonElement);

        // Espera un tiempo (ajusta esto según tus necesidades)
        setTimeout(function() {
          // Llama a blueprintsModule.getClicks después de blueprintsModule.paintBp
          blueprintsModule.getClicks(buttonElement);
        }, 1000); // Espera 1 segundo (ajusta este tiempo según sea necesario)
      },

      fakePaintBp: function(points) {
        var canvas = document.getElementById("myCanvas");
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        var currentPoint;
        var nextPoint;
        for (var i = 0; i < points.length - 1; i++) {
            if (points.length == 1) {
                currentPoint = points[i];
                nextPoint = currentPoint;
            } else {
                currentPoint = points[i];
                nextPoint = points[i + 1];
            }
            ctx.beginPath();
            ctx.moveTo(currentPoint.x, currentPoint.y);
            ctx.lineTo(nextPoint.x ,nextPoint.y);
            ctx.stroke();
            ctx.closePath();
        }
      },

      chainedPromises: function() {
        putBlueprints().then(function() {
            blueprintsModule.setAuthor(author);
          });
      },

      chainedPromisesToCreate: function() {
        var answer = prompt("Please, type the blueprint's name: ");
          // Verifica si el usuario ingresó un valor y maneja la respuesta
        if (answer !== null) {
            alert("The " + answer + " blueprint was created!");
            createBlueprint(answer).then(function() {
                return blueprintsModule.setBpsByAuthor(author);
            }).then(function() {
                setTimeout(function() {
                    var newRow = $("#blueprintTable tr").filter(function() {
                        var bpValue = $(this).find("td:first").text();
                        return bpValue === answer;
                    });
                    var newButton = newRow.find("button");
                    blueprintsModule.loadBlueprintsModuleFunctions(newButton);
                }, 1000);
            });
        } else {
            alert("You didn't type the name.");
        }
      },

      chainedPromisesToDelete: function() {
        deleteBlueprint().then(function() {
            blueprintsModule.setAuthor(author);
        }).then(function() {
            $("#saveButton").remove();
            $("#deleteButton").remove();
            currentBp = null;
            tempPoints = null;
            var canvas = document.getElementById("myCanvas");
            var ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            $("#bpNameText").text("Current Blueprint: ");
            canvas.onclick = null;
            canvas.onmousedown = null;
            canvas.onpointerdown = null;
        });
      }
    }
})();

//blueprintsModule.setAuthor("johnconnor");
//blueprintsModule.setBpsByAuthor("johnconnor");