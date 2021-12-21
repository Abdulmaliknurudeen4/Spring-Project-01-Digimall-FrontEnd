package com.alpha.practice.digimall.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.alpha.practice.digimallbackend.dto.Product;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		// write checks for file selection
		if (product.getFile() == null || product.getFile().getOriginalFilename().equals("")) {

			errors.rejectValue("file", null, "Please Select an Image File to Upload");
			return;
		}

		if (!(product.getFile().getContentType().equals("image/jpeg")
				|| product.getFile().getContentType().equals("image/png")
				|| product.getFile().getContentType().equals("image/gif"))) {

			errors.rejectValue("file", null, "Please use only image files for upload");
			return;

		}

	}

}
