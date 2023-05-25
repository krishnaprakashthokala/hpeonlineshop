package org.ecommerce.web.admin.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sergio
 */
@Controller("AdminSigninController")
@RequestMapping("/admin")
public class SigninController {
	private static Logger logger = LoggerFactory.getLogger(SigninController.class);

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		logger.info("sign   Controller  : " + request);

		String referrer = request.getHeader("Referer");
		if (referrer != null && referrer.startsWith("/admin")) {
			request.getSession().setAttribute("url_prior_login", referrer);
		}
		return "admin/account/login";
	}
}
