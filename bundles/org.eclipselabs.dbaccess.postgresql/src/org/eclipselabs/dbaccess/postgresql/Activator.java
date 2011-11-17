
package org.eclipselabs.dbaccess.postgresql;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

public class Activator implements BundleActivator
{

	private static BundleContext context;

	static BundleContext getContext()
	{
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception
	{
		Activator.context = bundleContext;
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, DataSourceFactoryConstants.POSTGRESQL_DRIVER_NAME);
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, DataSourceFactoryConstants.JDBC_4_DRIVER_VERSION);
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, DataSourceFactoryConstants.POSTGRESQL_DRIVER_CLASS);
		serviceRegistration = context.registerService(DataSourceFactory.class.getName(), new PostgresqlDataSourceFactory(), props);
	}

	public void stop(BundleContext bundleContext) throws Exception
	{
		Activator.context = null;

		if (serviceRegistration != null)
			serviceRegistration.unregister();
	}

	private ServiceRegistration serviceRegistration;
}
