package naming;

import java.security.PublicKey;
import java.util.HashMap;

import application.HospitalProxy;
import middleware.ClientProxy;

public class NamingService 
{
	class Service
	{
		public String name;
		public String host;
		public int port;
		public PublicKey public_key;
		
		public Service(String name, String host, int port, PublicKey public_key)
		{
			this.name = name;
			this.host = host;
			this.port = port;
			this.public_key = public_key;
		}
	}
	
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
	ClientProxy LookupService(String service_name)
	{
		ClientProxy client_proxy = null;
		
		//Access Database of Services 
		Service service = this.service_database.get(service_name);
		
		//Get the correct service
		if(service_name.equals("hospital"))
			client_proxy = (ClientProxy) new HospitalProxy(service.host, service.port, service.public_key);
		
		//Return the obtained service proxy
		return client_proxy;
	}
}
