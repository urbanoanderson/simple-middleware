package naming;

import extra.Constant;
import middleware.ClientProxy;

public class NamingServiceProxy extends ClientProxy
{
	public NamingServiceProxy()
	{
		super(Constant.NAMING_SERVER_HOST,
			  Constant.NAMING_SERVER_PORT,
			  Constant.NAMING_SERVER_PUBLIC_KEY);
	}
	
	//Method for adding a new service to the database
	public void AddService(String name, String host, int port, byte[] public_key)
	{
		String method_name = "AddService";
		Object[] parameters = {name, host, port, public_key};
		
		this.requestor.Request(method_name, parameters);
	}
	
	//Method for getting a proxy for a specific service
	public Service LookupService(String service_name)
	{
		String method_name = "LookupService";
		Object[] parameters = {service_name};
		
		return (Service) this.requestor.Request(method_name, parameters);
	}
}
