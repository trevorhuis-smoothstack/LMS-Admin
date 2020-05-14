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
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.service.admin.AdminAuthorService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdminAuthorControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private AdminAuthorService adminAuthorService;
	
	@InjectMocks
	private AdminAuthorController authorController;

	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
	}
	
	@Test
	public void readAuthorTest() throws Exception, SQLException
	{
		Author author = new Author(1, "david");
		
		JSONObject item = new JSONObject();
		item.put("authorId", 1);
		item.put("authorName", "david");
		
		Mockito.when(adminAuthorService.readAuthor(1)).thenReturn(author);

		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/authors/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(item.toString()));
		
		Mockito.when(adminAuthorService.readAuthor(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/authors/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Test
	public void readAuthorsTest() throws Exception, SQLException
	{
		List<Author> authors = new ArrayList<Author>();
		authors.add( new Author(1, "david"));
		
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("authorId", 1);
		item.put("authorName", "david");
		array.add(item);
		
		Mockito.when(adminAuthorService.readAuthors()).thenReturn(authors);

		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/authors").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		Mockito.when(adminAuthorService.readAuthors()).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/authors"))
		.andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void testCreateAuthor() throws Exception
	{
		JSONObject item = new JSONObject();
		item.put("authorId", 1);
		item.put("authorName", "david");

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/author")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		JSONObject item2 = new JSONObject();
		item2.put("authorId", 1);
		item2.put("authorName", "a string that is more than forty five characters long is being put into the database");
	
		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/author")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item2.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testUpdateAuthor() throws Exception
	{
		Author author = new Author(1, "david");
		Mockito.when(adminAuthorService.readAuthor(1)).thenReturn(author);
		
		JSONObject item = new JSONObject();
		item.put("authorId", 1);
		item.put("authorName", "david");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/author")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		JSONObject item2 = new JSONObject();
		item2.put("authorId", 1);
		item2.put("authorName", "a string that is more than forty five characters long is being put into the database");
	
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/author")
				.contentType(MediaType.APPLICATION_JSON)
				.content(item2.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testDeleteAuthorTest()throws Exception
	{
		Author author = new Author(1,"david");
		Mockito.when(adminAuthorService.readAuthor(1)).thenReturn(author);

		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/authors/1"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.when(adminAuthorService.readAuthor(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/authors/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
