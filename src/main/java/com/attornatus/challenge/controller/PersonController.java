package com.attornatus.challenge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.challenge.service.PersonService;

import lombok.AllArgsConstructor;

@RequestMapping("/persons")
@RestController
@AllArgsConstructor
public class PersonController {
	
	private PersonService personService;
	
}
