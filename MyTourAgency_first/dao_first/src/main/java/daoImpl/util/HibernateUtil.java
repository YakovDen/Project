package daoImpl.util;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import agency.KindOfTour;
import agency.Tour;
import agency.TypeOfTour;
import agency.User;
import agency.UserTour;
import agency.UserTourPK;

public class HibernateUtil {
	
	    private static HibernateUtil util = null;
	    private static Logger log = Logger.getLogger(HibernateUtil.class);	   
	    //private SessionFactory sessionFactory = null;	   
	    private final ThreadLocal<Session> sessions = new ThreadLocal<Session>(); 	   
	    private static  SessionFactory sessionFactory=configureSessionFactory();
	    private static ServiceRegistry serviceRegistry;    
			
			
		    private static SessionFactory configureSessionFactory() throws HibernateException {
		        Configuration configuration = new Configuration();
		        configuration.configure();
		        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
		        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		        return sessionFactory;
		    }
		    public static SessionFactory getSessionFactory()
		    {
		        return sessionFactory;
		    }
		    
}
