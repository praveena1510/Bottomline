package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.gmail.model.Message;

import dto.CreateEmailInputDTO;
import dto.CreateEmailInputRqDTO;

public class Utility {

	public static Object[][] getInputData(String file) throws IOException, 
	ParseException {
		 JSONArray inputJsonArray = (JSONArray) new JSONParser()
	                .parse(new FileReader(file));
	        List<CreateEmailInputDTO> createEmailInputDTOList = 
	        		new ArrayList<CreateEmailInputDTO>();
	        ObjectMapper mapper = new ObjectMapper();
	        for(int i=0;i<inputJsonArray.size();i++) {
	            JSONObject jsonObject = (JSONObject) inputJsonArray.get(i);
	            CreateEmailInputDTO createCustomerDTO = 
	            		mapper.readValue(jsonObject.toString(),
	            		CreateEmailInputDTO.class);
	            createEmailInputDTOList.add(createCustomerDTO);
	        }
	        Object[][] returnValue = new Object[createEmailInputDTOList.size()][1];
	        int index = 0;
	        for (Object[] each : returnValue) {
	            each[0] = createEmailInputDTOList.get(index++);
	        }
	        return returnValue;
	}

	/**
	 * Create a MimeMessage using the parameters provided.
	 *
	 * @param to email address of the receiver
	 * @param from email address of the sender, the mailbox account
	 * @param subject subject of the email
	 * @param bodyText body text of the email
	 * @return the MimeMessage to be used to send email
	 * @throws MessagingException
	 */
	public static MimeMessage createEmail(String to,
			String from,
			String subject,
			String bodyText)
					throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO,
				new InternetAddress(to));
		email.setSubject(subject);
		email.setText(bodyText);
		return email;
	}

	/**
	 * Create a message from an email.
	 *
	 * @param emailContent Email to be set to raw of message
	 * @return a message containing a base64url encoded email
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static Message createMessageWithEmail(MimeMessage emailContent)
			throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}

	public static Message 
	getMessage(CreateEmailInputRqDTO createEmailInputRqDTO) throws
	MessagingException, IOException {
		MimeMessage mimeMessage = createEmail(createEmailInputRqDTO.getTo(), 
				createEmailInputRqDTO.getFrom(), 
				createEmailInputRqDTO.getSubject(), 
				createEmailInputRqDTO.getBodyText());
		return createMessageWithEmail(mimeMessage);
	}
	
	public static String getRandomString() {
		return UUID.randomUUID().toString();
	}
	
	public static String getSubject(String rawMessage) throws MessagingException {
		byte[] emailBytes = Base64.decodeBase64(rawMessage);
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		return new MimeMessage(session, new ByteArrayInputStream(emailBytes)).getSubject();
	}

}
