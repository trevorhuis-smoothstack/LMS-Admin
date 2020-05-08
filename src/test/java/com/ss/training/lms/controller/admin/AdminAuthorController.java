package com.ss.training.lms.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.lms.service.admin.AdminAuthorService;

/**
 * @author Justin O'Brien
 */
@RestController
public class AdminAuthorController {

	@Autowired
	AdminAuthorService adminAuthorService;

}
