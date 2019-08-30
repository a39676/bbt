<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<div class="row" name="folderList">
  <div class="col-md-12">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-3"><span class="badge badge-success">fileName</span></div>
        <div class="col-md-3"><span class="badge badge-success">createTime</span></div>
        <div class="col-md-3"><span class="badge badge-success">lastModifyTime</span></div>
        <div class="col-md-3"><span class="badge badge-success">size</span></div>
      </div>
      <c:forEach items="${folderDetails}" var="folderDetail" >
        <div class="row border border-success rounded">
          <div class="col-md-2">
            <span name="folderName">${folderDetail.fileName}</span>
          </div>
          <div class="col-md-1">
            <form action="/fakeFTP/downloadThis?${_csrf.parameterName}=${_csrf.token}" method="POST">
              <input type="text" name="filePath" style="display: none;" value="${folderDetail.path}">
              <button type="submit" class="btn btn-sm btn-success">下载</button>
            </form>
          </div>
          <div class="col-md-3"><span>${folderDetail.createTime}</span></div>
          <div class="col-md-3"><span>${folderDetail.lastModifyTime}</span></div>
          <div class="col-md-3"><span>${folderDetail.size}</span></div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>

<div class="row" name="fileList">
  <div class="col-md-12">
    <div class="container-fluid">
      <c:forEach items="${fileDetails}" var="fileDetail" >
        <div class="row border border-bottom border-primary">
          <div class="col-md-2">
            <span name="fileName">${fileDetail.fileName}</span>
          </div>
          <div class="col-md-1">
            <form action="/fakeFTP/downloadThis?${_csrf.parameterName}=${_csrf.token}" method="POST">
              <input type="text" name="filePath" style="display: none;" value="${fileDetail.path}">
              <button type="submit" class="btn btn-sm btn-primary">下载</button>
            </form>
          </div>
          <div class="col-md-3"><span>${fileDetail.createTime}</span></div>
          <div class="col-md-3"><span>${fileDetail.lastModifyTime}</span></div>
          <div class="col-md-3"><span>${fileDetail.size}</span></div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>


<footer>
<script type="text/javascript">
  $(document).ready(function() {
    $("input[name='folderPathInput']").val("${folderPath}");
    $("span[name='folderPath']").text("${folderPath}");
    $("span[name='message']").text("${message}");

    $("span[name='folderName']").click(function () {
      var url = "/fakeFTP/getFilePathDetail";
      var filePath = $("input[name='folderPathInput']").val();
      var folderName = $(this).text();
      filePath = filePath + folderName;
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
    });

    // $("span[name='fileName']").click(function () {
    //   var url = "/fakeFTP/downloadThis";
    //   var filePath = $("input[name='folderPathInput']").val();
    //   var fileName = $(this).text();
    //   filePath = filePath + fileName;
    //   var jsonOutput = {
    //     filePath:filePath
    //   };

    //   $.ajax({  
    //     type : "POST",  
    //     async : true,
    //     url : url,  
    //     data: JSON.stringify(jsonOutput),
    //     cache : false,
    //     contentType: "application/json",
    //     // dataType: "json",
    //     timeout:50000,  
    //     beforeSend: function(xhr) {
    //       xhr.setRequestHeader(csrfHeader, csrfToken);
    //     },
    //     success:function(datas){
    //       // var blob = datas;
    //       // var reader = new FileReader();
    //       // reader.readAsDataURL(blob);

    //       // reader.onload = function (e) {
    //       //   // 转换完成，创建一个a标签用于下载
    //       //   var a = document.createElement('a');
    //       //   a.download = fileName;
    //       //   a.href = e.target.result;
    //       //   // $("body").append(a);    // 修复firefox中无法触发click
    //       //   a.click();
    //       //   $(a).remove();
    //       // }
    //     },  
    //     error: function(datas) {  
          
    //     }
    //   });  
    // });


  });
</script>
</footer>