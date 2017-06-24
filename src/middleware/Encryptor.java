package middleware;

import cryptography.*;

public class Encryptor
{
	private static final boolean USE_CRYPTOGRAPHY = true;
	
	Symmetric symmetric;
	Asymmetric asymmetric;
	
	public Encryptor() 
	{
		if(USE_CRYPTOGRAPHY)
		{
			this.symmetric = new Dummy();
			//this.symmetric = new AES();
			
			this.asymmetric = new RSA();
		}
		
		else
		{
			this.symmetric = new Symmetric();   //Does not encrypt
			this.asymmetric = new Asymmetric();		//Does not encrypt
		}	
	}
	
	//-----------------------------------------------------
	//SYMMETRIC ENCRYPTION
	//-----------------------------------------------------
	
	public byte[] GenerateSymmetricKey()
	{
		return this.symmetric.GenerateKey();
	}
	
	public byte[] EncryptSymmetric(byte [] message, byte[] key)
	{
		return this.symmetric.Encrypt(message, key);	
	}
	
	public byte[] DecryptSymmetric(byte [] message, byte[] key)
	{
		return this.symmetric.Decrypt(message, key);
	}
			
	//-----------------------------------------------------
	//ASYMMETRIC ENCRYPTION
	//-----------------------------------------------------
	
	public AsymmetricKeyPair GenerateAsymmetricKeyPair()
	{		
		return this.asymmetric.GenerateKeyPair();
	}
	
	public byte [] EncryptAsymmetric(byte [] message, byte[] public_key)
	{
		return this.asymmetric.Encrypt(message, public_key);
	}
	
	public byte [] DecryptAsymmetric(byte [] message, byte[] private_key)
	{
		return this.asymmetric.Decrypt(message, private_key);
	}
}
