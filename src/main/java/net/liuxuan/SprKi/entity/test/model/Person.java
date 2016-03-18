package net.liuxuan.SprKi.entity.test.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A typical Person entity.
 * 
 * @author tduchateau
 */
@Entity  //实体类
@Table(name = "TEST_Person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String mail;
	private Date birthDate;
	private String salary;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Company company;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Address address;

	public Person() {

	}

	public Person(Long id, String firstName, String lastName, String mail, Address address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.address = address;
	}
	
	public Person(Long id, String firstName, String lastName, String mail) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
	}

	public Person(int id, String firstName, String lastName, String mail, String birthDate, String salary, String companyName, String street, String townName, String townPostcode) throws ParseException{
		
		Company company = new Company();
		company.setName(companyName);
		Town town = new Town();
		town.setName(townName);
		town.setPostcode(townPostcode);
		Address address = new Address(street);
		address.setTown(town);
		this.id = Long.valueOf(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
		this.salary = salary;
		this.company = company;
		this.address = address;
	}

	public Person(int id, String firstName, String lastName, String mail, String birthDate, String salary, int companyId, String companyName, int addressId, String street, int townId, String townName, String townPostcode) throws ParseException{
		
		Company company = new Company((long) companyId, companyName);
		Town town = new Town((long) townId, townName, townPostcode);
		Address address = new Address(street);
		address.setId((long) addressId);
		address.setTown(town);
		this.id = Long.valueOf(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.birthDate = new SimpleDateFormat("YYYY-mm-dd").parse(birthDate);
		this.salary = salary;
		this.company = company;
		this.address = address;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", mail=" + mail + ", address=" + address + "]";
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}