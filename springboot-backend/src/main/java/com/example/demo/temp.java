package com.example.demo;


public class temp {
	
	
	
	
	/*********************************************************************************
	 * RECUPERATION DU MAIL AU FORMAT HTML
	 * 
	String mimeType = message.getPayload().getMimeType();
	List<MessagePart> parts = message.getPayload().getParts();
	if (mimeType.contains("alternative")) {
	    for (MessagePart part : parts) {
	    	@SuppressWarnings("deprecation")
			String mailBody = new String(Base64.decodeBase64(part.getBody()
	                .getData().getBytes()));
	    	System.out.println(mailBody);

	    }
	    
	}
	***********************************************************************************/
	
	
	
	

	/************************AFFICHER L'OBJET ET LE CONTENU D'UN MAIL A PARTIR D'UNE ADRESSE MAIL
	 *
	 *
	String query = "from:adam.mkh@hotmail.fr";
	Gmail.Users.Messages.List request = service.users().messages().list(user).setQ(query);
	
	ListMessagesResponse messagesResponse = request.execute();
	request.setPageToken(messagesResponse.getNextPageToken());
	
	//GetId of the email you are looking for
	String messageId = messagesResponse.getMessages().get(0).getId();
	
	Message message = service.users().messages().get(user, messageId).execute();
	
	
	
	JsonPath jp = new JsonPath(message.toString());
	String subject = jp.getString("payload.headers.find { it.name == 'Subject' }.value");
	
	
   String body = new String(Base64.decodeBase64(jp.getString("payload.parts[0].body.data")));
	
	@SuppressWarnings("deprecation")
	String emailBody = StringUtils.newStringUtf8(Base64.decodeBase64(message.getPayload().getParts().get(0).getBody().getData()));
	
	
	
	System.out.println("Email Header: " + subject + "\n\n");
	
	System.out.println("Email Body: " + body);
	*/
	
}




/*



//Acces Gmail inbox
String query = "from:adam.mkh@hotmail.fr";//paypal@mail.paypal.fr

ListMessagesResponse request = service.users().messages().list(user).setQ(query).execute();
ArrayList<Message> allMessages = new ArrayList<Message>();

while(request.getMessages() != null) {
	allMessages.addAll(request.getMessages());
	if(request.getNextPageToken() != null) {
		String pageToken = request.getNextPageToken();
		request = service.users().messages().list(user).setQ(query).setPageToken(pageToken).execute();
	}else {
		break;
	}
}
System.out.println("Nombres total de mails: " + allMessages.size());

Message msg = allMessages.get(0);
String messageId = msg.getId();
Message message = service.users().messages().get(user, messageId).execute();

Date date = new Date(message.getInternalDate());
System.out.println("Date: " + date); //format yyyy/mm/dd

String dateMailHEURE = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date (message.getInternalDate()));
System.out.println("Date + heure: " + dateMailHEURE);



List<MessagePartHeader> headers = message.getPayload().getHeaders();

String from="";
for (MessagePartHeader header:headers){
    if(header.getName().equals("From")){
        from=header.getValue();
        break;
    }
}


System.out.println("Provenant de: " + from);



JsonPath jp = new JsonPath(message.toString());
String subject = jp.getString("payload.headers.find { it.name == 'Subject' }.value");


String mimeType = message.getPayload().getMimeType();
String mailBody = "";
List<MessagePart> parts = message.getPayload().getParts();
if (mimeType.contains("alternative")) {
    for (MessagePart part : parts) {
    	mailBody = new String(Base64.decodeBase64(part.getBody()
                .getData().getBytes()));
    	//mailBody = new String(Base64.encodeBase64(part.getBody()
        //        .getData().getBytes()));
    }
	//System.out.println(mailBody);
	//EmailData mailTest = new EmailData(subject, mailBody);
}




mailService.saveMail("TESTtitre", "TESTcorps");

EmailData dataMail = new EmailData();
dataMail.setSubject(subject.toString());
dataMail.setMailBody(mailBody.toString());

//System.out.println(mailBody.toString());

//mailService.saveMail(subject.toString(), mailBody.toString());

//setRepo(emailRepo, new EmailData("test1", "tet"));
//emailService.save(new EmailData("test1", "tet"));


/*
for(int i=0; i<5; i++) {
	Message msg = allMessages.get(i);
	String messageId = msg.getId();
	Message message = service.users().messages().get(user, messageId).execute();

	JsonPath jp = new JsonPath(message.toString());
	String subject = jp.getString("payload.headers.find { it.name == 'Subject' }.value");
	System.out.println("Email Subject: " + subject);
	
	String body = new String(Base64.decodeBase64(jp.getString("payload.parts[0].body.data")));
	System.out.println("Email Body: " + body + "\n----------------------------------------------\n");
}
*/