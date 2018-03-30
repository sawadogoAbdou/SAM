import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server
 * @author Abdou Sawadogo
 *
 */
public class SocketServer {
    
	static final int PORT = 8080;

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Waiting Alarm from Client: ");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new threa for a client
            
            new EchoThread(socket).start();
        }
    }
}