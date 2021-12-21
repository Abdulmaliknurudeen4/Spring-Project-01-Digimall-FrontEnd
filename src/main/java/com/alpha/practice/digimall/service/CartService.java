package com.alpha.practice.digimall.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.practice.digimall.model.UserModel;
import com.alpha.practice.digimallbackend.dao.CartLineDAO;
import com.alpha.practice.digimallbackend.dao.ProductDAO;
import com.alpha.practice.digimallbackend.dto.Cart;
import com.alpha.practice.digimallbackend.dto.CartLine;
import com.alpha.practice.digimallbackend.dto.Product;

@Service
public class CartService {

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private HttpSession session;

	@Autowired
	private ProductDAO productDAO;

	// return user cart
	private Cart getCart() {

		return ((UserModel) session.getAttribute("userModel")).getCart();
	}

	// return entire cartlines
	public List<CartLine> getCartLines() {
		return cartLineDAO.list(this.getCart().getId());
	}

	public String manageCartLine(int cartLineId, int count) {
		// fetch cartline
		CartLine cartLine = cartLineDAO.get(cartLineId);
		if (cartLine == null) {
			return "result=error";
		} else {

			Product product = cartLine.getProduct();
			double oldTotal = cartLine.getTotal();
			// checking if the product quantity is available
			if (product.getQuantity() < count) {
				return "result=unavailable";
			}

			cartLine.setProductCount(count);
			cartLine.setBuyingPrice(product.getUnitPrice());

			cartLine.setTotal(product.getUnitPrice() * count);

			cartLineDAO.update(cartLine);

			Cart cart = this.getCart();
			// the oldtotal of the cartLine taken away from the cart and the new total added
			// gives final result
			cart.setGrandTotal(cart.getGrandTotal() - oldTotal + cartLine.getTotal());
			cartLineDAO.updateCart(cart);
			return "result=updated";
		}
	}

	public String deleteCartLine(int cartLineId) {
		// fetch cartLIne
		CartLine cartLine = cartLineDAO.get(cartLineId);
		if (cartLine == null) {
			return "result=error";
		} else {
			// update cart
			Cart cart = this.getCart();
			cart.setGrandTotal(cart.getGrandTotal() - cartLine.getTotal());
			cart.setCartLines(cart.getCartLines() - 1);
			cartLineDAO.updateCart(cart);
			// remove the cartLine
			cartLineDAO.delete(cartLine);
			return "result=deleted";

		}
	}

	public String addCartLine(int productId) {
		String response = null;

		Cart cart = this.getCart();
		// check if available
		CartLine cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), productId);
		if (cartLine == null) {

			cartLine = new CartLine();

			Product product = productDAO.get(productId);
			//
			cartLine.setCartId(cart.getId());
			cartLine.setProduct(product);
			cartLine.setBuyingPrice(product.getUnitPrice());
			cartLine.setProductCount(1);
			cartLine.setTotal(product.getUnitPrice());
			cartLine.setAvailable(true);
			// persist
			cartLineDAO.add(cartLine);
			cart.setCartLines(cart.getCartLines() + 1);
			cart.setGrandTotal(cart.getGrandTotal() + cartLine.getTotal());
			cartLineDAO.updateCart(cart);
			response = "result=added";
		} else {

			// if cartLine has reached maximum count
			if (cartLine.getProductCount() < 3) {
				// update product count in cartLIne'=
				response = this.manageCartLine(cartLine.getId(), cartLine.getProductCount() + 1);
			} else {
				response = "result=maximum";
			}

		}

		return response;
	}

	public String validateCartLine() {
		Cart cart = this.getCart();
		List<CartLine> cartlines = cartLineDAO.listAvailable(cart.getId());
		double grandTotal = 0.0;
		int lineCount = 0;
		String response = "response=success";
		boolean changed = false;
		Product product = null;

		for (CartLine cartLine : cartlines) {
			product = cartLine.getProduct();
			// checks
			// check if the product is active or not
			// if it is not active make the availability of cartLine as false
			// if the product is not active and the quantity of it is zero and the cartLine
			// is available despite the rubbish
			// set the cartline avaliablity to false
			if ((!product.isIsactive() == true && product.getQuantity() == 0) && cartLine.isAvailable()) {
				cartLine.setAvailable(false);
				changed = true;
			}
			/*
			 * If the product is active and the quantity of the product is active but the
			 * cartline is not avalaible set the cartline to true
			 */
			if ((product.isIsactive() && product.getQuantity() > 0) && !cartLine.isAvailable()) {
				cartLine.setAvailable(true);
				changed = true;
			}
			/*
			 * check if the buying price of the prouduct has changed
			 */
			if (cartLine.getBuyingPrice() != product.getUnitPrice()) {
				// change the price of the cartline
				cartLine.setBuyingPrice(product.getUnitPrice());
				cartLine.setTotal(product.getUnitPrice() * cartLine.getProductCount());
				changed = true;
			}
			//if the count in the cartLine is not up to the product due to someone buying and the quantity reduces
			if (cartLine.getProductCount() > product.getQuantity()) {
				cartLine.setProductCount(product.getQuantity());
				cartLine.setTotal(product.getQuantity() * cartLine.getProductCount());

			}
			// changes has happened

			if (changed) {
				// update the cartLine
				cartLineDAO.update(cartLine);
				// return
				return "result=modified";
			}
			//get all the cartline total and add it to the grand total
			grandTotal += cartLine.getTotal();
			//update the number of cartLines in the cart
			lineCount++;
		}
		cart.setCartLines(lineCount);
		cart.setGrandTotal(grandTotal);
		cartLineDAO.updateCart(cart);

		return response;
	}

}
