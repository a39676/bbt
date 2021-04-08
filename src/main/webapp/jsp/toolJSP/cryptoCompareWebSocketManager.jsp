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
<button id="destoryWS">destoryWS</button>

</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#addChannel").click( function() {
      addChannel();
    });

    function addChannel() {
      var url = "/cryptoCompareWS/addChannel";
      var addChannel = $("#addChannelInput").val();

      $.get(
        "/cryptoCompareWS/addChannel",
        {channel : addChannel},
      );
    };

    $("#removeChannel").click( function() {
      removeChannel();
    });

    function removeChannel() {
      var url = "/cryptoCompareWS/removeChannel";
      var removeChannel = $("#removeChannelInput").val();

      $.get(
        "/cryptoCompareWS/removeChannel",
        {channel : removeChannel},
      );
    };

    $("#removeAllSubscription").click( function() {
      removeAllSubscription();
    });

    function removeAllSubscription() {
      var url = "/cryptoCompareWS/removeAllSubscription";
      $.get(
        "/cryptoCompareWS/removeAllSubscription",
      );
    };

    $("#destoryWS").click( function() {
      destoryWS();
    });

    function destoryWS() {
      var url = "/cryptoCompareWS/destory";
      $.get(
        "/cryptoCompareWS/destory",
      );
    };
   
  });

</script>
</footer>
</html>