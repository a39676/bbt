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

  <!-- Font -->
  <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">

  <!-- Stylesheets -->
  <link href="/common-css/ionicons.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.4.6/css/swiper.css">
  <link href="layout-1/css/styles.css" rel="stylesheet">
  <link href="layout-1/css/responsive.css" rel="stylesheet">
  <link href="front-page-category/css/styles.css" rel="stylesheet">
  <link href="front-page-category/css/responsive.css" rel="stylesheet">

</head>
<body >

  <%@ include file="../baseElementJSP/bonaHeader.jsp" %>

  <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
    <div class="row border-bottom border-secondary">
      <div class="col-md-12" name="navigation">
        <%@ include file="../baseElementJSP/navigationAdminV3.jsp" %>
      </div>
    </div>
  </sec:authorize>

  <!-- slider start -->
  <div class="main-slider">
    <div class="swiper-container position-static" data-slide-effect="slide" data-autoheight="false"
      data-swiper-speed="500" data-swiper-autoplay="10000" data-swiper-margin="0" data-swiper-slides-per-view="4"
      data-swiper-breakpoints="true" data-swiper-loop="true" >
      <div class="swiper-wrapper" id="swiperWrapper">

        <c:forEach items="${articleChannels.publicChannels}" var="publicChannel">
        <div class="swiper-slide">
          <a class="slider-category" href="#" uuid="${publicChannel.uuid}">
            <div class="blog-image"><img src="${publicChannel.channelImage}" alt="Blog Image"></div>

            <div class="category">
              <div class="display-table center-text">
                <div class="display-table-cell">
                  <h3><b>${publicChannel.channelName}</b></h3>
                </div>
              </div>
            </div>

          </a>
        </div><!-- swiper-slide -->
        </c:forEach>
        <c:forEach items="${articleChannels.flashChannels}" var="flashChannel">
        <div class="swiper-slide">
          <a class="slider-category" href="#" uuid="${flashChannel.uuid}">
            <div class="blog-image"><img src="${flashChannel.channelImage}" alt="Blog Image"></div>

            <div class="category">
              <div class="display-table center-text">
                <div class="display-table-cell">
                  <h3><b>${flashChannel.channelName}</b></h3>
                </div>
              </div>
            </div>

          </a>
        </div><!-- swiper-slide -->
        </c:forEach>
        <c:forEach items="${articleChannels.privateChannels}" var="privateChannel">
        <div class="swiper-slide">
          <a class="slider-category" href="#" uuid="${privateChannel.uuid}">
            <div class="blog-image"><img src="${privateChannel.channelImage}" alt="Blog Image"></div>

            <div class="category">
              <div class="display-table center-text">
                <div class="display-table-cell">
                  <h3><b>${privateChannel.channelName}</b></h3>
                </div>
              </div>
            </div>

          </a>
        </div><!-- swiper-slide -->
        </c:forEach>
        
      </div><!-- swiper-wrapper -->

    </div><!-- swiper-container -->

  </div>
  <!-- slider end -->

  <!-- section start-->
  <section class="blog-area section" id="blogArea" articleChannel="${defaultUUID}" loadingFlag="0" markTime="">
    <div class="container">

      <div class="row" id="dynamicRow">
        <sec:authorize access="hasRole('ROLE_USER')">
          <button class="load-more-btn" id="createNewArticle"><b>Create new</b></button>
        </sec:authorize>
      </div><!-- dynamic row -->

      <div class="row" id="blogRowArea">
      </div><!-- blogRowArea row -->

      <img src="https://wx1.sinaimg.cn/mw690/0062ci2oly1ftzyk5dlejg303k03k40e.gif" 
        style="width: 30px; height: 30px;" 
        name="articleAreaLoadingImg">

      <button class="load-more-btn" id="loadMoreButton"><b>LOAD MORE</b></button>

      <sec:authorize access="hasRole('ROLE_ADMIN')">
      <%-- 管理员专用搜索框 --%>
      <%@ include file="../articleJSP/articleSearchV3.jsp" %>
      </sec:authorize>

    </div><!-- container -->
  </section>
  <!-- section end-->

  <%@ include file="../baseElementJSP/bonaFooter.jsp" %>

  <!-- SCIPTS -->
  <%@ include file="../baseElementJSP/normalJSPart.jsp" %>
  <script src="https://cdn.bootcss.com/sockjs-client/1.1.0/sockjs.min.js"></script>
  <script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
  <script src="common-js/swiper.js"></script>
  <script src="common-js/scripts.js"></script>
  <script type="text/javascript" src="/static_resources/js/summaryListFrame/summaryListFrame.js"></script>
  <script type="text/javascript" src="<c:url value='/static_resources/js/home/homeV3.js'/>"></script>
  <sec:authorize access="hasRole('ROLE_ADMIN')">
  <script type="text/javascript" src="<c:url value='/static_resources/js/home/homeV3_admin.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/static_resources/js/article/articleSearch.js'/>"></script>
  </sec:authorize>

</body>
</html>
