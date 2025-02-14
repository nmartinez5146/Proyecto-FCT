package ceu.proyecto.fct.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ceu.proyecto.fct.model.PracticeRecord;
import ceu.proyecto.fct.model.User;
import ceu.proyecto.fct.repository.CompanyRepository;
import ceu.proyecto.fct.repository.DateRepository;
import ceu.proyecto.fct.repository.MentorRepository;
import ceu.proyecto.fct.repository.PracticeRecordRepository;
import ceu.proyecto.fct.repository.StudentRepository;
import ceu.proyecto.fct.repository.UserRepository;

@org.springframework.stereotype.Service
public class ServiceImp implements Service {

	private static final Logger log = LoggerFactory.getLogger(ServiceImp.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MentorRepository mentorRepository;

	@Autowired
	private DateRepository dateRepository;

	@Autowired
	private PracticeRecordRepository pRRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public User login(String username, String pass) throws UserException, WrongUserException, IncorrectDateException {
		try {
			
		
		log.info("Attempting login for username: {}", username);
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error("User not found");
			throw new WrongUserException("User not found.");
		}

		if (user.getIdStudent() == null) {
			log.error("User is not associated with a student");
			throw new WrongUserException("User is not associated with a student.");
		}

		if (!user.isActive()) {
			log.error("User is not active");
			throw new WrongUserException("User is not active.");
		}

		String passwordCifrada = DigestUtils.sha256Hex(pass);
		if (!passwordCifrada.equals(user.getPass())) {
			log.error("Incorrect password");
			throw new IncorrectDateException("Incorrect password.");
		}

		log.info("Login successful for user: {}", username);
		return user;
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error");
		}
	}

	@Override
	public User changePasword(String newPass, User user) throws UserException {
		try {
			
		
		log.info("Changing password for user: {}", user);

		if (user == null) {
			log.error("User not found");
			throw new UserException("User not found.");
		}

		if (newPass == null || newPass.trim().isEmpty()) {
			log.error("New password cannot be empty");
			throw new UserException("New password cannot be empty.");
		}
		user.setPass(newPass);
		log.info("Password changed successfully for user: {}", user);
		return user;
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error");
		}
	}

	@Override
	public User showUser(User user) throws UserException {
		try {
			
		
		if (user == null) {
			log.error("User not found");
			throw new UserException("User not found.");
		}
		log.info("Showing user details: {}", user);
		return user;
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error");
		}
	}

	@Override
	public List<PracticeRecord> consultAllRecords(User user, LocalDate date1, LocalDate date2, String stateDate) throws UserException {
		try {
			
		
	    log.info("Consulting all records for user: {}", user);

	    if (user == null) {
	        log.error("User not found");
	        throw new UserException("User not found.");
	    }

	    UUID studentId = user.getIdStudent().getId();
	    if (studentId == null) {
	        log.error("User has no associated student ID");
	        throw new UserException("User has no associated student ID.");
	    }

	    log.info("Fetching records for student ID: {}", studentId);

	    List<PracticeRecord> allRecords = pRRepository.findByAssociatedStudent_Id(studentId);

	    List<PracticeRecord> filteredRecords = allRecords.stream()
	        .filter(record -> {
	            LocalDate recordDate = record.getAssociatedDate().getDate();
	            return (date1 == null || !recordDate.isBefore(date1)) &&
	                   (date2 == null || !recordDate.isAfter(date2));
	        })
	        .map(record -> {
	            PracticeRecord truncatedRecord = new PracticeRecord();
	            truncatedRecord.setAssociatedDate(record.getAssociatedDate());
	            truncatedRecord.setHours(record.getHours());
	            truncatedRecord.setDescription(record.getDescription().length() > 20 ?
	                record.getDescription().substring(0, 20) + "..." : record.getDescription());
	            return truncatedRecord;
	        })
	        .collect(Collectors.toList());

	    return filteredRecords;
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error");
		}
	}

	@Override
	public void deleteRecord(UUID idRecord) throws UserException {
		try {
			
		
		log.info("Deleting record with ID: {}", idRecord);

		if (idRecord == null) {
			log.error("Record ID cannot be null");
			throw new UserException("Record ID cannot be null.");
		}
		if (!pRRepository.existsById(idRecord)) {
			log.error("Record not found");
			throw new UserException("Record not found.");
		}
		pRRepository.deleteById(idRecord);
		log.info("Record deleted successfully");
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error");
		}
	}

	@Override
	public void createRecord(PracticeRecord record) throws UserException {
		try {
			
		
		log.info("Creating new record: {}", record);

		if (record == null) {
			log.error("Record cannot be null");
			throw new UserException("Record cannot be null.");
		}
		if (record.getAssociatedStudent() == null || record.getAssociatedDate() == null) {
			log.error("Record must be associated with a student and a date");
			throw new UserException("Record must be associated with a student and a date.");
		}
		pRRepository.save(record);
		log.info("Record created successfully");
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error");
		}
	}
}
