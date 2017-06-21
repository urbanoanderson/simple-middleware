package extra;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import middleware.Encryptor;

public final class Constant
{
    public static final String NAMING_SERVER_HOST = "localhost";
    public static final int NAMING_SERVER_PORT = 2000;
    public static final KeyPair NAMING_SERVER_KEYPAIR = Encryptor.GenerateKeyPair();
    public static final PublicKey NAMING_SERVER_PUBLIC_KEY = NAMING_SERVER_KEYPAIR.getPublic();
    public static final PrivateKey NAMING_SERVER_PRIVATE_KEY = NAMING_SERVER_KEYPAIR.getPrivate();
    
    public static final String HOSPITAL_SERVER_HOST = "localhost";
    public static final int HOSPITAL_SERVER_PORT = 2001;
    public static final KeyPair HOSPITAL_SERVER_KEYPAIR = Encryptor.GenerateKeyPair();
    public static final PublicKey HOSPITAL_SERVER_PUBLIC_KEY = HOSPITAL_SERVER_KEYPAIR.getPublic();
    public static final PrivateKey HOSPITAL_SERVER_PRIVATE_KEY = HOSPITAL_SERVER_KEYPAIR.getPrivate();
}
