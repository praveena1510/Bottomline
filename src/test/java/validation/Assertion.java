package validation;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.mail.MessagingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import dto.CreateEmailInputRqDTO;
import dto.SendEmailResponseDTO;
import servicecalls.GmailCalls;
import utils.Utility;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Assertion {

	public static void validateEmailReceive(Gmail service, 
			List<Message> msgList,CreateEmailInputRqDTO createEmailInputRqDTO)
			throws MessagingException, IOException, GeneralSecurityException {
		boolean found = false;
		assertThat("Email not received", msgList.size(), greaterThan(0));
		for (Message message : msgList) {
			Message resultMsg = GmailCalls.getMessage(service, 
					createEmailInputRqDTO.getTo(), message.getId());
			String subjectAct = Utility.getSubject(resultMsg.getRaw());
			if (createEmailInputRqDTO.getSubject().equals(subjectAct)) {
				found = true;
				break;
			}
		}
		assertThat("Email not received", found == true);
	}

	public static void validateEmailSend
	(Message message)throws MessagingException, IOException, 
	GeneralSecurityException {
		 ObjectMapper mapper = new ObjectMapper();
	            SendEmailResponseDTO sendEmailResponseDTO = 
	            		mapper.readValue(message.toString(),
	            				SendEmailResponseDTO.class);
	     assertThat("Id is null", sendEmailResponseDTO.getId(),
	    		 not(nullValue()));  
	     assertThat("Email not sent", sendEmailResponseDTO.getLabelIds()[0],
	    		 is("SENT"));
	}

}
