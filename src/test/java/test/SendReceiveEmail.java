package test;

import com.google.api.services.gmail.Gmail;

import datahelper.DataHelper;

import com.google.api.services.gmail.model.Message;

import dto.CreateEmailInputDTO;
import dto.CreateEmailInputRqDTO;
import servicecalls.GmailCalls;
import utils.Utility;
import validation.Assertion;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.mail.MessagingException;

import org.json.simple.parser.ParseException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SendReceiveEmail {

	public static CreateEmailInputRqDTO createEmailInputRqDTOCopy = null;

	@DataProvider
	public Object[][] getEmailData() throws IOException, ParseException {
		return Utility.getInputData("src/test/resources/Email.json");
	}

	@Test(dataProvider = "getEmailData")
	public void sendEmail(CreateEmailInputDTO createEmailInputDTO)
			throws MessagingException, IOException, GeneralSecurityException {
		createEmailInputDTO.getRq()
		.setSubject("subject" + Utility.getRandomString());
		Message message = Utility.getMessage(createEmailInputDTO.getRq());
		Message result = GmailCalls.sendMessage(GmailCalls.getService
				(DataHelper.REFRESH_TOKEN_SENDER),
				createEmailInputDTO.getRq().getFrom(), message);
		Assertion.validateEmailSend(result);
		createEmailInputRqDTOCopy = createEmailInputDTO.getRq();
	}

	@Test(dependsOnMethods = { "sendEmail" })
	public void searchEmail() throws MessagingException, IOException, 
	GeneralSecurityException, InterruptedException {
		Gmail service = 
				GmailCalls.getService(DataHelper.REFRESH_TOKEN_RECEIVER);
		Thread.sleep(1000);
		List<Message> listMessage = GmailCalls.listMessage(service, 
				createEmailInputRqDTOCopy.getTo(),
				"subject:" + createEmailInputRqDTOCopy.getSubject());
		Assertion.validateEmailReceive(service, listMessage, 
				createEmailInputRqDTOCopy);
	}

}