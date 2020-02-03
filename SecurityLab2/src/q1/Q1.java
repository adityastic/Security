package q1;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Q1 {	

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		String message = "Something to Send";
		
		HMacManager hmacSender = new HMacManager(message);

		HMacManager hmacRec = new HMacManager(message);
		
		System.out.println("\nAre they equal? " +  hmacRec.equals(hmacSender));
	}
}
