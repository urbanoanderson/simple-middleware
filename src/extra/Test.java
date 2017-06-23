/*
 * ESSA CLASSE É APENAS PARA TESTES ALEATÓRIOS 
 */

package extra;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import middleware.Encryptor;
import middleware.Marshaller;
import naming.Service;

public class Test
{	
	//Class for Reflection test
	static public class A
	{		
		public void Method1(String str)
		{
			System.out.println(str);
		}
		
		public void Method2(String str, Integer value)
		{
			System.out.println(str + String.valueOf(value));
		}
		
		public String Method3(String str, byte[] byte_array)
		{
			return (str + (new String(byte_array)));
		}
		
		public void AddService(String name, String host, Integer port, byte[] public_key)
		{
			HashMap<String, Service> service_database = new HashMap<String, Service>();
			Service service = new Service(name, host, port, public_key);
			service_database.put(name, service);
		}
	}
	
	public static void main(String [] args) throws InvalidKeyException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		ReflectionTest();
		//AsymmetricEncryptionTest();
		//SymmetricEncryptionTest();
		//MessageSimulationTest();
	}
	
	@SuppressWarnings("rawtypes")
	public static void ReflectionTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		A obj = new A();
		String method_name = "AddService";
		String param1 = "hospital";
		String param2 = "localhost";
		Integer param3 = 2001;
		byte[] param4 = "World".getBytes();
		
		Object[] params = { param1, param2, param3, param4 };
		
		Class[] param_types = new Class[params.length];
		for(int i = 0; i < params.length; i++)
			param_types[i] = params[i].getClass();
		
		Method method = obj.getClass().getMethod(method_name, param_types);
		Object return_value = method.invoke(obj, params);
		
		System.out.print("Parameters: ");
		for(int i = 0; i < params.length; i++)
			System.out.print(params[i] + ", ");
		System.out.println("");
		
		System.out.print("Parameter Types: ");
		for(int i = 0; i < params.length; i++)
			System.out.print(param_types[i] + ", ");
		System.out.println("");
		System.out.println("Return: " + return_value);
	}
	
	public static void SymmetricEncryptionTest() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{		
		//Message to send
		String plain_text = "HelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorld";
		
		Encryptor encryptor = new Encryptor();
		byte[] key = Encryptor.GenerateSymmetricKey();
		byte[] enc_message = encryptor.EncryptSymmetric(plain_text.getBytes(), key);
        byte[] dec_message = encryptor.DecryptSymmetric(enc_message, key);
		
        System.out.println(new String(dec_message));
	}
		
	@SuppressWarnings({ "unchecked" })
	public static void AsymmetricEncryptionTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException
	{		
		//-----------------------------------

		Encryptor encryptor = new Encryptor();
		Marshaller marshaller = new Marshaller();
		KeyPair keyPair = Encryptor.GenerateAsymmetricKeyPair();
		PublicKey public_key = keyPair.getPublic();
		PrivateKey private_key = keyPair.getPrivate();
		byte[] public_key_bytes = public_key.getEncoded();
		byte[] private_key_bytes = private_key.getEncoded();
		
		//Information to send
		String method_name = "HelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorld";
		
		//Create Content
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("method_name", method_name);
		
		//Marshal and encrypt content
		byte[] marsh_content = marshaller.Marshall(content);
		byte[] enc_content = encryptor.EncryptAsymmetric(marsh_content, public_key_bytes);
		
		//Create message
		ArrayList<byte[]> pub_key_obj = new ArrayList<byte[]>();
		pub_key_obj.add(public_key_bytes);
		
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("content", enc_content);
		message.put("public_key", pub_key_obj);
		
		//Marshal message
		byte[] marsh_message = marshaller.Marshall(message);
		
		//Send message
		//...

		//----------------------------------------
		
		//Receive message
		//...
		
		//Unmarshal message
		HashMap<String, Object> unmarsh_message = (HashMap<String, Object>) marshaller.Unmarshall(marsh_message);
		byte[] enc_content2 = (byte[]) unmarsh_message.get("content");
		
		//Decrypt and Unmarshal content
		byte[] dec_content = encryptor.DecryptAsymmetric(enc_content2, private_key_bytes);
		HashMap<String, Object> content2 = (HashMap<String, Object>) marshaller.Unmarshall(dec_content);
		
		//Print information from content
		String info = (String) content2.get("method_name");
		System.out.println(info);
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void MessageSimulationTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException
	{		
		//-----------------------------------

		Encryptor encryptor = new Encryptor();
		Marshaller marshaller = new Marshaller();
		KeyPair keyPair = Encryptor.GenerateAsymmetricKeyPair();
		byte[] server_public_key = keyPair.getPublic().getEncoded();
		byte[] server_private_key = keyPair.getPrivate().getEncoded();
		byte[] client_symmetric_key = Encryptor.GenerateSymmetricKey();
		
		//Information to send
		String method_name = "HelloWorld";

		//Create Content
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("method_name", method_name);
		
		//Marshal and symmetrically encrypt content
		byte[] marsh_content = marshaller.Marshall(content);
		byte[] enc_content = encryptor.EncryptSymmetric(marsh_content, client_symmetric_key);
		
		//Asymmetrically encrypt the symmetric key
		byte[] encrypted_symmetric_key = encryptor.EncryptAsymmetric(client_symmetric_key, server_public_key);
		
		//Create message		
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("content", enc_content);
		message.put("encrypted_symmetric_key", encrypted_symmetric_key);
		
		//Marshal message
		byte[] marsh_message = marshaller.Marshall(message);
		
		//Send message
		//...

		//----------------------------------------
		
		//Receive message
		//...
		
		//Unmarshal message
		HashMap<String, Object> unmarsh_message = (HashMap<String, Object>) marshaller.Unmarshall(marsh_message);
		
		//Get objects from inside message
		byte[] encrypted_symmetric_key2 = (byte[]) unmarsh_message.get("encrypted_symmetric_key");
		byte[] enc_content2 = (byte[]) unmarsh_message.get("content");
		
		//Decrypt symmetric key
		byte[] client_symmetric_key2 = encryptor.DecryptAsymmetric(encrypted_symmetric_key2, server_private_key);
		
		//Decrypt and Unmarshal content
		byte[] dec_content = encryptor.DecryptSymmetric(enc_content2, client_symmetric_key2);
		HashMap<String, Object> content2 = (HashMap<String, Object>) marshaller.Unmarshall(dec_content);
		
		//Print information from content
		String info = (String) content2.get("method_name");
		System.out.println(info);
	}
}
