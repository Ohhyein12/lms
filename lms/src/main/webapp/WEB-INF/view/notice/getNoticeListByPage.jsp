<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>LMS-TFT</title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/feather/feather.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- Plugin css for this page -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/vendors/ti-icons/css/themify-icons.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/select.dataTables.min.css">
  <!-- End plugin css for this page -->
  <!-- inject:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/tftace.jpg" />
  <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
  <style>
  	.bottom {margin-bottom : 30px;}
  </style>
</head>
<body>
  <div class="container-scroller">
    <!-- partial:partials/_navbar.html -->
    <jsp:include page="/inc/topbar.jsp"/>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
      <!-- partial:partials/_sidebar.html -->
      <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <jsp:include page="/inc/sidebar.jsp"/>
      </nav>
      <!-- partial -->
      <div class="main-panel">
        <div class="content-wrapper">
          <div class="row">
	          <div class="col-md-12 grid-margin">
		          <div class="row">
			          <div class="col-12 col-xl-4">
				           <div class="justify-content-end d-flex">
				           </div>
			          </div>
		          </div>
	          </div>
          </div>
          
		  <!-- 공지사항리스트 시작 --> 
          <div class="row">
            <div class="col-lg-10 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
	              <h3 class="bottom">[공지사항]</h3> 
                  <c:if test="${sessionLv >= 3}">
                    <a class="float-right btn btn-info" href="${pageContext.request.contextPath}/loginCheck/adminManagerCheck/addNotice">게시글 입력</a>
                </c:if>
                  <div class="table-responsive">
                    <table class="table">
				        <thead>
				        	<tr>
				        		<th>번호</th>
				        		<th>제목</th>
				        		<th>작성자</th>
				        		<th>작성날짜</th>
				        	</tr>
				        </thead>
				        <tbody>
			        		<c:forEach var="n" items="${list}">
				        		<tr>
					        		<td>${n.noticeNo}</td>
					        		<td><a href="${pageContext.request.contextPath}/loginCheck/getNoticeOne?noticeNo=${n.noticeNo}">${n.noticeTitle}</a></td>
					        		<td>${n.loginId}</td>
					        		<td>${n.createDate}</td>
					        	</tr>
			        		</c:forEach>
				        </tbody>
				    </table>
				    <c:if test="${currentPage > 1}">
				    	<a href="${pageContext.request.contextPath}/loginCheck/getNoticeListByPage?currentPage=${currentPage-1}">이전</a>
				    </c:if>
				    <c:if test="${currentPage < lastPage}">
				    	<a href="${pageContext.request.contextPath}/loginCheck/getNoticeListByPage?currentPage=${currentPage+1}">다음</a>
				    </c:if>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- 공지사항 리스트 끝 -->
          
          <!-- partial:partials/_footer.html -->
		  <jsp:include page="/inc/footer.jsp" />
		  
      </div>
      <!-- main-panel ends -->
    </div>   
    <!-- page-body-wrapper ends -->
  </div>
  <!-- container-scroller -->
 </div>
  <!-- plugins:js -->
  <script src="${pageContext.request.contextPath}/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page -->
  <script src="${pageContext.request.contextPath}/vendors/chart.js/Chart.min.js"></script>
  <script src="${pageContext.request.contextPath}/vendors/datatables.net/jquery.dataTables.js"></script>
  <script src="${pageContext.request.contextPath}/vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
  <script src="${pageContext.request.contextPath}/js/dataTables.select.min.js"></script>

  <!-- End plugin js for this page -->
  <!-- inject:js -->
  <script src="${pageContext.request.contextPath}/js/off-canvas.js"></script>
  <script src="${pageContext.request.contextPath}/js/hoverable-collapse.js"></script>
  <script src="${pageContext.request.contextPath}/js/template.js"></script>
  <script src="${pageContext.request.contextPath}/js/settings.js"></script>
  <script src="${pageContext.request.contextPath}/js/todolist.js"></script>
  <!-- endinject -->
  <!-- Custom js for this page-->
  <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
  <script src="${pageContext.request.contextPath}/js/Chart.roundedBarCharts.js"></script>
  <!-- End custom js for this page-->

</body>
</html>

