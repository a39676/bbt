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
  <table>
    <tr>
      <td><label>最大上传5m</label></td>
      <td><input type="file" id="fileUpload" /></td>
    </tr>
    <tr>
      <td><label>存放路径 : ${storePath}</label></td>
    </tr>
    <tr>
      <td><button id="uploadButton">上传</button></td>
    </tr>
  </table>
</form>
<div id="uploadResultMessage"></div>
</body>
<footer>
<%@ include file="../../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("#uploadButton").click( function() {
      upload();
    });

    function upload(){ 
      var uploadFile = new FormData();
      uploadFile.append("file", fileUpload.files[0]);
      
      var url = "${pageContext.request.contextPath}/uploadPri/${uploadUrl}";

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
</html>