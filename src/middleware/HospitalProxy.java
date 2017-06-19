package middleware;

import java.util.HashMap;

public class HospitalProxy extends ClientProxy
{
	public HospitalProxy(String remote_obj_host, int remote_obj_port)
	{
		super(remote_obj_host, remote_obj_port);
	}
	
	public String GetMedicalRecord(String username)
	{
		String method_name = "GetMedicalRecord";
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		
		//CALL REMOTE OBJECT
		String record = (String) this.requestor.Request(method_name, parameters);
		return record;
	}
}
