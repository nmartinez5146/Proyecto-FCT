package ceu.proyecto.fct.api;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.proyecto.fct.model.PracticeRecord;
import ceu.proyecto.fct.model.User;
import ceu.proyecto.fct.service.IncorrectDataException;
import ceu.proyecto.fct.service.ServiceImp;
import ceu.proyecto.fct.service.UserException;
import ceu.proyecto.fct.service.WrongUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
@SecurityRequirement(name = "Authorization")
public class UserApiService {

	@Autowired
	private ServiceImp service;

	@GetMapping
	@Operation(summary = "Login", description = "Logs in a user using their username and password.")
	public User login(@RequestParam String username, @RequestParam String pass)
			throws UserException, WrongUserException, IncorrectDataException {
		return service.login(username, pass);
	}

	@PutMapping
	@Operation(summary = "Change pass", description = "Changes the old password to a new one.")
	public User changePass(@RequestParam @Valid UUID userUUID, @RequestParam @Valid String newPass)
			throws UserException, WrongUserException, IncorrectDataException {
		User user = service.showUser(userUUID);
		return service.changePasword(newPass, user);
	}

	@GetMapping("/data/{userId}")
	@Operation(summary = "Show full user", description = "Displays a user with all associated entities.")
	public User showUser(@PathVariable UUID userId) throws UserException, WrongUserException {
		return service.showUser(userId);
	}

	@GetMapping("/practiceRecord")
	@Operation(summary = "Retrieve records", description = "Retrieves all user records based on the given filters.")
	public List<PracticeRecord> consultAllRecords(@RequestParam User user,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date1,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date2,
			@RequestParam(required = false) String stateDate) throws UserException, WrongUserException {
		return service.consultAllRecords(user, date1, date2, stateDate);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete record", description = "Deletes a record based on its ID.")
	public void deleteRecord(@PathVariable UUID id) throws UserException, IncorrectDataException {
		service.deleteRecord(id);
	}

	@PostMapping
	@Operation(summary = "Create record", description = "Creates a record based on the given data.")
	public void createRecord(@RequestParam UUID userUUID, @RequestBody @Valid PracticeRecord practiceRecord) throws UserException, IncorrectDataException {
		service.createRecord(userUUID, practiceRecord);
	}

}
