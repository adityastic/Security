
import java.security.NoSuchAlgorithmException;

public class Q1 {
	
	public static void main(String[] args) {
		String s1 = "aditya";
		String s2 = "adityg";
		
		try {
			SHAHelper sha = new SHAHelper()
					.generateDigestedMessage(s1)
					.generateBase64Encoding();
				
			System.out.println("For String 'aditya' - " + sha.getBase64() + " with lenght: " + sha.getBase64().length());

			SHAHelper sha2 = new SHAHelper()
					.generateDigestedMessage(s2)
					.generateBase64Encoding();

			System.out.println("For String 'adityg' - " + sha2.getBase64() + " with lenght: " + sha.getBase64().length());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
