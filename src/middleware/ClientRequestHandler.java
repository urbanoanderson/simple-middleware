package middleware;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRequestHandler
{
	private String host;
	private int port;
	
	private Socket clientSocket = null;
	private DataOutputStream outToServer = null;
	private DataInputStream inFromServer = null;
	
	public ClientRequestHandler(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
	public void establishTCP() throws UnknownHostException, IOException
	{
		this.clientSocket = new Socket(this.host, this.port);
		this.clientSocket.setKeepAlive(true);
		this.clientSocket.setTcpNoDelay(true);
		this.clientSocket.setSoTimeout(1500);
		
		this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
		this.inFromServer = new DataInputStream(clientSocket.getInputStream());
	}
	
	public void closeTCP() throws IOException
	{
		clientSocket.close();
		outToServer.close();
		inFromServer.close();
	}
	
	public void sendTCP(byte [] msg) throws InterruptedException, IOException
	{
		System.out.println("ClientRequestHandler: sending message to " + this.host + ":" + this.port);
		
		int sentMessageSize = msg.length;
		outToServer.writeInt(sentMessageSize);
		outToServer.write(msg ,0,sentMessageSize);
		//outToServer.flush();
	}
	
	public byte [] receiveTCP() throws IOException
	{
		System.out.println("ClientRequestHandler: receiving message");
		
		byte[] msg = null;
		
		int receivedMessageSize = 0;
		receivedMessageSize = inFromServer.readInt();
		
		if(receivedMessageSize != 0)
		{
			msg = new byte [receivedMessageSize];
			inFromServer.read(msg, 0, receivedMessageSize);
		}
		
		return msg;
	}
}
