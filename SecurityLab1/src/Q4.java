import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class Q4 {

	public static  byte[] getFileHash(String fileName) throws FileNotFoundException, IOException {
		return DigestUtils.md5(new FileInputStream("test.txt"));
	}
	
	public static void main(String[] args) {

		try {

			System.out.println("For file test.txt - " + getFileHash("test.txt"));
			
			System.out.println("For file test2.txt - " + getFileHash("test2.txt"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
