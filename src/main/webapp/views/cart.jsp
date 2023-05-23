<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
  .medium_img{
    width:100px;
  }
</style>
<script>

  // let allcart = {
  //   init : ()=>{
  //     $('#delete_btn').click(()=>{
  //
  //     })//삭제버튼이 눌렸을 때
  //   }//init
  //
  // }
  //
  //
  // $(()=>{
  //   allcart.init();
  // })//document. onload()
</script>

<div class="col-sm-8 text-left">
  <div class="container">
    <div class="col-sm-6 text-left">


      <c:set var="total" value="0"/>

      <table class="table table-hover">
        <thead>
        <tr>
          <th> 카트번호 </th>
          <th>IMG</th>
          <th>상품 id</th>
          <th>상품명</th>
          <th>Price</th>
          <th>수량</th>
          <th>총 가격</th>
          <th>Regdate</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="obj" items="${allcart}">
          <tr>
            <td>${obj.id}</td>
            <td><img class="medium_img" src="/uimg/${obj.item_imgname}" ></td>
            <td>${obj.item_id}</td>
            <td>${obj.item_name}</td>
            <td><fmt:formatNumber value="${obj.item_price}" type="currency" /></td>
            <td>${obj.cnt}</td>
            <td>${obj.cnt * obj.item_price}</td>
            <td><fmt:formatDate  value="${obj.rdate}" pattern="dd-MM-yyyy" /></td>
            <td><a href="/item/delcart?id=${obj.id}" id="delete_btn"class="btn btn-danger" role="button">DELETE</a></td>
          </tr>

          <c:set var="total" value="${total +  (obj.cnt * obj.item_price)  }"/>

        </c:forEach>
        </tbody>
      </table>

      <!-- 카트에 들어간 전체 합계액 -->

<%--      <h4><c:out value= "${total} "></c:out></h4>--%>
      <h4>총 결제금액 : <fmt:formatNumber value="${total}" type="currency" /></h4>

    </div> <!--end of sm-6-->
  </div>

</div>

