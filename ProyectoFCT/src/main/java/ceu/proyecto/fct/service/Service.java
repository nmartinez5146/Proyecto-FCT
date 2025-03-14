package ceu.proyecto.fct.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import ceu.proyecto.fct.model.PracticeRecord;
import ceu.proyecto.fct.model.User;

public interface Service {

	public User login(String username, String pass) throws UserException, WrongUserException, IncorrectDataException;

	public User changePasword(String newPass, User user)
			throws UserException, WrongUserException, IncorrectDataException;

	public User showUser(UUID userId) throws UserException, WrongUserException;

	public List<PracticeRecord> consultAllRecords(UUID userUUID, LocalDate date1, LocalDate date2, String stateDate)
			throws UserException, WrongUserException;

	public void deleteRecord(UUID idRecord) throws UserException, IncorrectDataException;

	void createRecord(UUID userUUID, PracticeRecord practiceRecord) throws UserException, IncorrectDataException;

}
