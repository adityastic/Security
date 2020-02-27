package q3;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.spec.DHParameterSpec;


public class Q3 {

	public static void generateKeyPairFor(String name) throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		String[] dhParams = readFromFile("data/dhParams").split(",");

		DHParameterSpec dhSpec = new DHParameterSpec(
				new BigInteger(dhParams[0]), 
				new BigInteger(dhParams[1]),
				Integer.parseInt(dhParams[2])
				);

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
		keyPairGen.initialize(dhSpec);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		writeObjToFile("data/"+name+"Public",keyPair.getPublic());
		writeObjToFile("data/"+name+"Private",keyPair.getPrivate());

	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidParameterSpecException, IOException, InvalidAlgorithmParameterException {
		
		generateKeyPairFor("Aditya");
		generateKeyPairFor("Siddhart");
	}

	private static String readFromFile(String fileName) throws IOException {
		String output = "";
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String line = "";
		while ((line = br.readLine()) != null) {
			output += line;
		}
		br.close();	
		//		System.out.println("Key Read: " + output);
		return output;
	}

	public static void writeObjToFile(String file, Object serObj) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
