package com.dnyanu.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnyanu.user.service.entities.User;
import com.dnyanu.user.service.services.UserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 =  userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	int retryCount = 1;
	
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
	//@Retry(name = "ratingHotelBreakerRetry", fallbackMethod = "ratingHotelFallBack")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		retryCount++;
		System.out.println("Retry count : "+retryCount);
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
	
	//rating fallback method for Circuit breaker
	public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex){
		
		//Logger.info("fallback is executed because service is down :  ", ex.getMessage());
		User user = User.builder()
					.email("dummy@gmail.com")
					.name("dummy")
					.about("This user is created dummy because some service is down")
					.userId("00000")
					.build();
				return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
}
