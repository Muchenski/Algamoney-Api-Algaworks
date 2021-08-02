package com.muchenski.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Profile("oauth-security")
@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private final String[] PUBLIC_GET = new String[] {};
	private final String[] PUBLIC_POST = new String[] { "/oauth/token/**" };

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired // Para o AuthenticationManagerBuilder ser injetado.
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		// Caso seja o perfil de testes, queremos poder acessar o banco de dados h2.
		if (activeProfile.equalsIgnoreCase("test")) {
			http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
			http.headers().frameOptions().disable();
		}

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, PUBLIC_GET).permitAll()
				.antMatchers(HttpMethod.POST, PUBLIC_POST).permitAll().anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Para não manter sessões.
				.and()
				.csrf().disable();
	}

}
