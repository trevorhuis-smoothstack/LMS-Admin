package com.ss.training.lms.controller.test;

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

import com.ss.training.lms.controller.AdminBorrowerController;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Borrower;
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
	private AdminBorrowerController adminBorrowerController;
	
	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(adminBorrowerController).build();
	}

	@Test
	public void testReadBorrower() throws Exception
	{
		// mock data to be returned. 
			Borrower borrower = new Borrower(1,"name", "address", "phone");
			
			JSONObject item = new JSONObject();
			item.put("cardNo", 1);
			item.put("name", "name");
			item.put("address", "address");
			item.put("phone","phone");
			
			Mockito.when(borrowerService.readBorrower(1)).thenReturn(borrower);
			
			mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/borrowers/1").accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().json(item.toString()));
			
			Mockito.when(borrowerService.readBorrower(1)).thenReturn(null);
			
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
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/borrowers").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		Mockito.when(borrowerService.readAllBorrowers()).thenReturn(null);
		
		// test the return when the database is empty
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/borrowers"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void testAddABorrower() throws Exception
	{
		JSONObject item = new JSONObject();
		item.put("cardNo", 1);
		item.put("name", "david");
		item.put("address", "address");
		item.put("phone", "phone");

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/borrowers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		JSONObject item2 = new JSONObject();
		item2.put("cardNo", 1);
		item2.put("name", "a string that is more than forty five characters long is being put into the database");
		item2.put("address", "address");
		item2.put("phone", "phone");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/borrowers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item2.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testUpdateBorrower() throws Exception
	{
		Borrower borrower = new Borrower(1, "david", "address", "phone");
		Mockito.when(borrowerService.readBorrower(1)).thenReturn(borrower);
		
		JSONObject item = new JSONObject();
		item.put("cardNo", 1);
		item.put("name", "david");
		item.put("address", "address");
		item.put("phone", "phone");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/borrowers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isAccepted());
		
		JSONObject item2 = new JSONObject();
		item2.put("cardNo", 1);
		item2.put("name", "a string that is more than forty five characters long is being put into the database");
		item2.put("address", "address");
		item2.put("phone", "phone");
	
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/borrowers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item2.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		Mockito.when(borrowerService.readBorrower(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/borrowers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	public void testDeleteBorrower() throws Exception
	{
		Borrower borrower = new Borrower(1,"david","address","phone");
		Mockito.when(borrowerService.readBorrower(1)).thenReturn(borrower);

		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/borrowers/1"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.when(borrowerService.readBorrower(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/authors/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
