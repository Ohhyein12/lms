<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body>
	<nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
      <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
        <a class="navbar-brand brand-logo mr-5" href="${pageContext.request.contextPath}/loginCheck/main">LMS-TFT</a>
        <a class="navbar-brand brand-logo-mini" href="${pageContext.request.contextPath}/loginCheck/main">LMS</a>
      </div>
      <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
        <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
          <span class="icon-menu"></span>
        </button>
        <ul class="navbar-nav mr-lg-2">
          <li class="nav-item nav-search d-none d-lg-block">
            <div class="input-group">
               
            </div>
          </li>
        </ul>
        <ul class="navbar-nav navbar-nav-right">
          <li class="nav-item nav-profile dropdown">
          	<c:choose>
          		<c:when test="${sessionLv == 4}">
          			<a class="nav-link dropdown-toggle" data-toggle="dropdown" id="profileDropdown">
		              <img src="${pageContext.request.contextPath}/file/memberPhoto/admin.PNG" alt="profile"/>
		            </a>
          		</c:when>
          		<c:otherwise>
		            <a class="nav-link dropdown-toggle" data-toggle="dropdown" id="profileDropdown">
		              <img src="${pageContext.request.contextPath}/file/memberPhoto/${memberFileName}" alt="profile"/>
		            </a>
          		</c:otherwise>
          	</c:choose>
            <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
	        	<a class="dropdown-item" href="${pageContext.request.contextPath}/loginCheck/getMemberOne?loginId=${sessionId}">
	            	<i class="ti-settings text-primary"></i> MyPage 
	         	</a>
	            <a class="dropdown-item" href="${pageContextPath.request.getContextPath}/lms/loginCheck/logout">
	               <i class="ti-power-off text-primary"></i>
	               Logout
	            </a>
            </div>
          </li>
        </ul>
        <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
          <span class="icon-menu"></span>
        </button>
      </div>
    </nav>
</body>
</html>