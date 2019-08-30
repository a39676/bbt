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
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>  

  <div>
    <label name="message"></label>
  </div>

  <div>
    <div class="form-group">
      <span name="email">email<i class="fa fa-envelope-o fa-lg" style="color: rgb(0, 0, 0);" ></i></span>
      <input class="form-control" name="email" placeholder="请输入注册时的邮箱" />
      <span name="sendForgetPasswordMailResult"></span>
    </div>

    <div class="btn-group">
      <button class="btn  btn-primary  btn-sm" name="sendForgetPasswordMail">
        <span class="badge badge-warning">发送重置密码邮件</span>
      </button>
      <button class="btn  btn-success  btn-sm" name="sendForgetUsernameMail">
        <span class="badge badge-warning">发送用户名邮件</span>
      </button>
    </div>
  </div>


  <div>
    <span id="registResult" style="font-size: smaller"></span>
  </div>
  <div id="loginView">
    
  </div>
</body>  
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("button[name='sendForgetPasswordMail']").click(function() {
      var url = "${pageContext.request.contextPath}/user/forgotPassword";
      sendMail(url);
    });

    $("button[name='sendForgetUsernameMail']").click(function() {
      var url = "${pageContext.request.contextPath}/user/forgotUsername";
      sendMail(url); 
    });

    function sendMail(url) {
      var jsonOutput ={
        email:$("input[name='email']").val()
      };

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
          $("span[name='sendForgetPasswordMailResult']").text(datas.message);
        },  
        error: function(datas) {  
          
        }  
      });  
    }

  });
  </script>
</footer>
</html>