package naming;

import java.util.HashMap;

public class NamingService 
{	
	//Database of services
	HashMap<String, Service> service_database;
		
	public NamingService()
	{
		this.service_database = new HashMap<String, Service>();
	}
	
	//Method for adding new services to database
	public void AddService(String name, String host, Integer port, byte[] public_key)
	{
		Service service = new Service(name, host, port, public_key);
		this.service_database.put(name, service);
	}
	
	//Method for getting a proxy for a specific service
	public Service LookupService(String service_name)
	{		
		//Access Database of Services 
		Service service = this.service_database.get(service_name);
		
		//Return the obtained service proxy
		return service;
	}
}
