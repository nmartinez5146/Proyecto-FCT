package ceu.dam.proyectofct.apiclient;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ceu.dam.proyectofct.apiclient.model.ChangePassRequest;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.User;

public class ApiClient {

	private static final String BASE_URL = "http://localhost:8080/api-docs/user";
	private final RestTemplate restTemplate;
	private final Gson gson;
	
	public ApiClient() {
		this.restTemplate = new RestTemplate();
		this.gson = new Gson();
	}
	
	public User login(String username, String password) {
		String passCif = DigestUtils.sha256Hex(password);
		String url = BASE_URL + "?username=" + username + "&pass=" + passCif;
		ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
		return response.getBody();
	}
	
	public User changePassword(String username, String newPassword) {
		String passCif = DigestUtils.sha256Hex(newPassword);
		String url = BASE_URL;
		ChangePassRequest request = new ChangePassRequest(username, passCif);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<ChangePassRequest> entity = new HttpEntity<>(request, headers);
		
		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT, entity, User.class);
		return response.getBody();
	}
	
	public User showUser(String username) {
		String url = BASE_URL + "/data?user=" + username;
		ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
		return response.getBody();
	}
	
	public List<PracticeRecord> getRecords(String username, String date1, String date2, String stateDate) {
		String url = BASE_URL + "/PracticeRecord?user=";
		if (date1 != null) url += "&date1=" + date1;
		if (date2 != null) url += "&date2=" + date2;
		if (stateDate != null) url += "&stateDate=" + stateDate;
		
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return gson.fromJson(response.getBody(), new TypeToken<List<PracticeRecord>>() {}.getType());
	}
	
	public void deleteRecord(UUID id) {
		String url = BASE_URL + "/" + id;
		restTemplate.delete(url);
	}
	
	public void createRecord(PracticeRecord record) {
		String url = BASE_URL;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<PracticeRecord> entity = new HttpEntity<>(record, headers);
		
		restTemplate.postForEntity(url, entity, Void.class);
	}
}
