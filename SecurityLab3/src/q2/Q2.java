package q2;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import q1.KeyManager;

public class Q2 {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKey key = new KeyManager().getDESKey();
		
		String message = "hello";
		
		DES aes = new DES();
		
		String encrypted = aes.encrypt(message, key);
		System.out.println("Encrypted: " + encrypted);

		String decrypted = aes.decrypt(encrypted, key);
		System.out.println("Decrypted: " + decrypted);
	}
}
