package com.ss.training.lms.controller.test;

import java.sql.SQLException;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.training.lms.controller.AdminBorrwerController;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.BookCopies;
import com.ss.training.lms.entity.Borrower;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.admin.AdminBorrowerService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdminBorrowerControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
    AdminBorrowerService borrowerService;
	
	@InjectMocks
	private AdminBorrwerController adminBorrowerController;
	
	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(adminBorrowerController).build();
	}

	@Test
	public void testReadABorrower() throws Exception
	{
		// mock data to be returned. 
			Borrower borrower = new Borrower(1,"name", "address", "phone");
			
			JSONObject item = new JSONObject();
			item.put("cardNo", 1);
			item.put("name", "name");
			item.put("address", "address");
			item.put("phone","phone");
			
			Mockito.when(borrowerService.readABorrower(1)).thenReturn(borrower);
			
			mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/borrowers/1"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().json(item.toString()));
			
			Mockito.when(borrowerService.readABorrower(1)).thenReturn(null);
			
			// test the return when the database is empty
			mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/borrowers/1"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testReadAllBorrower() throws Exception
	{
		// mock data to be returned. 
		List<Borrower> borrowers = new ArrayList<Borrower>();
		borrowers.add(new Borrower(1,"name", "address", "phone"));
		
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("cardNo", 1);
		item.put("name", "name");
		item.put("address", "address");
		item.put("phone","phone");
		array.add(item);
		
		Mockito.when(borrowerService.readAllBorrowers()).thenReturn(borrowers);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/borrowers"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		Mockito.when(borrowerService.readAllBorrowers()).thenReturn(null);
		
		// test the return when the database is empty
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/borrowers"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
