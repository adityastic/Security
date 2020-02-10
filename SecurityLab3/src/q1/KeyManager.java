package q1;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyManager {
	public SecretKey getAESKey() throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance("AES").generateKey();
	}

	public SecretKey getDESKey() throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance("DES").generateKey();
	}
}
