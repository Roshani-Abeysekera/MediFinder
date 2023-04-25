package com.adl.interns.medifinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "drugs")
public class Drug implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "brand_name")
	private String drugName;

	@Column(name = "genetic_name")
	private String geneticName;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "availability")
	private boolean availability;

	@ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;

	public Drug() {
	}

	public Drug(String drugName, String geneticName, String type, boolean availability, User user) {
		super();
		this.drugName = drugName;
		this.geneticName = geneticName;
		this.type = type;
		this.availability = availability;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getGeneticName() {
		return geneticName;
	}

	public void setGeneticName(String geneticName) {
		this.geneticName = geneticName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "Drug{" +
				"id=" + id +
				", drugName='" + drugName + '\'' +
				", geneticName='" + geneticName + '\'' +
				", type='" + type + '\'' +
				", availability='" + availability + '\'' +
				", customer=" + user +
				'}';
	}
}
