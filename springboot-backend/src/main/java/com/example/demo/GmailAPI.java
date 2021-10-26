package com.example.demo;


import com.example.demo.controller.EmailDataController;


import com.example.demo.model.CommercialData;
import com.example.demo.model.CommercialMailData;
import com.example.demo.model.EmailData;
import com.example.demo.repository.CommercialDataRepository;
import com.example.demo.repository.EmailDataRepository;
import com.example.demo.service.CommercialDataService;
import com.example.demo.service.CommercialMailDataService;
import com.example.demo.service.EmailDataService;
import com.google.api.client.auth.oauth2.Credential;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.Users;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.Gmail.Users.GetProfile;
import com.google.api.services.gmail.Gmail.Users.Settings.Delegates.Create;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Delegate;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.ListThreadsResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.google.api.services.gmail.model.Profile;

import com.google.api.services.people.*;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.ListOtherContactsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.SearchResponse;

import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManagerFactory;

import org.hibernate.service.spi.ServiceException;
//import org.mortbay.jetty.servlet.Context;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

import io.restassured.path.json.JsonPath;

@Component
@SuppressWarnings({ "unused", "deprecation" })
public class GmailAPI {
	
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String user = "me";
    //private static final List<String> SCOPES = new ArrayList<String>(GmailScopes.all());
    private static final String TOKENS_DIRECTORY_PATH = "tokens";


    //private static final String CREDENTIALS_FILE_PATH = "/credentials_google_type.json";//src/main/resources
	private static final List<String> SCOPES = List.of(GmailScopes.MAIL_GOOGLE_COM, DirectoryScopes.ADMIN_DIRECTORY_USER, PeopleServiceScopes.CONTACTS_READONLY, PeopleServiceScopes.CONTACTS_OTHER_READONLY);//Collections.singletonMap(GmailScopes.MAIL_GOOGLE_COM);
    private static String filePath = System.getProperty("user.dir")+"/src/main/resources/credentials.json";//credentials_omicrone1.json
    
    
    static Gmail service;
    
    @Autowired
    private static JdbcTemplate jdbcTemplate;
    
    static ConfigurableApplicationContext context;
    
    static CommercialDataService commercialDataService;
 	static CommercialMailDataService commercialMailDataService;
    static EmailDataService emailDataService;
    
