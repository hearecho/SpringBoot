$(document).ready(function() {
    function wirteToScreen(message) {
        var current = $("#show").val()
        strjson = JSON.parse(message)
        var myDate = new Date()
        var addmessage = "  "+myDate+":\n"+strjson["message"]+"\n"
        if (strjson.hasOwnProperty("sendRole") ){
            addmessage = strjson["sendRole"] + addmessage
        } else {
            addmessage = "系统通知"+addmessage
        }
        $("#show").val(current+"\n"+addmessage)
    }

    $("#add").click(function(){
        var url = $("#url").val()
        wss = new WebSocket(url)

        wss.onopen = function() {
            console.log("已经连接")
        }
    
        wss.onmessage = function(event) {
            console.log(event.data)
            wirteToScreen(event.data)
        }

        wss.onerror = function(event) {
            console.log("出错了")
        }

        wss.onclose = function(event) {
            console.log("断开")
        }
        $("#close").click(function(){
            wss.close()
        })

        $("#send").click(function() {
            message = $("#message").val()
            $("#message").val()
            wss.send(message)
        })
    })

})