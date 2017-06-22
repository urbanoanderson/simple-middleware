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
	
	public void establishTCP()
	{
		try {
			this.clientSocket = new Socket(this.host, this.port);
			this.clientSocket.setKeepAlive(true);
			this.clientSocket.setTcpNoDelay(true);
			this.clientSocket.setSoTimeout(1500);
			this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
			this.inFromServer = new DataInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeTCP()
	{
		try {
			clientSocket.close();
			outToServer.close();
			inFromServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendTCP(byte [] msg)
	{
		System.out.println("ClientRequestHandler: sending message to " + this.host + ":" + this.port);
		
		int sentMessageSize = msg.length;
		try {
			outToServer.writeInt(sentMessageSize);
			outToServer.write(msg ,0,sentMessageSize);
			outToServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte [] receiveTCP()
	{
		System.out.println("ClientRequestHandler: receiving message");
		
		byte[] msg = null;
		
		int receivedMessageSize = 0;
		try {
			receivedMessageSize = inFromServer.readInt();
			
			if(receivedMessageSize != 0)
			{
				msg = new byte [receivedMessageSize];
				inFromServer.read(msg, 0, receivedMessageSize);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return msg;
	}
}
