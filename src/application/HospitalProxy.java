package application;

import java.security.PublicKey;
import java.util.HashMap;

import middleware.ClientProxy;

public class HospitalProxy extends ClientProxy
{
	private static final long serialVersionUID = 929690120228542281L;

	public HospitalProxy(String remote_obj_host, int remote_obj_port, PublicKey remote_obj_public_key)
	{
		super(remote_obj_host, remote_obj_port, remote_obj_public_key);
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
