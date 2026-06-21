package hashcrack_mt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

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
	
	private static long[][] zones(long max,int threadNumber){
		long init = max / threadNumber;
		long[][] res = new long[threadNumber][2];
		res[0][0]=0;
		res[0][1]=init;
		for(int i = 1; i < threadNumber ;i++) {
			res[i][0]=res[i-1][1]+1;
			res[i][1]=res[i][0]+init;
		}
		return res;
	}
	
	
	public static String MultiThreadStyle(String hash , int length, String algo) throws NoSuchAlgorithmException, InterruptedException{
		String[] res= {null};
		long candidatsNumbers = CandidateGenerator.totalWords(length);
		byte[] target = HexFormat.of().parseHex(hash);
		ArrayList<Thread> threads = new ArrayList<Thread>();
		AtomicBoolean exit = new AtomicBoolean(false);
		long[][] zone= zones(candidatsNumbers,length);
		for(int i = 0 ; i < length;i++) {
			long init = zone[i][0];
			long end= zone[i][1];
			Runnable r= ()->{
				try {
					MessageDigest md = MessageDigest.getInstance(algo);

					for(long k = init ; k < end ;k++) {
						String candidat = CandidateGenerator.wordAt(k,length);
						md.update(candidat.getBytes(StandardCharsets.UTF_8));
						
						byte[] candidatbytes = md.digest();
						if(Arrays.equals(target, candidatbytes)) {
							res[0]=candidat;
							exit.set(true);
						}
						if(exit.get())break;
					}		
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					throw new AssertionError(e);
				}
			};
			
			Thread th= Thread.ofPlatform().start(r);
			threads.add(th);
		}
		
		for (Thread thread : threads) {
			thread.join();
		}
		
		return res[0];
	}
	
}
