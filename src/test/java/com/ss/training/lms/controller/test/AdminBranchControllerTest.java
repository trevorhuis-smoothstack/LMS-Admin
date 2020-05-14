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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ss.training.lms.controller.AdminAuthorController;
import com.ss.training.lms.controller.AdminBranchController;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.LibraryBranch;
import com.ss.training.lms.service.admin.AdminBranchService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdminBranchControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private AdminBranchService branchService;
	
	@InjectMocks
	private AdminBranchController branchController;

	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
	}
	
	@Test
	public void testReadBranch() throws Exception
	{
		LibraryBranch branch = new LibraryBranch(1,"branch","address");
		
		JSONObject item = new JSONObject();
		item.put("branchId", 1);
		item.put("branchName", "branch");
		item.put("branchAddress", "address");
		
		Mockito.when(branchService.readBranch(1)).thenReturn(branch);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/branches/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(item.toString()));
		
		Mockito.when(branchService.readBranch(1)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/branches/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testReadAll() throws Exception
	{
		List<LibraryBranch> branches = new ArrayList<LibraryBranch>();
		LibraryBranch branch = new LibraryBranch(1,"branch","address");
		branches.add(branch);
		
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("branchId", 1);
		item.put("branchName", "branch");
		item.put("branchAddress", "address");
		array.add(item);
		
		Mockito.when(branchService.readBranches()).thenReturn(branches);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/branches").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		Mockito.when(branchService.readBranches()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/branches").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	
	@Test
	public void testCreateBanch() throws Exception
	{
		JSONObject newBranch = new JSONObject();
		newBranch.put("branchId",1);
		newBranch.put("branchName","branch");
		newBranch.put("branchAddress","address");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/branch")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newBranch.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		JSONObject improperBranch = new JSONObject();
		improperBranch.put("branchId",1);
		improperBranch.put("branchName","a string that is more than forty five characters long is being put into the database");
		improperBranch.put("branchAddress","address");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/branch")
				.contentType(MediaType.APPLICATION_JSON)
				.content(improperBranch.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testUpdateBranch() throws Exception
	{
		LibraryBranch branch = new LibraryBranch(1,"branch","address");
		Mockito.when(branchService.readBranch(1)).thenReturn(branch);
		
		JSONObject updatingBranch = new JSONObject();
		updatingBranch.put("branchId",1);
		updatingBranch.put("branchName","branch");
		updatingBranch.put("branchAddress","address");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/branch")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatingBranch.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		JSONObject improperBranch = new JSONObject();
		improperBranch.put("branchId",1);
		improperBranch.put("branchName","a string that is more than forty five characters long is being put into the database");
		improperBranch.put("branchAddress","address");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/branch")
				.contentType(MediaType.APPLICATION_JSON)
				.content(improperBranch.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		Mockito.when(branchService.readBranch(1)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/branch")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatingBranch.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		
	}
	
	@Test
	public void testDeleteBranch() throws Exception
	{
		LibraryBranch branch = new LibraryBranch(1,"branch","address");
		Mockito.when(branchService.readBranch(1)).thenReturn(branch);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/branches/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.when(branchService.readBranch(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/branches/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());


	}
}