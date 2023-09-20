package com.maxtrain.java.capstone.proj.product;

import com.maxtrain.java.capstone.proj.vendor.Vendor;

import jakarta.persistence.*;

@Entity
@Table(name="products", uniqueConstraints=@UniqueConstraint(name="UIDX_Code", columnNames={"partNbr"}))
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id = 0;
	@Column(length=30, nullable=false)
	private String partNbr = "";
	@Column(length=30, nullable=false)
	private String name = "";
	@Column(columnDefinition="decimal(11,2) not null")
	private double price = 0;
	@Column(length=30, nullable=false)
	private String unit = "";
	@Column(length=255, nullable=true)
	private String photoPath = ""; 
	@ManyToOne(optional=false)
	@JoinColumn(name="vendorId")
	private Vendor vendor;
	
	// Getter & Setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPartNbr() {
		return partNbr;
	}
	public void setPartNbr(String partNbr) {
		this.partNbr = partNbr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPhotpath() {
		return photoPath;
	}
	public void setPhotpath(String photpath) {
		this.photoPath = photpath;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	
}
