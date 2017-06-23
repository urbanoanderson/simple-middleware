package middleware;

public class ClientProxy
{
	protected String remote_obj_host;
	protected int remote_obj_port;
	protected byte[] remote_obj_public_key;
	
	protected Requestor requestor;
	
	public ClientProxy(String remote_obj_host, int remote_obj_port, byte[] remote_obj_public_key)
	{
		this.remote_obj_host = remote_obj_host;
		this.remote_obj_port = remote_obj_port;
		this.remote_obj_public_key = remote_obj_public_key;
		
		this.requestor = new Requestor(this.remote_obj_host, 
									   this.remote_obj_port,
									   this.remote_obj_public_key);
	}
}
