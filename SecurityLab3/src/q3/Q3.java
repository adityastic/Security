package q3;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import q1.KeyManager;

public class Q3 {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, IOException, InvalidKeyException, ClassNotFoundException, BadPaddingException {
		SecretKey key = new KeyManager().getAESKey();
		Cipher encCipher = Cipher.getInstance("AES");
		encCipher.init(Cipher.ENCRYPT_MODE, key);
        
		Employee emp = new Employee();
		emp.name = "Aditya";
		emp.address = "Athlone";
		emp.telno = "0894912645";

		SealedObject sealedObject = new SealedObject(emp, encCipher);

		Cipher decCipher = Cipher.getInstance("AES");
		decCipher.init(Cipher.DECRYPT_MODE, key);
		Employee empFound = (Employee) sealedObject.getObject(decCipher);
		
		System.out.println("Name: " + empFound.name +
				"\nAddress: " + empFound.address + 
				"\nTelNo: " + empFound.telno);
	}
}
