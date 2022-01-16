package com.veterinary.demo.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.veterinary.demo.model.Customer;
import com.veterinary.demo.model.Pet;
import com.veterinary.demo.repository.CustomerRepository;
import com.veterinary.demo.repository.PetRepository;

@RestController
@RequestMapping("/rest")
public class PetRestController {
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PetRepository petRepository;
	
	
	@RequestMapping(value = "/pets/{customerid}", method = RequestMethod.GET)
	public ResponseEntity<List<Pet>> findAllPets(@PathVariable int customerid, Map<String, Object> map) throws SQLException {

		Customer customer = customerRepository.findById(customerid).get();
		List<Pet> pets = petRepository.findByCustomer(customer);
		return  ResponseEntity.ok(pets);

		
	}
	
}
