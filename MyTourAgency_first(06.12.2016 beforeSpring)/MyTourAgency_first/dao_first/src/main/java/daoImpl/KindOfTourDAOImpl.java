package daoImpl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import agency.KindOfTour;
import dao.IKindOfTourDAO;
import daoImpl.base.BaseDAO;

/**
 * The class extends the standard methods of dao
 * for info-object of tour. 
 */
@Repository
public class KindOfTourDAOImpl extends BaseDAO<KindOfTour> implements IKindOfTourDAO {
	final Logger log = Logger.getLogger(KindOfTourDAOImpl.class);

}
