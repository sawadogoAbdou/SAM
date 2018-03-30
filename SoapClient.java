import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.TextMessage;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SoapClient {
	static Date date = new Date() ;
    static //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
    //Configuration  variables:
    
    
    
    
    
    //End Read configuration variable
    
   /* public static String DataDir = "C:/Users/xt20726/Documents/SAM/xmloutput/";
 //  	 public static String DataDir ="/opt/5620sam/cn/data/";
	//public static String DataDir ="/opt/5620sam/server/clientucmdb/";
	 public static String selfFilename = DataDir+"shelf_"+dateFormat.format(date)+".csv";
	 
	 public static String networkFilename = DataDir+"network_"+dateFormat.format(date)+".csv";
     public static String finalFileName =DataDir+"ucmdFinal_"+dateFormat.format(date)+".csv";
     public static String labpassword="4bc60d71c78fabbe695f06bfabfdf716";
     public static String prodpassword ="eac2e6703cdcd98fa86a4fd71e15e7c5";*/
    
    
     public static  String DataDir =null;
     public static  String prodpassword =null;
     public static  String urlprimary=null;
     public static  String urlstandby =null;
     public static String attachment =null;
     public static String ucmdbfilename=null;
     public static String ucmdbfilenamer =null;
     public static  String selfFilename = null;
	 
	 public static String networkFilename = null;
     public  static String finalFileName = null;
     
     
     
    
     
     
     public static  void readConf(String filename){
    	 
    	 File File = new File(filename);
    	 // Open the file
   	  FileInputStream fstream1 = null;
   	 
   	try {
   		fstream1 = new FileInputStream(File);
   	} catch (FileNotFoundException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
   	  BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
   	
   	  String strLine1 = null;
   	  int comp =0;
   	  try {

   		  while ((strLine1 = br1.readLine()) != null)   {
   	   		String token[] = strLine1.split("=");
			 
			 if (token[0].equals("DataDir")){
				 DataDir = token[1];
				// System.out.println("DataDir:  "+DataDir);
			 }
		  
			 if (token[0].equals("password")){
				 prodpassword = token[1];
				// System.out.println("password:  "+prodpassword);
			 }
		  
		    
			 if (token[0].equals("urlprimary")){
				 urlprimary = token[1];
				// System.out.println("urlprimary:  "+urlprimary);
			 }
		  
			 if (token[0].equals("urlstandby")){
				 urlstandby = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
   		  
			 if (token[0].equals("attachment")){
				 attachment = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
   		  
			 if (token[0].equals("ucmdbfilename")){
				 ucmdbfilename = token[1];
				 ucmdbfilenamer= ucmdbfilename.replaceAll("/+$", "");
				// System.out.println("urlstandby:  "+urlstandby);
			 }
   		  
   		  
   		  }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	  
   	  try {
		br1.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	  try {
		fstream1.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
     }
     
     
    
     
     public static void soapToSam(String url) throws Throwable, SOAPException{
    	 
    	 
    	 try {
    		 
    		 
    		 Date date = new Date() ;
             //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
             File file01 = new File("Shelf_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_"+dateFormat.format(date)+".xml") ;
             File file02 = new File("Network_"+dateFormat.format(date)+".xml") ;
             File file1 = new File(DataDir+file01);
             File file2 = new File(DataDir+file02);
             File file3 = new File(DataDir+file03);
    		 // Create SOAP Connection
             SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
             SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
             
            // System.out.println("SOAP Resp Body :  ");
             
           //  System.out.println("Print File :  "+file2);
             SOAPMessage soapResponse1 = soapConnection.call( network(),url);
             
           //  System.out.println("SOAP Resp Body :  ");
             
           //  System.out.println("Print File :  "+file2);
              printSOAPResponse(soapResponse1, file2);
               
            
              
              SOAPMessage soapResponse2 = soapConnection.call(findShelf(),url);
              
              
              printSOAPResponse(soapResponse2, file1);
             
             
              
               parseMessage(file1);
               parseLocation(file2);
               final_To_ucmdb(networkFilename,selfFilename); 
    		 
    	      
    	    } catch (Exception ex) {
    	     ex.printStackTrace();
    	     System.out.println("Second Connection Failed: " +ex.getCause());
    	     
    	     
    	     
    	     
    	    
    	    
    	    
    	    }
        }
     
     
     
     /**
     * Starting point for  - SOAP Client Testing
     * @return 
     */
	  public   static void main(String args[]) {
        try {
           
        	if (args.length == 0)
    		{
                            System.err.println ("Please Configuration file is  Missing, Please provide as firt argument !");
    			System.exit(0);
    		}
        	
        	
        	String filename = args[0]; 
       	
        	
            	
        	//readConf("C:/Users/xt20726/Documents/SAM/xmlbackup/soapClientConf.txt");
        	readConf(filename);
        	
        	// soapToSam();
        	 System.out.println("Configuration data : ");
        	 
        	  System.out.println();
        	  
           	System.out.println("DataDir:  "+DataDir);
        	 System.out.println("password:  "+prodpassword);
        	 System.out.println("urlprimary:  "+urlprimary);
        	 System.out.println("urlstandby:  "+urlstandby);
        	 System.out.println("attachment:  "+attachment);
        	selfFilename = DataDir+"shelf_"+dateFormat.format(date)+".csv";
        	 
        	networkFilename = DataDir+"network_"+dateFormat.format(date)+".csv";
          //  finalFileName = DataDir+"ucmdFinal_"+dateFormat.format(date)+".csv";
        	finalFileName = DataDir+ucmdbfilenamer;
        	
        	System.out.println("UCMDB File Name:" + finalFileName);
        	  	 
        	 // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
           
        
            
             
             Date date = new Date() ;
             //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
             File file01 = new File("Shelf_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_"+dateFormat.format(date)+".xml") ;
             File file02 = new File("Network_"+dateFormat.format(date)+".xml") ;
             File file1 = new File(DataDir+file01);
             File file2 = new File(DataDir+file02);
             File file3 = new File(DataDir+file03);
             
             System.out.println("SOAP Connection to SAM-O Please wait ::::");
             System.out.println();
             
             //network() : Method to fetch networks inventory data
             
             SOAPMessage soapResponse1 = soapConnection.call(network(),urlprimary);
            
             System.out.println("SOAP Resp Body :  ");
           
         //  System.out.println("Print File :  "+file2);
            printSOAPResponse(soapResponse1, file2);
             
             //findShelf(): Method to fetch Shelf  inventory data 
            
             SOAPMessage soapResponse2 = soapConnection.call(findShelf(),urlprimary);
            
            
            printSOAPResponse(soapResponse2, file1);
           
           
            
               parseMessage(file1);
               parseLocation(file2);
               final_To_ucmdb(networkFilename,selfFilename); 
             
        
           
             
             // Send Acknowledgement  to SAM 
             
             
          //  SOAPMessage soapResponse = soapConnection.call(createSOAPRequestAlarmAck("faultManager:database-manager|alarm-197-29-153","Ack by Abdou And Alain"), url);
            // Process the SOAP Response
            
           
           
           
        //    soapConnection.close();
       
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            System.out.println();
            e.printStackTrace();
            System.out.println("Failed to connect to Primary URL, Let Try standby URL : "+e.getCause());
            System.out.println();
            
          try {
			soapToSam(urlstandby);
		} catch (SOAPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
            
            
            
            
        } catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  

  public static int readFileLine(String File) throws IOException{
	
	  // Open the file
	  FileInputStream fstream1 = null;
	 
	try {
		fstream1 = new FileInputStream(File);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));

	  String strLine1;
	  int comp =0;
	  while ((strLine1 = br1.readLine()) != null)   {
		comp++;  
	  }
	  
	  br1.close();
	  fstream1.close();
	  
	  return comp;
	  
  }
	 
  private static void  final_To_ucmdb( String File1, String File2) throws IOException{
		 
	 
		 // Open the file
			  FileInputStream fstream1 = null;
			  FileInputStream fstream2 = null;
			
			  
			  try {
				fstream1 = new FileInputStream(File1);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));

			  String strLine1;

			  //Read File Line By Line
			  
			// Open the file
			//  FileInputStream fstream2 = null;
			try {
				fstream2 = new FileInputStream(File2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));

			     String strLine2;

			    String delims1 = ";";
		    	
			   int compt=0;
			   /* while ((strLine1 = br1.readLine()) != null)   {	
			    compt++;	
			    }*/
			//    System.out.println("Compteur "+compt);
			 //   String[] myIP = new String[435];
			    int arrasize =readFileLine(File1);
			   
			//    System.out.println("File Size:  "+arrasize);
			    String[][] myUcmdb = new String[arrasize][15];
			  // String[] myIP ;
			    
		int i=0;	  
		
		
		 String[] tokens1 = null;
		 String[] tokens2 = null;
		

		// String data =null;
		    
	  //   try { 
	    
	     
		 
		 
		 File outputcsv = new File(finalFileName);
	     
	      if (outputcsv.exists()) {
	    	  
	    	//  System.out.println ("Final Filename in delete " +outputcsv);
		     	outputcsv.delete();
	      }
	     
	     
	      BufferedWriter bw = null;
	     
	     if (!outputcsv.exists()) {
	     	outputcsv.createNewFile();
	     	// System.out.println ("Final Filename created " +outputcsv);
		  
	     	FileWriter fw = new FileWriter(outputcsv);
		    bw = new BufferedWriter(fw);
		  //  String header ="displayedName"+";"+"siteId"+";"+"siteName"+";"+"serialNumber"+";"+"manufacturerBoardNumber"+";"+"equipmentCategory"+";"+"cleiCode"+";"+"operationalState"+";"+"baseMacAddress"+";"+"location";
		   // String header ="displayedName"+";"+"serialNumber"+";"+"manufacturerBoardNumber"+";"+"equipmentCategory"+";"+"cleiCode"+";"+"operationalState"+";"+"IP_baseMacAddress"+";"+"location";
		 //   String header ="ipAddress_baseMacAddress"+";"+"systemAddressType"+";"+"location"+";"+"version"+";"+"sysObjectId"+";"+"genericSysUpTime"+";"+"sbiPrimaryDns"+";"+"olcState"+";"+"displayedName"+";"+"Site Priority"+";"+"System"+";"+"NodeModel"+";"+"Attachments"+";"+"SerialNumber"+";"+"manufacturerBoardNumber";
		  //  String header ="ipAddress_baseMacAddress"+";"+"systemAddressType"+";"+"location"+";"+"version"+";"+"sysObjectId"+";"+"sbiPrimaryDns"+";"+"olcState"+";"+"displayedName"+";"+"NodeModel"+";"+"Shortcuts"+";"+"SerialNumber"+";"+"manufacturerBoardNumber";
		    String header ="ipAddress_baseMacAddress"+";"+"systemAddressType"+";"+"location"+";"+"version"+";"+"sysObjectId"+";"+"sbiPrimaryDns"+";"+"displayedName"+";"+"NodeModel"+";"+"Shortcuts"+";"+"SerialNumber"+";"+"manufacturerBoardNumber";
		    bw.write (header);
		   
		    bw.newLine();
		 
	     }
		 
		 
		 
		 
		 
		 
	while ((strLine1 = br1.readLine()) != null)   {	
			  
				  
				    // Print the content on the console
		            
		       
		  
				//    System.out.println ("File1:::::::::::::" +strLine1);
				    
				    tokens1 = strLine1.split(delims1);
				//    Ip1 = tokens1[1];
				//     System.out.println("IP::::1 "+Ip1);
				   
				   
				 //   String ip1 = tokens1[1];
				//    myIP[i]= tokens1[1] ;
				    myUcmdb[i][0] = tokens1[0]+"|"+tokens1[1];;
				    myUcmdb[i][1] = tokens1[2];
				    myUcmdb[i][2] = tokens1[3];
				    myUcmdb[i][3] = tokens1[4];
				    myUcmdb[i][4] = tokens1[5];
				    myUcmdb[i][5] = tokens1[6];
				    myUcmdb[i][6] = tokens1[7];
				    myUcmdb[i][7] = tokens1[8];
				    myUcmdb[i][8] = tokens1[9];
				    myUcmdb[i][9] = tokens1[10];
				    myUcmdb[i][10] = tokens1[11];
				 //   myUcmdb[i][11] = tokens1[12];
				    //myUcmdb[i][12] = tokens1[13];
				    myUcmdb[i][11] = tokens1[0];
				    //myUcmdb[i][14] = tokens1[0];
				    
				    
		
			
		
		 i++ ; 
		}	
		
	while ((strLine2 = br2.readLine()) != null) {	  
			   
	   	  tokens2 = strLine2.split(delims1);
	   	 
	   	 
			    
			    
			      
	           String ip2 = tokens2[1];	
	          	
	         
	       
	      //   System.out.println("Ip2:  "+ip2);
	         
	 for (int a=0; a< myUcmdb.length ;a++){
			 	
			 	
	         
	           if(myUcmdb[a][11].equals(ip2)){
			    	//  System.out.println("SAWADOGO");	
	        	//   System.out.println("Ip1 1 : "+ myUcmdb[a][13]+" Ip2 :  "+ip2);	
	        	      String SerialNumber = tokens2[2];
	        	      String manufacturerBoardNumber = tokens2[3];
	        	 //   String mydata =myUcmdb[a][0]+";"+myUcmdb[a][1]+";"+myUcmdb[a][2]+";"+myUcmdb[a][3]+";"+myUcmdb[a][4]+";"+myUcmdb[a][5]+";"+myUcmdb[a][6]+";"+myUcmdb[a][7]+";"+myUcmdb[a][8]+";"+Location;
	        	  //  String mydata =myUcmdb[a][0]+";"+myUcmdb[a][1]+";"+myUcmdb[a][2]+";"+myUcmdb[a][3]+";"+myUcmdb[a][4]+";"+myUcmdb[a][5]+";"+myUcmdb[a][6]+";"+myUcmdb[a][7]+";"+myUcmdb[a][8]+";"+myUcmdb[a][9]+";"+myUcmdb[a][10]+";"+myUcmdb[a][11]+";"+myUcmdb[a][12]+";"+SerialNumber+";"+manufacturerBoardNumber;
	        	    
	        	       String ipType=null;
	        	       
	        	       if(myUcmdb[a][1].equals("ipv4")){
	        	    	   
                        ipType="IPv4";	        	       
	        	       }
	        	       
	        	       if(myUcmdb[a][1].equals("ipv6")){
	        	    	   
	                        ipType="IPv6";	        	       
		        	       }
	        	       
	        	       
	        	     //  System.out.println("IP Type : "+ipType.toUpperCase());
	        	       
	        	     //  String mydata =myUcmdb[a][0]+";"+ipType+";"+myUcmdb[a][2]+";"+myUcmdb[a][3]+";"+myUcmdb[a][4]+";"+myUcmdb[a][5]+";"+myUcmdb[a][6]+";"+myUcmdb[a][7]+";"+myUcmdb[a][10]+";"+myUcmdb[a][11]+";"+SerialNumber+";"+manufacturerBoardNumber;
	        	     
	        	       //olc position is myUcmdb[a][6]
	        	       String mydata =myUcmdb[a][0]+";"+ipType+";"+myUcmdb[a][2]+";"+myUcmdb[a][3]+";"+myUcmdb[a][4]+";"+myUcmdb[a][5]+";"+myUcmdb[a][6]+";"+myUcmdb[a][9]+";"+myUcmdb[a][10]+";"+SerialNumber+";"+manufacturerBoardNumber;
	        	       bw.write (mydata);
		               bw.newLine();
	        	   // System.out.println("============");
	            //	System.out.println(myIP[a]);
		    	  // System.out.println(tokens2[0]);
		    	 //	System.out.println(Location);
		    	 //	System.out.println("My Data :"+mydata);
		    	 //	System.out.println("============");
		    	 	
		    	 	
			   
			          }
			
			      }
	         
	    
	      /*     for (int a=0; a< 435 ;a++){
	        	   for (int b=0; b < 9 ;b++){
	        		   System.out.println("Ucmdb :" +myUcmdb[a][b]);  
	        	   }
	           
	           }*/
		 
		 
		    }
		
		 //br1.close();
	     	
	bw.flush();
	bw.close();
	 }
			  

  
  
  
  
  
  
  
  

	  
	  
	//Parse XML File to CSV  
	  
	  private static String getValue(String tag, Element element) {

	    	NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();

	    	Node node = (Node) nodes.item(0);

	    	return node.getNodeValue();

	    	}
	  
	 private static  void  parseMessage(File file1){
		    
	    	 String data =null;
	   	    
	        try { 
	      //  File file1 = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+FilenameIN);
	        File outputcsv = new File(selfFilename);
	        
	        BufferedWriter bw = null;
	        
	        if (outputcsv.exists()) {
	        	
	       // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
		     	outputcsv.delete();
	        }
	        
	        if (!outputcsv.exists()) {
	        	outputcsv.createNewFile();
	        	
	        //	System.out.println("File created  ParseMessage: "+outputcsv);
	   	  
	    	FileWriter fw = new FileWriter(outputcsv);
		    bw = new BufferedWriter(fw);
		    String header ="displayedName"+";"+"siteId"+";"+"serialNumber"+";"+"manufacturerBoardNumber"+";"+"baseMacAddress";
		    bw.write (header);
		    bw.newLine();
		   // bw.flush();
		   // bw.close();
	        
	        }
	        
	        
	        
	        	//File file = new File("C:/"+Filename);
	       	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

	       	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	       	
	       	 Document doc =  dBuilder.parse(file1);

	       	 
	             doc.getDocumentElement().normalize();
	               
	              // System.out.println("root of xml file : " +counter+ ":::::"+ doc.getDocumentElement().getNodeName());

	               NodeList nodes = doc.getElementsByTagName("equipment.Shelf");
	               
	        if(nodes.getLength()>0){       
	               System.out.println("Nodes Length: " + nodes.getLength());

	               System.out.println("==========================");

	              
	               
	               for (int i = 0; i < nodes.getLength(); i++) {

	               Node node =  nodes.item(i);
	                   
	               if (node.getNodeType() == Node.ELEMENT_NODE) {

	               Element element = (Element) node;
	                        
	               
	               
	               //System.out.println("shelfId: " + getValue("shelfId", element));
	               System.out.println("displayedName: " + getValue("displayedName", element));
	               System.out.println("siteId: " + getValue("siteId", element));
	              // System.out.println("siteName: " + getValue("siteName", element));
	               System.out.println("serialNumber: " + getValue("serialNumber", element));
	               System.out.println("manufacturerBoardNumber: " + getValue("manufacturerBoardNumber", element));
	               //System.out.println("equipmentCategory: " + getValue("equipmentCategory", element));
	             //  System.out.println("cleiCode: " + getValue("cleiCode", element));
	               
	              // System.out.println("operationalState: " + getValue("operationalState", element));
	               System.out.println("baseMacAddress: " + getValue("baseMacAddress", element));
	               
	               
	                
	              
	               System.out.println("==========================");
	                   
	               
	               data= getValue("displayedName", element) + ";"+getValue("siteId", element)+";"+getValue("serialNumber", element)+";"+getValue("manufacturerBoardNumber", element)+";"+ getValue("baseMacAddress", element);
	               
	              System.out.println("==========================");
	              System.out.println("Data: " +data);
	               bw.write (data);
	               bw.newLine();
	               }
	               }
	               }
	               // file.move("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename,"C:/Users/xt20726/Documents/SAM/xmlbackup/"+Filename);
	                
	        bw.flush();
			bw.close();
	        
	               } catch (Exception ex) {

	               ex.printStackTrace();

	               }
	    	
	    }
	    
	  
	  
	 public static String truncate(String value, int length) {
			// Ensure String length is longer than requested size.
			if (value.length() > length) {
			    return value.substring(0, length);
			} else {
			    return value;
			}
	 }
	 
	 
	 
	 
	 private static void  parseLocation(File file1){
		    
    	 String data =null;
    	 String model =null;
   	     String olc_status=null;
        try { 
      //  File file1 = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+FilenameIN);
        File outputcsv = new File(networkFilename);
        
        BufferedWriter bw = null;
        
        if (outputcsv.exists()) {
	     	outputcsv.delete();
        }
        
        if (!outputcsv.exists()) {
        	outputcsv.createNewFile();
   	  
    	FileWriter fw = new FileWriter(outputcsv);
	    bw = new BufferedWriter(fw);
	  //String header ="ipAddress"+";"+"baseMacAddress"+";"+"systemAddressType"+";"+"location"+";"+"chassisType"+";"+"version"+";"+"sysObjectId"+";"+"genericSysUpTime"+";"+"sbiPrimaryDns"+";"+"olcState"+";"+"displayedName"+";"+"Site Priority"+";"+"System"+";"+"NodeModel"+";"+"Attachments";;
	    
	    // Retired OLCS state 
	  //String header ="ipAddress"+";"+"baseMacAddress"+";"+"systemAddressType"+";"+"location"+";"+"chassisType"+";"+"version"+";"+"sysObjectId"+";"+"genericSysUpTime"+";"+"sbiPrimaryDns"+";"+"olcState"+";"+"displayedName"+";"+"Site Priority"+";"+"System"+";"+"NodeModel"+";"+"Attachments";;
	    String header ="ipAddress"+";"+"baseMacAddress"+";"+"systemAddressType"+";"+"location"+";"+"version"+";"+"sysObjectId"+";"+"sbiPrimaryDns"+";"+"displayedName"+";"+"Site Priority"+";"+"System"+";"+"NodeModel"+";"+"Shortcuts";
	  //  String header ="ipAddress"+";"+"baseMacAddress"+";"+"systemAddressType"+";"+"location"+";"+"version"+";"+"sysObjectId"+";"+"sbiPrimaryDns"+";"+"olcState"+";"+"displayedName"+";"+"Site Priority"+";"+"System"+";"+"NodeModel"+";"+"Shortcuts";
	    
	    /*<ipAddress>172.28.0.11</ipAddress>

<baseMacAddress>8C-90-D3-91-C7-45</baseMacAddress>

<systemAddressType>ipv4</systemAddressType>

<sysDescription>N/A</sysDescription>

<location>AB.EDMONTON.EG.COMM</location>

<chassisType>sr_shelf_12Slotc</chassisType>

<version>TiMOS-B-11.0.R3 </version>

<sysObjectId>.1.3.6.1.4.1.6527.1.3.6</sysObjectId>

<genericSysUpTime>1 day, 16 hours, 18 minutes, 39 seconds.</genericSysUpTime>

<sbiPrimaryDns>165.115.6.54</sbiPrimaryDns>

<olcState>inService</olcState>

<siteId>172.28.0.11</siteId>

<displayedName>edmwyop02-0048</displayedName>*/
	    
	    
	    
	    
	    bw.write (header);
	    bw.newLine();
	   // bw.flush();
	   // bw.close();
        
        }
        
        
        
        	//File file = new File("C:/"+Filename);
       	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

       	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       	
       	 Document doc =  dBuilder.parse(file1);

       	 
             doc.getDocumentElement().normalize();
               
              // System.out.println("root of xml file : " +counter+ ":::::"+ doc.getDocumentElement().getNodeName());

               NodeList nodes = doc.getElementsByTagName("netw.NetworkElement");
               
        if(nodes.getLength()>0){       
               System.out.println("Nodes Length: " + nodes.getLength());

               System.out.println("==========================");

              
               
               for (int i = 0; i < nodes.getLength(); i++) {

               Node node =  nodes.item(i);
                   
               if (node.getNodeType() == Node.ELEMENT_NODE) {

               Element element = (Element) node;
                        
               
               
               System.out.println("ipAddress: "  + getValue("ipAddress", element));
               System.out.println("baseMacAddress: " + getValue("baseMacAddress", element));
               System.out.println("systemAddressType: " + getValue("systemAddressType", element));
             //  System.out.println("sysDescription: " + getValue("sysDescription", element));
               
               System.out.println("location: " + getValue("location", element));
               System.out.println("chassisType: " + getValue("chassisType", element));
               System.out.println("version: " + getValue("version", element));
               System.out.println("sysObjectId: " + getValue("sysObjectId", element));
               System.out.println("genericSysUpTime: " + getValue("genericSysUpTime", element));
               System.out.println("sbiPrimaryDn: " + getValue("sbiPrimaryDns", element));
               System.out.println("olcState: " + getValue("olcState", element));
             //  System.out.println("siteId: " + getValue("siteId", element));
               System.out.println("displayedName: " + getValue("displayedName", element));
              
               
               //String attachment = "\\"+"\\"+"cn.ca"+"\\"+"dfs"+"\\"+"ittel"+"\\"+"Diagrams"+"\\"+"System Layouts"+"\\"+"Alcatel MPLS Transport"+"\\"+"Alcatel MPLS Transport System Layout.vsd";

            //   System.out.println("Attacmment: "+attachment );

              
               System.out.println("==========================");
                   
               if(getValue("chassisType", element).equals("sr_shelf_12Slotc")){
            	   System.out.println("Chassis Type in 7750: "+ getValue("chassisType", element));  
               model="7750 SRc12";
            //   System.out.println("Model: "+ model);
               
                 }
               if(getValue("chassisType", element).equals("sar_shelf_8Slot_v2")){
            	   
            	   System.out.println("Chassis Type in 7705: "+ getValue("chassisType", element));
            	   
                   model="7705 SAR8v2";
              //     System.out.println("Model: "+ model);
                   
                 }
               
               
                if(getValue("chassisType", element).equals("shelf_7701")){
            	   
            	   System.out.println("Chassis Type in CPAA: "+ getValue("chassisType", element));
            	   
                   model="7701 CPAA";
              //     System.out.println("Model: "+ model);
                   
                 }
               
               
             
               
                    if(getValue("olcState", element).equals("inService")){
            	   
                        olc_status="INSTALLED";
              //     System.out.println("OLC Status: "+ olc_status);
                   
                     }
                   if(getValue("olcState", element).equals("outOfService")){
                	   
                       olc_status="Out of Service";
                   //    System.out.println("OLC Status: "+ olc_status);
                       
                     }
                   
                     if(getValue("olcState", element).equals("maintenance")){
                	   
                       olc_status="Maintenance";
                     //  System.out.println("OLC Status: "+ olc_status);
                       
                     }
                   
                   
                    String token[]=null;
                    token = getValue("genericSysUpTime", element).split(",");
                    //String sysUptime = truncate(getValue("genericSysUpTime", element),6);
                    String sysUptime=token[0];
                 //   System.out.println("Print System Up days: "+sysUptime);
               
            
                     
                   //  data= getValue("ipAddress", element)+";"+getValue("baseMacAddress", element)+";"+getValue("systemAddressType", element)+";"+getValue("location", element)+";"+getValue("version", element)+";"+getValue("sysObjectId", element)+";"+getValue("sbiPrimaryDns", element)+";"+olc_status+";"
                 //   +getValue("displayedName", element)+";"+"2"+";"+"MPLS"+";"+model+";"+attachment; 
                     
                     //Retired OLCS state:
                     data= getValue("ipAddress", element)+";"+getValue("baseMacAddress", element)+";"+getValue("systemAddressType", element)+";"+getValue("location", element)+";"+getValue("version", element)+";"+getValue("sysObjectId", element)+";"+getValue("sbiPrimaryDns", element)+";"
                             +getValue("displayedName", element)+";"+"2"+";"+"MPLS"+";"+model+";"+attachment;  
               
               
               
               System.out.println("==========================");
              System.out.println("Data: " +data);
               bw.write (data);
               bw.newLine();
               }
               }
               }
               // file.move("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename,"C:/Users/xt20726/Documents/SAM/xmlbackup/"+Filename);
                
        bw.flush();
		bw.close();
        
               } catch (Exception ex) {

               ex.printStackTrace();

               }
    	
    }
    
  
	  
	  
	  
	  
	public void SOAPConnection(String objectFullname, String text){
		
		try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
          // String url = "http://10.247.198.12:8080/xmlapi/invoke";
           //String url = "http://165.115.6.35:8080/xmlapi/invoke";
           
            String url ="http://mtl-sam01pbe:8080/xmlapi/invoke"; 
            
           // SOAPMessage soapResponse = soapConnection.call(createSOAPRequestFind(), url);
           
            SOAPMessage soapResponse = soapConnection.call(getServiceInfo(), url);
           // SOAPMessage soapResponse = soapConnection.call(createSOAPRequestAlarmAck(objectFullname,text), url);
            // Process the SOAP Response
         //   printSOAPResponse(soapResponse);  Abdou commentaire 

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
		
	}
	
	private  SOAPMessage createSOAPRequestFind() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
      

        //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
       
        
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
        usrname.addTextNode("ossuser3");
   
        SOAPElement password  =  header1.addChildElement("password");
        password.addTextNode("4bc60d71c78fabbe695f06bfabfdf716");
      
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("netw.NetworkElement");
       
        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

	private static SOAPMessage getServiceInfo() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
      

        //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
       
        
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
        usrname.addTextNode("ossuser3");
   
        SOAPElement password  =  header1.addChildElement("password");
       // password.addTextNode("4bc60d71c78fabbe695f06bfabfdf716");
        
       
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
       SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("service.Service");
       
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
      //  soapBodyElem10.setAttribute("class","service.Service");
        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("children");
        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
	
   
	
	
	private static SOAPMessage findTopology() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
      

        //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
       
        
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
       // usrname.addTextNode("ossuser3");
        usrname.addTextNode("ossuser3");
        SOAPElement password  =  header1.addChildElement("password");
        //password.addTextNode("4bc60d71c78fabbe695f06bfabfdf716");
        password.addTextNode("eac2e6703cdcd98fa86a4fd71e15e7c5");
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("netw.TopologyGroup");
        
        SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("filter");
        SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
        
        SOAPElement soapBodyElem5 = soapBodyElem4.addChildElement("notEqual");
        soapBodyElem5.setAttribute("name","viewName");
        soapBodyElem5.setAttribute("value","SERVICE ROOT GROUP");
        
        SOAPElement soapBodyElem6 = soapBodyElem4.addChildElement("notEqual");
        soapBodyElem6.setAttribute("name","application");
        soapBodyElem6.setAttribute("value","cpam");
        
        SOAPElement soapBodyElem13 = soapBodyElem1.addChildElement("resultFilter");
        SOAPElement soapBodyElem14 = soapBodyElem13.addChildElement("attribute");
        soapBodyElem14.addTextNode("description");
        SOAPElement soapBodyElem15 = soapBodyElem13.addChildElement("attribute");
        soapBodyElem15.addTextNode("displayedName");
        SOAPElement soapBodyElem16 = soapBodyElem13.addChildElement("attribute");
        soapBodyElem16.addTextNode("elementCount");
        
        SOAPElement soapBodyElem17 = soapBodyElem13.addChildElement("attribute");
        soapBodyElem17.addTextNode("viewName");
        
        SOAPElement soapBodyElem18 = soapBodyElem13.addChildElement("attribute");
        soapBodyElem18.addTextNode("objectFullName");
        
        //SOAPElement soapBodyElem19 = soapBodyElem1.addChildElement("</resultFilter>");
        
        //soapBody.addChildElement("</find>");
        
        
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }

	
	
	
	//SOAP Find Alarms
	
	private static SOAPMessage findAlarms() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
      

        //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
       
        
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
       // usrname.addTextNode("ossuser3");
        usrname.addTextNode("ossuser3");
        SOAPElement password  =  header1.addChildElement("password");
       //Lab
       password.addTextNode("4bc60d71c78fabbe695f06bfabfdf716");
       //Prod
     // password.addTextNode("eac2e6703cdcd98fa86a4fd71e15e7c5");
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        QName mycmd = new QName("xmlapi_1.0","fm.FaultManager.findFaults");
 
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
       
          
        SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("faultFilter");
        SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
        soapBodyElem4.setAttribute("class","fm.AlarmInfo");
       
      
        SOAPElement soapBodyElem5 = soapBodyElem4.addChildElement("equal");
        soapBodyElem5.setAttribute("name","isAcknowledged");
        soapBodyElem5.setAttribute("value","false");
        
        SOAPElement soapBodyElem6 = soapBodyElem4.addChildElement("or");
        
        SOAPElement soapBodyElem7 = soapBodyElem6.addChildElement("equal");
        soapBodyElem7.setAttribute("name","severity");
        soapBodyElem7.setAttribute("value","critical");
        
        SOAPElement soapBodyElem8 = soapBodyElem6.addChildElement("equal");
        soapBodyElem8.setAttribute("name","severity");
        soapBodyElem8.setAttribute("value","minor");
        
        SOAPElement soapBodyElem9 = soapBodyElem6.addChildElement("equal");
        soapBodyElem9.setAttribute("name","severity");
        soapBodyElem9.setAttribute("value","cleared");
        
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
        
       // SOAPElement soapBodyElemxx = soapBodyElem10.addChildElement("attribute");
       // soapBodyElem10.addTextNode("description");
        
        SOAPElement soapBodyElem11 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem11.addTextNode("severity");
        SOAPElement soapBodyElem12 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem12.addTextNode("highestSeverity");
        
        SOAPElement soapBodyElem13 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem13.addTextNode("probableCause");
        
        SOAPElement soapBodyElem14 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem14.addTextNode("alarmName");
        
        SOAPElement soapBodyElem15 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem15.addTextNode("affectedObjectFullName");
        
        SOAPElement soapBodyElem16 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem16.addTextNode("isAcknowledged");

        SOAPElement soapBodyElem17 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem17.addTextNode("firstTimeDetected");

       

        SOAPElement soapBodyElem18 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem18.addTextNode("lastTimeDetected");

        SOAPElement soapBodyElem19 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem12.addTextNode("numberOfOccurences");

        SOAPElement soapBodyElem20 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem20.addTextNode("isServiceAffecting");

        SOAPElement soapBodyElem21 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem21.addTextNode("additionalText");

        SOAPElement soapBodyElem22 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem22.addTextNode("additionalText");

        SOAPElement soapBodyElem23 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem23.addTextNode("nodeId");

        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem24.addTextNode("nodeName");

        
        SOAPElement soapBodyElem25 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem25.addTextNode("displayedName");
        
        
        SOAPElement soapBodyElem26 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem26.addTextNode("objectFullName");
        
        
       
        
        
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }

	
	
	
	private static SOAPMessage findShelf() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        Date date = new Date() ;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        File file = new File("inventory_"+dateFormat.format(date)+".xml") ;

        
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
       //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
       // usrname.addTextNode("ossuser3");
        usrname.addTextNode("ossuser3");
        SOAPElement password  =  header1.addChildElement("password");
      // password.addTextNode(labpassword);
        
        //System.out.print("SAWADOGO ::::::::::::::::::::  "+prodpassword);
        password.addTextNode(prodpassword );
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        //QName mycmd = new QName("xmlapi_1.0","findToFile");
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("equipment.Equipment");
        
        
        //SOAPElement soapBodyElemftp = soapBodyElem1.addChildElement("fileName");
        
       // soapBodyElemftp.addTextNode("ftp://samadmin:samadmin@10.247.198.10/clienapp1/"+file);
       //   soapBodyElemftp.addTextNode("home.txt20726.data.inventory.inventory.xml");
        
        SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("filter");
        SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
       soapBodyElem4.setAttribute("class","equipment.Shelf");  
       
        SOAPElement soapBodyElemxx = soapBodyElem4.addChildElement("wildcard");
       soapBodyElemxx.setAttribute("name","objectFullName");
       soapBodyElemxx.setAttribute("value","network:%.%.%.%:shelf-1"); 
       
      // SOAPElement soapBodyElem5 = soapBodyElem4.addChildElement("equal");
      // soapBodyElem5.setAttribute("name","equipmentState");
      // soapBodyElem5.setAttribute("value","12");
           
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
        soapBodyElem10.setAttribute("class","equipment.Shelf");
       
     
        
      //  SOAPElement soapBodyElem11 = soapBodyElem10.addChildElement("attribute");
       // soapBodyElem11.addTextNode("administrativeState");
        
        SOAPElement soapBodyElem12 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem12.addTextNode("baseMacAddress");
        
      // SOAPElement soapBodyElem13 = soapBodyElem10.addChildElement("attribute");
      // soapBodyElem13.addTextNode("cleiCode");
        
        SOAPElement soapBodyElem14 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem14.addTextNode("displayedName");
        
        //SOAPElement soapBodyElem15 = soapBodyElem10.addChildElement("attribute");
        // soapBodyElem15.addTextNode("equipmentCategory");
        
        //SOAPElement soapBodyElem16 = soapBodyElem10.addChildElement("attribute");
       // soapBodyElem16.addTextNode("equipmentState");

        // SOAPElement soapBodyElem17 = soapBodyElem10.addChildElement("attribute");
        //  soapBodyElem17.addTextNode("olcState");
        
         // SOAPElement soapBodyElem18 = soapBodyElem10.addChildElement("attribute");
         // soapBodyElem18.addTextNode("operationalState");
        
        // SOAPElement soapBodyElem19 = soapBodyElem10.addChildElement("attribute");
        //  soapBodyElem19.addTextNode("olcState");
        
        SOAPElement soapBodyElem20 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem20.addTextNode("serialNumber");
       
        //SOAPElement soapBodyElem21 = soapBodyElem10.addChildElement("attribute");
        //soapBodyElem21.addTextNode("shelfId");
       
        SOAPElement soapBodyElem22 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem22.addTextNode("siteId");
        
        SOAPElement soapBodyElem23 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem23.addTextNode("siteName");
        
        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem24.addTextNode("manufacturerBoardNumber"); 
        
       // SOAPElement soapBodyElem25 = soapBodyElem10.addChildElement("attribute");
       // soapBodyElem25.addTextNode("shelfDesc"); 
        
       // SOAPElement soapBodyElem26 = soapBodyElem10.addChildElement("attribute");
       // soapBodyElem26.addTextNode("phyShelfRole"); 
       
         SOAPElement soapBodyElem27 = soapBodyElem10.addChildElement("children");
            
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }
	
	
	private  SOAPMessage findIventoryNetw() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        Date date = new Date() ;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        File file = new File("network_"+dateFormat.format(date)+".xml") ;

        
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
       //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
       // usrname.addTextNode("ossuser3");
        usrname.addTextNode("ossuser3");
        SOAPElement password  =  header1.addChildElement("password");
         password.addTextNode(prodpassword);
    //   password.addTextNode(prodpassword);
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        //QName mycmd = new QName("xmlapi_1.0","findToFile");
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("netw.NetworkElement");
        
        
       
        
       // SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("filter");
     //   SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
   //    soapBodyElem4.setAttribute("class","netw.Network");  
       
    //    SOAPElement soapBodyElemxx = soapBodyElem4.addChildElement("wildcard");
   //     soapBodyElemxx.setAttribute("name","objectFullName");
      //   soapBodyElemxx.setAttribute("value","network:%"); 
       
      
       
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
     
      //  soapBodyElem10.setAttribute("class","netw.Network");
       
     
        
       SOAPElement soapBodyElem11 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem11.addTextNode("ipAddress");
        
        SOAPElement soapBodyElem12 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem12.addTextNode("baseMacAddress");
        
       SOAPElement soapBodyElem13 = soapBodyElem10.addChildElement("attribute");
       soapBodyElem13.addTextNode("siteId");
        
        SOAPElement soapBodyElem14 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem14.addTextNode("displayedName");
        
        SOAPElement soapBodyElem15 = soapBodyElem10.addChildElement("attribute");
         soapBodyElem15.addTextNode("location");
        
        SOAPElement soapBodyElem16 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem16.addTextNode("olcState");

        SOAPElement soapBodyElem17 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem17.addTextNode("systemAddressType");
        
       
        
        SOAPElement soapBodyElem19 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem19.addTextNode("version");
        
        SOAPElement soapBodyElem20 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem20.addTextNode("sbiDnsDomain");
        
        SOAPElement soapBodyElem21 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem21.addTextNode("sysObjectId");
        
        SOAPElement soapBodyElem22 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem22.addTextNode("genericSysUpTime");


        
         SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("children");
            
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }
	
	private static SOAPMessage findIventoryService() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        Date date = new Date() ;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        File file = new File("ServiceSites_"+dateFormat.format(date)+".xml") ;

        
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
       //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
       // usrname.addTextNode("ossuser3");
        usrname.addTextNode("ossuser3");
        SOAPElement password  =  header1.addChildElement("password");
     //  password.addTextNode("4bc60d71c78fabbe695f06bfabfdf716");
       password.addTextNode("eac2e6703cdcd98fa86a4fd71e15e7c5");
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        //QName mycmd = new QName("xmlapi_1.0","findToFile");
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("service.Site");
        
        
       
        
       // SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("filter");
     //   SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
   //    soapBodyElem4.setAttribute("class","netw.Network");  
       
    //    SOAPElement soapBodyElemxx = soapBodyElem4.addChildElement("wildcard");
   //     soapBodyElemxx.setAttribute("name","objectFullName");
      //   soapBodyElemxx.setAttribute("value","network:%"); 
       
      
           
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
      //  soapBodyElem10.setAttribute("class","netw.Network");
       
     
        
       

        
        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("children");
            
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }
	
	
	
	private static  SOAPMessage network() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        Date date = new Date() ;
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        File file = new File("NetworkElement_"+dateFormat.format(date)+".xml") ;

        
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
       //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
       // usrname.addTextNode("ossuser3");
        usrname.addTextNode("ossuser3");
        SOAPElement password  =  header1.addChildElement("password");
        password.addTextNode(prodpassword);
      // password.addTextNode(labpassword);
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        //QName mycmd = new QName("xmlapi_1.0","findToFile");
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("netw.NetworkElement");
        
           
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
        SOAPElement soapBodyElem11 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem11.addTextNode("ipAddress");
        
        SOAPElement soapBodyElem12 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem12.addTextNode("baseMacAddress");
        
       SOAPElement soapBodyElem13 = soapBodyElem10.addChildElement("attribute");
       soapBodyElem13.addTextNode("siteId");
        
        SOAPElement soapBodyElem14 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem14.addTextNode("displayedName");
        
        SOAPElement soapBodyElem15 = soapBodyElem10.addChildElement("attribute");
         soapBodyElem15.addTextNode("location");
        
        SOAPElement soapBodyElem16 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem16.addTextNode("olcState");

        SOAPElement soapBodyElem17 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem17.addTextNode("systemAddressType");
        
       
        
        SOAPElement soapBodyElem19 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem19.addTextNode("version");
        
        SOAPElement soapBodyElem20 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem20.addTextNode("sbiPrimaryDns");
        
        SOAPElement soapBodyElem21 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem21.addTextNode("sysObjectId");
        
        SOAPElement soapBodyElem22 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem22.addTextNode("genericSysUpTime");

        SOAPElement soapBodyElem23 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem23.addTextNode("chassisType");
        
        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem24.addTextNode("sysDescription");
        
        SOAPElement soapBodyElem25 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem25.addTextNode("sysUpTime");

        
        SOAPElement soapBodyElem26 = soapBodyElem10.addChildElement("children");
            
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }
	
	
	
    private static SOAPMessage createSOAPRequestAlarmAck(String faultObjectFullName,String text) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
      

        //SOAP Header
        SOAPHeader header = envelope.getHeader();
        QName myheader =new QName("xmlapi_1.0","header");
        SOAPHeaderElement sec= header.addHeaderElement(myheader);
       
        
        SOAPElement header1 = sec.addChildElement("security");
       
        SOAPElement usrname  =   header1.addChildElement("user");
        usrname.addTextNode("ossuser3");
   
        SOAPElement password  =  header1.addChildElement("password");
        password.addTextNode("4bc60d71c78fabbe695f06bfabfdf716");
      
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        QName mycmd = new QName("xmlapi_1.0","fm.FaultManager.acknowledgeFault");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("deployer");
        soapBodyElem2.addTextNode("immediate");
        SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("urgency");
        soapBodyElem3.addTextNode("4");
        SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("doDelete");
        soapBodyElem4.addTextNode("false");
        SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("faultObjectFullName");
        soapBodyElem5.addTextNode(faultObjectFullName);
        SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("text");
        soapBodyElem6.addTextNode(text);
        SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("doMarkAsAcknowledged");
        soapBodyElem7.addTextNode("true");
      
        SOAPElement soapBodyElem8 = soapBodyElem1.addChildElement("continueOnFailure");
        soapBodyElem8.addTextNode("true");
       
        SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("alteredSeverity");
        soapBodyElem9.addTextNode("4");
        
        
        soapMessage.saveChanges();
         
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    
    
    
    /**
     * Method used to print the SOAP Response
     */
    private static void printSOAPResponse(SOAPMessage soapResponse, File file) throws Exception {
    	
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        
        if (file.exists()) {
      	     file.delete();
        }
        
      //  File file = new File("/opt/5620sam/server/clienapp1/data/"+file0);
        System.out.print("\nResponse SOAP Message = ");
     
        StreamResult result = new StreamResult(System.out);
        
        transformer.transform(sourceContent, result);
        
       
        
        BufferedWriter bw = null;
        
        if (!file.exists()) {
   	     file.createNewFile();
   	  
    	FileWriter fw = new FileWriter(file);
	    bw = new BufferedWriter(fw);
	    bw.write (log(soapResponse));
	    bw.flush();
	    bw.close();
        
        }
        
        
    }

    private static String log(SOAPMessage message) throws SOAPException, IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        message.writeTo(out);
      //  logger.debug(out.toString());
        return out.toString();
    }



}