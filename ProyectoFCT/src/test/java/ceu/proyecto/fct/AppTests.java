package ceu.proyecto.fct;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import ceu.proyecto.fct.model.User;
import ceu.proyecto.fct.model.num.Perfil;
import ceu.proyecto.fct.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class AppTests {

    @Autowired
    private UserRepository userRepository; 
    

    @Test
    void testSaveAndFindUser() {
        // Crear usuario
        User user = new User();
        user.setUsername("testuser");
        user.setPass("password");
        user.setProfile(Perfil.STUDENT);
        user.setActive(true);

        // Guardar en la base de datos
        User savedUser = userRepository.save(user);
        UUID userId = savedUser.getId();
        
        // Buscar el usuario guardado
        User foundUser = userRepository.findById(userId).orElse(null);

        // Verificaciones
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser");
        assertThat(foundUser.getProfile()).isEqualTo(Perfil.STUDENT);
        assertThat(foundUser.isActive()).isTrue();
    }
    
    
}
