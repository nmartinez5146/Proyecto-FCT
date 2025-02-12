package ceu.proyecto.fct.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ServiceRepository {

	public User login(String username, String pass) throws UserException, WrongUserException, IncorrectDateException;
	
	public User changePasword(String newPass, User user) throws UserException;
	
	public User showUser(User user) throws UserException;
	
	public List<PracticeRecord> consultAllRecords(User user, LocalDate date1, LocalDate date2, String stateDate) throws UserException;
	
	public void deleteRecord(UUID idRecord) throws UserException;
	
	public void createRecord(PracticeRecord record) throws UserException;
	
}
