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
<form action="${pageContext.request.contextPath}/tool/database/getImageCache?${_csrf.parameterName}=${_csrf.token}" method="POST">
  <div>
    <input type="text" for="limitNum" id="limitNum" name="limitNum" value="999" style="height: 50px"><br>
  </div>
  <div>
    <button type="submit" id="imageCacheExport">imageCacheExport</button>
  </div>
</form>

<hr>

<form action="${pageContext.request.contextPath}/tool/getTomcatOut?${_csrf.parameterName}=${_csrf.token}" method="POST">
  <div>
    <button type="submit" id="getTomcatOut">getTomcatOut</button>
  </div>
</form>

<hr>

<div>
  <input type="text" name="customStartDate" value="2018-01-01 00:00:00"><br>
  <input type="text" name="customEndDate" value="2018-01-01 00:00:00"><br>
  <button id="deleteUserIpRecord">deleteUserIpRecord</button><br>
  <button id="batchUpdatePrivateKey">batchUpdatePrivateKey</button><br>
  <button id="createFakeEvaluationStore">createFakeEvaluationStore</button><br>
</div>

<hr>

<div>
  <input type="text" name="refreshSystemConstant" placeholder="变量名以空格分隔"><br>
  <button id="refreshSystemConstant">refreshSystemConstant</button><br>
</div>

<hr>

<div>
  <input type="text" name="loadHomepageAnnouncementStr" placeholder="刷新主页公告位"><br>
  <button id="loadHomepageAnnouncementStr">loadHomepageAnnouncementStr</button><br>
</div>



<div id="resultView">
  resultView
  <span name="resultSpan">resultSpan</span>
</div>

</body>
<footer>
<%@ include file="../baseElementJSP/normalJSPart.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#deleteUserIpRecord").click( function() {
      deleteUserIpRecord();
    });

    function deleteUserIpRecord() {
      
      var url = "${pageContext.request.contextPath}/admin/deleteUserIpRecord";
      var startDate = $("input[name='customStartDate']").val();
      var endDate = $("input[name='customEndDate']").val();
      var jsonOutput = {
        startDate:startDate,
        endDate:endDate
      };

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
        success:function(datas){
          console.log(datas);
          $("span[name='resultSpan']").text(datas.message);
        },  
        error: function(datas) {  
          $("span[name='resultSpan']").text(datas.message);
        }  
      });  
    };

    $("#batchUpdatePrivateKey").click( function() {
      batchUpdatePrivateKey();
    });

    function batchUpdatePrivateKey() {
      
      var url = "${pageContext.request.contextPath}/articleAdmin/batchUpdatePrivateKey";
      var startDate = $("input[name='customStartDate']").val();
      var endDate = $("input[name='customEndDate']").val();
      var jsonOutput = {
        startDate:startDate,
        endDate:endDate
      };

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
        success:function(datas){
          console.log(datas);
          $("span[name='resultSpan']").text(datas.message);
        },  
        error: function(datas) {  
          $("span[name='resultSpan']").text(datas.message);
        }  
      });  
    };

    $("#refreshSystemConstant").click( function() {
      refreshSystemConstant();
    });

    function refreshSystemConstant() {
      
      var url = "${pageContext.request.contextPath}/admin/refreshSystemConstant";
      var keys = $("input[name='refreshSystemConstant']").val();
      var jsonOutput = {
        keys:keys
      };

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
        success:function(datas){
          console.log(datas);
          $("span[name='resultSpan']").text(datas);
        },  
        error: function(datas) {  
          $("span[name='resultSpan']").text(datas);
        }  
      });  
    };

    $("#loadHomepageAnnouncementStr").click( function() {
      loadHomepageAnnouncementStr();
    });

    function loadHomepageAnnouncementStr() {
      
      var url = "${pageContext.request.contextPath}/admin/loadHomepageAnnouncementStr";
      var homepageAnnouncementStr = $("input[name='loadHomepageAnnouncementStr']").val();
      var jsonOutput = {
        homepageAnnouncementStr:homepageAnnouncementStr
      };

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
        success:function(datas){
          $("span[name='resultSpan']").text(datas.homepageAnnouncementStr);
        },  
        error: function(datas) {  
          $("span[name='resultSpan']").text(datas.message);
        }  
      });  
    };


    $("#createFakeEvaluationStore").click( function() {
      createFakeEvaluationStore();
    });

    function createFakeEvaluationStore() {
      
      var url = "${pageContext.request.contextPath}/admin/createFakeEvaluationStore";
      var jsonOutput = {
      };

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
        success:function(datas){
          console.log(datas);
          $("span[name='resultSpan']").text(datas.message);
        },  
        error: function(datas) {  
          $("span[name='resultSpan']").text(datas.message);
        }  
      });  
    };

  });

</script>
</footer>
</html>