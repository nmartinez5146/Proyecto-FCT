package ceu.proyecto.fct.service;

import java.time.LocalDate;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ceu.proyecto.fct.model.Company;
import ceu.proyecto.fct.model.Date;
import ceu.proyecto.fct.model.Mentor;
import ceu.proyecto.fct.model.PracticeRecord;
import ceu.proyecto.fct.model.Student;
import ceu.proyecto.fct.model.User;
import ceu.proyecto.fct.model.num.Course;
import ceu.proyecto.fct.model.num.Evaluation;
import ceu.proyecto.fct.model.num.Perfil;
import ceu.proyecto.fct.repository.CompanyRepository;
import ceu.proyecto.fct.repository.DateRepository;
import ceu.proyecto.fct.repository.MentorRepository;
import ceu.proyecto.fct.repository.PracticeRecordRepository;
import ceu.proyecto.fct.repository.StudentRepository;
import ceu.proyecto.fct.repository.UserRepository;

@Component
public class Test {

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

	public void createFakeUser() {

		User user = new User();
		user.setUsername("Quichan");
		String passwordCifrada = DigestUtils.sha256Hex("Quichan");
		user.setPass(passwordCifrada);
		user.setProfile(Perfil.STUDENT);
		user.setActive(true);

		Mentor mentor = new Mentor();
		mentor.setFullName("Eibol");
		mentor.setActive(true);
		mentor = mentorRepository.save(mentor);

		Company company = new Company();
		company.setName("Tier 1");
		company.setMentorName("Jane Smith");
		company.setMentorEmail("jane.smith@faketech.com");
		company.setMentorPhone("123456789");
		company.setActive(true);
		company = companyRepository.save(company);

		Student student = new Student();
		student.setFullName("Quichan fritos");
		student.setCourse(Course.DAM);
		student.setEvaluation(Evaluation.SEPTIEMBRE);
		student.setCourseYear(2024);
		student.setMentor(mentor);
		student.setCompany(company);
		student = studentRepository.save(student);

		user.setIdStudent(student);
		user.setIdMentor(mentor);

		userRepository.save(user);

		Date practiceDate = new Date();
		practiceDate.setDate(LocalDate.now());
		practiceDate.setCourseYear(student.getCourseYear());
		practiceDate.setEvaluation(Evaluation.SEPTIEMBRE);
		practiceDate = dateRepository.save(practiceDate);

		PracticeRecord practiceRecord = new PracticeRecord();
		practiceRecord.setAssociatedStudent(student);
		practiceRecord.setAssociatedDate(practiceDate);
		practiceRecord.setHours(5);
		practiceRecord.setDescription("Práctica sobre desarrollo web en Java");

		recordRepository.save(practiceRecord);
	}

}
