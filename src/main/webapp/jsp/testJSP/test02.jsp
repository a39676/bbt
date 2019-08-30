<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
<%@ include file="../baseElementJSP/normalHeader.jsp" %>
</head>

<div class="row" name="baseInfo">
  <div class="col-md-2">
    <span class="badge badge-info badge-pill">姓名</span><br>
    <span class="badge badge-info">sample@gmail.com</span><br>
    <span class="badge badge-info">13800138000</span><br>
    <span class="badge badge-info badge-pill">地址: </span><span class="badge badge-info">普通地址</span><br>
    <span class="badge badge-info badge-pill">微信/QQ: </span><span class="badge badge-info">123456789</span><br>
    <span class="badge badge-info badge-pill">目前年收入: </span><span class="badge badge-info">13万左右</span><br>
  </div>
  <div class="col-md-5 border-bottom border-dark rounded" style="background: linear-gradient(to bottom left, #ffffff , #e6e6e6);">
    <span class="badge badge-warning">我擅长的</span><br>
    <span class="badge badge-light badge-pill">熟练java</span><br>
    <span class="badge badge-light badge-pill">熟练mysql 建表, 基础查询语句优化, 熟悉语句运行原理.</span><br>
    <span class="badge badge-light badge-pill">熟练spring + springMVC + myBatis 搭建(注解模式)</span><br>
    <span class="badge badge-light badge-pill">熟练git svn 版本管理, 团队协作开发</span><br>
    <span class="badge badge-light badge-pill">熟悉linux基础指令</span><br>
    <span class="badge badge-light badge-pill">熟悉与前端接口交互, js jquery ajax</span><br>
  </div>

  <div class="col-md-5 border-bottom border-warning rounded">
    <span class="badge badge-light badge-pill">期望薪资: 12k</span><span class="badge badge-light badge-pill">  </span><span class="badge badge-light badge-pill">坐标: 广州,海珠</span><br>
    <span class="badge badge-light badge-pill">期望职位: java开发</span><br>
    <span class="badge badge-light badge-pill">9工作经验</span><span class="badge badge-danger badge-pill">5年金融 + 4年java</span><span class="badge badge-light badge-pill"></span><br>
    <span class="badge badge-light badge-pill">目前已离职, 随时可到岗.</span><br>
  </div>
</div>

