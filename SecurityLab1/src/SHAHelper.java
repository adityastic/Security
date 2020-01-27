import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHAHelper {
	private byte[] encodedBytes;
	private String encodedBase64;

	public SHAHelper generateBase64Encoding() {
		this.encodedBase64 = Base64.getEncoder().encodeToString(this.encodedBytes);
		return this;
	}

	public SHAHelper generateDigestedMessage(String str) throws NoSuchAlgorithmException {
		this.encodedBytes = MessageDigest.getInstance("SHA-256").digest(str.getBytes());
		return this;
	}

	public SHAHelper generateDigestedMessageFromFile(String filename) throws Exception {
		InputStream fis =  new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("SHA-256");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		this.encodedBytes = complete.digest();
		return this;
	}

	public String getBase64() {
		return this.encodedBase64;
	}
}
