package q4;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;

import q1.KeyManager;

public class Q4 {
	private static SecretKey key;
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

		int port = 34000;
		
		// Generate a HmacMD5 secret key, Base64 encode it and store it in “data/secretKey”
		generateAndStoreKey();

		// Instantiate a String challenge and calculate its HMAC
		String challenge = "Something Interesting";
		byte[] hmac = getHMac(challenge);
		
		try(ServerSocket server = new ServerSocket(port)){
			System.out.println("Server is listening on port " + port);

			while(true) {
				Socket serverSocket = server.accept();

                DataInputStream dis = new DataInputStream(serverSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(serverSocket.getOutputStream());
				
				// Read name
				String name = readFromStream(dis);
				System.out.println("Hello " + name);
				
				// Send Challenge
				writeToStream(dos, challenge);
				
				// Read HMAC Back
				byte[] foundHmac = getHmacFromBase64(readFromStream(dis));

				// Compare and Report
				writeToStream(dos, 
					Arrays.equals(foundHmac, hmac)
						? "success"
						: "failure"
					);
				
				System.out.println("closed server");
				serverSocket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] getHmacFromBase64(String encodedBase64) {
		return Base64.getDecoder().decode(encodedBase64);
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

	public static byte[] getHMac(String message) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(key);
		return mac.doFinal(message.getBytes());
	}
	
	public static void generateAndStoreKey() throws IOException {
		key = new KeyManager().getKey();
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("Key Write: " + base64Key);
		writeToFile("data/secretKey", base64Key);
	}

	public static void writeToFile(String file, String message) throws IOException {
		BufferedWriter fout = new BufferedWriter(new FileWriter(file));
		fout.write(message);
		fout.close();
	}
}
