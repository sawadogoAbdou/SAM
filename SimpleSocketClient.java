import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
 
/**
 *

 * @author Abdou Sawadogo CN IT-Telecom
 *
 */
public class SimpleSocketClient
{
 
  // call our constructor to start the program
	
	

 
  public Socket  getsocket()
  {
	Socket socket =null;  
    String testServerName = "localhost";
    int port = 8080;
    try
    {
      // open a socket
     socket = openSocket(testServerName, port);
             
      // write-to, and read-from the socket.
      // in this case just write a simple command to a web server.
     // String result = writeToAndReadFromSocket(socket, "New Alarm");
      
     
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return socket ;
  }
   
  private void  writeToAndReadFromSocket(Socket socket, String writeTo) throws Exception
  {
    try
    {
      // write text to the socket
     System.out.println("Sending request to Socket Server: "+writeTo);
      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      bufferedWriter.write(writeTo);
      bufferedWriter.flush();
      
    //   ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      
    //  oos.writeObject(writeTo);
     // oos.flush();
      //oos.writeObject("\n.\n");
      
       
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
      throw e;
    }
  }
   
  /**
   * Open a socket connection to the given server on the given port.
   * This method currently sets the socket timeout value to 10 seconds.
   * (A second version of this method could allow the user to specify this timeout.)
   */
  private Socket openSocket(String server, int port) throws Exception
  {
    Socket socket;
     
    // create a socket with a timeout
    try
    {
      InetAddress inteAddress = InetAddress.getByName(server);
      SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);
   
      // create a socket
      socket = new Socket();
   
      // this method will block no more than timeout ms.
      int timeoutInMs = 10*1000;   // 10 seconds
      socket.connect(socketAddress, timeoutInMs);
       
      return socket;
    } 
    catch (SocketTimeoutException ste) 
    {
      System.err.println("Timed out waiting for the socket.");
      ste.printStackTrace();
      throw ste;
    }
  }
 
}