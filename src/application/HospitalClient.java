package application;

import naming.NamingServiceProxy;
import naming.Service;

public class HospitalClient
{		
	public static void main(String [] args)
	{
		//Use Naming Service to obtain hospital service information
		NamingServiceProxy naming_server = new NamingServiceProxy();
		Service hospital_service = (Service) naming_server.LookupService("hospital");

		//Create a proxy for the HospitalService
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