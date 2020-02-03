package q4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
				Socket socket = server.accept();
				
				// Read name
				String name = readFromStream(socket.getInputStream());
				System.out.println("Hello " + name);
				
				// Send Challenge
				writeToStream(socket.getOutputStream(), challenge);
				
				// Read HMAC Back
				byte[] foundHmac = getHmacFromBase64(readFromStream(socket.getInputStream()));
				
				// Compare and Report
				writeToStream(socket.getOutputStream(), 
					Arrays.equals(foundHmac, hmac)
						? "success"
						: "failure"
					);
				
				socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] getHmacFromBase64(String encodedBase64) {
		return Base64.getDecoder().decode(encodedBase64);
	}

	private static String readFromStream(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String out = "", line = "";
		while((line = br.readLine()) != null)
			out += line;
		return out;
	}
	
	private static void writeToStream(OutputStream outputStream, String message) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
		bw.write(message);
		bw.close();
	}

	public static byte[] getHMac(String message) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(key);
		return mac.doFinal(message.getBytes());
	}
	
	public static void generateAndStoreKey() throws IOException {
		key = new KeyManager().getKey();
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
		writeToFile("data/secretKey", base64Key);
	}

	public static void writeToFile(String file, String message) throws IOException {
		BufferedWriter fout = new BufferedWriter(new FileWriter(file));
		fout.write(message);
		fout.close();
	}
}
