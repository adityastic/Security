package q5;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import q1.KeyManager;

public class Q5 {

	private static SecretKey key;
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

		int port = 34000;

		// Read Key
		readAndStoreKey();
		
		try(Socket clientSocket = new Socket("localhost", port)){
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

			// Send a name
			writeToStream(dos, "Aditya");
			
			// Read the challenge string back from the server
			String challenge = readFromStream(dis);
			
			// HMAC it using the secret key.
			byte[] hmac = getHMac(challenge);
			
			// Send the HMAC to the server
			writeToStream(dos, getBase64FromHmac(hmac));
			
			// Read the reply and print it to the screen
			System.out.println("Reply from server:" + readFromStream(dis));
			
			System.out.println("closed client");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getBase64FromHmac(byte[] hmac) {
		SecretKey key = new KeyManager().getKey();
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	private static void readAndStoreKey() throws IOException {
		byte[] decodedBase64 = Base64.getDecoder().decode(readFromFile("data/secretKey"));
		key = new SecretKeySpec(decodedBase64, 0, decodedBase64.length, "HmacSHA256");
	}

	public static byte[] getHMac(String message) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(key);
		return mac.doFinal(message.getBytes());
	}
	
	private static String readFromStream(DataInputStream br) throws IOException {
		String found = br.readUTF();
		System.out.println("Read: " + found);
		return found;
	}

	private static void writeToStream(DataOutputStream bw, String message) throws IOException {
		System.out.println("Wrote: " + message);
		bw.writeUTF(message);
	}

	private static String readFromFile(String fileName) throws IOException {
		String output = "";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		String line = "";
		while ((line = br.readLine()) != null) {
			output += line;
		}
		br.close();	
		System.out.println("Key Read: " + output);
		return output;
	}
}
