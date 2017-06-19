package middleware;

public class NamingService
{
	//Address of this Naming Service
	public String host;
	public int port;
	
	//Database of Service Addresses 
	public static final String HOSPITAL_SERVICE_HOST = "localhost";
	public static final int HOSPITAL_SERVICE_PORT = 2000;
	
	public NamingService(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
	//Search for a specific service
	public ClientProxy LookupService(String service_name)
	{
		//If is the hospital service
		if(service_name.equals("hospital"))
		{
			return (ClientProxy) new HospitalProxy(HOSPITAL_SERVICE_HOST, HOSPITAL_SERVICE_PORT);
		}
		
		//Service not found
		else
			return null;
	}
	
	//TODO: Make Naming service a server that can be accessed using ip/port
	public static void main(String [] args)
	{
	}
}
