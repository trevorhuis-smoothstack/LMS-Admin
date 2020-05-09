package com.ss.training.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.lms.service.admin.AdminPublisherService;

/**
 * @author Justin O'Brien
 */
@RestController
public class AdminPublisherController {

	@Autowired
	AdminPublisherService service;

}
