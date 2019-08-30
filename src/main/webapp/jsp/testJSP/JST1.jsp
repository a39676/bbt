<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>

  <!-- 此处href资源受springMVC.xml mvc:resources 影响
     需以 c:url 标签形式引入
  -->
  <!-- <link href="<c:url value="/static_resources/css/bootstrap.css" />" rel="stylesheet" /> -->


</script>

<style type="text/css">
  footer{
  	display: block;
  	background: yellow;
  }
</style>
<title>JSTest1</title>
</head>
<body>
  ${testMessage }
  <%@ include file="JST2.jsp" %>
  <div>
  	<input id="button01" type="button" onclick="alertStr01()" value="button01_value" name="button01">
  </div>

  <div class="jumbotron text-center">
    <h1>My First Bootstrap Page</h1>
    <p>Resize this responsive page to see the effect!</p> 
    <img alt="静态图片测试" src="<c:url value="/static_resources/image/Icons.png" />" width="50" height="50"> 
  </div>
  
  <div class="container">
    <div class="row">
      <div class="col-sm-4">
        <h3>Column 1</h3>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
        <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
      </div>
      <div class="col-sm-4">
        <h3>Column 2</h3>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
        <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
      </div>
      <div class="col-sm-4">
        <h3>Column 3</h3>        
       <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit...</p>
        <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris...</p>
      </div>
    </div>
  </div>
</body>
<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript" src="<c:url value="/static_resources/js/js01.js" />">
</footer>
</html>