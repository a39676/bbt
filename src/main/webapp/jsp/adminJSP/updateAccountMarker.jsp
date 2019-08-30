<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true"%>
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>
  <h1>Message : ${message}</h1>
  <div class="testArea">${testArea}</div>
  <form class="col-md-8" method="post" action="${pageContext.request.contextPath}/admin/updateAccountMarker"  >

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  
    <div class="form-group row">
      <label class="control-label col-md-1" for="accountNumber">Account number :</label>
      <div class="col-md-6">
        <input type="text" class="form-control" name="accountNumber" id="accountNumber">
        <label name="accountNumberP"></label>
      </div>
    </div>

    <button type="submit" class="btn btn-default">Submit</button>
  </form>

</body>

<footer>
  <%@ include file="../baseElementJSP/normalFooter.jsp" %>
  <script type="text/javascript">

    $(document).ready(function() {

      // account number type number only
      $("input[name='accountNumber']").keyup(function () { 
        var $this = $(this);
        $this.val($this.val().replace(/[^0-9]/g, ""));
      });

      // accountNumber duplicate check
      $(function(){  
        $("input[name='accountNumber']").blur(function(){ 
          // $(this).css("background-color","blue");
          var accountNumber = $(this).val();
          var jsonParams = {};

          jsonParams['accountNumber'] = accountNumber;
          jsonParams[csrfParameter] = csrfToken; 

          $.ajax({               
            type: "POST",  
            url: "${pageContext.request.contextPath}/accountInfo/accountNumberDuplicateCheck",   
            data: JSON.stringify(jsonParams), 
            dataType: 'json',
            contentType: "application/json",
            beforeSend: function(xhr) {
              xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            timeout: 15000,
            success:function(data){  
              if (data.code === '0') {
                var tmpV = $("label[name='accountNumberP']");
                $("label[name='accountNumberP']").css("background-color","yellow");
                $("label[name='accountNumberP']").text("can use");
              } else {
                $("label[name='accountNumberP']").css("background-color","red");
                $("label[name='accountNumberP']").text(data.message);
              }
            }, 
            error:function(e){
              // console.log("error");
              // console.log(e);
            }
          });
        });  
      });


      

    });
  </script>
</footer>
</html>