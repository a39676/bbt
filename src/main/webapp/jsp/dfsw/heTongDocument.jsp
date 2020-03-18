<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>

<body>
  <p>东方思维 - 合同系统 - 在线文档</p>

  <div class="container-fluid">
    <div class="row">
      <div class="col-md-12">

        <!-- 1 - 关于 Jira -->
        <div class="row">
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-12">
                <button class="btn btn-sm btn-warning nbtn" numb="1"> 1 - 关于 Jira</button>
              </div>
            </div>
          </div>

          <div class="container-fluid subContent" numb="1">
            <div class="row">
              <div class="col-md-12">
                <span>jira上的问题要填预估时间, 而且预估时间要在开发的基础上增加时间，否则 Jira 将自动覆盖。</span><br>
                <span>比如开发已经预估填了1d， 预估是1h，那 预估的时候就填1d1h。耗费时间就如实直接填。</span><br>
                <img src="/static_resources/img/dfsw/1-1.jpg"><br>
                <span>首先开发流转过来的问题 要在这里填预估时间和测试用例</span>
              </div>
            </div>
  
            <div class="row">
              <div class="col-md-12">
                <span>测完了备注测试结论</span><br>
                <img src="/static_resources/img/dfsw/1-2.jpg"><br>
                <span>并登记“耗费时间”</span><br>
                <img src="/static_resources/img/dfsw/1-3.jpg"><br>
              </div>
            </div>

            <div class="row">
              <div class="col-md-12">
                <span>然后点【测试通过】  分配给运维</span><br>
                <img src="/static_resources/img/dfsw/1-4.jpg">
              </div>
            </div>
          </div>
        </div>

        <hr>

        <!-- 2 - 关于 生成部署包 -->
        <div class="row">
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-12">
                <button class="btn btn-sm btn-warning nbtn" numb="2"> 2 - 关于 生成部署包 </button>
              </div>
            </div>
          </div>

          <div class="container-fluid subContent" numb="2">
            <div class="row">
              <div class="col-md-12">
                <span>右键选择项目中的 OT.WebSite ---> 选择 "发布"</span><br>
                <img src="/static_resources/img/dfsw/2-1.jpg"><br>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <span>发布方法选择"文件系统"</span><br>
                <img src="/static_resources/img/dfsw/2-2.jpg"><br>
              </div>
            </div>

            <!-- 2.1 - 如果缺失部分 cshtml 文件 -->
            <div class="row">
              <div class="container-fluid">
                <div class="row">
                  <div class="col-md-12">
                    <button class="btn btn-sm btn-warning nbtn" numb="2.1"> 2.1 - 如果缺失部分 cshtml 文件 </button>
                  </div>
                </div>

                <div class="row">
                  <div class="container-fluid subContent" numb="2.1">
                    <div class="row">
                      <div class="col-md-12">
                        <span>可以试着找到 /合同系统/03实现/001程序代码/OT.CNT/OT.WebSite/OT.WebSite.csproj , 将对应页面文件名的元素注释掉 </span><br>
                        <img src="/static_resources/img/dfsw/2-2-1.jpg"><br>
                        <img src="/static_resources/img/dfsw/2-2-2.jpg"><br>
                      </div>
                    </div>                  
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <hr>

        <!-- 3 - 关于 增量更新 -->
        <div class="row">
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-12">
                <button class="btn btn-sm btn-warning nbtn" numb="3"> 3 - 关于 增量更新 </button>
              </div>
            </div>
            
            <div class="row">
              <div class="container-fluid subContent" numb="3">
                <div class="row">
                  <div class="col-md-12">
                    <span>因为某些原因, 大量更新文件时, 手动操作可能比较崩溃, 故编写了以下工具</span><br>
                    <img src="/static_resources/img/dfsw/3-1.jpg"><br>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>      
    </div>
  </div>
  
</body>

<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>

<script type="text/javascript">
  $(document).ready(function() {

    $(".subContent").hide();

    $(".nbtn").click(function () {
      var numb = $(this).attr("numb");
      var targetContent = $(".subContent[numb='"+numb+"']");
      if(targetContent.is(':visible')) {
        targetContent.hide();
      } else {
        targetContent.show();
      }
    });

  });
</script>
</footer>