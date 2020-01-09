<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="row" >
  <table>
    <tr>
      <td>公司名</td>
      <td>简历名</td>
      <td>查看总数</td>
      <td>最后查看时间</td>
      <td>平均兴趣</td>
      <td>最后兴趣</td>
      <td>companyLink</td>
    </tr>
    <c:forEach items="${voList}" var="vo">
      <tr>
        <td>${vo.companyName}</td>
        <td>${vo.myResumeName}</td>
        <td>${vo.watchCount}</td>
        <td>${vo.lastWatchTime}</td>
        <td>${vo.degreeOfInterestAvg}</td>
        <td>${vo.degreeOfInterest}</td>
        <td>${vo.companyLink}</td>
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
