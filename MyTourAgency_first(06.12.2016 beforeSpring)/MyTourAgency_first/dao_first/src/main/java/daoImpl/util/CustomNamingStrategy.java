package daoImpl.util;

import org.hibernate.cfg.DefaultNamingStrategy;
/**
 * класс с набором правил отображения столбцов и таблиц
 * @author Den
 *
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {	
	private static final long serialVersionUID = 1L;

	public String classToTableName(String className) {
        return super.classToTableName(className).toUpperCase();
    }

    public String propertyToColumnName(String propName) {
        return super.propertyToColumnName(propName);
    }

    public String columnName(String columnName) {
        return columnName;
    }

    public String tableName(String tableName) {
        return tableName;
    }
	
}
