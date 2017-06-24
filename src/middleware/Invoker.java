package middleware;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Invoker
{
	protected ServerRequestHandler server_request_handler;
	protected Marshaller marshaller;
	private Encryptor encryptor;
	
	protected byte[] server_public_key;
	protected byte[] server_private_key;
	protected Integer service_port;
	
	protected Object remote_object;
	
	public Invoker(Object remote_object, Integer service_port, byte[] server_public_key, byte[] server_private_key)
	{
		this.server_request_handler = new ServerRequestHandler();
		this.marshaller = new Marshaller();
		this.encryptor = new Encryptor();
		this.service_port = service_port;
		this.server_public_key = server_public_key;
		this.server_private_key = server_private_key;
		this.remote_object = remote_object;
	}
	
	public Integer GetServicePort()
	{
		return this.service_port;
	}
	
	//This method should be overridden by subclasses
	@SuppressWarnings("rawtypes")
	public Object ProcessRequest(String method_name, Object[] parameters)
	{ 
		//Get parameter type list for method
		Class[] param_types = new Class[parameters.length];
		for(int i = 0; i < parameters.length; i++)
			param_types[i] = parameters[i].getClass();
		
		//Invoke method on remote object
		Object return_value = null;
		try {
			Method method = this.remote_object.getClass().getMethod(method_name, param_types);
			return_value = method.invoke(this.remote_object, parameters);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		//Return empty message
		return return_value;
	}
	
	@SuppressWarnings("unchecked")
	public void Invoke()
	{	
		//Opens connection
		server_request_handler.establishTCP(this.service_port);
		
		//Tries to receive request
		byte [] marsh_request = server_request_handler.receiveTCP();
		
		if(!marsh_request.equals(null))
		{
			//##########################################################
			
			//Unmarshal message
			HashMap<String, Object> unmarsh_message = (HashMap<String, Object>) marshaller.Unmarshall(marsh_request);
			byte[] encrypted_symmetric_key = (byte[]) unmarsh_message.get("encrypted_symmetric_key");
			byte[] enc_content = (byte[]) unmarsh_message.get("content");
			
			//Decrypt client symmetric key using server private key
			byte[] client_symmetric_key = encryptor.DecryptAsymmetric(encrypted_symmetric_key, this.server_private_key);
			
			//Decrypt request content
			byte[] dec_content = enc_content;
			dec_content = encryptor.DecryptSymmetric(enc_content, client_symmetric_key);
			
			//Unmarshal request
			HashMap<String, Object> request = (HashMap<String, Object>) marshaller.Unmarshall(dec_content);
			
			//##########################################################
			
			//Process Request
			String method_name = (String) request.get("method_name");
			Object[] parameters = (Object[]) request.get("parameters");
			Object ret_obj = ProcessRequest(method_name, parameters);
			
			//##########################################################
			
			//Marshal results
			byte [] marsh_ret = this.marshaller.Marshall(ret_obj);
			
			//Encrypt Results
			marsh_ret = encryptor.EncryptSymmetric(marsh_ret, client_symmetric_key);
			
			//##########################################################
			
			//Send ret_message with TCP
			this.server_request_handler.sendTCP(marsh_ret);
		}
		
		//Closes Connection
		server_request_handler.closeTCP();
	}
}
