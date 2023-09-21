package com.maxtrain.java.capstone.proj.requestline;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequestLineRepository extends CrudRepository<RequestLine, Integer>{
	
	@Query("SELECT SUM(rl.quantity * p.price) FROM RequestLine rl JOIN rl.product p WHERE rl.request.id = :requestId")
	double calculateTotal(int requestId);
	
}
