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
import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.service.admin.AdminGenreService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdminGenreControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private AdminGenreService genreService;
	
	@InjectMocks
	private AdminGenreController genreController;

	@BeforeAll
	public void setup() throws Exception
	{
		mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
	}
	
	@Test
	public void testReadGenre() throws Exception
	{
		Genre genre = new Genre(1,"genre");
		
		JSONObject item = new JSONObject();
		item.put("genreId", 1);
		item.put("genreName", "genre");
		
		Mockito.when(genreService.readGenre(1)).thenReturn(genre);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/genre/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(item.toString()));
				
		Mockito.when(genreService.readGenre(1)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/genre/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	
	@Test
	public void testReadAllGenres() throws Exception
	{
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(new Genre(1,"genre"));
		

		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("genreId", 1);
		item.put("genreName", "genre");
		array.add(item);
		
		Mockito.when(genreService.readAllGenres()).thenReturn(genres);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/genre").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(array.toString()));
		
		Mockito.when(genreService.readAllGenres()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/lms/admin/genre").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNoContent());		
	}
	
	@Test
	public void testCreateGenre() throws Exception
	{
		JSONObject newGenre = new JSONObject();
		newGenre.put("genreId",1);
		newGenre.put("genreName","genre");
		

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/genre")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newGenre.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		JSONObject improperGenre = new JSONObject();
		improperGenre.put("genreId",1);
		improperGenre.put("genreName","a string that is more than forty five characters long is being put into the database");
		

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/genre")
				.contentType(MediaType.APPLICATION_JSON)
				.content(improperGenre.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		JSONObject nullGenreName = new JSONObject();
		nullGenreName.put("genreId",null);
		nullGenreName.put("genreName",null);
		

		mockMvc.perform(MockMvcRequestBuilders.post("/lms/admin/genre")
				.contentType(MediaType.APPLICATION_JSON)
				.content(nullGenreName.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void testUpdateGenre() throws Exception
	{
		Genre genre = new Genre(1,"genre");
		Mockito.when(genreService.readGenre(1)).thenReturn(genre);
		
		JSONObject updatingGenre = new JSONObject();
		updatingGenre.put("genreId",1);
		updatingGenre.put("genreName","genre");

		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/genre")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatingGenre.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		JSONObject improperGenre = new JSONObject();
		improperGenre.put("genreId",1);
		improperGenre.put("genreName","a string that is more than forty five characters long is being put into the database");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/genre")
				.contentType(MediaType.APPLICATION_JSON)
				.content(improperGenre.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		Mockito.when(genreService.readGenre(1)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/lms/admin/genre")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatingGenre.toJSONString()))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void testDeleteGenre() throws Exception
	{
		Genre genre = new Genre(1,"genre");
		Mockito.when(genreService.readGenre(1)).thenReturn(genre);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/genre/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.when(genreService.readGenre(1)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.delete("/lms/admin/genre/1"))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
