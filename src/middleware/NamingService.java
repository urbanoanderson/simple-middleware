package middleware;

public class NamingService
{
	public static final String HOSPITAL_SERVICE_HOST = "localhost";
	public static final int HOSPITAL_SERVICE_PORT = 2000;
	
	public String host;
	public int port;
	
	public NamingService(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
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
}
