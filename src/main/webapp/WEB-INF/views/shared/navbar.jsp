<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="container">
		<a class="navbar-brand" href="${contextRoot}/home">DigiMall Online
			Store</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarResponsive" aria-controls="navbarResponsive"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item" id="about"><a class="nav-link"
					href="${contextRoot}/about">About Us</a></li>
				<li class="nav-item" id="contact"><a class="nav-link"
					href="${contextRoot}/contact">Contact Us</a></li>
				<li class="nav-item" id="listProducts"><a class="nav-link"
					href="${contextRoot}/show/all/products">View Products</a></li>
					<security:authorize access="hasAuthority('ADMIN')">
				<li class="nav-item" id="manageProducts"><a class="nav-link"
					href="${contextRoot}/manage/products">Manage Products</a></li></security:authorize>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<security:authorize access="isAnonymous()">
					<li class="nav-item" id="register"><a class="nav-link"
						href="${contextRoot}/register">SignUp</a></li>
					<li class="nav-item" id="login"><a class="nav-link"
						href="${contextRoot}/login">Login</a></li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
					<li class="dropdown" id="userCart"><a href="javascript:void(0)"
						class="btn btn-default dropdown-toggle" id="dropdownMenu1"
						data-toggle="dropdown"> ${userModel.fullName}<span
							class="caret"></span>
					</a> <!-- Dropdown Menu -->
						<ul class="dropdown-menu">

							<security:authorize access="hasAuthority('USER')">
								<li><a href="${contextRoot}/cart/show"> <span
										class="fa fa-shopping"></span> <i class="fa fa-badge">${userModel.cart.cartLines}</i>
										-&#8377; ${userModel.cart.grandTotal}
								</a></li>


								<li class="divider" role="separator"></li>
							</security:authorize>
							<li><a href="${contextRoot}/perform-logout">LogOut</a></li>

						</ul></li>
				</security:authorize>

			</ul>

		</div>
	</div>
</nav>

<script>
window.userRole='${userModel.role}';

</script>