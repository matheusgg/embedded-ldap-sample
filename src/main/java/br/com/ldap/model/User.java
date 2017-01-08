package br.com.ldap.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String account;
	private String name;
	private Set<String> types;
	private Collection<? extends GrantedAuthority> authorities;
}
