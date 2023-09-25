package com.maxtrain.java.capstone.proj.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



	@CrossOrigin
	@RestController
	@RequestMapping("/api/users")
	public class UserController {
		
		@Autowired
		private UserRepository userRepo;
		
		@GetMapping
		public ResponseEntity<Iterable<User>> getUsers() {
			Iterable<User> user = userRepo.findAll();
			return new ResponseEntity<Iterable<User>>(user, HttpStatus.OK);
		}
		
		@GetMapping("{id}")
		public ResponseEntity<User> getUser(@PathVariable int id){
			if(id <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
			}
			
			Optional<User> user = userRepo.findById(id);
			if(user.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
			}
			return new ResponseEntity<User>(user.get(), HttpStatus.OK); 
			
		}
		
		@GetMapping("{username}/{password}")
		public ResponseEntity<User> UserLogin(@PathVariable String username, @PathVariable String password){
			Optional<User> user = userRepo.findUserByUsernameAndPassword(username, password);
				if(user.isEmpty()) {
					return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
				}
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
			}


		
		@PostMapping 
		public ResponseEntity<User> PostUser(@RequestBody User user) {
		        if (user.getId() != 0) {
		            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		        }
		        User savedUser = userRepo.save(user);
		        return new ResponseEntity<>(savedUser, HttpStatus.CREATED); 
		}
		@SuppressWarnings("rawtypes")
		@PutMapping("{id}") 
		public ResponseEntity PutUser(@PathVariable int id, @RequestBody User user) {
			if(user.getId() != id) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			userRepo.save(user);
			return new ResponseEntity(HttpStatus.OK);
		}
		
		
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity DeleteUser(@PathVariable int id) {
			if(id <= 0) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			userRepo.deleteById(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
		

