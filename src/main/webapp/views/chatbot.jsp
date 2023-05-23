<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    #all {
        width: 400px;
        height: 200px;
        overflow: auto;
        border: 2px solid red;
    }

    #me {
        width: 400px;
        height: 200px;
        overflow: auto;
        border: 2px solid blue;
    }

    #to {
        width: 400px;
        height: 200px;
        overflow: auto;
        border: 2px solid green;
    }
</style>

<script>
    let chatbot = {
        id:null,
        stompClient:null,
        init:function(){
            this.id = $('#adm_id').text();
            $("#connect").click(function() {
                console.log("클릭이 잘됨");
                chatbot.connect();
            });
            $("#disconnect").click(function() {
                chatbot.disconnect();
            });
            $("#sendall").click(function() {
                chatbot.sendAll();
            });
            $("#sendme").click(function() {
                chatbot.sendMe();
            });
            $("#sendto").click(function() {
                chatbot.sendTo();
            });
        },
        connect:function(){
            var sid = this.id;
            //var socket = new SockJS('http://127.0.0.1:8088/ws');
            //관리자 Admin 서버로 접속 중.....왜 ws냐? admin에서 그렇게 약속함. MvC
            var socket = new SockJS('${adminServer}/chbot');
            // (1) 서버에게 보내기 위해서 객체를 생성하고.
            // 해당 아이피로 요청하는 경우에는 내가 허용하겠어...

            this.stompClient = Stomp.over(socket);

            this.stompClient.connect({}, function(frame) {
                chatbot.setConnected(true);
                console.log('Connected: ' + frame);
                //서버가 나에게 /chsend/라고 보내면 그걸 내가 받을게.
            // (2) 서버로부터 받을 준비를 하겠어.
                this.subscribe('/chsend/'+sid, function(msg) {
                    $("#me").prepend(
                        "<h4>" + JSON.parse(msg.body).sendid +":"+
                        JSON.parse(msg.body).content1+ "</h4>");
                });//prepend라고 적었기 때문에 최신 것이 위에 쌓이는 것임.
                // 반대로 뒤로 붙이라고 했으면 최신 것은 아래로 쌓이겠지.
            });
        },
        disconnect:function(){
            if (this.stompClient !== null) {
                this.stompClient.disconnect();
            }
            chatbot.setConnected(false);
            console.log("Disconnected");
        },
        setConnected:function(connected){
            if (connected) {
                $("#status").text("Connected");
            } else {
                $("#status").text("Disconnected");
            }
        },
        sendMe:function(){
            var msg = JSON.stringify({
                'sendid' : this.id,
                'content1' : $('#metext').val()
            });
            this.stompClient.send("/chatbotme", {}, msg);
            //그렇다면, 관리자서버의 MsgController에서도 이 라우터가 작동해야한다.
            // 특히 이는 MessageMapping이라는 어노테이션이 잡는다.
            $('#me').append(
                '<h4>' + this.id + ':'+$('#metext').val()+'</h4>'
            );
            $('#metext').val(''); // 현재 내가 쓴 창은 지우자.
        }
    };
    $(function(){
        chatbot.init();
    })

</script>
<!-- Begin Page Content -->



    <!-- DataTales Example -->
    <div class="col-sm-8 text-left">


            <div class="col-sm-5">
                <H1 >Chat Bot</H1>
                <h1 id="adm_id">${logincust.id}</h1>
                <H1 id="status">Status</H1>
                <button id="connect">Connect</button>
                <button id="disconnect">Disconnect</button>

 

                <h3>Me</h3>
                <input type="text" id="metext"><button id="sendme">Send</button>
                <div id="me"></div>


            </div>

    </div>
    <!-- /.container-fluid -->
