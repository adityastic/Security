
public class Q2 {
	
	public static void main(String[] args) {
		
		try {
			SHAHelper sha = new SHAHelper()
					.generateDigestedMessageFromFile("test.txt")
					.generateBase64Encoding();

			System.out.println("For file test.txt - "+sha.getBase64());

			SHAHelper sha2 = new SHAHelper()
					.generateDigestedMessageFromFile("test2.txt")
					.generateBase64Encoding();
				
			System.out.println("For file test2.txt - "+sha2.getBase64());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
