package com.example.UserServer.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.UserServer.service.UserDAO;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private UserDAO userDAO;

    
	@Bean
	public CustomFilter customFilter() {
		return new CustomFilter();
	}

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/login", "/register").permitAll()
	                .anyRequest().authenticated()
	            )
	            .formLogin(formLogin ->
                formLogin
                    .loginPage("http://localhost:8080/signin-user")
                    .permitAll()
            )
	            .logout(logout ->
                logout
                	.logoutUrl("/logout")
                	.invalidateHttpSession(true)
                	.clearAuthentication(true)
                	.addLogoutHandler((request, response, authentication) -> {
                        // Retrieve the username from the authentication object
                        String username = authentication.getName();
                        // Remove the user's token and expiration timestamp upon logout
                        userDAO.removeUserToken(username);
                    })
                    .permitAll()
            )
	            .rememberMe(rememberMe ->
                rememberMe
                    .key("yourSecretKey")
                    .userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(604800) // 7 days
            );

	    }

	    @Bean
	    public PersistentTokenRepository persistentTokenRepository() {
	        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
	        //Add token to table when Martin's done
	        // Configure the DataSource for the token repository
	        // tokenRepository.setDataSource(dataSource);
	        return tokenRepository;
	    }
	    
    
}