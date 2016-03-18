package net.liuxuan.SprKi.repository.test;


import net.liuxuan.SprKi.entity.CMSContent;
import net.liuxuan.SprKi.entity.test.model.Company;
import net.liuxuan.SprKi.entity.test.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Repository interface for the <code>Person</code> domain object.
 * 
 * @author Thibault Duchateau
 */
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
//public interface PersonRepository {

//	/**
//	 * @return the complete list of persons stored in the repository.
//	 */
//	public List<Person> findAll();
//
//	/**
//	 * @param maxResult
//	 *            Max number of persons.
//	 * @return a limited list of persons.
//	 */
//	public List<Person> findLimited(int maxResult);
    public List<Person> findByCompany(Company company);
}
