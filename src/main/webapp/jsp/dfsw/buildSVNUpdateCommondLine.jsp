<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>

<body>
  <input type="text" name="" id="localPathPrefix" style="width: 600px;" placeholder="请输入本地的svn存放代码路径, 如: 'D:/work/合同系统/03实现/001程序代码'"
  >

  <br>
  <hr>

  <textarea id="input" style="width: 600px; height: 100px;">
    
  </textarea>
  <br>
  <span>如:</span><br>
  <span>Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/ApplicationServices/impl/OpfOrgService.cs</span><br>
  <span>Modified : /1开发库/企业组/集团合同管理系统(二期)/工程类/03实现/001程序代码/平台代码/OT.Platform/OT.OPF/Org/DomainModel/Company.cs</span><br>
  <span>可复制多行</span>

  <br>
  <hr>

  <span>请保证本地存放路径 与 bugFree 上的更新路径有重合部分 如:</span>
  <br>
  <span>D:/work/合同系统</span><span style="text-decoration: underline;font-weight: bold;">/03实现/001程序代码</span><br>
  <span>/1开发库/企业组/集团合同管理系统(二期)/工程类</span><span style="text-decoration: underline;font-weight: bold;">/03实现/001程序代码</span><span>/平台代码/OT.Platform/OT.OPF/Org/</span>

  <br>
  <hr>

  <button id="trans">转换</button>

  <br>
  <hr>

  <pre>
  <span id="result">
    
  </span>
  </pre>
  
</body>

<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>

<script type="text/javascript">
  $(document).ready(function() {

    $("#trans").click( function() {
      trans();
    });

    function trans(){ 
      var localPathPrefix = $("#localPathPrefix").val();
      var input = $("#input").val();
      
      var jsonOutput = {
        localPathPrefix:localPathPrefix,
        input:input
      };

      var url = "/dfsw/buildSVNUpdateCommondLine";

      $.ajax({  
        type : "POST",  
        async : true,
        url : url,  
        data: JSON.stringify(jsonOutput),
        cache : false,
        contentType: "application/json",
        dataType: "json",
        timeout:50000,  
        // beforeSend: function(xhr) {
        //   xhr.setRequestHeader(csrfHeader, csrfToken);
        // },
        success:function(datas){
          if(datas.code == 0) {
            $("#result").html(datas.output);
          } else {
            $("#result").html(datas.message);
          }
        },  
        error: function(datas) {  
          
        }  
      });  
    };

  });
</script>
</footer>