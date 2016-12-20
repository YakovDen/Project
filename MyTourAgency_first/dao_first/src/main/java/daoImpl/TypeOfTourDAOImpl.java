package daoImpl;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import agency.TypeOfTour;
import dao.ITypeOfTourDAO;
import daoImpl.base.BaseDAO;
/**
 * The class extends the standard methods of dao
 * for type of tour
 */
@Repository
public class TypeOfTourDAOImpl extends BaseDAO<TypeOfTour> implements ITypeOfTourDAO{	
	final Logger log = Logger.getLogger(TypeOfTourDAOImpl.class);
	
}
