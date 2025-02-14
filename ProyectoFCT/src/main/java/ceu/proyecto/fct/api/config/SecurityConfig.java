package ceu.proyecto.fct.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ceu.proyecto.fct.api.filters.ApiKeyFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(name = "Authorization", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "API-KEY")
public class SecurityConfig {

	@Autowired
	private ApiKeyFilter apiKeyFilter;

	@Bean
	SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
