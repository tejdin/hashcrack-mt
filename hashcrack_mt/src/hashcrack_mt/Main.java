package hashcrack_mt;

import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
		// TODO Auto-generated method stub
		String res = Hashcrack.MultiThreadStyle("de9b9ed78d7e2e1dceeffee780e2f919", 10,"MD5");
		IO.print(res);
		

	}

}
