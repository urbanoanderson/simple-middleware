package middleware;

import java.util.HashMap;

import extra.Constant;

public class Requestor
{
	private String server_host;
	private Integer server_port;
	private byte[] server_public_key;
	
	private Marshaller marshaller;
	private Encryptor encryptor;
	private ClientRequestHandler client_request_handler;
	
	public Requestor(String server_host, Integer server_port, byte[] server_public_key)
	{
		this.server_host = server_host;
		this.server_port = server_port;
		this.server_public_key = server_public_key;
		this.marshaller = new Marshaller();
		this.encryptor = new Encryptor();
		this.client_request_handler = new ClientRequestHandler(this.server_host, this.server_port);
	}
	
	public Object Request(String method_name, Object[] parameters)
	{
		//Every Request Establishes a new TCP Connection
		client_request_handler.establishTCP();
		
		//##########################################################
		
		//Create Content
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("method_name", method_name);
		content.put("parameters", parameters);
		
		//Marshal and symmetrically encrypt content
		byte[] marsh_content = marshaller.Marshall(content);
		byte[] enc_content = marsh_content;
		byte[] symmetric_key = Encryptor.GenerateSymmetricKey();
		
		if(Constant.USE_CRYPTOGRAPHY)
			enc_content = encryptor.EncryptSymmetric(marsh_content, symmetric_key);
		
		//Asymmetrically encrypt the symmetric key with the server public key
		byte[] encrypted_symmetric_key = encryptor.EncryptAsymmetric(symmetric_key, this.server_public_key);
		
		//Create message
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("content", enc_content);
		message.put("encrypted_symmetric_key", encrypted_symmetric_key);
		
		//Marshal message
		byte[] marsh_msg = marshaller.Marshall(message);
		
		//Send message through ClientRequestHandler
		this.client_request_handler.sendTCP(marsh_msg);
		
		//##########################################################
		
		//Receive return through ClientRequestHandler
		byte [] marsh_ret = this.client_request_handler.receiveTCP();
		
		//Decrypt answer
		if(Constant.USE_CRYPTOGRAPHY)
			marsh_ret = this.encryptor.DecryptSymmetric(marsh_ret, symmetric_key);
		
		//Unmarshal results
		 Object ret_message = (Object) this.marshaller.Unmarshall(marsh_ret);
		
		//##########################################################
		
		//End the TCP connection
		client_request_handler.closeTCP();
		
		//Return results
		return ret_message;
	}
}
