package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowired;




import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*
import com.example.demo.model.EmailData;
import com.example.demo.model.User;
import com.example.demo.repository.EmailDataRepository;
import com.example.demo.repository.UserRepository;
*/
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
@ComponentScan ({"com.example.demo"})
@EnableJpaRepositories ("com.example.demo.repository") // this fix the problem
@EnableAutoConfiguration
public class SpringbootBackendApplication implements CommandLineRunner{

	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	/*
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private EmailDataRepository emaildataRepository;
	*/
	
	@Override
	public void run(String... args) throws Exception {
		/*
		String sql = "CREATE DATABASE dashboard_gmail_omicrone DROP";

		jdbcTemplate.execute(sql);
		  */      
		/*
		this.userRepository.save(new User("mkhinini", "adam", "adam@hotmail.fr"));
		this.userRepository.save(new User("nom1", "prenom1", "email1@hotmail.fr"));
		this.userRepository.save(new User("nom2", "adam2", "email2@hotmail.fr"));
		this.userRepository.save(new User("mkhinini", "adammmm", "adam@hotmail.fr"));
		*/
		//this.emaildataRepository.save(new EmailData("titre", "contenu mail"));//<p>Bonjour,</p>\\n<p>Test.</p>\\n<p>Cordialement</p>\\n

	}

}