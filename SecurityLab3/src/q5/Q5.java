package q5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import q3.Employee;

public class Q5 {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, IOException, InvalidKeyException, ClassNotFoundException, BadPaddingException {
		// Read Key
		String encodedBase64 = readFromFile("data/secretKey");
		byte[] decodedBase64 = Base64.getDecoder().decode(encodedBase64);	
		SecretKey key = new SecretKeySpec(decodedBase64, 0, decodedBase64.length, "AES");	
				
		SealedObject sealedObject = (SealedObject) readObjectFromFile("data/sealedObject.dat");

		Cipher decCipher = Cipher.getInstance("AES");
		decCipher.init(Cipher.DECRYPT_MODE, key);
		Employee empFound = (Employee) sealedObject.getObject(decCipher);
		
		System.out.println("Name: " + empFound.name +
				"\nAddress: " + empFound.address + 
				"\nTelNo: " + empFound.telno);
		
	}

	public static Object readObjectFromFile(String filepath) {
		 
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filepath));
            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

	private static String readFromFile(String fileName) throws IOException {
		String output = "";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		String line = "";
		while ((line = br.readLine()) != null) {
			output += line;
		}
		br.close();
		return output;
	}
}
