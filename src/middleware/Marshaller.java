package middleware;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Marshaller
{
	public byte [] Marshall(Object message)
	{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(byteStream);
			os.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteStream.toByteArray();
	}
	
	public Object Unmarshall(byte [] message)
	{
		ByteArrayInputStream byteStream = new ByteArrayInputStream(message);
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(byteStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object ret = null;
		try {
			 ret = is.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
