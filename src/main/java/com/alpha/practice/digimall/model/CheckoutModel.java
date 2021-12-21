package com.alpha.practice.digimall.model;

import java.io.Serializable;
import java.util.List;

import com.alpha.practice.digimallbackend.dto.Address;
import com.alpha.practice.digimallbackend.dto.Cart;
import com.alpha.practice.digimallbackend.dto.CartLine;
import com.alpha.practice.digimallbackend.dto.OrderDetail;
import com.alpha.practice.digimallbackend.dto.User;

public class CheckoutModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private Address shipping;
	private Cart cart;
	private List<CartLine> cartlines;
	private OrderDetail orderDetails;
	private double checkoutTotal;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getShipping() {
		return shipping;
	}
	public void setShipping(Address shipping) {
		this.shipping = shipping;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public List<CartLine> getCartlines() {
		return cartlines;
	}
	public void setCartlines(List<CartLine> cartlines) {
		this.cartlines = cartlines;
	}
	public OrderDetail getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderDetail orderDetails) {
		this.orderDetails = orderDetails;
	}
	public double getCheckoutTotal() {
		return checkoutTotal;
	}
	public void setCheckoutTotal(double checkoutTotal) {
		this.checkoutTotal = checkoutTotal;
	}
	

}
