<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
<style>

</style>
</head>
<body>

  <div class="col-md-12" id="login-box">

    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
      <div class="message">${message}</div>
    </c:if>



    <div class="form-group">
      <span style="font-size: smaller;">用户名: </span>
      <input type="text" name="user_name" class="form-control" />
    </div>  
    <div class="form-group">
      <span style="font-size: smaller;">密码: </span>
      <input type="password" name="pwd" class="form-control" />
    </div>
    <!-- if this is login for update, ignore remember me check -->
    <%-- 
    <c:if test="${empty loginUpdate}">
      <tr>
        <td colspan="2">Remember Me: <input type="checkbox" name="remember-me" /></td>
      </tr>
    </c:if>
      --%>

    <button class="btn  btn-warning btn-sm" name="loginSubmit">
      <span class="badge badge-warning">[登录]</span>
    </button>        
    <button class="loginCustomButton btn btn-warning btn-sm" name="userRegist" url="${pageContext.request.contextPath}/user/userRegist">
      <span class="badge badge-warning">[注册]</span>
    </button>
    <button class="loginCustomButton btn btn-warning btn-sm" name="forgotPassword" url="${pageContext.request.contextPath}/user/forgotPasswordOrUsername">
      <span class="badge badge-warning">[忘记密码/用户名]</span>
    </button>

    <div>
      <span name="loginMessage" style="font-size: small;"></span>
    </div>

  </div>

</body>
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">
$(document).ready(function() {

  $("button[name='loginSubmit']").click(function () {

    var loginUrl = "${pageContext.request.contextPath}/auth/login_check";
    var userName = $("input[name='user_name']").val();
    var pwd = $("input[name='pwd']").val();

    $.ajax({               
      type: "POST",  
      url: loginUrl,
      data: {
        user_name:userName,
        pwd:pwd
      },
      dataType: 'json',
      // contentType: "application/json",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      timeout: 30000,
      success:function(data){  
        if(data.code == 0) {
          location.reload();
        } else {
          if(data.message.length < 1) {
            $("span[name='loginMessage']").text("账户名或密码错误");
          } else {
            $("span[name='loginMessage']").text(data.message);
          }
        }
      }, 
      error:function(e){

      }
    });
  });

  $(".loginCustomButton").click(function () {
    loginCustomButtonClick($(this).attr("url")); 
  });

  function loginCustomButtonClick(buttonUrl){
    $.ajax({  
      type : "GET",  
      async : true,
      url : buttonUrl,  
      success:function(datas){  
          $("div[name='subBodyRow']").html(datas);
          $("div[name='subBodyRow']").show(); 
      },  
      error: function(datas) {                
      }  
    });  
  }

});

</script>
</footer>
</html>