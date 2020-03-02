package q2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Q2 {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
		// Write to File
		writeOperations();
		
		// Read and Verify
		readAndVerify();
	}

	private static void readAndVerify() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
		String text = readFromFile("data/message");

		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		X509EncodedKeySpec x509KeySpec1 = new X509EncodedKeySpec(Base64.getDecoder().decode(readFromFile("data/publicKey")));
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec1);


		Signature dsa = Signature.getInstance("SHA1withDSA");
		dsa.initVerify(publicKey);
		dsa.update(text.getBytes());
		
		System.out.println("Signature Verification: " + dsa.verify(Base64.getDecoder().decode(readFromFile("data/digSig"))));
	}

	private static void writeOperations() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");	
		keyGen.initialize(1024, new SecureRandom() {{
			setSeed(2345);
		}});
		KeyPair pair = keyGen.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();

		String text = "Original Message";
		
		Signature dsa = Signature.getInstance("SHA1withDSA");
		dsa.initSign(privateKey);
		dsa.update(text.getBytes());
		byte[] sig = dsa.sign();
		
		writeToFile("data/message", text);
		writeToFile("data/publicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		writeToFile("data/digSig", Base64.getEncoder().encodeToString(sig));
	}
	
	public static void writeToFile(String file, String message) throws IOException {
		BufferedWriter fout = new BufferedWriter(new FileWriter(file));
		fout.write(message);
		fout.close();
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
