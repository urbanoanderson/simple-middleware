package middleware;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler
{
	private Socket socket = null;
	private ServerSocket serverSocket = null;
	private DataOutputStream dataOutputStream = null;
	private DataInputStream dataInputStream = null;

	public ServerRequestHandler() {}
	
	public void establishTCP(int port)
	{
		System.out.println("ServerRequestHandler: establishing connection at port " + port);
		
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			dataOutputStream= new DataOutputStream(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeTCP()
	{
		System.out.println("ServerRequestHandler: closing connection");
		
		try {
			this.socket.close();
			this.serverSocket.close();
			this.dataOutputStream.close();
			this.dataInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendTCP(byte[] msg)
	{	
		System.out.println("ServerRequestHandler: sending message");
		
		try {
			dataOutputStream.writeInt(msg.length);
			dataOutputStream.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] receiveTCP()
	{
		System.out.println("ServerRequestHandler: receiving message");
		
		byte[] msg = null;
		int length;
		try {
			length = dataInputStream.readInt();
			if(length > 0)
			{
			    byte[] message = new byte[length];
			    dataInputStream.readFully(message, 0, message.length);
			    msg = message;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return msg;
	}
}