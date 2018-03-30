import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


public class CircuitInventory {

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
     public static String execelpah_in =null;
     public static String excelpah_out =null;
     public static String columnwanted =null;
     public static String filtercolumn =null;
     public static String ucmdbfilename=null;
     public static String ucmdbfilenamer =null;
     public static  String sam_circuits_id = null;
     public static  String sam_circuits =null;
	 public static String networkFilename = null;
     public  static String finalFileName = null;
     public  static String sam_circuits_xml =null;
     
     
    
     
     
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
   		  
			 if (token[0].equals("excelpah_in")){
				 execelpah_in = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
   		  
			 if (token[0].equals("excelpah_out")){
				 excelpah_out = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
   		  
			 if (token[0].equals("columnwanted")){
				 columnwanted = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
			 
			 if (token[0].equals("filtercolumn")){
				 filtercolumn = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
			 
			 if (token[0].equals("sam_circuits_id")){
				 sam_circuits_id = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
			 
			 if (token[0].equals("sam_circuits")){
				 sam_circuits = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
			 
			 
			 if (token[0].equals("sam_circuits_xml")){
				 sam_circuits_xml = token[1];
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
            // File file01 = new File("Shelf_"+dateFormat.format(date)+".xml") ;
             File file01 = new File("sam_soap_circuit_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_"+dateFormat.format(date)+".xml") ;
           //  File file02 = new File("CircuitInvent_"+dateFormat.format(date)+".xml") ;
             File file02 = new File("CircuitInvent_2016-12-05.xml") ;
              
             File file1 = new File(DataDir+file01);
             File file2 = new File(DataDir+file02);
             File file3 = new File(DataDir+file03);
             
             sam_circuits =sam_circuits+dateFormat.format(date)+".txt";
           	sam_circuits_id =sam_circuits_id+dateFormat.format(date)+".txt";
    		 // Create SOAP Connection
             SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
             SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
             
            // System.out.println("SOAP Resp Body :  ");
             
           //  System.out.println("Print File :  "+file2);
           //  SOAPMessage soapResponse1 = soapConnection.call( network(),url);
             
           //  System.out.println("SOAP Resp Body :  ");
             
           //  System.out.println("Print File :  "+file2);
           //   printSOAPResponse(soapResponse1, file2);
               
             
              
             SOAPMessage soapResponse2 = soapConnection.call(findIventoryService(),url);
              
              
              printSOAPResponse(soapResponse2, file1);
              
            //  parseMessage(file1,sam_circuits,sam_circuits_id);
            // parseMessage(file1,);
            // File inputfile = new File(execelpah_in);
            // File outputfile = new File(execelpah_out);
                       
              //readCol(inputfile,columnwanted,filtercolumn);
            // CSVToExcelConverter(execelpah_in); 
             
            
          //   compareSM_To_SAM("C:/Users/xt20726/Documents/SAM/xmloutput/sam_cn.txt", "C:/Users/xt20726/Documents/SAM/xmloutput/sm_cn.txt","C:/Users/xt20726/Documents/SAM/xmloutput/sm_in_sam.txt","C:/Users/xt20726/Documents/SAM/xmloutput/sm_not_in_sam.txt");
             /*  parseMessage(file1);
               parseLocation(file2);
               final_To_ucmdb(networkFilename,selfFilename); */
    		 
    	      
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
        	 
        	 System.out.println("excell pah out :  "+excelpah_out);
        	 
        	//circuits = DataDir+"circuits_"+dateFormat.format(date)+".txt";
        	
        	sam_circuits =sam_circuits+dateFormat.format(date)+".txt";
        	sam_circuits_id =sam_circuits_id+dateFormat.format(date)+".txt";
        	
        	networkFilename = DataDir+"network_"+dateFormat.format(date)+".csv";
          //  finalFileName = DataDir+"ucmdFinal_"+dateFormat.format(date)+".csv";
        	finalFileName = DataDir+ucmdbfilenamer;
        	
        //	System.out.println("UCMDB File Name:" + finalFileName);
        	  	 
        	 // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
           
        
            
             
             Date date = new Date() ;
             //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
             
             File file01 = new File("sam_soap_circuit_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_"+dateFormat.format(date)+".xml") ;
             //File file02 = new File("CircuitInvent_"+dateFormat.format(date)+".xml") ;
              
            
             File file1 = new File(DataDir+file01);
            
             
             System.out.println("SOAP Connection to SAM-O Please wait ::::");
             System.out.println();
             
             //network() : Method to fetch networks inventory data
             
            SOAPMessage soapResponse1 = soapConnection.call(findIventoryService(),urlprimary);
             
            
            
             System.out.println("SOAP Resp Body :  ");
           
         //  System.out.println("Print File :  "+file2);
           
             
             printSOAPResponse(soapResponse1, file1);
             
             parseMessage(file1,sam_circuits,sam_circuits_id);
             
             
             
          
             File inputfile = new File(DataDir+execelpah_in);
             System.out.println("input file : "+inputfile);
             
            // File outputfile = new File(DataDir+execelpah_out);
             
          //  System.out.println("output file : "+outputfile);
             
             System.out.println("Column wanted : "+columnwanted);
             
             System.out.println("Filter  : "+filtercolumn);
             
             //readCol(inputfile,columnwanted,filtercolumn);
             CSVToExcelConverter(execelpah_in); 
             
             //  compareSM_To_SAM("C:/Users/xt20726/Documents/SAM/xmloutput/sam_cn.txt", "C:/Users/xt20726/Documents/SAM/xmloutput/sm_installed.txt","C:/Users/xt20726/Documents/SAM/xmloutput/sm_in_sam.txt","C:/Users/xt20726/Documents/SAM/xmloutput/sm_not_in_sam.txt");
             //findShelf(): Method to fetch Shelf  inventory data 
            
         /*    SOAPMessage soapResponse2 = soapConnection.call(findIventoryService(),urlprimary);
            
            
            printSOAPResponse(soapResponse2, file1);*/
           
           
            
            /*   parseMessage(file1);
               parseLocation(file2);
               final_To_ucmdb(networkFilename,selfFilename); */
             
        
           
             
             // Send Acknowledgement  to SAM 
             
             
          //  SOAPMessage soapResponse = soapConnection.call(createSOAPRequestAlarmAck("faultManager:database-manager|alarm-197-29-153","Ack by Abdou And Alain"), url);
            // Process the SOAP Response
            
           
           
           
        //    soapConnection.close();
       
        } catch (Exception e) {
            System.err.println("Error occurred ");
            System.out.println();
            e.printStackTrace();
          //  System.out.println("Error detail: "+e.getCause());
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
	 
 
  
  
  
  
  
  
  

	  
	  
	//Parse XML File to CSV  
	  
	  private static String getValue(String tag, Element element) {
		  Node node = null;
	    
	//	 if(!element.getElementsByTagName(tag).item(0).getChildNodes().equals(null)){ 
			
			// System.out.println("SAWA:"+ element.getElementsByTagName(tag).item(0).getChildNodes());
		 
		  NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();

	        node = (Node) nodes.item(0);
	//	 }
	    	return node.getNodeValue();
		 
	   }
	  
	 private static  void  parseMessage(File file1,String sam_circuits, String sam_circuits_id){
		    
	    	 String data =null;
	   	    
	        try { 
	      //  File file1 = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+FilenameIN);
	       
	       System.out.println("File Circuits:"+sam_circuits); 	
	       File outputcsv = new File(DataDir+sam_circuits);
	       
	        File outputcsv2 = new File(DataDir+sam_circuits_id);
	        BufferedWriter bw2 = null;
	        BufferedWriter bw = null;
	        
	        if (outputcsv.exists()) {
	        	
	       // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
		     	outputcsv.delete();
	        }
	        
	        if (!outputcsv.exists()) {
	        	outputcsv.createNewFile();
	        	
	        //	System.out.println("File created  ParseMessage: "+outputcsv);
	   	  
	        	if (outputcsv2.exists()) {
		        	
	     	       // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
	     		     	outputcsv2.delete();
	     	        }
	     	        
	     	        if (!outputcsv2.exists()) {
	     	        	outputcsv2.createNewFile();
	     	        }
	       
	    	FileWriter fw = new FileWriter(outputcsv);
		    bw = new BufferedWriter(fw);
		    
		    FileWriter fw2 = new FileWriter(outputcsv2);
		    bw2 = new BufferedWriter(fw2);
		    
		    String header ="description"+";"+"svcComponentId"+";"+"serviceName"+";"+"siteId"+";"+"operationalState";
		   // bw.write (header);
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

	               //NodeList nodes = doc.getElementsByTagName("*.Site");
	               
	              

 
	             
	         //      NodeList nodes = doc.getElementsByTagName("fm.AlarmInfo");	 
	             
	           
	             NodeList nodes =  (NodeList) doc.getElementsByTagName("result").item(0);;
	            
	            /* NodeList nodes = ((Node) rootElement).getChildNodes();

	             System.out.println("Element List: " + nodes.item(0));*/
	             
	             
	           //  NodeList nodes = doc.getChildNodes();	 
	                             
	               if(nodes.getLength()>0){       
	               System.out.println("Nodes Length: " + nodes.getLength());

	               System.out.println("==========================");

	              
	               
	               for (int i = 0; i < nodes.getLength(); i++) {

	               Node node =  nodes.item(i);
	                   
	             //  System.out.println("=========================="+nodes.item(i));
	               
	               if (node.getNodeType() == Node.ELEMENT_NODE) {

	               Element element = (Element) node;
	                        
	             
	               
	           if (!getValue("description", element).equals("N/A")){
	               	  
	               
	               System.out.println("description: " + getValue("description", element));
	               System.out.println("svcComponentId: " + getValue("svcComponentId", element));
	               System.out.println("serviceName: " + getValue("serviceName", element));
	               System.out.println("siteId: " + getValue("siteId", element));  
	               System.out.println("operationalState: " + getValue("operationalState", element));   
	               
	            
	                System.out.println("==========================");
	                   
	               
	               data= getValue("description", element) + ":"+getValue("svcComponentId", element)+":"+getValue("serviceName", element)+":"+getValue("siteId", element)+":"+ getValue("operationalState", element);
	               
	             // System.out.println("==========================");
	           //   System.out.println("Data: " +data);
	               
	               if(!data.equals(null)){
	               bw.write (data);
	               }
	               bw.newLine();
	              
	               
	               
	                  String token[] = getValue("description", element).split(" ");
		            
		              int desclenght=getValue("description", element).length();
		              
		               System.out.println("desc : "+desclenght);
		             
		              String cncircuit = null;
		               
		              //System.out.println("Lenght"+token[0].length());
		              
		            if(token[0].length()>=9 ){
		                
		            	  System.out.println("description :"+getValue("description", element));
		            	 
		            	  cncircuit=token[0];
		            	  
		                    System.out.println("Circuit in >=9 :"+cncircuit);
		                         }
		            
		         
		            if(token[0].equals("BRP")){   
		             
		            	//System.out.println("Print token 1 < 9 :"+token[1]);
		        	       
		            //	if(token[1].equals(null)){
		            		  
		            	  
		            	 System.out.println("token :"+token[0]+token[1]+token[2]);
		            	  
		            	  cncircuit=token[0]+token[1]+token[2];
		                  
		            	  System.out.println("Circuit BRP < 9 :"+cncircuit);
		            	  
		            	  
		            	}
		            
		              if(token[0].equals("VPLS")){ 
		            	
		            	String token2[] = getValue("serviceName", element).split(" ");
		            	
		            	String cnnum= token2[1];
			             
		            	//System.out.println("Print token 1 < 9 :"+token[1]);
		        	       
		            //	if(token[1].equals(null)){
		            		  
		            	  
		                  System.out.println("token :"+cnnum);
		            	  
		            	 //cncircuit= cnnum;;
		                  
		                  cncircuit=token[0]+token[1]+token[2];
		                  
		            	  System.out.println("VPLS :"+cncircuit);
		            	  
		            	  
		            	}
		            
		           
		              if(token[0].equals("ITUS")){ 
			            	
			            	String token2[] = getValue("serviceName", element).split(" ");
			            	
			            	String cnnum= token2[1];
				             
			            	//System.out.println("Print token 1 < 9 :"+token[1]);
			        	       
			            //	if(token[1].equals(null)){
			            		  
			            	  
			                  System.out.println("token :"+cnnum);
			            	  
			            	  cncircuit= cnnum;;
			                  
			            	  System.out.println("ITUS :"+cncircuit);
			            	  
			            	  
			            	}    
		    
		            
		            if(token[0].equals("Alcatel")){ 
			            	
			            	String token2[] = getValue("serviceName", element).split(" ");
			            	
			            	String cnnum= token2[1];
				             
			            	//System.out.println("Print token 1 < 9 :"+token[1]);
			        	       
			            //	if(token[1].equals(null)){
			            		  
			            	  
			                  System.out.println("token :"+cnnum);
			            	  
			            	 // cncircuit= cnnum;;
			                  
			                  cncircuit=token[0]+token[1]+token[2];
			                  
			            	  System.out.println("Alcatel :"+cncircuit);
			            	  
			            	  
			            	}  
		    
		              
		            
		            
		        
		         
		           
		           System.out.println("Circuit Name Final :"+cncircuit); 
		          
		       //    String mydata =cncircuit+":"+getValue("description", element)+":"+getValue("serviceName", element);
		           String mydata =cncircuit;
		           
		           if(mydata !=null){
		               bw2.write (mydata);
		             }
		                
		                bw2.newLine();
	               
	               
	               
	              
	               
	               
	               }
	               
	             //  }
	              
	               
	             } // Fin N/A
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
        soapBodyElem2.addTextNode("service.Endpoint");
        
        
       
        
        SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("filter");
        SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
       soapBodyElem4.setAttribute("class","service.Endpoint");  
       
       SOAPElement soapBodyElemxx = soapBodyElem4.addChildElement("wildcard");
       soapBodyElemxx.setAttribute("name","objectFullName");
       soapBodyElemxx.setAttribute("value","svc-mgr:service-%"); 
       
      
           
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
        
        SOAPElement soapBodyElem11 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem11.addTextNode("displayedName");
        SOAPElement soapBodyElem12 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem12.addTextNode("description");
        
        //SOAPElement soapBodyElem13 = soapBodyElem10.addChildElement("attribute");
        //soapBodyElem13.addTextNode("svcComponentId");
        
        SOAPElement soapBodyElem14 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem14.addTextNode("serviceId");
        
        //SOAPElement soapBodyElem15 = soapBodyElem10.addChildElement("attribute");
        //soapBodyElem15.addTextNode("serviceName");
        
        //SOAPElement soapBodyElem16 = soapBodyElem10.addChildElement("attribute");
        //soapBodyElem16.addTextNode("subscriberName");
        
        SOAPElement soapBodyElem17 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem17.addTextNode("siteId");
        
        //SOAPElement soapBodyElem18 = soapBodyElem10.addChildElement("attribute");
        //soapBodyElem18.addTextNode("operationalState");
        
       // SOAPElement soapBodyElem19 = soapBodyElem10.addChildElement("attribute");
       // soapBodyElem19.addTextNode("vcId");
        
        SOAPElement soapBodyElem20 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem20.addTextNode("revertTime");
        
        
        
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




//Method to read Excell file:
    

    
    
   /* public static void read() throws IOException  {
        File inputWorkbook = new File("C:/Users/xt20726/Documents/SAM/xmloutput/circuit _inventory2.xls");
        Workbook w;
        try {
                w = Workbook.getWorkbook(inputWorkbook);
                // Get the first sheet
                Sheet sheet = w.getSheet("export");
                // Loop over first 10 column and lines

                for (int j = 0; j < sheet.getColumns(); j++) {
                        for (int i = 0; i < sheet.getRows(); i++) {
                                Cell cell = sheet.getCell(j, i);
                                CellType type = cell.getType();
                                if (type == CellType.LABEL) {
                                        System.out.println("I got a label "
                                                        + cell.getContents());
                                }

                                if (type == CellType.NUMBER) {
                                        System.out.println("I got a number "
                                                        + cell.getContents());
                                }

                        }
                }
        } catch (BiffException e) {
            e.printStackTrace();
    }
}*/

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void readExcell(File inputFile, File outputFile){
    	
    	try 
    	{
    	        // Get the workbook instance for XLSX file
    	        
    		BufferedWriter bw = null;

    		FileWriter fw = new FileWriter(outputFile);
    		bw = new BufferedWriter(fw);
    		


    		    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(inputFile));

    	        // Get first sheet from the workbook
    	        XSSFSheet sheet = wb.getSheetAt(0);

    	        Row row;
    	        Cell cell;

    	        // Iterate through each rows from first sheet
    	        Iterator<Row> rowIterator = sheet.iterator();

    	        while (rowIterator.hasNext()) 
    	        {
    	                row = rowIterator.next();

    	                // For each row, iterate through each columns
    	                Iterator<Cell> cellIterator = row.cellIterator();
    	                
    	                while (cellIterator.hasNext()) 
    	                {
    	                cell = cellIterator.next();

    	                switch (cell.getCellType()) 
    	                {

    	                case Cell.CELL_TYPE_BOOLEAN:
    	                        System.out.println(cell.getBooleanCellValue());
    	                      //  bw.write (cell.getBooleanCellValue());
    	    	        		bw.newLine();
    	                        
    	                        break;

    	                case Cell.CELL_TYPE_NUMERIC:
    	                        System.out.println(cell.getNumericCellValue());
    	                        
    	                        //bw.write((int) cell.getNumericCellValue());
    	    	        		//bw.newLine();
    	                        
    	                        break;

    	                case Cell.CELL_TYPE_STRING:
    	                        System.out.println(cell.getStringCellValue());
    	                        bw.write((cell.getStringCellValue()));
    	                        bw.newLine();
    	                        break;

    	                case Cell.CELL_TYPE_BLANK:
    	                        System.out.println(" ");
    	                        break;

    	                default:
    	                        System.out.println(cell);

    	                }
    	                
    	               

    	        	 //   bw.flush();
    	        	//	bw.close();
    	                
    	                }
    	        }
    	}
    	catch (Exception e) 
    	{
    	        System.err.println("Exception :" + e.getMessage());
    	}





    }



    
 //Read CSV .
    public static void CSVToExcelConverter(String sm_file_name) throws IOException{

    	
    	ArrayList arList=null;
    	ArrayList al=null;

    	File outputFile  = new File(DataDir+"sm_extrat_cn_"+dateFormat.format(date)+".txt") ;
    	File outputFilexsl  = new File(DataDir+"sm_extrat_cn_"+dateFormat.format(date)+".xls") ;

             System.out.println("Excell out: " +outputFile ); 
        	
    	        BufferedWriter bw = null;
    	        
    	        if (outputFile.exists()) {
    	        	
    	       // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
    	        	outputFile.delete();
    	        }
    	        
    	        if (!outputFile.exists()) {
    	        	outputFile.createNewFile();
    	        	
    	        //	System.out.println("File created  ParseMessage: "+outputcsv);
    	        }
    	        	

                    FileWriter fw = new FileWriter(outputFile);
    		    
    	    	bw = new BufferedWriter(fw);
        







    	//String fName="\\"+"\\"+"Mtl-hq-f07"+"\\"+"hq5aw1"+"\\"+"ITIL"+"\\"+"Application"+"\\"+"Extracts"+"\\"+"Export_cncircuits.csv";
            //String fName="\\"+"\\"+"Mtl-hq-f07"+"\\"+"hq5aw1"+"\\"+"ITIL"+"\\"+"Application"+"\\"+"Extracts"+"\\"+sm_file_name;
        String fName=DataDir+execelpah_in;
    	System.out.println("File name: "+fName);
    	String thisLine;
    	int count=0;
    	FileInputStream fis = null;
		try {
			fis = new FileInputStream(fName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	DataInputStream myInput = new DataInputStream(fis);
    	int i=0;
    	arList = new ArrayList();

            String[] tokens2 = null;
            String delims1 = ",";
            
    	while ((thisLine = myInput.readLine()) != null)
    	{
    	    // System.out.println("Line : "+thisLine);
    	
                 tokens2 = thisLine.split(delims1);
    	   	 
    	       String id = tokens2[0];		 
    	       System.out.println("Line : "+id);    
    	      bw.write (id);
    	      bw.newLine();		    
    			      
    	           

    	al = new ArrayList();
    	String strar[] = thisLine.split(",");
    	for(int j=0;j<strar.length;j++)
    	{
    	al.add(strar[j]);
    	}
    	arList.add(al);
    	System.out.println();
    	i++;
    	}
            bw.flush();
    	bw.close();


    	try
    	{
    	HSSFWorkbook hwb = new HSSFWorkbook();
    	HSSFSheet sheet = hwb.createSheet("new sheet");
    	

            for(int k=0;k<arList.size();k++)
    	{
    	ArrayList ardata = (ArrayList)arList.get(k);
    	HSSFRow row = sheet.createRow((short) 0+k);
    	for(int p=0;p<ardata.size();p++)
    	{
    	HSSFCell cell = row.createCell((short) p);
    	String data = ardata.get(p).toString();
    	if(data.startsWith("=")){
    	cell.setCellType(Cell.CELL_TYPE_STRING);
    	data=data.replaceAll("\"", "");
    	data=data.replaceAll("=", "");
    	cell.setCellValue(data);
    	}else if(data.startsWith("\"")){
    	data=data.replaceAll("\"", "");
    	cell.setCellType(Cell.CELL_TYPE_STRING);
    	cell.setCellValue(data);
    	}else{
    	data=data.replaceAll("\"", "");
    	cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    	cell.setCellValue(data);
    	}
    	//*/
    	// cell.setCellValue(ardata.get(p).toString());
    	}
    	System.out.println();
    	}
    	FileOutputStream fileOut = new FileOutputStream(outputFilexsl);
    	hwb.write(fileOut);
    	fileOut.close();
    	System.out.println("Your excel file has been generated");
    	} catch ( Exception ex ) {
    	ex.printStackTrace();
    	} //main method ends
    	


           }
    	
    
// Read by colum number
    
  public static void readCol(File inputfile, String columnwanted, String filtercolumname) throws IOException{
    	
    	// File outsm = new File("C:/Users/xt20726/Documents/SAM/xmloutput/sm_cn.txt");
    	
    	  Date date = new Date() ;
         //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        
         File outputFile  = new File(DataDir+"sm_extrat_cn_"+dateFormat.format(date)+".txt") ;
         
    	//File outputFile = new File(DataDir+outputFilex);
	        
      	 System.out.println("Excell out: " +outputFile ); 
    	
	        BufferedWriter bw = null;
	        
	        if (outputFile.exists()) {
	        	
	       // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
	        	outputFile.delete();
	        }
	        
	        if (!outputFile.exists()) {
	        	outputFile.createNewFile();
	        	
	        //	System.out.println("File created  ParseMessage: "+outputcsv);
	        }
	        	
	       
	    	FileWriter fw = new FileWriter(outputFile);
		    
	    	bw = new BufferedWriter(fw);
    
    
    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(inputfile));

    // Get first sheet from the workbook
    XSSFSheet sheet = wb.getSheetAt(0);


    //we will search for column index containing string "Your Column Name" in the row 0 (which is first row of a worksheet
    String columnWanted = columnwanted;
    Integer columnNo = null;
    
    String filtercolumn = filtercolumname ;
    Integer filtercolumnNo = null;
    //output all not null values to the list
   
    ArrayList<Cell> cells = new ArrayList<Cell>();

    Row firstRow = sheet.getRow(0);

   for(Cell cell:firstRow){
        if (cell.getStringCellValue().equals(filtercolumn) ){
        	filtercolumnNo = cell.getColumnIndex();
         //   System.out.println("SM CN" + cell.getStringCellValue() ); 
            
		   
		   
            
            
        }
    }

   
   for(Cell cell:firstRow){
       if (cell.getStringCellValue().equals(columnWanted) ){
       	columnNo = cell.getColumnIndex();
          System.out.println("SM CN :" + cell.getStringCellValue() ); 
           
		   
		   
           
           
       }
   }
   
   

    if (columnNo != null){
    	System.out.println("Collumn: " +columnNo ); 	
    	
    for (Row row : sheet) {
       Cell c = row.getCell(columnNo);
    //   Cell cf = row.getCell(filtercolumnNo);
       
      // if(cf.getStringCellValue().equals("INSTALLED")){
      
    	//   System.out.println("CIRCUIT Value  :" + c.getStringCellValue() +" Status:  " + cf.getStringCellValue() );
      
       bw.write(c.getStringCellValue());
       
     //  bw.write((int) c.getNumericCellValue());
       bw.newLine();
       //}
       
       
       if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) {
          // Nothing in the cell in this row, skip it
       } else {
          cells.add(c);
      
       
       }
    }
    }else{
        System.out.println("could not find column " + columnWanted + " in first row of " + wb.toString());
    }

    
    bw.flush();
	bw.close();
	
    
    }


    
 public static void writeInFile(String File, String data) throws IOException{
	 
	 File outfile = new File(File);
     
     
     BufferedWriter bw = null;
     
     if (outfile.exists()) {
     	
    // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
	     	outfile.delete();
     }
     
     if (!outfile.exists()) {
     	outfile.createNewFile();
     	
     //	System.out.println("File created  ParseMessage: "+outputcsv);
     }
     	
    
 	    FileWriter fw = new FileWriter(outfile);
 	   
 	    bw = new BufferedWriter(fw);

	    bw.write(data);
	    bw.newLine();
	    
	 //   bw.flush();
		//bw.close();
	    
	 
 }
  
    public static void compareSM_To_SAM(String File1, String File2, String outIN, String outNOTin) throws IOException{
    	
    	//F1 => SAM
    	//F2 => SM
    	 FileInputStream fstream1 = null;
		  FileInputStream fstream2 = null;
		  
		  
		  String[] tokens1 = null;
	      String[] tokens2 = null;
	     
	      
	      String delims1 = ":";
	      
	      
	      File outfile1 = new File(outIN);
	      
	      
	      BufferedWriter bw1 = null;
	      BufferedWriter bw2 = null;
	      
	      if (outfile1.exists()) {
	      	
	     // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
	 	     	outfile1.delete();
	      }
	      
	      if (!outfile1.exists()) {
	      	outfile1.createNewFile();
	      	
	      //	System.out.println("File created  ParseMessage: "+outputcsv);
	      }
	      	
	     
	  	    FileWriter fw1 = new FileWriter(outfile1);
	  	   
	  	    bw1 = new BufferedWriter(fw1);
	  	    
            
	  	    
	  	    
	  	  File outfile2 = new File(outNOTin);
	      
	      
	     
	      
	      if (outfile2.exists()) {
	      	
	     // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
	 	     	outfile2.delete();
	      }
	      
	      if (!outfile2.exists()) {
	      	outfile2.createNewFile();
	      	
	      //	System.out.println("File created  ParseMessage: "+outputcsv);
	      }
	      	
	     
	  	    FileWriter fw2 = new FileWriter(outfile2);
	  	   
	  	    bw2 = new BufferedWriter(fw2);
	  	    
	      
	     
	  	    
	  	    
	  	    
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
			     
			     int compt=0;
			    
				
				    int arrasize =readFileLine(File1); 
				    String[] mysmdata = new String[arrasize];
			        int i=0;
			        
		 while ((strLine1 = br1.readLine()) != null)   {	
				    	
				    	tokens1 = strLine1.split(delims1);	
				    	mysmdata[i] = tokens1[0];
				    	
				    	
				    i++;	
				    	
				    }
    	
				    
				    
	    while ((strLine2 = br2.readLine()) != null) {	  
						   
					   	//  tokens2 = strLine2.split(delims1);
					   	 
					   	  
					   	for (int a=0; a <mysmdata.length ;a++){
					   		
					   	 if(mysmdata[a].equals(strLine2)){
					   	 
					   		//System.out.println("SM data found in SAM:" +mysmdata[a]+" SM :"+ strLine2);
					   		bw1.write(strLine2);
					   		bw1.newLine();
					   		
					   //	 writeInFile("C:/Users/xt20726/Documents/SAM/xmloutput/sm_in_sam.txt",mysmdata[a]);
					   	 
					   	     }
					   	 
					  /* 	if(!mysmdata[a].equals(strLine2)) {
					   		 
					   		System.out.println(strLine2);
					   	     
					   		 bw2.write(strLine2);
					   	 
		                     }*/
					   	
				          		   	
					   //	
					   	
					   	}
				
					  // 	 bw2.flush();
				      //   bw2.close();
					
					   	
					   	
					   	/* 	 int  flag=1;
					     
						   	for (int b=0; b< mysmdata.length ;b++){
						   		
						   	 if(!mysmdata[b].equals(strLine2)){
						   	    flag = 0;
						   		//System.out.println("SM data found in SAM:" +mysmdata[a]+" SM :"+ strLine2);
						   		//bw2.write(strLine2);
						   		//bw1.newLine();
						   		
						   //	 writeInFile("C:/Users/xt20726/Documents/SAM/xmloutput/sm_in_sam.txt",mysmdata[a]);
						   	 
						   	 }
					   	
							}
						  
					   	 if(flag==0){
					   		
					   		System.out.println("Flag =0:"+ flag +" " +strLine2 );
					   		//System.out.println("SM data NOT in SAM:" +mysmdata[a]+" SM " +strLine2);
					   	//	writeInFile("C:/Users/xt20726/Documents/SAM/xmloutput/sm_not_in_sam.txt",mysmdata[a]);
					   		 
					   		bw2.write(strLine2);
					   		bw2.newLine();
					   		
					   	 }*/
					   	
					   	
					   	
					   	
					   	System.out.println(strLine2);	   
    	
                        
			}
          bw1.flush();
          bw1.close();
          
  }   
    
 }
