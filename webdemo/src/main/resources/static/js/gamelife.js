var cellularArray;
var cells;
//根据参数控制页面显示的细胞阵列
function createTable(cellularArray) {
    //清空控件内容
    $("#table").empty();
    var rowCount = cellularArray.length;
    var colCount = cellularArray[rowCount-1].length;

    var table = $("<table border='1' float:center>");
    table.appendTo($("#table"));
    for (var i = 0; i < rowCount; ++i) {
        var tr = $("<tr></tr>");
        tr.appendTo(table);
        for (var j = 0; j < colCount; ++j) {
            var td;
            if (cellularArray[i][j] == 0) {
                td = $("<td class='td-white'  </td>");
            } else {
                td = $("<td class='td-black'  </td>");
            }
            td.appendTo(tr);
        }
    }
    $("#table").append("</table>");
}
$("#initButton").click(function () {
    var rowCount = $("#rowText").val();
    var colCount = $("#colText").val();
    $.ajax({
        url: "/lifegame/init",
        type: "GET",
        dataType: "text",
        data: {
            row: rowCount,
            col: colCount
        },
        success: function (result) {
            cellularArray = JSON.parse(result);
            createTable(cellularArray);
        }
    });
    $("#generateCount").val(0);
});

//繁衍
$("#generateButton").click(function () {
    var rowCount = $("#rowText").val();
    var colCount = $("#colText").val();
    var generateCount = $("#generateCount").val();
    // console.log(cellularArray)
    $.ajax({
        type: "POST",
        url: "/lifegame/generate",
        dataType: "text",
        data: {
            "data":JSON.stringify(cellularArray),
            "row":rowCount,
            "col":colCount,
        },
        success: function (result) {
            cellularArray = JSON.parse(result);
            createTable(cellularArray);
        }
    });
    $("#generateCount").val(parseInt(generateCount) + 1);
});

$("#countCleanButton").click(function () {
    $("#generateCount").val(0);
});


