package q1;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class Q1 {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
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
		byte[] sendText = text.getBytes();
		dsa.update(sendText);
		byte[] sig = dsa.sign();
		
		dsa.initVerify(publicKey);
		dsa.update(sendText);
		System.out.println("Signature verifies: " + dsa.verify(sig));
	}
}
