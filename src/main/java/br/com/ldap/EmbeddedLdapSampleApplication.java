package br.com.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmbeddedLdapSampleApplication {

	public static void main(final String[] args) {
		SpringApplication.run(EmbeddedLdapSampleApplication.class, args);
	}
}
