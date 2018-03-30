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


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SoapAlarm {


	static Date date = new Date() ;
    static //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm") ;
    //Configuration  variables:
    
  //  static Logger log = Logger.getLogger(log4jExample.class.getName());
    
    
    
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
     public static  String labpassword =null;
     public static  String urlprimary=null;
     public static  String urlstandby =null;
     public static String attachment =null;
     public static String confAlarm =null;
     public static String conf =null;
     public static String alarmdef =null;
     public static  String selfFilename = null;
	 
	 public static String networkFilename = null;
     public  static String finalFileName = null;
     
     public  static String outputpath = null;
     public  static String outputfilename = null;
     
     public  static String tempdata =null;
     public  static String alarmNamed =null;
     public  static String alarmCoded =null;
     public  static String alarmTyped =null;
     public  static String alarmPackaged =null;
     public  static String alarmProbaleCaused =null;
    
     
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
		  
			 if (token[0].equals("password")){
				 labpassword = token[1];
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
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
             File file01 = new File("Shelf_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_"+dateFormat.format(date)+".xml") ;
             File file02 = new File("Network_"+dateFormat.format(date)+".xml") ;
             File file1 = new File(DataDir+file01);
             File file2 = new File(DataDir+file02);
             File file3 = new File(DataDir+file03);
    	
             
             
             // Create SOAP Connection
             SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
             SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            
            
              
            SOAPMessage soapResponse1 = soapConnection.call(findAlarmsTo_ucmdb(),url);
             
          //   SOAPMessage soapResponse1 = soapConnection.call( getServiceInfo(),url);  
             printSOAPResponse(soapResponse1, file3);
             
            parseMessage(file3);
              
               
    		 
    	      
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
        	//System.out.println("Dir name : "+filename);
        
        	readConf(filename);
        	
        	// soapToSam();
        	 System.out.println("Configuration data : ");
        	 
        	  System.out.println();
        	  
           	System.out.println("DataDir:  "+DataDir);
        	// System.out.println("password:  "+prodpassword);
        	 System.out.println("urlprimary:  "+urlprimary);
        	 System.out.println("urlstandby:  "+urlstandby);
        	 System.out.println("Conf:  "+conf);
        	 System.out.println("outputpath:  "+outputpath);
        	 System.out.println("outputfilename:  "+outputfilename);
        	 
        	 
        	//selfFilename = DataDir+"shelf_"+dateFormat.format(date)+".csv";
        	 
        	//networkFilename = DataDir+"network_"+dateFormat.format(date)+".csv";
        //    finalFileName = DataDir+"ucmdFinal_"+dateFormat.format(date)+".csv";
        	 
        	     	
        	   
        	 
        	 // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
           
        
            
             
             Date date = new Date() ;
             //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
             File file01 = new File("Shelf_"+dateFormat.format(date)+".xml") ;
             File file03 = new File("Alarm_"+dateFormat.format(date)+".xml") ;
             File file02 = new File("Network_"+dateFormat.format(date)+".xml") ;
             File file1 = new File(DataDir+file01);
             File file2 = new File(DataDir+file02);
             File file3 = new File(DataDir+file03);
             
             System.out.println("SOAP Connection to SAM-O Please wait ::::");
             System.out.println();
             
          // SOAPMessage soapResponse1 = soapConnection.call(findAlarmsTo_ucmdb(),urlprimary);
         
             SOAPMessage soapResponse1 = soapConnection.call(findAlarmsTo_ucmdb(),urlprimary); 
           // SOAPMessage soapResponse1 = soapConnection.call( getServiceInfo() ,urlprimary); 
             
             
           System.out.println("SOAP Resp Body :  ");
           
         //  System.out.println("Print File :  "+file2);
           printSOAPResponse(soapResponse1, file3);
            
            parseMessage(file3);
       
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
        
      //  SOAPElement soapBodyElem19 = soapBodyElem1.addChildElement("</resultFilter>");
        
        //soapBody.addChildElement("</find>");
        
        
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }

	
	
	
	//SOAP TestAlarm
	
    private static SOAPMessage testAlarm() throws Exception {
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
        QName mycmd = new QName("xmlapi_1.0","fm.FaultManager.testAlarm");
        SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("deployer");
        soapBodyElem2.addTextNode("immediate");
        SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("objectInstanceName");
        soapBodyElem3.addTextNode("svc-mgr:service-3:10.1.1.90");
        SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("alarmNameId");
        soapBodyElem4.addTextNode("97");
        SOAPElement soapBodyElem5 = soapBodyElem1.addChildElement("alarmTypeId");
        soapBodyElem5.addTextNode("17");
        SOAPElement soapBodyElem6 = soapBodyElem1.addChildElement("probableCauseId");
        soapBodyElem6.addTextNode("84");
        SOAPElement soapBodyElem7 = soapBodyElem1.addChildElement("severity");
        soapBodyElem7.addTextNode("critical");
      
        SOAPElement soapBodyElem8 = soapBodyElem1.addChildElement("alarmClassTag");
        soapBodyElem8.addTextNode("svc.ServiceSiteDown");
        
        SOAPElement soapBodyElem9 = soapBodyElem1.addChildElement("namingComponent");
      //  soapBodyElem9.addTextNode("Generated from for BSMc prob Testing");
       
        SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("additionalText");
        soapBodyElem10.addTextNode("Generated from for BSMc prob Testing");
        
        SOAPElement soapBodyElem11 = soapBodyElem1.addChildElement("nodeTimeOffset");
        soapBodyElem11.addTextNode("-1");
        
        soapMessage.saveChanges();
         
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
	
	
	
	//SOAP Find Alarms
	
	private static SOAPMessage findAlarmsTo_ucmdb() throws Exception {
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
       password.addTextNode(labpassword);
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
        
        
        
      // SOAPElement soapBodyElem6 = soapBodyElem4.addChildElement("or");
        
      /*  SOAPElement soapBodyElem7 = soapBodyElem4.addChildElement("equal");
        soapBodyElem7.setAttribute("name","severity");
        soapBodyElem7.setAttribute("value","critical");*/
        
        SOAPElement soapBodyElem8 = soapBodyElem4.addChildElement("equal");
        soapBodyElem8.setAttribute("name","olcState");
        soapBodyElem8.setAttribute("value","inService");
       
       /* soapBodyElem7.setAttribute("name","olcState");
        soapBodyElem7.setAttribute("value","inService");*/
       
       
       
       
        
       /*SOAPElement soapBodyElem8 = soapBodyElem6.addChildElement("equal");
       soapBodyElem8.setAttribute("name","severity");
        soapBodyElem8.setAttribute("value","minor");
        
       SOAPElement soapBodyElem9 = soapBodyElem6.addChildElement("equal");
        soapBodyElem9.setAttribute("name","severity");
        soapBodyElem9.setAttribute("value","cleared");
        
        
        SOAPElement soapBodyElem9 = soapBodyElem6.addChildElement("equal");
        soapBodyElem9.setAttribute("name","olcState");
        soapBodyElem9.setAttribute("value","inService");
        
        SOAPElement soapBodyElem91 = soapBodyElem6.addChildElement("equal");
        soapBodyElem91.setAttribute("name","severity");
        soapBodyElem91.setAttribute("value","warning");
     
        SOAPElement soapBodyElem912 = soapBodyElem6.addChildElement("equal");
        soapBodyElem912.setAttribute("name","severity");
        soapBodyElem912.setAttribute("value","major");*/
        
        
        
      
        
       SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
        
       
        
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

     //   SOAPElement soapBodyElem22 = soapBodyElem10.addChildElement("attribute");
      //  soapBodyElem22.addTextNode("additionalText");

        SOAPElement soapBodyElem23 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem23.addTextNode("nodeId");

        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem24.addTextNode("nodeName");

        
        SOAPElement soapBodyElem25 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem25.addTextNode("displayedName");
        
        
        SOAPElement soapBodyElem26 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem26.addTextNode("objectFullName");
        
        SOAPElement soapBodyElem27 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem27.addTextNode("description");
       
        SOAPElement soapBodyElem28 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem28.addTextNode("clearedBy");
        
        SOAPElement soapBodyElem29 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem29.addTextNode("deletedBy");
      
        SOAPElement soapBodyElem30 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem30.addTextNode("additionalText");
        
        SOAPElement soapBodyElem31 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem31.addTextNode("numberOfOccurences");
        
        SOAPElement soapBodyElem32 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem32.addTextNode("applicationDomain");
        
      /*  SOAPElement soapBodyElem33 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem33.addTextNode("applicationDomain");*/
        
        
        SOAPElement soapBodyElem35 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem35.addTextNode("previousSeverity");
        
        SOAPElement soapBodyElem36 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem36.addTextNode("originalSeverity");
        
        SOAPElement soapBodyElem37 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem37.addTextNode("highestSeverity");
        
        SOAPElement soapBodyElem38 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem38.addTextNode("alarmName");
        
        SOAPElement soapBodyElem39 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem39.addTextNode("type");
        
        SOAPElement soapBodyElem40 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem40.addTextNode("specificProblem");
        
        SOAPElement soapBodyElem41 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem41.addTextNode("alarmClassTag");
        
        SOAPElement soapBodyElem42 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem42.addTextNode("correlatingAlarm");
        
        SOAPElement soapBodyElem43 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem43.addTextNode("displayedName");
        
        SOAPElement soapBodyElem34 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem34.addTextNode("olcState");
        
        
        
        SOAPElement soapBodyElem44 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem44.addTextNode("correlatingAlarm");
        
        
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message 
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();*/
     

        return soapMessage;
    }
	
	
	
	
	private static SOAPMessage findAlarms_history_To_ucmdb() throws Exception {
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
       password.addTextNode(labpassword);
       //Prod
     // password.addTextNode("eac2e6703cdcd98fa86a4fd71e15e7c5");
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
       QName mycmd = new QName("xmlapi_1.0","find");
       SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
       
       
       SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("fullClassName");
       soapBodyElem2.addTextNode("fm.AlarmHistoryObject");
       
       
       //SOAPElement soapBodyElemftp = soapBodyElem1.addChildElement("fileName");
       
      // soapBodyElemftp.addTextNode("ftp://samadmin:samadmin@10.247.198.10/clienapp1/"+file);
      //   soapBodyElemftp.addTextNode("home.txt20726.data.inventory.inventory.xml");
       
        SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("filter");
       SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
       soapBodyElem4.setAttribute("class","fm.AlarmHistoryObject");  
      
       SOAPElement soapBodyElemxx = soapBodyElem4.addChildElement("wildcard");
        soapBodyElemxx.setAttribute("name","nodeName");
      soapBodyElemxx.setAttribute("value","memcm01-0051"); 
      
      /* SOAPElement soapBodyElem5 = soapBodyElem4.addChildElement("equal");
       soapBodyElem5.setAttribute("name","applicationDomain");
       soapBodyElem5.setAttribute("value","Routing Management: General");*/
      
       
      //  QName mycmd = new QName("xmlapi_1.0","fm.AlarmHistoryObjects");
     //   SOAPElement soapBodyElem1 = soapBody.addChildElement(mycmd);
       
        /*  SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("faultFilter");
        SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("and");
        soapBodyElem4.setAttribute("class","fm.AlarmHistoryObject.alarmStatus");*/
       
       
        
     /* SOAPElement soapBodyElem10 = soapBodyElem1.addChildElement("resultFilter");
        
       
        
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

     //   SOAPElement soapBodyElem22 = soapBodyElem10.addChildElement("attribute");
      //  soapBodyElem22.addTextNode("additionalText");

        SOAPElement soapBodyElem23 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem23.addTextNode("nodeId");

        SOAPElement soapBodyElem24 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem24.addTextNode("nodeName");

        
        SOAPElement soapBodyElem25 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem25.addTextNode("displayedName");
        
        
        SOAPElement soapBodyElem26 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem26.addTextNode("objectFullName");
        
        SOAPElement soapBodyElem27 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem27.addTextNode("description");
       
        SOAPElement soapBodyElem28 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem28.addTextNode("clearedBy");
        
        SOAPElement soapBodyElem29 = soapBodyElem10.addChildElement("attribute");
        soapBodyElem29.addTextNode("deletedBy");
        SOAPElement soapBodyElem30 = soapBodyElem10.addChildElement("children");*/
        
        soapMessage.saveChanges();

        /* Print the request message */
        
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
     

        return soapMessage;
    }
	
	
	
	
	
	
	//Parse Alarm to ucmdb format
	
	

    
    public static void  parseMessage(File file1 ){
    
    	 String Alarm =null;
    	 String Alarm_Name =null;
    	 String Alarm_ProbableCause =null;
    	 
    	 //System.out.println("SAWADOGO IN PARSING   : ");
    	 
    	  Date date = new Date() ;
         //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
        
         //File file03 = new File("Alarm_To_BSMc_"+dateFormat.format(date)+".xml") ;
        // File file03 = new File(outputfilename) ;
        
         
         File outputfile = new File(outputpath+outputfilename);
         System.out.println("File Path : " + outputfile);
        try { 
        //File file1 = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+filename);
        //File filed = new File("C:/Users/xt20726/Documents/SAM/xmlbackup/"+filename);
        	//File file = new File("C:/"+Filename);
       	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

       	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       	
       	 Document doc =  dBuilder.parse(file1);

       	 
             doc.getDocumentElement().normalize();
               
              System.out.println("root of xml file : " +file1);

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
               
               
            /*   System.out.println("severity: " + getValue("severity", element));
               System.out.println("probableCause: " + getValue("probableCause", element));
               System.out.println("alarmName: " + getValue("alarmName", element));
               System.out.println("type: " + getValue("type", element));
               System.out.println("specificProblem: " + getValue("specificProblem", element));
               System.out.println("affectedObjectFullName: " + getValue("affectedObjectFullName", element));
               System.out.println("affectedObjectClassName: " + getValue("affectedObjectClassName", element));
               System.out.println("objectFullName: " + getValue("objectFullName", element));
               
               System.out.println("isAcknowledged: " + getValue("isAcknowledged", element));
               System.out.println("firstTimeDetected: " + getValue("firstTimeDetected", element));
               System.out.println("isServiceAffecting: " + getValue("isServiceAffecting", element));
               System.out.println("additionalText: " + getValue("additionalText", element));
               System.out.println("nodeId: " + getValue("nodeId", element));
               System.out.println("nodeName: " + getValue("nodeName", element));
               System.out.println("affectedObjectDisplayedName: " + getValue("affectedObjectDisplayedName", element)); 
               System.out.println("applicationDomain: " + getValue("applicationDomain", element));
               System.out.println("displayedClass: " + getValue("displayedClass", element));
               System.out.println("alarmClassTag: " + getValue("alarmClassTag", element));
               System.out.println("olcState: " + getValue("olcState", element));
               System.out.println("displayedName: " + getValue("displayedName", element));*/
               
               
          //   String affectedObjectDisplayedName =getValue("alarmClassTag", element);  
           
           // String tempdata = readFileLine("C:/Users/xt20726/Documents/SAM/alarms/config/GlobalConfig.txt", getValue("alarmName", element));
                
            //   String confpath= conf+alarmdef;
               
             //  System.out.println("Conf file path: "  +conf); 
             //  System.out.println("SAWADOGO: "  +conf+alarmdef);
               
                tempdata = readFileLine(conf+alarmdef, getValue("alarmName", element));
            
             //   System.out.println("SAWADOGO: "  +tempdata);
            
            
            //System.out.println("AlarmName from xml :  " + getValue("alarmName", element) +"AlarmName_ProbableCause from read file : " +tempdata);
       		
               Element alarm_data =docOut.createElement("alarm_data");
          	   alarmInfo.appendChild(alarm_data); 
            
          if(tempdata !=null){
            	
        //	    System.out.println("TEMPDATA: "  +tempdata);
            	String token[] = tempdata.split(":");
            	
            	
            	//Alarm_Name =  token[0]+":"+"Check in SAM to get more info";
            	Alarm_Name =  token[0];
             	Alarm_ProbableCause = token[1];
                alarmNamed = token[0];
                alarmCoded = token[1];
                alarmTyped = token[2];
                alarmProbaleCaused = token[3];
                alarmPackaged = token[4];
             	
           //    Element alarm_data =docOut.createElement("alarm_data");
          	 //  alarmInfo.appendChild(alarm_data);
       		   Element title = docOut.createElement("title");
       		
    	    	title.appendChild(docOut.createTextNode(Alarm_Name));
    		    alarm_data.appendChild(title);
    		
    		    Element description = docOut.createElement("description");
    		    description.appendChild(docOut.createTextNode("MPLS SAM Alarm"));
    		     alarm_data.appendChild(description);
    		     
    		 	Element alarmName = docOut.createElement("alarmName");
        		alarmName.appendChild(docOut.createTextNode(alarmNamed));
        		alarm_data.appendChild(alarmName);
        		
        		
        		Element alarmCode = docOut.createElement("alarmCode");
        		alarmCode.appendChild(docOut.createTextNode(alarmCoded));
        		alarm_data.appendChild(alarmCode);
        		
        		
        		Element alarmType = docOut.createElement("alarmType");
        		alarmType.appendChild(docOut.createTextNode(alarmTyped));
        		alarm_data.appendChild(alarmType);
        		
        		Element alarmProbableCause = docOut.createElement("alarmProbableCause");
        		alarmProbableCause.appendChild(docOut.createTextNode(alarmProbaleCaused));
        		alarm_data.appendChild(alarmProbableCause);
                
        		Element alarmPackage = docOut.createElement("alarmPackage");
        		alarmPackage.appendChild(docOut.createTextNode(alarmPackaged));
        		alarm_data.appendChild(alarmPackage); 
        		
        		//Start here
        		
        		Element highestSeverity = docOut.createElement("highestSeverity");
        		highestSeverity.appendChild(docOut.createTextNode(getValue("highestSeverity", element)));
        		alarm_data.appendChild(highestSeverity);
        		
        		Element affectedObjectFullName = docOut.createElement("affectedObjectFullName");
        		affectedObjectFullName.appendChild(docOut.createTextNode(getValue("affectedObjectFullName", element)));
        		alarm_data.appendChild(affectedObjectFullName);
        		
        		
        		Element isAcknowledged = docOut.createElement("isAcknowledged");
        		isAcknowledged.appendChild(docOut.createTextNode(getValue("isAcknowledged", element)));
        		alarm_data.appendChild(isAcknowledged);
        		

        		Element firstTimeDetected = docOut.createElement("firstTimeDetected");
        		firstTimeDetected.appendChild(docOut.createTextNode(getValue("firstTimeDetected", element)));
        		alarm_data.appendChild(firstTimeDetected);
        		
        		Element lastTimeDetected = docOut.createElement("lastTimeDetected");
        		lastTimeDetected.appendChild(docOut.createTextNode(getValue("lastTimeDetected", element)));
        		alarm_data.appendChild(lastTimeDetected);
        		
        		
        		Element numberOfOccurences = docOut.createElement("numberOfOccurences");
        		numberOfOccurences.appendChild(docOut.createTextNode(getValue("numberOfOccurences", element)));
        		alarm_data.appendChild(numberOfOccurences);
        		
        		Element additionalText = docOut.createElement("additionalText");
        		additionalText.appendChild(docOut.createTextNode(getValue("additionalText", element)));
        		alarm_data.appendChild(additionalText);
        		
        		Element displayedName = docOut.createElement("displayedName");
        		displayedName.appendChild(docOut.createTextNode(getValue("displayedName", element)));
        		alarm_data.appendChild(displayedName);
        		
        		
        		Element correlatingAlarm = docOut.createElement("correlatingAlarm");
        		correlatingAlarm.appendChild(docOut.createTextNode(getValue("correlatingAlarm", element)));
        		alarm_data.appendChild(correlatingAlarm);
        		
        		
        		
    		   
            }else{
            	
            //   Element alarm_data =docOut.createElement("alarm_data");
          	//   alarmInfo.appendChild(alarm_data);
            	
            	String mytemp = getValue("objectFullName", element);
            	String token2[] = mytemp.split("\\|");
            	
            	//String myAlarmetitle=token2[0]+":"+"Check in SAM to get more info";
            	String myAlarmetitle=getValue("alarmClassTag", element)+":"+"Node is :"+getValue("nodeName", element)+": " +"Check in SAM to get more info";
            	
            	String token3[] = token2[1].split("-");
            	
            	
            	//String AlarmID =token3[0]+"-"+token3[1]+"-"+token3[2]+"-"+token3[3];
            	String descrip ="MPLS SAM Alarm";
            	
            	//System.out.println("Object Full Name: " + mytemp);
            	//System.out.println("Alarm ID : " + AlarmID);
            	//System.out.println("token2[0]: " + token2[0]);
            	//System.out.println("token2[1]: " + token2[1]);
            	
            	
            	if(myAlarmetitle !=null){
            	Element title = docOut.createElement("title");
                title.appendChild(docOut.createTextNode(myAlarmetitle));
        		alarm_data.appendChild(title);
            	}
            	
            	
        		if(descrip !=null){
        		Element description = docOut.createElement("description");
        		description.appendChild(docOut.createTextNode(descrip));
        		alarm_data.appendChild(description);
        		}
        		
        		if(getValue("alarmName", element) !=null){
        		Element alarmCode = docOut.createElement("alarmCode");
        		alarmCode.appendChild(docOut.createTextNode(getValue("alarmName", element)));
        		alarm_data.appendChild(alarmCode);
        		}
        		
        		//Start here
        		if(getValue("highestSeverity", element) !=null){
        		Element highestSeverity = docOut.createElement("highestSeverity");
        		highestSeverity.appendChild(docOut.createTextNode(getValue("highestSeverity", element)));
        		alarm_data.appendChild(highestSeverity);
        		}
        		
        		if(getValue("affectedObjectFullName", element) !=null){
        		Element affectedObjectFullName = docOut.createElement("affectedObjectFullName");
        		affectedObjectFullName.appendChild(docOut.createTextNode(getValue("affectedObjectFullName", element)));
        		alarm_data.appendChild(affectedObjectFullName);
        		}
        		
        		if(getValue("isAcknowledged", element) !=null){
        		Element isAcknowledged = docOut.createElement("isAcknowledged");
        		isAcknowledged.appendChild(docOut.createTextNode(getValue("isAcknowledged", element)));
        		alarm_data.appendChild(isAcknowledged);
        		}       		
                
        		if(getValue("firstTimeDetected", element) !=null){
        		Element firstTimeDetected = docOut.createElement("firstTimeDetected");
        		firstTimeDetected.appendChild(docOut.createTextNode(getValue("firstTimeDetected", element)));
        		alarm_data.appendChild(firstTimeDetected);
        		} 
        		
        		if(getValue("lastTimeDetected", element) !=null){
        		Element lastTimeDetected = docOut.createElement("lastTimeDetected");
        		lastTimeDetected.appendChild(docOut.createTextNode(getValue("lastTimeDetected", element)));
        		alarm_data.appendChild(lastTimeDetected);
        		} 
        		
        		
        		if(getValue("numberOfOccurences", element) !=null){
        		Element numberOfOccurences = docOut.createElement("numberOfOccurences");
        		numberOfOccurences.appendChild(docOut.createTextNode(getValue("numberOfOccurences", element)));
        		alarm_data.appendChild(numberOfOccurences);
        		} 
        		
        		
        		if(getValue("additionalText", element) !=null){
        		Element additionalText = docOut.createElement("additionalText");
        		additionalText.appendChild(docOut.createTextNode(getValue("additionalText", element)));
        		alarm_data.appendChild(additionalText);
        		} 
        		
        		if(getValue("displayedName", element) !=null){
        		Element displayedName = docOut.createElement("displayedName");
        		displayedName.appendChild(docOut.createTextNode(getValue("displayedName", element)));
        		alarm_data.appendChild(displayedName);
        		} 	
            
        		if(getValue("correlatingAlarm", element) !=null){
        		Element correlatingAlarm = docOut.createElement("correlatingAlarm");
        		correlatingAlarm.appendChild(docOut.createTextNode(getValue("correlatingAlarm", element)));
        		alarm_data.appendChild(correlatingAlarm);
        		}
            
            }
    		
        //  Element alarm_data =docOut.createElement("alarm_data");
   	  //    alarmInfo.appendChild(alarm_data);
         //  System.out.println("SAWADOGO: "  +tempdata);
          // String tokend[] = tempdata.split(":");
            
    		Element severity = docOut.createElement("severity");
    		severity.appendChild(docOut.createTextNode(getValue("severity", element)));
    		alarm_data.appendChild(severity);
    		
    		Element state = docOut.createElement("state");
    		state.appendChild(docOut.createTextNode("OPEN"));
    		alarm_data.appendChild(state);
    		
    		
    		
    		Element time_created = docOut.createElement("time_created");
    		time_created.appendChild(docOut.createTextNode(getValue("firstTimeDetected", element)));
    		alarm_data.appendChild(time_created);

    		Element node = docOut.createElement("node");
    		node.appendChild(docOut.createTextNode(getValue("nodeName", element)));
    		alarm_data.appendChild(node);
             
    		Element objectFullName = docOut.createElement("objectFullName");
    		objectFullName.appendChild(docOut.createTextNode(getValue("objectFullName", element)));
    		alarm_data.appendChild(objectFullName);
            
    		Element affectedObjectFullName = docOut.createElement("affectedObjectFullName");
    		affectedObjectFullName.appendChild(docOut.createTextNode(getValue("affectedObjectFullName", element)));
    		alarm_data.appendChild(affectedObjectFullName);
    		
    		
    		Element clearedBy = docOut.createElement("clearedBy");
    		clearedBy.appendChild(docOut.createTextNode(getValue("clearedBy", element)));
    		alarm_data.appendChild(clearedBy);
    		
    		Element deletedBy = docOut.createElement("deletedBy");
    		deletedBy.appendChild(docOut.createTextNode(getValue("deletedBy", element)));
    		alarm_data.appendChild(deletedBy);
    		
    		Element firstTimeDetected = docOut.createElement("firstTimeDetected");
    		firstTimeDetected.appendChild(docOut.createTextNode(getValue("firstTimeDetected", element)));
    		alarm_data.appendChild(firstTimeDetected);
           
    		Element lastTimeDetected = docOut.createElement("lastTimeDetected");
    		lastTimeDetected.appendChild(docOut.createTextNode(getValue("lastTimeDetected", element)));
    		alarm_data.appendChild(lastTimeDetected);
    		
    		
    		Element numberOfOccurences = docOut.createElement("numberOfOccurences");
    		numberOfOccurences.appendChild(docOut.createTextNode(getValue("numberOfOccurences", element)));
    		alarm_data.appendChild(numberOfOccurences);
    		
    		Element applicationDomain = docOut.createElement("applicationDomain");
    		applicationDomain.appendChild(docOut.createTextNode(getValue("applicationDomain", element)));
    		alarm_data.appendChild(applicationDomain);
    		
    		Element olcState = docOut.createElement("olcState");
    		olcState.appendChild(docOut.createTextNode(getValue("olcState", element)));
    		alarm_data.appendChild(olcState);
    		
    		Element alarmClassTag = docOut.createElement("alarmClassTag");
    		alarmClassTag.appendChild(docOut.createTextNode(getValue("alarmClassTag", element)));
    		alarm_data.appendChild(alarmClassTag);
    		
    	/*	Element alarmName = docOut.createElement("alarmName");
    		alarmName.appendChild(docOut.createTextNode(alarmNamed));
    		alarm_data.appendChild(alarmName);
    		
    		
    		Element alarmCode = docOut.createElement("alarmCode");
    		alarmCode.appendChild(docOut.createTextNode(alarmCoded));
    		alarm_data.appendChild(alarmCode);
    		
    		
    		Element alarmType = docOut.createElement("alarmType");
    		alarmType.appendChild(docOut.createTextNode(alarmTyped));
    		alarm_data.appendChild(alarmType);
    		
    		Element alarmProbableCause = docOut.createElement("alarmProbableCause");
    		alarmProbableCause.appendChild(docOut.createTextNode(alarmProbaleCaused));
    		alarm_data.appendChild(alarmProbableCause);
            
    		Element alarmPackage = docOut.createElement("alarmPackage");
    		alarmPackage.appendChild(docOut.createTextNode(alarmPackaged));
    		alarm_data.appendChild(alarmPackage);*/
    		
    		
    		
    		
    		
            }
              
               
               }
              
               //StreamResult result = new StreamResult(new File("C:/Users/xt20726/Documents/SAM/xmloutput/test_ucmdb.xml"));
               StreamResult result = new StreamResult(outputfile);
               transformer.transform(source, result);
               
               }
             
      
               } catch (Exception ex) {

               ex.printStackTrace();

               }
    	
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
