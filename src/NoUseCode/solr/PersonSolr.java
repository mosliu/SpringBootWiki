package net.liuxuan.SprKi.entity.test.model;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A typical Person entity.
 *
 * @author tduchateau
 */
@Data
@SolrDocument(solrCoreName = "core1")
@Table(name = "TEST_Person")
public class PersonSolr {
    @Id
    @Field
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field
    private String firstName;

    @Field
    private String lastName;

    @Field
    private String mail;

    @Field
    private Date birthDate;

    @Field
    private String salary;


    public PersonSolr() {

    }

    public PersonSolr(Long id, String firstName, String lastName, String mail, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public PersonSolr(Long id, String firstName, String lastName, String mail) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public PersonSolr(int id, String firstName, String lastName, String mail, String birthDate, String salary, String companyName, String street, String townName, String townPostcode) throws ParseException {

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
    }

    public PersonSolr(int id, String firstName, String lastName, String mail, String birthDate, String salary, int companyId, String companyName, int addressId, String street, int townId, String townName, String townPostcode) throws ParseException {

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
    }



    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", mail=" + mail +  "]";
    }


}