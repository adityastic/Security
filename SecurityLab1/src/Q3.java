import org.apache.commons.codec.digest.DigestUtils;

public class Q3 {

	public static void main(String[] args) {

		System.out.println("For String 'aditya' with SHA - " +DigestUtils.sha256("aditya"));
		System.out.println("For String 'adityg' with SHA - " +DigestUtils.sha256("adityg"));
		System.out.println("For String 'aditya' with MD5 - " +DigestUtils.md5("aditya"));
		System.out.println("For String 'adityg' with MD5 - " +DigestUtils.md5("adityg"));

	}
}
