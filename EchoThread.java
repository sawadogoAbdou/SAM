import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        
      //  System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
         String dateformat = dateFormat.format(date);
        

        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        
        //File file = new File("C:/Users/164704/Documents/SAM/alarmsoutput.txt");
        File file = new File("C:/Users/xt20726/Documents/SAM/alarmsoutput.txt");
        BufferedWriter bw = null;
        if (!file.exists()) {
                  	     try {
							file.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                  	  
                   
               	    
       }
        while (true) {
            try {
                line = brinp.readLine();
                if (line !=null){
                	FileWriter fw = null;
					try {
						fw = new FileWriter(file,true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               	    bw = new BufferedWriter(fw);
                System.out.println("Message Received: " + line);
                System.out.println("");
                bw.write (dateformat+" "+"ALARM : "+ line);
                bw.newLine();
                bw.newLine();
                bw.flush();
                bw.close();
                }
                if ((line == null) || line.equalsIgnoreCase("EXIT")) {
                    inp.close();
                	socket.close();
                    return;
                } else {
                   // out.writeBytes(line + "\n\r");
                    //out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
