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
public class ServiceImp implements Service{

	
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
		User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new WrongUserException("Usuario no encontrado.");
        }

        
        if (user.getIdStudent() == null) {
            throw new WrongUserException("El usuario no está asociado a un alumno.");
        }
        
        
        if (!user.isActive()) {  
            throw new WrongUserException("El usuario no está activo.");
        }
        
        String passwordCifrada = DigestUtils.sha256Hex(pass);  
        if (!passwordCifrada.equals(user.getPass())) {  
            throw new IncorrectDateException("Contraseña incorrecta.");
        }

        
        return user;
	}

	@Override
	public User changePasword(String newPass, User user) throws UserException {

		if (user == null) {
			throw new UserException("Usuario no encontrado.");
		}

		if (newPass == null || newPass.trim().isEmpty()) {
			throw new UserException("La nueva contraseña no puede ser vacía.");
		}
		if (newPass.length() != 8) {
			throw new UserException("La nueva contraseña debe tener exactamente 8 caracteres.");
		}
		String passwordCifrada = DigestUtils.sha256Hex(newPass); 
		user.setPass(newPass);
		return user;
	}

	@Override
	public User showUser(User user) throws UserException {

		if (user == null) {
			throw new UserException("Usuario no encontrado.");
		}
		return user;
	}

	@Override
	public List<PracticeRecord> consultAllRecords(User user, LocalDate date1, LocalDate date2, String stateDate)
			throws UserException {
		
		
		return null;
	}

	@Override
	public void deleteRecord(UUID idRecord) throws UserException {
		
		
	}

	@Override
	public void createRecord(PracticeRecord record) throws UserException {
		
		
	}

}
