<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../baseElementJSP/normalHeader.jsp" %>
  <title>${title}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Stylesheets -->

</head>
<body>

  <%@ include file="../baseElementJSP/headerCustom.jsp" %>

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12" id="dynamicArea">
      </div>

    </div>
  </div>
  <!-- main container -->

  <!-- SCIPTS -->
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>

</body>
</html>
