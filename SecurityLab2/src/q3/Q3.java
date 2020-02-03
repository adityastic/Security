package q3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Q3 {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		
		// Read Key
		String encodedBase64 = readFromFile("data/secretKey");
		byte[] decodedBase64 = Base64.getDecoder().decode(encodedBase64);	
		SecretKey key = new SecretKeySpec(decodedBase64, 0, decodedBase64.length, "HmacSHA256");
		
	
		// Read Message
		String message = readFromFile("data/sendText.txt");
		
		
		// Calculate and Generate HMAC
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(key);
		
		byte[] genHMAC = mac.doFinal(message.getBytes());
		
		
		// Read HMAC from File
		encodedBase64 = readFromFile("data/hmac");
		byte[] foundHMAC = Base64.getDecoder().decode(encodedBase64);
		
		
		System.out.println("HMac's are " + Arrays.equals(genHMAC, foundHMAC));
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
