/******************************************************************************
 *
 * $RCSfile: JmsTest.java,v $
 *
 ****************************************************************************
 *
 * $Revision: 1.3 $
 *
 ****************************************************************************
 *
 * Copyright (c) 2008-2010 Alcatel-Lucent, Inc. All Rights Reserved.
 * Please read the associated COPYRIGHTS file for more details.
 *
 ****************************************************************************
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Hashtable;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Class       : JmsTest.java
 * <p/>
 * Description: This file contains the main() function call to connect to the
 * SAMO topics and print the events received
 * <p/>
 * Disclaimer : This code sample is provided for illustrative purposes only.
 * Alcatel shall have no responsibility to correct any defects or
 * errors in this code or to assure that it operates properly.
 */
public class JmsTest extends Thread implements ExceptionListener,
    MessageListener
{
    /**
     * Support for 5.0 external JMS context.
     */
    private static final String JMS_CONTEXT = "external/5620SamJMSServer";

    /**
     * The connection factory.
     */
    protected static final String CONNECTION_FACTORY = "SAMConnectionFactory";

    /**
     * The port seperator character for initial context construction.
     */
    protected static final char PORT_SEP_CHAR = ':';

    /**
     * The URL seperator character for initial context construction.
     */
    protected static final char URL_SEP_CHAR = '/';

    /**
     * The seperator character for initial context construction.
     */
    private static final char MULTI_SEP_CHAR = ',';

    /**
     * The list of available topics.
     * Note: These may not be correct based on configuration of the
     * attributes in nms-server.xml.
     */
    private static final String[] AVAILABLE_TOPICS = new String[]{
        "5620-SAM-topic-xml", "5620-SAM-topic-xml-general", "5620-SAM-topic-xml-file",
        "5620-SAM-topic-xml-fault", "5620-SAM-topic-xml-stats"
    };

    /**
     * This is the topic string name.
     */
    protected String strName;

    /**
     * This is the URL string for a topic.
     */
    protected String strUrl;

    /**
     * This is the port for the topic.
     */
    protected String port;

    /**
     * This is the high availability URL string for a topic.
     */
    protected String strHaUrl;

    /**
     * This is the high availability port for the topic.
     */
    protected String haPort;

    /**
     * This is the client id for the topic.
     */
    protected String clientId;

    /**
     * This is the accepted client id for the topic. This attribute
     * contains the client id that was successfully registered with the server.
     */
    protected String acceptedClientId;

    /**
     * This is the user name for the topic.
     */
    protected String user;

    /**
     * This is the password of the user for the topic.
     */
    protected String password;

    /**
     * This is the message selector for the topic.
     */
    protected String filter;

    /**
     * This identifies if the consumer is listening to the topic.
     */
    protected boolean isListening;

    /**
     * This identifies if the consumer is connected.
     */
    protected boolean isConnected = false;

    /**
     * This identifies if the consumer has been stopped.
     */
    protected boolean isStopped = false;

    /**
     * The JNDI context of the connection.
     */
    private Context jndiContext = null;

    /**
     * The topic connection factory.
     */
    private TopicConnectionFactory topicConnectionFactory = null;

    /**
     * The topic connection.
     */
    private TopicConnection topicConnection = null;

    /**
     * The topic session.
     */
    private TopicSession topicSession = null;

    /**
     * The topic.
     */
    private Topic topic = null;

    /**
     * The topic subscriber.
     */
    private TopicSubscriber topicSubscriber = null;

    /**
     * Identifies if the subscriber is durable.
     */
    private boolean isPersistent = false;

    /**
     * Identifies if high availability is enabled.
     */
    private boolean isHaEnabled = false;
    
    public static  boolean flag=false;

    /**
     * Counter for total number of messages.
     */
    private static int counter = 1;
    
    public   String Alarmtest =null ;
   // private static String Alarm =null ;
     public static String  Filename =null;
     
     public static String     FileOutput =null;

    /**
     * Constructor for creating an instance of JmsTest.
     *
     * @param aInTopic The topic to connect to.
     * @param aInUrl The server to connect to.
     * @param aInHaUrl The high availability server.
     * @param aInId The unique id for the connection.
     * @param aInUser The user to connect to the server with.
     * @param aInPassword The password for the user.
     * @param aInIsPersistent If the client is durable or not.
     * @param aInFilter The filter to use for the subscription.
     */
    public JmsTest(String aInTopic, String aInUrl, String aInHaUrl, String aInId,
                   String aInUser, String aInPassword, boolean aInIsPersistent,
                   String aInFilter)
    {
        String[] lUrlPort = aInUrl.split(":");
        strName = aInTopic;
        strUrl = lUrlPort[0];
        port = lUrlPort[1];
        clientId = aInId;
        user = aInUser;
        password = aInPassword;
        isPersistent = aInIsPersistent;
        filter = aInFilter;
        if (aInHaUrl != null)
        {
            String[] lHaUrlPort = aInHaUrl.split(":");
            isHaEnabled = true;
            haPort = lHaUrlPort[1];
            strHaUrl = lHaUrlPort[0];
        }
    }

    /**
     * This method identifies if the JMS subscription is persistent.
     *
     * @return True if persistent, false if non-persistent.
     */
    public boolean isPersistent()
    {
        return isPersistent;
    }

    /**
     * This method is called by message service for each event received.
     *
     * @param aInMessage The event received
     */
    public void onMessage(Message aInMessage)
    {
    	 String Alarm=null; 
    	 String nodeName =null;
       
    	
    	 try
        {
            System.out.print("Event " + counter);
            Filename="soapEvent"+counter+"."+"xml";
            String content ;
            
           
            
            
            
           File file = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename);
           // File file = new File("C:/"+Filename);
            if (aInMessage instanceof TextMessage)
            {
                content = ((TextMessage) aInMessage).getText();
            	
                System.out.println(" Message: " + ((TextMessage) aInMessage).getText());
                BufferedWriter bw = null;
                
                if (!file.exists()) {
           	     file.createNewFile();
           	  
            	FileWriter fw = new FileWriter(file);
        	    bw = new BufferedWriter(fw);
        	    bw.write (content);
        	    bw.flush();
        	    bw.close();
        	    System.out.println("File written Successfully");
        	    
    
                
                }
                
                
                
               // decodeSoap((SOAPMessage) aInMessage);
                
                System.out.println();
                counter++;
            }
            // In SAM 5.0, TextMessages are encapsulated in Object Messages.  The following
            // code allows the TextMessage to be unwrapped and processed as normal.
            // This will allow backwards compatability with previous versions of SAM.
            // NOTE: JMS header properties are contained in the Object Message, not the
            //        encapsulated TextMessage.
            else if (aInMessage instanceof ObjectMessage)
            {
                Object lObject = ((ObjectMessage) aInMessage).getObject();
                if (lObject != null && lObject instanceof Message)
                {
                    onMessage((Message) lObject);
                }
            }
            else
            {
                System.out.println("Invalid Message Type.");
            }
        }
        catch (Throwable e)
        {
            System.out.println("Exception: " + e.toString() + aInMessage);
        }
    }

   
    
    
    public static String listFilesForFolder(final File folder) throws Exception{
    	
    	String Alarm =null;
    	int i =0;
    	 for (final File fileEntry : folder.listFiles()) {
    		 System.out.println("Number of I: "+i);
    	            System.out.println("File name in Saved directory: "+fileEntry.getName());
    	            
    	            
    	            if(fileEntry.getName() !=null){
    	             Alarm = parseMessage(fileEntry.getName());
    	             System.out.println("File name in List :" +fileEntry.getName());
    	             flag =false;
    	           // System.out.println("===========================");
    	            if (Alarm !=null){
    	            System.out.println("Alarm name :" + Alarm);
    	            }
    	            }
    	            
    	        i++;
    	    }
    	
    	return Alarm;
    }
    
    
    public static boolean getFlag(final File folder) throws Exception{
    	
    	boolean myflag =false;
    	 for (final File fileEntry : folder.listFiles()) {
    		
    	            
    	            if(fileEntry.getName() != null){
    	            FileOutput =	fileEntry.getName();
    	            myflag = true;
    	            System.out.println("Iam if : " +myflag );
    	           
    	            }else{
    	            	myflag=false;
    	            }
    	            
    	        
    	    }
    	
    	return myflag ;
    }
    
    private static void deleteFiles(File file) {
        if (file.isDirectory())
            for (File f : file.listFiles()){
                deleteFiles(f);
          //  System.out.println("I am deleting file:" + f);
            }else
            //	System.out.println("I am deleting file in else:");
            file.delete();
    }
    
    public static String parseObjectName(String Alarm){
    	
    	String delims1 = "[$]";
    	String delims2 = "[=]";
    	String ObjectName =null;
    	String[] tokens = Alarm.split(delims1);
    	for (int i = 0; i < tokens.length; i++){
    	    
    		System.out.println(tokens[i]);
    		
    		String[] tokens2 = tokens[i].split(delims2);
    		
    		for (int j = 0; j < tokens2.length; j++){
    			
    			System.out.println("Name Object: " + tokens2[0]);
    			System.out.println("Value Object: " + tokens2[1]);
    			String objectname = tokens2[0];
    			//String name="sawadogo";
    			if(objectname.equals("objectFullName")){
    				
    				//System.out.println("Value Object in If Loop: " + tokens2[1]);
    			    ObjectName = tokens2[1];
    			}
    			
    		}
    		
    	}
        return ObjectName;
    }
    
   
    
    private static void copyFileUsingFileStreams(File source, File dest) throws IOException { 

    	InputStream input = null; 

    	OutputStream output = null; 
            try { 

                 input = new FileInputStream(source); 

                 output = new FileOutputStream(dest); 

                 byte[] buf = new byte[1024]; 

                 int bytesRead; 

                 while ((bytesRead = input.read(buf)) > 0) { 
                   output.write(buf, 0, bytesRead); 
    	            } 
    	 
    	    } finally { 

    	        input.close(); 

    	        output.close(); 
    	 
    	    } 



    	} 

    File find = new File("C:/Users/xt20726/Documents/SAM/xmloutput/");
    File[] matchingFiles = find.listFiles(new FilenameFilter() {
        
    	public boolean accept(File dir, String name) {
        		
        	
            return name.startsWith(name) && name.endsWith("xml");
        }
    });
    
    
    
    
    
    public static String  getFullObjectName(String Filename){
        
   	 String FullObjectName =null;
  	    
       try { 
         File file = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

      	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      	
      	 Document doc =  dBuilder.parse(file);

      	 
            doc.getDocumentElement().normalize();
              
             // System.out.println("root of xml file : " +counter+ ":::::"+ doc.getDocumentElement().getNodeName());

              NodeList nodes = doc.getElementsByTagName("fm.AlarmInfo");
              
              
              
              for (int i = 0; i < nodes.getLength(); i++) {

              Node node =  nodes.item(i);
                  
              if (node.getNodeType() == Node.ELEMENT_NODE) {

              Element element = (Element) node;
              
              System.out.println("affectedObjectFullName: " + getValue("affectedObjectFullName", element));
              
               FullObjectName = getValue("affectedObjectFullName", element);
            
              
              }

              }
            
              
              } catch (Exception ex) {

              ex.printStackTrace();

              }
           	return FullObjectName;
   }
   

    
    public static String  getAlarmSeverity(String Filename){
        
      	 String Severity =null;
     	    
          try { 
             File file = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename);
             DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

         	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         	
         	 Document doc =  dBuilder.parse(file);

         	 
               doc.getDocumentElement().normalize();
                 
                // System.out.println("root of xml file : " +counter+ ":::::"+ doc.getDocumentElement().getNodeName());

                 NodeList nodes = doc.getElementsByTagName("fm.AlarmInfo");
                 
 
                 
                 for (int i = 0; i < nodes.getLength(); i++) {

                 Node node =  nodes.item(i);
                     
                 if (node.getNodeType() == Node.ELEMENT_NODE) {

                  Element element = (Element) node;
                 
                  System.out.println("severity: " + getValue("severity", element));
                 
                  Severity = getValue("severity", element);
               
                 
                 }

                 }
               
                 
                 } catch (Exception ex) {

                 ex.printStackTrace();

                 }
              	return Severity;
      }
      
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static String  parseMessage(String FilenameIN){
    
    	 String Alarm =null;
   	    
        try { 
        File file1 = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+FilenameIN);
        File filed = new File("C:/Users/xt20726/Documents/SAM/xmlbackup/"+FilenameIN);
        	//File file = new File("C:/"+Filename);
       	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

       	 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       	
       	 Document doc =  dBuilder.parse(file1);

       	 
             doc.getDocumentElement().normalize();
               
              // System.out.println("root of xml file : " +counter+ ":::::"+ doc.getDocumentElement().getNodeName());

               NodeList nodes = doc.getElementsByTagName("fm.AlarmInfo");
               
        if(nodes.getLength()>0){       
               System.out.println("Nodes Length: " + nodes.getLength());

               System.out.println("==========================");

               for (int i = 0; i < nodes.getLength(); i++) {

               Node node =  nodes.item(i);
                   
               if (node.getNodeType() == Node.ELEMENT_NODE) {

               Element element = (Element) node;
               
               
               System.out.println("severity: " + getValue("severity", element));
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
               System.out.println("displayedName: " + getValue("displayedName", element));
               
               Alarm ="Nodename"+"="+getValue("nodeName", element)+"$"+"Severity"+"="+getValue("severity", element)+"$"+"ProbableCause"+"="+getValue("probableCause", element)+"$"+"AlarmType"+"="+getValue("type", element)+"$"+"SpecificProblem"+"="+getValue("specificProblem", element)
               +"$"+"AffectedObjectFullName"+"="+getValue("affectedObjectFullName", element)+"$"+"AffectedObjectClassName"+"="+getValue("affectedObjectClassName", element)+"$"+"objectFullName"+"="+getValue("objectFullName", element);
               System.out.println("==========================");
                   
               
               }
               }
               }
               // file.move("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename,"C:/Users/xt20726/Documents/SAM/xmlbackup/"+Filename);
                
        if(file1.exists()){
                copyFileUsingFileStreams(file1, filed);
               
               file1.delete();
               }
               } catch (Exception ex) {

               ex.printStackTrace();

               }
    	return Alarm;
    }
    
    
    
    
    
    
        private static String getValue(String tag, Element element) {

    	NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();

    	Node node = (Node) nodes.item(0);

    	return node.getNodeValue();

    	}

        

        public  void  setAlarm(String Alarm) {

        	Alarmtest = Alarm;

        	}


        public  String  getAlarm() {

        	return this.Alarmtest ;

        	}

        
      public static void writeToAndReadFromSocket(String server, int port,String writeTo) throws Exception
        {
         
    	  
    	  Socket socket=null;
          
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
          } 
          catch (SocketTimeoutException ste) 
          {
            System.err.println("Timed out waiting for the socket.");
            ste.printStackTrace();
            throw ste;
          }
    	  
    
    	  
    	  try
          {
            // write text to the socket
         // System.out.println("Sending request to Socket Server: "+writeTo);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(writeTo);
           // System.out.println("Sending Fini !!!: ");
            bufferedWriter.flush();
            socket.close();
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
         
        public static Socket  openSocket(String server, int port) throws Exception
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
             
            
          } 
          catch (SocketTimeoutException ste) 
          {
            System.err.println("Timed out waiting for the socket.");
            ste.printStackTrace();
            throw ste;
          }
          return socket;
        }  
    
    
    /**
     * This method is called to initialize the connection to the
     * server.
     *
     * @throws Exception The exception thrown if a conneciton error occurs.
     */
    public void initializeConnection() throws Exception
    {
        try
        {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            env.put("jnp.disableDiscovery", "true");
            env.put("jnp.timeout", "60000");

            // check if redundancy is enabled, if so, add the high availability
            // URL to the initial context.
            if (isHaEnabled)
            {
                env.put(Context.PROVIDER_URL, strUrl + PORT_SEP_CHAR + port +
                    MULTI_SEP_CHAR + strHaUrl + PORT_SEP_CHAR + haPort);
                System.out.println("URL for app server: " + strUrl + " Redundant URL: " + strHaUrl);
            }
            else
            {
                env.put(Context.PROVIDER_URL,
                    "jnp://" + strUrl + PORT_SEP_CHAR + port + URL_SEP_CHAR);
                System.out.println("Standalone URL for app server: " + strUrl);
            }
            jndiContext = new InitialContext(env);

            System.out.println("Initializing topic (" + strName + ")...");

            // get the topic connection factory.
            //
            // If you just support SAM 5.0+, you can remove the try/catch below and replace
            // it with:
            //	topicConnectionFactory = getExternalFactory(jndiContext);
            //
            try
            {
                // For redundancy, the following connection factories should be
                // used:
                // SAM Release: 2.1 - 3.0 R2 - 5620SAMConnectionFactory
                // For standalone, the following connection factories should be 
                // used:
                // SAM Release: 2.1 - 3.0 R2 - UIL2ConnectionFactory
                //
                // After Release 3.0 R3, the following connection factory is used
                // in both cases: SAMConnectionFactory
                topicConnectionFactory = (TopicConnectionFactory)
                    jndiContext.lookup(CONNECTION_FACTORY);
            }
            catch (Exception e)
            {
                // For SAM 5.0 support of the external JMS server.
                topicConnectionFactory = getExternalFactory(jndiContext);
            }

            // To use persistent JMS, the user must have durable subscription
            // permission (i.e. durable subscription role).
            if (user != null)
            {
                topicConnection = topicConnectionFactory.createTopicConnection(user, password);
                System.out.println("Connection created for user: " + user);
            }
            else
            {
                topicConnection = topicConnectionFactory.createTopicConnection();
                System.out.println("Connection created.");
            }

            // Check for persistant JMS, if so, set the unique client id.
            // IMPORTANT: Client Id must be unique! In case of connection failure,
            // it identifies which messages this client missed.
            if ((isPersistent) && (null == clientId))
            {
                System.out.println("Client ID cannot be null for a durable subscription.");
                throw new JMSException("Client ID cannot be null for a durable subscription.");
            }
            if ((null != clientId) && (!"".equals(clientId)))
            {
                topicConnection.setClientID(clientId);
                System.out.println("Using client id: " + clientId);
            }

            // create the topic session.
            topicSession = topicConnection.createTopicSession(true,TopicSession.AUTO_ACKNOWLEDGE);
                //TopicSession.AUTO_ACKNOWLEDGE);
            		
            System.out.println("Topic session created.");

            // find the topic.
            try
            {
                topic = (Topic) jndiContext.lookup(strName);
            }
            catch (NamingException ne)
            {
                // For SAM 5.0 support of the external JMS server.
                Context lInitialContext = (Context) jndiContext.lookup(JMS_CONTEXT);
                topic = (Topic) lInitialContext.lookup(strName);
            }
            System.out.println("Finished initializing topic...");

            // create topic subscriber based on persistance.
            if (isPersistent)
            {
                // This is where the subscriber is created with durable subscription
                // for persistant JMS.  The client must specify a name that uniquely
                // identifies each durable subscription it creates.
                if (null != filter)
                {
                    topicSubscriber =
                        topicSession.createDurableSubscriber(topic, clientId, filter, false);
                    System.out.println("Durable topic subscriber created with filter: " + filter);
                }
                else
                {
                    topicSubscriber =
                        topicSession.createDurableSubscriber(topic, clientId);
                    System.out.println("Durable topic subscriber created.");
                }
            }
            else
            {
                if (null != filter)
                {
                    topicSubscriber = topicSession.createSubscriber(topic, filter, false);
                    System.out.println("Topic subscriber created with filter: " + filter);
                }
                else
                {
                    topicSubscriber = topicSession.createSubscriber(topic);
                    System.out.println("Topic subscriber created.");
                }
            }
            acceptedClientId = topicConnection.getClientID();
            System.out.println("Client id: " + topicConnection.getClientID());
            setMessageListener(this);
            setExceptionListener(this);
            startListening();
            isConnected = true;
            System.out.println("Connected and listening...");
        }
        catch (Throwable jmse)
        {
            if (topicSession != null)
            {
                topicSession.close();
            }
            if (topicConnection != null)
            {
                topicConnection.close();
            }
            System.out.println("Exception: " + jmse.getMessage());
            isConnected = false;
            throw new JMSException(jmse.getMessage());
        }
    }

    /**
     * This method is used for the seperate SAM JMS server (SAM 5.0+).
     *
     * @param aInContext The initial context for the SAM server.
     *
     * @return The found connection factory.
     *
     * @throws NamingException If the factory could not be found.
     */
    private TopicConnectionFactory getExternalFactory(Context aInContext) throws NamingException
    {
        try
        {
            Context lInitialContext = (Context) aInContext.lookup(JMS_CONTEXT);
            return (TopicConnectionFactory) lInitialContext.lookup(CONNECTION_FACTORY);
        }
        catch (NamingException e)
        {
            System.out.println("JNDI API lookup failed: " + e.toString());
            throw e;
        }
    }

    /**
     * This method is called when the consumer wants to start
     * receiving messages.
     *
     * @throws JMSException If an exception happens on the connection.
     */
    public void startListening() throws JMSException
    {
        topicConnection.start();
        System.out.println("Topic subscriber Listening...");
        isListening = true;
    }

    /**
     * This method is called when the consumer wants to stop receiving
     * messages.
     *
     * @throws JMSException If an exception happens on the connection.
     */
    public void stopListening() throws JMSException
    {
        if (null != topicConnection)
        {
            topicConnection.stop();
        }
        System.out.println("Topic subscriber not Listening...");
        isListening = false;
    }

    /**
     * This method is called to unsubscribe from a durable subscription.  This
     * MUST be called to remove the subscription from the server otherwise
     * messages will be queued forever for this subscription.
     *
     * @throws JMSException If an error occurrs unsubscribing.
     */
    public void unsubscribe() throws JMSException
    {
        topicSession.unsubscribe(acceptedClientId);
    }

    /**
     * This method sets the exception listener on the connection.
     *
     * @param aInListener The connection listener to set.
     *
     * @throws JMSException If an exception happens on the connection.
     */
    public void setExceptionListener(ExceptionListener aInListener) throws JMSException
    {
        if (null != topicConnection)
        {
            topicConnection.setExceptionListener(aInListener);
        }
    }

    /**
     * This method sets the message listener for the connection.
     *
     * @param aInListener The message listener to receive messages.
     */
    public void setMessageListener(MessageListener aInListener)
    {
        try
        {
            topicSubscriber.setMessageListener(aInListener);
        }
        catch (Exception e)
        {
            System.out.println("Exception se"
            		+ "tting message listener: " + e.getMessage());
        }
    }

    /**
     * This method is called when the consumer wants to close the
     * connection.
     */
    public synchronized void closeConnection()
    {
        close();
        isStopped = true;
    }

    /**
     * This method is called when the consumer wants to close the
     * connection.
     */
    private synchronized void close()
    {
        try
        {
            isConnected = false;
            stopListening();
            try
            {
                topicSubscriber.close();
            }
            catch (Exception e)
            {
                System.out.println("Exception on subscriber close: " + e.getMessage());
            }
            if (isPersistent())
            {
                try
                {
                    unsubscribe();
                }
                catch (Exception e)
                {
                    System.out.println("Exception on unsubscribe: " + e.getMessage());
                }
            }
            try
            {
                topicSession.close();
            }
            catch (Exception e)
            {
                System.out.println("Exception on session close: " + e.getMessage());
            }
            try
            {
                topicConnection.close();
            }
            catch (Exception e)
            {
                System.out.println("Exception on topic close: " + e.getMessage());
            }
            System.out.println("Topic subscriber connection closed.");
        }
        catch (Exception e)
        {
            System.out.println("Exception on close: " + e.getMessage());
        }
    }

    /**
     * This method is called when an exception occurrs on the JMS connection.
     *
     * @param aInException The exception that occurred.
     */
    public void onException(JMSException aInException)
    {
        System.out.println("An Exception has occurred for the connection: " +
            aInException.getMessage());
        try
        {
            setExceptionListener(null);
            topicConnection.close();
        }
        catch (Exception e)
        {
            // Ignore this exception, the TCP connection may already be closed.
        }
        if (isHaEnabled)
        {
            int lAttempts = 0;
            while (!isConnected && !isStopped)
            {
                lAttempts++;
                try
                {
                    initializeConnection();
                    return;
                }
                catch (Exception e)
                {
                    System.out.println("Connection Attempt #: " + lAttempts +
                        " Exception: " + e.getMessage());
                }
                try
                {
                    Thread.sleep(5000);
                }
                catch (Exception e)
                {
                    // This exception should not happen unless the process
                    // is killed at this point, in which case it is ignored.
                }
            }
        }
        else
        {
            System.out.println("Exiting...");
            System.exit(3);
        }
    }

    private static void printUsage()
    {
        System.out.println("Format: JmsTest -t <topic> -s <App server IP:port> {-r <HA App server IP:port>} -u <user> -p <password> {-f \"<filter>\"} {-persistent -c <uniqueid>}");
        System.out.println("  Mandatory Parameters:");
        System.out.println("      -t <topic> : The topic to connect to.");
        System.out.println("      -s <App server IP:port> : The application server and port (url) to connect to.");
        System.out.println("      -u <user> -p <password> : The user and password to login with.");
        System.out.println("  Optional Parameters:");
        System.out.println("      -r <HA App server IP:port> : The high availability application server and port (url) to connect to (redundancy support)");
        System.out.println("      -f \"<filter>\" : The filter for messages (in SQL92 format)");
        System.out.println("      -persistent : Makes the connection durable.  If this option is taken, the client Id must be specified.");
        System.out.println("      -c <uniqueid> : The unique client id for this connection.");
        System.out.println("  Possible Topics:");
        for (int i = 0; i < AVAILABLE_TOPICS.length; i++)
        {
            System.out.println("      " + AVAILABLE_TOPICS[i]);
        }
        System.exit(1);
    }

    /**
     * This method parses the command line and either displays an error message or creates
     * a new instance of JmsTest.
     *
     * @param aInArgs The command line arguments.
     *
     * @return A new instance of JmsTest, or exits on error.
     *
     * @throws Exception if an error occurs parsing the command line.
     */
    public static JmsTest parseCommandLine(String[] aInArgs) throws Exception
    {
        if (aInArgs.length < 1)
        {
            printUsage();
        }

        int lIndex = 0;
        String lTopic = null;
        String lUrl = null;
        String lHaUrl = null;
        String lUser = null;
        String lPassword = null;
        boolean lIsPersistent = false;
        String lFilter = "";
        String lClientId = "";
        
        while (lIndex < aInArgs.length)
        {
            if (aInArgs[lIndex].equals("-t"))
            {
                lTopic = aInArgs[++lIndex];
                lIndex++;
            }
            else if (aInArgs[lIndex].equals("-s"))
            {
                lUrl = aInArgs[++lIndex];
                lIndex++;
            }
            else if (aInArgs[lIndex].equals("-r"))
            {
                lHaUrl = aInArgs[++lIndex];
                lIndex++;
            }
            else if (aInArgs[lIndex].equals("-u"))
            {
                lUser = aInArgs[++lIndex];
                lIndex++;
            }
            else if (aInArgs[lIndex].equals("-p"))
            {
                lPassword = aInArgs[++lIndex];
                lIndex++;
            }
            else if (aInArgs[lIndex].equals("-f"))
            {
                lFilter = aInArgs[++lIndex];
                lIndex++;
            }
            else if (aInArgs[lIndex].equals("-persistent"))
            {
                lIsPersistent = true;
                lIndex++;
            }
            else if (aInArgs[lIndex].equals("-c"))
            {
                lClientId = aInArgs[++lIndex];
                lIndex++;
            }
            else if ("-help".equals(aInArgs[lIndex]))
            {
                printUsage();
            }
            else
            {
                lIndex++;
            }
        }

        return new JmsTest(lTopic, lUrl, lHaUrl, lClientId, lUser, lPassword, lIsPersistent, lFilter);
    }

    /**
     * This method prints the menu for the display.
     *
     * @param aInIsPersistent Identifies if the connection is persistent.
     * If so, it allows pausing/unpausing the connection.
     * @param aInIsPaused true if the connection is currently paused, false otherwise.
     */
    public static void printMenu(boolean aInIsPersistent, boolean aInIsPaused)
    {
        System.out.println("");
        System.out.println("r) Reset count");
        if (aInIsPersistent)
        {
            if (!aInIsPaused)
            {
                System.out.println("p) Pause Connection (for persistant JMS)");
            }
            else
            {
                System.out.println("u) Un-pause Connection (for persistant JMS)");
            }
        }
        System.out.println("q) Quit");
    }

    
    //Decode Soap Message:
    
    
    // Find File
   
    public static boolean  findFile(String name,File file)
    {
    	
    	boolean flagin =false;
        File[] list = file.listFiles();
        if(list!=null)
        for (File fil : list)
        {
            if (fil.isDirectory())
            {
                findFile(name,fil);
                
              flagin =true;
            }
            else if (name.equalsIgnoreCase(fil.getName()))
            {
                System.out.println(fil.getParentFile());
                
                flagin =true;
            }
        }
      
        
        return flagin;
    }

    
    
    /**
     * Main entry to JmsTest.  It displays help if requested, or if activated
     * successfully, it will display a menu to the user.
     *
     * @param aInArgs Command line operands
     */
    public static void main(String[] aInArgs)
    {
    	
    	
    	 
      //  flag = true;
        boolean bContinue = true;

        try
        {
            // determine jmstest configuration based on command line arguments.
            JmsTest jmsTest = parseCommandLine(aInArgs);
             
            // connect to the server.
            jmsTest.initializeConnection();
          //  SimpleSocketClient SendAlarm = new SimpleSocketClient(); 
              SoapClient SoapAckAlarm =new SoapClient(); 
            
            
            final File folder = new File("C:/Users/xt20726/Documents/SAM/xmloutput/");
          //  Socket socket = openSocket("localhost",8080);
            
          boolean go = true;
          boolean go1=false;
          boolean mainFlag=false;
      
         while(go){
        	 
        	 
        	   
        	    	 
        	 
        	 
     //   System.out.println("SAWADOGO In firts While : " );
         // mainFlag =  getFlag(folder);
         // System.out.println("mianFlag : " +mainFlag);
          //if(mainFlag){
        	//  go1 = true;
          //}
          
        //  System.out.println("My Flag in Main : " +mainFlag);
         
            int j=0;
            //String myAlarm="bla";  
            
            //go1 =findFile(Filename,folder);
            //System.out.println("GO Flag in while loop : " +go1);
            
            if(getFlag(folder))
            {
            	//System.out.println("SAWADOGO In IF Loop  : " );
            	//String myAlarm=  listFilesForFolder(folder);
            
            	System.out.println("Filename SAWADOGO : " +FileOutput);
            	String myAlarm=parseMessage(FileOutput);
            	
             	//deleteFiles(folder);
        	   // System.out.println("SAWADOGO I Tired : " );
               
             //  writeToAndReadFromSocket("localhost", 8080,"sawadogo");
               
        	    
        	    
        	    if (myAlarm != null){
            	   String objectName= parseObjectName(myAlarm);
            	    System.out.println("ObjectName before sending : " +objectName);
	                System.out.println("Myalarm ready to send : " + myAlarm);
	                writeToAndReadFromSocket("localhost", 8080, myAlarm);
	                SoapAckAlarm.SOAPConnection(objectName,"This Alarm is acked by Abdou from JMS");
            	   
	              //  writeToAndReadFromSocket(socket,myAlarm);   
	           
               }
               
               j++;
             //  System.out.println("J in while loop : " +j);
            
              // System.out.println("K in while loop : " +k);
               //deleteFiles(folder);
              
              // mainFlag=false;
               
              // break;
               //deleteFiles(folder);
               
               
              // deleteFiles(folder);
              // File file = new File("C:/Users/xt20726/Documents/SAM/xmloutput/"+Filename);
              // file.delete();
                // go1 = false;
              //  break;
               Thread.sleep(30000);
            }
           
        // Thread.sleep(15000);
         
         }
            
            boolean paused = false;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
          
            
            while (bContinue)
            {
                printMenu(jmsTest.isPersistent(), paused);

                String line = in.readLine();
                if (line.length() == 0)
                {
                    continue;
                }

                switch (line.toLowerCase().charAt(0))
                {
                    case 'r':
                        // reset count
                        counter = 0;
                        break;

                    case 'p':
                        // pause/stop listening to messages
                        jmsTest.stopListening();
                        paused = true;
                        break;

                    case 'u':
                        // unpause/listen to messages
                        jmsTest.startListening();
                        
                        
                        
                        paused = false;
                        break;

                    case 'q':
                        //quit
                        bContinue = false;
                        break;

                    default:
                        System.out.println("Invalid choice: " + line.charAt(0));
                        break;
                }
           
            
               
                
            
            
            }
            System.out.println("Received " + counter + " events.");
            
            //
            
           
            
            //
            
            
            
            jmsTest.closeConnection();
            
            
        }
        catch (Exception e)
        {
            System.out.println("Exception - " + e.getMessage());
            System.exit(1);
        }
    
         

    
    
    
    }


   


}

