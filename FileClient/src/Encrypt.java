import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Scanner;

public class Encrypt {

	//Written by Isaiah Mon Desir
	private static String oIn;
	private static String oBin;
	private static String eIn;
	private static String eBin;
	private static String dKey;
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	//Text to binary ~~~~ISSUE~~~~
	public static String toText(String t) throws IOException
	{
		String s2 = "";   
		char nextChar;

		for(int i = 0; i <= t.length()-8; i += 9) //this is a little tricky.  we want [0, 7], [9, 16], etc (increment index by 9 if bytes are space-delimited)
		{
		     nextChar = (char)Integer.parseInt(t.substring(i, i+8), 2);
		     s2 += nextChar;
		}

		return s2;
	}
	
	//Binary to text
	public static String toBinary(String b)
	{
		String bin = "";
		  byte[] bytes = b.getBytes();
		  StringBuilder binary = new StringBuilder();
		  for (byte a : bytes)
		  {
		     int val = a;
		     for (int i = 0; i < 8; i++)
		     {
		        binary.append((val & 128) == 0 ? 0 : 1);
		        val <<= 1;
		     }
		     binary.append(" ");
		  }
		  //System.out.println("'" + b + "' to binary: " + binary);
		  bin  = binary.toString();
		  return bin;
	}
	
	//Creates an encrypted binary through comparison of binary values of key an original text
	public static String encrBin(String o, String d)
	{
		String ref = d;
		d = toBinary(d);
		String eB ="";
		for(int i = 0; i < o.length(); i++){
			if((o.substring(i, i+1).compareTo(d.substring(i, i+1)) == 0) && (o.substring(i, i+1).compareTo(" ") != 0))
				eB += "0";
			else if((o.substring(i, i+1).compareTo(d.substring(i, i+1)) != 0) && (o.substring(i, i+1).compareTo(" ") != 0))
				eB += "1";
			else
				eB += " ";
		}
		//System.out.println("New binary: " + eB);
		return eB;
	}
	
	//Generates random decryption key of length o.length()
	public static String generateKey(String o)
	{
		String dK = "";
		int len = o.length();
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		dK = sb.toString();
		//System.out.println(dK);
		return dK;	
	}
	
	
	public static void main (String [] args) throws IOException
	{
		Scanner scan = new Scanner (System.in);
		oIn = args[0];//scan.nextLine();
		
		//Translate oIn into Binary form
		oBin = toBinary(oIn);
		//Create a random string of size oIn.length()-1
		dKey = generateKey(oIn);
		//Create encrypted binary
		eBin = encrBin(oBin, dKey);
		//Turn encrypted binary to text
		eIn = toText(eBin);
		System.out.println(eIn);
		
		WriteToFile file = new WriteToFile(eIn + ">dk< " + dKey);
		file.write();
	}
}
