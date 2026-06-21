package hashcrack_mt;

public class CandidateGenerator {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALPHABET.length();           
    
    public static long totalWords(int maxLen) {
        long total = 0, pow = 1;
        for (int len = 1; len <= maxLen; len++) {
            pow *= BASE;                                  
            total += pow;                             
        }
        return total;
    }

    public static String wordAt(long i,int length) {
        var sb = new StringBuilder(length);
        long n = i;                              
        for (int k = 0; k < length; k++) {  
            sb.append(ALPHABET.charAt((int) (n % BASE)));
            n /= BASE;
        }
        return sb.reverse().toString();             
    }
}
