package com.alpha.practice.digimall.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.practice.digimall.model.CheckoutModel;
import com.alpha.practice.digimall.model.UserModel;
import com.alpha.practice.digimallbackend.dao.CartLineDAO;
import com.alpha.practice.digimallbackend.dao.ProductDAO;
import com.alpha.practice.digimallbackend.dao.UserDAO;
import com.alpha.practice.digimallbackend.dto.Address;
import com.alpha.practice.digimallbackend.dto.Cart;
import com.alpha.practice.digimallbackend.dto.CartLine;
import com.alpha.practice.digimallbackend.dto.OrderDetail;
import com.alpha.practice.digimallbackend.dto.OrderItem;
import com.alpha.practice.digimallbackend.dto.Product;
import com.alpha.practice.digimallbackend.dto.User;



@Component
public class CheckoutHandler {

	private static final Logger logger = LoggerFactory.getLogger(CheckoutHandler.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private CartLineDAO cartLineDAO;

	@Autowired
	private HttpSession session;

	public CheckoutModel init(String name) throws Exception {
		// this parameter comes from the spring implicit declaration in the checkout
		// flow file
		User user = userDAO.getbyEmail(name);
		CheckoutModel checkoutModel = null;
		// if user is not null
		if (user != null) {
			checkoutModel = new CheckoutModel();
			checkoutModel.setUser(user);
			checkoutModel.setCart(user.getCart());
//set total to zero
			double checkOutTotal = 0.0;
			// gets all cartlines in the cart
			List<CartLine> cartlines = cartLineDAO.listAvailable(user.getCart().getId());
			if (cartlines.size() == 0) {
				throw new Exception("there are no cartlines in this cart!");
			}
			for (CartLine cartLine : cartlines) {
				// add the total of each cartline to the grandtotal
				checkOutTotal += cartLine.getTotal();
			}
			// pass the cartlines and total to the flow
			checkoutModel.setCartlines(cartlines);
			checkoutModel.setCheckoutTotal(checkOutTotal);
		}

		return checkoutModel;
	}

	public List<Address> getShippingAddresses(CheckoutModel checkoutmodel) {
		List<Address> addresses = userDAO.listShippingAddresses(checkoutmodel.getUser());
		if (addresses.size() == 0) {
			addresses = new ArrayList<>();
		}
		addresses.add(addresses.size(), userDAO.getBillingAddress(checkoutmodel.getUser()));

		return addresses;

	}

	public String saveAddress(CheckoutModel model, Address shipping) {
		String transitionValue = "success";
		// set the user id
		// set shipping as true
		shipping.setUser(model.getUser());
		shipping.setShipping(true);
		userDAO.addAddress(shipping);

		// passing the shipping object to the flowScope
		model.setShipping(shipping);

		return transitionValue;
	}

	public String saveOrderDetails(CheckoutModel model) {
		String transitionValue = "success";
		// create new OrderDetail object
		OrderDetail orderDetail = new OrderDetail();
		// set user
		orderDetail.setUser(model.getUser());
		orderDetail.setShipping(model.getShipping());
		// fetch all billing address
		Address billingaddresses = userDAO.getBillingAddress(model.getUser());
		orderDetail.setBilling(billingaddresses);
		List<CartLine> cartlines = model.getCartlines();
		OrderItem orderitem = null;
		Product product = null;
		int orderCount = 0;
		double orderTotal = 0;
		for (CartLine cartLine : cartlines) {
			orderitem = new OrderItem();
			orderitem.setBuyingPrice(cartLine.getBuyingPrice());
			orderitem.setProduct(cartLine.getProduct());
			orderitem.setProductCount(cartLine.getProductCount());
			orderitem.setTotal(cartLine.getTotal());
			// set the orderdetail and add the orderitem total to the ordertotal
			orderitem.setOrderDetail(orderDetail);
			orderDetail.getOrderItems().add(orderitem);
			orderTotal += orderitem.getTotal();
			orderCount++;
			// update the product and reduce it's quantity
//increase it's purchases
			product = cartLine.getProduct();
			product.setQuantity(product.getQuantity() - orderitem.getProductCount());
			product.setPurchases(product.getPurchases() + orderitem.getProductCount());
			productDAO.update(product);
			cartLineDAO.delete(cartLine);
			// nb they are not deleting the cart
			// they are just removing the cartlines that's all
			// so that they have a cart to add item during new actions

		}
		orderDetail.setOrderTotal(orderTotal);
		orderDetail.setOrderCount(orderCount);
		orderDetail.setOrderDate(new Date());
		// save order
		cartLineDAO.addOrderDetail(orderDetail);
		// set to checkoutModel to be able to render it
		model.setOrderDetails(orderDetail);

		// update the cart in the database
		Cart cart = model.getCart();
		cart.setGrandTotal(cart.getGrandTotal() - orderTotal);
		cart.setCartLines(cart.getCartLines() - orderCount);
		cartLineDAO.updateCart(cart);

		// update cart in the HTTP session object
		UserModel userModel = (UserModel) session.getAttribute("userModel");
		if (userModel != null) {
			userModel.setCart(cart);
		}

		return transitionValue;
	}

	public OrderDetail getOrderDetail(CheckoutModel model) {
		return model.getOrderDetails();
	}

	public String saveAddressSelection(CheckoutModel checkoutModel, int shippingId) {

		String transitionValue = "success";

		// logger.info(String.valueOf(shippingId));
		// get shipping address
		Address shipping = userDAO.getAddress(shippingId);

		checkoutModel.setShipping(shipping);

		return transitionValue;

	}

}
