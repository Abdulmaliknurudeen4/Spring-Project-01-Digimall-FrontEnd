package com.alpha.practice.digimall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alpha.practice.digimall.exception.ProductNotFoundException;
import com.alpha.practice.digimallbackend.dao.CategoryDAO;
import com.alpha.practice.digimallbackend.dao.ProductDAO;
import com.alpha.practice.digimallbackend.dto.Category;
import com.alpha.practice.digimallbackend.dto.Product;

@Controller
public class PageController {

	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private ProductDAO productDAO;

	@RequestMapping(value = { "/", "/home", "/index" })
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Home");
//logger details 
		logger.info("Inside PageController index method");
		logger.debug("Inside PageController index method");

		// passing the list of categories
		mv.addObject("categories", categoryDAO.list());

		mv.addObject("userClickHome", true);
		return mv;
	}

	@RequestMapping(value = "/about")
	public ModelAndView about() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "About Us");
		mv.addObject("userClickAbout", true);
		return mv;
	}

	@RequestMapping(value = "/contact")
	public ModelAndView contact() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Contact Us");
		mv.addObject("userClickContact", true);
		return mv;
	}

	/*
	 * Methods to load all products and based on categories
	 */
	@RequestMapping(value = "/show/all/products")
	public ModelAndView showAllProducts() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "All Products");
		// passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		mv.addObject("userClickAllProduct", true);
		return mv;
	}

	@RequestMapping(value = "/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("page");
		// categoryDAO to fetch a single category
		Category category = null;

		category = categoryDAO.getid(id);

		mv.addObject("title", category.getName());
		// passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		// passing single category
		mv.addObject("category", category);
		mv.addObject("userClickCategoryProduct", true);
		return mv;
	}

	/*
	 * SINGLE PRODUCT VIEW
	 */

	@RequestMapping(value = "/show/{id}/product")
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {
		ModelAndView mv = new ModelAndView("page");
		// update view count by getting and modifying its view paramter
		Product product = productDAO.get(id);

		if (product == null)
			throw new ProductNotFoundException("Product not Available Right Now!! :-)");

		product.setViews(product.getViews() + 1);
		productDAO.update(product);
		// -----------------
		// passing necessary parameter for the product into the master page
		mv.addObject("title", product.getName());
		mv.addObject("product", product);
		mv.addObject("userClickShowProduct", true);
		return mv;
	}

	/* Login Request Mapping Function */
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ModelAndView mv = new ModelAndView("login");

		if (error != null) {
			mv.addObject("message", "Invalid Username and Password");
		}

		if (logout != null) {
			mv.addObject("logout", "User has successfully Logged out");
		}

		mv.addObject("title", "LogIn Page");
		return mv;
	}

	/* Access Denited page */
	@RequestMapping(value = "/access-denied")
	public ModelAndView accessDenied() {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("title", "403 - Access Denied Error");
		mv.addObject("errorTitle", "Idiots Are not Allowed");
		mv.addObject("errorDescription", "Not Authorized to view this page, Get lost");

		return mv;
	}

	/* Logout request mapping */
	@RequestMapping(value = "/perform-logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
//first we are going to fetch the authentication
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			// this clears the validation object and it is returned to null
			// so no user is now logged in
			// it requires those parameters
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return "redirect:/login?logout";
	}

}
