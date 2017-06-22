package naming;

import java.io.Serializable;
import java.security.PublicKey;

public class Service implements Serializable
{
	private static final long serialVersionUID = -3248461674176133980L;
	
	public String name;
	public String host;
	public int port;
	public PublicKey public_key;
	
	public Service(String name, String host, int port, PublicKey public_key)
	{
		this.name = name;
		this.host = host;
		this.port = port;
		this.public_key = public_key;
	}
}
