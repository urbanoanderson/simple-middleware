package middleware;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;

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
		try {
			server_request_handler.establishTCP(this.service_port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Tries to receive request
		byte [] marsh_request = null;
		try {
			marsh_request = server_request_handler.receiveTCP();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(!marsh_request.equals(null))
		{
			//Undo cryptography
			marsh_request = this.encryptor.Decrypt(marsh_request, this.server_keypair.getPrivate());
			
			//Unmarshal request
			HashMap<String, Object> request = new HashMap<String, Object>();
			try {
				request = (HashMap<String, Object>) this.marshaller.Unmarshall(marsh_request);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//Process Request
			String method_name = (String) request.get("method_name");
			HashMap<String, Object> parameters = (HashMap<String, Object>) request.get("parameters");
			PublicKey client_public_key = (PublicKey) parameters.get("client_public_key");
			HashMap<String, Object> ret_message = ProcessRequest(method_name, parameters);
			
			//Marshal results
			byte [] marsh_ret = null;
			try {
				marsh_ret = this.marshaller.Marshall(ret_message);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//Encrypt Results
			marsh_ret = encryptor.Encrypt(marsh_ret, client_public_key);
			
			//Send ret_message with TCP
			try {
				this.server_request_handler.sendTCP(marsh_ret);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//Closes Connection
		try {
			server_request_handler.closeTCP();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
