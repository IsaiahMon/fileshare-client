import java.io.*;

public class Decrypt {
	
	private static String message = "";
	
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
	public static String encrBin(String o, String d)
	{
		String ref = d;
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
	
	public static String DCrypt(String message) throws IOException
	{
		String oMess = "";
		
		String dKey = message.substring(message.indexOf(">dk<")+5);
		String enM = message.substring(0, message.indexOf(">dk<"));
		
		String dBin = toBinary(enM);
		String keyBin = toBinary(dKey);
		
		String oBin = encrBin(dBin, keyBin);
		
		oMess = toText(oBin);
		System.out.println(oMess);
		return oMess;
	}
	public static void main(String [] args) throws IOException {
		// The name of the file to open.
			String dir = "/Users/isaiahmondesir/Downloads/";
	       dir += args[0];
	        // This will reference one line at a time
	        String line = null;
	        
	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(dir);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	            	message += line;
	                //System.out.println(line);
	            }
	            
	            

	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                dir + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + dir + "'");                  
	            // Or we could just do this: 
	            // ex.printStackTrace();
	        }
	        DCrypt(message);
	    }
	}
