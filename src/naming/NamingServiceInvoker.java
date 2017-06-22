package naming;

import java.security.PublicKey;
import java.util.HashMap;

import extra.Constant;
import middleware.ClientProxy;
import middleware.Invoker;
import middleware.Marshaller;
import middleware.ServerRequestHandler;

public class NamingServiceInvoker extends Invoker
{
	private NamingService naming_service;
	
	public NamingServiceInvoker()
	{
		super(Constant.NAMING_SERVER_PORT, Constant.NAMING_SERVER_KEYPAIR);
		this.server_request_handler = new ServerRequestHandler();
		this.marshaller = new Marshaller();
		this.naming_service = new NamingService();
		
		//Add naming service information to database
		this.naming_service.AddService("hospital",
				 Constant.NAMING_SERVER_HOST,
				 Constant.NAMING_SERVER_PORT,
				 Constant.NAMING_SERVER_PUBLIC_KEY);
	}
	
	public HashMap<String, Object> ProcessRequest(String method_name, HashMap<String, Object> parameters)
	{
		HashMap<String, Object> ret_message = new HashMap<String, Object>();
		
		//Process AddService method
		if(method_name.equals("AddService"))
		{
			String name = (String) parameters.get("name");
			String host = (String) parameters.get("host");
			int port = (int) parameters.get("port");
			PublicKey public_key = (PublicKey) parameters.get("public_key");
			this.naming_service.AddService(name, host, port, public_key);
			ret_message.put(method_name, "DONE");
		}
		
		//Process LookupService method
		else if(method_name.equals("LookupService"))
		{
			String service_name = (String) parameters.get("service_name");
			ClientProxy client_proxy = this.naming_service.LookupService(service_name);
			ret_message.put(method_name, (Object) client_proxy);
		}
		
		return ret_message;
	}
}
