package ceu.proyecto.fct.service;

import java.time.LocalDate;

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

        // 🔹 Crear usuario base en la tabla `users`
        User baseUser = new User();
        baseUser.setUsername("Quichan");
        baseUser.setPass("1f89ab777d7d833177ab31cbac2f11220fa6dc76ade5efb383fde86079253b00");
        baseUser.setProfile(Perfil.STUDENT);
        baseUser.setActive(true);
        baseUser = userRepository.save(baseUser); // ✅ Guardamos en la BD primero

        // 🔹 Crear estudiante **sin asignar manualmente el ID**
        Student student = new Student();
        student.setUsername(baseUser.getUsername());
        student.setPass(baseUser.getPass());
        student.setProfile(baseUser.getProfile());
        student.setActive(baseUser.isActive());
        student.setFullName("Quichan Fritos");
        student.setCourse(Course.DAM);
        student.setEvaluation(Evaluation.SEPTIEMBRE);
        student.setCourseYear(2024);

        student = studentRepository.save(student); // ✅ Guardar estudiante después de `User`

        // 🔹 Crear usuario base para el mentor
        User baseMentor = new User();
        baseMentor.setUsername("Eibol");
        baseMentor.setPass("EibolPass");
        baseMentor.setProfile(Perfil.MENTOR);
        baseMentor.setActive(true);
        baseMentor = userRepository.save(baseMentor); // ✅ Guardar en la BD antes de hacer `Mentor`

        // 🔹 Crear mentor **sin asignar manualmente el ID**
        Mentor mentor = new Mentor();
        mentor.setUsername(baseMentor.getUsername());
        mentor.setPass(baseMentor.getPass());
        mentor.setProfile(baseMentor.getProfile());
        mentor.setActive(baseMentor.isActive());
        mentor.setFullName("Eibol Mentor");

        mentor = mentorRepository.save(mentor); // ✅ Guardar mentor después de `User`

        // 🔹 Asignar mentor al estudiante y guardar
        student.setMentor(mentor);
        student = studentRepository.save(student);

        // 🔹 Crear empresa con tutor de la empresa
        Company company = new Company();
        company.setName("Tier 1");
        company.setMentorName("Jane Smith"); // Tutor de la empresa
        company.setMentorEmail("jane.smith@faketech.com");
        company.setMentorPhone("123456789");
        company.setActive(true);
        company = companyRepository.save(company);

        // Asignar empresa al estudiante y guardar
        student.setCompany(company);
        student = studentRepository.save(student);

        // 🔹 Crear fecha de práctica
        Date practiceDate = new Date();
        practiceDate.setDate(LocalDate.now().plusDays(1));
        practiceDate.setCourseYear(student.getCourseYear());
        practiceDate.setEvaluation(Evaluation.SEPTIEMBRE);
        practiceDate = dateRepository.save(practiceDate);

        // 🔹 Registrar práctica del estudiante
        PracticeRecord practiceRecord = new PracticeRecord();
        practiceRecord.setAssociatedStudent(student);
        practiceRecord.setAssociatedDate(practiceDate);
        practiceRecord.setHours(5);
        practiceRecord.setDescription("Práctica sobre desarrollo web en Java");

        recordRepository.save(practiceRecord);
    }
}
