<div class="container">

	<div class="row">

		<!-- Would be to load sidebar -->

		<div class="col-md-3">
			<%@include file="./shared/sidebar.jsp"%>
		</div>
		<!-- Would be to display actual products -->
		<div class="col-md-9">
			<!-- Added breadcrum Component -->
			<div class="row">
				<div class="col-lg-12">
					<c:if test="${userClickAllProduct == true}">

						<script>
							window.categoryId = '';
						</script>

						<ol class="breadcrumb">
							<li><a href="${contextRoot}/home">Home </a></li>
							<li class="breadcrumb-item active">All Products</li>
						</ol>
					</c:if>

					<c:if test="${userClickCategoryProduct == true}">

						<script>
							window.categoryId = '${category.id}';
						</script>

						<ol class="breadcrumb">
							<li><a href="${contextRoot}/home">Home </a></li>
							<li class="breadcrumb-item active">Category</li>
							<li class="breadcrumb-item active">${category.name}</li>
						</ol>
					</c:if>
				</div>

				<div class="row">
					<div class="col-xs-12">
						<div class="container-fluid">
							<div class="table-responsive">
								<table id="productListTable"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<th></th>
											<th>Name</th>
											<th>Brand</th>
											<th>UnitPrice</th>
											<th>Quantity. Av</th>
											<th></th>
										</tr>
									</thead>


									<tfoot>
										<tr>
											<th></th>
											<th>Name</th>
											<th>Brand</th>
											<th>UnitPrice</th>
											<th>Quantity. Av</th>
											<th></th>
										</tr>
									</tfoot>

								</table>
							</div>
						</div>

					</div>
				</div>

			</div>

		</div>

	</div>
</div>