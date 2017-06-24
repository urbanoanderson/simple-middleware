package cryptography;

public class AsymmetricKeyPair
{
	private byte[] public_key;
	private byte[] private_key;
	
	public AsymmetricKeyPair(byte[] public_key, byte[] private_key)
	{
		this.public_key = public_key;
		this.private_key = private_key;
	}
	
	public AsymmetricKeyPair()
	{
		this.public_key = new byte[0];
		this.private_key = new byte[0];
	}
	
	public byte[] GetPublicKey()
	{
		return this.public_key;
	}
	
	public byte[] GetPrivateKey()
	{
		return this.private_key;
	}
}
