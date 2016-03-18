package net.liuxuan.SprKi.entity.test.model;

import javax.persistence.*;

@Entity  //实体类
@Table(name = "TEST_Company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	public Company(){
		
	}
	public Company(Long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}