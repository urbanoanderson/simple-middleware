package naming;

import java.security.PublicKey;
import java.util.HashMap;

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
	public void AddService(String name, String host, int port, PublicKey public_key)
	{
		String method_name = "AddService";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("host", host);
		parameters.put("port", port);
		parameters.put("public_key", public_key);
		
		this.requestor.Request(method_name, parameters);
	}
	
	//Method for getting a proxy for a specific service
	public Service LookupService(String service_name)
	{
		String method_name = "LookupService";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("service_name", service_name);
		
		return (Service) this.requestor.Request(method_name, parameters);
	}
	
	public String GetMedicalRecord(String username)
	{
		String method_name = "GetMedicalRecord";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		
		//CALL REMOTE OBJECT
		String record = (String) this.requestor.Request(method_name, parameters);
		return record;
	}
}
