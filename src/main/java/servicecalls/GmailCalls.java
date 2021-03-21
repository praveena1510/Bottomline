package servicecalls;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

import datahelper.DataHelper;

public class GmailCalls {

	private static final String APPLICATION_NAME = "Gmail API";
	private static final JsonFactory JSON_FACTORY = 
			GsonFactory.getDefaultInstance();

	public static HttpRequestInitializer 
	getRequestInitializer(String accessToken) throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials
				.create(new AccessToken(accessToken, null));
		return new HttpCredentialsAdapter(googleCredentials);
	}

	public static GoogleClientSecrets getClientSecrets(String credentialsFile) 
			throws IOException {
		InputStream in = GmailCalls.class.getResourceAsStream(credentialsFile);
		GoogleClientSecrets clientSecrets = 
				GoogleClientSecrets.load(JSON_FACTORY, 
						new InputStreamReader(in));
//		System.out.println(clientSecrets.getDetails().getClientId());
		return clientSecrets;
	}

	public static Gmail getService(String refreshToken) throws 
	GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = 
				GoogleNetHttpTransport.newTrustedTransport();
		return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, 
				getRequestInitializer(Google.getAccessToken
						(getClientSecrets(DataHelper.CREDENTIALS_FILE_PATH), 
								refreshToken)))
								.setApplicationName(APPLICATION_NAME).build();
	}
	

	public static Message sendMessage(Gmail service, String userId, 
			Message message)throws MessagingException, IOException {
		return service.users().messages().send(userId, message)
				.execute();
	}
	
	public static Message getMessage(Gmail service, String userId, String id)
			throws MessagingException, IOException, GeneralSecurityException {
		return service.users().messages().get(userId, id).setFormat("raw")
				.execute();
	}
	
	public static List<Message> listMessage(Gmail service, String userId, 
			String query)
			throws MessagingException, IOException, GeneralSecurityException {
		ListMessagesResponse listMessagesResponse = service.users().messages()
				.list(userId).setQ(query).execute();
		List<Message> messages = new ArrayList<Message>();
		while (listMessagesResponse.getMessages() != null) {
			messages.addAll(listMessagesResponse.getMessages());
			if (listMessagesResponse.getNextPageToken() != null) {
				String pageToken = listMessagesResponse.getNextPageToken();
				listMessagesResponse = service.users().messages()
						.list(userId).setQ(query).setPageToken(pageToken)
						.execute();
			} else {
				break;
			}
		}
		return messages;
	}

}
