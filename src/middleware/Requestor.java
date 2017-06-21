package middleware;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;

public class Requestor
{
	private String host;
	private int port;
	private KeyPair client_keypair;
	private PublicKey server_public_key;
	
	private Marshaller marshaller;
	private Encryptor encryptor;
	private ClientRequestHandler client_request_handler;
	
	public Requestor(String host, int port, PublicKey server_public_key, KeyPair client_keypair)
	{
		this.host = host;
		this.port = port;
		this.client_keypair = client_keypair;
		this.server_public_key = server_public_key;
		this.marshaller = new Marshaller();
		this.encryptor = new Encryptor();
		this.client_request_handler = new ClientRequestHandler(this.host, this.port);
	}
	
	@SuppressWarnings("unchecked")
	public Object Request(String method_name, HashMap<String, Object> parameters)
	{
		//Every Request Establishes a new TCP Connection
		try {
			client_request_handler.establishTCP();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Creates message from method name and parameters and client public key
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("method_name", method_name);
		message.put("parameters", (Object) parameters);
		message.put("client_public_key", (Object) this.client_keypair.getPublic());
		
		//Marshal message
		byte [] marsh_msg = null;
		try {
			marsh_msg = this.marshaller.Marshall(message);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		//Encrypt Message
		marsh_msg = encryptor.Encrypt(marsh_msg, this.server_public_key);
		
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
		
		//Decrypt answer
		marsh_ret = this.encryptor.Decrypt(marsh_ret, this.client_keypair.getPrivate());
		
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
