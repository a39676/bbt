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
<body name="errorCustomBody">

  <div class="col-md-12">

    <span style="font-size: smaller;">${message}</span><br>
    <button class="btn  btn-warning  btn-sm" name="urlButton" url="${urlRedirect}">
      <span style="font-size: smaller">[回到主页]</span>
    </button>

  </div>

</body>
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">
$(document).ready(function() {

  $("button[name='urlButton']").click(function () {

    location.reload();

  });

});

</script>
</footer>
</html>