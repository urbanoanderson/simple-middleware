package application;

//Hospital Remote Object Implementation
public class Hospital
{
	//Retrieve medical record for a user
	public String GetMedicalRecord(String username)
	{
		//Access Medical Record Database
		if(username.equals("Anderson Urbano"))
			return "Healthy";
		else if(username.equals("Isabela Rangel"))
			return "Not so much";
		else
			return "User not known";
	}
}
