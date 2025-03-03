package ceu.dam.proyectofct.apiclient;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ceu.dam.proyectofct.apiclient.model.Mentor;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.Student;
import ceu.dam.proyectofct.apiclient.model.User;

public class ApiClient {

	private static final String BASE_URL = "http://localhost:8080/user";
	private static final String API_KEY = "1234";
	
	private final RestTemplate restTemplate;
	private final Gson gson;
	
	public ApiClient() {
		this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		this.gson = new Gson();
	}
	
	public Object login(String username, String password) {
	    String passCif = DigestUtils.sha256Hex(password);
	    String url = BASE_URL + "?username=" + username + "&pass=" + passCif;

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    headers.set("API-KEY", API_KEY);

	    HttpEntity<String> entity = new HttpEntity<>(null, headers);
	    System.out.println("Headers enviados: " + headers);
	    
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    System.out.println("JSON Response: " + response.getBody());  // 🔥 IMPRIMIMOS EL JSON

	    if (response.getBody() == null || response.getBody().isEmpty()) {
	        return null;
	    }

	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    try {
	        User user = objectMapper.readValue(response.getBody(), User.class);
	        System.out.println("User deserializado correctamente: " + user);

	        if ("STUDENT".equals(user.getProfile().toString())) {
	            return objectMapper.readValue(response.getBody(), Student.class);
	        } else if ("MENTOR".equals(user.getProfile().toString())) {
	            return objectMapper.readValue(response.getBody(), Mentor.class);
	        } else {
	            return user;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();  // 🔥 IMPRIMIMOS ERROR SI FALLA
	        return null;
	    }
	}


	
	public User changePassword(UUID userUUID, String newPassword) {
		String passCif = DigestUtils.sha256Hex(newPassword);
		String url = BASE_URL + "?userUUID=" + userUUID + "&newPass=" + passCif;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("API-KEY", API_KEY);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT, entity, User.class);
		return response.getBody();
	}

	
	public List<PracticeRecord> getRecords(UUID userId, String date1, String date2, String stateDate) {
		String url = BASE_URL + "/practiceRecord?user=" + userId;
		if (date1 != null) url += "&date1=" + date1;
		if (date2 != null) url += "&date2=" + date2;
		if (stateDate != null) url += "&stateDate" + stateDate;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("API-KEY", API_KEY);
		HttpEntity<Object> entity = new HttpEntity<>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return gson.fromJson(response.getBody(), new TypeToken<List<PracticeRecord>>() {}.getType());
	}
	
	public void deleteRecord(UUID id) {
		String url = BASE_URL + "/" + id;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("API-KEY", API_KEY);
		HttpEntity<Void> entity = new HttpEntity<>(null, headers);
		
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
	}
	
	public void createRecord(PracticeRecord practiceRecord) {
		String url = BASE_URL;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("API-KEY", API_KEY);
		
		HttpEntity<PracticeRecord> entity = new HttpEntity<>(practiceRecord, headers);
		restTemplate.postForEntity(url, entity, Void.class);
	}
}
