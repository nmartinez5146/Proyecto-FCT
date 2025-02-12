package ceu.proyecto.fct.api;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
import ceu.proyecto.fct.service.IncorrectDateException;
import ceu.proyecto.fct.service.ServiceRepository;
import ceu.proyecto.fct.service.UserException;
import ceu.proyecto.fct.service.WrongUserException;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("user")
public class UserApiService {

	private ServiceRepository service;
	
	@GetMapping
	@Operation(summary = "Login de usuario", description = "Hace login de un usuario a partir de su nombre de usuario y contraseña")
	public User login(@RequestParam String username, @RequestParam String pass) throws UserException, WrongUserException, IncorrectDateException {
		return service.login(username, pass);
	}
	
	@PutMapping
	@Operation(summary = "Cambiar contraseña", description = "Cambia la antigua contraseña por una nueva")
	public User changePass(@RequestBody String newPass, @RequestBody User user) throws UserException {
		return service.changePasword(newPass, user);
	}
	
	@GetMapping("/all")
	@Operation(summary = "Mostrar usuario completo", description = "Muestra un usuario con todas sus entidades asociadas")
	public User showUser(@RequestParam User user) throws UserException {
		return service.showUser(user);
	} 
	
	@GetMapping("/PracticeRecord")
	@Operation(summary = "Consultar registros", description = "Consulta todos los registros de un usuario a partir de los filtros dados")
	public List<PracticeRecord> consultAllRecords(@RequestParam User user, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date1, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date2, @RequestParam String stateDate) throws UserException{
		return service.consultAllRecords(user, date1, date2, stateDate);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Borrar registro", description = "Borra un registro a partir de su ID")
	public void deleteRecord(@PathVariable UUID id) throws UserException{
		service.deleteRecord(id);
	}
	
	@PostMapping
	@Operation(summary = "Crear registro", description = "Crea un registro a partir del dado")
	public void createRecord(@RequestBody PracticeRecord record) throws UserException{
		service.createRecord(record);
	}
	
}
