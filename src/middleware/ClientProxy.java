package middleware;

import java.security.KeyPair;
import java.security.PublicKey;

public class ClientProxy
{
	protected String remote_obj_host;
	protected int remote_obj_port;
	protected Requestor requestor;
	protected PublicKey remote_obj_public_key;
	
	protected KeyPair client_keypair;
	
	public ClientProxy(String remote_obj_host, int remote_obj_port, PublicKey remote_obj_public_key)
	{
		this.remote_obj_host = remote_obj_host;
		this.remote_obj_port = remote_obj_port;
		this.remote_obj_public_key = remote_obj_public_key;
		
		this.client_keypair = Encryptor.GenerateKeyPair();
		
		this.requestor = new Requestor(this.remote_obj_host, 
									   this.remote_obj_port,
									   this.remote_obj_public_key,
									   this.client_keypair);
	}
}
