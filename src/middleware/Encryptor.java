package middleware;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encryptor
{
	private static String ALGORITHM = "RSA";
	 
	private Cipher cipher;
	
	public Encryptor()
	{				
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	public static KeyPair GenerateKeyPair()
	{
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance(ALGORITHM);
			keyGen.initialize(1024);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return keyGen.generateKeyPair();
	}
	
	public byte [] Encrypt(byte [] message, PublicKey public_key)
	{
		try {
			this.cipher.init(Cipher.ENCRYPT_MODE, public_key);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		}
		
		byte [] encrypted_message = null;
		try {
			encrypted_message = cipher.doFinal(message);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return encrypted_message;
	}
	
	public byte [] Decrypt(byte [] encrypted_message, PrivateKey private_key)
	{
		byte[] message = null;
		
		try {
			this.cipher.init(Cipher.DECRYPT_MODE, private_key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		try {
			message = this.cipher.doFinal(encrypted_message);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return message;
	}
}
