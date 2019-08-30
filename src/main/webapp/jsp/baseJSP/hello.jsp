<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@page session="false"%>
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
	<h1>Message : ${message}</h1>

	
</body>
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
</footer>
</html>