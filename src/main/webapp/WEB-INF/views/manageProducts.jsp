<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<div class="container">
	<div class="row">

		<c:if test="${not empty message}">
			<div class="col-xs-12 col-md-offset-2 col-md-8">
				<div class="alert alert-success alert-dismissible fade show"
					role="alert">${message}
					<button type="button" class="close" data-dismiss="alert">&times;</button>

				</div>
			</div>
		</c:if>
		<div class="col-md-offset-2 col-md-8">

			<div class="card">

				<div class="card-header">
					<h4>Product Management</h4>

				</div>

				<div class="card-body">
					<!-- FORM ELEMENTs -->


					<sf:form class="form-horizontal" modelAttribute="product"
						action="${contextRoot}/manage/products" method="POST"
						enctype="multipart/form-data">
						<!-- First From Group -->
						<div class="form-group">
							<label class="control-label col-md-4" for="name">Enter
								Product Name</label>

							<div class="col-md-8">
								<sf:input type="text" path="name" id="name"
									placeholder="Product Name" class="form-control" />
							</div>
							<sf:errors path="name" cssClass="help-block" element="em" />
						</div>
						<!--   - -  - - -->

						<!-- Second From Group -->
						<div class="form-group">
							<label class="control-label col-md-4" for="brand">Enter
								Brand Name</label>

							<div class="col-md-8">
								<sf:input type="text" path="brand" id="brand"
									placeholder="Brand Name" class="form-control" />
							</div>
							<sf:errors path="brand" cssClass="help-block" element="em" />
						</div>
						<!--   - -  - - -->

						<!-- Third From Group -->
						<div class="form-group">
							<label class="control-label col-md-4" for="brand">Product
								Description</label>

							<div class="col-md-8">
								<sf:textarea path="description" id="description" rows="4"
									placeholder="Write a Description here.." class="form-control" />
							</div>
							<sf:errors path="description" cssClass="help-block" element="em" />
						</div>
						<!--   - -  - - -->

						<!-- Fouth From Group -->
						<div class="form-group">
							<label class="control-label col-md-4" for="brand">Enter
								Unit Price</label>

							<div class="col-md-8">
								<sf:input type="text" path="unitPrice" id="price"
									placeholder="Unit Price in &#8377;" class="form-control" />
							</div>
							<sf:errors path="unitPrice" cssClass="help-block" element="em" />
						</div>
						<!--   - -  - - -->


						<!-- fifth From Group -->
						<div class="form-group">
							<label class="control-label col-md-4" for="brand">Quantity
								Available</label>

							<div class="col-md-8">
								<sf:input type="number" path="quantity" id="quantity"
									placeholder="Quantity Available" class="form-control" />
							</div>

						</div>
						<!--   - -  - - -->

						<!-- File Element For Image From Group -->
						<div class="form-group">
							<label class="control-label col-md-4" for="file">Select
								an Image:</label>

							<div class="col-md-8">
								<sf:input type="file" path="file" id="file" class="form-control" />
								<sf:errors path="file" cssClass="help-block" element="em" />
							</div>

						</div>
						<!--   - -  - - -->


						<!-- Sixth From Group -->
						<!-- Setting Dropdown from scrath -->
						<div class="form-group">
							<label class="control-label col-md-4" for="brand">Select
								Category:</label>
							<sf:select class="form-control" id="categoryId" path="categoryId"
								items="${categories}" itemLabel="name" itemValue="id" />


							<c:if test="${product.id == 0 }">
								<br />
								<div class="text-right">
									<button type="button" data-toggle="modal"
										data-target="#myCategoryModal" class="btn btn-warning btn-xs">Add
										Category</button>
								</div>
							</c:if>

							<div class="col-md-8"></div>
						</div>
						<!--   - -  - - -->


						<!-- Submit From Group -->
						<div class="form-group">

							<div class="col-md-offset-4 col-md-8">
								<input type="submit" name="submit" id="submit" value="Submit"
									class="btn btn-primary" />
							</div>
						</div>
						<!--   - -  - - -->
						<!-- Hidden Fields -->
						<sf:hidden path="id" />
						<sf:hidden path="code" />
						<sf:hidden path="supplierId" />
						<sf:hidden path="isactive" />
						<sf:hidden path="purchases" />
						<sf:hidden path="views" />


					</sf:form>




				</div>

			</div>

		</div>

	</div>

	<!-- Table Product for admin to manage product -->
	<div class="row">


		<div class="col-xs-12">
			<h3>Available Products:</h3>

		</div>
		<br>
		<div class="col-xs-12">
			<div class="container-fluid">
				<div class="table-responsive">


					<table id="adminProductsTable"
						class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Id</th>
								<th>&#160;</th>
								<th>Name</th>
								<th>Brand</th>
								<th>Quantity</th>
								<th>Unit Price</th>
								<th>Active</th>
								<th>Edit</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>Id</th>
								<th>&#160;</th>
								<th>Name</th>
								<th>Brand</th>
								<th>Quantity</th>
								<th>Unit Price</th>
								<th>Active</th>
								<th>Edit</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myCategoryModal" role="dialog"
		tabindex="-1">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">

					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">Add New Category</h4>
				</div>

				<div class="modal-body">
					<!-- Modal Body Form -->
					<sf:form modelAttribute="category"
						action="${contextRoot}/manage/category" method="POST"
						class="form-horizontal" id="categoryForm">
						<div class="form-group">
							<label for="category_name" class="control-label col-md-4">Category
								Name</label>
							<div class="col-md-8">
								<sf:input type="text" path="name" class="form-control"
									id="category_name" />
							</div>
						</div>

						<div class="form-group">
							<label for="category_description" class="control-label col-md-4">Category
								Description</label>
							<div class="col-md-8">
								<sf:textarea cols="" rows="5" path="description"
									id="category_description" class="form-control" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<input type="submit" value="Add Category"
									class="btn btn-primary" />
							</div>
						</div>


					</sf:form>
				</div>
			</div>
		</div>
	</div>

</div>