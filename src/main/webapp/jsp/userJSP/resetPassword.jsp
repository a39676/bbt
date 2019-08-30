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
  <div style="display: none">
    <label name="mailKey">${mailKey}</label>
  </div>

  <div>
    <div class="form-group">
      <span name="pwd">请输入新密码</span>
      <input type="password" id="pwd" class="form-control"/>
    </div>

    <div class="form-group">
      <span name="pwdRepeat">重复输入一次密码</span>
      <input type="password" id="pwdRepeat" class="form-control"/>
    </div>

    <div>
      <button class="btn  btn-warning  btn-sm" name="resetPassword">
        <span style="font-size: smaller;">确认重置密码</span>
      </button>
    </div>
  </div>


  <div>
    <span id="resetPasswordResult" style="font-size: smaller"></span>
  </div>
  <div id="loginView">
    
  </div>
</body>  
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("button[name='resetPassword']").click(function() {
        
      var url = "${pageContext.request.contextPath}/user/resetPassword";

      var jsonOutput ={
        newPassword : $("#pwd").val(),
        newPasswordRepeat : $("#pwdRepeat").val(),
        mailKey : $("label[name='mailKey']").text()
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
          $("#resetPasswordResult").text(datas.message);
        },  
        error: function(datas) {  
          
        }  
      });  
    
    });

  });
  </script>
</footer>
</html>