package application;

//Hospital Remote Object Implementation
public class Hospital
{
	//Retrieve medical record for a user
	public String GetMedicalRecord(String username)
	{
		//Access Medical Record Database
		if(username.equals("Anderson Urbano"))
			return "Has disease A";
		else if(username.equals("Isabela Rangel"))
			return "Has disease B";
		else if(username.equals("Italo Roberto"))
			return "Has disease C";
		else if(username.equals("Pedro Queiroz"))
			return "Has disease D";
		else
			return "User not known";
	}
}
