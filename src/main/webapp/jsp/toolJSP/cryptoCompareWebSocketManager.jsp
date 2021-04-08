<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>

<p>add channel</p>
<input type="text" name="" id="addChannelInput" style="width: 340px;" 
placeholder=""><label>example: 5~CCCAGG~BTC~USD</label>
<button id="addChannel">addChannel</button><br>
<br>
<p>remove channel</p>
<input type="text" name="" id="removeChannelInput" style="width: 340px;" 
placeholder=""><label>example: 5~CCCAGG~BTC~USD</label>
<button id="removeChannel">removeChannel</button><br>
<br>
<button id="removeAllSubscription">removeAllSubscription</button><br>
<br>
<button id="syncSubscription">syncSubscription</button>
<br>
<button id="destoryWS">destoryWS</button>

</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#addChannel").click( function() {
      var url = "/cryptoCompareWS/addChannel";
      var addChannel = $("#addChannelInput").val();

      $.get(
        url,
        {channel : addChannel},
      );
    });

    $("#removeChannel").click( function() {
      var url = "/cryptoCompareWS/removeChannel";
      var removeChannel = $("#removeChannelInput").val();

      $.get(
        url,
        {channel : removeChannel},
      );
    });

    $("#removeAllSubscription").click( function() {
      var url = "/cryptoCompareWS/removeAllSubscription";
      $.get(
        url,
      );
    });

    $("#syncSubscription").click( function() {
      var url = "/cryptoCompareWS/syncSubscription";
      $.get(
        url,
      );
    });


    $("#destoryWS").click( function() {
      var url = "/cryptoCompareWS/destory";
      $.get(
        url,
      );
    });

  });

</script>
</footer>
</html>