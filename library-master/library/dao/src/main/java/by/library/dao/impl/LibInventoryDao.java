package by.library.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.library.dao.InventoryDao;
import by.library.entities.Inventory;


/**
 * The class extends the standard methods of dao
 * for physical object of book with unique inventory number
 */
@Repository
public class LibInventoryDao extends BaseDao<Inventory> 
						implements InventoryDao {
	Logger log = Logger.getLogger(LibInventoryDao.class);

	
    public LibInventoryDao(){
    }

}
