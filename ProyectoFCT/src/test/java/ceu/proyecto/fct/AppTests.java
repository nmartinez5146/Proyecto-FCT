package ceu.proyecto.fct;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import ceu.proyecto.fct.model.Company;
import ceu.proyecto.fct.model.Date;
import ceu.proyecto.fct.model.Mentor;
import ceu.proyecto.fct.model.PracticeRecord;
import ceu.proyecto.fct.model.Student;
import ceu.proyecto.fct.model.User;
import ceu.proyecto.fct.model.num.Evaluation;
import ceu.proyecto.fct.model.num.Perfil;
import ceu.proyecto.fct.repository.CompanyRepository;
import ceu.proyecto.fct.repository.DateRepository;
import ceu.proyecto.fct.repository.MentorRepository;
import ceu.proyecto.fct.repository.PracticeRecordRepository;
import ceu.proyecto.fct.repository.StudentRepository;
import ceu.proyecto.fct.repository.UserRepository;
import ceu.proyecto.fct.service.IncorrectDataException;
import ceu.proyecto.fct.service.ServiceImp;
import ceu.proyecto.fct.service.UserException;
import ceu.proyecto.fct.service.WrongUserException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class AppTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MentorRepository mentorRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private DateRepository dateRepository;

	@Autowired
	private PracticeRecordRepository recordRepository;

	@Autowired
	private ServiceImp userService;

	private User testUser;
	private PracticeRecord testRecord;

	@BeforeAll
	void setUp() {
		User user = new User();
		user.setUsername("Franchisco");
		String passwordCifrada = DigestUtils.sha256Hex("Franchisco");
		user.setPass(passwordCifrada);
		user.setProfile(Perfil.STUDENT);
		user.setActive(true);

		Mentor mentor = new Mentor();
		mentor.setFullName("Indalecio");
		mentor.setActive(true);
		mentor = mentorRepository.save(mentor);

		Company company = new Company();
		company.setName("NTT");
		company.setMentorName("Rosario Smith");
		company.setMentorEmail("Rosi.smith@faketech.com");
		company.setMentorPhone("987654321");
		company.setActive(true);
		company = companyRepository.save(company);

		Student student = new Student();
		student.setFullName("Franchiscos Cavrera");
		student.setCourse(Course.DAM);
		student.setEvaluation(Evaluation.MARZO);
		student.setCourseYear(2024);
		student.setMentor(mentor);
		student.setCompany(company);
		student = studentRepository.save(student);

		user.setIdStudent(student);

		userRepository.save(user);
		testUser = user;

		Date practiceDate = new Date();
		practiceDate.setDate(LocalDate.now());
		practiceDate.setCourseYear(student.getCourseYear());
		practiceDate.setEvaluation(Evaluation.MARZO);
		practiceDate = dateRepository.save(practiceDate);

		PracticeRecord practiceRecord = new PracticeRecord();
		practiceRecord.setAssociatedStudent(student);
		practiceRecord.setAssociatedDate(practiceDate);
		practiceRecord.setHours(8);
		practiceRecord.setDescription("Práctica sobre desarrollo web en Java");

		recordRepository.save(practiceRecord);
		testRecord = practiceRecord;
	}

	@Test
	@Order(1)
	void loginSuccessful() throws UserException, WrongUserException, IncorrectDataException {
		User result = userService.login("Franchisco", "Franchisco");

		assertNotNull(result);
		assertEquals("Franchisco", result.getUsername());
	}

	@Test
	@Order(2)
	void loginUserNotFound() {
		assertThrows(WrongUserException.class, () -> {
			userService.login("NonExistentUser", "password");
		});
	}

	@Test
	@Order(3)
	void loginUserWithoutStudent() {
		testUser.setIdStudent(null);
		userRepository.save(testUser);

		assertThrows(WrongUserException.class, () -> {
			userService.login("Franchisco", "Franchisco");
		});
	}

	@Test
	@Order(4)
	void loginInactiveUser() {
		testUser.setActive(false);
		userRepository.save(testUser);

		assertThrows(WrongUserException.class, () -> {
			userService.login("Franchisco", "Franchisco");
		});
	}

	@Test
	@Order(5)
	void loginIncorrectPassword() {
		assertThrows(IncorrectDataException.class, () -> {
			userService.login("Franchisco", "wrongpassword");
		});
	}

	@Test
	@Order(6)
	void changePasswordSuccess() throws UserException, WrongUserException, IncorrectDataException {
		String newPassword = "newPassword123";
		String newPasswordCifrada = DigestUtils.sha256Hex(newPassword);
		User updatedUser = userService.changePasword(newPassword, testUser);

		assertNotNull(updatedUser);
		assertEquals(newPasswordCifrada, updatedUser.getPass());
	}

	@Test
	@Order(7)
	void changePasswordUserNotFound() {
		assertThrows(WrongUserException.class, () -> {
			userService.changePasword("newPassword123", null);
		});
	}

	@Test
	@Order(8)
	void changePasswordEmptyPassword() {
		assertThrows(IncorrectDataException.class, () -> {
			userService.changePasword("", testUser);
		});
	}

	@Test
	@Order(9)
	void changePasswordNullPassword() {
		assertThrows(IncorrectDataException.class, () -> {
			userService.changePasword(null, testUser);
		});
	}

	@Test
	@Order(10)
	void changePasswordSamePassword() {
		String samePassword = "newPassword123";
		String samePasswordCifrada = DigestUtils.sha256Hex(samePassword);

		assertThrows(IncorrectDataException.class, () -> {
			userService.changePasword(samePasswordCifrada, testUser);
		});
	}

	@Test
	@Order(11)
	void showUserSuccess() throws UserException, WrongUserException {
		User retrievedUser = userService.showUser(testUser.getId());

		assertNotNull(retrievedUser);
		assertEquals(testUser.getUsername(), retrievedUser.getUsername());
		assertEquals(testUser.getPass(), retrievedUser.getPass());
		assertEquals(testUser.getProfile(), retrievedUser.getProfile());
		assertTrue(retrievedUser.isActive());
	}

	@Test
	@Order(12)
	void showUserUserNotFound() {
		assertThrows(WrongUserException.class, () -> {
			userService.showUser(null);
		});
	}

	@Test
	@Order(13)
	void consultAllRecordsSuccess() throws UserException, WrongUserException {
		List<PracticeRecord> records = userService.consultAllRecords(testUser.getId(), LocalDate.of(2024, 3, 15),
				LocalDate.now(), "All");

		assertNotNull(records);
		assertEquals(1, records.size());
	}

	@Test
	@Order(14)
	void consultAllRecordsUserNotFound() {
		assertThrows(WrongUserException.class, () -> {
			userService.consultAllRecords(null, LocalDate.of(2024, 3, 15), LocalDate.now(), null);
		});
	}

	@Test
	@Order(15)
	void consultAllRecordsUserWithoutStudentId() {
		User userWithoutStudent = new User();
		userWithoutStudent.setUsername("UserWithoutStudent");

		assertThrows(UserException.class, () -> {
			userService.consultAllRecords(userWithoutStudent.getId(), LocalDate.of(2024, 3, 15), LocalDate.now(), null);
		});
	}

	@Test
	@Order(16)
	void deleteRecordSuccess() throws Exception {
		userService.deleteRecord(testRecord.getId());
		assertFalse(recordRepository.findById(testRecord.getId()).isPresent());
	}

	@Test
	@Order(17)
	void deleteRecordNullId() {
		assertThrows(IncorrectDataException.class, () -> userService.deleteRecord(null));
	}

	@Test
	@Order(18)
	void deleteRecordRecordNotFound() {
		UUID fakeId = UUID.randomUUID();
		assertThrows(IncorrectDataException.class, () -> userService.deleteRecord(fakeId));
	}

	@Test
	@Order(19)
	void createRecordSuccess() throws Exception {

		Date practiceDate = new Date();
		practiceDate.setDate(LocalDate.now());
		practiceDate.setCourseYear(testUser.getIdStudent().getCourseYear());
		practiceDate.setEvaluation(Evaluation.MARZO);
		practiceDate = dateRepository.save(practiceDate);

		PracticeRecord record = new PracticeRecord();
		record.setAssociatedStudent(testUser.getIdStudent());
		record.setAssociatedDate(practiceDate);
		record.setHours(5);
		record.setDescription("Práctica en empresa");

		userService.createRecord(testUser.getId(), record);

		assertTrue(recordRepository.findById(record.getId()).isPresent());
	}

	@Test
	@Order(20)
	void createRecordNullRecord() {
		assertThrows(IncorrectDataException.class, () -> userService.createRecord(testUser.getId(), null));
	}

	@Test
	@Order(21)
	void createRecordNoStudentOrDate() {

		Date practiceDate = new Date();
		practiceDate.setDate(LocalDate.now());
		practiceDate.setCourseYear(testUser.getIdStudent().getCourseYear());
		practiceDate.setEvaluation(Evaluation.MARZO);
		practiceDate = dateRepository.save(practiceDate);

		PracticeRecord recordWithoutStudent = new PracticeRecord();
		recordWithoutStudent.setAssociatedDate(practiceDate);
		recordWithoutStudent.setHours(5);
		recordWithoutStudent.setDescription("Práctica en empresa");

		assertThrows(IncorrectDataException.class,
				() -> userService.createRecord(testUser.getId(), recordWithoutStudent));

		PracticeRecord recordWithoutDate = new PracticeRecord();
		recordWithoutDate.setAssociatedStudent(testUser.getIdStudent());
		recordWithoutDate.setHours(5);
		recordWithoutDate.setDescription("Práctica en empresa");

		assertThrows(IncorrectDataException.class, () -> userService.createRecord(testUser.getId(), recordWithoutDate));
	}

	@Test
	@Order(22)
	void loginMentorSuccess() throws UserException, WrongUserException, IncorrectDataException {
		Mentor mentor = new Mentor();
		mentor.setFullName("Mentor Test");
		mentor.setActive(true);
		mentor = mentorRepository.save(mentor);

		User mentorUser = new User();
		mentorUser.setUsername("MentorUser");
		mentorUser.setPass(DigestUtils.sha256Hex("MentorPass"));
		mentorUser.setProfile(Perfil.MENTOR);
		mentorUser.setActive(true);
		mentorUser = userRepository.save(mentorUser);

		User result = userService.login("MentorUser", "MentorPass");

		assertNotNull(result);
		assertEquals("MentorUser", result.getUsername());
	}

	@Test
	@Order(23)
	void changePasswordTooShort() {
		assertThrows(IncorrectDataException.class, () -> {
			userService.changePasword("123", testUser);
		});
	}

	@Test
	@Order(24)
	void changePasswordNotHashed() {
		String plainPassword = "simplepassword";

		assertThrows(IncorrectDataException.class, () -> {
			userService.changePasword(plainPassword, testUser);
		});
	}

	@Test
	@Order(25)
	void consultAllRecordsWithReversedDates() {
		assertThrows(UserException.class, () -> {
			userService.consultAllRecords(testUser.getId(), LocalDate.now(), LocalDate.of(2024, 3, 15), "All");
		});
	}

	@Test
	@Order(26)
	void deleteRecordAlreadyDeleted() {
		userService.deleteRecord(testRecord.getId());

		assertThrows(IncorrectDataException.class, () -> {
			userService.deleteRecord(testRecord.getId());
		});
	}

	@Test
	@Order(27)
	void createRecordWithMoreThan8Hours() {
		Date practiceDate = new Date();
		practiceDate.setDate(LocalDate.now());
		practiceDate.setCourseYear(testUser.getIdStudent().getCourseYear());
		practiceDate.setEvaluation(Evaluation.MARZO);
		practiceDate = dateRepository.save(practiceDate);

		PracticeRecord record = new PracticeRecord();
		record.setAssociatedStudent(testUser.getIdStudent());
		record.setAssociatedDate(practiceDate);
		record.setHours(9);
		record.setDescription("Práctica en empresa");

		assertThrows(IncorrectDataException.class, () -> userService.createRecord(testUser.getId(), record));
	}

	@Test
	@Order(28)
	void createRecordWithLessThan1Hour() {
		Date practiceDate = new Date();
		practiceDate.setDate(LocalDate.now());
		practiceDate.setCourseYear(testUser.getIdStudent().getCourseYear());
		practiceDate.setEvaluation(Evaluation.MARZO);
		practiceDate = dateRepository.save(practiceDate);

		PracticeRecord record = new PracticeRecord();
		record.setAssociatedStudent(testUser.getIdStudent());
		record.setAssociatedDate(practiceDate);
		record.setHours(0);
		record.setDescription("Práctica en empresa");

		assertThrows(IncorrectDataException.class, () -> userService.createRecord(testUser.getId(), record));
	}

}
