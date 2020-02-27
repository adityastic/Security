package q4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AlgorithmParameterGenerator;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
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

public class Q4 {
	public static void main(String[] args) throws UnknownHostException, IOException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {

		int port = 34000;
		Socket clientSocket = new Socket("localhost", port);

		OutputStream o = clientSocket.getOutputStream();
		PrintWriter p = new PrintWriter(o);
		InputStream in = clientSocket.getInputStream();
		Scanner r = new Scanner(in);

		clientFunction(p,r);


		System.out.println("closed client");

	}

	private static void clientFunction(PrintWriter p, Scanner r) throws NoSuchAlgorithmException, InvalidParameterSpecException, IOException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidKeyException {


		// get DH params as string
		String params = generateDHParams();

		// send DH params as string
		p.println(params);
		p.flush();

		// create DHParameterSpec object
		String[] values = params.split(",");
		BigInteger pp = new BigInteger(values[0]);
		BigInteger g = new BigInteger(values[1]);
		int l = Integer.parseInt(values[2]);
		DHParameterSpec dhSpec = new DHParameterSpec(pp, g, l);

		// Generate a DH key pair  (using DHParameterSpec object)
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
		keyGen.initialize(dhSpec);
		KeyPair keypair = keyGen.generateKeyPair();
		PrivateKey privateKey = keypair.getPrivate();
		PublicKey publicKey = keypair.getPublic();

		// send own Base64 encoded public key
		String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		p.println(publicKeyBase64);
		p.flush();

		// read Base64 encoded servers public key
		String input = r.nextLine();
		byte[] serverPublicKeyBytes = Base64.getDecoder().decode(input);
		// convert it to a public key object
		KeyFactory keyFactory = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec1 = new X509EncodedKeySpec(serverPublicKeyBytes);
		PublicKey serverPublicKey = keyFactory.generatePublic(x509KeySpec1);

		// generate symmetric key
		KeyAgreement ka = KeyAgreement.getInstance("DH");
		ka.init(privateKey);
		ka.doPhase(serverPublicKey, true);
		// SecretKey secretKey1 = ka.generateSecret(algorithm);
		byte[] rawValue = ka.generateSecret();
		SecretKey secretKey = new SecretKeySpec(rawValue, 0, 16, "AES");

		// Base64 encode the Secret key and print it out
		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		System.out.println("Symmetric Key at Client: " + encodedKey);
	}

	public static String generateDHParams() throws NoSuchAlgorithmException, InvalidParameterSpecException {
		AlgorithmParameterGenerator param = AlgorithmParameterGenerator.getInstance("DH");
		param.init(1024);

		DHParameterSpec dhSpec = param.generateParameters().getParameterSpec(DHParameterSpec.class);

		String result = dhSpec.getP() + "," + dhSpec.getG() + "," + dhSpec.getL();

		return result;
	}
}
