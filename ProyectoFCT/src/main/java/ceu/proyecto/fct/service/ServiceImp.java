package ceu.proyecto.fct.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
	}

	@Override
	public User changePasword(String newPass, User user) throws UserException {
		log.info("Changing password for user: {}", user);

		if (user == null) {
			log.error("User not found");
			throw new UserException("User not found.");
		}

		if (newPass == null || newPass.trim().isEmpty()) {
			log.error("New password cannot be empty");
			throw new UserException("New password cannot be empty.");
		}
		if (newPass.length() != 8) {
			log.error("New password must be exactly 8 characters");
			throw new UserException("New password must be exactly 8 characters.");
		}
		String passwordCifrada = DigestUtils.sha256Hex(newPass);
		user.setPass(newPass);
		log.info("Password changed successfully for user: {}", user);
		return user;
	}

	@Override
	public User showUser(User user) throws UserException {
		if (user == null) {
			log.error("User not found");
			throw new UserException("User not found.");
		}
		log.info("Showing user details: {}", user);
		return user;
	}

	@Override
	public List<PracticeRecord> consultAllRecords(User user, LocalDate date1, LocalDate date2, String stateDate) throws UserException {
		log.info("Consulting all records for user: {}", user);

		if (user == null) {
			log.error("User not found");
			throw new UserException("User not found.");
		}

		if (date1 == null || date2 == null) {
			log.error("Dates cannot be null");
			throw new UserException("Dates cannot be null.");
		}

		UUID studentId = user.getIdStudent().getId();
		if (studentId == null) {
			log.error("User has no associated student ID");
			throw new UserException("User has no associated student ID.");
		}

		log.info("Fetching records for student ID: {}", studentId);
		return pRRepository.findByAssociatedStudentAndAssociatedDateBetweenAndState(studentId, date1, date2, stateDate);
	}

	@Override
	public void deleteRecord(UUID idRecord) throws UserException {
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
	}

	@Override
	public void createRecord(PracticeRecord record) throws UserException {
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
	}
}
