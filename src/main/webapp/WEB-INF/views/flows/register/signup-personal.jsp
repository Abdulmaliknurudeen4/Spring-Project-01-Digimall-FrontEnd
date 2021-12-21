<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@include file="../shared/flows-header.jsp"%>
<!-- Page Content -->
<div class="content">

	<div class="row">

		<div class="col-md-6 col-md-offset-3">

			<div class="card card-primary">

				<div class="card-heading">
					<h4>Sign Up - Personal</h4>
				</div>

				<div class="card-body">

					<sf:form method="POST" modelAttribute="user"
						class="form-horizontal" id="registerForm">


						<div class="form-group">
							<label class="control-label col-md-4">First Name</label>
							<div class="col-md-8">
								<sf:input type="text" path="firstname" class="form-control"
									placeholder="First Name" />
								<sf:errors path="firstname" cssClass="help-block" element="em" />
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-md-4">Last Name</label>
							<div class="col-md-8">
								<sf:input type="text" path="lastname" class="form-control"
									placeholder="Last Name" />
								<sf:errors path="lastname" cssClass="help-block" element="em" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Email</label>
							<div class="col-md-8">
								<sf:input type="text" path="email" class="form-control"
									placeholder="abc@zyx.com" />
								<sf:errors path="email" cssClass="help-block" element="em" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Contact Number</label>
							<div class="col-md-8">
								<sf:input type="text" path="contactNumber" class="form-control"
									placeholder="XXXXXXXXXX" maxlength="10" />
								<sf:errors path="contactNumber" cssClass="help-block"
									element="em" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Password</label>
							<div class="col-md-8">
								<sf:input type="password" path="password" class="form-control"
									placeholder="Password" />
								<sf:errors path="password" cssClass="help-block" element="em" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-4">Confirm Password</label>
							<div class="col-md-8">
								<sf:input type="password" path="confirmPassword" class="form-control"
									placeholder="Re-enter Password" />
								<sf:errors path="confirmPassword" cssClass="help-block" element="em" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-4">Select Role</label>
							<div class="col-md-8">
								<label class="radio-inline"> <sf:radiobutton path="role"
										value="USER" checked="checked" /> User
								</label> <label class="radio-inline"> <sf:radiobutton
										path="role" value="SUPPLIER" /> Supplier
								</label>
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<button type="submit" class="btn btn-primary"
									name="_eventId_billing">
									Next- Billing <span class="fa fa-arrow-right"></span>
								</button>
							</div>
						</div>


					</sf:form>


				</div>


			</div>


		</div>


	</div>
</div>
<!-- Continue From Video Part 8 3:45 -->
<%@include file="../shared/flows-footer.jsp"%>