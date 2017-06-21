package application;

import java.util.HashMap;

import middleware.Invoker;
import middleware.Marshaller;
import middleware.ServerRequestHandler;

public class HospitalInvoker extends Invoker
{
	private Hospital hospital;
	
	public HospitalInvoker(int hospital_service_port)
	{
		super(hospital_service_port);
		this.server_request_handler = new ServerRequestHandler();
		this.marshaller = new Marshaller();
		this.hospital = new Hospital();
	}
	
	public HashMap<String, Object> ProcessRequest(String method_name, HashMap<String, Object> parameters)
	{
		HashMap<String, Object> ret_message = new HashMap<String, Object>();
		
		//Process GetMedicalRecord method
		if(method_name.equals("GetMedicalRecord"))
		{
			String username = (String) parameters.get("username");
			String record = this.hospital.GetMedicalRecord(username);
			ret_message.put(method_name, record);
		}
		
		return ret_message;
	}
}