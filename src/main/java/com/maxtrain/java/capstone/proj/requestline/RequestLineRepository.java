package com.maxtrain.java.capstone.proj.requestline;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequestLineRepository extends CrudRepository<RequestLine, Integer>{
	/*
	@Query("SELECT SUM(rl.Quantity * p.Price) AS LineTotal\r\n"
			+ "FROM RequestLines AS rl\r\n"
			+ "JOIN Products AS p ON rl.ProductId = p.Id\r\n"
			+ "WHERE rl.userId = userId;\r\n"
			+ "")
	List<RequestLine> RecalculateTotal(int requestId);
	*/
}
