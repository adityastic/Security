package q2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import javax.crypto.SecretKey;

import q1.HMacManager;
import q1.KeyManager;

public class Q2 {
	public static void main(String[] args) throws IOException {
		
		// Save Key
		SecretKey key = new KeyManager().getKey();
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		
		writeToFile("data/secretKey", base64Key);
		

		// Save Message
		String message = "Something to Send";

		writeToFile("data/sendText.txt", message);
		
		// Save HMac

		HMacManager hmacSender = new HMacManager(message);

		writeToFile("data/hmac", hmacSender.getBase64());
	}
	
	public static void writeToFile(String file, String message) throws IOException {
		BufferedWriter fout = new BufferedWriter(new FileWriter(file));
		fout.write(message);
		fout.close();
	}
}
