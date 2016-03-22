package net.liuxuan.SprKi.entity.test.model;


import lombok.Data;
import javax.persistence.*;

/**
 * A typical Address entity.
 * 
 * @author tduchateau
 */
@Data
@Entity  //实体类
@Table(name = "TEST_Address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String street;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Town town;

	public Address() {

	}

	public Address(String street) {
		this.street = street;
	}

	public Address(String street, Town town) {
		this.street = street;
		this.town = town;
	}

	public Town getTown() {
		return town;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	

}