<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
<style>

</style>
</head>
<body>

  <div id="login-box">

    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
      <div class="message">${message}</div>
    </c:if>

    <!-- <form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'> -->
    <form name='loginForm' action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />" method='POST'>

      <table>
        <tr>
          <td>用户名:</td>
          <td><input type='text' name='user_name' value=''></td>
        </tr>
        <tr>
          <td>密码:</td>
          <td><input type='password' name='pwd' /></td>
        </tr>
        <!-- if this is login for update, ignore remember me check -->
<%-- 
        <c:if test="${empty loginUpdate}">
          <tr>
            <td colspan="2">Remember Me: <input type="checkbox" name="remember-me" /></td>
          </tr>
        </c:if>
         --%>
        <tr>
          <td colspan='1'>
            <input name="submit" type="submit" value="登录" />
          </td>
          <td colspan="1">
            <a href="${pageContext.request.contextPath}/user/userRegist">[注册]</a>
          </td>
        </tr>
      </table>

      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
  </div>

</body>
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
  
});

</script>
</footer>
</html>