package com.ss.training.lms.controller.test;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ss.training.lms.controller.AdminGenreController;
import com.ss.training.lms.controller.AdminOverrideLoanController;
import com.ss.training.lms.entity.BookLoan;
import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.service.admin.AdminGenreService;
import com.ss.training.lms.service.admin.AdminOverrideLoanService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdminOverrideControllerTest {
private MockMvc mockMvc;
	
	@Mock
	private AdminOverrideLoanService overrideLoanService;
	
	@InjectMocks
	private AdminOverrideLoanController overrideLoanController;

	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(overrideLoanController).build();
	}
	
	@Test
	public void testAddAWeekToALoan() throws Exception
	{
		LocalDateTime dateOne = LocalDateTime.now();
		LocalDateTime dateTwo = dateOne.plusDays(7);
		Timestamp dateOut = Timestamp.valueOf(dateOne);
		Timestamp dueDate = Timestamp.valueOf(dateTwo);
		BookLoan bookLoan = new BookLoan(1,1,1, dateOut, dueDate, null);
		Mockito.when(overrideLoanService.addAWeekToALoan(bookLoan)).thenReturn(true);
		
		JSONObject updatingLoan = new JSONObject();
		updatingLoan.put("bookId",1);
		updatingLoan.put("branchId",1);
		updatingLoan.put("cardNo",1);
		updatingLoan.put("dateOut",null);
		updatingLoan.put("dueDate",null);
		updatingLoan.put("dateIn",null);

		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/bookloans/extend")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatingLoan.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
