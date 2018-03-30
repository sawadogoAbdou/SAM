import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class readSmFile {


	private static void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
	public static void main(String args[]) throws IOException
	{
		
		File inputFile  = new File("\\"+"\\"+"Mtl-hq-f07"+"\\"+"hq5aw1"+"\\"+"ITIL"+"\\"+"Application"+"\\"+"Extracts"+"\\"+"Export_cncircuits.csv"); ;
    	File outputFilexsl  = new File("C:/Users/xt20726/Documents/Automation/SM/Export_cncircuits.csv") ;
    	System.out.println("Input File"+inputFile);
    	System.out.println("Onput File"+outputFilexsl);
    	copyFileUsingStream(inputFile,outputFilexsl);
	}

}
