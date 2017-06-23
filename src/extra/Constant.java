package extra;

import java.security.KeyPair;

import middleware.Encryptor;

public final class Constant
{
    public static final String NAMING_SERVER_HOST = "localhost";
    public static final Integer NAMING_SERVER_PORT = 2000;
    public static final KeyPair NAMING_SERVER_KEYPAIR = Encryptor.GenerateAsymmetricKeyPair();
    public static final byte[] NAMING_SERVER_PUBLIC_KEY = NAMING_SERVER_KEYPAIR.getPublic().getEncoded();
    public static final byte[] NAMING_SERVER_PRIVATE_KEY = NAMING_SERVER_KEYPAIR.getPrivate().getEncoded();
    
    public static final String HOSPITAL_SERVER_HOST = "localhost";
    public static final Integer HOSPITAL_SERVER_PORT = 2001;
    public static final KeyPair HOSPITAL_SERVER_KEYPAIR = Encryptor.GenerateAsymmetricKeyPair();
    public static final byte[] HOSPITAL_SERVER_PUBLIC_KEY = HOSPITAL_SERVER_KEYPAIR.getPublic().getEncoded();
    public static final byte[] HOSPITAL_SERVER_PRIVATE_KEY = HOSPITAL_SERVER_KEYPAIR.getPrivate().getEncoded();
    
    public static final boolean USE_CRYPTOGRAPHY = false;
}