<div class="row" style="background: linear-gradient(to bottom left, #ffffff , #8fb7fc);" name="introduction">
  <div class="col-md-12">
    <br>
    <br>
    <span class="badge badge-light">非常高兴您光临这个比较粗暴的网站..., 其实我一直主力写后端, 页面方面没有太大投入<img src="http://ww3.sinaimg.cn/large/7280659bgw1f981mdn1klj20c80c8t9f.jpg" style="width: 85px; height: 85px;">, 让您见笑了.</span><br> 
    <span class="badge badge-light"></span><br>
    <span class="badge badge-light">这里再补充一下我个人的情况吧.</span><br>
    <span class="badge badge-light"></span><br>
    <span class="badge badge-light">2009年大专(金融专业)毕业后, 从事金融行业工作(业务线, 非技术类型, 熟悉证券、银行间多种个人, 机构业务)</span><br>
    <span class="badge badge-light">2014年非全日制本科(会计)毕业, 并转行IT, 当时跟随朋友在上文的超联软件中从打杂中摸索学习, </span><br>
    <span class="badge badge-light">但这家公司的加班比较多, 而我其实</span><span class="badge badge-warning">不喜欢加班</span><br>
    <span class="badge badge-light">再加上转行以来的这段时间, 收入锐减, 也必须为实际情况考虑, 于是结合自己过往的工作经历, 转向一家互联网金融公司.</span><br>
    <span class="badge badge-light">在合众经历了各种大小版本更新, 甚至项目框架转换. 获益良多, 但政策不支持互联网金融任性发展已是大势, 没必要等到最后一刻.</span><br>
    <span class="badge badge-light">所以,现在就开始搜寻下一个职位了.</span><br>
    <br>
    <span class="badge badge-light">至于这个网站, 是工余时间搭建的. </span><br>
    <span class="badge badge-light">使用 spring 4.3 + springMVC 4.3 + mybatis 3.4, mysql 5.7</span><br>
    <span class="badge badge-light">gradle 打包(个人认为比maven方便...)</span><br>
    <span class="badge badge-light">评论功能刚上线不久, 还没做各种测试...或许会有BUG</span><br>
    <span class="badge badge-light">如果真要怪这个奇丑无比的页面...我已经尽力了...真的...</span><br>
    <span class="badge badge-light"></span><br>
    <span class="badge badge-light">仍有些疑问??</span><br>
    <span class="badge badge-primary question" style="cursor : pointer;" name="q1">❓为什么转行还在读会计?</span>
    <span class="badge badge-success badge-pill answer" name="q1" style="display : none;">报读的时候还没确定要转行, 只有想法</span><br>
    <span class="badge badge-primary question" style="cursor : pointer;" name="q2">❓为什么转行</span>
    <span class="badge badge-success badge-pill answer" name="q2" style="display : none;">其实金融行业没有印象中的光鲜靓丽, 如果您不嫌我罗嗦想听这其中的血泪史??  别犹豫了, 招我吧!👏🏼</span><br>
    <span class="badge badge-primary question" style="cursor : pointer;" name="q3">❓为什么不报培训班</span>
    <span class="badge badge-success badge-pill answer" name="q3" style="display : none;">这位前辈向我展示过google就是最好的老师👨🏼‍🏫, 以及培训班的实际情况🤷🏼‍, 综合考量的确没有必要.</span><br>
    <span class="badge badge-primary question" style="cursor : pointer;" name="q4">❓非科班出身 能做的来吗?</span>
    <span class="badge badge-success badge-pill answer" name="q4" style="display : none;">没问题, 转行这几年, 过得比以前好多了. 当然也需要持续不断的接触新技术.👨🏼‍💻</span><br>
    <span class="badge badge-primary question" style="cursor : pointer;" name="q5">❓您担心我怎么了解底层原理?</span>
    <span class="badge badge-success badge-pill answer" name="q5" style="display : none;"> 框架的源码,有时候看看,偶尔能找到一些优秀案例能应用  关于java的运行原理, jvm参数设置, 内存指向 还是有个大概的印象,  线程,进程的区别还是懂的. 但往深了去, cpu的调度这种, 就是碎片化的知识了)</span><br>
    <span class="badge badge-primary question" style="cursor : pointer;" name="q6">❓加班? 其实很多加班是可以避免的👦🏼</span>
    <span class="badge badge-success badge-pill answer" name="q6" style="display : none;"> 目前所见到导致加班的原因  多是人为失误...前期挖坑... 当然也会有少部分突发情况, 但我依然认为, 正常工作, 能力相称的情况下👌🏼, 是不应该长期加班的👍🏼.</span><br>
    <span class="badge badge-primary question" style="cursor : pointer;" name="q7">❓您想招一个小团队?</span> 
    <span class="badge badge-success badge-pill answer" name="q7" style="display : none;">或许真可以, 也有同事和我想法差不多</span><br>
    <span class="badge badge-light">在工作以外, 有两种爱好, 种植/无人机(450, 自组, 有一段时间没飞了..., 附近适合放飞的地方不多...唉...).</span><br>
    <span class="badge badge-light">然后...还有什么想要了解的? 请联系我吧!</span>
  </div>
</div>

