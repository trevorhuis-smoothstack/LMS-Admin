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

import com.ss.training.lms.controller.AdminGenreController;
import com.ss.training.lms.controller.AdminPublisherController;
import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.entity.Publisher;
import com.ss.training.lms.service.admin.AdminGenreService;
import com.ss.training.lms.service.admin.AdminPublisherService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdminPublisherControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private AdminPublisherService publisherService;
	
	@InjectMocks
	private AdminPublisherController publisherController;

	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(publisherController).build();
	}
	
	@Test
	public void testReadPublisher() throws Exception
	{
		Publisher pub = new Publisher(1, "name", "address", null);
		
		JSONObject publisher = new JSONObject();
		publisher.put("publisherId", 1);
		publisher.put("publisherName", "name");
		publisher.put("address", "address");
		
		Mockito.when(publisherService.readPublisher(1)).thenReturn(pub);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/publishers/1")
											  .accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(publisher.toString()));
		

		Mockito.when(publisherService.readPublisher(1)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/publishers/1")
											  .accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testReadAllPublisher() throws Exception
	{
		List<Publisher> publishers = new ArrayList<Publisher>();
		Publisher pub = new Publisher(1, "name", "address", "phone");
		publishers.add(pub);
		
		JSONArray pubArray = new JSONArray();
		JSONObject publisher = new JSONObject();
		publisher.put("publisherId", 1);
		publisher.put("publisherName", "name");
		publisher.put("address", "address");
		publisher.put("phone", "phone");
		pubArray.add(publisher);
		
		Mockito.when(publisherService.readPublishers()).thenReturn(publishers);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/publisher")
											  .accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(pubArray.toString()));
		

		Mockito.when(publisherService.readPublishers()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/publisher")
											  .accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	public void testCreatePublisher() throws Exception
	{
		JSONObject newPublisher = new JSONObject();
		newPublisher.put("publisherId", 1);
		newPublisher.put("publisherName", "name");
		newPublisher.put("address", "address");
		newPublisher.put("phone", "phone");
		

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/publisher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newPublisher.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		JSONObject improperPublisher = new JSONObject();
		improperPublisher.put("publisherId", 1);
		improperPublisher.put("publisherName", "a string that is more than forty five characters long is being put into the database");
		improperPublisher.put("address", "address");
		improperPublisher.put("phone", "phone");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/publisher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(improperPublisher.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		JSONObject nullPublisher = new JSONObject();
		newPublisher.put("publisherId", 1);
		newPublisher.put("publisherName", null);
		newPublisher.put("address", null);
		newPublisher.put("phone", null);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/publisher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(nullPublisher.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void testUpdatePublisher() throws Exception
	{
		Publisher pub = new Publisher(1, "name", "address", "phone");
		Mockito.when(publisherService.readPublisher(1)).thenReturn(pub);
		
		JSONObject updatingPublisher = new JSONObject();
		updatingPublisher.put("publisherId", 1);
		updatingPublisher.put("publisherName", "name");
		updatingPublisher.put("address", "address");
		updatingPublisher.put("phone", "phone");
		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/publisher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatingPublisher.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		JSONObject improperPublisher = new JSONObject();
		improperPublisher.put("publisherId", 1);
		improperPublisher.put("publisherName", "a string that is more than forty five characters long is being put into the database");
		improperPublisher.put("address", "address");
		improperPublisher.put("phone", "phone");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/publisher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(improperPublisher.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		// publisher not found in the database
		Mockito.when(publisherService.readPublisher(1)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/publisher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatingPublisher.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testDeletePublisher() throws Exception
	{
		Publisher pub = new Publisher(1, "name", "address", "phone");
		Mockito.when(publisherService.readPublisher(1)).thenReturn(pub);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/publishers/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.when(publisherService.readPublisher(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/publishers/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
}

// update delete