package com.maxtrain.java.capstone.proj.product;

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
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Product>> getProducts() {
		Iterable<Product> prod = prodRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(prod, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id){
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		
		Optional<Product> prod = prodRepo.findById(id);
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<Product>(prod.get(), HttpStatus.OK); 
		
	}

	
	@PostMapping 
	public ResponseEntity<Product> PostProduct(@RequestBody Product prod) {
	    try {
	        if (prod.getId() != 0) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        Product savedProduct = prodRepo.save(prod);
	        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); 
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}") 
	public ResponseEntity PutProduct(@PathVariable int id, @RequestBody Product prod) {
		if(prod.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		prodRepo.save(prod);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity DeleteProduct(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		prodRepo.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}

