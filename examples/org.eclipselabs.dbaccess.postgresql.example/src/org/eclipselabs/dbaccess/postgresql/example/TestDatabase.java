
package org.eclipselabs.dbaccess.postgresql.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

public class TestDatabase
{
	protected void activate()
	{
		System.out.println("TestDatabase.activate()");

		Properties props = new Properties();
		props.put(DataSourceFactory.JDBC_SERVER_NAME, "localhost");
		props.put(DataSourceFactory.JDBC_DATABASE_NAME, "db");
		props.put(DataSourceFactory.JDBC_USER, "postgres");
		props.put(DataSourceFactory.JDBC_PASSWORD, "password");

		try
		{
			DataSource dataSource = dataSourceFactory.createDataSource(props);
			Connection connection = dataSource.getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			System.out.println("Driver accessed by sample Gemini DBAccess client:" + "\n\tName = " + metadata.getDriverName() + "\n\tVersion = " + metadata.getDriverVersion() + "\n\tUser = "
					+ metadata.getUserName());

			connection.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	protected void bindDataSourceFactory(DataSourceFactory dataSourceFactory)
	{
		System.out.println("TestDatabase.bindDataSourceFactory()");
		this.dataSourceFactory = dataSourceFactory;
	}

	private DataSourceFactory dataSourceFactory;
}
