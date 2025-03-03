package ceu.dam.proyectofct.apiclient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ceu.dam.proyectofct.apiclient.model.Mentor;
import ceu.dam.proyectofct.apiclient.model.PracticeRecord;
import ceu.dam.proyectofct.apiclient.model.Student;
import ceu.dam.proyectofct.apiclient.model.User;

public class ApiClient {

	private static final String BASE_URL = "http://localhost:8080/user";
	private static final String API_KEY = "1234";
	
	private final RestTemplate restTemplate;
	
	public ApiClient() {
		this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}
	
	public User login(String username, String password) {
	    String passCif = DigestUtils.sha256Hex(password);
	    String url = BASE_URL + "?username=" + username + "&pass=" + passCif;

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    headers.set("API-KEY", API_KEY);

	    HttpEntity<String> entity = new HttpEntity<>(null, headers);

	    try {
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        
	        if (response.getBody() == null || response.getBody().isEmpty()) {
	            return null;
	        }

	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	        JsonNode rootNode = objectMapper.readTree(response.getBody());
	        String profile = rootNode.get("profile").asText();
	        
	        System.out.println("RootNode: " + rootNode);
	        System.out.println("Profile: " + profile);

	        if ("STUDENT".equals(profile)) {
	            return objectMapper.treeToValue(rootNode, Student.class);
	        } else if ("MENTOR".equals(profile)) {
	            return objectMapper.treeToValue(rootNode, Mentor.class);
	        } else {
	            return objectMapper.treeToValue(rootNode, User.class);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
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

	
	public List<PracticeRecord> getRecords(UUID studentId, LocalDate date1, LocalDate date2, String stateDate) {
	    if (studentId == null) {
	        System.out.println("⚠️ ERROR: studentId es null, no se pueden obtener los records.");
	        return new ArrayList<>();
	    }

	    // Construcción correcta de la URL con parámetros opcionales
	    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/practiceRecord")
	            .queryParam("userUUID", studentId);

	    if (date1 != null) {
	        uriBuilder.queryParam("date1", date1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	    }
	    if (date2 != null) {
	        uriBuilder.queryParam("date2", date2.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	    }
	    if (stateDate != null && !stateDate.isEmpty()) {
	        uriBuilder.queryParam("stateDate", stateDate);
	    }

	    String url = uriBuilder.toUriString();
	    System.out.println("📌 URL generada para GET: " + url); // 🛠️ Depuración

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    headers.set("API-KEY", API_KEY);

	    HttpEntity<String> entity = new HttpEntity<>(null, headers);

	    try {
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

	        if (response.getBody() == null || response.getBody().isEmpty()) {
	            System.out.println("⚠️ No se recibieron records.");
	            return new ArrayList<>();
	        }

	        // Deserialización con Jackson
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule()); // Soporte para LocalDate
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        
	        objectMapper.configOverride(LocalDate.class)
	        .setFormat(JsonFormat.Value.forPattern("dd-MM-yyyy"));

	        return objectMapper.readValue(response.getBody(), new TypeReference<List<PracticeRecord>>() {});
	    } catch (HttpClientErrorException e) {
	        System.out.println("❌ Error en la solicitud GET a " + url + ": " + e.getMessage());
	        e.printStackTrace();
	        return new ArrayList<>();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}
	
	public void deleteRecord(UUID id) {
		String url = BASE_URL + "/" + id;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("API-KEY", API_KEY);
		HttpEntity<Void> entity = new HttpEntity<>(null, headers);
		
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
	}
	
	public void createRecord(UUID userUUID, PracticeRecord practiceRecord) {
		String url = BASE_URL + "?userUUID=" + userUUID;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("API-KEY", API_KEY);
		
		HttpEntity<PracticeRecord> entity = new HttpEntity<>(practiceRecord, headers);
		restTemplate.postForEntity(url, entity, Void.class);
	}
}
