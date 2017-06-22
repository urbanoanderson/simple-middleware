package application;

import naming.NamingServiceProxy;
import naming.Service;

public class HospitalClient
{		
	public static void main(String [] args)
	{
		//Initialize distributed application with middleware
		NamingServiceProxy naming_server = new NamingServiceProxy();
		System.out.println("HospitalClient: looking for hospital service info");
		Service hospital_service = (Service) naming_server.LookupService("hospital");
		HospitalProxy hospital = new HospitalProxy(hospital_service.host, hospital_service.port, hospital_service.public_key);
		
		//Request medical record for user at the remote hospital object
		String username = "Anderson Urbano";
		System.out.println("HospitalClient: getting medical record");
		String med_rec = hospital.GetMedicalRecord(username);
		
		//Print medical record
		System.out.println("\nMedical record for " + username + ":");
		System.out.println(med_rec);
	}
}