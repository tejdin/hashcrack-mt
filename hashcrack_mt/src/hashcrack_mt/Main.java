package hashcrack_mt;

import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		String res = Hashcrack.MonoStyle("69c459dd76c6198f72f0c20ddd3c9447", 5);
		IO.print(res);
		

	}

}
