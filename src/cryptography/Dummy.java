package cryptography;

import java.security.SecureRandom;

public class Dummy extends Symmetric
{
	public byte[] GenerateKey()
    {
    	SecureRandom random = new SecureRandom();
    	byte [] key = new byte[32]; //256 bits
    	random.nextBytes(key);
    	return key;
    }
	
	public byte[] Encrypt(byte[] message, byte[] key)
	{
		byte[] encrypted = message;
  
		return encrypted;
	}
  
	public byte[] Decrypt(byte[] message, byte[] key)
	{
		byte[] decrypted = message;
  
		return decrypted;
	}
}
