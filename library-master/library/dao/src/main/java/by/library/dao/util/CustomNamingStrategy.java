package by.library.dao.util;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * Class for automatic naming of tables and fields 
 * based on class names and their property
 */

public class CustomNamingStrategy extends DefaultNamingStrategy {

	private static final long serialVersionUID = 1L;

	public String classToTableName(String className) {
        return "T_" + super.classToTableName(className).toUpperCase();
    }

    public String propertyToColumnName(String propName) {
        return "F_" + super.propertyToColumnName(propName);
    }

    public String columnName(String columnName) {
        return columnName;
    }

    public String tableName(String tableName) {
        return tableName;
    }
}


