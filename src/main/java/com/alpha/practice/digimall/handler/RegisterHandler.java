package com.alpha.practice.digimall.handler;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.alpha.practice.digimall.model.RegisterModel;
import com.alpha.practice.digimallbackend.dao.UserDAO;
import com.alpha.practice.digimallbackend.dto.Address;
import com.alpha.practice.digimallbackend.dto.Cart;
import com.alpha.practice.digimallbackend.dto.User;

@Component
public class RegisterHandler implements Serializable {

	@Autowired
	private UserDAO userdao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * This is a bean so it will be automatically detected by the Spring web flow so
	 * there's no need to declare it in the signup-flow.xml
	 */
	private static final long serialVersionUID = 1L;

	public RegisterModel init() {

		return new RegisterModel();
	}

	public void addUser(RegisterModel model, User user) {
		model.setUser(user);

	}

	public void addAddress(RegisterModel model, Address billing) {
		model.setBilling(billing);
	}

	public String saveAll(RegisterModel model) {
		String transitionValue = "success";
		User user = model.getUser();
		if (user.getRole().equals("USER")) {
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}
		//encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// save user

		userdao.addUser(user);

		// save address
		Address billing = model.getBilling();
		billing.setUser(user);
		billing.setBilling(true);
		// save address
		userdao.addAddress(billing);
		return transitionValue;
	}

	/*
	 * custome validation for custom password field for email id uniqueness
	 */
	public String validateUser(User user, MessageContext error) {
		String transitionValue = "success";
//checking for password
		if (!(user.getPassword().equals(user.getConfirmPassword()))) {
			transitionValue = "failure";
			error.addMessage(new MessageBuilder().error().source("confirmPassword")
					.defaultText("Password doesn't match, Please reenter").build());
		}

		if (userdao.getbyEmail(user.getEmail()) != null) {
			transitionValue = "failure";
			error.addMessage(new MessageBuilder().error().source("email")
					.defaultText("Email adddress is already taken, please login or use another email.").build());

		}

		return transitionValue;

	}
}