    //---------------------------------------------------------------------------------------//
    //-------------------------------Methodes d'initialisation-------------------------------//
    //---------------------------------------------------------------------------------------//   
    
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
        // Load client secrets.
        InputStream in = new FileInputStream(new File(filePath));
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + filePath);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(9999).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    public static void setService() throws IOException, GeneralSecurityException, Exception {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        GmailAPI.service = service;
    }
    
    
    //---------------------------------------------------------------------------------------//
    //-----------------Methodes de récupération de tout les users du domaine-----------------//
    //---------------------------------------------------------------------------------------//    
    
    //AllUsers{User1{name:nomUser1, email:mailUser1, userId:idUser1}, User2{name:nomUser2, email:mailUser2, userId:idUser2}}
    public static List<HashMap<String, String>> getAllUsersDomain() throws Exception {	//retourne une liste contenant les informations des users en liste
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Directory service1 = new Directory.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        HashMap<String, String> userData = new HashMap<String, String>();
        List<HashMap<String, String>> allUsersData = new ArrayList<>();
        // Print the users in the domain.
        Users result = service1.users().list()
                .setCustomer("my_customer")
                .setOrderBy("email")
                .execute();
        List<User> users = result.getUsers();
        if (users == null || users.size() == 0) {
            System.out.println("No users found.");
        } else {
        	System.out.println("Users:");
            for(User user : users) { //(int i=0; i<2; i++)
                System.out.println(user.getName().getFullName());
                userData.put("name", user.getName().getFullName());
                System.out.println(user.getPrimaryEmail());
                userData.put("email", user.getPrimaryEmail());
                System.out.println(user.getId() + "\n--------------");
                userData.put("userid", user.getId());
                
                allUsersData.add(userData);
                userData = new HashMap<String, String>();
                
            }
            
        }
		return allUsersData;
    }
    
    
    //---------------------------------------------------------------------------------------//
    //-------------------------------Methodes Nombres de mails-------------------------------//
    //---------------------------------------------------------------------------------------//

  
    public static Integer getCountMailsReceived(String userID) throws IOException {	//retourne le nombre de tout les mails reçues (boite de reception + spams)
    	String query = "in:inbox";//"to:" + getEmailUser(userID);
    	
    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ(query).execute();
    	ArrayList<Message> allMessages = new ArrayList<Message>();
		while(request.getMessages() != null) {
			allMessages.addAll(request.getMessages());
			if(request.getNextPageToken() != null) {
				String pageToken = request.getNextPageToken();
				request = GmailAPI.service.users().messages().list(userID).setQ(query).setPageToken(pageToken).execute();
			}else {
				break;
			}
		}
		return allMessages.size();
    }
    public static Integer getCountMailsReceivedFromQuery(String userID, String query) throws IOException {	//retourne le nombre de tout les mails reçues (boite de reception + spams)
    	String query1 = query + " in:inbox";
    	
    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ(query1).execute();
    	ArrayList<Message> allMessages = new ArrayList<Message>();
		while(request.getMessages() != null) {
			allMessages.addAll(request.getMessages());
			if(request.getNextPageToken() != null) {
				String pageToken = request.getNextPageToken();
				request = GmailAPI.service.users().messages().list(userID).setQ(query1).setPageToken(pageToken).execute();
			}else {
				break;
			}
		}
		return allMessages.size();
    }
    

    public static Integer getCountMailsSent(String userID) throws IOException {	//retourne le nombre de tout les mails envoyés
    	String query = "in:sent";//"from:" + getEmailUser(userID);
    	    	
    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ(query).execute();
    	ArrayList<Message> allMessages = new ArrayList<Message>();
		while(request.getMessages() != null) {
			allMessages.addAll(request.getMessages());
			if(request.getNextPageToken() != null) {
				String pageToken = request.getNextPageToken();
				request = GmailAPI.service.users().messages().list(userID).setQ(query).setPageToken(pageToken).execute();
			}else {
				break;
			}
		}
		return allMessages.size();
    }
    public static Integer getCountMailsSentFromQuery(String userID, String query) throws IOException {	//retourne le nombre de tout les mails envoyés
    	String query1 = query + " in:sent";//"from:" + getEmailUser(userID);
    	    	
    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ(query1).execute();
    	ArrayList<Message> allMessages = new ArrayList<Message>();
		while(request.getMessages() != null) {
			allMessages.addAll(request.getMessages());
			if(request.getNextPageToken() != null) {
				String pageToken = request.getNextPageToken();
				request = GmailAPI.service.users().messages().list(userID).setQ(query1).setPageToken(pageToken).execute();
			}else {
				break;
			}
		}

		return allMessages.size();
    }
    
    public static Integer getTotalCountMails(String userID) throws IOException {	//retourne le total de mails (envoyés, reçues, spams,...)
    	Profile request = null;
    	int totalMails = 0;
    	if(GmailAPI.service.users().getProfile(userID).execute() != null) {
    		request = GmailAPI.service.users().getProfile(userID).execute();
    		totalMails = request.getThreadsTotal();
    	}
    	

		return totalMails;//.getMessagesTotal();
    }
    
    public static Integer getTotalCountMailsFromQuery(String userID, String query) throws IOException {	//retourne le total de mails (envoyés, reçues, spams,...)
    	//String query = "in:sent";//"from:" + getEmailUser(userID);
    	
    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ(query).execute();
    	ArrayList<Message> allMessages = new ArrayList<Message>();
		while(request.getMessages() != null) {
			allMessages.addAll(request.getMessages());
			if(request.getNextPageToken() != null) {
				String pageToken = request.getNextPageToken();
				request = GmailAPI.service.users().messages().list(userID).setQ(query).setPageToken(pageToken).execute();
			}else {
				break;
			}
		}

		return allMessages.size();
    }
    
    
    
    //---------------------------------------------------------------------------------------//
    //----------------------------Methodes Récupération de mails-----------------------------//
    //---------------------------------------------------------------------------------------//
    public static List<Message> getMailsReceivedBySize(String userID, Long numbersizeResult) throws IOException {	//retourne une liste de tout les mails reçues
    	String query = "in:inbox";//"to:" + getEmailUser(userID);    	

    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ(query).execute();
    	ArrayList<Message> allMessages = new ArrayList<Message>();

		request = service.users().messages().list(userID).setQ(query).setMaxResults(numbersizeResult).execute();
		
		allMessages.addAll(request.getMessages());

		return allMessages;
    }
    public static List<Message> getMailsSentBySize(String userID, Long numbersizeResult) throws IOException {	//retourne une liste de tout les mails reçues
    	String query = "in:sent";//"to:" + getEmailUser(userID);    	

    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ(query).execute();
    	ArrayList<Message> allMessages = new ArrayList<Message>();

		request = service.users().messages().list(userID).setQ(query).setMaxResults(numbersizeResult).execute();
		
		allMessages.addAll(request.getMessages());

		return allMessages;

    }

    
    public static Message getLastMailSent(String userID) throws IOException {	//retourne le dernier mail reçues
    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ("in:sent").execute();
		
		return request.getMessages().get(0);
    }
    public static Message getLastMailReceived(String userID) throws IOException {	//retourne le dernier mail envoyé
    	ListMessagesResponse request = GmailAPI.service.users().messages().list(userID).setQ("in:inbox").execute();
		
		return request.getMessages().get(0);
    }
    
    
    //---------------------------------------------------------------------------------------//
    //----------------------------Methodes Information d'un mail-----------------------------//
    //---------------------------------------------------------------------------------------//
    
    public static Date getDateMail(Message message, String userID) throws IOException, ParseException {		//date de reçeption d'un mail passé en paramètre
    	//Date date = new Date(msg.getInternalDate());	//MM-dd-yyyy
    	Message msg = getMailById(message.getId(), userID);
		msg = service.users().messages().get(userID, msg.getId()).execute();

		String date = new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date (msg.getInternalDate()));
    	
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date parsed = format.parse(date);
        java.sql.Date dateMail = new java.sql.Date(parsed.getTime());
		
		return dateMail;
    }
    
    public static String getHeureMail(Message message, String userID) throws IOException {		//heure de reçeption d'un mail passé en paramètre
    	Message msg = getMailById(message.getId(), userID);
		msg = service.users().messages().get(userID, msg.getId()).execute();

		String date = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date (msg.getInternalDate()));
    	
		return date;
    }

    public static Message getMailById(String msgId, String userID) throws IOException {	//retourne un mail à partir de l'ID
    	Message message = service.users().messages().get(userID, msgId).execute();//a partir d'une requete: reponse plus complete
    	return message;
    }

    public static String getSenderName(Message message, String userID) throws IOException {	//obtenir le nom et le mail de l'expediteur
    	Message msg = getMailById(message.getId(), userID);
		List<MessagePartHeader> headers = msg.getPayload().getHeaders();

		String from="";
        for (MessagePartHeader header:headers){
            if(header.getName().equals("From")){
                from=header.getValue();
                break;
            }
        }
		from = from.replace("\"", "");
		
		return from;
    }
    public static String getReceiverName(Message message, String userID) throws IOException {	//obtenir le nom et le mail de l'expediteur
    	Message msg = getMailById(message.getId(), userID);
		List<MessagePartHeader> headers = msg.getPayload().getHeaders();

		String to="";
        for (MessagePartHeader header:headers){
            if(header.getName().equals("To")){
            	to=header.getValue();
                break;
            }
        }
        to = to.replace("\"", "");
		
		return to;
    }
    
    public static String getSubjectMail(Message message, String userID) throws IOException {	//obtenir l'objet du mail passé en parametre
    	Message msg = getMailById(message.getId(), userID);
    	JsonPath jp = new JsonPath(msg.toString());
    	String subject = jp.getString("payload.headers.find { it.name == 'Subject' }.value");
		
		return subject;
    } 
    /*
    public static String getHTMLCodeFromMail(Message message, String userID) throws IOException {	//obtenir l'objet du mail passé en parametre
    	Message msg = getMailById(message.getId(), userID);
    	String mimeType = msg.getPayload().getMimeType();
    	List<MessagePart> parts = msg.getPayload().getParts();
    	
    	String htmlCode = "";
    	if (mimeType.contains("alternative")) {
    	    for (MessagePart part : parts) {
    	    	String html = new String(Base64.decodeBase64(part.getBody()
    	                .getData().getBytes()));
    	    	htmlCode = html;
    	    }
    	}
		return htmlCode;
    } 
*/
    
    public static String getEmailSender(Message message, String userID) throws IOException {	//obtenir l'email du mail passé en parametre
    	String sender = getSenderName(message, userID), open = "<", close = ">";
    	String emailSender = sender.substring(sender.indexOf( open ) + 1, sender.indexOf( close ));
		
		return emailSender;
    }
    
    public static String getEmailUser(String userID) throws IOException {	//obtenir l'email du user grace a l'ID passé en parametre
    	String emailUser = service.users().getProfile(userID).execute().getEmailAddress();

		return emailUser;
    }

    
    //---------------------------------------------------------------------------------------//
    //---------------------------------------Sauvegarde--------------------------------------//
    //---------------------------------------------------------------------------------------//
    
    
  
    
    public static void saveEmailDataReceivedSent(String userID) throws IOException, InterruptedException, ParseException {
    	emailDataService.deleteEmailData(userID);
    	
    	saveEmailDataReceivedByID(userID);
    	saveEmailDataSentByID(userID);
    	
    }
    
    public static void saveEmailDataReceivedByID(String userID) throws IOException, ParseException {
    	int numberResult = 5;
    	List<Message> allMSG = getMailsReceivedBySize(userID, (long) numberResult);
    	
    	
    	
		for(Message msg: allMSG) {
			System.out.println("MAIL RECU LISTE: " + getSenderName(msg, userID));

			EmailData emailData = new EmailData(userID, getEmailSender(msg, userID), getDateMail(msg, userID), getHeureMail(msg, userID), true, false);
			emailDataService.saveEmailData(emailData);
		}
		
    }
    public static void saveEmailDataReceived() throws IOException, ParseException {
    	int numberResult = 5;
    	
    	String userID = "";
    	
    	List<CommercialData> commercialD = commercialDataService.getDelegatedCommercial();
    	List<Message> allMSG = new ArrayList<>();
    	
    	for(int i=0; i<commercialD.size(); i++) {
    		userID = commercialD.get(i).getIdCommercial();
    		
    		allMSG = getMailsReceivedBySize(commercialD.get(i).getIdCommercial(), (long) numberResult);

			for(Message msg: allMSG) {
				System.out.println("MAIL RECU LISTE: " + getEmailSender(msg, userID));
				EmailData emailData = new EmailData(userID, getEmailSender(msg, userID), getDateMail(msg, userID), getHeureMail(msg, userID), true, false);
				emailDataService.saveEmailData(emailData);
			}
			allMSG = new ArrayList<>();
    	}
		
    }
    
    public static void saveEmailDataSentByID(String userID) throws IOException, ParseException {
    	int numberResult = 5;
    	List<Message> allMSG = getMailsSentBySize(userID, (long) numberResult);
    	
    	
    	
		for(Message msg: allMSG) {
			System.out.println("MAIL ENVOYE LISTE: " + getReceiverName(msg, userID));

			EmailData emailData = new EmailData(userID, getReceiverName(msg, userID), getDateMail(msg, userID), getHeureMail(msg, userID), false, true);
			emailDataService.saveEmailData(emailData);
		}
		
    }
    public static void saveEmailDataSent() throws IOException, ParseException {
    	int numberResult = 5;
    	
    	String userID = "";
    	
    	List<CommercialData> commercialD = commercialDataService.getDelegatedCommercial();
    	List<Message> allMSG = new ArrayList<>();
    	
    	for(int i=0; i<commercialD.size(); i++) {
    		userID = commercialD.get(i).getIdCommercial();
    		
    		allMSG = getMailsSentBySize(commercialD.get(i).getIdCommercial(), (long) numberResult);
    		
			for(Message msg: allMSG) {
				System.out.println("MAIL ENVOYE LISTE: " + getReceiverName(msg, userID));
				EmailData emailData = new EmailData(userID, getReceiverName(msg, userID), getDateMail(msg, userID), getHeureMail(msg, userID), false, true);
				emailDataService.saveEmailData(emailData);
			}
			allMSG = new ArrayList<>();
    	}
		
    }
    
    
    
    
    
    public static String getFirstLastDayRequest(Integer x) throws IOException {
    
    	LocalDate now = LocalDate.now();

    	// determine country (Locale) specific first day of current week
    	DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
    	LocalDate startOfCurrentWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //"EEE dd/MM/yyyy"
    	
    	String datesWeek = "";
    	
    	LocalDate printDate = null;
    	switch (x){
    		case 0:
    			//aujourd'hui
    			datesWeek = "newer_than:1d";
    			break;
    		case 1:
    			//7 derniers jours
    	    	printDate = startOfCurrentWeek;
    			datesWeek = "after:" + printDate.format(formatter);//.add(printDate.format(formatter));
    			datesWeek = datesWeek + " before:" + printDate.plusDays(6).format(formatter);//.add(printDate.plusDays(7).format(formatter));

    	    	break;
    		case 2:
    			//14 derniers jours
    	    	printDate = startOfCurrentWeek.minusWeeks(1);
    	    	datesWeek = "after:" + printDate.format(formatter);//.add(printDate.format(formatter));
    			datesWeek = datesWeek + " before:" + printDate.plusDays(14).format(formatter);//.add(printDate.plusDays(14).format(formatter));
    	    	break;
    		case 3:
    			//en 1 mois
    			printDate = LocalDate.now().withDayOfMonth(1); 
    			datesWeek = "after:" + printDate.format(formatter); //.add(printDate.format(formatter));
    	    	
    	    	printDate = printDate.plusMonths(1);
    			datesWeek = datesWeek + " before:" + printDate.format(formatter);//.add(printDate.format(formatter));
    	    	break;
    	}

    	return datesWeek;
    }
    
    public static void saveCommercialData() throws Exception {
    	
    	List<HashMap<String, String>> usersDomain = getAllUsersDomain();
    	
 	    CommercialDataService commercialDataService = context.getBean(CommercialDataService.class);
 	     	    
		CommercialData commercialDATA = new CommercialData();//userIDCommercial, "nameComm", "MAIL@azlazl.com", Long.valueOf(99));
		//CommercialDataService.saveCommercialData(commercialDATA);
		
		List<CommercialData> commercialD = commercialDataService.getDelegatedCommercial();
		

		boolean isDelegated = false;
		
		String userIdCommercial = "";
		String userNameCommercial = "";
		String userEmailCommercial = "";
		int nbMailsReceived = 0;
		int nbMailsSent = 0;
		int totalMailsUser = 0;
		
		for(int i=0; i<usersDomain.size(); i++) {
			
			if(usersDomain.get(i).get("email").equals(getEmailUser("me"))) {
				userIdCommercial = usersDomain.get(i).get("userid");
				userNameCommercial = usersDomain.get(i).get("name");
				userEmailCommercial = usersDomain.get(i).get("email");
				
				nbMailsReceived = getCountMailsReceived(usersDomain.get(i).get("userid"));
				nbMailsSent = getCountMailsSent(usersDomain.get(i).get("userid"));
				totalMailsUser = nbMailsReceived + nbMailsSent;
				
				isDelegated = true;
				
			}else {
				userIdCommercial = usersDomain.get(i).get("userid");
				userNameCommercial = usersDomain.get(i).get("name");
				userEmailCommercial = usersDomain.get(i).get("email");
				
				nbMailsReceived = 0;
				nbMailsSent = 0;
				totalMailsUser = 0;
				
				isDelegated = false;

			}
			commercialDATA = new CommercialData(userIdCommercial, userNameCommercial, userEmailCommercial, Long.valueOf(nbMailsReceived), Long.valueOf(nbMailsSent) ,Long.valueOf(totalMailsUser), isDelegated);
			commercialDataService.saveCommercialData(commercialDATA);

		}
		
		for(int i=0; i<commercialD.size(); i++){//if(commercialD.get(i).getIsDelegated()) {
			userIdCommercial = commercialD.get(i).getIdCommercial();
			userNameCommercial = commercialD.get(i).getNomCommercial();
			userEmailCommercial = commercialD.get(i).getEmailCommercial();
			
			nbMailsReceived = commercialD.get(i).getNbMailsReceived().intValue();
			nbMailsSent = commercialD.get(i).getNbMailsSent().intValue();
			totalMailsUser = nbMailsReceived + nbMailsSent;
			
			isDelegated = true;
		}
	}
    
    
    
