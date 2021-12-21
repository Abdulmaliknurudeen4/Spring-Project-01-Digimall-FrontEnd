<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="css" value="/resources/css" />
<spring:url var="js" value="/resources/js" />
<spring:url var="images" value="/resources/images" />
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<!-- For CSRF AJAx request -->
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">


<title>Digimall - ${title}</title>

<script>
	window.menu = '${title}';
	window.contextRoot = '${contextRoot}';
</script>

<!-- Bootstrap core CSS -->
<link href="${css}/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${css}/myapp.css" rel="stylesheet">

<!-- DataTable Boostrap -->
<link href="${css}/dataTables.bootstrap4.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="${css}/font-awesome.min.css" rel="stylesheet">
</head>

<body>
 <div class="se-pre-con"></div>
	<div class="wrapper">
		<!-- Navigation -->
		<%@include file="./shared/navbar.jsp"%>

		<!-- Page Content -->
		<div class="content">
			<!-- Loading Home Content when user clicks home option -->
			<c:if test="${userClickHome == true}">
				<%@include file="home.jsp"%>
			</c:if>
			<!-- Loading About Content when user clicks home option -->
			<c:if test="${userClickAbout == true}">
				<%@include file="about.jsp"%>
			</c:if>
			<!-- Loading Contact Content when user clicks home option -->
			<c:if test="${userClickContact == true}">
				<%@include file="contact.jsp"%>
			</c:if>

			<!-- Loading Contact Content when user clicks home option -->
			<c:if
				test="${userClickAllProduct == true or userClickCategoryProduct == true}">
				<%@include file="listProducts.jsp"%>
			</c:if>

			<!-- Loading when user click show product -->
			<c:if test="${userClickShowProduct == true}">
				<%@include file="singleProduct.jsp"%>
			</c:if>

			<!-- Loading when user click manage products -->
			<c:if test="${userClickManageProduct == true}">
				<%@include file="manageProducts.jsp"%>
			</c:if>
			<!-- Loading when user click cart -->
			<c:if test="${userClickShowCart == true}">
				<%@include file="cart.jsp"%>
			</c:if>

		</div>
		<!-- /.container -->

		<!-- Footer -->
		<%@include file="./shared/footer.jsp"%>
		<!-- Bootstrap core JavaScript -->
		<script src="${js}/jquery-3.3.1.min.js"></script>
		<script src="${js}/bootstrap.bundle.min.js"></script>
		<!-- Jqueyr Table Plugins -->
		<script src="${js}/jquery.dataTables.js"></script>
		<!-- Jqueyr Table Plugins Bootstrap -->
		<script src="${js}/dataTables.bootstrap4.js"></script>
		<!-- bootBox JavaScript -->
		<script src="${js}/bootbox.min.js"></script>
		<!-- Validate JavaScript -->
		<script src="${js}/jquery.validate.js"></script>
		<!-- Custom JavaScript -->
		<script src="${js}/myapp.js"></script>
	</div>
</body>

</html>
