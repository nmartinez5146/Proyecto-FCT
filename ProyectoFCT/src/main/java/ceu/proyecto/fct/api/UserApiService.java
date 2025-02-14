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

import ceu.proyecto.fct.api.request.ChangePassRequest;
import ceu.proyecto.fct.model.PracticeRecord;
import ceu.proyecto.fct.model.User;
import ceu.proyecto.fct.service.IncorrectDateException;
import ceu.proyecto.fct.service.ServiceRepository;
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
	private ServiceRepository service; /////// Cambiar por el service

	@GetMapping
	@Operation(summary = "Login", description = "Logs in a user using their username and password.")
	public User login(@RequestParam String username, @RequestParam String pass)
			throws UserException, WrongUserException, IncorrectDateException {
		return service.login(username, pass);
	}

	@PutMapping
	@Operation(summary = "Change pass", description = "Changes the old password to a new one.")
	public User changePass(@RequestBody @Valid ChangePassRequest request) throws UserException {
		return service.changePasword(request.getNewPass(), request.getUser());
	}

	@GetMapping("/data")
	@Operation(summary = "Show full user", description = "Displays a user with all associated entities.")
	public User showUser(@RequestParam User user) throws UserException {
		return service.showUser(user);
	}

	@GetMapping("/PracticeRecord")
	@Operation(summary = "Retrieve records", description = "Retrieves all user records based on the given filters.")
	public List<PracticeRecord> consultAllRecords(@RequestParam User user,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date1,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date2,
			@RequestParam(required = false) String stateDate) throws UserException {
		return service.consultAllRecords(user, date1, date2, stateDate);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete record", description = "Deletes a record based on its ID.")
	public void deleteRecord(@PathVariable UUID id) throws UserException {
		service.deleteRecord(id);
	}

	@PostMapping
	@Operation(summary = "Create record", description = "Creates a record based on the given data.")
	public void createRecord(@RequestBody @Valid PracticeRecord record) throws UserException {
		service.createRecord(record);
	}

}
