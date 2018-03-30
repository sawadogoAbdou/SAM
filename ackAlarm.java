
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ackAlarm {


	static Date date = new Date() ;
    static //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm") ;
    //Configuration  variables:
    
    static Logger log = Logger.getLogger(ackAlarm .class.getName());
    
    
    
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
     public static  String sampassword =null;
     public static  String samuser =null;
     public static  String labpassword =null;
     public static  String urlprimary=null;
     public static  String urlstandby =null;
     public static String attachment =null;
     public static String confAlarm =null;
     public static String conf =null;
     public static String alarmdef =null;
     public static  String selfFilename = null;
     public static  String xmlTobsmc = null;
	 public static String networkFilename = null;
     public  static String finalFileName = null;
     
     public  static String outputpath = null;
     public  static String outputfilename = null;
     public  static String objectFullName = null;
     
     public  static String getobjectFullName = null;
     public  static String getext = null;
     public  static String statutAlarm = null;
     
     public  static String tempdata =null;
     public  static String alarmNamed =null;
     public  static String alarmCoded =null;
     public  static String alarmTyped =null;
     public  static String alarmPackaged =null;
     public  static String alarmProbaleCaused =null;
    
     
     
    
     static File  soaprep = new File("Soap_Ack_Response.xml") ;
     
     static File SoapAckResponse = new File(DataDir+soaprep);     	
     
     
     //Read Alarm code definition 
     public static String readFileLine(String Filename, String AlarmNameCode) throws IOException{
    		
   	  // Open the file
   	  FileInputStream fstream1 = null;
   	 
   	try {
   		fstream1 = new FileInputStream(Filename);
   	} catch (FileNotFoundException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
   	  BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));

   	  String strLine1;
   	  int comp =0;
   	  String myLine = null;
   	  String AlarmName =null;
   	  String ProbableCause =null;
   	  while ((strLine1 = br1.readLine()) != null)   {
   		
   		String token[] = strLine1.split(":");  
   		  
     	 if (token[1].equals(AlarmNameCode)){
	 	  AlarmName = token[0];
		  ProbableCause = token[2];
	   //   System.out.println("Alarm Name :  "+AlarmName);
	  //    System.out.println("ProbableCause :  "+ProbableCause);
	 
         //  myLine= AlarmName+":"+ProbableCause;
		  myLine=strLine1;
		  
     	 } 
   		 
   	  
   	  
   	  }
   	  
   	  br1.close();
   	  fstream1.close();
   	  
   	  return myLine ;
   	  
     }
   	 
     
     
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
		  
			
			 if (token[0].equals("samuser")){
				 samuser= token[1];
				// System.out.println("password:  "+prodpassword);
			 }
		  
			 
			 
			 if (token[0].equals("sampassword")){
				 sampassword= token[1];
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
   		  
			 if (token[0].equals("alarmdef")){
				 alarmdef = token[1];
				// System.out.println("urlstandby:  "+urlstandby);
			 }
   		  
			 if (token[0].equals("conf")){
				 conf = token[1];
				// System.out.println("conf:  "+conf);
			 }
		  
			 if (token[0].equals("outputpath")){
				 outputpath = token[1];
				// System.out.println("conf:  "+conf);
			 }
			 
			 if (token[0].equals("outputfilename")){
				 outputfilename = token[1];
				// System.out.println("conf:  "+conf);
			 }
		
			 
			 if (token[0].equals("xmlTobsmc")){
				 xmlTobsmc = token[1];
				// System.out.println("conf:  "+conf);
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
     
     
    
     
     public static void soapToSam(String url,File SoapAckResponse ) throws Throwable, SOAPException{
    	 
    	 
    	 try {
    		 
    		 
    		 Date date = new Date() ;
             //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
             File file01 = new File("Children_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_To_BSMc-V1.xml") ;
             File file02 = new File("SoapRep_"+dateFormat.format(date)+".xml") ;
             File file1 = new File(DataDir+file01);
             File file2 = new File(DataDir+file02);
             File file3 = new File(DataDir+file03);
    	
             
             
             // Create SOAP Connection
             SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
             SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            
            // Find Correlating alarm children, the object here is  the correlating Alarm, each children has this object as correlating alarm
         
             
             SOAPMessage soapResponse1 = soapConnection.call(findAlarm(getobjectFullName),url);
             
         //   SOAPMessage soapResponse1 = soapConnection.call( getServiceInfo(),url);  
            printSOAPResponse(soapResponse1, file1);
         //System.out.println("Alarm Status :  "+ parseMessage(file1,url));
          
            
            // Acknowledgement of  the children
             parseMessage(file1,url);
           
             //  Acknowledgment of the parent
             
             SOAPMessage soapResponse2 = soapConnection.call(createSOAPRequestAlarmAck(getobjectFullName,getext),url);
           ///  printSOAPResponse(soapResponse2,SoapAckResponse);
             log.info(dateFormat.format(date)+":"+"OBJECTFULLNAME: "+getobjectFullName+ ":"+ "TICKET: "+getext);
             log.info(log(soapResponse2));
             
             //  printSOAPResponseAck(soapResponse2);
            
       //     if(statutAlarm.equals("false")){
            	
        ///	SOAPMessage soapResponse2 = soapConnection.call(createSOAPRequestAlarmAck("faultManager:svc-mgr@service-662@10.247.0.2@interface-1/3/7-inner-tag-0-outer-tag-0|alarm-249-32-32","IM000000"),url);
          ///	printSOAPResponse(soapResponse2, file2);   	
            	
        //    }
            
            
            
          if (file1.exists()) {
               	
                // 	 System.out.println("File name in delete ParseMessage: "+outputcsv);
         	     	file1.delete();
                 }
         //   parseMessage(file3);
              
           //  getCorrelated(file1);
    		 
    	      
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
        	
      // 	String log4jConfPath ="C:/Users/xt20726/Downloads";
      //	PropertyConfigurator.configure(log4jConfPath);
        	
      	
        	String filename = args[0]; 
        	
        	 getobjectFullName = args[1]; 
        	
        	 getext = args[2]; 
        	
        	
        	

        	 
        	 //System.out.println("Dir name : "+filename);
        
        	readConf(filename);
        	
        	// soapToSam();
        /*	 System.out.println("Configuration data : ");
        	 
        	  System.out.println();
        	  
           	System.out.println("DataDir:  "+DataDir);
        	// System.out.println("password:  "+prodpassword);
        	 System.out.println("urlprimary:  "+urlprimary);
        	 System.out.println("urlstandby:  "+urlstandby);
        	 System.out.println("Conf:  "+conf);
        	 System.out.println("outputpath:  "+outputpath);
        	 System.out.println("outputfilename:  "+outputfilename);
        	 
        	 
        	 System.out.println();
        	 
        	 System.out.println("getobjectFullName:  "+getobjectFullName);
        	 System.out.println("getext:  "+getext);*/
        	 
        	 
        	 
        	 
        	//selfFilename = DataDir+"shelf_"+dateFormat.format(date)+".csv";
        	 
        	//networkFilename = DataDir+"network_"+dateFormat.format(date)+".csv";
        //    finalFileName = DataDir+"ucmdFinal_"+dateFormat.format(date)+".csv";
        	 
        	
        	   
        	 
        	 // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
           
        
            
             
             Date date = new Date() ;
             //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
             File file01 = new File("Children_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_To_BSMc-V1.xml") ;
             File file02 = new File("SoapRep_"+dateFormat.format(date)+".xml") ;
             File file1 = new File(DataDir+file01);
             File file2 = new File(DataDir+file02);
             File file3 = new File(DataDir+file03);
            
             
             soaprep = new File("Soap_Ack_Response.xml") ;
             
             SoapAckResponse = new File(DataDir+soaprep);     	
             
             
             System.out.println("SOAP Connection to SAM-O Please wait ::::");
             System.out.println();
             
        //   SOAPMessage soapResponse1 = soapConnection.call(findAlarmsTo_ucmdb(),urlprimary);
         
           //  SOAPMessage soapResponse1 = soapConnection.call(getCorrelated(file3),urlprimary); 
             
            // getCorrelated(file3);
             
           // SOAPMessage soapResponse1 = soapConnection.call( getServiceInfo() ,urlprimary); 
             
             
             
            SOAPMessage soapResponse1 = soapConnection.call(findAlarm(getobjectFullName),urlprimary);
            
            printSOAPResponse(soapResponse1, file1);
            
            SOAPMessage soapResponse2 = soapConnection.call(createSOAPRequestAlarmAck(getobjectFullName,getext),urlprimary);
            
            log.info(dateFormat.format(date)+":"+"OBJECTFULLNAME: "+getobjectFullName+ ":"+ "TICKET: "+getext);
            log.info(log(soapResponse2));
            
        ///    printSOAPResponse(soapResponse2,SoapAckResponse);
          
       //     printSOAPResponseAck(soapResponse2);
             
           System.out.println("SOAP Resp Body :  ");
           
       //   System.out.println("Print File :  "+file1);
       //   printSOAPResponse(soapResponse1, file3);
           
        //   System.out.println("Alarm Status :  "+ parseMessage(file1,urlprimary));
          
           parseMessage(file1,urlprimary);
           
           
           
           if (file1.exists()) {
           	
                //	 System.out.println("File name in delete ParseMessage: "+outputcsv);
        	     //	file1.delete();
                }
       
          
      //  if(statutAlarm.equals("false")){
        	
        ///	SOAPMessage soapResponse2 = soapConnection.call(createSOAPRequestAlarmAck("faultManager:svc-mgr@service-662@10.247.0.2@interface-1/3/7-inner-tag-0-outer-tag-0|alarm-249-32-32","IM000002"),urlprimary);
        ///	printSOAPResponse(soapResponse2, file2);
        	
        	
    //    }
        
        
        
        
        
        
        
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            System.out.println();
            e.printStackTrace();
            System.out.println("Failed to connect to Primary URL, Let Try standby URL : "+e.getCause());
            System.out.println();
            
          try {
			soapToSam(urlstandby,SoapAckResponse);
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

 
 
  
  
  
  
  
  
  
  

	  
	  
	//Parse XML File to CSV  
	  
	  private static String getValue(String tag, Element element) {

	    	NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();

	    	Node node = (Node) nodes.item(0);

	    	return node.getNodeValue();

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
	
	private static  SOAPMessage createSOAPRequestFind() throws Exception {
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
        //Lab
        password.addTextNode(labpassword);
        
     
       // password.addTextNode("4bc60d71c78fabbe695f06bfabfdf716");
        
       
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        QName mycmd = new QName("xmlapi_1.0","find");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
       SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
        soapBodyElem2.addTextNode("service.SapBaseStats");
       
      /*  SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
      //  soapBodyElem10.setAttribute("class","service.Service");
        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("children");*/
        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
	
   
	
	
	
	
	
	
	//SOAP TestAlarm
	
   
	
	//SOAP Find Alarms
	
	
	 private static  void  getCorrelated(File file1){
		    
    	 String data =null;
   	    
        try { 
      
       
        	File outputcsv = new File(outputpath+outputfilename);
            System.out.println("File Path : " + outputcsv);
        	
        	//File outputcsv = new File(xmlTobsmc);
        
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
	   
	    bw.newLine();
	  
        
        }
        
        
        
        	//File file = new File("C:/"+Filename);
       	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

       	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       	
       	 Document doc =  dBuilder.parse(file1);

       	 
             doc.getDocumentElement().normalize();
               
              // System.out.println("root of xml file : " +counter+ ":::::"+ doc.getDocumentElement().getNodeName());

               NodeList nodes = doc.getElementsByTagName("alarm_data");
               
        if(nodes.getLength()>0){       
               System.out.println("Nodes Length: " + nodes.getLength());

               System.out.println("==========================");

              
               
               for (int i = 0; i < nodes.getLength(); i++) {

               Node node =  nodes.item(i);
                   
               if (node.getNodeType() == Node.ELEMENT_NODE) {

               Element element = (Element) node;
                        
               
               
           //    if(getValue("correlatingAlarm", element) !=null){
              
               System.out.println("correlatingAlarm: " + getValue("correlatingAlarm", element));
               System.out.println("objectFullName: " + getValue("objectFullName", element));
               System.out.println("firstTimeDetected: " + getValue("firstTimeDetected", element));
               System.out.println("node: " + getValue("node", element));  
              
               System.out.println("==========================");
               
               if(getValue("correlatingAlarm", element).equals("N/A")){
               
              data= getValue("correlatingAlarm", element) + ";"+getValue("objectFullName", element)+";"+getValue("node", element)+";"+getValue("firstTimeDetected", element);
             //  data= getValue("objectFullName", element)+";"+getValue("node", element)+";"+getValue("firstTimeDetected", element);
               
               }
               
               }
              System.out.println("==========================");
              System.out.println("Data: " +data);
               bw.write (data);
               bw.newLine();
               }
               }
          //     }
               // file.move("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename,"C:/Users/xt20726/Documents/SAM/xmlbackup/"+Filename);
                
        bw.flush();
		bw.close();
        
               } catch (Exception ex) {

               ex.printStackTrace();

               }
    	
    }
    

    
	 public static String  parseMessage(File file1,String url ) throws SOAPException{
		    
    	 String Alarm =null;
    	 String Alarm_Name =null;
    	 String Alarm_ProbableCause =null;
    	
    	 
        
    	 
    	 //System.out.println("SAWADOGO IN PARSING   : ");
    	 
    	 // Create SOAP Connection
         SOAPConnectionFactory soapConnectionFactory = null;
		try {
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
		} catch (UnsupportedOperationException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         SOAPConnection soapConnection = soapConnectionFactory.createConnection();
    	 
    	 
    	 Date date = new Date() ;
         //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
        
         
         File outputfile = new File(outputpath+outputfilename);
      //   System.out.println("File Path : " + outputfile);
        try { 
      
       	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

       	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       	
       	 Document doc =  dBuilder.parse(file1);

       	 
             doc.getDocumentElement().normalize();
               
             // System.out.println("root of xml file : " +file1);

               NodeList nodes = doc.getElementsByTagName("fm.AlarmInfo");
               
       
              //Output xml File to bsmsc 
               DocumentBuilderFactory docFactoryOut = DocumentBuilderFactory.newInstance();
       		   DocumentBuilder docBuilderOut = docFactoryOut.newDocumentBuilder();
       		   
       		   
       		   Document docOut= docBuilderOut.newDocument();   
        	   DOMSource source = new DOMSource(docOut);
        	   
        	   Element alarmInfo =docOut.createElement("fm.AlarmInfo");
        	   docOut.appendChild(alarmInfo);
        	 //  Element alarm_data =docOut.createElement("alarm_data");
        	  // alarmInfo.appendChild(alarm_data);
        	   
          	//   docOut.appendChild(alarmInfo);
               
       		
       		// write the content into xml file
       		TransformerFactory transformerFactory = TransformerFactory.newInstance();
       		Transformer transformer = transformerFactory.newTransformer();
       		
       		
       		
       		//DOMSource source = new DOMSource(docOut);
         	//StreamResult result = new StreamResult(new File("C:/Users/xt20726/Documents/SAM/xmloutput/test_ucmdb.xml"));

               
               
               
               if(nodes.getLength()>0){       
               System.out.println("Nodes Length: " + nodes.getLength());

               System.out.println("==========================");

               for (int i = 0; i < nodes.getLength(); i++) {
            	   
            	

               Node node1 =  nodes.item(i);
                   
               if (node1.getNodeType() == Node.ELEMENT_NODE) {

               Element element = (Element) node1;
               
               
            //   System.out.println("objectFullName: " + getValue("objectFullName", element));
               
               objectFullName =getValue("objectFullName", element);
              
               
               // Envoie des multiples des requetes SOAP
               
                SOAPMessage soapResponse2 = soapConnection.call(createSOAPRequestAlarmAck(objectFullName,getext),url);
            	//printSOAPResponse(soapResponse2,SoapAckResponse);
               
            	 
                 log.info(dateFormat.format(date)+":"+"OBJECTFULLNAME: "+getValue("objectFullName", element)+" "+"AFFECTEDOBJECTFULLNAME: "+getValue("affectedObjectFullName", element)+":"+ "TICKET: "+getext);
            	log.info(log(soapResponse2));
            	// createSOAPRequestAlarmAck(objectFullName,"IM00002");
               
               
               }
               
               }
               }
               } catch (Exception ex) {

               ex.printStackTrace();

               }
		return objectFullName;
    	
               
        }
	
	//Create Node
    
    public Node createContactNode(Document document) {

        // create FirstName and LastName elements
        Element firstName = document.createElement("FirstName");
        Element lastName = document.createElement("LastName");

        firstName.appendChild(document.createTextNode("First Name"));
        lastName.appendChild(document.createTextNode("Last Name"));

        // create contact element
        Element alarm_data = document.createElement("alarm_data");

        
        // append attribute to contact element
       // alarm_data.setAttributeNode(genderAttribute);
        alarm_data.appendChild(firstName);
        alarm_data.appendChild(lastName);

        return alarm_data;
      }


	
	
	
	//SOAP Find Alarms
	
	
	
	
	
    private static SOAPMessage findAlarm(String alarmInstanceFullName) throws Exception {
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
        usrname.addTextNode(samuser);
   
        SOAPElement password  =  header1.addChildElement("password");
        password.addTextNode(sampassword);
      
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        QName mycmd = new QName("xmlapi_1.0","fm.FaultManager.findFaults");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
       
     /*   SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("alarmInstanceFullName");
        soapBodyElem2.addTextNode(alarmInstanceFullName);*/
      
        
         SOAPElement soapBodyElem14 = soapBodyElem1.addChildElement("faultFilter");
        SOAPElement soapBodyElem14a = soapBodyElem14.addChildElement("and");
        soapBodyElem14a.setAttribute("class","fm.AlarmInfo");
        SOAPElement soapBodyElem14b = soapBodyElem14a.addChildElement("equal");
        soapBodyElem14b.setAttribute("name","correlatingAlarm");
        soapBodyElem14b.setAttribute("value",alarmInstanceFullName);
        
        
      /*  SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("resultFilter");
        
       
        
        SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("attribute");
        soapBodyElem4.addTextNode("isAcknowledged");*/
        
        soapMessage.saveChanges();
         
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    
	
    
    
    private static SOAPMessage ackAlarmBack(String instanceFullName,String text) throws Exception {
        
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
         QName mycmd = new QName("xmlapi_1.0","fm.FaultManager.acknowledgeFaults");
         SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
         SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("deployer");
         soapBodyElem2.addTextNode("immediate");
         SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("urgency");
         soapBodyElem3.addTextNode("4");
         SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("doDelete");
         soapBodyElem4.addTextNode("false");
         SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("instanceFullName");
         soapBodyElem5.addTextNode(instanceFullName);
         SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("text");
         soapBodyElem6.addTextNode(text);
         
         
         
        SOAPElement soapBodyElem13 = soapBodyElem1.addChildElement("alarmableInstanceFilter");
         soapBodyElem13.setAttribute("class","fm.AlarmInfo"); 
         
         SOAPElement soapBodyElem13a = soapBodyElem13.addChildElement("equal");
        soapBodyElem13a.setAttribute("name","nodeId");
         soapBodyElem13a.setAttribute("value","10.247.0.2");  
        soapBodyElem13a.setAttribute("name","severity");  
         soapBodyElem13a.setAttribute("value","critical"); 
         soapBodyElem13a.setAttribute("name","affectedObjectFullName");  
         soapBodyElem13a.setAttribute("value",instanceFullName); 
         
         SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("doMarkAsAcknowledged");
         soapBodyElem7.addTextNode("true");
       
         SOAPElement soapBodyElem8 = soapBodyElem1.addChildElement("continueOnFailure");
         soapBodyElem8.addTextNode("true");
        
         SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("alteredSeverity");
         soapBodyElem9.addTextNode("4");
         
      //   SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("alteredSeverity");
      //   soapBodyElem10.addTextNode("4");
         
         SOAPElement soapBodyElem11 = soapBodyElem1.addChildElement("scopeType");
         soapBodyElem11.addTextNode("1");
         
         SOAPElement soapBodyElem12 = soapBodyElem1.addChildElement("scopeDepth");
         
         soapBodyElem12.addTextNode("0");
         
         
         
       /*  SOAPElement soapBodyElem14 = soapBodyElem1.addChildElement("faultFilter");
         SOAPElement soapBodyElem14a = soapBodyElem14.addChildElement("and");
         soapBodyElem14a.setAttribute("class","fm.AlarmInfo");
         SOAPElement soapBodyElem14b = soapBodyElem14a.addChildElement("equal");
         soapBodyElem14b.setAttribute("name","correlatingAlarm");
         soapBodyElem14b.setAttribute("value","faultManager:network@10.247.0.2@shelf-1@cardSlot-1@card@daughterCardSlot-3@daughterCard@port-3|alarm-455-3-326");*/
         
         
         soapMessage.saveChanges();
          
         /* Print the request message */
         System.out.print("Request SOAP Message = ");
         soapMessage.writeTo(System.out);
         System.out.println();

         return soapMessage;
    
    
    }

    
	
    private static SOAPMessage filtered(String instanceFullName,String text) throws Exception {
        
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
        QName mycmd = new QName("xmlapi_1.0","generic.GenericObject.filteredFind");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
    //    SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("deployer");
    //    soapBodyElem2.addTextNode("immediate");
  //      SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("urgency");
   //     soapBodyElem3.addTextNode("4");
  //      SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("doDelete");
   //     soapBodyElem4.addTextNode("false");
        SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("instanceFullName");
        soapBodyElem5.addTextNode(instanceFullName);
      //  SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("text");
       // soapBodyElem6.addTextNode(text);
        
       // SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("doMarkAsAcknowledged");
       // soapBodyElem7.addTextNode("true");
      
        SOAPElement soapBodyElem8 = soapBodyElem1.addChildElement("continueOnFailure");
        soapBodyElem8.addTextNode("true");
       
        SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("capabillities");
        soapBodyElem9.setAttribute("set","1"); 
        //soapBodyElem9.addTextNode("1");
        
     //   SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("alteredSeverity");
     //   soapBodyElem10.addTextNode("4");
        
        SOAPElement soapBodyElem11 = soapBodyElem1.addChildElement("scopeType");
        soapBodyElem11.addTextNode("2");
        
       SOAPElement soapBodyElem12 = soapBodyElem1.addChildElement("scopeDepth");
        
        soapBodyElem12.addTextNode("2");
        
     /*  SOAPElement soapBodyElem13 = soapBodyElem1.addChildElement("alarmableInstanceFilter");
        soapBodyElem13.setAttribute("class","fm.AlarmInfo"); 
        
        SOAPElement soapBodyElem13a = soapBodyElem13.addChildElement("equal");
        soapBodyElem13a.setAttribute("name","affectedObjectFullName");
        soapBodyElem13a.setAttribute("value","svc-mgr:service-647:10.247.0.2");  
    //    soapBodyElem13a.setAttribute("name","severity");  
    //    soapBodyElem13a.setAttribute("value","warning"); */
        

        
        
        
   /*     SOAPElement soapBodyElem14 = soapBodyElem1.addChildElement("faultFilter");
        SOAPElement soapBodyElem14a = soapBodyElem14.addChildElement("and");
        soapBodyElem14a.setAttribute("class","fm.AlarmInfo");
        SOAPElement soapBodyElem14b = soapBodyElem14a.addChildElement("equal");
        soapBodyElem14b.setAttribute("name","severity");
        soapBodyElem14b.setAttribute("value","critical");*/
        
        
        soapMessage.saveChanges();
         
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
        usrname.addTextNode(samuser);
   
        SOAPElement password  =  header1.addChildElement("password");
        password.addTextNode(sampassword);
      
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
        
     /*   if (file.exists()) {
      	     file.delete();
        }*/
        
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

    
private static void printSOAPResponseAck(SOAPMessage soapResponse) throws Exception {
    	
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        
     /*   if (file.exists()) {
      	     file.delete();
        }*/
        
        File file = new File("C:/Users/xt20726/Documents/SAM/xmloutput/test.xml");
        System.out.print("\nResponse SOAP Message = ");
     
        StreamResult result = new StreamResult(System.out);
        
        transformer.transform(sourceContent, result);
        
       
        
        BufferedWriter bw = null;
        
        if (!file.exists()) {
   	     file.createNewFile();
   	  
    	FileWriter fw = new FileWriter(file,true);
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
