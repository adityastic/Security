package q1;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyManager {
	static SecretKey currentKey;
	
	public SecretKey getKey() {
		if(currentKey == null) {
			try {
				currentKey = KeyGenerator.getInstance("HmacSHA256").generateKey();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return currentKey;
	}
}
