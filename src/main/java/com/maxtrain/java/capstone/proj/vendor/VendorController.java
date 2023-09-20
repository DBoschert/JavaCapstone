package com.maxtrain.java.capstone.proj.vendor;

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
@RequestMapping("/api/vendors")
public class VendorController {
	
	@Autowired
	private VendorRepository vendRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors() {
		Iterable<Vendor> vend = vendRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vend, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id){
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		
		Optional<Vendor> vend = vendRepo.findById(id);
		if(vend.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<Vendor>(vend.get(), HttpStatus.OK); 
		
	}

	
	@PostMapping 
	public ResponseEntity<Vendor> PostVendor(@RequestBody Vendor vend) {
	    try {
	        if (vend.getId() != 0) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        Vendor savedVendor = vendRepo.save(vend);
	        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED); 
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}") 
	public ResponseEntity PutVendor(@PathVariable int id, @RequestBody Vendor vend) {
		if(vend.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		vendRepo.save(vend);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity DeleteVendor(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		vendRepo.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
