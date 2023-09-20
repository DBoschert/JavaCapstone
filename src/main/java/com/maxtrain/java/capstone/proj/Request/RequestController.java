package com.maxtrain.java.capstone.proj.Request;

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
@RequestMapping("/api/requests")
public class RequestController {
	
	@Autowired
	private RequestRepository reqRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> getRequests() {
		Iterable<Request> req = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(req, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> getRequest(@PathVariable int id){
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		
		Optional<Request> req = reqRepo.findById(id);
		if(req.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<Request>(req.get(), HttpStatus.OK); 
		
	}
	
	

	
	@PostMapping 
	public ResponseEntity<Request> PostRequest(@RequestBody Request req) {
	    try {
	        if (req.getId() != 0) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        Request savedRequest = reqRepo.save(req);
	        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED); 
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}") 
	public ResponseEntity PutRequest(@PathVariable int id, @RequestBody Request req) {
		if(req.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		reqRepo.save(req);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity DeleteRequest(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		reqRepo.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}