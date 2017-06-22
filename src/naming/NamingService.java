package naming;

import java.security.PublicKey;
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
	public void AddService(String name, String host, int port, PublicKey public_key)
	{
		Service service = new Service(name, host, port, public_key);
		this.service_database.put(name, service);
	}
	
	//Method for getting a proxy for a specific service
	Service LookupService(String service_name)
	{		
		//Access Database of Services 
		Service service = this.service_database.get(service_name);
		
		//Return the obtained service proxy
		return service;
	}
}
