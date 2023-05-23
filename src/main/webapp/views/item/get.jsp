<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    let item_get = {
        init:()=>{

            $('#cart_btn').click(()=>{
                let cust_id = '${logincust.id}';
                let item_id = ${gitem.id};
// <!-- cust_id는 String이라서 싱글 쿼테이션 쓰고,
// item_id는 숫자라서 안 씀 -->
                let cnt = $('#cnt').val();
                $.ajax({
                    url:'/addcart',
                    data:{
                        cust_id : cust_id,
                        item_id : item_id,
                        cnt : cnt
                    }, //json 형태로 보내자.
                    success:()=>{
                        // alert('sent complete');
                        $('#myModal').modal();
                    }// 성공시
                })// ajax ends.


                // $('#cart_form').attr({
                //     method: 'get',
                //     action : "/item/addcart"
                // });
                // $('#cart_form').submit();
            })//cart_btn
        }//item_get.init
    };// item_get 선언
    $(()=>{
        item_get.init();
    });//document.ready 선언
</script>

<div class="col-sm-8 text-left">
    <h1> ${gitem.id} 번째 상품입니다. </h1>

    <h4>가격   : ${gitem.price}</h4>
    <h4>이름   : ${gitem.name}</h4>
    <h4>등록일  : ${gitem.rdate}</h4>

    <c:if test="${logincust != null}">
        <form class="form-inline well" id="cart_form" action="">
            <input type="hidden" name="cust_id" value="${logincust.id}">
            <input type="hidden" name="item_id" value="${gitem.id}">
            <input type="hidden" name="item_id" value="${gitem.name}">
            <input type="hidden" name="item_id" value="${gitem.price}">
            <!-- 왜 이렇게 히든 태그를 쓰는가? 같이 한번에 서버로 보내긴 해야되는데 화면에 안나왔으면 할 때-->

            <div class="form-group">
                <label class="control-label col-sm-4" for="cnt">Count 수량 :</label>
                <div class="col-sm-10">
                    <input type="number" class="form-control" id="cnt" placeholder="수량?" name="cnt">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" id="cart_btn" class="btn btn-primary">Cart에 담자</button>
                </div>
            </div>
        </form>
    </c:if>

</div>




<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 style="color:red;"><span class="glyphicon glyphicon-lock"></span> Login</h4>
            </div>
            <div class="modal-body">
                <a href="/item/allcart?id=${logincust.id}" class="btn btn-info" role="button">장바구니로 이동</a>
                <a href="/item/allpage" class="btn btn-info" role="button">계속 쇼핑하시겠습니까</a>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-default btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
                <p>Not a member? <a href="#">Sign Up</a></p>
                <p>Forgot <a href="#">Password?</a></p>
            </div>
        </div>
    </div>
</div>