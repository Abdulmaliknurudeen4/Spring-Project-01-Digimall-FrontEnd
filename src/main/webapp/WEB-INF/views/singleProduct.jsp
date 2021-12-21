<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<div class="container">
	<div class="row">
		<div class="col-xs-12">

			<ol class="breadcrumb">
				<li><a href="${contextRoot}/home"></a>Home</li>
				<li><a href="${contextRoot}/show/all/products"></a>Products</li>
				<li class="active">${product.name}</li>
			</ol>
		</div>

		<div class="row">
			<!-- Display Product Image -->
			<div class="col-xs-12 col-sm-4">
				<div class="thumbnail">
					<img alt="" src="${images}/${product.code}.jpg"
						class="img img-responsive">
				</div>
			</div>
			<!-- Display Product Description -->
			<div class="col-xs-12 col-sm-8">
				<h3>${product.name}</h3>
				<hr>
				<p>${product.description}</p>
				<hr>
				<h4>
					Price: <strong> &#8377; ${product.unitPrice}</strong>
				</h4>




				<c:choose>

					<c:when test="${product.quantity <1}">
						<h6>
							Qty. Available : <span style="color: red">Out of Stock !</span>
						</h6>

					</c:when>
					<c:otherwise>
						<h6>Qty. Available: ${product.quantity}</h6>
					</c:otherwise>
				</c:choose>


<security:authorize access="hasAuthority('USER')">

				<c:choose>

					<c:when test="${product.quantity <1}">
						<a href="javascript:void(0)"></a>
						<a href="${contextRoot}/cart/add/${product.id}/product"
							class="btn btn-success disabled"><strike><span
								class="fa fa-cart-plus"></span></strike></a>
					</c:when>
					<c:otherwise>
						<a href="${contextRoot}/cart/add/${product.id}/product"
							class="btn btn-success"><span class="fa fa-cart-plus"></span></a>
					</c:otherwise>
				</c:choose>
				</security:authorize>
				
				<security:authorize access="hasAuthority('ADMIN')">
				<a href="${contextRoot}/manage/${product.id}/product"
							class="btn btn-warning"><span class="fa fa-pencil"></span></a>
				
				
				</security:authorize>



				<!-- Display Add to Cart -->


				<!-- Back Button -->
				<a href="${contextRoot}/show/all/products" class="btn btn-primary">Back</a>

			</div>
		</div>

	</div>

</div>