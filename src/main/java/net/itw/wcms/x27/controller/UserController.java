package net.itw.wcms.x27.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import net.itw.wcms.x27.service.impl.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

}
