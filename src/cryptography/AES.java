package cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class AES extends Symmetric
{ 
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    private void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes();
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
    }
    
    public byte[] GenerateKey()
    {
    	SecureRandom random = new SecureRandom();
    	byte [] key = new byte[32];
    	random.nextBytes(key);
    	return key;
    }
 
    public byte[] Encrypt(byte[] message, byte[] secret_key) 
    {
    	String strToEncrypt = new String(message);
    	String secret = new String(secret_key);
    	
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String enc = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            return enc.getBytes();
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        
        return null;
    }
 
    public byte[] Decrypt(byte[] message, byte[] secret_key) 
    {
    	String strToDecrypt = new String(message);
    	String secret = new String(secret_key);
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            String dec = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            return dec.getBytes();
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        
        return null;
    }
}