package middleware;

import java.io.IOException;
import java.net.UnknownHostException;

import java.util.HashMap;

public class Requestor
{
	private String host;
	private int port;
	
	private Marshaller marshaller;
	private ClientRequestHandler client_request_handler;
	
	public Requestor(String host, int port)
	{
		this.host = host;
		this.port = port;
		this.marshaller = new Marshaller();
		this.client_request_handler = new ClientRequestHandler(this.host, this.port);
	}
	
	@SuppressWarnings("unchecked")
	public Object Request(String method_name, HashMap<String, Object> parameters)
	{
		//Every Invocation Establishes a new TCP Connection
		try {
			client_request_handler.establishTCP();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Creates message from method name and parameters
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("method_name", method_name);
		message.put("parameters", (Object) parameters);
		
		//Marshal message
		byte [] marsh_msg = null;
		try {
			marsh_msg = this.marshaller.Marshall(message);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//Send message through ClientRequestHandler
		try {
			this.client_request_handler.sendTCP(marsh_msg);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Receive return through ClientRequestHandler
		byte [] marsh_ret = null;
		try {
			marsh_ret = this.client_request_handler.receiveTCP();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Unmarshal results
		HashMap<String, Object> ret_message = new HashMap<String, Object>();
		try {
			ret_message = (HashMap<String, Object>) this.marshaller.Unmarshall(marsh_ret);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//End the TCP connection
		try {
			client_request_handler.closeTCP();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Return results
		return ret_message.get(method_name);
	}
}
