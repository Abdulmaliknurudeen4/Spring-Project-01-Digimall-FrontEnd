package com.alpha.practice.digimall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alpha.practice.digimall.model.UserModel;
import com.alpha.practice.digimallbackend.dao.UserDAO;
import com.alpha.practice.digimallbackend.dto.User;

@ControllerAdvice
public class GlobalController {
	@Autowired
	private HttpSession session;

	@Autowired
	private UserDAO userdao;

	private UserModel userModel = null;

	@ModelAttribute("userModel")
	public UserModel getUserModel() {
		if (session.getAttribute("userModel") == null) {
			// add user model to session
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			User user = userdao.getbyEmail(authentication.getName());
			if (user != null) {
				// create a new user model object to pass object details to the session
				userModel = new UserModel();
				userModel.setId(user.getId());
				userModel.setEmail(user.getEmail());
				userModel.setRole(user.getRole());
				userModel.setFullName(user.getFirstname() + " " + user.getLastname());
				if (userModel.getRole().equals("USER")) {
					// if user is a buyer
					userModel.setCart(user.getCart());
				}
				// set to seesion
				session.setAttribute("userModel", userModel);
				return userModel;
			}
		}

		return (UserModel) session.getAttribute("userModel");
	}

}
