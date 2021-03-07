package practice;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
import com.email.durgesh.Email; 
public class Mail  
{  
	public Mail(String m,String n)
	{
		Email email=new Email("samleidforprojecthost@gmail.com", "sampleotphost");
		try {
			email.setFrom("samleidforprojecthost@gmail.com", "Cuckoo Raven");
			email.setSubject("Request");
			email.setContent("Your have a request from "+n,"text/html");
			email.addRecipient(m);
			email.send();
			//System.out.print("Success");
		} catch (Exception e) {
			System.out.print(e);; 
   } 	
}
}