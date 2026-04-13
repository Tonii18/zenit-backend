package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.RecipeRequestDTO;
import com.example.demo.services.RecipeService;

@RestController
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@PostMapping("/recipe/request")
	public ResponseEntity<String> requestRecipe(@RequestBody RecipeRequestDTO request) {
		try {
			String recipe = recipeService.generateRecipe(request);
			return ResponseEntity.ok(recipe);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body("An error while generating recipe has occurred: " + e.getMessage());
		}
	}

}
