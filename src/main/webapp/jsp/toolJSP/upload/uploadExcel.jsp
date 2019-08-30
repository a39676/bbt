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
  <div class="container">
    <div class="row">
      <div class="col-md-5">
        <table>
          <tr>
            <td><span class="badge badge-primary">最大上传5m </span></td>
            <td><input type="file" id="fileUpload" /></td>
          </tr>
          <tr>
            <td><button class="btn btn-sm btn-primary" id="uploadButton">上传</button></td>
          </tr>
        </table>
      </div>
    </div>

    <div class="row"></div>

    <div class="row" style="display: none;" id="chartTypeDiv">
      <div class="col-md-5">
        <div class="btn-group">
          <c:forEach items="${chartTypeList}" var="subChartType">
            <button class="btn btn-primary btn-sm" name="chartTypeButton" testType="3" chartTypeCode="${subChartType.code}">
              <span class="badge badge-primary">${subChartType.name}</span>
            </button>
          </c:forEach>
        </div>
      </div>
    </div>

    <div class="row"></div>

    <div class="row">
      <div class="col-md-5">
        <div id="uploadResultMessage" url="" uri="" pk="" chartType=""></div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-8">
        <div id="dynamicDiv"></div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        
      </div>
    </div>
  </div>

</body>
<footer>
<%@ include file="../../baseElementJSP/normalFooter.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script type="text/javascript">

  $(document).ready(function() {

    var inputPk = "${pk}";
    var inputChartType = "${chartType}";
    var inputColumnToRow = "${columnToRow}";

    

    if (inputPk.length) {
      $("#chartTypeDiv").fadeIn(150);
      $("#uploadResultMessage").attr("url", "${chartViewUrl}");
      $("#uploadResultMessage").attr("uri", "${chartViewUri}");
      $("#uploadResultMessage").attr("chartType", inputChartType);
      $("#uploadResultMessage").attr("pk", inputPk);
      getChartView("${chartViewUri}?pk=" + inputPk + "&chartType=" + inputChartType);
    }

    $("#uploadButton").click( function() {
      upload();
    });

    function upload(){ 
      var uploadFile = new FormData();
      uploadFile.append("file", fileUpload.files[0]);
      
      var url = "/upload/${uploadUrl}";

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data : uploadFile,
        cache : false,
        contentType : false,
        processData : false,
        timeout:50000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){  
          console.log(datas);
          var jsonResponse = JSON.parse(datas);
          var responseUrl = jsonResponse.url;
          var responseUri = jsonResponse.uri;
          var responsePk = jsonResponse.pk;
          $("#uploadResultMessage").html(
            "<span class='badge badge-primary'>文件已上传, 可保留下方链接随时查看统计图表, 颜色随机匹配, 若对颜色不满意, 刷新即可重新生成</span> <br>" +
            "<span class='badge badge-warning'>"
              +"<a href='"+responseUri+"?pk="+responsePk+"' target='_blank'>"
              +responseUrl+"?pk="+responsePk
              +"</a>"
            +"</span>"
          );
          $("#uploadResultMessage").attr("url", responseUrl);
          $("#uploadResultMessage").attr("uri", responseUri);
          $("#uploadResultMessage").attr("pk", responsePk);
          getChartView(jsonResponse.uri + "?pk=" + responsePk);
        },  
        error: function(datas) {  
          $("#uploadResultMessage").html(datas.responseText);
        }  
      });  
    };

    function getChartView(url) {
      $.ajax({  
        type : "GET",  
        async : true,
        url : url,
        cache : false,
        contentType : false,
        processData : false,
        timeout:50000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){  
          $("#dynamicDiv").html(datas);
          $("#chartTypeDiv").fadeIn(150);
        },  
        error: function(datas) {  
        }  
      });  
    }

    $("button[name='chartTypeButton']").click(function () {
      var pk = $("#uploadResultMessage").attr("pk");
      var uri = $("#uploadResultMessage").attr("uri");
      var chartType = $(this).attr("chartTypeCode");

      getChartView(uri + "?pk=" + pk + "&chartType=" + chartType);

    });
  
  });

</script>
</footer>
</html>