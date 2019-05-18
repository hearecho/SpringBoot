$(document).ready(function() {
    function wirteToScreen(message) {
        var current = $("#show").val()
        addmessage = JSON.parse(message)
        $("#show").val(current+addmessage)
    }

    $("#add").click(function(){
        var url = $("#url").val()
        wss = new WebSocket(url)

        wss.onopen = function() {
            console.log("已经连接")
        }
    
        wss.onmessage = function(event) {
            console.log(event.data)
        }

        wss.onerror = function(event) {
            console.log("出错了")
        }

        $("#close").click(function(){
            wss.onclose = function(event) {
                console.log("断开")
            }
        })
    })

})