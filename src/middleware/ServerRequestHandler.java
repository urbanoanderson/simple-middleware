package middleware;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler
{
	private Socket connectionSocket = null;
	private ServerSocket welcomeSocket = null;
	private DataOutputStream outToClient = null;
	private DataInputStream inFromClient = null;

	public ServerRequestHandler()
	{
	}
	
	public void establishTCP(int port) throws IOException
	{
		welcomeSocket= new ServerSocket(port);
		connectionSocket = welcomeSocket.accept();
		//connectionSocket.setKeepAlive(true);
		//connectionSocket.setTcpNoDelay(true);
		//connectionSocket.setSoTimeout(1500);
		outToClient= new DataOutputStream(connectionSocket.getOutputStream());
		inFromClient = new DataInputStream(connectionSocket.getInputStream());
	}
	
	public void closeTCP() throws IOException
	{
		this.connectionSocket.close();
		this.welcomeSocket.close();
		this.outToClient.close();
		this.inFromClient.close();
	}

	public void sendTCP(byte[] msg) throws IOException,InterruptedException
	{
		int sentMessageSize = msg.length;
		System.out.println("message size: "+sentMessageSize);
		outToClient.writeInt(sentMessageSize);
		outToClient.write(msg);
		//outToClient.flush();
	}
	
	public byte[] receiveTCP() throws IOException,InterruptedException
	{
		byte[] msg = null;
		
		int receivedMessageSize = 0;
		receivedMessageSize = inFromClient.readInt();
		msg = new byte[receivedMessageSize];
		inFromClient.read(msg,0,receivedMessageSize);
		
		return msg;
	}
}