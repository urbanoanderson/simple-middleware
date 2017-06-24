package cryptography;

public class Asymmetric
{
	public AsymmetricKeyPair GenerateKeyPair()
    {
		return new AsymmetricKeyPair();
    }
	
	public byte[] Encrypt(byte[] message, byte[] public_key)
    {
		return message;
    }
	
	public byte[] Decrypt(byte[] message, byte[] private_key)
    {
		return message;
    }
}
