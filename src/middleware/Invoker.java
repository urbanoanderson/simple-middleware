package middleware;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;

import extra.Constant;

public class Invoker
{
	protected ServerRequestHandler server_request_handler;
	protected Marshaller marshaller;
	protected int service_port;
	private KeyPair server_keypair;
	private Encryptor encryptor;
	
	public Invoker(int service_port, KeyPair server_keypair)
	{
		this.server_request_handler = new ServerRequestHandler();
		this.marshaller = new Marshaller();
		this.encryptor = new Encryptor();
		this.service_port = service_port;
		this.server_keypair = server_keypair;
	}
	
	public int GetServicePort()
	{
		return this.service_port;
	}
	
	//This method should be overridden by subclasses
	public HashMap<String, Object> ProcessRequest(String method_name, HashMap<String, Object> parameters)
	{
		//Return empty message
		HashMap<String, Object> ret_message = new HashMap<String, Object>();
		return ret_message;
	}
	
	@SuppressWarnings("unchecked")
	public void Invoke()
	{	
		//Opens connection
		server_request_handler.establishTCP(this.service_port);
		
		//Tries to receive request
		byte [] marsh_request = null;
		marsh_request = server_request_handler.receiveTCP();
		
		if(!marsh_request.equals(null))
		{
			//Undo cryptography
			if(Constant.USE_CRYPTOGRAPHY)
				marsh_request = this.encryptor.Decrypt(marsh_request, this.server_keypair.getPrivate());
			
			//Unmarshal request
			HashMap<String, Object> request = new HashMap<String, Object>();
			request = (HashMap<String, Object>) this.marshaller.Unmarshall(marsh_request);
			
			//Process Request
			String method_name = (String) request.get("method_name");
			HashMap<String, Object> parameters = (HashMap<String, Object>) request.get("parameters");
			PublicKey client_public_key = (PublicKey) parameters.get("client_public_key");
			HashMap<String, Object> ret_message = ProcessRequest(method_name, parameters);
			
			//Marshal results
			byte [] marsh_ret = null;
			marsh_ret = this.marshaller.Marshall(ret_message);
			
			//Encrypt Results
			if(Constant.USE_CRYPTOGRAPHY)
				marsh_ret = encryptor.Encrypt(marsh_ret, client_public_key);
			
			//Send ret_message with TCP
			this.server_request_handler.sendTCP(marsh_ret);
		}
		
		//Closes Connection
		server_request_handler.closeTCP();
	}
}
