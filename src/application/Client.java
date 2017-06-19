package application;

import middleware.HospitalProxy;
import middleware.NamingService;

public class Client
{
	public static final String NAMING_SERVICE_HOST = "localhost";
	public static final int NAMING_SERVICE_PORT = 2000;
	
	public static void main(String [] args)
	{
		//Initialize distributed application with middleware
		NamingService naming_server = new NamingService(NAMING_SERVICE_HOST, NAMING_SERVICE_PORT);
		HospitalProxy hospital = (HospitalProxy) naming_server.LookupService("hospital");
		
		//Request medical record for user at the remote hospital object
		String username = "Anderson Urbano";
		String med_rec = hospital.GetMedicalRecord(username);
		
		//Print medical record
		System.out.println("\nMedical record for " + username + ":");
		System.out.println(med_rec);
	}
}
