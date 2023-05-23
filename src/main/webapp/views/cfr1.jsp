<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://fonts.googleapis.com/css2?family=Bitter:wght@600&family=Nanum+Myeongjo:wght@800&display=swap"
      rel="stylesheet">
<script>

    let cfr1_form = {
        init: function () {
            $("#cfr1_btn").click(function () {
                cfr1_form.send(); // this.send() 안됨! 유의
            });
        },
        // 서버로 보내기
        send: function () {
            $("#cfr1_form").attr({
                'action': '/cfr1impl',
                'method': 'post'
            });
            $("#cfr1_form").submit();
        }
    };
    // web04 - webapp - views = login.jsp

    // 화면 로딩
    $(function () {
        cfr1_form.init();
    });

</script>
<div class="col-sm-8 text-left">
    <div class="container">
        <div class="row content">
            <div class="col-sm-6 text-left">
                <div>
                    <h2 style="text-align: center">CFR1</h2>
                </div>
                <br><br><br>
                <form class="form-horizontal" id="cfr1_form" action="/cfr1impl" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="img">Image:</label>
                        <div class="col-sm-10">
                            <input type="file" class="form-control" id="img" placeholder="Upload your image" name="img">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="button" id="cfr1_btn" class="btn btn-primary">upload</button>
                        </div>
                    </div>
                </form>

                <div id="result">
                    <h4 id="textresult">찾으신 유명인의 이름은 : ${name} 입니다. </h4>
                </div>

            </div>
        </div>
    </div>
</div>