package application;

import middleware.ClientProxy;

public class HospitalProxy extends ClientProxy
{
	public HospitalProxy(String remote_obj_host, int remote_obj_port, byte[] remote_obj_public_key)
	{
		super(remote_obj_host, remote_obj_port, remote_obj_public_key);
	}
	
	public String GetMedicalRecord(String username)
	{
		String method_name = "GetMedicalRecord";
		Object[] parameters = {username};
		
		//CALL REMOTE OBJECT
		String record = (String) this.requestor.Request(method_name, parameters);
		return record;
	}
}
