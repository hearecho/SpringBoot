$("#query").click(function () {
    var title = $("#title").val();
    var datetime = Date.parse($("#date").val());
    var type = $("#type").val();
    if(isNaN(datetime)) {
        datetime = 0;
    }
    // console.log(title);
    // console.log(datetime);
    // console.log(type);
    
    $.ajax({
        url:"",
        type:"GET",
        data:{
            title:title,
            datetime:datetime,
            type:type
        },
        cache:false,
        dataType:"text",
        success:function (result) {
            console.log("得到参数")
            console.log(result)
        },
        error:function () {

        }

    });

});

$("#insert").click(function () {
    var title = $("#title").val();
    var datetime = new Date().getTime();
    var type = $("#type").val();

    $.ajax({
        url:"/sql/insert",
        type:"GET",
        data:{
            title:title,
            datetime:datetime,
            type:type
        },
        cache:false,
        dataType:"text",
        success:function (result) {
            console.log("得到参数")
            console.log(result)
        },
        error:function () {

        }

    });
});

$("#update").click(function () {

});