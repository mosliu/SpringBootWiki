package net.liuxuan.supportsystem.entity.test.model;

import javax.persistence.*;

/**
 * A typical Town entity.
 * 
 * @author tduchateau
 */
@Entity  //实体类
@Table(name = "TEST_Town")
public class Town {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String postcode;

	public Town() {

	}

	public Town(Long id, String name, String postcode){
		this.id = id;
		this.name = name;
		this.postcode = postcode;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Town(String label) {
		this.name = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String label) {
		this.name = label;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}