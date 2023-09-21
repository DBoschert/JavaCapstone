package com.maxtrain.java.capstone.proj.Request;

import java.util.List;

import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Integer>{
	@Query("SELECT r FROM Request r WHERE r.status = 'REVIEW' AND r.user.id <> :userId")
	List<Request> findRequestByUserId(int userId);
}
