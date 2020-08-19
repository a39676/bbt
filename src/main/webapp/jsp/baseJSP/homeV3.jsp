<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <%@ include file="../baseElementJSP/normalHeader.jsp" %>
  <title>${title}</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta charset="UTF-8">

  <!-- Stylesheets -->
  <link href="/static_resources/css/home/home.css" rel="stylesheet">

</head>
<body>

  <%@ include file="../baseElementJSP/headerCustom.jsp" %>

  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <div class="row border-bottom border-secondary">
      <div class="col-md-12" name="navigation">
        <%@ include file="../baseElementJSP/navigationAdminV3.jsp" %>
      </div>
    </div>
  </sec:authorize>

  <div class="container-fluid">
    <div class="row"><hr style="color: rgba(55, 66, 250,1.0)"></div>
    <div class="row">
      <div class="col-md-10" id="dynamicArea">
        <%@ include file="../articleJSP/articleMainFrame.jsp" %>
      </div>
      <!-- dynamic area -->

      <div class="col-md-2" id="rightSide">
        <div class="border border-primary rounded mb-12">
          <img class="card-img-top" 
          src="https://res.cloudinary.com/dy20bdekn/image/upload/c_thumb,w_200,g_face/v1548477518/headImg.png" 
          style="height: 30px; width: 30px;" 
          alt="Card image cap">
          <div class="card-header">--------</div>
          <div class="card-body text-primary">
            <h5 class="card-title">关于这里</h5>
            <p class="card-text">
              码海无涯,回头是岸
            </p>
            <a href="#" class="btn btn-primary">Go somewhere</a>
          </div>
        </div>
      </div>
      <!-- right side -->
    </div>
    <div class="row"><hr style="color: rgba(55, 66, 250,1.0)"></div>
  </div>
  <!-- main container -->

  <!-- section start-->
  <section class="blog-area section" id="blogArea" articleChannel="" loadingFlag="0" markTime="">
    <div class="container">

      <div class="row" id="dynamicRow">
        
      </div><!-- dynamic row -->

      <div class="row" id="blogRowArea">
      </div><!-- blogRowArea row -->

    </div><!-- container -->
  </section>
  <!-- section end-->

  <!-- SCIPTS -->
  <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  <!-- <script src="https://cdn.bootcss.com/sockjs-client/1.1.0/sockjs.min.js"></script> -->
  <!-- <script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script> -->
  <script type="text/javascript" src="<c:url value='/static_resources/js/home/homeV3.js'/>"></script>
  <script type="text/javascript" src="/static_resources/js/article/articleMainFrame.js"></script>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleSearch.js'/>"></script>
  </sec:authorize>

</body>
</html>
