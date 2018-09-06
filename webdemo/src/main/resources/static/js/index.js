var cellularArray;
//根据参数控制页面显示的细胞阵列
function createTable(cellularArray) {
    //清空控件内容
    $("#table").empty();
    var rowCount = cellularArray.row;
    var colCount = cellularArray.col;
    var table = $("<table border='1' float:center>");
    table.appendTo($("#table"));
    var cells = cellularArray.cells;
    for (var i = 0; i < rowCount; ++i) {
        var tr = $("<tr></tr>");
        tr.appendTo(table);
        for (var j = 0; j < colCount; ++j) {
            var td;
            if (cells[i][j] == 0) {
                td = $("<td class='td-white' data-x='" + i + "' data-y='" + j + "' onclick=\"{checkCell(this)}\">" + "</td>");
            } else {
                td = $("<td class='td-black' data-x='" + i + "' data-y='" + j + "' onclick=\"{checkCell(this)}\">" + "</td>");
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
        url: "/backend/randInit",
        type: "GET",
        dataType: "text",
        data: {
            row: rowCount,
            col: colCount
        },
        success: function (result) {
            cellularArray = result;
            createTable(cellularArray);
        }
    });
    $("#generateCount").val(0);
});
$("#generateButton").click(function () {
    var rowCount = $("#rowText").val();
    var colCount = $("#colText").val();
    var generateCount = $("#generateCount").val();
    $.ajax({
        type: "POST",
        url: "/backend/generate",
        dataType: "JSON",
        contentType: "application/json",
        data: JSON.stringify(cellularArray),
        success: function (result) {
            cellularArray = result;
            createTable(cellularArray);
        }
    });
    $("#generateCount").val(parseInt(generateCount) + 1);
});
$("#countCleanButton").click(function () {
    $("#generateCount").val(0);
});
$("#cellCleanButton").click(function () {
    var rowCount = $("#rowText").val();
    var colCount = $("#colText").val();
    $.ajax({
        url: "/backend/empty",
        type: "GET",
        dataType: "JSON",
        contentType: "application/json",
        data: {
            row: rowCount,
            col: colCount
        },
        success: function (result) {
            cellularArray = result;
            createTable(cellularArray);
            $("#generateCount").val(0);
            $("#rowText").val(cellularArray.row);
            $("#colText").val(cellularArray.col);
        }
    });
});