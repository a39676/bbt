<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
<div>
  <label id="imageCacheHandledfiles">${imageCacheHandledfiles}</label>
</div>

<div>
  <button name="imageCacheProcess">process to sotre</button>
</div>

<div id="resultView"></div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("button[name='imageCacheProcess']").click( function() {

      imageCacheProcess();
    });

    function imageCacheProcess() {
      
      var url = "${pageContext.request.contextPath}/tool/database/imageCacheProcess";
      
      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        // data: JSON.stringify(jsonOutput),
        cache : false,
        // contentType: "application/json",
        // dataType: "json",
        timeout:5000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#resultView").html(datas);
        },  
        error: function(datas) {  
          $("#resultView").html(datas.responseText);
        }  
      });  
    };

  });

</script>
</footer>
</html>