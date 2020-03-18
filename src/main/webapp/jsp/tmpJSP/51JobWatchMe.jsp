<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div class="row" >
  <table>
    <tr>
      <td align="center">公司名</td>
      <td align="center">简历名</td>
      <td align="center">查看总数</td>
      <td align="center">最后查看时间</td>
      <td align="center">平均兴趣</td>
      <td align="center">最后兴趣</td>
    </tr>
    <c:forEach items="${voList}" var="vo">
      <tr>
        <td align="center"><a href="${vo.companyLink}" target="_blank">${vo.companyName}</a></td>
        <td align="center">${vo.myResumeName}</td>
        <td align="center">${vo.watchCount}</td>
        <td align="center">${vo.lastWatchTime}</td>
        <td align="center">${vo.degreeOfInterestAvg}</td>
        <td align="center">${vo.degreeOfInterest}</td>
      </tr>
    </c:forEach>
  </table>
</div>


<footer>
<script type='text/javascript'>
$(document).ready(function() {
  
});
</script>
</footer>
