/* Call SOAP URL and send the Request XML and Get Response XML back */
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
 
public class SoapXml {
 
    public  void sendSoapRequest(String SOAPUrl) throws Exception {
        //use this if you need proxy to connect
   //     Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("YOUR PROXY", PORT NUMBER));
        //String SOAPUrl = "http://10.247.198.10:8080/xmlapi/invoke";
        String xmlFile2Send = "C:/Users/xt20726/Documents/SAM/alarms/soap_ack_v2.xml";
    	//String xmlFile2Send = "C:/Users/xt20726/Documents/SAM/alarms/rep2.xml";
        String responseFileName = "C:/Users/xt20726/Documents/SAM/xmloutput/response.xml";
 
        // Create the connection with http
        URL url = new URL(SOAPUrl);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        FileInputStream fin = new FileInputStream(xmlFile2Send);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
 
        copy(fin, bout);
        fin.close();
 
        byte[] b = bout.toByteArray();
        StringBuffer buf=new StringBuffer();
        String s= new String(b);
 
        //replacing a sample value in Request XML
       // s=s.replaceAll("VALUE", value);
        b=s.getBytes();
 
        // Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "application/soap+xml ; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", "");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
 
        // send the XML that was read in to b.
        OutputStream out = httpConn.getOutputStream();
        out.write(b);
        out.close();
 
        // Read the response.
        httpConn.connect();
        System.out.println("http connection status :"+ httpConn.getResponseMessage());
        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        FileOutputStream fos=new FileOutputStream(responseFileName);
        copy(httpConn.getInputStream(),fos);
        in.close();
    }
 
    public static void copy(InputStream in, OutputStream out)
            throws IOException {
 
        synchronized (in) {
            synchronized (out) {
                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1)
                        break;
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }




    public static void main(String args[]) throws Throwable {
        
    	SoapXml SoapXml = new SoapXml();
    	SoapXml.sendSoapRequest("http://10.247.198.12:8080/xmlapi/invoke");
    	//SoapXml.sendSoapRequest("http://104.221.6.148:8080");
    }










}
