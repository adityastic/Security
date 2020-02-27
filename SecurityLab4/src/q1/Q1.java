package q1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.spec.DHParameterSpec;

public class Q1 {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidParameterSpecException, IOException {
		
		AlgorithmParameterGenerator param = AlgorithmParameterGenerator.getInstance("DH");
		param.init(1024);
		
		DHParameterSpec dhSpec = param.generateParameters().getParameterSpec(DHParameterSpec.class);
		
		String result = dhSpec.getP() + "," + dhSpec.getG() + "," + dhSpec.getL();
		
		System.out.println(result);
		
		writeToFile("data/dhParams", result);
	}

	public static void writeToFile(String file, String message) throws IOException {
		BufferedWriter fout = new BufferedWriter(new FileWriter(file));
		fout.write(message);
		fout.close();
	}
}
