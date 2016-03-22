package net.liuxuan.SprKi.repository.test;


import net.liuxuan.SprKi.entity.test.model.PersonSolr;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Repository interface for the <code>Person</code> domain object.
 * 
 * @author Thibault Duchateau
 */
//public interface PersonRepository extends SolrCrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {
public interface PersonSolrRepository extends SolrCrudRepository<PersonSolr, Long> {
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
//    public List<Person> findByCompany(Company company);
}
