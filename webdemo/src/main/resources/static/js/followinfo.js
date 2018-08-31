//用于ajax请求获取用户的关注
$("#submit").click(function () {
    var mid = $("#mid").val();
    // console.log("运行了");
    // console.log(mid);
    $("#error").text("");
    if(mid == "")  {
        $("#error").text("不可为空");
    } else if(isNaN(mid)) {
        $("#error").text("请输入数字");
    } else {
        $.ajax({
            url:"/bili/getfollow",
            type:"POST",
            data:$("#queryinfo").serialize(),
            cache:false,
            dataType:"text",
            success:function (data) {
                console.log(data);
                var info = JSON.parse(data);
                for(var k in info) {
                    var str="#";
                    str+=k;
                    if(str=="#face"){
                        $(str).attr("src",info[k]);
                    }
                    if(str=="#error") {
                        $(str).text(info[k]);
                    }
                    $(str).val(info[k]);
                }
                // console.log(data.get("regtime"));
                // $("#datashow").text(data);
            },
            error:function () {
                console.log("error")
            }
        });
    }
});


