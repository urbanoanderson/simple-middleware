package middleware;

public class ClientProxy
{
	protected String remote_obj_host;
	protected int remote_obj_port;
	protected Requestor requestor;
	
	public ClientProxy(String remote_obj_host, int remote_obj_port)
	{
		this.remote_obj_host = remote_obj_host;
		this.remote_obj_port = remote_obj_port;
		this.requestor = new Requestor(this.remote_obj_host, this.remote_obj_port);
	}
}
