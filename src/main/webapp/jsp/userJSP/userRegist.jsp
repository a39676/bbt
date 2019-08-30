<%@ page language="java" contentType="text/html; charset=UTF-8"
      pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>
<body>  

  <div>
    <label name="message"></label>
  </div>

  <div  class="col-md-12" id="registForm">

    <div class="form-group">
      <span name="userName" >账户名</span>
      <input id="userName" class="form-control" placeholder="请输入账户名(登录用...)" />
    </div>

    <div class="form-group">
      <span name="nickName" >昵称</span>
      <input id="nickName" class="form-control" placeholder="请填一个昵称吧(其他用户只看到昵称..看不到账户名,不建议与账户同名哦)" />
    </div>

    <div class="form-group">
      <span name="email" >email</span>
      <input id="email" class="form-control" placeholder="请填入电子邮箱,以接收验证邮件" />
    </div>

    <div class="form-group">
      <span name="pwd" >密码</span>
      <input type="password" id="pwd" class="form-control"/>
    </div>

    <div class="form-group">
      <span name="pwdRepeat" >重复输入一次密码</span>
      <input type="password" id="pwdRepeat" class="form-control"/>
    </div>

    <div class="form-group">
      <span name="gender" >性别</span>
      <input type="radio" id="male"
        name="gender" value="male">
      <span>男</span>
  	  <label>|</label>
      <input type="radio" id="female"
        name="gender" value="female">
      <span>女</span>
      <label>|</label>
      <input type="radio" id="secret"
        name="gender" value="secret" checked="checked">
      <span>保密</span>
    </div>

    <div class="form-group">
      <span name="qq" >qq号</span>
      <input id="qq" class="form-control" placeholder="要填个Q号不?" />
    </div>

    <div class="form-group">
      <span name="mobile" >手机号</span>
      <input id="mobile" class="form-control" placeholder="如果你想留个手机号?" />
    </div>

    <div class="form-group">
      <span name="reservationInformation" >预留信息</span>
      <input id="reservationInformation" class="form-control" placeholder="开发中的一个功能,可能以后用于账号找回,网站验证之类的." />
    </div>
    <div>
      <button name="apply" class="btn btn-warning btn-sm">提交</button>
    </div>
  </div>


  <div>
    <span id="registResult" style="font-size: smaller"></span>
  </div>
  <br>
  <div id="loginView">
    
  </div>
</body>  
<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type="text/javascript">

  $(document).ready(function() {

    $("button[name='apply']").click(
      function() {
        userRegist();
    });


    function userRegist(){ 
      $("label[name='message']").empty();

      var url = "${pageContext.request.contextPath}/user/userRegist";

      var gender;
      if($('#male').is(':checked')) { 
        gender = 1;
      } else if ($('#female').is(':checked')) {
        gender = 0;
      } else {
        gender = -1;
      }

      var jsonOutput = {
        userName : $("#userName").val(),
        nickName : $("#nickName").val(),
        email : $("#email").val(),
        pwd : $("#pwd").val(),
        pwdRepeat : $("#pwdRepeat").val(),
        qq : $("#qq").val(),
        mobile : $("#mobile").val(),
        reservationInformation : $("#reservationInformation").val(),
        gender : gender
      };

      $.ajax({  
          type : "POST",  
          async : true,
          url : url,  
          data: JSON.stringify(jsonOutput),
          contentType: "application/json",
          dataType: 'json',
          timeout:15000,  
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
          },
          success:function(datas){
            
            if(datas.result == 0) {
              $("#registForm").html("");
              $("#registResult").text(datas.message);
              getLoginView();
            } else {
              $("label[name='message']").text(datas.message);
              $("#pwd").val("");
              $("#pwdRepeat").val("");
              if(datas.json.hasOwnProperty("userName") > 0) {
                $("label[name='message']").append(datas.json.userName + "<br>");
              }
              if(datas.json.hasOwnProperty("nickName") > 0) {
                $("label[name='message']").append(datas.json.nickName + "<br>");
              }
              if(datas.json.hasOwnProperty("email") > 0) {
                $("label[name='message']").append(datas.json.email + "<br>");
              }
              if(datas.json.hasOwnProperty("pwd") > 0) {
                $("label[name='message']").append(datas.json.pwd + "<br>");
              }
              if(datas.json.hasOwnProperty("pwdRepeat") > 0) {
                $("label[name='message']").append(datas.json.pwdRepeat + "<br>");
              }
              if(datas.json.hasOwnProperty("qq") > 0) {
                $("label[name='message']").append(datas.json.qq + "<br>");
              }
              if(datas.json.hasOwnProperty("mobile") > 0) {
                $("label[name='message']").append(datas.json.mobile + "<br>");
              }
              if(datas.json.hasOwnProperty("reservationInformation") > 0) {
                $("label[name='message']").append(datas.json.reservationInformation + "<br>");
              }
            }
          },  
          error: function(datas) {  
            $("#registResult").text(datas.message);
          }  
      });  
    };

    function getLoginView() {
      var url = "${pageContext.request.contextPath}/login/login";

      $.ajax({  
        type : "GET",  
        async : true,
        url : url,  
        cache : false,
        timeout:50000,  
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success:function(datas){
          $("#loginView").html(datas);
        },  
        error: function(datas) {  
          
        }  
      });  
    };
  });
  </script>
</footer>
</html>