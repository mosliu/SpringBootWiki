package net.liuxuan.supportsystem.repository.test;


import net.liuxuan.supportsystem.entity.test.model.Company;
import net.liuxuan.supportsystem.entity.test.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for the <code>Person</code> domain object.
 * 
 * @author Thibault Duchateau
 */
//public interface PersonRepository extends SolrCrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {
public interface PersonRepository extends JpaRepository<Person, Long> {
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
