package com.maxtrain.java.capstone.proj.requestline;


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

import com.maxtrain.java.capstone.proj.Request.Request;
import com.maxtrain.java.capstone.proj.Request.RequestRepository;

import jakarta.transaction.Transactional;

	
	@CrossOrigin
	@RestController
	@RequestMapping("/api/requestlines")
	public class RequestLineController {
		
		@Autowired
		private RequestLineRepository rlRepo;
		@Autowired
		private RequestRepository requestRepo;
		
		/*
		
		//  PRIVATE RecalculateRequestTotal(requestId)
		// recalculate the total in request everytime PUT, POST, or DELETE is called
		private void RecalculateRequestTotal(@PathVariable int requestId){
			RequestLine total = rlRepo.RecalculateTotal(requestId);
			();
			
		}
		*/
		
		
		@Transactional
	    private void recalculateRequestTotal(int requestId) {
	        double total = rlRepo.calculateTotal(requestId);
	        Optional<Request> optionalRequest = requestRepo.findById(requestId);
	        
	        if (optionalRequest.isPresent()) {
	            Request request = optionalRequest.get();
	            request.setTotal(total);
	            requestRepo.save(request);
	        }
	    }
		
		 
		
		
		@GetMapping
		public ResponseEntity<Iterable<RequestLine>> getRequestLines() {
			Iterable<RequestLine> rl = rlRepo.findAll();
			return new ResponseEntity<Iterable<RequestLine>>(rl, HttpStatus.OK);
		}
		
		@GetMapping("{id}")
		public ResponseEntity<RequestLine> getRequestLine(@PathVariable int id){
			if(id <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
			}
			
			Optional<RequestLine> rl = rlRepo.findById(id);
			if(rl.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
			}
			return new ResponseEntity<RequestLine>(rl.get(), HttpStatus.OK); 
			
		}

		
		@PostMapping 
		public ResponseEntity<RequestLine> PostRequestLine(@RequestBody RequestLine rl) {
		    try {
		        if (rl.getId() != 0) {
		            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		        }
		        
		        RequestLine savedRequestLine = rlRepo.save(rl);
		        int requestId = savedRequestLine.getRequest().getId();
		        recalculateRequestTotal(requestId);
		        return new ResponseEntity<>(savedRequestLine, HttpStatus.CREATED); 
		    } catch (Exception e) {
		        e.printStackTrace(); 
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
		
		
		@SuppressWarnings("rawtypes")
		@PutMapping("{id}") 
		public ResponseEntity PutRequestLine(@PathVariable int id, @RequestBody RequestLine rl) {
			if(rl.getId() != id) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			int requestId = rl.getRequest().getId();
			rlRepo.save(rl);
			recalculateRequestTotal(requestId);
			
			return new ResponseEntity(HttpStatus.OK);
		}
		
		
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity DeleteRequestLine(@PathVariable int id) {
			if(id <= 0) {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
			
			rlRepo.deleteById(id);
			recalculateRequestTotal(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
