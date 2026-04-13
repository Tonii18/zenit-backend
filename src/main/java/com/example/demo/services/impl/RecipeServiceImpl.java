package com.example.demo.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.models.RecipeRequestDTO;
import com.example.demo.services.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Value("${groq.api.key}")
	private String apiKey;

	@Value("${groq.api.url}")
	private String apiUrl;

	private final WebClient webClient = WebClient.create();

	@Override
	public String generateRecipe(RecipeRequestDTO recipe) {
		// TODO Auto-generated method stub
		
		String prompt = "Actúa como un nutricionista y chef de alta precisión."
				+ "Tu tarea: Crear una receta basada exclusivamente en los 4 parámetros númericos proporcionados por el"
				+ "usuario:"
				+ "1. Gramos de proteína (" + recipe.getProtein() + ")"
				+ "2. Gramos de carbohidratos (" + recipe.getCarb() + ")"
				+ "3. Gramos de fibra (" + recipe.getFiber() + ")"
				+ "4. Calorías totales (" + recipe.getCalorie() + ")"
				+ "Reglas de cumplimiento obligatorio: "
				+ "1. Cero relleno: No saludes, no des consejos nutricionales ni añadas frases como 'Aqui tienes tu receta'"
				+ "2. Precisión: Los ingredientes y cantidades deben sumar, de la forma más exacta posible los"
				+ "macronutrientes y calorías solicitados"
				+ "3. Formato estricto: Responde únicamente siguiendo esta estructura: "
				+ ""
				+ "[Título de la receta]"
				+ "Ingredientes: "
				+ "1. [Cantidad ingrediente 1] - [Ingrediente 1]"
				+ "2. [Cantidad ingrediente 2] - [Ingrediente 2]"
				+ "..."
				+ "Preparación: "
				+ "[Breve explicación de máximo 3 0 4 pasos]";

		// Build the request body

		Map<String, Object> body = Map.of(
			    "model", "llama-3.1-8b-instant",
			    "messages", List.of(
			        Map.of("role", "user", "content", prompt)
			    )
		);
		
		// Request to API
		
		try {
			Map response = webClient.post()
				    .uri(apiUrl)
				    .header("Content-Type", "application/json")
				    .header("Authorization", "Bearer " + apiKey)
				    .bodyValue(body)
				    .retrieve()
				    .bodyToMono(Map.class)
				    .block();

	        List choices = (List) response.get("choices");
	        Map choice = (Map) choices.get(0);
	        Map message = (Map) choice.get("message");
	        
	        return (String) message.get("content");
	        
	    } catch (WebClientResponseException e) {

	        System.out.println("Status: " + e.getStatusCode());
	        System.out.println("Body: " + e.getResponseBodyAsString());
	        throw new RuntimeException("Error: " + e.getResponseBodyAsString());
	    }
	}

}
