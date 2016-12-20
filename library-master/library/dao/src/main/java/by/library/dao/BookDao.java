package by.library.dao;

import java.util.List;

import by.library.dao.exceptions.DAOException;
import by.library.entities.Book;
import by.library.entities.BookDTO;

public interface BookDao extends Dao<Book> {
	List<BookDTO> getBookByTitle(String a, int page) throws DAOException;

}
