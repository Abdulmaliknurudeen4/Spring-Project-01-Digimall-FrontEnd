package com.alpha.practice.digimall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alpha.practice.digimallbackend.dao.ProductDAO;
import com.alpha.practice.digimallbackend.dto.Product;

@Controller
@RequestMapping("/json/data")
public class JsonDataController {

	@Autowired
	private ProductDAO productDAO;

	/*
	 * ALL PRODUCT
	 */
	@RequestMapping("/all/products")
	@ResponseBody
	public List<Product> getAllProducts() {

		return productDAO.listActiveProducts();
	}

	/*
	 * ALL PRODUCT BY CATEGORY
	 */
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getProductsByCategory(@PathVariable("id") int id) {

		return productDAO.listActiveProductsByCategory(id);
	}

	/*
	 * ALL PRODUCT for admin
	 */
	@RequestMapping("admin/all/products")
	@ResponseBody
	public List<Product> getAllProductsForAdmin() {

		return productDAO.list();
	}

}
