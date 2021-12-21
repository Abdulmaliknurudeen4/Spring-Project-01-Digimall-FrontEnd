package com.alpha.practice.digimall.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alpha.practice.digimall.util.FileUploadUtility;
import com.alpha.practice.digimall.validator.ProductValidator;
import com.alpha.practice.digimallbackend.dao.CategoryDAO;
import com.alpha.practice.digimallbackend.dao.ProductDAO;
import com.alpha.practice.digimallbackend.dto.Category;
import com.alpha.practice.digimallbackend.dto.Product;

@Controller
@RequestMapping("/manage")
public class ManagementController {

	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private ProductDAO productDAO;

	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name = "operation", required = false) String operation) {
		ModelAndView mv = new ModelAndView("page");

		mv.addObject("userClickManageProduct", true);
		mv.addObject("title", "Manage Products");
		// creating products and adding some values
		Product nProduct = new Product();
		nProduct.setSupplierId(1);
		nProduct.setIsactive(true);
		// add to mv
		mv.addObject("product", nProduct);

		if (operation != null) {
			if (operation.equals("product")) {
				mv.addObject("message", "product submitted succesfully to the admin");
			} else if (operation.equals("category")) {
				mv.addObject("message", "Category added Successfully to the Application");
			}
		}

		return mv;

	}

//returning categories for all the meetings
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryDAO.list();
	}

	// handling product sumisssion
	/*
	 * Always make sure to add the directvalid annotation, the binding result should
	 * be after the modelAttribute not after it, Ok
	 */
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mproduct, BindingResult results,
			Model model, HttpServletRequest request) {
		/*
		 * //checking if the product is new or old //retrieving it from the hidden form
		 * in the manageProduct.jsp //if it is new, Validate the Product
		 */ if (mproduct.getId() == 0) {
			new ProductValidator().validate(mproduct, results);
		} else {
			/*
			 * //if it is not new, please check again if the //user is not adding any file
			 * //if the user is adding any file , please validate those files
			 */ if (!mproduct.getFile().getOriginalFilename().equals("")) {
				// validate if the the user is putting a new product
				new ProductValidator().validate(mproduct, results);
			}
		}

		// check for errors
		if (results.hasErrors()) {
			model.addAttribute("userClickManageProduct", true);
			model.addAttribute("title", "Manage Products");
			model.addAttribute("message", "Validation failed for Product Submission!!!");

			return "page";
		}

		// logs
		logger.info(mproduct.toString());
		// create a new product
		if (mproduct.getId() == 0) {
			// create new product if id is equals to zero
			productDAO.add(mproduct);
		} else {
			// update the product if the id is not 0
			productDAO.update(mproduct);
		}
//checking if the Product has any file attached to it
		// no validation yet
		if (!mproduct.getFile().getOriginalFilename().equals("")) {
			FileUploadUtility.uploadFile(request, mproduct.getFile(), mproduct.getCode());
		}

		return "redirect:/manage/products?operation=product";
	}

	@RequestMapping(value = "/product/{id}/activation", method = RequestMethod.POST)
	@ResponseBody
	public String handleProductActivation(@PathVariable int id) {
		// fetch product
		Product product = productDAO.get(id);
		// active ?
		boolean isActive = product.isIsactive();
		// sett
		product.setIsactive(!product.isIsactive());
		productDAO.update(product);

		return (isActive) ? "You have successfully deactivated the Product with Id of " + product.getId()
				: "You have successfully activated the Product with the id of " + product.getId();
	}

	@RequestMapping(value = "/{id}/product", method = RequestMethod.GET)
	public ModelAndView showEditProduct(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("page");

		mv.addObject("userClickManageProduct", true);
		mv.addObject("title", "Manage Products");
		// creating products and adding some values
		Product nProduct = productDAO.get(id);
		// setProduct fetched
		mv.addObject("product", nProduct);
		return mv;

	}

	// returning categories for all the meetings
	@ModelAttribute("category")
	public Category getCategory() {
		return new Category();
	}

	/* add a new category */
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public String handleCategorySubmission(@ModelAttribute Category category) {
		categoryDAO.add(category);
		return "redirect:/manage/products?operation=category";
	}

}
