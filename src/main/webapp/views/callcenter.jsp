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
    let callcenter = {
        id:null,
        stompClient:null,
        init:function(){
            //this.id = $('#adm_id').text();
            this.id = 'user1'; // DB 문제로 강제로 넣겠음.
            $("#connect").click(function() {
                console.log("클릭이 잘됨");
                callcenter.connect();
            });
            $("#disconnect").click(function() {
                callcenter.disconnect();
            });
            $("#sendto").click(function() {
                callcenter.sendTo();
            });
        },
        connect:function(){
            var sid = this.id;
            //var socket = new SockJS('http://127.0.0.1:8088/ws');
            //관리자 Admin 서버로 접속 중.....왜 ws냐? admin에서 그렇게 약속함. MvC
            //var socket = new SockJS('${adminServer}/ws');
            var socket = new SockJS('http://172.16.20.129:8088/ws');

            // 해당 아이피로 요청하는 경우에는 내가 허용하겠어...

            this.stompClient = Stomp.over(socket);

            this.stompClient.connect({}, function(frame) {
                callcenter.setConnected(true);
                console.log('Connected: ' + frame);
                this.subscribe('/send/to/'+sid, function(msg) {
                    $('#target').val(JSON.parse(msg.body).sendid);
                    $("#to").prepend(
                        "<h4>" + JSON.parse(msg.body).sendid +":"+
                        JSON.parse(msg.body).content1
                        + "</h4>");
                });
            });
        },
        disconnect:function(){
            if (this.stompClient !== null) {
                this.stompClient.disconnect();
            }
            callcenter.setConnected(false);
            console.log("Disconnected");
        },
        setConnected:function(connected){
            if (connected) {
                $("#status").text("Connected");
            } else {
                $("#status").text("Disconnected");
            }
        },
        sendTo:function(){
            var msg = JSON.stringify({
                // 'sendid' : this.id,
                'sendid' : 'user1',
                'receiveid' : $('#target').val(),
                'content1' : $('#totext').val()
            });
            this.stompClient.send('/receiveto', {}, msg);
        }

    };
    $(function(){
        callcenter.init();
    })

</script>
<!-- Begin Page Content -->



    <!-- DataTales Example -->
    <div class="col-sm-8 text-left">


            <div class="col-sm-5">
                <H1 >Call center</H1>
                <h1 id="adm_id">${logincust.id}</h1>
                <h1> user1 </h1>
                <H1 id="status">Status</H1>
                <button id="connect">Connect</button>
                <button id="disconnect">Disconnect</button>


                <h3>To</h3>
                <input type="text" id="target">
                <input type="text" id="totext"><button id="sendto">Send</button>
                <div id="to"></div>

            </div>

    </div>
    <!-- /.container-fluid -->
