package com.veterinary.demo.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "pet")
public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="animal_type")
	@Enumerated(EnumType.STRING)
	private Animals type;
	
	@Column(name = "name")
	private String name;

	@Column(name = "diagnosis")
	private String diagnosis;
	


	@Column(name = "age")
	private Long age;

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid")
	public Customer customer;

	
	public Pet() {
		super();
	}

	public Pet(Animals type, String name, String diagnosis, Customer customer) {
		super();
		this.type = type;
		this.name = name;
		this.diagnosis = diagnosis;
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Animals getType() {
		return type;
	}

	public void setType(Animals type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
