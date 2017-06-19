package application;

public class Hospital
{
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
