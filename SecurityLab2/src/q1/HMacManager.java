package q1;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;

public class HMacManager {
	private Mac currentMac;
	private String message;
	
	public HMacManager(String message) {
		this.message = message;
		try {
			this.currentMac = Mac.getInstance("HmacSHA256");
			this.currentMac.init(new KeyManager().getKey());
			
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	public byte[] getHMac() {
		return this.currentMac.doFinal(message.getBytes());
	}
	
	public String getBase64() {
		return Base64.getEncoder().encodeToString(getHMac());
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof HMacManager)
			? Arrays.equals(((HMacManager) obj).getHMac(), getHMac())
			: false;
	}
}
