package hashcrack_mt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Objects;

public class Hashcrack {
	private static void checklength(int l) {
		if(l < 0)throw new IllegalArgumentException();
	}
	
	public static String MonoStyle(String hash , int length) throws NoSuchAlgorithmException {
		checklength(length);
		Objects.requireNonNull(hash);
		
		long candidatsNumbers = CandidateGenerator.totalWords(length);
		//HashSet<String> checked = new HashSet<>();
		byte[] target = HexFormat.of().parseHex(hash);
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		
		
		for(int i = 0 ; i < candidatsNumbers ;i++) {
			String candidat = CandidateGenerator.wordAt(i,length);
			//if(checked.contains(candidat))continue;
			md.update(candidat.getBytes(StandardCharsets.UTF_8));
			
			byte[] candidatbytes = md.digest();
			if(Arrays.equals(target, candidatbytes))return candidat;
			//checked.add(candidat);
		}		
		return null;
	}
}
