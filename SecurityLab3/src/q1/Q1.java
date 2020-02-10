package q1;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Q1 {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKey key = new KeyManager().getAESKey();
		
		String message = "hello";
		
		AES aes = new AES();
		
		String encrypted = aes.encrypt(message, key);
		System.out.println("Encrypted: " + encrypted);

		String decrypted = aes.decrypt(encrypted, key);
		System.out.println("Decrypted: " + decrypted);
	}
}
