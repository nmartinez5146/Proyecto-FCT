package ceu.proyecto.fct.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ceu.proyecto.fct.model.Mentor;
import ceu.proyecto.fct.model.PracticeRecord;
import ceu.proyecto.fct.model.Student;
import ceu.proyecto.fct.model.User;
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
	private PracticeRecordRepository recordRepository;

	@Override
	public User login(String username, String pass) throws UserException, WrongUserException, IncorrectDataException {
		try {

			log.info("Attempting login for username: {}", username);
			User user = userRepository.findByUsername(username);
			if (user == null) {
				log.error("User not found");
				throw new WrongUserException("User not found.");
			}

			if (!user.isStudent()) {
				log.error("User is not associated with a student");
				throw new WrongUserException("User is not associated with a student.");
			}

			if (!user.isActive()) {
				log.error("User is not active");
				throw new WrongUserException("User is not active.");
			}

			if (!pass.equals(user.getPass())) {
				log.error("Incorrect password");
				throw new IncorrectDataException("Incorrect password.");
			}

			log.info("Login successful for user: {}", username);

			if (user instanceof Student) {
				Student student = (Student) user;
				Hibernate.initialize(student.getCompany());
				Hibernate.initialize(student.getMentor());
				return student;
			}

			if (user instanceof Mentor) {
				Mentor mentor = (Mentor) user;
				Hibernate.initialize(mentor.getStudents());
				return mentor;
			}

			log.info("User is not instance of any Student or Mentor");
			return user;
		} catch (DataAccessException e) {
			log.error("Data Base Error", e);
			throw new UserException("Data Base Error", e);
		}
	}

	@Override
	public User changePasword(String newPass, User user)
			throws UserException, WrongUserException, IncorrectDataException {
		try {

			log.info("Changing password for user: {}", user);

			if (user == null) {
				log.error("User not found");
				throw new WrongUserException("User not found.");
			}

			if (newPass == null || newPass.trim().isEmpty()) {
				log.error("New password cannot be empty");
				throw new IncorrectDataException("New password cannot be empty.");
			}

			if (newPass.equals(user.getPass())) {
				log.error("New password cannot be the same as the last pass");
				throw new IncorrectDataException("New password cannot be the same as the last pass");
			}

			user.setPass(newPass);
			userRepository.save(user);
			log.info("Password changed successfully for user: {}", user);
			return user;
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error", e);
		}
	}

	@Override
	public User showUser(UUID userID) throws UserException, WrongUserException {
		try {
			Optional<User> userOptional = userRepository.findById(userID);

			if (userOptional.isEmpty()) {
				log.error("User not found with ID: {}", userID);
				throw new WrongUserException("User not found.");
			}

			User user = userOptional.get();
			log.info("Showing user details: {}", user);
			return user;
		} catch (DataAccessException e) {
			log.error("Database error while fetching user with ID: {}", userID, e);
			throw new UserException("Database error", e);
		}
	}

	@Override
	public List<PracticeRecord> consultAllRecords(UUID userUUID, LocalDate date1, LocalDate date2, String stateDate)
			throws UserException, WrongUserException {
		try {

			Optional<User> userOp = userRepository.findById(userUUID);

			log.info("Consulting all records for userUUID: {}", userUUID);

			if (!userOp.isPresent()) {
				log.error("User not found");
				throw new WrongUserException("User not found.");
			}

			if (!studentRepository.findById(userUUID).isPresent()) {
				log.error("User has no associated student ID");
				throw new UserException("User has no associated student ID.");
			}

			log.info("Fetching records for student ID: {}", userUUID);

			List<PracticeRecord> allRecords = recordRepository.findByAssociatedStudent_Id(userUUID);

			List<PracticeRecord> filteredRecords = allRecords.stream().filter(record -> {
				LocalDate recordDate = record.getAssociatedDate().getDate();
				return (date1 == null || !recordDate.isBefore(date1)) && (date2 == null || !recordDate.isAfter(date2));
			}).map(record -> {
				PracticeRecord fullRecord = new PracticeRecord();
				fullRecord.setAssociatedDate(record.getAssociatedDate());
				fullRecord.setHours(record.getHours());
				fullRecord.setDescription(record.getDescription());
				return fullRecord;
			}).collect(Collectors.toList());

			return filteredRecords;
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error", e);
		}
	}

	@Override
	public void deleteRecord(UUID idRecord) throws UserException, IncorrectDataException {
		try {

			log.info("Deleting record with ID: {}", idRecord);

			if (idRecord == null) {
				log.error("Record ID cannot be null");
				throw new IncorrectDataException("Record ID cannot be null.");
			}
			if (!recordRepository.existsById(idRecord)) {
				log.error("Record not found");
				throw new IncorrectDataException("Record not found.");
			}
			recordRepository.deleteById(idRecord);
			log.info("Record deleted successfully");
		} catch (DataAccessException e) {
			log.error("Data Base Error");
			throw new UserException("Data Base Error", e);
		}
	}

	@Override
	public void createRecord(UUID userUUID, PracticeRecord practiceRecord)
			throws UserException, IncorrectDataException {
		try {

			log.info("Creating new record: {}", practiceRecord);

			if (practiceRecord == null) {
				log.error("Record cannot be null");
				throw new IncorrectDataException("Record cannot be null.");
			}
			Optional<Student> student = studentRepository.findById(userUUID);

			if (!student.isPresent()) {
				log.error("User is not present");
				throw new IncorrectDataException("User is not present");
			}

			practiceRecord.setAssociatedStudent(student.get());
			System.out.println(practiceRecord);
			System.out.println(practiceRecord.getAssociatedStudent());
			System.out.println(practiceRecord.getAssociatedDate());

			if (practiceRecord.getAssociatedStudent() == null || practiceRecord.getAssociatedDate() == null) {
				log.error("Record must be associated with a student and a date");
				throw new IncorrectDataException("Record must be associated with a student and a date.");
			}
			recordRepository.save(practiceRecord);
			log.info("Record created successfully");
		} catch (DataAccessException e) {
			log.error("Data Base Error", e);
			throw new UserException("Data Base Error", e);
		}
	}
}
