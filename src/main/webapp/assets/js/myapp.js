$(function() {
	// for adding a loader
	
	$precon = $('.se-pre-con');
	if ($precon) {
		setTimeout(function() {
			$precon.fadeOut('slow');
		}, 500);
	}
	// solving the active menu problem
	// using switch to get the class menu
	switch (menu) {
	case 'About Us':
		$('#about').addClass('active');
		break;
	case 'Contact Us':
		$('#contact').addClass('active');
		break;
	case 'Home':
		$('#home').addClass('active');
		break;
	case 'All Products':
		$('#listProducts').addClass('active');
		break;
	case 'Manage Products':
		$('#manageProducts').addClass('active');
		break;
	case 'User Cart':
		$('#userCart').addClass('active');
		break;
	default:
		$('#listProducts').addClass('active');
		$('#a_' + menu).addClass('active');
		break;
	}
	// crsf token issues for ajax request pollings

	var token = $('meta[name="_csrf"]').attr('content');
	var header = $('meta[name="_csrf_header"]').attr('content');

	if (token.length > 0 && header.length > 0) {

		// set token for request
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		})
	}

	// code for jquery dataTable
	var $table = $('#productListTable');
	// execute the below code only where the table is availabe only

	if ($table.length) {

		// Json Url
		var jsonUrl = '';

		if (window.categoryId == '') {
			jsonUrl = window.contextRoot + '/json/data/all/products';
		} else {
			jsonUrl = window.contextRoot + '/json/data/category/'
					+ window.categoryId + '/products';
		}

		$table
				.DataTable({
					lengthMenu : [ [ 3, 5, 10, -1 ],
							[ '3 Records', '5 Records', '10 Records', 'ALL' ] ],
					pageLength : 5,
					ajax : {
						url : jsonUrl,
						dataSrc : ''
					},
					columns : [
							{
								data : 'code',
								bSortable : false,
								mRender : function(data, type, row) {
									return '<img src ="' + window.contextRoot
											+ '/resources/images/' + data
											+ '.jpg" class="dataTableImg" />';
								}
							},
							{
								data : 'name'

							},
							{
								data : 'brand'

							},
							{
								data : 'unitPrice',
								mRender : function(data, type, row) {
									return '&#8377; ' + data
								}

							},
							{
								data : 'quantity',
								mRender : function(data, type, row) {
									if (data < 1) {
										return '<span style="color:red">Out of Stock</span>';
									}
									return data;
								}

							},
							{
								data : 'id',
								bSortable : false,
								mRender : function(data, type, row) {
									
									
									
									
									
									
									
									
									
						
										
										
									
									
									
									
									
									
									var str = '';
									str += '<a href="'
											+ window.contextRoot
											+ '/show/'
											+ data
											+ '/product" class="btn btn-primary"><span class="fa fa-eye"></a> &#160;';

									
									if(userRole !== 'ADMIN') {
										if (row.quantity < 1) {
											str += '<a href="javascript:void(0)" class="btn btn-success disabled"><span class="fa fa-cart-plus"></a>';
										} else {
	
											str += '<a href="'
													+ window.contextRoot
													+ '/cart/add/'
													+ data
													+ '/product" class="btn btn-success"><span class="fa fa-cart-plus"></a>';
										}
									}
									else {
										str += '<a href="'
											+ window.contextRoot
											+ '/manage/'
											+ data
											+ '/product" class="btn btn-warning"><span class="fa fa-pencil"></a>';
									}
									
									return str;
									
				
									
									
									
								}
							} ]

				});
	}

	/*------*/
	/* for fading out the alert message after 3 seconds */
	$alert = $('.alert');
	if ($alert.length) {
		setTimeout(function() {
			$alert.fadeOut('slow');
		}, 3000);
	}

	// /-----------------
	// jqeury for toogle button
	$('.switch input[type="checkbox"]')
			.on(
					'change',
					function() {

						var checkbox = $(this);
						var checked = checkbox.prop('checked');

						var dMsg = (checked) ? ' You want to Activate the product ?'
								: 'You want to Deactivate the product';
						var value = checkbox.prop('value');

						bootbox
								.confirm({

									size : 'medium',
									title : 'Product Activation & Deactivation',
									message : dMsg,
									callback : function(confirmed) {
										if (confirmed) {
											bootbox
													.alert({

														size : 'medium',
														title : 'Information',
														message : 'You are going to perform operation on product'
																+ value
													});
											console.log(value);
										} else {
											checkbox.prop('checked', !checked);
										}
									}
								});

					});

	// --------------------------------------------------------
	// -ADMIN DATATABLE
	// ------------------------------------------------

	var $adminProductsTable = $('#adminProductsTable');
	// execute the below code only where the table is availabe only

	if ($adminProductsTable.length) {

		// Json Url
		var jsonUrl = window.contextRoot + '/json/data/admin/all/products';

		$adminProductsTable
				.DataTable({
					lengthMenu : [ [ 10, 30, 50, -1 ],
							[ '10 Records', '30 Records', '50 Records', 'ALL' ] ],
					pageLength : 30,
					ajax : {
						url : jsonUrl,
						dataSrc : ''
					},
					columns : [

							{
								data : 'id'
							},
							{
								data : 'code',
								bSortable : false,
								mRender : function(data, type, row) {
									return '<img src ="'
											+ window.contextRoot
											+ '/resources/images/'
											+ data
											+ '.jpg" class="adminDataTableImg" />';
								}
							},
							{
								data : 'name'

							},
							{
								data : 'brand'

							},
							{
								data : 'quantity',
								mRender : function(data, type, row) {
									if (data < 1) {
										return '<span style="color:red">Out of Stock</span>';
									}
									return data;
								}

							},
							{
								data : 'unitPrice',
								mRender : function(data, type, row) {
									return '&#8377; ' + data
								}

							},
							{
								data : 'isactive',
								bSortable : false,
								mRender : function(data, type, row) {

									var str = '';
									str += '<label class="switch">';

									if (data) {
										str += '<input type="checkbox" checked="checked" value="'
												+ row.id + '" />';
									} else {
										str += '<input type="checkbox" value="'
												+ row.id + '" />';
									}

									str += '<div class="slider"></div></label>';

									return str;
								}

							},
							{

								data : 'id',
								bSortable : false,
								mRender : function(data, type, row) {
									var str = '';

									str += '<a href="'
											+ window.contextRoot
											+ '/manage/'
											+ data
											+ '/product" class="btn btn-warning">';

									str += '<span class="fa fa-pencil"></span></a>';

									return str;

								}
							} ],

					initComplete : function() {
						var api = this.api();
						api
								.$('.switch input[type="checkbox"]')
								.on(
										'change',
										function() {

											var checkbox = $(this);
											var checked = checkbox
													.prop('checked');

											var dMsg = (checked) ? ' You want to Activate the product ?'
													: 'You want to Deactivate the product';
											var value = checkbox.prop('value');

											bootbox
													.confirm({

														size : 'medium',
														title : 'Product Activation & Deactivation',
														message : dMsg,
														callback : function(
																confirmed) {
															if (confirmed) {
																var activationUrl = window.contextRoot
																		+ '/manage/product/'
																		+ value
																		+ '/activation';
																$
																		.post(
																				activationUrl,
																				function(
																						data) {
																					bootbox
																							.alert({

																								size : 'medium',
																								title : 'Information',
																								message : data
																							});
																				})

																console
																		.log(value);

															} else {
																checkbox
																		.prop(
																				'checked',
																				!checked);
															}
														}
													});

										});

					}

				});
	}

	// -----------------------------------------------
	// validation code for category

	var $categoryForm = $('#categoryForm');
	if ($categoryForm.length) {

		$categoryForm
				.validate({

					rules : {
						name : {
							required : true,
							minlength : 5
						},
						description : {
							required : true
						}
					},
					messages : {

						name : {
							required : 'Please add the Category Name',
							minlength : 'The Category name should not be less than 2 characters'
						},
						description : {
							required : 'Please add the Category Description'

						},
						errorElement : "em",
						errorPlacement : function(error, element) {
							// add the class of help-block
							error.addClass("help-block");
							// add the error element after the input element
							error.insertAfter(element);

						}

					}
				});
	}
	// -------------------------------------------

	// -----------------------------------------------
	// validation for login form

	var $loginForm = $('#loginForm');
	if ($loginForm.length) {

		$loginForm.validate({

			rules : {
				username : {
					required : true,
					email : true
				},
				password : {
					required : true
				}
			},
			messages : {

				username : {
					required : 'Please Enter Username',
					email : 'Please input in correct Characters'
				},
				password : {
					required : 'Please input password'

				},
				errorElement : "em",
				errorPlacement : function(error, element) {
					// add the class of help-block
					error.addClass("help-block");
					// add the error element after the input element
					error.insertAfter(element);

				}

			}
		});
	}
	// -------------------------------------------

	// handling the click event of refresh cart button

	$('button[name="refreshCart"]')
			.click(
					function() {
						var cartLineId = $(this).attr('value');
						var countElement = $('#count_' + cartLineId);

						var originalCount = countElement.attr('value');

						var currentCount = countElement.val();

						// work only when count has change
						if (currentCount != originalCount) {
							console.log("current count" + currentCount);
							console.log("original Count" + originalCount);

							if (countElement.val() < 1
									|| countElement.val() > 3) {
								// revert back to original count if user gives
								// wrong count element
								countElement.val(originalCount)
								bootbox
										.alert({

											size : 'medium',
											title : 'Error',
											message : 'Product Count should be mininum 1 and maximum 3!'
										});
							} else {

								var updateUrl = window.contextRoot + '/cart/'
										+ cartLineId + '/update?count='
										+ currentCount;
								// forward to controller
								window.location.href = updateUrl;
							}

						}

					});

});
