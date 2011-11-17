/*******************************************************************************
 * Copyright (c) 2010 Oracle.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution. 
 * The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at 
 *     http://www.opensource.org/licenses/apache2.0.php.
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *     mkeith - CLient/Server Derby JDBC support
 *     Bryan Hunt - implementation based on org.eclipse.gemini.dbaccess.derby
 *     
 * This code was copied from the org.eclipse.gemini.dbaccess.derby bundle and
 * modified.
 ******************************************************************************/

package org.eclipselabs.dbaccess.postgresql;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.xa.PGXADataSource;

/**
 * A factory for creating PostgreSQL network data sources. The properties specified
 * in the create methods determine how the created object is configured.
 * 
 * Sample code for obtaining a Derby network data source:
 * 
 * Properties props = new Properties();
 * props.put(DataSourceFactory.JDBC_SERVER_NAME, "localhost");
 * props.put(DataSourceFactory.JDBC_DATABASE_NAME, "myDB");
 * props.put(DataSourceFactory.JDBC_USER, "user");
 * props.put(DataSourceFactory.JDBC_PASSWORD, "password");
 * DataSource ds = dsf.createDataSource(props);
 * 
 * This service also supports a URL-based data source. The following 3 properties
 * can be provided instead of the 5 properties above:
 * 
 * props.put(DataSourceFactory.JDBC_URL, "jdbc:postgresql://localhost/myDB");
 * props.put(DataSourceFactory.JDBC_USER, "user");
 * props.put(DataSourceFactory.JDBC_PASSWORD, "password");
 */
public class PostgresqlDataSourceFactory extends AbstractDataSourceFactory
{

	public PostgresqlDataSourceFactory()
	{}

	/**
	 * Create a PostgreSQL DataSource object.
	 * 
	 * @param props The properties that define the DataSource implementation to
	 *          create and how the DataSource is configured.
	 * @return The configured DataSource.
	 * @throws SQLException
	 * @see org.osgi.service.jdbc.DataSourceFactory#createDataSource(java.util.Properties)
	 */
	public DataSource createDataSource(Properties props) throws SQLException
	{
		if (props == null)
			props = new Properties();

		if (props.get(PostgresqlDataSourceFactory.JDBC_URL) != null)
			return new UrlBasedDriverDataSource(props);

		DataSource dataSource = new PGSimpleDataSource();
		setDataSourceProperties(dataSource, props);
		return dataSource;
	}

	/**
	 * Create a PostgreSQL ConnectionPoolDataSource object.
	 * 
	 * @param props The properties that define the ConnectionPoolDataSource
	 *          implementation to create and how the ConnectionPoolDataSource is
	 *          configured.
	 * @return The configured ConnectionPoolDataSource.
	 * @throws SQLException
	 * @see org.osgi.service.jdbc.DataSourceFactory#createConnectionPoolDataSource(java.util.Properties)
	 */
	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException
	{
		if (props == null)
			props = new Properties();

		ConnectionPoolDataSource dataSource = new PGConnectionPoolDataSource();
		setDataSourceProperties(dataSource, props);
		return dataSource;
	}

	/**
	 * Create a PostgreSQL XADataSource object.
	 * 
	 * @param props The properties that define the XADataSource implementation
	 *          to create and how the XADataSource is configured.
	 * @return The configured XADataSource.
	 * @throws SQLException
	 * @see org.osgi.service.jdbc.DataSourceFactory#createXADataSource(java.util.Properties)
	 */
	public XADataSource createXADataSource(Properties props) throws SQLException
	{
		if (props == null)
			props = new Properties();

		XADataSource dataSource = new PGXADataSource();
		setDataSourceProperties(dataSource, props);
		return dataSource;
	}

	/**
	 * Create a new org.postgresql.Driver.
	 * 
	 * @param props The properties used to configure the Driver. Null
	 *          indicates no properties.
	 *          If the property cannot be set on the Driver being
	 *          created then a SQLException must be thrown.
	 * @return A configured org.postgresql.Driver.
	 * @throws SQLException If the org.postgresql.Driver cannot be created.
	 */
	public Driver createDriver(Properties props) throws SQLException
	{
		// Properties not used when accessing the raw driver.
		Driver driver = new org.postgresql.Driver();
		setDataSourceProperties(driver, props);
		return driver;
	}
}