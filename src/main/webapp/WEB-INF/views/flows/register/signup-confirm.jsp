<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@include file="../shared/flows-header.jsp"%>

<!-- Page Content -->
<div class="content">
	<div class="row">
		<div class="col-sm-6">
			<!-- Display Personal Details -->
			<div class="card panel-primary">
				<div class="card-heading">
					<h4>Personal Details</h4>
				</div>
				<div class="card-body">
					<div class="text-center">
						<h4>${registerModel.user.firstname}
							${registerModel.user.lastname}</h4>
						<h5>Email: ${registerModel.user.email}</h5>
						<h5>Contact Number: ${registerModel.user.contactNumber}</h5>
						<h5>Role: ${registerModel.user.role}</h5>


					</div>
				</div>
				<div class="card-footer">
					<!-- Display footer to Edit Content-->
					<a href="${flowExecutionUrl}&_eventId_personal"
						class="btn btn-primary">Edit</a>
				</div>
			</div>
		</div>

		<!-- Address -->

		<div class="col-sm-6">
			<!-- Display Personal Details -->
			<div class="card card-primary">
				<div class="card-heading">
					<h4>Address</h4>
				</div>
				<div class="card-body">
					<div class="text-center">
						<h4>${registerModel.billing.adddressLineOne}</h4>
						<h5>${registerModel.billing.addressLineTwo}</h5>
						<h5>${registerModel.billing.city}-
							${registerModel.billing.postalCode}</h5>
						<h5>${registerModel.billing.state}-
							${registerModel.billing.country}</h5>
					</div>
				</div>
				<div class="card-footer">
					<!-- Display footer to Edit Content -->
					<a href="${flowExecutionUrl}&_eventId_billing"
						class="btn btn-primary">Edit</a>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-4 col-sm-offset-4">
			<div class="text-center">
				<a href="${flowExecutionUrl}&_eventId_submit"
					class="btn btn-primary">Confirm</a>
			</div>
		</div>
	</div>
</div>

<%@include file="../shared/flows-footer.jsp"%>