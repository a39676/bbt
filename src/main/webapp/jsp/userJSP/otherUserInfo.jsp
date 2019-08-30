<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="col-sm-6">
  <div class="container-fluid border border-success rounded">
    <div class="row" name="moreUserDetail" nickName="${userDetail.nickName}" pk="${pk}">
      <div class="col-sm-6">
        <span name="nickName" class="badge badeg-light">昵称</span>
        <span class="badge badge-info">${userDetail.nickName}</span>
        <c:choose>
          <c:when test="${userDetail.gender == '男'}">
            <i class="fa fa-male fa-lg" style="color: rgb(255, 82, 57);" ></i>
            <span class="badge badeg-light">${userDetail.gender}</span>
          </c:when>
          <c:when test="${userDetail.gender == '女'}">
            <i class="fa fa-female fa-lg" style="color: rgb(0, 157, 238);" ></i>
            <span class="badge badeg-light">${userDetail.gender}</span>
          </c:when>
          <c:otherwise>
            <i class="fa fa-transgender-alt fa-lg" style="color: rgb(0, 0, 0);" ></i>
            <span class="badge badeg-light">${userDetail.gender}</span>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="col-sm-6">
        <span class="badge badge-info badge-pill" nickName="${userDetail.nickName}" name="closeMoreUserDetail" style="cursor: pointer;">收起</span>
      </div>
    </div>
    
    <c:choose>
      <c:when test="${userDetail.privateMessage.length() > 0}">
          <div class="row">
            <div class="col-sm-6">
              <span class="badge badeg-light">${userDetail.privateMessage}</span> 
            </div>
          </div>
      </c:when>
      <c:otherwise>
        <div class="row">
          <div class="col-sm-6">
            <span name="email" class="badge badeg-light">email<i class="fa fa-envelope-o fa-lg" style="color: rgb(0, 0, 0);" ></i></span>
            <span class="badge badge-info">${userDetail.email}</span>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6">
            <span name="qq" class="badge badeg-light">qq号</span>
            <span class="badge badge-info">${userDetail.qq}</span>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6">
            <span name="mobile" class="badge badeg-light">手机号<i class="fa fa-mobile-o fa-lg" style="color: rgb(0, 0, 0);" ></i></span>
            <span class="badge badge-info">${userDetail.mobile}</span>
          </div>
        </div>
      </c:otherwise>
    </c:choose>    
  </div>
</div>

<script type="text/javascript">

  $(document).ready(function() {

    $("span[name='closeMoreUserDetail']").click(function () {
      $(".moreUserDetail").fadeOut();
    });

  });

</script>