package cryptography;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import middleware.Marshaller;
 
public class RSA extends Asymmetric
{    
    private static int bitlength = 1024;
 
    public AsymmetricKeyPair GenerateKeyPair()
    {
        Random r = new Random();
        BigInteger p = BigInteger.probablePrime(bitlength, r);
        BigInteger q = BigInteger.probablePrime(bitlength, r);
        BigInteger N = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
            e.add(BigInteger.ONE);
        BigInteger d = e.modInverse(phi);
        
        Marshaller marshaller = new Marshaller();
        ArrayList<BigInteger> public_key_obj = new ArrayList<BigInteger>();
    	public_key_obj.add(e);
    	public_key_obj.add(N);
    	byte[] public_key = marshaller.Marshall(public_key_obj);
    	
    	ArrayList<BigInteger> private_key_obj = new ArrayList<BigInteger>();
    	private_key_obj.add(d);
    	private_key_obj.add(N);
    	byte[] private_key = marshaller.Marshall(private_key_obj);
    	
    	return new AsymmetricKeyPair(public_key, private_key);
    }
    
    public byte[] Encrypt(byte[] message, byte[] public_key)
    {
    	Marshaller marshaller = new Marshaller();
    	@SuppressWarnings("unchecked")
		ArrayList<BigInteger> key = (ArrayList<BigInteger>) marshaller.Unmarshall(public_key);
    	BigInteger e = key.get(0);
    	BigInteger N = key.get(1);
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }
    
    public byte[] Decrypt(byte[] message, byte[] private_key)
    {
    	Marshaller marshaller = new Marshaller();
    	@SuppressWarnings("unchecked")
		ArrayList<BigInteger> key = (ArrayList<BigInteger>) marshaller.Unmarshall(private_key);
    	BigInteger d = key.get(0);
    	BigInteger N = key.get(1);
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
}