<div class="row" name="jobHistory">
  <div class="container-fluid">
    <div class="row border-bottom border-info rounded" style="background: linear-gradient(to bottom left, #ffffff , #fdffd2);">
      <div class="col-md-12">
        <br>
        <br>
        <span class="badge badge-light">2015/11 ~ 至今</span> <span class="badge badge-success">公司名1</span><br>
        <span class="badge badge-secondary">p2p金融项目. </span><br>
        <br>
        <span class="badge badge-light">2015年开始. 早期以 <u>playFramework 1.27</u> 作为主要架构, 分理财端, 借款端, 管理后台. </span><br>
        <span class="badge badge-light">2016年下半年后开始全部转为 <u>spring + springMVC + mybatis</u>, 并增设风控, 数据分析项目.</span><br>
        <span class="badge badge-light">生产数据库一直保留 mysql 5.7, 数据分析使用 postgreSQL.</span><br>
        <br>
        <span class="badge badge-primary">模块化实现第三方数据接口,</span><br>
        &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-light">信用数据类, 芝麻分, 360报告, 同盾报告</span><br>
        <span class="badge badge-primary">同移动端同事接合数据接口</span><br>
        &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-light">手机端登录, 借款, 提交资料等操作</span><br>
        <span class="badge badge-primary">优化日常业务查询sql语句.</span><br>
        &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-light">借款端各种日常, 季度业绩统计, 风控业绩统计, 日常定时任务等..</span><br>
        <span class="badge badge-primary">协助组建数据仓库.</span><br>
        &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-light">从生产库拉取数据, 过滤, 拼装到BI库</span><br>
        <span class="badge badge-primary">协助风控/催收部门, 统计 归纳 分析借款客户人群的各种数据.</span><span class="badge badge-light"></span><br>
        &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-light">根据第三方数据清洗,挖掘.总结出风险点, 风险人群.</span><br>
      </div>
    </div>
    <div class="row border-bottom border-info rounded" style="background: linear-gradient(to bottom left, #ffffff , #fdffd2);">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-12">
            <br>
            <br>
            <span class="badge badge-light">2014/04 ~ 2015/11</span> <span class="badge badge-success">公司名2</span><br>
            <span class="badge badge-light">使用 <u>spring + strust2 + hibernate + mysql</u></span><br>
            <span class="badge badge-light">二次开发</span>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 border-bottom border-info rounded" style="">
            <span class="badge badge-secondary">侨鑫集团 企业内部管理软件</span><br>
              <span class="badge badge-primary">主要负责合同管理部分</span><br>
              &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-light">用户上传合同扫描文件, 文件使用阿里云oss保存, 数据库保存文件url路径, 提供文件保存, 读取接口</span>
          </div>
          <div class="col-md-6 border-bottom border-info rounded" style="">
            <span class="badge badge-secondary">卓志物流 订舱系统</span><br>
              <span class="badge badge-primary">主要负责内部数据(物流, 财务数据)展示部分</span><br>
              &nbsp;&nbsp;&nbsp;&nbsp;<span class="badge badge-light">从数据库查询,整理数据,并提供前端接口</span>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
          </div>
        </div>
      </div>
    </div>
  </div>
</div>


<div class="row" name="photo">
  <div class="col-md-12">
    <span class="badge badge-light">
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      emmmmmm.....还在往下看?!?!?!?!?!?
    </span><br> 
    <span class="badge badge-light">
      这是...需要照片?  好吧...可以...原以为敲代码是不需要考虑外貌的, 而且我也<s><span style="color : #c1c5cc">太不<span></s>不太好看<br>
      <img src="https://www.jiuwa.net/tuku/20180101/sTFj3Wny.jpg"><br>
      <br>
      <s><span style="color : #c1c5cc">明明只需要看我的代码啊!!!不是吗???<span></s>
      <br> 
    </span><br>
    <br> 

    <img src="http://ww3.sinaimg.cn/bmiddle/9150e4e5gw1f9ssyqcr6ej20k00k0js9.jpg"><br>

    <img src="" style="width: 150px; height: 150px;"><br>

    <span class="badge badge-light">好吧  希望没有吓到你.  就说到这里了.  顺祝工作愉快~</span><br>
    <img src="http://wx4.sinaimg.cn/large/814268e3gy1ffv1dqd0t3j20b40b4jrk.jpg">
  </div>
</div>

<footer>
<%@ include file="../baseElementJSP/normalFooter.jsp" %>
<script type='text/javascript'>
$(document).ready(function() {
  $(".question").mouseenter(function () {
    var qName = $(this).attr("name");

    $(".answer").fadeOut();
    $(".answer[name='"+qName+"']").fadeIn();
  });
});
</script>
</footer>
