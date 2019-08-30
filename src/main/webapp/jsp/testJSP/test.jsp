<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.css" rel="stylesheet">
 
<sec:csrfMetaTags />
<title>${ title }</title>
<span style="font-size: smaller;">${message}</span><br>
<span style="font-size: smaller;">test jsp in webapp2</span><br>
<input type="text" name="" id="testMessage"><br> 
<button id="su2">提交2</button><br>
<button id="su3">提交3</button><br>
<input type="text" name="" id="testUrl" value="/product/findProductInfoPageForApp" style="width: 400px;"><br>
<input type="text" name="" id="key1" placeholder="key1"><input type="text" name="" id="value1" placeholder="value1"><br>
<input type="text" name="" id="key2" placeholder="key2"><input type="text" name="" id="value2" placeholder="value2"><br>
<input type="text" name="" id="key3" placeholder="key3"><input type="text" name="" id="value3" placeholder="value3"><br>
<input type="text" name="" id="key4" placeholder="key4"><input type="text" name="" id="value4" placeholder="value4"><br>
<input type="text" name="" id="key5" placeholder="key5"><input type="text" name="" id="value5" placeholder="value5"><br>
<input type="text" name="" id="key6" placeholder="key6"><input type="text" name="" id="value6" placeholder="value6"><br>
<input type="text" name="" id="key7" placeholder="key7"><input type="text" name="" id="value7" placeholder="value7"><br>
<input type="text" name="" id="key8" placeholder="key8"><input type="text" name="" id="value8" placeholder="value8"><br>
<input type="text" name="" id="key9" placeholder="key9"><input type="text" name="" id="value9" placeholder="value9"><br>
<input type="text" name="" id="key10" placeholder="key10"><input type="text" name="" id="value10" placeholder="value10"><br>
<input type="text" name="" id="key11" placeholder="key11"><input type="text" name="" id="value11" placeholder="value11"><br>
<input type="text" name="" id="key12" placeholder="key12"><input type="text" name="" id="value12" placeholder="value12"><br>

<button id="su">提交</button>
<p id="testResult">testResult</p>

<footer>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.1.1/jquery.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.3/js/tether.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.js"></script>
<!-- csrf part -->
<script type="text/javascript">
  var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
  var csrfHeader = $("meta[name='_csrf_header']").attr("content");
  var csrfToken = $("meta[name='_csrf']").attr("content");
</script>
<script type="text/javascript">

$(document).ready(function() {

$("#su").click(function () {
  var url = $("#testUrl").val();
  var key1 = $("#key1").val();
  var key2 = $("#key2").val();
  var key3 = $("#key3").val();
  var key4 = $("#key4").val();
  var key5 = $("#key5").val();
  var key6 = $("#key6").val();
  var key7 = $("#key7").val();
  var key8 = $("#key8").val();
  var key9 = $("#key9").val();
  var key10 = $("#key10").val();
  var key11 = $("#key11").val();
  var key12 = $("#key12").val();

  var jsonOutput = {};
  jsonOutput[key1] = $("#value1").val();
  jsonOutput[key2] = $("#value2").val();
  jsonOutput[key3] = $("#value3").val();
  jsonOutput[key4] = $("#value4").val();
  jsonOutput[key5] = $("#value5").val();
  jsonOutput[key6] = $("#value6").val();
  jsonOutput[key7] = $("#value7").val();
  jsonOutput[key8] = $("#value8").val();
  jsonOutput[key9] = $("#value9").val();
  jsonOutput[key10] = $("#value10").val();
  jsonOutput[key11] = $("#value11").val();
  jsonOutput[key12] = $("#value12").val();

  $.ajax({  
    type : "POST",  
    async : true,
    url : url,  
    data: JSON.stringify(jsonOutput),
    cache : false,
    contentType: "application/json",
    dataType: "json",
    timeout:50000,  
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    success:function(datas) {
      console.log(datas);
    },  
    error: function(datas) {
      console.log(datas);
    }  
  });  
});

$("#su2").click(function () {
  var url = $("#testUrl").val();
  var jsonOutput = $("#testMessage").val();
  console.log(jsonOutput);

  $.ajax({  
    type : "POST",  
    async : true,
    url : url,  
    data: jsonOutput,
    cache : false,
    contentType: "application/json",
    dataType: "json",
    timeout:50000,  
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    success:function(datas) {
      console.log(datas);
    },  
    error: function(datas) {
      console.log(datas);
    }  
  });  
});

$("#su3").click(function () {
  var url = $("#testUrl").val();
  var dataOutput = $("#testMessage").val();
  console.log(dataOutput);

  $.ajax({  
    type : "POST",  
    async : true,
    url : url,  
    data: dataOutput,
    cache : false,
    // contentType: "application/json",
    // dataType: "json",
    timeout:50000,  
    beforeSend: function(xhr) {
      xhr.setRequestHeader(csrfHeader, csrfToken);
    },
    success:function(datas) {
      console.log(datas);
    },  
    error: function(datas) {
      console.log(datas);
    }  
  });  
});

});

 </script>
</footer>