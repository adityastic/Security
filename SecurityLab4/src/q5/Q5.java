package q5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Q5 {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidKeyException {
		int port = 34000;

		ServerSocket server = new ServerSocket(port);
		System.out.println("Server is listening on port " + port);

		while(true) {

			Socket serverSocket = server.accept();

			OutputStream o = serverSocket.getOutputStream();
			PrintWriter p = new PrintWriter(o);
			InputStream in = serverSocket.getInputStream();
			Scanner r = new Scanner(in);


			serverFunction(p,r);


			System.out.println("closed server");
			serverSocket.close();
		}
	}

	private static void serverFunction(PrintWriter p, Scanner r) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException, InvalidKeySpecException, InvalidKeyException {

		// Read DH parameters
		String dhParam= r.nextLine();

		// Generate own public key private key pair
		String[] dhParams = dhParam.split(",");

		DHParameterSpec dhSpec = new DHParameterSpec(
				new BigInteger(dhParams[0]), 
				new BigInteger(dhParams[1]),
				Integer.parseInt(dhParams[2])
				);

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
		keyPairGen.initialize(dhSpec);
		
		KeyPair keyPair = keyPairGen.generateKeyPair();
		
		// Read the clients public key and Base64 decode it
		String input = r.nextLine();
		byte[] serverPublicKeyBytes = Base64.getDecoder().decode(input);
		
		// convert it to a public key object
		KeyFactory keyFactory = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec1 = new X509EncodedKeySpec(serverPublicKeyBytes);
		PublicKey clientPublicKey = keyFactory.generatePublic(x509KeySpec1);

		// Send own public key as Base64 encoded string
		String publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
		p.println(publicKeyBase64);
		p.flush();

		// Generate a symmetric key using own private key and servers public key.
		KeyAgreement ka = KeyAgreement.getInstance("DH");
		ka.init(keyPair.getPrivate());
		ka.doPhase(clientPublicKey, true);
		// SecretKey secretKey1 = ka.generateSecret(algorithm);
		byte[] rawValue = ka.generateSecret();
		SecretKey secretKey = new SecretKeySpec(rawValue, 0, 16, "AES");

		// Print out symmetric key (Base64 encoded)
		String encodedKey = Base64.getEncoder().
				encodeToString(secretKey.getEncoded());
		System.out.println("Symmetric Key Server: " + encodedKey);

	}
}
