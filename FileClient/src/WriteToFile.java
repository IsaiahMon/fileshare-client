import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

	private static final String FILENAME = "/Users/isaiahmondesir/Downloads/encrypted.txt";
	private static String input;
	
	public WriteToFile (String in)
	{
		input = in;
	}
	public static void write() {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			String content = input;
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}