<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.css" rel="stylesheet">
 
<sec:csrfMetaTags />
<title>${ title }</title>

<label>message:</label><input type="text" name="" id="testMessage">${message}<br> 

<div id="main-content" class="container">
  <div class="row">
    <div class="col-md-6">
      <form class="form-inline">
        <div class="form-group">
          <label for="connect">WebSocket connection:</label>
          <button id="connectToPublic" class="btn btn-default" type="submit">Connect To Public</button>
          <button id="disconnectPublic" class="btn btn-default" type="submit" disabled="disabled">Disconnect
          </button>
        </div>
        <div class="form-group">
          <button id="connectToSingle" class="btn btn-default" type="submit">Connect To Single</button>
          <button id="disconnectSingle" class="btn btn-default" type="submit" disabled="disabled">Disconnect
          </button>
        </div>
      </form>
    </div>
    <div class="col-md-6">
      <form class="form-inline">
        <div class="form-group">
          <label for="singleTopicRoom">What is your room?</label>
          <input type="text" id="singleTopicRoom" class="form-control" placeholder="Your roomName here...">
        </div>
        <button id="sendToSingle" class="btn btn-default" type="submit">sendToSingle</button>
      </form>
      <form class="form-inline">
        <div class="form-group">
          <label for="inputContent">What is your content?</label>
          <input type="text" id="inputContent" class="form-control" placeholder="Your content here...">
        </div>
        <button id="sendToPublic" class="btn btn-default" type="submit">sendToPublic</button>
      </form>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <table id="conversation" class="table table-striped">
        <thead>
        <tr>
          <th>Message</th>
        </tr>
        </thead>
        <tbody id="receiveMessageList">
        </tbody>
      </table>
    </div>
  </div>
</div>

<footer>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.1.1/jquery.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.3/js/tether.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.js"></script>
<script src="https://cdn.bootcss.com/sockjs-client/1.1.0/sockjs.min.js"></script>
<script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
<!-- csrf part -->
<script type="text/javascript">
  var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
  var csrfHeader = $("meta[name='_csrf_header']").attr("content");
  var csrfToken = $("meta[name='_csrf']").attr("content");
</script>
<script type="text/javascript">

$(document).ready(function() {
var stompClient = null;


function setConnectedPublic(connected) {
  $("#connectToPublic").prop("disabled", connected);
  $("#disconnectPublic").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  }
  else {
    $("#conversation").hide();
  }
  $("#receiveMessageList").html("");
}

function setConnectedSingle(connected) {
  $("#connectToSingle").prop("disabled", connected);
  $("#disconnectSingle").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  }
  else {
    $("#conversation").hide();
  }
  $("#receiveMessageList").html("");
}

function connectPublic() {
  var socket = new SockJS('/topic/public');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnectedPublic(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/public', function (greeting) {
      showMessage(JSON.parse(greeting.body).content);
    });
  });
}

function connectSingle() {
  var socket = new SockJS('/topic/single');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnectedSingle(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/single', function (singleMessage) {
      showMessage(JSON.parse(singleMessage.body).content);
    });
  });
}

function disconnectPublic() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnectedPublic(false);
  console.log("Disconnected");
}

function disconnectSingle() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnectedSingle(false);
  console.log("Disconnected");
}

function sendToPublic() {
  var nameOutput = $("#inputContent").val();
  console.log("inputContent: " + nameOutput);
  console.log("stompClient: " + stompClient);
  stompClient.send("/app/public", {}, JSON.stringify({'inputContent': nameOutput}));
}

function showMessage(message) {
  $("#receiveMessageList").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $( "#connectToPublic" ).click(function() { connectPublic(); });
  $( "#disconnectPublic" ).click(function() { disconnectPublic(); });
  $( "#sendToPublic" ).click(function() { sendToPublic(); });
});

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $( "#connectSingle" ).click(function() { connectSingle(); });
  $( "#disconnectSingle" ).click(function() { disconnectSingle(); });
  $( "#sendToSingle" ).click(function() { sendToPublic(); });
});

});

 </script>
</footer>