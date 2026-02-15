package com.hrs.notificationservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(produces = "application/json", value = "Operations pertaining to manage notifications in hotel reservation system")
@RestController
@RequestMapping("/api")
public class NotificationController {

	@GetMapping("/welcome")
	private ResponseEntity<String> displayWelcomeMessage() {
		return new ResponseEntity<>("Welcome to notification service !!", HttpStatus.OK);
	}

}
