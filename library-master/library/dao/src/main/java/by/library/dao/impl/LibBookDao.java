package by.library.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import by.library.dao.BookDao;
import by.library.dao.exceptions.DAOException;
import by.library.entities.Book;
import by.library.entities.BookDTO; 


/**
 * The class extends the standard methods of dao
 * for info-object of book. There is added the method 
 * to extract a list of books that match a user's search
 */
@Repository
public class LibBookDao extends BaseDao<Book> implements BookDao {

	Logger log = Logger.getLogger(LibBookDao.class);;
	
	public LibBookDao(){
	}
	
	/**
	 * This method helps to find the books
	 *  with the title containing the search query.
	 * Native SQL, pagination and DTO transformer are used.
	 * 
	 * @param a - user's search query
	 * @param page - the resulting book list page
	 *               the user wants to see
	 * @return a list of books that match a user's search
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<BookDTO> getBookByTitle(String a, int page) throws DAOException {
		List<BookDTO> results = new ArrayList<BookDTO>();
		try{
			int pageSize = 2;
			SQLQuery sql =  getSession().createSQLQuery("SELECT b.f_title as title, b.f_writer as writer, b.f_year as year, " 
					+ "b.f_isbn as isbn, i.f_inventory_id as id "
					+ "FROM perfectlibrary.t_book b "
					+ "LEFT JOIN perfectlibrary.t_inventory i "
					+ "ON b.f_isbn = i.isbn WHERE b.f_title LIKE '%"
					+ a + "%' and i.f_state = 1 Group BY b.f_isbn");
			
			sql.addScalar("title", StandardBasicTypes.STRING);
			sql.addScalar("writer", StandardBasicTypes.STRING);
			sql.addScalar("year", StandardBasicTypes.STRING);
			sql.addScalar("isbn", StandardBasicTypes.STRING);
			sql.addScalar("id", StandardBasicTypes.INTEGER);
			sql.setCacheable(true);
			sql.setResultTransformer(Transformers.aliasToBean(BookDTO.class));
			
			if(page > 0){
				sql.setFirstResult((page - 1)*pageSize);
				sql.setMaxResults(pageSize);
			}
			results = sql.list();
		} catch (HibernateException e) {
			log.error("Error extract List of Book by HQL in Dao --- " + e);
			throw new DAOException(e);
		}
		
		return results;
	}
}
