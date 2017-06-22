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
	
	private Socket socket = null;
	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;
	
	public ClientRequestHandler(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
	public void establishTCP()
	{
		System.out.println("ClientRequestHandler: establishing connection to " + this.host + ":" + this.port);
		
		try {
			this.socket = new Socket(this.host, this.port);
			this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
			this.dataInputStream = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeTCP()
	{
		System.out.println("ClientRequestHandler: closing connection");
		
		try {
			socket.close();
			dataOutputStream.close();
			dataInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendTCP(byte [] msg)
	{
		System.out.println("ClientRequestHandler: sending message to " + this.host + ":" + this.port);
		
		try {
			dataOutputStream.writeInt(msg.length);
			dataOutputStream.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte [] receiveTCP()
	{
		System.out.println("ClientRequestHandler: receiving message");
		
		byte[] msg = null;
		int length;
		try {
			length = dataInputStream.readInt();
			if(length > 0) {
			    byte[] message = new byte[length];
			    dataInputStream.readFully(message, 0, message.length);
			    msg = message;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return msg;
	}
}
