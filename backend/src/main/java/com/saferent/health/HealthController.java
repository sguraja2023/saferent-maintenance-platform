package com.saferent.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class HealthController {

	@GetMapping
	public HealthResponse getHealth() {
		return new HealthResponse("UP", "SafeRent Backend");
	}

	public record HealthResponse(String status, String service) {
	}
}