public static void updateCommercialData() throws Exception {
    	
    	
 	    CommercialDataService commercialDataService = context.getBean(CommercialDataService.class);
 	     	    
		CommercialData commercialDATA = new CommercialData();//userIDCommercial, "nameComm", "MAIL@azlazl.com", Long.valueOf(99));
		//CommercialDataService.saveCommercialData(commercialDATA);
		
		List<CommercialData> commercialD = commercialDataService.findAllCommercialData();// .getDelegatedCommercial();
		
		for(int i=0; i<commercialD.size(); i++) {
			commercialDataService.updateCommercialData(commercialD.get(i).getId(), commercialD.get(i));
		}
		
	}
    
		
		
    
    
    
    
    public static void saveCommercialMailData() throws Exception {
 	    

    	List<CommercialData> commData = commercialDataService.findAllCommercialData();// .getDelegatedCommercial();
    	
		String thisWeek = getFirstLastDayRequest(1);
		
    	thisWeek = thisWeek.replace("after:", "");
    	thisWeek = thisWeek.replace("before:", "");
    	thisWeek = thisWeek.replace("/", "-");
    	
		List<String> datesWeek = new ArrayList<String>(Arrays.asList(thisWeek.split(" ")));

    	
    	
		

 	    
		CommercialMailData commercialMailDATA = new CommercialMailData();//userIDCommercial, "nameComm", "MAIL@azlazl.com", Long.valueOf(99));
		//CommercialDataService.saveCommercialData(commercialDATA);
		
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

		
		int nbMailsReceived1W = 0;
		int nbMailsSent1W = 0;
		
		int totalMailsUser = 0;

		for(int i=0; i<commData.size(); i++) {
			if(commData.get(i).getIsDelegated() == true ) {
				
		        java.util.Date parsed = format.parse(datesWeek.get(0));
		        java.sql.Date dateStart = new java.sql.Date(parsed.getTime());
		        
		        java.util.Date parsed1 = format.parse(datesWeek.get(1));
		        java.sql.Date dateEnd = new java.sql.Date(parsed1.getTime());
		        
		        nbMailsReceived1W = getCountMailsReceivedFromQuery(commData.get(i).getIdCommercial(), getFirstLastDayRequest(1));
		        nbMailsSent1W = getCountMailsSentFromQuery(commData.get(i).getIdCommercial(), getFirstLastDayRequest(1));
				
				commercialMailDATA = new CommercialMailData(commData.get(i).getIdCommercial(), dateStart, dateEnd, Long.valueOf(nbMailsReceived1W), Long.valueOf(nbMailsSent1W));
				commercialMailDataService.saveCommercialMailData(commercialMailDATA);
				
				
			}
		}
		
		
		
    }
    
    public static void saveCommercialMailDataById(String idCommercial) throws Exception {
 	    CommercialMailDataService commercialMailDataService = context.getBean(CommercialMailDataService.class);

    	List<CommercialMailData> commData = commercialMailDataService.findCommercialMailDataByIdCommercial(idCommercial);// .getDelegatedCommercial();
    	    	
		String thisWeek = getFirstLastDayRequest(1);
		
    	thisWeek = thisWeek.replace("after:", "");
    	thisWeek = thisWeek.replace("before:", "");
    	thisWeek = thisWeek.replace("/", "-");
    	
		List<String> datesWeek = new ArrayList<String>(Arrays.asList(thisWeek.split(" ")));

 	    
		CommercialMailData commercialMailDATA = new CommercialMailData();//userIDCommercial, "nameComm", "MAIL@azlazl.com", Long.valueOf(99));
		//CommercialDataService.saveCommercialData(commercialDATA);
		
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

		
		int nbMailsReceived1W = 0;
		int nbMailsSent1W = 0;
		
		int totalMailsUser = 0;

		java.util.Date parsed = format.parse(datesWeek.get(0));
		java.sql.Date dateStart = new java.sql.Date(parsed.getTime());
		        
		java.util.Date parsed1 = format.parse(datesWeek.get(1));
		java.sql.Date dateEnd = new java.sql.Date(parsed1.getTime());
		
		        
		//commData.forEach(null).contains(dateStart)) {
		ArrayList<Date> listDateStartWeek = new ArrayList<Date>();
		ArrayList<Date> listDateEndWeek = new ArrayList<Date>();
		for(CommercialMailData commD: commData) { 
			listDateStartWeek.add(commD.getDateStartWeek());
			listDateEndWeek.add(commD.getDateEndWeek());
    	}
		
		
		System.out.println(listDateStartWeek);
		System.out.println(dateStart);
		
		if(listDateStartWeek.contains(dateStart)  && listDateEndWeek.contains(dateEnd)) {
			System.out.println("true++++++");
			nbMailsReceived1W = getCountMailsReceivedFromQuery(idCommercial, getFirstLastDayRequest(1));
			nbMailsSent1W = getCountMailsSentFromQuery(idCommercial, getFirstLastDayRequest(1));
				
			commercialMailDATA = new CommercialMailData(idCommercial, dateStart, dateEnd, Long.valueOf(nbMailsReceived1W), Long.valueOf(nbMailsSent1W));
			commercialMailDataService.updateCommercialMailData(commercialMailDATA);
		}else {
			System.out.println("false-----------");
			nbMailsReceived1W = getCountMailsReceivedFromQuery(idCommercial, getFirstLastDayRequest(1));
			nbMailsSent1W = getCountMailsSentFromQuery(idCommercial, getFirstLastDayRequest(1));
				
			commercialMailDATA = new CommercialMailData(idCommercial, dateStart, dateEnd, Long.valueOf(nbMailsReceived1W), Long.valueOf(nbMailsSent1W));
			commercialMailDataService.saveCommercialMailData(commercialMailDATA);
		}
		
		
		
    }
    
    
    
    
	public static void main(String... args) throws Exception {
		setService();
		
		context = SpringApplication.run(SpringbootBackendApplication.class, args);
		emailDataService = context.getBean(EmailDataService.class);
		
		commercialDataService = context.getBean(CommercialDataService.class);
 	    
	 	commercialMailDataService = context.getBean(CommercialMailDataService.class);
		
	 	
	 	
		String userIDCommercial = "107144080827818361871";
		
		saveCommercialData();
		saveCommercialMailData();
		
		
		saveEmailDataReceived();
		saveEmailDataSent();
		
    }
    

}
