package cryptography;

public class Symmetric
{
	public byte[] GenerateKey()
    {
    	return new byte[0];
    }
	
	public byte[] Encrypt(byte[] message, byte[] key)
	{
		return message;
	}
  
	public byte[] Decrypt(byte[] message, byte[] key)
	{
		return message;
	}
}
