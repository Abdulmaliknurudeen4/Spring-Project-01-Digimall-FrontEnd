package com.alpha.practice.digimall.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handlerNoHandlerFoundException() {
		ModelAndView mv = new ModelAndView("error");

		mv.addObject("errorTitle", "The page is not Contructed!!");
		mv.addObject("errorDescription", "The page you're looking for is not available");
		mv.addObject("title", "404 page");

		return mv;
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handlerProductNotFoundException() {
		ModelAndView mv = new ModelAndView("error");

		mv.addObject("errorTitle", "Product not available");
		mv.addObject("errorDescription", "The product you're looking for is not available right now");
		mv.addObject("title", "Product Unavailable");

		return mv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Exception ex) {
		ModelAndView mv = new ModelAndView("error");

		mv.addObject("errorTitle", "Contact Your Administrator");
		
		/*
		 * Printing StackTrace for Debugging purposes only
		 * */
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		
		mv.addObject("errorDescription", sw.toString());
		mv.addObject("title", "Error");

		return mv;
	}

}