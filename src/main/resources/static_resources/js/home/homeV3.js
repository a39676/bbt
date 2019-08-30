/**
 * for home.jsp
 */
$(document).ready(function() {

  var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
    sURLVariables = sPageURL.split('&'),
    sParameterName,
    i;
    for (i = 0; i < sURLVariables.length; i++) {
      sParameterName = sURLVariables[i].split('=');
      if (sParameterName[0] === sParam) {
        return sParameterName[1] === undefined ? true : sParameterName[1];
      }
    }
  };

  $(".slider-category").click(function () {
    var uuid = $(this).attr("uuid");
    loadArticleLongSummaryFirstPage(uuid);
  });


  document.getElementById("loginTag").onclick = function () {
    showLogin()
  };
  function showLogin(){ 
    var url = "/login/login";
    $.ajax({  
      type : "GET",  
      async : true,
      url : url,  
      success:function(datas){
        $("#dynamicLoginDiv").html(datas);
      },  
      error: function(datas) {                
      }  
    });  
  };
  
  /** 
  * 页面滚动信息频道
  * 2019-06-08 spring security 相关改动中 socket 通讯暂时搁置
  */
  // function connectPublic() {
  //   var hostname = location.hostname;
  //   // var socket = new SockJS('/topic/public');
  //   var socket = new SockJS(hostname + '/topic/public');
  //   stompClient = Stomp.over(socket);
  //   stompClient.connect({}, function (frame) {
  //     stompClient.subscribe('/topic/public', function (message) {
  //       showMessage(JSON.parse(message.body).content);
  //     });
  //   });
  // }

  // function showMessage(message) {
  //   $("#homepageRollingAnnouncement").text(message);
  // }

  // connectPublic();

});
