package q4;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import q1.KeyManager;
import q3.Employee;

public class Q4 {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, IOException, InvalidKeyException, ClassNotFoundException, BadPaddingException {
		SecretKey key = new KeyManager().getAESKey();
		Cipher encCipher = Cipher.getInstance("AES");
		encCipher.init(Cipher.ENCRYPT_MODE, key);
        
		Employee emp = new Employee();
		emp.name = "Aditya";
		emp.address = "Athlone";
		emp.telno = "0894912645";

		SealedObject sealedObject = new SealedObject(emp, encCipher);

		// Save Encrypted Object
		writeObjectToFile(sealedObject, "data/sealedObject.dat");

		// Save Key
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		writeToFile("data/secretKey", base64Key);
	}

	public static void writeObjectToFile(Object serObj, String filePath) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath));
            objectOut.writeObject(serObj);
            objectOut.close();
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	public static void writeToFile(String file, String message) throws IOException {
		BufferedWriter fout = new BufferedWriter(new FileWriter(file));
		fout.write(message);
		fout.close();
	}
}
