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
  <label>${message}</label>
  <br>
  <label>userName: ${user.userName} </label>
  <br>
  <label>enabled: ${user.enabled} </label>
  <br>
  <label>accountNonLocked: ${user.accountNonLocked} </label>
  <br>
  <label>accountNonExpired: ${user.accountNonExpired} </label>
  <br>
  <label>credentialsNonExpired: ${user.credentialsNonExpired} </label>
  <br>
</body>  
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
</footer>
</html>