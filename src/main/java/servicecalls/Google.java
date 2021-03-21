package servicecalls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import datahelper.DataHelper;
import dto.TokenResponseDTO;

public class Google {

	public static String getAccessToken(GoogleClientSecrets clientSecrets, 
			String refreshToken) throws IOException {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("client_id", clientSecrets.getDetails().getClientId());
		params.put("client_secret", 
				clientSecrets.getDetails().getClientSecret());
		params.put("grant_type", DataHelper.REFRESH_TOKEN_CONSTANT);
		params.put(DataHelper.REFRESH_TOKEN_CONSTANT, refreshToken);
		
		StringBuilder postData = new StringBuilder();
		for(Map.Entry<String, Object> param : params.entrySet()) {
			if(postData.length() != 0) {
				postData.append('&');
			}
			postData.append(URLEncoder.encode(param.getKey(), 
					DataHelper.ENCODING));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), 
					DataHelper.ENCODING));
		}
		byte[] postDataBytes = 
				postData.toString().getBytes(DataHelper.ENCODING);
		URL url = new URL(DataHelper.TOKEN_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.getOutputStream().write(postDataBytes);
		
		BufferedReader reader = 
				new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuffer buffer = new StringBuffer();
		for(String line = reader.readLine(); line != null; 
				line  = reader.readLine()) {
			buffer.append(line);
		}
		  ObjectMapper objectMapper = new ObjectMapper();
		  TokenResponseDTO tokenResponseDTO = 
				  objectMapper.readValue(buffer.toString(), 
						  TokenResponseDTO.class);
		return tokenResponseDTO.getAcccessToken();
	}

}
