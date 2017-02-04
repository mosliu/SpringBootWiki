package net.liuxuan.SprKi.entity.test.model;

import lombok.Data;

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
@Data
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
    @JoinColumn(name="company")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="address")
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

    public Person(int id, String firstName, String lastName, String mail, String birthDate, String salary, String companyName, String street, String townName, String townPostcode) throws ParseException {

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

    public Person(int id, String firstName, String lastName, String mail, String birthDate, String salary, int companyId, String companyName, int addressId, String street, int townId, String townName, String townPostcode) throws ParseException {

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



    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", mail=" + mail + ", address=" + address + "]";
    }

//    public PersonSolr createSolr() throws InvocationTargetException, IllegalAccessException {
//        PersonSolr personSolr = new PersonSolr();
//        BeanUtils.copyProperties(personSolr,this);
//        return personSolr;
//    }
}