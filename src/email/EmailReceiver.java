package email;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;



public class EmailReceiver {
	
	   public void receiveMail(String userName, String password) throws MessagingException 	{
	
			   Properties properties = new Properties();
			   properties.setProperty("mail.store.protocol", "imaps");
			   Session emailSession = Session.getDefaultInstance(properties);
			   Store emailStore = emailSession.getStore("imaps");
			   emailStore.connect("imap.gmail.com", userName, password);
			   
			   Folder emailFolder = emailStore.getFolder("INBOX");
			   emailFolder.open(Folder.READ_ONLY);
			   Message[] messages = emailFolder.getMessages();
			   Message latestEmail = messages[messages.length - 1];
			   int lastEmailNumber = latestEmail.getMessageNumber();
			   monitorMail(emailFolder, lastEmailNumber);
			   emailFolder.close(false);
			   emailStore.close(); 
	   }
	   
	   public void monitorMail(Folder emailFolder, int lastEmailNumber) throws MessagingException	{
		   while(true)	{
			   Message[] messages = emailFolder.getMessages();
			   Message latestEmail = messages[messages.length - 1];
			   int latestEmailNumber = latestEmail.getMessageNumber();
			   if (lastEmailNumber < latestEmailNumber) {
				   System.out.println(latestEmail.getMessageNumber());
				   System.out.println(latestEmail.getSubject());
				   lastEmailNumber = latestEmailNumber;
			   }
		   }
	   }

}