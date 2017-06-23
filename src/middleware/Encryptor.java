package middleware;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import extra.Constant;

public class Encryptor
{
	private static final String SYMMETRIC_ALGORITHM = "AES";
	private static final int SYMMETRIC_KEY_SIZE = 128;
	private static final String ASYMMETRIC_ALGORITHM = "RSA";
	private static final int ASYMMETRIC_KEY_SIZE = 2048;
	
	public Encryptor() {}
	
	//#####################################################
	//SYMMETRIC ENCRYPTION
	//#####################################################
	
	public static byte[] GenerateSymmetricKey()
	{
		byte[] key = new byte[SYMMETRIC_KEY_SIZE/8];
		Random rand = new Random();
		rand.nextBytes(key);
		
		return key;
	}
	
	public byte[] EncryptSymmetric(byte [] message, byte[] key)
	{
		if(!Constant.USE_CRYPTOGRAPHY)
			return message;
		
		SecretKeySpec secret_key = new SecretKeySpec(key, SYMMETRIC_ALGORITHM);
		Cipher cipher = null;
		byte[] encrypted = null;
		
		try {
			cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secret_key);
			encrypted = cipher.doFinal(message);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
        return encrypted;
	}
	
	public byte[] DecryptSymmetric(byte [] encrypted_message, byte[] key)
	{
		if(!Constant.USE_CRYPTOGRAPHY)
			return encrypted_message;
		
		SecretKeySpec secret_key = new SecretKeySpec(key, SYMMETRIC_ALGORITHM);
		Cipher cipher = null;
		byte[] decrypted = null;
		
		try {
			cipher = Cipher.getInstance(SYMMETRIC_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secret_key);
			decrypted = cipher.doFinal(encrypted_message);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
        return decrypted;
	}
			
	//#####################################################
	//ASYMMETRIC ENCRYPTION
	//#####################################################
	
	public static KeyPair GenerateAsymmetricKeyPair()
	{
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance(ASYMMETRIC_ALGORITHM);
			keyGen.initialize(ASYMMETRIC_KEY_SIZE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return keyGen.generateKeyPair();
	}
	
	public static byte[] GetPublic(KeyPair keyPair)
	{
		return keyPair.getPublic().getEncoded();
	}
	
	public static byte[] GetPrivate(KeyPair keyPair)
	{
		return keyPair.getPrivate().getEncoded();
	}
	
	public byte [] EncryptAsymmetric(byte [] message, byte[] public_key_bytes)
	{
		if(!Constant.USE_CRYPTOGRAPHY)
			return message;
		
		PublicKey public_key = null;
		try {
			public_key = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM).generatePublic(new X509EncodedKeySpec(public_key_bytes));
		} catch (InvalidKeySpecException e2) {
			e2.printStackTrace();
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ASYMMETRIC_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, public_key);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
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
	
	public byte [] DecryptAsymmetric(byte [] encrypted_message, byte[] private_key_bytes)
	{
		if(!Constant.USE_CRYPTOGRAPHY)
			return encrypted_message;
		
		PrivateKey private_key = null;
		try {
			private_key = KeyFactory.getInstance(ASYMMETRIC_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(private_key_bytes));
		} catch (InvalidKeySpecException e2) {
			e2.printStackTrace();
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		
		byte[] message = null;
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ASYMMETRIC_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, private_key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		try {
			message = cipher.doFinal(encrypted_message);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return message;
	}
}
