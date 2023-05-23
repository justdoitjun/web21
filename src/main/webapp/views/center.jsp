<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    let weatherRequest = {
        init:()=>{
            $.ajax({
                url:'/weather3',
                success:(data)=>{
                    console.log(data);
                    weatherRequest.display(data);
                },
                fail:()=>{}
            })//ajax 요청
        },//
        display:(data)=>{
            //parsing 시작
            //이렇게 객체를 열어서 열어도 되고
            //let result = data["response"]["body"]["items"]["item"][0]["wfSv"];
            //이렇게 열어도 되고...
            // let result = data.response.body.items.item[0].wfSv;
            // console.log(result);
            // $('#weather2').html(result);
            // $('#w2').val(result);

            //let result = data.response.body.items.item;
            let result = data["response"]["body"]["items"]["item"];

            let txt = '';
            $(result).each((index, item)=>{
                txt += '<h5>';
                txt += item.ta;
                txt += '</h5>';
            })
            $('#w2').html(txt);
        }
    }


    $(()=>{
        weatherRequest.init();
    });//document on Ready
</script>

<style>
    #w2{
        width:500px;
        border:2px solid red;
    }
</style>



<div class="col-sm-8 text-left">
    <h1>Welcome</h1>
    <hr>
    <h2> 서버에서 보내준 녀석</h2>
    <textarea id="w1" cols="100" rows="10" >
        ${weatherinfo}
    </textarea>
    <hr>
    <h2> 웹단에서 파싱한 녀석</h2>
<%--    <textarea id="w2" cols="100" rows="10">--%>
<%--    </textarea>--%>
    <div id="w2"></div>
</div>
