package application;

import naming.NamingServiceProxy;

public class HospitalClient
{		
	public static void main(String [] args)
	{
		//Initialize distributed application with middleware
		NamingServiceProxy naming_server = new NamingServiceProxy();
		HospitalProxy hospital = (HospitalProxy) naming_server.LookupService("hospital");
		
		//Request medical record for user at the remote hospital object
		String username = "Anderson Urbano";
		String med_rec = hospital.GetMedicalRecord(username);
		
		//Print medical record
		System.out.println("\nMedical record for " + username + ":");
		System.out.println(med_rec);
	}
}