package com.veterinary.demo.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.veterinary.demo.model.Animals;
import com.veterinary.demo.model.Customer;
import com.veterinary.demo.model.Pet;
import com.veterinary.demo.repository.CustomerRepository;
import com.veterinary.demo.repository.PetRepository;
import com.veterinary.demo.service.impl.CustomerServiceImp;
import com.veterinary.demo.service.impl.PetServiceImp;
import com.veterinary.demo.util.ApiPaths;

@Controller
@RequestMapping(ApiPaths.PetBasicCtrl.CTRL)
public class PetController {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerServiceImp customerServiceImp;
	@Autowired
	PetServiceImp petServiceImp;
	@Autowired
	PetRepository petRepository;

	@RequestMapping(value = "/show-pets/{customerid}", method = RequestMethod.GET)
	public String PetsShowPanel(@PathVariable int customerid, Map<String, Object> map) throws SQLException {

		Optional<Customer> customer = customerServiceImp.findById(customerid);
		if(customer.isPresent()) {
			
			List<Pet> pets = petServiceImp.findByCustomer(customer.get());
			map.put("title", "Müşteri Ait Hayvanlar");
			map.put("customer", customer.get());
			map.put("pet", new Pet());
			map.put("pets", pets);
			return "pet/pets";
		}else {			
			List<Customer> customers = customerServiceImp.findAll();
			map.put("title", "Müşteriler");
			map.put("customers", customers);
			map.put("message", " Kayıt bulunamamıştır.");
			return "customer/customers";
		}
	}
	@RequestMapping(value = "/show-pets-by-name/{customerid}", method = RequestMethod.GET)
	public String PetsShowPanel2(@RequestParam("name") String name,@PathVariable int customerid, Map<String, Object> map) throws SQLException {

		List<Pet> pets=new ArrayList<>();
		Optional<Customer> customer = customerServiceImp.findById(customerid);
		if(customer.isPresent()) {
			List<Pet> pets2 = petServiceImp.findByCustomer(customer.get());
			pets2.stream().forEach(item-> {
				if(item.getName().equals(name))	{
					item=item;
					System.out.println(item.getId()+" : "+item.getName()+" "+item.getDiagnosis());
					pets.add(item);
				}
			});
			if(pets.size()>0) {
				map.put("pets", pets);
				map.put("message","Kayıtlar bulunmuştur");
			}
			else {
				map.put("message", name+" isme ait bir hayvan bulunamamıştır.");
				pets2 = petServiceImp.findByCustomer(customer.get());
				map.put("pets", pets2);
			}
			map.put("title", "Müşteri Ait Hayvanlar");
			map.put("customer", customer.get());
			return "pet/pets";
		}else {
			List<Customer> customers = customerServiceImp.findAll();
			map.put("title", "Müşteriler");
			map.put("customers", customers);
			map.put("message", "Bu isimde bir kayıt yoktur. id : " + customerid);
			return "customer/customers";
		}
		
	}	

	
	@RequestMapping(value = "/insert-pet/{customerid}", method = RequestMethod.GET)
	public String InsertPetPanel(@PathVariable int customerid, Map<String, Object> map,
			 @ModelAttribute("pet") Pet pet, BindingResult result, Model model)  throws SQLException {

		Optional<Customer> customer = customerServiceImp.findById(customerid);
		if(customer.isPresent()) {
			List<Pet> pets = petServiceImp.findByCustomer(customer.get());
			map.put("title", "Müşteri Detayları");
			map.put("customer", customer.get());
			map.put("pet", new Pet());
			map.put("pets", pets);
			map.put("types", (Arrays.asList(Animals.values())));

			return "pet/pet-insert-panel";
		}else {
			List<Customer> customers = customerServiceImp.findAll();
			map.put("title", "Müşteriler");
			map.put("customers", customers);
			map.put("message", " Kayıt bulunamamıştır.");
			return "customer/customers";
		}
		
	}
	@RequestMapping(value = "/insert-pet/{customerid}", method = RequestMethod.POST)
	public String InsertPet(@PathVariable int customerid, Map<String, Object> map,
			@ModelAttribute("pet") Pet pet, BindingResult result, Model model)  throws SQLException {
		
		if (!pet.getName().equals("") || !pet.getDiagnosis().equals("") ) {
			Optional<Customer> customer = customerServiceImp.findById(customerid);
			if(customer.isPresent()) {
				if (result.hasErrors()) {
					map.put("message", " Bir problem oluştu.");				

				} else {
					pet.setCustomer(customer.get());
					petRepository.save(pet);
					map.put("message", "Kayıt işlemi başarılı");
				}
				List<Pet> pets = petServiceImp.findByCustomer(customer.get());
				map.put("customer", customer.get());
				map.put("pet", new Pet());
				map.put("pets", pets);
				map.put("title", "Müşteri Ait Hayvanlar");
				return "pet/pets";
			}else {
				List<Customer> customers = customerRepository.findAll();
				map.put("title", "Müşteriler");
				map.put("customers", customers);
				map.put("message", " Kayıt bulunamamıştır.");
				return "customer/customers";
			}
		} else {
			Optional<Customer> customer = customerServiceImp.findById(customerid);
			if(customer.isPresent()) {
				List<Pet> pets = petServiceImp.findByCustomer(customer.get());
				map.put("customer", customer.get());
				map.put("pet", new Pet());
				map.put("message", "Boş alanları doldurunuz");
				map.put("pets", pets);

				return "customer/show-customer";
			} else {
				List<Customer> customers = customerRepository.findAll();
				map.put("title", "Müşteriler");
				map.put("customers", customers);
				map.put("message", "Boş alanları doldurunuz");
				return "customer/customers";
			}
		}
	}
	
	
	@RequestMapping(value = "/delete-pet/{pet_id}", method = RequestMethod.GET)
	public String DeletePet(@PathVariable long pet_id, Map<String, Object> map,  @ModelAttribute("pet") Pet pet,
			BindingResult result, Model model) throws SQLException {

		Optional<Pet> selected_pet = petServiceImp.findById(pet_id);
		if(selected_pet.isPresent()) {
			int customerid = selected_pet.get().getCustomer().getCustomerid();
			selected_pet.get().setCustomer(null);
			Boolean control=petServiceImp.delete(selected_pet.get());
			if(control==true) {
				map.put("message","Kayıt başarıyla silinmiştir.");
			}else {
				map.put("message","Bir hata oluştu.");
			}
			Customer customer = customerRepository.findById(customerid).get();
			
			List<Pet> pets = petRepository.findByCustomer(customer);
			map.put("title", "Müşteri Ait Hayvanlar");
			map.put("customer", customer);
			map.put("pet", new Pet());
			map.put("pets", pets);
			return "pet/pets";
		}else {
			List<Customer> customers = customerRepository.findAll();
			map.put("title", "Müşteriler");
			map.put("customers", customers);
			map.put("message","Pet Kaydı bulunamamıştır.");
			return "customer/customers";
		}


	}

}
