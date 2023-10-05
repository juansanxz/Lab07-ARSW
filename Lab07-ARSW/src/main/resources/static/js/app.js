var blueprintsModule = (function()  {
    var author;
    var bps;
    var currentBp;
    var currentModule = apiclient;

    return {
      setAuthor: function(newAuthor) {
        author = newAuthor;
        blueprintsModule.setBpsByAuthor(author);
        $("#authorNameText").text("Name of Author: " + author);
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
                newRow.append("<td><button onclick='blueprintsModule.paintBp(this)'>Open</button></td>");
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

      }
    }
})();

//blueprintsModule.setAuthor("johnconnor");
//blueprintsModule.setBpsByAuthor("johnconnor");