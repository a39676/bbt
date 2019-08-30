<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>

<div class="container-fluid">
  <div class="row">
    <div class="input-group col-md-4">
      <input class="form-control" type="text" name="folderPathInput" style="width: 300px;" placeholder="请输入目标路径">
      <button class="input-group-append btn btn-primary btn-sm" name="goThere">Go There</button>
    </div>
    <div class="input-group col-md-5">
      <input class="form-control" type="file" id="fileUpload"/>
      <button class="input-group-append btn btn-primary btn-sm" id="uploadButton">上传</button>
    </div>
    <div class="col-md-3">
      <div id="uploadResultMessage"></div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-5">
      <span class="badge badge-primary" name="folderPath">${folderPath}</span>
      <span class="badge badge-danger" name="message">${message}</span>
    </div>
  </div>
</div>

<div class="container-fluid" name="fileListDetail">
  <%@ include file="../fakeFTPJSP/fileListDetail.jsp" %>
</div>

<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">
  $(document).ready(function() {

    $("button[name='goThere']").click( function() {
      getFilePathDetail();
    });

    function getFilePathDetail() {
      
      var url = "${pageContext.request.contextPath}/fakeFTP/getFilePathDetail";
      var filePath = $("input[name='folderPathInput']").val();
      var jsonOutput = {
        filePath:filePath
      };

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        // dataType: "json",
        timeout:50000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("div[name='fileListDetail']").empty();
          $("div[name='fileListDetail']").append(datas);
        },  
        error: function(datas) {  
          
        }  
      });  
    };

    $("#uploadButton").click( function() {
      upload();
    });

    function upload(){ 
      var uploadForm = new FormData();
      uploadForm.append("file", fileUpload.files[0]);
      uploadForm.append("savePath", $("input[name='folderPathInput']").val());
      
      var url = "${pageContext.request.contextPath}/fakeFTP/fakeFTPUpload";

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data : uploadForm,
        cache : false,
        contentType: false,
        processData : false,
        timeout:50000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){  
          $("#uploadResultMessage").html(datas)
        },  
        error: function(datas) {  
          $("#uploadResultMessage").html(datas.responseText)
        }  
      });  
    };

  });

</script>
</footer>