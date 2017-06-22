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

	public ServerRequestHandler() {}
	
	public void establishTCP(int port)
	{
		try {
			welcomeSocket = new ServerSocket(port);
			connectionSocket = welcomeSocket.accept();
			connectionSocket.setKeepAlive(true);
			connectionSocket.setTcpNoDelay(true);
			connectionSocket.setSoTimeout(1500);
			outToClient= new DataOutputStream(connectionSocket.getOutputStream());
			inFromClient = new DataInputStream(connectionSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeTCP()
	{
		try {
			this.connectionSocket.close();
			this.welcomeSocket.close();
			this.outToClient.close();
			this.inFromClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendTCP(byte[] msg)
	{
		int sentMessageSize = msg.length;
		
		try {
			outToClient.writeInt(sentMessageSize);
			outToClient.write(msg);
			outToClient.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] receiveTCP()
	{
		byte[] msg = null;
		int receivedMessageSize = 0;
		
		try {
			receivedMessageSize = inFromClient.readInt();
			msg = new byte[receivedMessageSize];
			inFromClient.read(msg,0,receivedMessageSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return msg;
	}
}