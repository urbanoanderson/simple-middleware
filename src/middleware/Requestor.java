package middleware;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;

import extra.Constant;

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
		client_request_handler.establishTCP();
		
		//Creates message from method name and parameters and client public key
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("method_name", method_name);
		message.put("parameters", (Object) parameters);
		message.put("client_public_key", (Object) this.client_keypair.getPublic());
		
		//Marshal message
		byte [] marsh_msg = null;
		marsh_msg = this.marshaller.Marshall(message);
		
		//Encrypt Message
		if(Constant.USE_CRYPTOGRAPHY)
			marsh_msg = encryptor.Encrypt(marsh_msg, this.server_public_key);
		
		//Send message through ClientRequestHandler
		this.client_request_handler.sendTCP(marsh_msg);
		
		//Receive return through ClientRequestHandler
		byte [] marsh_ret = null;
		marsh_ret = this.client_request_handler.receiveTCP();
		
		//Decrypt answer
		if(Constant.USE_CRYPTOGRAPHY)
			marsh_ret = this.encryptor.Decrypt(marsh_ret, this.client_keypair.getPrivate());
		
		//Unmarshal results
		HashMap<String, Object> ret_message = new HashMap<String, Object>();
		ret_message = (HashMap<String, Object>) this.marshaller.Unmarshall(marsh_ret);
		
		//End the TCP connection
		client_request_handler.closeTCP();
		
		//Return results
		return ret_message.get(method_name);
	}
}
