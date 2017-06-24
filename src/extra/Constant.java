package extra;

import cryptography.AsymmetricKeyPair;
import middleware.Encryptor;

public final class Constant
{
	private static final Encryptor encryptor = new Encryptor();
	
    public static final String NAMING_SERVER_HOST = "localhost";
    public static final Integer NAMING_SERVER_PORT = 2000;
    private static final AsymmetricKeyPair NAMING_SERVER_KEYPAIR = encryptor.GenerateAsymmetricKeyPair();
    public static final byte[] NAMING_SERVER_PUBLIC_KEY = NAMING_SERVER_KEYPAIR.GetPublicKey();
    public static final byte[] NAMING_SERVER_PRIVATE_KEY = NAMING_SERVER_KEYPAIR.GetPrivateKey();
    
    public static final String HOSPITAL_SERVER_HOST = "localhost";
    public static final Integer HOSPITAL_SERVER_PORT = 2001;
    private static final AsymmetricKeyPair HOSPITAL_SERVER_KEYPAIR = encryptor.GenerateAsymmetricKeyPair();
    public static final byte[] HOSPITAL_SERVER_PUBLIC_KEY = HOSPITAL_SERVER_KEYPAIR.GetPublicKey();
    public static final byte[] HOSPITAL_SERVER_PRIVATE_KEY = HOSPITAL_SERVER_KEYPAIR.GetPrivateKey();
}
