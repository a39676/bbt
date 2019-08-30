<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="btn-group">
  <button class="btn btn-sm btn-outline-primary">
    <a href="/" name="bonaHeaderHomeButton">Home</a>
  </button>
  <button class="btn btn-sm btn-primary" style="display: none;">
    <input type="hidden" name="" value="${message}" />
  </button>
  <sec:authorize access="!hasRole('ROLE_USER')">
    <button class="btn btn-sm btn-outline-primary" name="login">
      <a href="#" id="loginTag">[登录]</a>
    </button>
    <%-- 
    <button class="btn btn-sm btn-default" name="userRegist" url="/user/userRegist">
      <a href="">[注册]</a>
    </button>
      --%>
  </sec:authorize>
  <sec:authorize access="hasRole('ROLE_USER')">
    <button class="btn btn-sm btn-outline-primary" name="login" style="display: none;">
      <a href="#" id="loginTag">[登录]</a>
    </button>
    <button class="btn btn-sm btn-outline-primary" name="logout">
      <a href="/login/logout">[登出]</a>
    </button>
    <button class="btn btn-sm btn-outline-primary" name="userInfo" url="/user/userInfo">
      <a href="">[个人中心]</a>
    </button>
  </sec:authorize>
  <!-- main-menu -->
  <!-- 
  <div class="src-area">
    <form>
      <button class="src-btn" type="submit"><i class="ion-ios-search-strong"></i></button>
      <input class="src-input" type="text" placeholder="Type of search">
    </form>
  </div>
    -->
</div><!-- conatiner -->
<div class="container-fluid position-relative no-side-padding">
  <label class="badge badge-primary" id="homepageRollingAnnouncement"></label>
</div>

<div id="dynamicLoginDiv"></div>
