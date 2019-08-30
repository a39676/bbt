<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- 文字图标 --%>
<%-- <link href="<c:url value='/static_resources/css/bootstrapIcon/font-awesome.css' />" rel="stylesheet"> --%>
<%-- <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet"> --%>
</head>
<body>  

  <div>
    <label name="message"></label>
  </div>

  <div>

    <div class="form-group">
      <span name="nickName" >昵称</span>
      <input disabled="disabled" id="nickName" class="form-control" placeholder="${nickName}" />

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

    <div class="form-group">
      <span name="email" >email<i class="fa fa-envelope-o fa-lg" style="color: rgb(0, 0, 0);" ></i></span>
      <input disabled="disabled" id="email" class="form-control" placeholder="${email}" />
      <c:if test="${not empty notActive}">
        <input  class="form-control" type="text" disabled="disabled" name="mailKey" value="请使用注册邮箱发送 激活码: ${mailKey} 至: ${adminMail}">
        <button class="btn btn-default" name="modifyRegistMailApply">更换注册邮箱</button>
      </c:if>
      <span name="resendRegistMailResult"></span>
    </div>

    <div class="form-group" name="modifyRegistMail" style="display: none">
      <span name="modifyRegistMail" >更换注册邮箱<i class="fa fa-envelope-o fa-lg" style="color: rgb(0, 0, 0);" ></i></span>
      <input id="modifyRegistMail" class="form-control" name="modifyRegistMail" placeholder="请输入新的注册邮箱" />
      <button class="btn btn-default" name="modifyRegistMail">确认更换</button>
      <span name="modifyRegistMailResult"></span>
    </div>

    <div class="form-group">
      <button class="btn btn-default" name="resetPasswordApply">更改密码</button>
    </div>

    <div class="form-group" name="resetPassword" style="display: none">
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">请输入原密码</span>
        </div>
        <input type="password" class="form-control" name="oldPassword">
      </div>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">请输入新密码</span>
        </div>
        <input type="password" class="form-control" name="newPassword">
      </div>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="basic-addon1">请再次输入密码</span>
        </div>
        <input type="password" class="form-control" name="newPasswordRepeat">
      </div>
      <button class="btn btn-default" name="resetPassword">确认更改</button>
      <span name="resetPasswordResult"></span>
    </div>

    <div class="form-group">
      <span name="qq" >qq号</span>
      <input disabled="disabled" id="qq" class="form-control" placeholder="${qq}" />
    </div>

    <div class="form-group">
      <span name="mobile" >手机号<i class="fa fa-mobile-o fa-lg" style="color: rgb(0, 0, 0);" ></i></span>
      <input disabled="disabled" id="mobile" class="form-control" placeholder="${mobile}" />
    </div>

    <div class="form-group">
      <span name="reservationInformation" >预留信息<i class="fa fa-file-text fa-lg" style="color: rgb(0, 0, 0);" ></i></span>
      <input disabled="disabled" id="reservationInformation" class="form-control" placeholder="${reservationInformation}" />
    </div>


  </div>


  <div>
    <span id="registResult" style="font-size: smaller"></span>
  </div>
  <div id="loginView">
    
  </div>
</body>  
<footer>
<script type="text/javascript">

  $(document).ready(function() {

    $("button[name='modifyRegistMailApply']").click(function() {
      $("div[name='modifyRegistMail']").show();
    });


    $("button[name='modifyRegistMail']").click(function() {
      
      var modifyRegistMail = $("input[name='modifyRegistMail']").val();
      $("input[name='modifyRegistMail']").attr('disabled','disabled');
      $("button[name='modifyRegistMail']").attr('disabled','disabled');
      var url = "${pageContext.request.contextPath}/user/modifyRegistMail";
      var jsonOutput = {
        modifyRegistMail : modifyRegistMail
      };


      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data: JSON.stringify(jsonOutput),
        cache : false,
        timeout:50000,  
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("span[name='modifyRegistMailResult']").text(datas.message);
          if (datas.result != 0) {
            $("input[name='modifyRegistMail']").removeAttr('disabled');
            $("button[name='modifyRegistMail']").removeAttr('disabled');
          }
        },  
        error: function(datas) {  
          
        }  
      });  
    
    });

    $("button[name='resetPasswordApply']").click(function() {
      $("div[name='resetPassword']").show();
    });

    $("button[name='resetPassword']").click(function() {
        
      var url = "${pageContext.request.contextPath}/user/resetPassword";

      var oldPassword = $("input[name='oldPassword']").val();
      var newPassword = $("input[name='newPassword']").val();
      var newPasswordRepeat = $("input[name='newPasswordRepeat']").val();

      var jsonOutput = {
        oldPassword:oldPassword,
        newPassword:newPassword,
        newPasswordRepeat:newPasswordRepeat
      }

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        cache : false,
        timeout:50000,  
        data: JSON.stringify(jsonOutput),
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("span[name='resetPasswordResult']").text(datas.message);
        },  
        error: function(datas) {  
          
        }  
      });  
    
    });
    
  });
  </script>
</footer>
</html>